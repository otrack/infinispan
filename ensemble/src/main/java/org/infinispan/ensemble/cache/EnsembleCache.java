package org.infinispan.ensemble.cache;

import org.infinispan.commons.api.BasicCache;
import org.infinispan.commons.util.concurrent.NotifyingFuture;
import org.infinispan.ensemble.Site;
import org.infinispan.ensemble.indexing.Indexable;
import org.infinispan.ensemble.indexing.Primary;
import org.infinispan.ensemble.indexing.Stored;
import org.infinispan.util.logging.Log;
import org.infinispan.util.logging.LogFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 *
 * An EnsembleCache offers a ConcurrentMap API over a list of EnsembleCaches.
 * Such an abstraction is of interest in various cases, e.g., when aggregating multiple Infinispan deployments.
 *
 * @author Pierre Sutra
 * @since 7.0
 */
public abstract class EnsembleCache<K,V> extends Indexable implements BasicCache<K,V> {

    protected static final Log log = LogFactory.getLog(EnsembleCache.class);

    @Primary
    @Stored
    protected String name;

    @Stored
    protected List<? extends EnsembleCache<K,V>> caches;

    public EnsembleCache(String name, List<? extends EnsembleCache<K,V>> caches){
        this.name = name;
        this.caches= caches;
    }

    public boolean isLocal(){
        return false;
    }

    // READ

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }


    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object o) {
        for (EnsembleCache cache : caches){
            if (cache.containsKey(o))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object o) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> getAsync(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVersion() {
        throw new UnsupportedOperationException();
    }

    // WRITE

    @Override
    public V putIfAbsent(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o, Object o2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K k, V v, V v2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Void> clearAsync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> removeAsync(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Boolean> removeAsync(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> replaceAsync(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> replaceAsync(K key, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<V> replaceAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue, V newValue, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue, V newValue, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K key, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, V oldValue, V value, long lifespan, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, V oldValue, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        throw new UnsupportedOperationException();
    }

    //
    // LIFE CYCLE
    //

    @Override
    public void start() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException();
    }

    // OTHERS

    public String getName() {
        return name;
    }

    public List<? extends EnsembleCache<K,V>> getCaches(){ return caches;}

    public Set<Site> sites(){
        Set<Site> ret = new HashSet<>();
        for(EnsembleCache<K,V> cache : caches){
            ret.addAll(cache.sites());
        }
        return ret;
    }

    @Override
    public String toString(){
        return getName();
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

}
