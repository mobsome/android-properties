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
import android.util.Log;

/**
 * Class representing single property
 */
public abstract class Property<T> {
    private static final String TAG = Property.class.getSimpleName();
    /**
     * Property name
     */
    final String name;
    /**
     * Property value
     */
    private T value;
    /**
     * Property default value
     */
    final T defaultValue;
    /**
     * Property store
     */
    private final PropertyStore store;
    /**
     * Whether real value has been set
     */
    private boolean valueSet;

    /**
     * Creates property with provided name, value and persisting mechanism
     *
     * @param name         property name
     * @param defaultValue default property value
     * @param store        persisting mechanism
     */
    Property(@NonNull String name, T defaultValue, @NonNull PropertyStore store) {
        Preconditions.checkNotNull(name, "name must not be null");
        Preconditions.checkNotNull(store, "store must not be null");
        this.name = name;
        this.defaultValue = defaultValue;
        this.store = store;
        restore();
    }

    /**
     * Restores property value from persistent storage
     */
    synchronized final void restore() {
        final PropertyReader reader = store.getReader();
        if (reader.contains(name)) {
            try {
                value = readValue(reader);
                valueSet = true;
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                valueSet = false;
            }
        }
    }

    /**
     * Reads property value from persistent storage with provided reader
     *
     * @param reader property value reader
     * @return property value
     * @throws PropertyAccessException when property access failed
     */
    abstract T readValue(@NonNull PropertyReader reader) throws PropertyAccessException;

    /**
     * Writes property value to persistent storage
     *
     * @param async whether operation should be performed asynchronously
     */
    synchronized final void persist(boolean async) {
        if (valueSet) {
            final PropertyWriter writer = store.getWriter();
            writer.edit();
            try {
                writeValue(writer, value);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }

            if (async) {
                writer.commitAsync();
            } else {
                writer.commit();
            }
        }
    }

    /**
     * Writes property value to persistent storage with provided writer
     *
     * @param writer property value writer
     * @param value  property value to be stored
     * @throws PropertyAccessException when property access failed
     */
    abstract void writeValue(@NonNull PropertyWriter writer, T value)
            throws PropertyAccessException;

    /**
     * Sets new value for this property. Operation is performed on the same calling thread.
     *
     * @param value new property value
     */
    public synchronized final void set(T value) {
        this.value = value;
        valueSet = true;
        persist(false);
    }

    /**
     * Sets new value for this property. Value is persisted in underlying {@link PropertyStore}
     * asynchronously
     *
     * @param value new property value
     */
    public synchronized final void setAsync(T value) {
        this.value = value;
        valueSet = true;
        persist(true);
    }

    /**
     * Returns property value or default value if real has not been set
     *
     * @return property value or default one
     */
    public synchronized final T get() {
        if (valueSet) {
            return value;
        }
        return defaultValue;
    }
}
