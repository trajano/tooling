package net.trajano.openfolder.test;

import junit.framework.TestCase;
import net.trajano.openfolder.CompositeSelectionProcessor;

/**
 * Tests {@linkplain CompositeSelectionProcessor}.
 * 
 * @author Archimedes Trajano
 * 
 */
public class CompositeSelectionProcessorTest extends TestCase {
	/**
	 * Tests the processor.
	 */
	public void testCompositeSelectionProcessor() {
		final CompositeSelectionProcessor selectionProcessor = new CompositeSelectionProcessor();
		selectionProcessor.addProcessor(new CompositeSelectionProcessor());
		assertNull(selectionProcessor.getPath("test")); //$NON-NLS-1$
	}
}
