package net.trajano.openfolder.internal.preferences;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.trajano.openfolder.internal.OpenInActivator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values. It loads up the defaults
 * from a properties file and uses the OS as part of its lookup key. This allows
 * for OS dependent command lines.
 */
public final class PreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Resource bundle containing the default command lines.
	 */
	private static final String BUNDLE_NAME = "net.trajano.openfolder.internal.DefaultCommandLines"; //$NON-NLS-1$

	/**
	 * This is the prefix to file command prompt command line.
	 */
	private static final String CMD_PREFIX = "cmd"; //$NON-NLS-1$

	/**
	 * This is the prefix to file explorer command line.
	 */
	private static final String EXPLORER_PREFIX = "explorer"; //$NON-NLS-1$
	/**
	 * This is the prefix to file explorer command line.
	 */
	private static final String EXPLORER_WITH_FILE_PREFIX = "explorerWithFile"; //$NON-NLS-1$

	/**
	 * @inheritDoc
	 */
	public void initializeDefaultPreferences() {
		final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

		String fileExplorerCommandLine;
		try {
			fileExplorerCommandLine = bundle.getString(EXPLORER_PREFIX + '.'
					+ Platform.getOS());
		} catch (final MissingResourceException e) {
			fileExplorerCommandLine = bundle.getString(EXPLORER_PREFIX);
		}

		String fileExplorerWithFileCommandLine;
		try {
			fileExplorerWithFileCommandLine = bundle
					.getString(EXPLORER_WITH_FILE_PREFIX + '.'
							+ Platform.getOS());
		} catch (final MissingResourceException e) {
			fileExplorerWithFileCommandLine = bundle
					.getString(EXPLORER_WITH_FILE_PREFIX);
		}

		String commandPromptCommandLine;
		try {
			commandPromptCommandLine = bundle.getString(CMD_PREFIX + '.'
					+ Platform.getOS());
		} catch (final MissingResourceException e) {
			commandPromptCommandLine = bundle.getString(CMD_PREFIX);
		}

		final IPreferenceStore store = OpenInActivator.getDefault()
				.getPreferenceStore();
		store.setDefault(PreferenceConstants.P_EXPLORER_CMD,
				fileExplorerCommandLine);
		store.setDefault(PreferenceConstants.P_EXPLORER_WITH_FILE_CMD,
				fileExplorerWithFileCommandLine);
		store.setDefault(PreferenceConstants.P_COMMAND_CMD,
				commandPromptCommandLine);
	}
}
