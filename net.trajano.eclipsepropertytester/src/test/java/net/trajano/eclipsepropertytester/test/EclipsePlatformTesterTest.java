package net.trajano.eclipsepropertytester.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import net.trajano.eclipsepropertytester.DefinitionRegistry;
import net.trajano.eclipsepropertytester.EclipsePlatformLocationTester;
import net.trajano.eclipsepropertytester.EclipsePlatformTester;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

public class EclipsePlatformTesterTest extends TestCase {

	public void testDefinition() throws Exception {

		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
				.getExtensionPoint("org.eclipse.core.expressions.definitions");

		assertNotNull(extensionPoint);
	}

	public void testInvalid() {
		assertFalse(new EclipsePlatformTester().test(null, "invalid property",
				new Object[] {}, "foo"));
	}

	/**
	 * Test that it can be a Mac OSX.
	 * 
	 * @throws Exception
	 */
	public void testMacOSX() throws Exception {
		final Expression expression = new DefinitionRegistry()
				.getExpression("net.trajano.eclipsepropertytester.isMacOSX");
		assertNotNull(expression);
		final EvaluationResult evaluate = expression
				.evaluate(new EvaluationContext(null, new ArrayList()));
		assertTrue(EvaluationResult.TRUE.equals(evaluate)
				|| EvaluationResult.FALSE.equals(evaluate));
	}

	/**
	 * Test that it cannot be both Mac and Windows at the same time. Basically
	 * if either Mac or Win32 is true then the other one must be false.
	 * 
	 * @throws Exception
	 */
	public void testMacOSXNotWin32() throws Exception {
		final DefinitionRegistry registry = new DefinitionRegistry();
		final Expression macexpression = registry
				.getExpression("net.trajano.eclipsepropertytester.isMacOSX");
		final Expression winexpression = registry
				.getExpression("net.trajano.eclipsepropertytester.isWin32");
		final EvaluationResult macevaluate = macexpression
				.evaluate(new EvaluationContext(null, new ArrayList()));
		final EvaluationResult winevaluate = winexpression
				.evaluate(new EvaluationContext(null, new ArrayList()));
		assertFalse(EvaluationResult.TRUE.equals(macevaluate)
				&& EvaluationResult.TRUE.equals(winevaluate));

	}

	public void testNL() {
		assertTrue(new EclipsePlatformTester().test(null,
				EclipsePlatformTester.PROPERTY_NL, new Object[] {}, Platform
						.getNL()));
	}

	/**
	 * Tests the regular expression capability.
	 */
	public void testNLRegEx() {
		assertTrue(new EclipsePlatformTester().test(null,
				EclipsePlatformTester.PROPERTY_NL, new Object[] { "re" }, "."));
	}

	public void testOS() {
		assertTrue(new EclipsePlatformTester().test(null,
				EclipsePlatformTester.PROPERTY_OS, new Object[] {}, Platform
						.getOS()));
	}

	public void testOSArch() {
		assertTrue(new EclipsePlatformTester().test(null,
				EclipsePlatformTester.PROPERTY_OS_ARCH, new Object[] {},
				Platform.getOSArch()));
	}

	/**
	 * This does a plugin lookup.
	 * 
	 * @throws Exception
	 */
	public void testPluginLookup() throws Exception {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
				.getExtensionPoint(
						"org.eclipse.core.expressions.propertyTesters");
		assertNotNull(extensionPoint);
		final IExtension extension = extensionPoint
				.getExtension("net.trajano.eclipsepropertytester.propertyTesters");
		assertNotNull(extension);
		assertEquals(2, extension.getConfigurationElements().length);
		assertEquals(EclipsePlatformLocationTester.class.getName(), extension
				.getConfigurationElements()[0].getAttribute("class"));
		assertEquals(EclipsePlatformTester.class.getName(), extension
				.getConfigurationElements()[1].getAttribute("class"));
	}

	/**
	 * Test that it can be a win32.
	 * 
	 * @throws Exception
	 */
	public void testWin32() throws Exception {
		final Expression expression = new DefinitionRegistry()
				.getExpression("net.trajano.eclipsepropertytester.isWin32");
		assertNotNull(expression);
		final EvaluationResult evaluate = expression
				.evaluate(new EvaluationContext(null, new ArrayList()));
		assertTrue(EvaluationResult.TRUE.equals(evaluate)
				|| EvaluationResult.FALSE.equals(evaluate));
	}

	public void testWs() {
		assertTrue(new EclipsePlatformTester().test(null,
				EclipsePlatformTester.PROPERTY_WS, new Object[] {}, Platform
						.getWS()));
	}
}
