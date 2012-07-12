package net.trajano.eclipsepropertytester;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * This provides a simple uncached definition registry to allow clients to
 * lookup an expression by definition ID. Eclipse provides a cached definition
 * registry, but it is not exposed. This is primarily used for unit testing
 * definitions.
 * 
 * @author Archimedes Trajano <arch@trajano.net>
 * 
 */
public class DefinitionRegistry {
	/**
	 * Gets an expression based on the ID. If not found it will return
	 * <code>null</code>.
	 * 
	 * @param id
	 *            ID of the definition
	 * @return an expression.
	 * @throws CoreException
	 *             thrown when there is a problem converting the definition to
	 *             an {@link Expression}.
	 */
	public Expression getExpression(final String id) throws CoreException {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] ces = registry
				.getConfigurationElementsFor(
						"org.eclipse.core.expressions", "definitions"); //$NON-NLS-1$ //$NON-NLS-2$

		for (int i = 0; i < ces.length; i++) {
			final String cid = ces[i].getAttribute("id"); //$NON-NLS-1$
			if (cid != null && cid.equals(id)) {
				return ExpressionConverter.getDefault().perform(
						ces[i].getChildren()[0]);
			}
		}
		return null;
	}
}
