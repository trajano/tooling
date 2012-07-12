package net.trajano.eclipse.platform.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * <p>
 * Copies the Fully qualified path to the clipboard. The resulting string can be
 * used with {@link Class#getResource(String)}. This will return the portable
 * string if the file is not in the class path.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: CopyFullyQualifiedNameToClipboardHandler.java 154 2006-02-19
 *          09:00:39 +0000 (Sun, 19 Feb 2006) trajano $
 */
public final class CopyFullyQualifiedPathToClipboardHandler extends
		AbstractCopyToClipboardHandler {
	/**
	 * Separator character.
	 */
	private static final char SEPARATOR = '/';

	/**
	 * {@inheritDoc}
	 */
	protected final String getAdaptableTextData(final IAdaptable adaptable) {
		final IJavaElement element = (IJavaElement) adaptable
				.getAdapter(IJavaElement.class);
		if (element instanceof ICompilationUnit) {
			final StringBuffer text = new StringBuffer(SEPARATOR);
			try {
				final IPackageDeclaration[] packageDeclarations = ((ICompilationUnit) element)
						.getPackageDeclarations();
				if (packageDeclarations.length > 0) {
					text.append(packageDeclarations[0].getElementName()
							.replace('.', SEPARATOR));
					text.append(SEPARATOR);
				}
				text.append(element.getPath().lastSegment());
			} catch (final JavaModelException e) {
			}
			return text.toString();
		} else if (element instanceof IPackageFragment) {
			return "/" + element.getElementName().replace('.', SEPARATOR);
		} else if (element instanceof IType) {
			final StringBuffer text = new StringBuffer(SEPARATOR);
			try {
				final IPackageDeclaration[] packageDeclarations = ((ICompilationUnit) element
						.getParent()).getPackageDeclarations();
				if (packageDeclarations.length > 0) {
					text.append(packageDeclarations[0].getElementName()
							.replace('.', SEPARATOR));
					text.append(SEPARATOR);
				}
				text.append(element.getPath().lastSegment());
			} catch (final JavaModelException e) {
			}
			return text.toString();
		}

		final IResource resource = (IResource) adaptable
				.getAdapter(IResource.class);
		if (resource != null) {
			final IJavaProject jp = getProject(resource);
			if (jp == null) {
				return null;
			}
			final IFile file = (IFile) adaptable.getAdapter(IFile.class);

			try {
				final IPackageFragment parent = jp.findPackageFragment(file
						.getParent().getFullPath());
				if (parent != null) {
					final StringBuffer text = new StringBuffer(SEPARATOR);
					if (parent.getElementName().length() > 0) {
						text.append(parent.getElementName().replace('.',
								SEPARATOR));
						text.append(SEPARATOR);
					}

					text.append(file.getName());
					return text.toString();
				} else {
					return file.getFullPath().toPortableString();
				}
			} catch (final JavaModelException e) {
			}
		}
		return null;
	}

	/**
	 * Retrieves the {@link IJavaProject} containing the resource.
	 * 
	 * @param r
	 *            resource
	 * @return the project, <code>null</code> if not found.
	 */
	private IJavaProject getProject(final IResource r) {
		final IJavaElement p = (IJavaElement) r.getAdapter(IJavaElement.class);
		if (p != null) {
			return p.getJavaProject();
		}
		if (r.getParent() == null) {
			return null;
		}

		return getProject(r.getParent());
	}

}
