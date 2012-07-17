/*
 * Created on 6-Feb-2006
 * Copyright (c) 2006 IBM Canada, Ltd.
 */
package net.trajano.openfolder;

import java.util.LinkedList;
import java.util.List;

import net.trajano.openfolder.internal.Messages;
import net.trajano.openfolder.internal.OpenInActivator;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * A composite for the {@link ISelectionProcessor} classes.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: CompositeSelectionProcessor.java,v 1.1 2006/02/17 04:38:22
 *          trajano Exp $
 */
public final class CompositeSelectionProcessor implements ISelectionProcessor {

	/**
	 * This is a list of {@link ISelectionProcessor}s that are associated with
	 * the composite.
	 */
	private final List<ISelectionProcessor> processors = new LinkedList<ISelectionProcessor>();

	/**
	 * Adds a processor to the composite.
	 * 
	 * @param processor
	 *            processor to add.
	 */
	public void addProcessor(final ISelectionProcessor processor) {
		processors.add(processor);
	}

	/**
	 * This will go through the processors associated with the composite. It
	 * will return the IPath of the first processor that does not return a
	 * <code>null</code> value. This will log any error to the plugin log.
	 * {@inheritDoc}
	 */
	public IPath getPath(final Object o) {
		for (final ISelectionProcessor iSelectionProcessor : processors) {
			final ISelectionProcessor processor = iSelectionProcessor;
			try {
				final IPath p = processor.getPath(o);
				if (p != null && p.toFile().exists()) {
					return p;
				}
			} catch (final Exception e) {
				OpenInActivator
						.getDefault()
						.getLog()
						.log(Messages
								.error("selectionProcessor.error", new Object[] { o, processor, e }, e)); //$NON-NLS-1$
			}
		}
		return null;
	}

}
