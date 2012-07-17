package net.trajano.openfolder.test;

import java.io.IOException;

import junit.framework.TestCase;
import net.trajano.openfolder.ExecUtil;

import org.eclipse.core.runtime.Path;

/**
 * <p>
 * Tests {@link net.trajano.openfolder.ExecUtil} methods.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: ExecUtilTest.java 154 2006-02-19 09:00:39Z trajano $
 */
public class ExecUtilTest extends TestCase {
    /**
     * Tests an invalid command for file explorer.
     * 
     * @throws Exception
     */
    public void testBadFileExplorer() throws Exception {
        try {
            ExecUtil.runFileExplorer("nowayjose", new Path("/")); //$NON-NLS-1$ //$NON-NLS-2$
            fail("should've gotten IOException"); //$NON-NLS-1$
        } catch (IOException e) {
        }
    }

    /**
     * Tests an invalid command for command prompt.
     * 
     * @throws Exception
     */
    public void testBadCommandPrompt() throws Exception {
        try {
            ExecUtil.runCommandPrompt("nowayjose", new Path("/")); //$NON-NLS-1$ //$NON-NLS-2$
            fail("should've gotten IOException"); //$NON-NLS-1$
        } catch (IOException e) {
        }
    }
}
