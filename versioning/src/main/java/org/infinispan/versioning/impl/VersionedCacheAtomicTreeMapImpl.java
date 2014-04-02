package org.infinispan.versioning.impl;

import org.infinispan.Cache;
import org.infinispan.atomic.AtomicObjectFactory;
import org.infinispan.versioning.utils.version.Version;
import org.infinispan.versioning.utils.version.VersionGenerator;
import org.jboss.logging.Logger;

import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * // TODO: Document this
 *
 * @author Pierre Sutra
 * @since 6.0
 */
public class VersionedCacheAtomicTreeMapImpl<K,V> extends VersionedCacheAbstractImpl<K,V> {

    AtomicObjectFactory factory;
    Logger logger;
    public VersionedCacheAtomicTreeMapImpl(Cache delegate, VersionGenerator generator, String name) {
        super(delegate,generator,name);
        factory = new AtomicObjectFactory((Cache<Object, Object>) delegate);
        this.logger  = Logger.getLogger(this.getClass());

    }

    @Override
    protected SortedMap<Version, V> versionMapGet(K key) {
        return factory.getInstanceOf(TreeMap.class,key,true,null,false);
    }

    @Override
    protected void versionMapPut(K key, V value, Version version) {
        factory.getInstanceOf(TreeMap.class,key,true,null,false).put(version, value);
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return delegate.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        for(Object k: delegate.keySet()){
            if(factory.getInstanceOf(TreeMap.class,k,true,null,false).containsValue(o))
                return true;
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<V> get(K key, Version first, Version last) {
        return factory.getInstanceOf(TreeMap.class,key,true,null,false).subMap(first, last).values();
    }

}
