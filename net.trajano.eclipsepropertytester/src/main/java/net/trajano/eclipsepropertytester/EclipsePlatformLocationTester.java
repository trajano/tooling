package net.trajano.eclipsepropertytester;

import java.io.File;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;

/**
 * This is a {@link PropertyTester} that checks the static values in
 * {@link Platform} specific for locations.
 * 
 * @author Archimedes Trajano <arch@trajano.net>
 * 
 */
public class EclipsePlatformLocationTester extends PropertyTester {
	/**
	 * Do the test based on {@link Platform#getUserLocation()}.
	 */
	public static final String PROPERTY_USER = "user.location";
	/**
	 * Do the test based on {@link Platform#getInstallLocation()}.
	 */
	public static final String PROPERTY_INSTALL = "install.location";
	/**
	 * Do the test based on {@link Platform#getInstanceLocation()}.
	 */
	public static final String PROPERTY_INSTANCE = "instance.location";

	/**
	 * Converts a {@link Location} to an {@link IPath} object. It assumes that
	 * the {@link Location} object points to a local file system file.
	 * 
	 * @param location
	 *            location object
	 * @return the IPath equivalent.
	 */
	private IPath locationToIPath(Location location) {
		File file = new File(location.getURL().getFile());
		return new Path(file.toString());
	}

	/**
	 * This checks whether the given operating system property matches the value
	 * given. By default it will convert to an IPath and do the evaluations from
	 * there.
	 * 
	 * @param property
	 *            this is one of the PROPERTY constants. If it is not valid the
	 *            function will return false.
	 * @param receiver
	 *            unused
	 * @param args
	 *            unused for now, eventually will have "repath", "reurl" which
	 *            will pattern match against the path or URL.
	 * @param expectedValue
	 *            expected value.
	 */
	public boolean test(final Object receiver, final String property,
			final Object[] args, final Object expectedValue) {
		final IPath finalExpectedValue;
		if (expectedValue instanceof Location) {
			finalExpectedValue = locationToIPath((Location) expectedValue);
		} else if (expectedValue instanceof IPath) {
			finalExpectedValue = (IPath) expectedValue;
		} else {
			finalExpectedValue = new Path(expectedValue.toString());
		}

		if (PROPERTY_INSTANCE.equals(property)) {
			return locationToIPath(Platform.getInstanceLocation()).equals(
					finalExpectedValue);
		} else if (PROPERTY_USER.equals(property)) {
			return locationToIPath(Platform.getUserLocation()).equals(
					finalExpectedValue);
		} else if (PROPERTY_INSTALL.equals(property)) {
			return locationToIPath(Platform.getInstallLocation()).equals(
					finalExpectedValue);
		} else {
			return false;
		}
	}
}
