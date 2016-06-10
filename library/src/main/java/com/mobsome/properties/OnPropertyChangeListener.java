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
 * Interface definition for a callback to be invoked when a property is changed.
 */
public interface OnPropertyChangeListener {
    /**
     * Called when a property is changed, added, or removed. This
     * may be called even if a property is set to its existing value.
     *
     * @param propertyStore The {@link PropertyStore} that received
     *                      the change.
     * @param key           The key of the property that was changed, added, or
     *                      removed.
     */
    void onPropertyChanged(@NonNull PropertyStore propertyStore, @NonNull String key);
}
