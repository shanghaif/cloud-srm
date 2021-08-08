package com.midea.cloud.component.i18n;

import java.io.File;
import java.util.*;

public class OrderedProperties extends Properties {

    private static final long serialVersionUID = -4627607243846121965L;

    private File file;
    private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

    public LinkedHashMap<Object, Object> getProps() {
        return props;
    }

    private final LinkedHashMap<Object, Object> props = new LinkedHashMap<Object, Object>();

    public OrderedProperties() {
        super();
    }

    public OrderedProperties(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    public Object put(Object key, Object value) {
        keys.add(key);
        props.put(key, value);
        return super.put(key, value);
    }

    public Set<Object> keySet() {
        return keys;
    }

    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<String>();
        for (Object key : this.keys) {
            set.add((String) key);
        }
        return set;
    }
}
