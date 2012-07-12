package net.trajano.eclipse.platform.actions;

import net.trajano.eclipse.platform.PlatformExtensionsPlugin;
import net.trajano.eclipse.platform.preferences.PreferenceConstants;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public final class RecycleEclipseAction implements
		IWorkbenchWindowActionDelegate {
	/**
	 * Window.
	 */
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public RecycleEclipseAction() {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog. {@inheritDoc}
	 */
	public void init(final IWorkbenchWindow aWindow) {
		this.window = aWindow;
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI. {@inheritDoc}
	 */
	public void run(final IAction action) {
		if (!PlatformExtensionsPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.P_CONFIRM_RESTART)
				|| showConfirmDialog()) {
			window.getWorkbench().restart();
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created. {@inheritDoc}
	 */
	public void selectionChanged(final IAction action,
			final ISelection selection) {
	}

	/**
	 * Shows the confirmation dialog.
	 * 
	 * @return true if the user accepted.
	 */
	private boolean showConfirmDialog() {
		final MessageDialog dialog = new MessageDialog(
				window.getShell(),
				PlatformExtensionsPlugin
						.getResourceString("RecycleEclipseAction.recycleEclipseTitle"), //$NON-NLS-1$
				null, // accept the default window icon
				PlatformExtensionsPlugin
						.getResourceString("RecycleEclipseAction.recycleEclipseMessage"), //$NON-NLS-1$
				MessageDialog.QUESTION,
				new String[] { IDialogConstants.YES_LABEL,
						IDialogConstants.NO_LABEL }, 1);
		// no is the default
		return dialog.open() == 0;
	}
}
