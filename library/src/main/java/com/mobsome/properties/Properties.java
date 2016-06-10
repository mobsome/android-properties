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
 * Basic properties types
 */
public class Properties {

    /**
     * Property that holds integer value
     */
    public static class IntProperty extends Property<Integer> {
        private static final String TAG = IntProperty.class.getSimpleName();

        public IntProperty(@NonNull String name, int defaultValue,
                           @NonNull PropertyStore store) {
            super(name, defaultValue, store);
        }

        @Override
        protected Integer readValue(@NonNull PropertyReader reader) {
            Preconditions.checkNotNull(reader, "reader must not be null");
            try {
                return reader.getInt(name, defaultValue);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                return defaultValue;
            }
        }

        @Override
        protected void writeValue(@NonNull PropertyWriter writer, Integer value) {
            Preconditions.checkNotNull(writer, "writer must not be null");
            try {
                writer.writeInt(name, value);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }
        }
    }

    /**
     * Property that holds long value
     */
    public static class LongProperty extends Property<Long> {
        private static final String TAG = LongProperty.class.getSimpleName();

        public LongProperty(@NonNull String name, long defaultValue,
                            @NonNull PropertyStore store) {
            super(name, defaultValue, store);
        }

        @Override
        protected Long readValue(@NonNull PropertyReader reader) {
            Preconditions.checkNotNull(reader, "reader must not be null");
            try {
                return reader.getLong(name, defaultValue);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                return defaultValue;
            }
        }

        @Override
        protected void writeValue(@NonNull PropertyWriter writer, Long value) {
            Preconditions.checkNotNull(writer, "writer must not be null");
            try {
                writer.writeLong(name, value);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }
        }
    }

    /**
     * Property that holds float value
     */
    public static class FloatProperty extends Property<Float> {
        private static final String TAG = FloatProperty.class.getSimpleName();

        public FloatProperty(@NonNull String name, float defaultValue,
                             @NonNull PropertyStore store) {
            super(name, defaultValue, store);
        }

        @Override
        protected Float readValue(@NonNull PropertyReader reader) {
            Preconditions.checkNotNull(reader, "reader must not be null");
            try {
                return reader.getFloat(name, defaultValue);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                return defaultValue;
            }
        }

        @Override
        protected void writeValue(@NonNull PropertyWriter writer, Float value) {
            Preconditions.checkNotNull(writer, "writer must not be null");
            try {
                writer.writeFloat(name, value);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }
        }
    }

    /**
     * Property that holds double value
     */
    public static class DoubleProperty extends Property<Double> {
        private static final String TAG = DoubleProperty.class.getSimpleName();

        public DoubleProperty(@NonNull String name, double defaultValue,
                              @NonNull PropertyStore store) {
            super(name, defaultValue, store);
        }

        @Override
        protected Double readValue(@NonNull PropertyReader reader) {
            Preconditions.checkNotNull(reader, "reader must not be null");
            try {
                return reader.getDouble(name, defaultValue);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                return defaultValue;
            }
        }

        @Override
        protected void writeValue(@NonNull PropertyWriter writer, Double value) {
            Preconditions.checkNotNull(writer, "writer must not be null");
            try {
                writer.writeDouble(name, value);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }
        }
    }

    /**
     * Property that holds boolean value
     */
    public static class BooleanProperty extends Property<Boolean> {
        private static final String TAG = BooleanProperty.class.getSimpleName();

        public BooleanProperty(@NonNull String name, boolean defaultValue,
                               @NonNull PropertyStore store) {
            super(name, defaultValue, store);
        }

        @Override
        protected Boolean readValue(@NonNull PropertyReader reader) {
            Preconditions.checkNotNull(reader, "reader must not be null");
            try {
                return reader.getBoolean(name, defaultValue);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                return defaultValue;
            }
        }

        @Override
        protected void writeValue(@NonNull PropertyWriter writer, Boolean value) {
            Preconditions.checkNotNull(writer, "writer must not be null");
            try {
                writer.writeBoolean(name, value);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }
        }
    }

    /**
     * Property that holds string value
     */
    public static class StringProperty extends Property<String> {
        private static final String TAG = StringProperty.class.getSimpleName();

        public StringProperty(@NonNull String name, String defaultValue,
                              @NonNull PropertyStore store) {
            super(name, defaultValue, store);
        }

        @Override
        protected String readValue(@NonNull PropertyReader reader) {
            Preconditions.checkNotNull(reader, "reader must not be null");
            try {
                return reader.getString(name, defaultValue);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                return defaultValue;
            }
        }

        @Override
        protected void writeValue(@NonNull PropertyWriter writer, String value) {
            Preconditions.checkNotNull(writer, "writer must not be null");
            try {
                writer.writeString(name, value);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }
        }
    }

    /**
     * Property that holds enum value
     */
    public static class EnumProperty<E extends Enum<E>> extends Property<E> {
        private static final String TAG = EnumProperty.class.getSimpleName();
        private final Class<E> enumClass;

        public EnumProperty(@NonNull String name, E defaultValue,
                            @NonNull PropertyStore store) {
            super(name, defaultValue, store);
            enumClass = defaultValue.getDeclaringClass();
        }

        @Override
        protected E readValue(@NonNull PropertyReader reader) {
            Preconditions.checkNotNull(reader, "reader must not be null");
            final String value;
            try {
                value = reader.getString(name, defaultValue != null ? defaultValue.name() : null);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to read property '" + name + "', " + e.getMessage());
                return defaultValue;
            }
            return value != null ? E.valueOf(enumClass, value) : null;
        }

        @Override
        protected void writeValue(@NonNull PropertyWriter writer, E value) {
            Preconditions.checkNotNull(writer, "writer must not be null");
            try {
                writer.writeString(name, value != null ? value.name() : null);
            } catch (PropertyAccessException e) {
                Log.e(TAG, "Failed to store property '" + name + "', " + e.getMessage());
            }
        }
    }
}
