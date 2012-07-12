package net.trajano.eclipse.platform.actions;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;

/**
 * <p>
 * Implements GC action.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: GarbageCollectionAction.java,v 1.7 2006/02/19 09:00:34 trajano
 *          Exp $
 */
public final class AutoBuildAction extends ActionDelegate implements IWorkbenchWindowActionDelegate {
    /**
     * Workbench window.
     */
    private IWorkbenchWindow window;

    /**
     * {@inheritDoc}
     */
    public void init(final IAction action) {
        IWorkspaceDescription description = ResourcesPlugin.getWorkspace().getDescription();
        action.setChecked(description.isAutoBuilding());
    }

    /**
     * {@inheritDoc}
     */
    public void init(final IWorkbenchWindow aWindow) {
        this.window = aWindow;
    }

    /**
     * {@inheritDoc}
     */
    public void run(final IAction action) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceDescription description = workspace.getDescription();
        description.setAutoBuilding(!description.isAutoBuilding());
        action.setChecked(description.isAutoBuilding());
        try {
            workspace.setDescription(description);
        } catch (CoreException e) {
            ErrorDialog.openError(window.getShell(), null, null, e.getStatus());
        }
    }

}
