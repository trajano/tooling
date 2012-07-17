/**
 * 
 */
package net.trajano.openfolder.internal;

import net.trajano.openfolder.CompositeSelectionProcessor;
import net.trajano.openfolder.ISelectionProcessor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

/**
 * @author ARCHIET
 * 
 */
public final class SelectionProcessorFactory {
	/**
	 * Attribute name "class" in "selectionProcessor" tag.
	 */
	private static final String CLASS = "class"; //$NON-NLS-1$	

	/**
	 * "selectionProcessor" tag name.
	 */
	private static final String SELECTION_PROCESSOR = "selectionProcessor"; //$NON-NLS-1$
	/**
	 * Selection processor extension point ID.
	 */
	private static final String SELECTION_PROCESSOR_EXTENSION_POINT_ID = "net.trajano.openfolder.selectionProcessor"; //$NON-NLS-1$

	/**
	 * This gets the selection processor which in this case is a composite
	 * selection processor that consists of all the extension point providers.
	 * 
	 * @return a selection processor.
	 */
	public static ISelectionProcessor getSelectionProcessor() {
		final CompositeSelectionProcessor processors = new CompositeSelectionProcessor();
		final IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(SELECTION_PROCESSOR_EXTENSION_POINT_ID)
				.getExtensions();
		for (final IExtension extension : extensions) {
			final IConfigurationElement[] configurationElements = extension
					.getConfigurationElements();
			try {
				for (final IConfigurationElement configurationElement : configurationElements) {
					if (configurationElement.getName().equals(
							SELECTION_PROCESSOR)) {
						processors
								.addProcessor((ISelectionProcessor) configurationElement
										.createExecutableExtension(CLASS));
					}
				}
			} catch (final CoreException e) {
				OpenInActivator
						.getDefault()
						.getLog()
						.log(Messages
								.error("selectionprocessor.error.message", new Object[] {}, e)); //$NON-NLS-1$
			}
		}
		return processors;
	}

	/**
	 * Prevent instantiation of factory class.
	 */
	private SelectionProcessorFactory() {

	}

}
