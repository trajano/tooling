package net.trajano.openfolder.internal.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.trajano.openfolder.ISelectionProcessor;
import net.trajano.openfolder.internal.OpenInActivator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;

/**
 * Open Folder actions extend this class which will check whether the selected
 * item is a folder or has a folder. It will also peform the open folder call
 * with the command that was passed into it.
 * 
 * @author Archimedes Trajano
 * @version $Id: AbstractOpenFolderAction.java,v 1.1 2006/02/17 04:38:22 trajano
 *          Exp $
 */
public abstract class AbstractOpenFolderCommand extends AbstractHandler {

	/**
	 * This will create a job for each path to open and schedule them.
	 * {@inheritDoc}
	 */
	public final Object execute(final ExecutionEvent event)
			throws ExecutionException {
		final ISelectionProcessor selectionProcessor = getSelectionProcessor();
		final EvaluationContext applicationContext = (EvaluationContext) event
				.getApplicationContext();

		final Collection<?> selection = (Collection<?>) applicationContext
				.getDefaultVariable();

		final Set<IPath> paths = new HashSet<IPath>(selection.size());
		for (Object object : selection) {
			if (object instanceof IAdaptable) {
				final IPath p = selectionProcessor.getPath(object);
				if (p != null) {
					paths.add(p);
				}
			}
		}

		final Object activeMenuEditorInput = applicationContext
				.getVariable(ISources.ACTIVE_MENU_EDITOR_INPUT_NAME);
		if (activeMenuEditorInput != null
				&& activeMenuEditorInput instanceof IStructuredSelection) {
			final IPath p = selectionProcessor
					.getPath(((IStructuredSelection) activeMenuEditorInput)
							.getFirstElement());
			if (p != null) {
				paths.add(p);
			}
		}

		for (final IPath iPath : paths) {
			openPath(iPath);
		}
		return null;
	}

	/**
	 * This gets the selection processor which in this case is a composite
	 * selection processor that consists of all the extension point providers.
	 * 
	 * @return a selection processor.
	 */
	private ISelectionProcessor getSelectionProcessor() {
		return OpenInActivator.getDefault().getSelectionProcessor();
	}

	/**
	 * Open the path.
	 * 
	 * @param path
	 *            path to open
	 */
	protected abstract void openPath(final IPath path);

}
