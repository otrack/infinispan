package org.infinispan.client.hotrod.impl.avro;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.impl.RemoteCacheImpl;

/**
 * // TODO: Document this
 *
 * @author otrack
 * @since 4.0
 */
public class AvroSearch {

    private AvroSearch() {
    }

    public static AvroQueryFactory getQueryFactory(RemoteCache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("cache parameter cannot be null");
        }

        return new AvroQueryFactory((RemoteCacheImpl) cache);
    }

}
