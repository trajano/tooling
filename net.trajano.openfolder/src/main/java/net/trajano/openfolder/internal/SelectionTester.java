/**
 * 
 */
package net.trajano.openfolder.internal;

import org.eclipse.core.expressions.PropertyTester;

/**
 * @author ARCHIET
 * 
 */
public class SelectionTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 */
	public boolean test(final Object receiver, final String property,
			final Object[] args, final Object expectedValue) {
		// return OpenInActivator.getDefault().getSelectionProcessor().getPath(
		// receiver) != null;
		return true;
	}

}
