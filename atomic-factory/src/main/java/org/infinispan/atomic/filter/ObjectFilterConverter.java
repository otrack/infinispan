package org.infinispan.atomic.filter;

import org.infinispan.Cache;
import org.infinispan.atomic.object.*;
import org.infinispan.metadata.Metadata;
import org.infinispan.notifications.cachelistener.filter.AbstractCacheEventFilterConverter;
import org.infinispan.notifications.cachelistener.filter.CacheAware;
import org.infinispan.notifications.cachelistener.filter.EventType;
import org.infinispan.util.logging.Log;
import org.infinispan.util.logging.LogFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.infinispan.atomic.object.Utils.marshall;
import static org.infinispan.atomic.object.Utils.unmarshall;

/**
 * @author Pierre Sutra
 * @since 7.2
 */
public class ObjectFilterConverter<K> extends AbstractCacheEventFilterConverter<K,Object,Object>
      implements CacheAware<Object,Object>, Externalizable{

   // Class fields & methods

   private static Log log = LogFactory.getLog(ObjectFilterConverter.class);
   private static ConcurrentHashMap<Object,ObjectFilterConverter> objectFilterConverterMap = new ConcurrentHashMap<>();
   public static ObjectFilterConverter get(Object[] params){
      
      Object key = params[1];
      
      if (!objectFilterConverterMap.containsKey(key)) {
      
         ObjectFilterConverter objectFilterConverter =
               new ObjectFilterConverter<>( // params[0] = containerID 
                     params[1],
                     (Class) params[2],
                     params.length >= 4 ? Arrays.copyOfRange(params, 3, params.length - 1) : null);
      
         objectFilterConverterMap.putIfAbsent(key, objectFilterConverter);
      }
      
      return objectFilterConverterMap.get(key);
   }

   // Object fields

   private Cache<Object,Object> cache;
   private Object key;
   private Object object;
   private Class clazz;
   private CallClose pendingCloseCall;
   private Object[] initArgs;
   private Set<UUID> openedContainersID;

   public ObjectFilterConverter(){}

   private ObjectFilterConverter(
         final K key,
         final Class clazz,
         final Object... initArgs){
      this.key = key;
      this.clazz = clazz;
      this.initArgs = initArgs;
      this.openedContainersID = new HashSet<>();
   }

   @Override
   public void setCache(Cache <Object,Object> cache) {
      this.cache = cache;
   }

   @Override
   public synchronized Object filterAndConvert(Object key, Object oldValue, Metadata oldMetadata, Object newValue,
         Metadata newMetadata, EventType eventType) {

      assert (cache!=null);

      try {

         Call call = (Call) newValue;

         CallFuture future = new CallFuture(call.getCallID());

         if (call instanceof CallInvoke) {

            if (log.isDebugEnabled()) log.debug(this + "retrieved CallInvoke ");
            Object responseValue = handleInvocation((CallInvoke) call);
            future.set(responseValue);

         } else if (call instanceof CallPersist) {

            if (log.isDebugEnabled()) log.debug(this + "retrieved CallPersist ");
            assert (pendingCloseCall!=null);
            future = new CallFuture(pendingCloseCall.getCallID());
            future.set(null);
            pendingCloseCall = null;


         } else if (call instanceof CallOpen ) {

            openedContainersID.add(call.getCallerID());

            if (object==null) {

               if (oldValue == null) {

                  if (log.isDebugEnabled()) log.debug(this + "Creating new object");
                  object = Utils.initObject(clazz, initArgs);

               } else {

                  if (oldValue instanceof CallPersist) {

                     if (log.isDebugEnabled())
                        log.debug(this + "Retrieving object from persistent state.");
                     object = unmarshall(((CallPersist) oldValue).getBytes());
                     assert object.getClass().equals(clazz);

                  } else {

                     throw new IllegalStateException("Cannot rebuild object.");

                  }

               }
               
            } else if (((CallOpen)call).getForceNew()) {

               if (log.isDebugEnabled()) log.debug(this + "Creating new object (forced)");
               object = Utils.initObject(clazz, initArgs);

            }

            future.set(null);

         } else if (call instanceof CallClose) {

            openedContainersID.remove(call.getCallerID());

            if (openedContainersID.size()==0 && pendingCloseCall==null) {

               assert (object!=null);
               if (log.isDebugEnabled()) log.debug(this + "Persisting object");
               pendingCloseCall = (CallClose)call;
               cache.putAsync(
                     key,
                     new CallPersist(call.getCallerID(), marshall(object)));

            } else {

               future.set(null);
               
            }

         }

         if (log.isDebugEnabled()) log.debug(this + "Future " + future.getCallID() + " -> "+future.isDone());

         if (future.isDone())
            return future;

      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;

   }

   @Override
   public String toString(){
      return "ObjectFilterConverter["+ key.toString()+"]";
   }

   /**
    *
    * @param invocation
    * @return true if the operation is local
    * @throws InvocationTargetException
    * @throws IllegalAccessException
    */
   private Object handleInvocation(CallInvoke invocation)
         throws InvocationTargetException, IllegalAccessException {
      Object ret = Utils.callObject(object, invocation.method, invocation.arguments);
      if (log.isDebugEnabled()) log.debug(this+"Calling " + invocation+" (="+(ret==null ? "null" : ret.toString())+")");
      return  ret;
   }

   @Override 
   public synchronized void writeExternal(ObjectOutput objectOutput) throws IOException {
      objectOutput.writeObject(key);
      objectOutput.writeObject(object);
      objectOutput.writeObject(clazz);
      objectOutput.writeObject(pendingCloseCall);
      objectOutput.writeObject(initArgs);
      objectOutput.writeObject(openedContainersID);
   }

   @Override 
   public synchronized void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
      key = objectInput.readObject();
      object = objectInput.readObject();
      clazz = (Class) objectInput.readObject();
      pendingCloseCall = (CallClose) objectInput.readObject();
      initArgs = (Object[]) objectInput.readObject();
      openedContainersID = (Set<UUID>) objectInput.readObject();
   }
   
   public Object readResolve(){
      objectFilterConverterMap.putIfAbsent(key,this);
      return objectFilterConverterMap.get(key);
   }
}
