package net.trajano.openfolder.internal.preferences;

import java.io.IOException;

import net.trajano.openfolder.ExecUtil;
import net.trajano.openfolder.internal.Messages;
import net.trajano.openfolder.internal.OpenInActivator;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * <p>
 * Preference page creation for the plug-in.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: OpenFolderPreferencePage.java 257 2007-07-17 07:50:28Z trajano
 *          $
 */
public final class OpenFolderPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	/**
	 * The default margin to reset to.
	 */
	private static final int DEFAULT_MARGIN = 5;

	/**
	 * Root path. This is the path used for testing if the command lines work
	 * correctly.
	 */
	private static final Path ROOT_PATH = new Path("/"); //$NON-NLS-1$

	/**
	 * 
	 * 
	 */
	public OpenFolderPreferencePage() {
		super(GRID);
		setPreferenceStore(OpenInActivator.getDefault().getPreferenceStore());
		setDescription(Messages.message("prefpage.description")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	protected void adjustGridLayout() {
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 1;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;

		getFieldEditorParent().setLayout(gridLayout);
	}

	/**
	 * @inheritDoc
	 */
	protected void createFieldEditors() {
		final GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 2;

		final GridData fillRowGridData = new GridData(GridData.FILL_HORIZONTAL);
		fillRowGridData.horizontalSpan = 2;

		{
			final Group group = new Group(getFieldEditorParent(), SWT.NONE);
			group.setText(Messages.message("prefpage.explorer")); //$NON-NLS-1$
			group.setLayout(groupLayout);
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			final Composite note = createNoteComposite(group.getFont(),
					group,
					Messages.message("prefpage.explorer.note"), //$NON-NLS-1$
					Messages
							.message(
									"prefpage.explorer.note.text", new Object[] { ExecUtil.MARKER })); //$NON-NLS-1$
			note.setLayoutData(fillRowGridData);

			final StringFieldEditor explorerFieldEditor = new StringFieldEditor(
					PreferenceConstants.P_EXPLORER_CMD, Messages
							.message("prefpage.explorer.command"), group); //$NON-NLS-1$
			addField(explorerFieldEditor);
			final Button b = new Button(group, SWT.PUSH);
			b.setLayoutData(fillRowGridData);
			b.setText(Messages.message("prefpage.explorer.test")); //$NON-NLS-1$
			b.addMouseListener(new MouseListener() {
				public void mouseDoubleClick(final MouseEvent e) {
				}

				public void mouseDown(final MouseEvent e) {
				}

				public void mouseUp(final MouseEvent e) {
					try {
						ExecUtil.runFileExplorer(explorerFieldEditor
								.getStringValue(), ROOT_PATH);
					} catch (final IOException ex) {
						Messages
								.openErrorDialog(
										"navigator.error.title", "navigator.error.message", new Object[] {}); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}

			});

			resetLayout(group);
		}
		{
			final Group group = new Group(getFieldEditorParent(), SWT.NONE);
			group.setText(Messages.message("prefpage.explorerWithFile")); //$NON-NLS-1$
			group.setLayout(groupLayout);
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			final Composite note = createNoteComposite(group.getFont(),
					group,
					Messages.message("prefpage.explorer.note"), //$NON-NLS-1$
					Messages
							.message(
									"prefpage.explorer.note.text", new Object[] { ExecUtil.MARKER })); //$NON-NLS-1$
			note.setLayoutData(fillRowGridData);

			final StringFieldEditor explorerFieldEditor = new StringFieldEditor(
					PreferenceConstants.P_EXPLORER_WITH_FILE_CMD,
					Messages.message("prefpage.explorerWithFile.command"), group); //$NON-NLS-1$
			addField(explorerFieldEditor);
			final Button b = new Button(group, SWT.PUSH);
			b.setLayoutData(fillRowGridData);
			b.setText(Messages.message("prefpage.explorerWithFile.test")); //$NON-NLS-1$
			b.addMouseListener(new MouseListener() {
				public void mouseDoubleClick(final MouseEvent e) {
				}

				public void mouseDown(final MouseEvent e) {
				}

				public void mouseUp(final MouseEvent e) {
					try {
						ExecUtil.runFileExplorer(explorerFieldEditor
								.getStringValue(), ROOT_PATH);
					} catch (final IOException ex) {
						Messages
								.openErrorDialog(
										"navigator.error.title", "navigator.error.message", new Object[] {}); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}

			});

			resetLayout(group);
		}
		if (!Platform.OS_MACOSX.equals(Platform.getOS())) {
			final Group group = new Group(getFieldEditorParent(), SWT.SHADOW_IN);
			group.setText(Messages.message("prefpage.cmd")); //$NON-NLS-1$
			group.setLayout(groupLayout);
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			final StringFieldEditor commandPromptStringFieldEditor = new StringFieldEditor(
					PreferenceConstants.P_COMMAND_CMD, Messages
							.message("prefpage.cmd.command"), group); //$NON-NLS-1$
			addField(commandPromptStringFieldEditor);
			final Button b = new Button(group, SWT.PUSH);
			b.setLayoutData(fillRowGridData);
			b.setText(Messages.message("prefpage.cmd.test")); //$NON-NLS-1$
			b.addMouseListener(new MouseListener() {
				public void mouseDoubleClick(final MouseEvent e) {
				}

				public void mouseDown(final MouseEvent e) {
				}

				public void mouseUp(final MouseEvent e) {
					try {
						ExecUtil
								.runCommandPrompt(
										commandPromptStringFieldEditor
												.getStringValue(), ROOT_PATH);
					} catch (final IOException ex) {
						Messages
								.openErrorDialog(
										"navigator.error.title", "navigator.error.message", new Object[] {}); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}

			});

			resetLayout(group);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void init(final IWorkbench workbench) {

	}

	/**
	 * This resets the layout of the composite after the
	 * {@link FieldEditorPreferencePage#addField(org.eclipse.jface.preference.FieldEditor)}
	 * gets invoked.
	 * 
	 * @param composite
	 *            composite that has a grid layout.
	 */
	private void resetLayout(final Composite composite) {
		final GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 2;
		layout.marginHeight = DEFAULT_MARGIN;
		layout.marginWidth = DEFAULT_MARGIN;
		composite.setLayout(layout);
	}
}
