package net.trajano.openfolder.internal;

import net.trajano.openfolder.CompositeSelectionProcessor;
import net.trajano.openfolder.ISelectionProcessor;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plug-in class to be used in the desktop.
 */
public class OpenInActivator extends AbstractUIPlugin {

	/**
	 * Attribute name "class" in "selectionProcessor" tag.
	 */
	private static final String CLASS = "class"; //$NON-NLS-1$	

	/**
	 * The shared instance.
	 */
	private static OpenInActivator plugin;

	/**
	 * "selectionProcessor" tag name.
	 */
	private static final String SELECTION_PROCESSOR = "selectionProcessor"; //$NON-NLS-1$

	/**
	 * Selection processor extension point ID.
	 */
	private static final String SELECTION_PROCESSOR_EXTENSION_POINT_ID = "net.trajano.openfolder.selectionProcessor"; //$NON-NLS-1$

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance.
	 */
	public static OpenInActivator getDefault() {
		return plugin;
	}

	private ISelectionProcessor selectionProcessor;

	/**
	 * The constructor. Sets the default reference to the newly constructed
	 * object.
	 */
	public OpenInActivator() {
		plugin = this;
	}

	/**
	 * This gets the selection processor which in this case is a composite
	 * selection processor that consists of all the extension point providers.
	 * 
	 * @return a selection processor.
	 */
	public ISelectionProcessor getSelectionProcessor() {
		return selectionProcessor;
	}

	/**
	 * This will load up all the selection processors and cache them into a
	 * field in the activator.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		final CompositeSelectionProcessor processors = new CompositeSelectionProcessor();
		final IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(SELECTION_PROCESSOR_EXTENSION_POINT_ID)
				.getExtensions();
		for (final IExtension extension : extensions) {
			final IConfigurationElement[] configurationElements = extension
					.getConfigurationElements();
			for (final IConfigurationElement configurationElement : configurationElements) {
				if (configurationElement.getName().equals(SELECTION_PROCESSOR)) {
					processors
							.addProcessor((ISelectionProcessor) configurationElement
									.createExecutableExtension(CLASS));
				}
			}
		}
		selectionProcessor = processors;
	}

	/**
	 * This method is called when the plug-in is stopped. It sets the default
	 * reference to null. {@inheritDoc}
	 */
	@Override
	public final void stop(final BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

}
