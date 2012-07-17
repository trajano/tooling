package net.trajano.openfolder.internal.actions;

import java.io.IOException;

import net.trajano.openfolder.ExecUtil;
import net.trajano.openfolder.internal.Messages;
import net.trajano.openfolder.internal.OpenInActivator;
import net.trajano.openfolder.internal.preferences.PreferenceConstants;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Opens the folder in the command prompt.
 */
public final class OpenFolderInCommandPromptAction extends
		AbstractOpenFolderAction {
	/**
	 * {@inheritDoc}
	 * 
	 * This does a something different for {@link Platform#OS_MACOSX} in which
	 * it invokes an AppleScript that starts up Terminal that is part of the
	 * plug-in distribution.
	 */
	@Override
	protected void openPath(final IPath path) {
		final String command = OpenInActivator.getDefault()
				.getPreferenceStore()
				.getString(PreferenceConstants.P_COMMAND_CMD);
		final Job job = new Job(Messages.message(
				"cmd.job", new Object[] { path.toOSString() })) { //$NON-NLS-1$
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					if (Platform.OS_MACOSX.equals(Platform.getOS())) {
						ExecUtil.runAppleScript(
								getClass()
										.getResourceAsStream(
												"/net/trajano/openfolder/internal/OpenInTerminal.applescript"),
								path);
					} else {
						ExecUtil.runCommandPrompt(command, path);
					}
					return Status.OK_STATUS;
				} catch (final IOException e) {
					return Messages
							.error("cmd.error", new Object[] { e, path.toOSString(), command }, e); //$NON-NLS-1$
				}
			}
		};
		job.schedule();

	}
}
