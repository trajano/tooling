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
public class OpenInCommandPromptCommand extends AbstractOpenFolderCommand {

	/**
	 * {@inheritDoc}
	 */
	protected void openPath(final IPath path) {
		final String command = OpenInActivator.getDefault()
				.getPreferenceStore().getString(
						PreferenceConstants.P_COMMAND_CMD);
		final Job job = new Job(Messages.message(
				"cmd.job", new Object[] { path.toOSString() })) { //$NON-NLS-1$
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					ExecUtil.runCommandPrompt(command, path);
					return Status.OK_STATUS;
				} catch (final IOException e) {
					return Messages
							.error(
									"cmd.error", new Object[] { e, path.toOSString(), command }, e); //$NON-NLS-1$
				}
			}
		};
		job.schedule();

	}
}
