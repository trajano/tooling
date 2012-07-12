package net.trajano.openfolder.selectionprocessor;

import net.trajano.openfolder.ISelectionProcessor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * This tries to convert an {@link org.eclipse.core.runtime.IAdaptable} object
 * and convert it to a {@link org.eclipse.core.resources.IResource} object which
 * normally has path information.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: ResourceSelectionProcessor.java,v 1.1 2006/02/17 04:38:56
 *          trajano Exp $
 */
public final class ResourceSelectionProcessor implements ISelectionProcessor {
    /**
     * {@inheritDoc}
     */
    public IPath getPath(final Object o) {
        IResource resource = (IResource) ((IAdaptable) o).getAdapter(IResource.class);
        if (resource != null) {
            return resource.getLocation();
        }
        return null;
    }

}
