/**
 * 
 */
package net.trajano.openfolder.internal.actions;

import java.io.IOException;

import net.trajano.openfolder.ExecUtil;
import net.trajano.openfolder.internal.Messages;
import net.trajano.openfolder.internal.OpenInActivator;
import net.trajano.openfolder.internal.preferences.PreferenceConstants;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * @author ARCHIET
 * 
 */
public class OpenInExplorerCommand extends AbstractOpenFolderCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void openPath(final IPath path) {
		final String command;
		if (path.toFile().isFile()) {
			command = OpenInActivator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.P_EXPLORER_WITH_FILE_CMD);
		} else {
			command = OpenInActivator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.P_EXPLORER_CMD);
		}
		final Job job = new Job(Messages.message(
				"explorer.job", new Object[] { path.toOSString() })) { //$NON-NLS-1$
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					ExecUtil.runFileExplorer(command, path);
					return Status.OK_STATUS;
				} catch (final IOException e) {
					return Messages
							.error("explorer.error", new Object[] { e, path.toOSString(), command }, e); //$NON-NLS-1$
				}
			}
		};
		job.schedule();
	}
}
