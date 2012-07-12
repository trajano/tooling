package net.trajano.eclipsepropertytester;

import java.util.regex.Pattern;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Platform;

/**
 * This is a {@link PropertyTester} that checks the static values in
 * {@link Platform}.
 * 
 * @author Archimedes Trajano <arch@trajano.net>
 * 
 */
public class EclipsePlatformTester extends PropertyTester {
	/**
	 * Do the test based on {@link Platform#getLocation()}.
	 */
	public static final String PROPERTY_LOCATION = "location";
	/**
	 * Do the test based on {@link Platform#getNL()}. The NLExt is not supported
	 * at this time as it will break support for Eclipse lower than 3.5.
	 */
	public static final String PROPERTY_NL = "nl";
	/**
	 * Do the test based on {@link Platform#getOS()}.
	 */
	public static final String PROPERTY_OS = "os";
	/**
	 * Do the test based on {@link Platform#getOSArch()}.
	 */
	public static final String PROPERTY_OS_ARCH = "os.arch";
	/**
	 * Do the test based on {@link Platform#getWS()}.
	 */
	public static final String PROPERTY_WS = "ws";

	/**
	 * This checks whether the given operating system property matches the value
	 * given. This also supports regular expression matching if the argument
	 * specifies "re"
	 * 
	 * @param property
	 *            this is one of the PROPERTY constants. If it is not valid the
	 *            function will return false.
	 * @param receiver
	 *            unused
	 * @param args
	 *            u
	 * @param expectedValue
	 *            expected value.
	 */
	public boolean test(final Object receiver, final String property,
			final Object[] args, final Object expectedValue) {
		if (args.length == 0) {
			if (PROPERTY_OS_ARCH.equals(property)) {
				return Platform.getOSArch().equals(expectedValue);
			} else if (PROPERTY_OS.equals(property)) {
				return Platform.getOS().equals(expectedValue);
			} else if (PROPERTY_NL.equals(property)) {
				return Platform.getNL().equals(expectedValue);
			} else if (PROPERTY_WS.equals(property)) {
				return Platform.getWS().equals(expectedValue);
			}
			return false;

		} else if ("re".equals(args[0])) {
			Pattern p = Pattern.compile(expectedValue.toString());
			if (PROPERTY_OS_ARCH.equals(property)) {
				return p.matcher(Platform.getOSArch()).find();
			} else if (PROPERTY_OS.equals(property)) {
				return p.matcher(Platform.getOS()).find();
			} else if (PROPERTY_NL.equals(property)) {
				return p.matcher(Platform.getNL()).find();
			} else if (PROPERTY_WS.equals(property)) {
				return p.matcher(Platform.getWS()).find();
			}
			return false;
		} else {
			return false;
		}
	}
}
