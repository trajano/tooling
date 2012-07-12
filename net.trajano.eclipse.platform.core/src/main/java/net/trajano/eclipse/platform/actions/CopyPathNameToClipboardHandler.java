package net.trajano.eclipse.platform.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

/**
 * <p>
 * Copies file name to the clipboard.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: CopyFileNameToClipboardHandler.java 154 2006-02-19 09:00:39
 *          +0000 (Sun, 19 Feb 2006) trajano $
 */
public final class CopyPathNameToClipboardHandler extends
		AbstractCopyToClipboardHandler {
	/**
	 * Extracts the text data that is supposed to be put into the clipboard for
	 * the selected {@link IAdaptable} object.
	 * 
	 * @param adaptable
	 *            adaptable object to process.
	 * @return the text data for the adaptable object
	 */
	protected String getAdaptableTextData(final IAdaptable adaptable) {
		return ((IResource) adaptable.getAdapter(IResource.class))
				.getLocation().toOSString();
	}
}
