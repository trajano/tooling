package net.trajano.eclipsepropertytester.test;

import java.io.File;

import junit.framework.TestCase;
import net.trajano.eclipsepropertytester.EclipsePlatformLocationTester;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

public class EclipsePlatformLocationTesterTest extends TestCase {
	public void testInvalid() {
		assertFalse(new EclipsePlatformLocationTester().test(null,
				"invalid property", null, "foo"));
	}

	public void testInstanceLocation() {
		assertTrue(new EclipsePlatformLocationTester().test(null,
				EclipsePlatformLocationTester.PROPERTY_INSTANCE, null, Platform
						.getInstanceLocation()));
	}

	public void testInstanceLocationEqualsLocation() throws Exception {
		assertEquals(Platform.getLocation(), new Path(new File(Platform
				.getInstanceLocation().getURL().toURI()).toString()));
	}

	/**
	 * This tests using the {@link Platform#getLocation} method which will
	 * return an IPath to the same thing as
	 * {@link Platform#getInstanceLocation()}.
	 */
	public void testLocationOSString() {
		assertTrue(new EclipsePlatformLocationTester().test(null,
				EclipsePlatformLocationTester.PROPERTY_INSTANCE, null, Platform
						.getLocation().toOSString()));
	}

	/**
	 * This tests using the {@link Platform#getLocation} method which will
	 * return an IPath to the same thing as
	 * {@link Platform#getInstanceLocation()}.
	 */
	public void testLocationPath() {
		assertTrue(new EclipsePlatformLocationTester().test(null,
				EclipsePlatformLocationTester.PROPERTY_INSTANCE, null, Platform
						.getLocation()));
	}

	/**
	 * This tests using the {@link Platform#getLocation} method which will
	 * return an IPath to the same thing as
	 * {@link Platform#getInstanceLocation()}.
	 */
	public void testLocationPortableString() {
		assertTrue(new EclipsePlatformLocationTester().test(null,
				EclipsePlatformLocationTester.PROPERTY_INSTANCE, null, Platform
						.getLocation().toPortableString()));
	}

}
