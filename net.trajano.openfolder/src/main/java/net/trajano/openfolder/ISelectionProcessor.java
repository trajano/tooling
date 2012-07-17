package net.trajano.openfolder;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * This processes a specific selection in order to retrieve the actual path
 * element. This interface is meant to be implemented by extension points.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: ISelectionProcessor.java 138 2006-02-17 04:38:56Z trajano $
 */
public interface ISelectionProcessor {
	/**
	 * Extracts the IPath from the object. This will return <code>null</code> if
	 * it cannot be extracted.
	 * 
	 * @param o
	 *            object to get the IPath information from.
	 * @return the IPath associated with the object.
	 */
	IPath getPath(Object o);
}
