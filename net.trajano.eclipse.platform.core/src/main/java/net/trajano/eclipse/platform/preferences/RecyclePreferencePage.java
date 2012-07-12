package net.trajano.eclipse.platform.preferences;

import net.trajano.eclipse.platform.PlatformExtensionsPlugin;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
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

public final class RecyclePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(
				PreferenceConstants.P_CONFIRM_RESTART,
				PlatformExtensionsPlugin
						.getResourceString("PreferencePage.recycle.confirm.text"), //$NON-NLS-1$
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
