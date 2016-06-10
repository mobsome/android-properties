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
 * Interface for properties writer. Classes implementing this interface are responsible
 * for persisting properties values.
 */
public interface PropertyWriter {
    /**
     * Starts editing properties to be stored by this writer. Must be called before any editing
     * value of any property
     */
    void edit();

    /**
     * Persists all changes made after {@link PropertyWriter#edit()} has been called.
     * Storing process is performed on the same thread this method has been called.
     */
    void commit();

    /**
     * Persists all changes made after {@link PropertyWriter#edit()} has been called.
     * Storing process is performed on separate thread.
     */
    void commitAsync();

    /**
     * Persists integer property value
     *
     * @param key   property name
     * @param value property value
     * @throws PropertyAccessException when property access failed
     */
    void writeInt(@NonNull String key, int value) throws PropertyAccessException;

    /**
     * Persists long property value
     *
     * @param key   property name
     * @param value property value
     * @throws PropertyAccessException when property access failed
     */
    void writeLong(@NonNull String key, long value) throws PropertyAccessException;

    /**
     * Persists float property value
     *
     * @param key   property name
     * @param value property value
     * @throws PropertyAccessException when property access failed
     */
    void writeFloat(@NonNull String key, float value) throws PropertyAccessException;

    /**
     * Persists double property value
     *
     * @param key   property name
     * @param value property value
     * @throws PropertyAccessException when property access failed
     */
    void writeDouble(@NonNull String key, double value) throws PropertyAccessException;

    /**
     * Persists boolean property value
     *
     * @param key   property name
     * @param value property value
     * @throws PropertyAccessException when property access failed
     */
    void writeBoolean(@NonNull String key, boolean value) throws PropertyAccessException;

    /**
     * Persists string property value
     *
     * @param key   property name
     * @param value property value
     * @throws PropertyAccessException when property access failed
     */
    void writeString(@NonNull String key, String value) throws PropertyAccessException;

    /**
     * Deletes property with provided name
     *
     * @param key property name
     */
    void remove(@NonNull String key);
}
