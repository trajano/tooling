package net.trajano.eclipse.platform.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * <p>
 * Copies the FQN to the clipboard with the defined separator.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: CopyFullyQualifiedNameToClipboardHandler.java 154 2006-02-19
 *          09:00:39 +0000 (Sun, 19 Feb 2006) trajano $
 */
public class CopyFullyQualifiedNameToClipboardHandler extends
		AbstractCopyToClipboardHandler {
	/**
	 * Separator character.
	 */
	private static final char SEPARATOR = '.';

	/**
	 * {@inheritDoc}
	 */
	protected final String getAdaptableTextData(final IAdaptable adaptable) {
		final IJavaElement element = (IJavaElement) adaptable
				.getAdapter(IJavaElement.class);
		if (element instanceof ICompilationUnit) {
			final StringBuffer text = new StringBuffer();
			try {
				final IPackageDeclaration[] packageDeclarations = ((ICompilationUnit) element)
						.getPackageDeclarations();
				if (packageDeclarations.length > 0) {
					text.append(packageDeclarations[0].getElementName());
					text.append(SEPARATOR);
				}
				text.append(element
						.getPath()
						.lastSegment()
						.substring(
								0,
								element.getPath().lastSegment().length()
										- element.getPath().getFileExtension()
												.length() - 1));
			} catch (final JavaModelException e) {
			}
			return text.toString();
		} else if (element instanceof IPackageFragment) {
			return element.getElementName();
		} else if (element instanceof IType) {
			final StringBuffer text = new StringBuffer();
			try {
				final IPackageDeclaration[] packageDeclarations = ((ICompilationUnit) element
						.getParent()).getPackageDeclarations();
				if (packageDeclarations.length > 0) {
					text.append(packageDeclarations[0].getElementName());
					text.append(SEPARATOR);
				}
				text.append(element
						.getPath()
						.lastSegment()
						.substring(
								0,
								element.getPath().lastSegment().length()
										- element.getPath().getFileExtension()
												.length() - 1));
			} catch (final JavaModelException e) {
			}
			return text.toString();
		} else if (element instanceof IMethod) {
			final StringBuffer text = new StringBuffer();
			try {
				final IPackageDeclaration[] packageDeclarations = ((ICompilationUnit) element
						.getParent().getParent()).getPackageDeclarations();
				if (packageDeclarations.length > 0) {
					text.append(packageDeclarations[0].getElementName());
					text.append(SEPARATOR);
				}
				text.append(element
						.getPath()
						.lastSegment()
						.substring(
								0,
								element.getPath().lastSegment().length()
										- element.getPath().getFileExtension()
												.length() - 1));
				text.append(SEPARATOR);
			} catch (final JavaModelException e) {
			}
			text.append(element.getElementName());
			return text.toString();
		}
		return null;
	}
}
