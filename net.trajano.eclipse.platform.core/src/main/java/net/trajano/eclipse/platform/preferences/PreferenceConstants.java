package net.trajano.eclipse.platform.preferences;

/**
 * Constant definitions for plug-in preferences.
 */
public final class PreferenceConstants {
    /**
     * Preference for number of GC iterations.
     */
    public static final String P_NUMBER_OF_GARBAGE_COLLECTION = "numberOfGarbageCollectionPreference"; //$NON-NLS-1$

    /**
     * Preference for sleep time between GC.
     */
    public static final String P_SLEEP_TIME_BETWEEN_GARBAGE_COLLECTION = "sleepTimeBetweenGarbageCollectionPreference"; //$NON-NLS-1$

    /**
     * Preference to confirm restart.
     */
    public static final String P_CONFIRM_RESTART = "confirmRestartPreference"; //$NON-NLS-1$

    /**
     * Preference to confirm GC.
     */
    public static final String P_CONFIRM_GC = "confirmGarbagePreference"; //$NON-NLS-1$

    /**
     * Prevent instantiation of constants class.
     */
    private PreferenceConstants() {

    }
}
