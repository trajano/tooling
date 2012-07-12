package net.trajano.eclipse.platform.preferences;

import net.trajano.eclipse.platform.PlatformExtensionsPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public final class PreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * Default GC iterations.
     */
    private static final int DEFAULT_GC_ITERATIONS = 3;

    /**
     * Default amount in ms for GC to sleep.
     */
    private static final int DEFAULT_GC_SLEEP = 100;

    /**
     * {@inheritDoc}
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = PlatformExtensionsPlugin.getDefault().getPreferenceStore();
        store.setDefault(PreferenceConstants.P_NUMBER_OF_GARBAGE_COLLECTION, DEFAULT_GC_ITERATIONS);
        store.setDefault(PreferenceConstants.P_SLEEP_TIME_BETWEEN_GARBAGE_COLLECTION, DEFAULT_GC_SLEEP);
        store.setDefault(PreferenceConstants.P_CONFIRM_RESTART, true);
        store.setDefault(PreferenceConstants.P_CONFIRM_GC, true);
    }
}
