package com.mobsome.properties;

import com.mobsome.properties.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Tests of {@link Property}
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PropertyTest {
    private SharedProperties sharedProperties;

    @Before
    public void setup() throws Exception {
        sharedProperties = new SharedProperties(new SharedPreferencesStore(RuntimeEnvironment.application));
    }

    @Test
    public void testSharedIntProperty() throws Exception {
        assertEquals(999, sharedProperties.intProperty.get().intValue());
        sharedProperties.intProperty.set(5);
        assertEquals(5, sharedProperties.intProperty.get().intValue());
    }

    private class SharedProperties extends PropertiesGroup {
        private Properties.IntProperty intProperty;

        public SharedProperties(PropertyStore store) {
            super(store);
            intProperty = createProperty("int_prop", 999);
        }
    }
}
