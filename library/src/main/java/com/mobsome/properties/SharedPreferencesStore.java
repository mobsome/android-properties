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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties persistent store that uses {@link android.content.SharedPreferences} to store
 * properties values
 */
public class SharedPreferencesStore implements PropertyStore {
    private final SharedPreferences sharedPreferences;
    private List<OnPropertyChangeListener> listeners;

    /**
     * Creates {@link com.mobsome.properties.SharedPreferencesStore} that uses default
     * {@link android.content.SharedPreferences} for storing preferences values
     *
     * @param context application context
     */
    public SharedPreferencesStore(@NonNull final Context context) {
        Preconditions.checkNotNull(context, "context must not be null");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Creates {@link com.mobsome.properties.SharedPreferencesStore} providing application context
     * and file name in which values will be stored
     *
     * @param context  application context
     * @param fileName shared preferences file
     */
    public SharedPreferencesStore(@NonNull final Context context, @NonNull final String fileName) {
        Preconditions.checkNotNull(context, "context must not be null");
        Preconditions.checkNotNull(fileName, "fileName must not be null");
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * Creates {@link com.mobsome.properties.SharedPreferencesStore} providing default
     * implementation of {@link android.content.SharedPreferences}
     *
     * @param sharedPreferences shared preferences used to store properties
     */
    public SharedPreferencesStore(@NonNull SharedPreferences sharedPreferences) {
        Preconditions.checkNotNull(sharedPreferences, "sharedPreferences must not be null");
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    @NonNull
    public PropertyReader getReader() {
        return reader;
    }

    @Override
    @NonNull
    public PropertyWriter getWriter() {
        return writer;
    }

    @Override
    public void registerOnPropertyChangeListener(@NonNull OnPropertyChangeListener listener) {
        Preconditions.checkNotNull(listener, "listener must not be null");

        synchronized (this) {
            if (listeners == null) {
                listeners = new ArrayList<>();
                sharedPreferences.registerOnSharedPreferenceChangeListener(globalListener);
            }

            listeners.add(listener);
        }
    }

    @Override
    public void unregisterOnPropertyChangeListener(@NonNull OnPropertyChangeListener listener) {
        Preconditions.checkNotNull(listener, "listener must not be null");

        synchronized (this) {
            if (listeners == null) {
                return;
            }
            listeners.remove(listener);

            if (listeners.isEmpty()) {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(globalListener);
                listeners = null;
            }
        }
    }

    /**
     * Shared preferences properties reader
     */
    private final PropertyReader reader = new PropertyReader() {
        @Override
        public boolean contains(@NonNull String key) {
            Preconditions.checkNotNull(key, "key must not be null");
            return sharedPreferences.contains(key);
        }

        @Override
        public int getInt(@NonNull String key, int defaultValue) {
            Preconditions.checkNotNull(key, "key must not be null");
            return sharedPreferences.getInt(key, defaultValue);
        }

        @Override
        public long getLong(@NonNull String key, long defaultValue) {
            Preconditions.checkNotNull(key, "key must not be null");
            return sharedPreferences.getLong(key, defaultValue);
        }

        @Override
        public float getFloat(@NonNull String key, float defaultValue) {
            Preconditions.checkNotNull(key, "key must not be null");
            return sharedPreferences.getFloat(key, defaultValue);
        }

        @Override
        public double getDouble(@NonNull String key, double defaultValue) {
            Preconditions.checkNotNull(key, "key must not be null");
            final String doubleValue = sharedPreferences.getString(key, null);
            double value = defaultValue;
            try {
                if (doubleValue != null) {
                    value = Double.parseDouble(doubleValue);
                }
            } catch (NumberFormatException ex) {
                // Can't do anything
            }
            return value;
        }

        @Override
        public boolean getBoolean(@NonNull String key, boolean defaultValue) {
            Preconditions.checkNotNull(key, "key must not be null");
            return sharedPreferences.getBoolean(key, defaultValue);
        }

        @Override
        public String getString(@NonNull String key, String defaultValue) {
            Preconditions.checkNotNull(key, "key must not be null");
            return sharedPreferences.getString(key, defaultValue);
        }
    };

    /**
     * Shared preferences value writer
     */
    private final PropertyWriter writer = new PropertyWriter() {
        private SharedPreferences.Editor editor;

        /**
         * Ensures that {@link PropertyWriter#edit()} has been called
         */
        private void ensureInEditMode() {
            if (editor == null) {
                throw new IllegalStateException();
            }
        }

        @Override
        public void edit() {
            editor = sharedPreferences.edit();
        }

        @Override
        public void commit() {
            ensureInEditMode();
            editor.commit();
        }

        @Override
        public void commitAsync() {
            ensureInEditMode();
            editor.apply();
        }

        @Override
        public void writeInt(@NonNull String key, int value) {
            Preconditions.checkNotNull(key, "key must not be null");
            ensureInEditMode();
            editor.putInt(key, value);
        }

        @Override
        public void writeLong(@NonNull String key, long value) {
            Preconditions.checkNotNull(key, "key must not be null");
            ensureInEditMode();
            editor.putLong(key, value);
        }

        @Override
        public void writeFloat(@NonNull String key, float value) {
            Preconditions.checkNotNull(key, "key must not be null");
            ensureInEditMode();
            editor.putFloat(key, value);
        }

        @Override
        public void writeDouble(@NonNull String key, double value) {
            Preconditions.checkNotNull(key, "key must not be null");
            ensureInEditMode();
            editor.putString(key, String.valueOf(value));
        }

        @Override
        public void writeBoolean(@NonNull String key, boolean value) {
            Preconditions.checkNotNull(key, "key must not be null");
            ensureInEditMode();
            editor.putBoolean(key, value);
        }

        @Override
        public void writeString(@NonNull String key, String value) {
            Preconditions.checkNotNull(key, "key must not be null");
            ensureInEditMode();
            editor.putString(key, value);
        }

        @Override
        public void remove(@NonNull String key) {
            Preconditions.checkNotNull(key, "key must not be null");
            ensureInEditMode();
            editor.remove(key);
        }
    };

    private final SharedPreferences.OnSharedPreferenceChangeListener globalListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    final List<OnPropertyChangeListener> listenersCopy;
                    synchronized (this) {
                        listenersCopy = new ArrayList<>(listeners);
                    }

                    for (OnPropertyChangeListener listener : listenersCopy) {
                        listener.onPropertyChanged(SharedPreferencesStore.this, key);
                    }
                }
            };
}
