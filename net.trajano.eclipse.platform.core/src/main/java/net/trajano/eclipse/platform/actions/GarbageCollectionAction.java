package net.trajano.eclipse.platform.actions;

import net.trajano.eclipse.platform.PlatformExtensionsPlugin;
import net.trajano.eclipse.platform.preferences.PreferenceConstants;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;

/**
 * <p>
 * Implements GC action.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: GarbageCollectionAction.java 154 2006-02-19 09:00:39 +0000
 *          (Sun, 19 Feb 2006) trajano $
 */
public final class GarbageCollectionAction extends ActionDelegate implements
		IWorkbenchWindowActionDelegate {
	/**
	 * <p>
	 * Job for GC.
	 * </p>
	 * 
	 * @author Archimedes Trajano
	 * @version $Id: GarbageCollectionAction.java 154 2006-02-19 09:00:39 +0000
	 *          (Sun, 19 Feb 2006) trajano $
	 */
	private class GarbageCollectionJob extends WorkspaceJob {

		/**
		 * Number of steps to perform when doing a GC.
		 */
		private static final int STEPS_IN_GC = 3;

		/**
         * 
         *
         */
		public GarbageCollectionJob() {
			super(PlatformExtensionsPlugin
					.getResourceString("GarbageCollectionAction.progress.text"));
			setPriority(SHORT);
		}

		/**
		 * {@inheritDoc}
		 */
		public IStatus runInWorkspace(final IProgressMonitor realMonitor)
				throws CoreException {
			final int numberOfGc = PlatformExtensionsPlugin.getDefault()
					.getPreferenceStore().getInt(
							PreferenceConstants.P_NUMBER_OF_GARBAGE_COLLECTION);
			final int sleeptime = PlatformExtensionsPlugin
					.getDefault()
					.getPreferenceStore()
					.getInt(
							PreferenceConstants.P_SLEEP_TIME_BETWEEN_GARBAGE_COLLECTION);

			final IProgressMonitor monitor = new GarbageCollectionProgressMonitor(
					realMonitor, getThread());
			monitor
					.beginTask(
							PlatformExtensionsPlugin
									.getResourceString("GarbageCollectionAction.progress.starting"), numberOfGc * STEPS_IN_GC - 1); //$NON-NLS-1$

			for (int i = numberOfGc - 1; i >= 0; --i) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				monitor
						.subTask(PlatformExtensionsPlugin
								.getResourceString("GarbageCollectionAction.progress.finalization")); //$NON-NLS-1$
				System.runFinalization();
				monitor.worked(1);
				monitor
						.subTask(PlatformExtensionsPlugin
								.getResourceString("GarbageCollectionAction.progress.gc")); //$NON-NLS-1$
				System.gc();
				monitor.worked(1);
				if (i > 0) {
					monitor
							.subTask(PlatformExtensionsPlugin
									.getResourceString("GarbageCollectionAction.progress.sleeping")); //$NON-NLS-1$
					try {
						Thread.sleep(sleeptime);
					} catch (final InterruptedException e) {
						return Status.CANCEL_STATUS;
					}
					monitor.worked(1);
				}
			}
			// done(Status.OK_STATUS);
			return Status.OK_STATUS;
		}
	}

	/**
	 * <p>
	 * Monitor used for garbage collection.
	 * </p>
	 * 
	 * @author Archimedes Trajano
	 * @version $Id: GarbageCollectionAction.java 154 2006-02-19 09:00:39 +0000
	 *          (Sun, 19 Feb 2006) trajano $
	 */
	private static class GarbageCollectionProgressMonitor extends
			ProgressMonitorWrapper {
		/**
		 * @param monitor
		 *            monitor
		 * @param thread
		 *            the execution thread.
		 */
		public GarbageCollectionProgressMonitor(final IProgressMonitor monitor,
				final Thread thread) {
			super(monitor);
		}
	}

	/**
	 * <p>
	 * Scheduling rule for GC. Prevents two GC jobs from running at the same
	 * time.
	 * </p>
	 * 
	 * @author Archimedes Trajano
	 * @version $Id: GarbageCollectionAction.java 154 2006-02-19 09:00:39 +0000
	 *          (Sun, 19 Feb 2006) trajano $
	 */
	private class GarbageCollectionSchedulingRule implements ISchedulingRule {
		/**
		 * {@inheritDoc}
		 */
		public boolean contains(final ISchedulingRule rule) {
			return rule == this;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean isConflicting(final ISchedulingRule rule) {
			return rule instanceof GarbageCollectionSchedulingRule;
		}
	}

	/**
	 * Workbench window.
	 */
	private IWorkbenchWindow window;

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
		if (PlatformExtensionsPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.P_CONFIRM_GC)
				&& !window.getWorkbench().saveAllEditors(true)) {
			return;
		}

		final GarbageCollectionJob job = new GarbageCollectionJob();

		job.setRule(new GarbageCollectionSchedulingRule());
		job.schedule();
	}
}
