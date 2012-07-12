package net.trajano.eclipse.platform;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public final class PlatformExtensionsPlugin extends AbstractUIPlugin implements
		IStartup {
	/**
	 * The shared instance.
	 */
	private static PlatformExtensionsPlugin plugin;

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance.
	 */
	public static PlatformExtensionsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 * 
	 * @param key
	 *            resource key.
	 * @return string from bundle.
	 */
	public static String getResourceString(final String key) {
		final ResourceBundle bundle = PlatformExtensionsPlugin.getDefault()
				.getResourceBundle();
		try {
			return bundle != null ? bundle.getString(key) : key;
		} catch (final MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found. Passes through a message formatter with only one arg.
	 * 
	 * @param key
	 *            resource key
	 * @param arg
	 *            format argument
	 * @return formatted string
	 */
	public static String getResourceString(final String key, final Object arg) {
		return MessageFormat.format(getResourceString(key),
				new Object[] { arg });
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found. Passes through a message formatter
	 * 
	 * @param key
	 *            resource key
	 * @param args
	 *            format arguments
	 * @return formatted string
	 */
	public static String getResourceString(final String key, final Object[] args) {
		return MessageFormat.format(getResourceString(key), args);
	}

	/**
	 * Resource bundle.
	 */
	private final ResourceBundle resourceBundle;

	/**
	 * The constructor.
	 */
	public PlatformExtensionsPlugin() {
		plugin = this;
		resourceBundle = ResourceBundle
				.getBundle("net.trajano.eclipse.platform.internal.PlatformExtensionsPluginResources"); //$NON-NLS-1$
	}

	/**
	 * This is needed for now until I switch the Copy to Clipboard actions to
	 * use the new Command mechanism.
	 * 
	 * {@inheritDoc}
	 */
	public void earlyStartup() {
	}

	/**
	 * Returns the plugin's resource bundle.
	 * 
	 * @return the resource bundle.
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop(final BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}
}
