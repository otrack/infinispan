/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.clustering.infinispan.subsystem;

import java.util.HashMap;
import java.util.Map;

import org.jboss.as.controller.AttributeDefinition;

/**
 * Enumerates the elements used in the Infinispan subsystem schema.
 *
 * @author Paul Ferraro
 * @author Richard Achmatowicz (c) 2011 RedHat Inc.
 * @author Tristan Tarrant
 */
public enum Element {
    // must be first
    UNKNOWN((String)null),

    ALIAS(ModelKeys.ALIAS),
    AUTHORIZATION(ModelKeys.AUTHORIZATION),
    BACKUP(ModelKeys.BACKUP),
    BACKUPS(ModelKeys.BACKUPS),
    BINARY_KEYED_TABLE(ModelKeys.BINARY_KEYED_TABLE),
    @Deprecated BUCKET_TABLE(ModelKeys.BUCKET_TABLE),
    CACHE_CONTAINER(ModelKeys.CACHE_CONTAINER),
    CONNECTION_POOL(ModelKeys.CONNECTION_POOL),
    CLUSTER_LOADER(ModelKeys.CLUSTER_LOADER),
    COMPATIBILITY(ModelKeys.COMPATIBILITY),
    COMPRESSION(ModelKeys.COMPRESSION),
    COMMON_NAME_ROLE_MAPPER(ModelKeys.COMMON_NAME_ROLE_MAPPER),
    CLUSTER_ROLE_MAPPER(ModelKeys.CLUSTER_ROLE_MAPPER),
    CUSTOM_ROLE_MAPPER(ModelKeys.CUSTOM_ROLE_MAPPER),
    DATA_COLUMN(ModelKeys.DATA_COLUMN),
    DISTRIBUTED_CACHE(ModelKeys.DISTRIBUTED_CACHE),
    @Deprecated ENTRY_TABLE(ModelKeys.ENTRY_TABLE),
    EVICTION(ModelKeys.EVICTION),
    EXPIRATION(ModelKeys.EXPIRATION),
    FILE_STORE(ModelKeys.FILE_STORE),
    ID_COLUMN(ModelKeys.ID_COLUMN),
    IDENTITY_ROLE_MAPPER(ModelKeys.IDENTITY_ROLE_MAPPER),
    INVALIDATION_CACHE(ModelKeys.INVALIDATION_CACHE),
    LEVELDB_STORE(ModelKeys.LEVELDB_STORE),
    @Deprecated JDBC_STORE("jdbc-store"),
    STRING_KEYED_JDBC_STORE(ModelKeys.STRING_KEYED_JDBC_STORE),
    BINARY_KEYED_JDBC_STORE(ModelKeys.BINARY_KEYED_JDBC_STORE),
    MIXED_KEYED_JDBC_STORE(ModelKeys.MIXED_KEYED_JDBC_STORE),
    IMPLEMENTATION(ModelKeys.IMPLEMENTATION),
    INDEXING(ModelKeys.INDEXING),
    LOADER(ModelKeys.LOADER),
    LOCAL_CACHE(ModelKeys.LOCAL_CACHE),
    LOCKING(ModelKeys.LOCKING),
    PROPERTY(ModelKeys.PROPERTY),
    @Deprecated REHASHING("rehashing"),
    REMOTE_SERVER(ModelKeys.REMOTE_SERVER),
    REMOTE_STORE(ModelKeys.REMOTE_STORE),
    REPLICATED_CACHE(ModelKeys.REPLICATED_CACHE),
    REST_STORE(ModelKeys.REST_STORE),
    ROLE(ModelKeys.ROLE),
    SECURITY(ModelKeys.SECURITY),
    STATE_TRANSFER(ModelKeys.STATE_TRANSFER),
    STORE(ModelKeys.STORE),
    STRING_KEYED_TABLE(ModelKeys.STRING_KEYED_TABLE),
    TAKE_OFFLINE(ModelKeys.TAKE_OFFLINE),
    TIMESTAMP_COLUMN(ModelKeys.TIMESTAMP_COLUMN),
    TRANSACTION(ModelKeys.TRANSACTION),
    TRANSPORT(ModelKeys.TRANSPORT),
    WRITE_BEHIND(ModelKeys.WRITE_BEHIND),
    ;

    private final String name;
    private final AttributeDefinition definition;

    Element(final String name) {
        this.name = name;
        this.definition = null;
    }

    Element(final AttributeDefinition definition) {
        this.name = definition.getXmlName();
        this.definition = definition;
    }

    /**
     * Get the local name of this element.
     *
     * @return the local name
     */
    public String getLocalName() {
        return name;
    }

    public AttributeDefinition getDefinition() {
        return definition;
    }

    private static final Map<String, Element> elements;

    static {
        final Map<String, Element> map = new HashMap<String, Element>();
        for (Element element : values()) {
            final String name = element.getLocalName();
            if (name != null) map.put(name, element);
        }
        elements = map;
    }

    public static Element forName(String localName) {
        final Element element = elements.get(localName);
        return element == null ? UNKNOWN : element;
    }
}
