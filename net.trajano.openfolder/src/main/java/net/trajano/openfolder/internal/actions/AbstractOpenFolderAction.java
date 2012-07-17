package net.trajano.openfolder.internal.actions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.trajano.openfolder.CompositeSelectionProcessor;
import net.trajano.openfolder.ISelectionProcessor;
import net.trajano.openfolder.internal.Messages;
import net.trajano.openfolder.internal.OpenInActivator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;

/**
 * Open Folder actions extend this class which will check whether the selected
 * item is a folder or has a folder. It will also peform the open folder call
 * with the command that was passed into it.
 * 
 * @author Archimedes Trajano
 * @version $Id: AbstractOpenFolderAction.java,v 1.1 2006/02/17 04:38:22 trajano
 *          Exp $
 */
public abstract class AbstractOpenFolderAction extends ActionDelegate implements
		IObjectActionDelegate {

	/**
	 * Attribute name "class" in "selectionProcessor" tag.
	 */
	private static final String CLASS = "class"; //$NON-NLS-1$

	/**
	 * "selectionProcessor" tag name.
	 */
	private static final String SELECTION_PROCESSOR = "selectionProcessor"; //$NON-NLS-1$

	/**
	 * Selection processor extension point ID.
	 */
	private static final String SELECTION_PROCESSOR_EXTENSION_POINT_ID = "net.trajano.openfolder.selectionProcessor"; //$NON-NLS-1$

	/**
	 * Set of {@link IPath} to open.
	 */
	private Set<IPath> toOpen;

	/**
	 * This gets the selection processor which in this case is a composite
	 * selection processor that consists of all the extension point providers.
	 * 
	 * @return a selection processor.
	 */
	private ISelectionProcessor getSelectionProcessor() {
		final CompositeSelectionProcessor processors = new CompositeSelectionProcessor();
		final IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(SELECTION_PROCESSOR_EXTENSION_POINT_ID)
				.getExtensions();
		for (final IExtension extension : extensions) {
			final IConfigurationElement[] configurationElements = extension
					.getConfigurationElements();
			try {
				for (final IConfigurationElement configurationElement : configurationElements) {
					if (configurationElement.getName().equals(
							SELECTION_PROCESSOR)) {
						processors
								.addProcessor((ISelectionProcessor) configurationElement
										.createExecutableExtension(CLASS));
					}
				}
			} catch (final CoreException e) {
				OpenInActivator
						.getDefault()
						.getLog()
						.log(Messages
								.error("selectionprocessor.error.message", new Object[] {}, e)); //$NON-NLS-1$
			}
		}
		return processors;
	}

	/**
	 * Open the path.
	 * 
	 * @param path
	 *            path to open
	 */
	protected abstract void openPath(final IPath path);

	/**
	 * This will create a job for each path to open and schedule them.
	 * {@inheritDoc}
	 */
	@Override
	public final void run(final IAction action) {
		for (final IPath iPath : toOpen) {
			openPath(iPath);
		}
	}

	/**
	 * This will run a check to see if the selection contains any valid parent
	 * folders. If there is one of more parent folder that is valid then it will
	 * enable the action. {@inheritDoc}
	 */
	@Override
	public final void selectionChanged(final IAction action,
			final ISelection selection) {
		if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
			return;
		}
		final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		toOpen = new HashSet<IPath>(structuredSelection.size());
		for (@SuppressWarnings("unchecked")
		final Iterator<IPath> i = structuredSelection.iterator(); i.hasNext();) {
			IPath p = getSelectionProcessor().getPath(i.next());
			if (p != null) {
				if (p.toFile().isFile()) {
					p = p.removeLastSegments(1);
				}
				toOpen.add(p);
			}
		}
		action.setEnabled(!toOpen.isEmpty());
	}

	/**
	 * Disables the action. Though it does not work. {@inheritDoc}
	 */
	public final void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
		action.setEnabled(false);
	}
}
