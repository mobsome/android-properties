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
 * Interface for properties reader. Classes implementing this interface are responsible
 * for reading values of persisted properties.
 */
public interface PropertyReader {
    /**
     * Checks whether property with specified name exists in persistence storage
     *
     * @param key property name
     * @return whether property with specified name exists
     */
    boolean contains(@NonNull String key);

    /**
     * Gets integer property value or returns default value if provided property doesn't exist
     *
     * @param key          property name
     * @param defaultValue default value which is returned if property doesn't exist
     * @return property value or default value
     * @throws PropertyAccessException when property access failed
     */
    int getInt(@NonNull String key, int defaultValue) throws PropertyAccessException;

    /**
     * Gets long property value or returns default value if provided property doesn't exist
     *
     * @param key          property name
     * @param defaultValue default value which is returned if property doesn't exist
     * @return property value or default value
     * @throws PropertyAccessException when property access failed
     */
    long getLong(@NonNull String key, long defaultValue) throws PropertyAccessException;

    /**
     * Gets float property value or returns default value if provided property doesn't exist
     *
     * @param key          property name
     * @param defaultValue default value which is returned if property doesn't exist
     * @return property value or default value
     * @throws PropertyAccessException when property access failed
     */
    float getFloat(@NonNull String key, float defaultValue) throws PropertyAccessException;

    /**
     * Gets double property value or returns default value if provided property doesn't exist
     *
     * @param key          property name
     * @param defaultValue default value which is returned if property doesn't exist
     * @return property value or default value
     * @throws PropertyAccessException when property access failed
     */
    double getDouble(@NonNull String key, double defaultValue) throws PropertyAccessException;

    /**
     * Gets boolean property value or returns default value if provided property doesn't exist
     *
     * @param key          property name
     * @param defaultValue default value which is returned if property doesn't exist
     * @return property value or default value
     * @throws PropertyAccessException when property access failed
     */
    boolean getBoolean(@NonNull String key, boolean defaultValue) throws PropertyAccessException;

    /**
     * Gets string property value or returns default value if provided property doesn't exist
     *
     * @param key          property name
     * @param defaultValue default value which is returned if property doesn't exist
     * @return property value or default value
     * @throws PropertyAccessException when property access failed
     */
    String getString(@NonNull String key, String defaultValue) throws PropertyAccessException;
}
