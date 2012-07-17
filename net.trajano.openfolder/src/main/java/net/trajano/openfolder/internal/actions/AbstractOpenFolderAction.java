package net.trajano.openfolder.internal.actions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.trajano.openfolder.CompositeSelectionProcessor;
import net.trajano.openfolder.ISelectionProcessor;
import net.trajano.openfolder.internal.OpenInActivator;
import net.trajano.openfolder.internal.Messages;

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
public abstract class AbstractOpenFolderAction extends ActionDelegate implements IObjectActionDelegate {

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
    private Set toOpen;

    /**
     * This will create a job for each path to open and schedule them.
     * {@inheritDoc}
     */
    public final void run(final IAction action) {
        for (Iterator i = toOpen.iterator(); i.hasNext();) {
            openPath((IPath) i.next());
        }
    }

    /**
     * This will run a check to see if the selection contains any valid parent
     * folders. If there is one of more parent folder that is valid then it will
     * enable the action. {@inheritDoc}
     */
    public final void selectionChanged(final IAction action, final ISelection selection) {
        if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
            return;
        }
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        toOpen = new HashSet(structuredSelection.size());
        for (Iterator i = structuredSelection.iterator(); i.hasNext();) {
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
    public final void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
        action.setEnabled(false);
    }

    /**
     * This gets the selection processor which in this case is a composite
     * selection processor that consists of all the extension point providers.
     * 
     * @return a selection processor.
     */
    private ISelectionProcessor getSelectionProcessor() {
        CompositeSelectionProcessor processors = new CompositeSelectionProcessor();
        IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(SELECTION_PROCESSOR_EXTENSION_POINT_ID).getExtensions();
        for (int i = 0; i < extensions.length; ++i) {
            IConfigurationElement[] configurationElements = extensions[i].getConfigurationElements();
            try {
                for (int j = 0; j < configurationElements.length; ++j) {
                    if (configurationElements[j].getName().equals(SELECTION_PROCESSOR)) {
                        processors.addProcessor((ISelectionProcessor) configurationElements[j].createExecutableExtension(CLASS));
                    }
                }
            } catch (CoreException e) {
                OpenInActivator.getDefault().getLog().log(Messages.error("selectionprocessor.error.message", new Object[] {}, e)); //$NON-NLS-1$
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
}
