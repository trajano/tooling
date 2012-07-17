package net.trajano.openfolder.internal;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * This utility class is used to get resource strings for the plugin.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: Messages.java 254 2007-07-17 06:41:22Z trajano $
 */
public final class Messages {
	/**
	 * Resource bundle.
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle
			.getBundle("net.trajano.openfolder.internal.FolderPluginResources"); //$NON-NLS-1$

	/**
	 * Creates an error status object.
	 * 
	 * @param key
	 *            message key
	 * @param args
	 *            arguments for the message
	 * @param e
	 *            exception related to the error
	 * @return the status object.
	 */
	public static IStatus error(final String key, final Object[] args,
			final Exception e) {
		return new Status(IStatus.ERROR, OpenInActivator.getDefault()
				.getBundle().getSymbolicName(), IStatus.ERROR, message(key,
				args), e);
	}

	/**
	 * Returns a message from the bundle.
	 * 
	 * @param key
	 *            resource bundle key.
	 * @return localized message.
	 */
	public static String message(final String key) {
		return message(key, new Object[] {});
	}

	/**
	 * Returns a message from the bundle.
	 * 
	 * @param key
	 *            resource bundle key.
	 * @param args
	 *            message arguments.
	 * @return localized message.
	 */
	public static String message(final String key, final Object[] args) {
		return MessageFormat.format(BUNDLE.getString(key), args);
	}

	/**
	 * Popups up a error message dialog.
	 * 
	 * @param titleKey
	 *            resource bundle key for the title.
	 * @param messageKey
	 *            resource bundle key for the message
	 * @param args
	 *            message arguments.
	 */
	public static void openErrorDialog(final String titleKey,
			final String messageKey, final Object[] args) {
		MessageDialog.openError(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(),
				message(titleKey, new Object[] {}), message(messageKey, args));
	}

	/**
	 * Prevent instantiaton of utility class.
	 */
	private Messages() {
	}
}
