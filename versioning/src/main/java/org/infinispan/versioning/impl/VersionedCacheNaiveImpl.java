package org.infinispan.versioning.impl;

import org.infinispan.Cache;
import org.infinispan.versioning.utils.version.Version;
import org.infinispan.versioning.utils.version.VersionGenerator;

import java.util.Set;
import java.util.SortedMap;

/**
 * Implement the Naive multi-versioning technique.
 * In this implementation, all the versions are stored under the same key.
 * 
 * @author valerio.schiavoni@gmail.com
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class VersionedCacheNaiveImpl<K,V> extends VersionedCacheAbstractImpl<K,V> {

	
	public VersionedCacheNaiveImpl(Cache<K, ?> delegate,
			VersionGenerator generator, String name) {
		super(delegate, generator, name);
	}

	@Override
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	@Override
	public boolean containsValue(Object arg0) {
		throw new UnsupportedOperationException("to be implemented");
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return delegate.keySet();
	}

	@Override
	protected SortedMap versionMapGet(Object key) {
		throw new UnsupportedOperationException("to be implemented");

	}

	@Override
	protected void versionMapPut(Object key, Object value,
			Version version) {
		
		throw new UnsupportedOperationException("to be implemented");

	}

}
