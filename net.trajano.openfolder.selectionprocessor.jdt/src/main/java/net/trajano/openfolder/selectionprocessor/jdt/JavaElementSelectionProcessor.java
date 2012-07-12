package net.trajano.openfolder.selectionprocessor.jdt;

import net.trajano.openfolder.ISelectionProcessor;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;

/**
 * <p>
 * This tries to get the path information from an
 * {@link org.eclipse.jdt.core.IJavaElement} object.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: JavaElementSelectionProcessor.java,v 1.1 2006/02/17 04:38:56
 *          trajano Exp $
 */
public final class JavaElementSelectionProcessor implements ISelectionProcessor {
    /**
     * {@inheritDoc}
     */
    public IPath getPath(final Object o) {
        if (o instanceof IJavaElement) {
            return ((IJavaElement) o).getPath();
        }
        return null;
    }

}
