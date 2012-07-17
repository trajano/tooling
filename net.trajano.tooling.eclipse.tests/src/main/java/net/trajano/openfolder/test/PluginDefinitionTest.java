package net.trajano.openfolder.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import net.trajano.eclipsepropertytester.DefinitionRegistry;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

public class PluginDefinitionTest extends TestCase {

	public void testDefinition() throws Exception {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
				.getExtensionPoint("org.eclipse.core.expressions.definitions");
		// net.trajano.eclipsepropertytester.definitions
		assertNotNull(extensionPoint);
		// new Property(IAdaptable.class,
		// "net.trajano.eclipsePropertyTester","os");
		final Expression expression = new DefinitionRegistry()
				.getExpression("net.trajano.openfolder.canOpen");
		assertNotNull(expression);
		expression.evaluate(new EvaluationContext(null, new ArrayList()));
	}
}
