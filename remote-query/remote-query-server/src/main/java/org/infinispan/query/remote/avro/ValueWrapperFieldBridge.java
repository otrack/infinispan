package org.infinispan.query.remote.avro;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

/**
 * // TODO: Document this
 *
 * @author otrack
 * @since 4.0
 */
public class ValueWrapperFieldBridge implements TwoWayFieldBridge{

    public static final String NULL="__null__";

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
        GenericData.Record record = (GenericData.Record) value;
        Schema schema = record.getSchema();
        StringField stringField;
        for(Schema.Field field : schema.getFields()){
            if (record.get(field.name())!=null){
                stringField = new StringField(
                        field.name(),
                        record.get(field.name()).toString(),
                        Field.Store.YES);
            }else{
                stringField = new StringField(
                        field.name(),
                        NULL,
                        Field.Store.YES);
            }
            document.add(stringField);
        }
    }

    @Override
    public Object get(String name, Document document) {
        if (document.get(name).equals(NULL))
            return null;
        return document.get(name);
    }

    @Override
    public String objectToString(Object object) {
        if (object==null)
            return NULL;
        return object.toString();
    }
}