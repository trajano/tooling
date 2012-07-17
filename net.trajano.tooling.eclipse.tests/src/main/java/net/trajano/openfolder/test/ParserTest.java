/**
 * 
 */
package net.trajano.openfolder.test;

import java.io.File;

import junit.framework.TestCase;

import net.trajano.openfolder.ExecUtil;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * @author ARCHIET
 * 
 */
public class ParserTest extends TestCase {
	private IPath container;
	/**
	 * path we are going to be working with;
	 */
	private IPath somePath;

	/**
	 * {@inheritDoc}
	 */
	protected void setUp() throws Exception {
		final File tempFile = File.createTempFile("fo%do", ".bar");
		tempFile.deleteOnExit();
		somePath = new Path(tempFile.getAbsolutePath());
		container = somePath.removeLastSegments(1);
	}

	/**
	 * Tests {@link ExecUtil#getContainingFolder(IPath)}.
	 * 
	 * @throws Exception
	 */
	public void testGetContainingDirectory() throws Exception {
		assertEquals(somePath.removeLastSegments(1), ExecUtil
				.getContainingFolder(somePath));
		assertEquals(somePath.removeLastSegments(1), ExecUtil
				.getContainingFolder(somePath.removeLastSegments(1)));
	}

	/**
	 * Perform the new parsing.
	 * 
	 * @throws Exception
	 */
	public void testNewParser1() throws Exception {
		final String parseThis = "foo %p";
		assertEquals("foo " + somePath.toOSString(), ExecUtil.parse(parseThis,
				somePath, true));
	}

	/**
	 * Perform the new parsing.
	 * 
	 * @throws Exception
	 */
	public void testNewParser2() throws Exception {
		final String parseThis = "foo %d";
		assertEquals("foo " + container.toOSString(), ExecUtil.parse(parseThis,
				somePath, true));
	}

	/**
	 * Perform the new parsing with "%%"
	 * 
	 * @throws Exception
	 */
	public void testNewParser3() throws Exception {
		final String parseThis = "foo %%";
		assertEquals("foo % " + container.toOSString(), ExecUtil.parse(
				parseThis, somePath, true));
	}

	/**
	 * Perform the old parsing.
	 * 
	 * @throws Exception
	 */
	public void testOldParser1() throws Exception {
		final String parseThis = "foo {}";
		assertEquals("foo " + container.toOSString(), ExecUtil.parse(parseThis,
				somePath, true));
	}

	/**
	 * Perform the old parsing.
	 * 
	 * @throws Exception
	 */
	public void testOldParser2() throws Exception {
		final String parseThis = "foo";
		assertEquals("foo " + container.toOSString(), ExecUtil.parse(parseThis,
				somePath, true));
	}

	/**
	 * Perform the old parsing.
	 * 
	 * @throws Exception
	 */
	public void testOldParser3() throws Exception {
		final String parseThis = "foo";
		assertEquals(parseThis, ExecUtil.parse(parseThis, somePath, false));
	}

	/**
	 * Some sanity tests regarding the path.
	 * 
	 * @throws Exception
	 */
	public void testPathSanity() throws Exception {
		assertTrue(somePath.toFile().isFile());
		assertFalse(somePath.toFile().isDirectory());
		assertTrue(somePath.removeLastSegments(1).toFile().isDirectory());
		assertTrue(container.toFile().isDirectory());
	}

}
