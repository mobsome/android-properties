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
 * Interface for property store. Class implementing this interface are responsible for delivering
 * mechanism for storing and retrieving properties values from persistent storage
 */
public interface PropertyStore {
    /**
     * Returns properties reader
     *
     * @return properties reader for this store
     */
    @NonNull
    PropertyReader getReader();

    /**
     * Returns properties writer
     *
     * @return properties writer for this store
     */
    @NonNull
    PropertyWriter getWriter();

    /**
     * Registers a callback to be invoked when a change happens to a property.
     *
     * @param listener The callback that will run.
     * @see #unregisterOnPropertyChangeListener
     */
    void registerOnPropertyChangeListener(@NonNull OnPropertyChangeListener listener);

    /**
     * Unregisters a previous callback.
     *
     * @param listener The callback that should be unregistered.
     * @see #registerOnPropertyChangeListener
     */
    void unregisterOnPropertyChangeListener(@NonNull OnPropertyChangeListener listener);
}
