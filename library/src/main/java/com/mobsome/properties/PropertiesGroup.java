/*
 * Copyright (C) 2016 Mobsome
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobsome.properties;

import android.support.annotation.NonNull;

/**
 * Group of properties that are stored within single {@link com.mobsome.properties.PropertyStore}
 */
public abstract class PropertiesGroup {
    private final PropertyStore store;

    /**
     * Creates group of properties with specified {@link com.mobsome.properties.PropertyStore}
     *
     * @param store property store for this property group
     */
    public PropertiesGroup(@NonNull PropertyStore store) {
        Preconditions.checkNotNull(store, "store must not be null");
        this.store = store;
    }

    /**
     * Creates integer property for this group
     *
     * @param key          property name
     * @param defaultValue default property value
     * @return integer property for this group
     */
    protected Properties.IntProperty createProperty(String key, int defaultValue) {
        return new Properties.IntProperty(key, defaultValue, store);
    }

    /**
     * Creates long property for this group
     *
     * @param key          property name
     * @param defaultValue default property value
     * @return long property for this group
     */
    protected Properties.LongProperty createProperty(String key, long defaultValue) {
        return new Properties.LongProperty(key, defaultValue, store);
    }

    /**
     * Creates float property for this group
     *
     * @param key          property name
     * @param defaultValue default property value
     * @return float property for this group
     */
    protected Properties.FloatProperty createProperty(String key, float defaultValue) {
        return new Properties.FloatProperty(key, defaultValue, store);
    }

    /**
     * Creates double property for this group
     *
     * @param key          property name
     * @param defaultValue default property value
     * @return double property for this group
     */
    protected Properties.DoubleProperty createProperty(String key, double defaultValue) {
        return new Properties.DoubleProperty(key, defaultValue, store);
    }

    /**
     * Creates boolean property for this group
     *
     * @param key          property name
     * @param defaultValue default property value
     * @return boolean property for this group
     */
    protected Properties.BooleanProperty createProperty(String key, boolean defaultValue) {
        return new Properties.BooleanProperty(key, defaultValue, store);
    }

    /**
     * Creates string property for this group
     *
     * @param key          property name
     * @param defaultValue default property value
     * @return string property for this group
     */
    protected Properties.StringProperty createProperty(String key, String defaultValue) {
        return new Properties.StringProperty(key, defaultValue, store);
    }

    /**
     * Creates enum property for this group
     *
     * @param key          property name
     * @param defaultValue default property value
     * @param <E>          enum type
     * @return enum property for this group
     */
    protected <E extends Enum<E>> Properties.EnumProperty<E> createProperty(String key,
                                                                            E defaultValue) {
        return new Properties.EnumProperty<>(key, defaultValue, store);
    }
}
