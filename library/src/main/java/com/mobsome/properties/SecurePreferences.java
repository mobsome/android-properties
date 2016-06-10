/*
 * Copyright (C) 2016 Mobsome, Inc.
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
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;


/**
 * Encrypted implementation of {@link android.content.SharedPreferences}
 */
class SecurePreferences {
    private final SharedPreferences sharedPreferences;
    private final ICipher cipher;

    /**
     * Constructor.
     *
     * @param context the caller's context
     * @param cipher  cipher for preferences encryption
     */
    public SecurePreferences(Context context, ICipher cipher) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.cipher = cipher;
    }

    /**
     * Constructor.
     *
     * @param context  the caller's context
     * @param cipher   cipher for preferences encryption
     * @param fileName shared preferences file
     */
    public SecurePreferences(Context context, ICipher cipher, String fileName) {
        this.sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        this.cipher = cipher;
    }


    public String getString(String key, String defaultValue) throws PropertyEncryptionException {
        final String encryptedValue = sharedPreferences.getString(
                key, null);
        return (encryptedValue != null) ? decrypt(encryptedValue) : defaultValue;
    }

    public Set<String> getStringSet(String key, Set<String> defaultValues) throws PropertyEncryptionException {
        final Set<String> encryptedSet = sharedPreferences.getStringSet(
                key, null);
        if (encryptedSet == null) {
            return defaultValues;
        }
        final Set<String> decryptedSet = new HashSet<>(encryptedSet.size());
        for (String encryptedValue : encryptedSet) {
            decryptedSet.add(decrypt(encryptedValue));
        }
        return decryptedSet;
    }

    public int getInt(String key, int defaultValue) throws PropertyEncryptionException {
        final String encryptedValue = sharedPreferences.getString(key, null);
        if (encryptedValue == null) {
            return defaultValue;
        }

        return Integer.parseInt(decrypt(encryptedValue));
    }

    public long getLong(String key, long defaultValue) throws PropertyEncryptionException {
        final String encryptedValue = sharedPreferences.getString(
                key, null);
        if (encryptedValue == null) {
            return defaultValue;
        }

        return Long.parseLong(decrypt(encryptedValue));
    }

    public float getFloat(String key, float defaultValue) throws PropertyEncryptionException {
        final String encryptedValue = sharedPreferences.getString(key, null);
        if (encryptedValue == null) {
            return defaultValue;
        }

        return Float.parseFloat(decrypt(encryptedValue));
    }

    public boolean getBoolean(String key, boolean defaultValue) throws PropertyEncryptionException {
        final String encryptedValue = sharedPreferences.getString(key, null);
        if (encryptedValue == null) {
            return defaultValue;
        }

        return Boolean.parseBoolean(decrypt(encryptedValue));
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public Editor edit() {
        return new Editor();
    }

    private String encrypt(String cleartext) throws PropertyEncryptionException {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }

        try {
            return cipher.encrypt(cleartext);
        } catch (Exception e) {
            throw new PropertyEncryptionException(e);
        }
    }

    private String decrypt(String ciphertext) throws PropertyEncryptionException {
        if (TextUtils.isEmpty(ciphertext)) {
            return ciphertext;
        }

        try {
            return cipher.decrypt(ciphertext);
        } catch (Exception e) {
            throw new PropertyEncryptionException(e);
        }
    }

    /**
     * Wrapper for Android's {@link android.content.SharedPreferences.Editor}.
     * <p/>
     * Used for modifying values in a {@link SecurePreferences} object. All
     * changes you make in an editor are batched, and not copied back to the
     * original {@link SecurePreferences} until you call {@link #commit()} or
     * {@link #apply()}.
     */
    public class Editor {
        private SharedPreferences.Editor editor;

        /**
         * Constructor.
         */
        private Editor() {
            editor = sharedPreferences.edit();
        }

        public SecurePreferences.Editor putString(String key, String value)
                throws PropertyEncryptionException {
            editor.putString(key, encrypt(value));
            return this;
        }

        public SecurePreferences.Editor putStringSet(String key, Set<String> values)
                throws PropertyEncryptionException {
            final Set<String> encryptedValues = new HashSet<>(values.size());
            for (String value : values) {
                encryptedValues.add(encrypt(value));
            }
            editor.putStringSet(key, encryptedValues);
            return this;
        }

        public SecurePreferences.Editor putInt(String key, int value)
                throws PropertyEncryptionException {
            editor.putString(key, encrypt(Integer.toString(value)));
            return this;
        }

        public SecurePreferences.Editor putLong(String key, long value)
                throws PropertyEncryptionException {
            editor.putString(key, encrypt(Long.toString(value)));
            return this;
        }

        public SecurePreferences.Editor putFloat(String key, float value)
                throws PropertyEncryptionException {
            editor.putString(key, encrypt(Float.toString(value)));
            return this;
        }

        public SecurePreferences.Editor putBoolean(String key, boolean value)
                throws PropertyEncryptionException {
            editor.putString(key, encrypt(Boolean.toString(value)));
            return this;
        }

        public SecurePreferences.Editor remove(String key) {
            editor.remove(key);
            return this;
        }

        public SecurePreferences.Editor clear() {
            editor.clear();
            return this;
        }

        public boolean commit() {
            return editor.commit();
        }

        public void apply() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                editor.apply();
            } else {
                commit();
            }
        }
    }

    public void registerOnSharedPreferenceChangeListener(
            final SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(
            final SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
