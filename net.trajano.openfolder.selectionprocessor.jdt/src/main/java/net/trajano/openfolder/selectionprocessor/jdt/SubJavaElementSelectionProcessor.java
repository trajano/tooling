package net.trajano.openfolder.selectionprocessor.jdt;

import net.trajano.openfolder.ISelectionProcessor;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;

/**
 * <p>
 * This tries to get a java element from an
 * {@link org.eclipse.ui.model.IWorkbenchAdapter} which is used to map against a
 * CVS tree.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: SubJavaElementSelectionProcessor.java,v 1.1 2006/02/17 04:38:56
 *          trajano Exp $
 */
public final class SubJavaElementSelectionProcessor implements
		ISelectionProcessor {

	/**
	 * {@inheritDoc}
	 */
	public IPath getPath(final Object o) {
		IWorkbenchAdapter workbenchAdapter = (IWorkbenchAdapter) ((IAdaptable) o)
				.getAdapter(IWorkbenchAdapter.class);

		if (workbenchAdapter != null
				&& !(workbenchAdapter instanceof IDeferredWorkbenchAdapter)) {

			Object[] children = workbenchAdapter.getChildren(null);
			if (children != null
					&& children.length > 0
					&& workbenchAdapter.getChildren(null)[0] instanceof IJavaElement) {
				IJavaElement javaElement = (IJavaElement) workbenchAdapter
						.getChildren(null)[0];
				return javaElement.getPath();
			}
		}
		return null;
	}
}
