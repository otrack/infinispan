package org.infinispan.versioning.impl;

import org.infinispan.Cache;
import org.infinispan.commons.util.concurrent.NotifyingFuture;
import org.infinispan.versioning.VersionedCache;
import org.infinispan.versioning.utils.version.Version;
import org.infinispan.versioning.utils.version.VersionGenerator;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * A basic implementation for a versioned cache.
 * This implementation is abstract.
 * A concrete implementation needs only to write two methods to be functional.
 * It is however advised to overload more methods to be efficient, as the algorithmic structure is quite basic.
 *
 * @author Pierre Sutra
 * @since 6.0
 */
public abstract class VersionedCacheAbstractImpl<K,V> implements VersionedCache<K,V> {

    protected Cache<K,?> delegate;
    protected VersionGenerator generator;
    protected String name;

    public VersionedCacheAbstractImpl(Cache<K, ?> delegate, VersionGenerator generator, String name) {
        this.delegate = delegate;
        this.generator = generator;
        this.name = name;
    }

    @Override
    public void put(K key, V value, Version version) {
        versionMapPut(key, value, version);
    }

    /**
     *
     * Return all the version between <i><first/i> and <i>last</i> exclusive.
     *
     * @param key
     * @param first
     * @param last
     * @return
     */
    @Override
    public Collection<V> get(K key, Version first, Version last) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.subMap(first, last).values();
    }

    @Override
    public V get(K key, Version version) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.get(version);
    }

    @Override
    public V getLatest(K key, Version upperBound) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.get(map.headMap(upperBound).lastKey());
    }

    @Override
    public V getEarliest(K key, Version lowerBound) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.get(map.tailMap(lowerBound).firstKey());
    }

    @Override
    public Version getLatestVersion(K key) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.lastKey();
    }

    @Override
    public Version getLatestVersion(K key, Version upperBound) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.tailMap(upperBound).firstKey();
    }

    @Override
    public Version getEarliestVersion(K key) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.firstKey();
    }

    @Override
    public Version getEarliestVersion(K key, Version lowerBound) {
        SortedMap<Version,V> map = versionMapGet(key);
        if(map.isEmpty())
            return null;
        return map.headMap(lowerBound).firstKey();
    }

    @Override
    public int size() {
        int result=0;
        for(K key: keySet()){
            SortedMap<Version,V> map = versionMapGet(key);
            result += map.size();
        }
        return result;
    }

    @Override
    public V get(Object o) {
        return get((K) o, getLatestVersion((K) o));
    }

    @Override
    public Collection<V> values() {
        Collection<V> result = new ArrayList<V>();
        for(K key: keySet()){
            SortedMap<Version,V> map = versionMapGet(key);
            result.addAll(map.values());
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        Version lversion = getLatestVersion(key);
        Version nversion = null;
        V lval=null;
        if(lversion==null){
            nversion = generator.generateNew();
        }else{
            lval = get(key,lversion);
            nversion = generator.increment(lversion);
        }
        put(key,value,nversion);
        return lval;
    }

    //
    // OBJECT METHODS
    //

    protected abstract SortedMap<Version,V> versionMapGet(K key);

    protected abstract void versionMapPut(K key, V value, Version version);


    //
    // NYI
    //

    @Override
    public NotifyingFuture<V> putAsync(K key, V value) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> putAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Void> putAllAsync(Map<? extends K, ? extends V> data, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Void> clearAsync() {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> putIfAbsentAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> removeAsync(Object key) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Boolean> removeAsync(Object key, Object value) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> replaceAsync(K key, V value) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> replaceAsync(K key, V value, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> replaceAsync(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue, V newValue) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue, V newValue, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue, V newValue, long lifespan, TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public NotifyingFuture<V> getAsync(K key) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public String getName() {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public String getVersion() {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit unit) {
        // TODO: Customise this generated block
    }

    @Override
    public V replace(K key, V value, long lifespan, TimeUnit unit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public boolean replace(K key, V oldValue, V value, long lifespan, TimeUnit unit) {
        return false;  // TODO: Customise this generated block
    }

    @Override
    public V put(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public V putIfAbsent(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        // TODO: Customise this generated block
    }

    @Override
    public V replace(K key, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public boolean replace(K key, V oldValue, V value, long lifespan, TimeUnit lifespanUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        return false;  // TODO: Customise this generated block
    }

    @Override
    public V remove(Object key) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        // TODO: Customise this generated block
    }

    @Override
    public void clear() {
        // TODO: Customise this generated block
    }

    @Override
    public V putIfAbsent(K k, V v) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public boolean remove(Object o, Object o2) {
        return false;  // TODO: Customise this generated block
    }

    @Override
    public boolean replace(K k, V v, V v2) {
        return false;  // TODO: Customise this generated block
    }

    @Override
    public V replace(K k, V v) {
        return null;  // TODO: Customise this generated block
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;  // TODO: Customise this generated block
    }


    @Override
    public void start() {
        // TODO: Customise this generated block
    }

    @Override
    public void stop() {
        // TODO: Customise this generated block
    }
}
