/**
 * 
 */
package net.trajano.eclipse.platform.actions;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

/**
 * @author Archimedes Trajano
 * 
 */
public abstract class AbstractCopyToClipboardHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final EvaluationContext applicationContext = (EvaluationContext) event
				.getApplicationContext();

		@SuppressWarnings("unchecked")
		final Collection<IAdaptable> selection = (Collection<IAdaptable>) applicationContext
				.getDefaultVariable();

		final StringBuilder buf = new StringBuilder();

		for (final Iterator<IAdaptable> i = selection.iterator(); i.hasNext();) {
			buf.append(getAdaptableTextData(i.next()));
			if (i.hasNext()) {
				buf.append('\n');
			}
		}
		final Object[] data = new Object[] { buf.toString() };
		final Transfer[] transfer = new Transfer[] { TextTransfer.getInstance() };
		new Clipboard(Display.getCurrent()).setContents(data, transfer);
		return null;
	}

	/**
	 * Extracts the text data that is supposed to be put into the clipboard for
	 * the selected {@link IAdaptable} object.
	 * 
	 * @param adaptable
	 *            adaptable object to process.
	 * @return the text data for the adaptable object
	 */
	protected abstract String getAdaptableTextData(final IAdaptable adaptable);
}
