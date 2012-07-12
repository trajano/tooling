package net.trajano.eclipse.platform.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

/**
 * This generates a message listing all the possible adapters for the selected
 * item.
 * 
 * @author trajano
 * 
 */
public class ListAdaptersHandler extends AbstractCopyToClipboardHandler {
	protected String getAdaptableTextData(final IAdaptable adaptable) {
		final StringBuffer buf = new StringBuffer();
		buf.append("(class) ");
		buf.append(adaptable.getClass().getName());
		buf.append('\n');
		final Class[] interfaces = adaptable.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; ++i) {
			buf.append("(interface) ");
			buf.append(interfaces[i].getName());
			buf.append('\n');

		}
		final String[] types = Platform.getAdapterManager()
				.computeAdapterTypes(adaptable.getClass());
		for (int i = 0; i < types.length; ++i) {
			buf.append("(adapter) ");
			buf.append(types[i]);
			buf.append('\n');
		}
		return buf.toString();

	}
}
