package net.trajano.eclipse.platform.preferences;

import net.trajano.eclipse.platform.PlatformExtensionsPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public final class GarbageCollectionPreferencePage extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Maximum number of GC iterations.
	 */
	private static final int MAX_GC_ITERATIONS = 99;

	/**
	 * Maximum GC sleep time.
	 */
	private static final int MAX_GC_SLEEP = 10000;

	/**
	 * Maximum GC sleep time field length.
	 */
	private static final int MAX_GC_SLEEP_FIELD_LENGTH = 5;

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		IntegerFieldEditor numberOfGarbageCollectionIterationsField = new IntegerFieldEditor(
				PreferenceConstants.P_NUMBER_OF_GARBAGE_COLLECTION,
				PlatformExtensionsPlugin
						.getResourceString("PreferencePage.garbagecollection.iteration.text"), //$NON-NLS-1$
				getFieldEditorParent());
		numberOfGarbageCollectionIterationsField.setEmptyStringAllowed(false);
		numberOfGarbageCollectionIterationsField.setValidRange(1,
				MAX_GC_ITERATIONS);
		numberOfGarbageCollectionIterationsField.setTextLimit(2);
		addField(numberOfGarbageCollectionIterationsField);

		IntegerFieldEditor sleepTimeBetweenGarbageCollectionField = new IntegerFieldEditor(
				PreferenceConstants.P_SLEEP_TIME_BETWEEN_GARBAGE_COLLECTION,
				PlatformExtensionsPlugin
						.getResourceString("PreferencePage.garbagecollection.sleep.text"), //$NON-NLS-1$
				getFieldEditorParent());
		sleepTimeBetweenGarbageCollectionField.setEmptyStringAllowed(true);
		sleepTimeBetweenGarbageCollectionField.setValidRange(0, MAX_GC_SLEEP);
		sleepTimeBetweenGarbageCollectionField
				.setTextLimit(MAX_GC_SLEEP_FIELD_LENGTH);
		addField(sleepTimeBetweenGarbageCollectionField);

		addField(new BooleanFieldEditor(
				PreferenceConstants.P_CONFIRM_GC,
				PlatformExtensionsPlugin
						.getResourceString("PreferencePage.garbagecollection.saveeditors.text"),
				getFieldEditorParent()));

	}

	/**
	 * {@inheritDoc}
	 */
	public void init(final IWorkbench workbench) {
		setPreferenceStore(PlatformExtensionsPlugin.getDefault()
				.getPreferenceStore());
	}
}
