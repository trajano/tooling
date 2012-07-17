package net.trajano.openfolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Utility class containing methods for execution of command lines.
 * </p>
 * 
 * @author Archimedes Trajano
 * @version $Id: ExecUtil.java 262 2007-07-17 19:21:50Z trajano $
 */
public final class ExecUtil {
	/**
	 * This is the marker to replace with the path if provided. It is made
	 * public in order for the preference page to show it as {} is not valid in
	 * the properties file.
	 */
	public static final String MARKER = "{}"; //$NON-NLS-1$

	/**
	 * Returns the container of the path specified. If the path is already a
	 * container, it will return itself.
	 * 
	 * @param path
	 *            path to analyse
	 * @return container
	 */
	public static IPath getContainingFolder(final IPath path) {
		if (path.toFile().isFile()) {
			return path.removeLastSegments(1);
		} else {
			return path;
		}
	}

	/**
	 * Returns the file portion of the path. It returns an empty string if the
	 * path is a directory.
	 * 
	 * @param path
	 *            path to evaluate
	 * @return file portion of the path.
	 */
	private static String getFileName(final IPath path) {
		if (path.toFile().isFile()) {
			return path.lastSegment();
		}
		return "";
	}

	/**
	 * @param commandLine
	 * @return
	 */
	private static boolean hasMarkers(final String commandLine) {
		return commandLine.indexOf("{}") >= 0 || commandLine.indexOf("%p") >= 0
				|| commandLine.indexOf("%d") >= 0
				|| commandLine.indexOf("%f") >= 0
				|| commandLine.indexOf("%P") >= 0
				|| commandLine.indexOf("%D") >= 0
				|| commandLine.indexOf("%F") >= 0;
	}

	/**
	 * This parses the command line. The parser is not too efficient, since it
	 * goes through each character one at a time, but is smart enough to handle
	 * path names with "%d" or any other marker.
	 * 
	 * The parser has the following states and transitions:
	 * 
	 * <ul>
	 * <li>0 = starting state</li>
	 * <li>0 -> 1 = if character is "%"</li>
	 * <li>0 -> 2 = if character is "{"</li>
	 * <li>1 -> 0 = after parsing the character</li>
	 * <li>2 -> 0 = after parsing the character</li>
	 * </ul>
	 * 
	 * @param commandLine
	 *            command line to parse.
	 * @param path
	 *            path to parse.
	 * @param autoAppend
	 *            automatically append the path if there are no markers found.
	 * @return the command line parsed
	 */
	public static String parse(final String commandLine, final IPath path,
			final boolean autoAppend) {
		final IPath container = getContainingFolder(path);
		final String fileName = getFileName(path);

		if (hasMarkers(commandLine)) {
			final StringBuffer parsedCommandLine = new StringBuffer(
					commandLine.length());
			final char[] commandLineArray = commandLine.toCharArray();
			int state = 0;
			for (final char element : commandLineArray) {
				if (state == 0) {
					if (element == '%') {
						state = 1;
					} else if (element == '{') {
						state = 2;
					} else {
						parsedCommandLine.append(element);
					}
				} else if (state == 1) {
					if (element == '%') {
						parsedCommandLine.append('%');
					} else if (element == 'p') {
						parsedCommandLine.append(path.toOSString());
					} else if (element == 'd') {
						parsedCommandLine.append(container.toOSString());
					} else if (element == 'f') {
						parsedCommandLine.append(fileName);
					} else if (element == 'P') {
						parsedCommandLine.append(path.toPortableString());
					} else if (element == 'D') {
						parsedCommandLine.append(container.toPortableString());
					} else if (element == 'F') {
						parsedCommandLine.append(fileName);
					} else {
						parsedCommandLine.append('%');
						parsedCommandLine.append(element);
					}
					state = 0;
				} else if (state == 2) {
					if (element == '}') {
						parsedCommandLine.append(container.toOSString());
					} else {
						parsedCommandLine.append('{');
						parsedCommandLine.append(element);
					}
					state = 0;
				}

			}
			return parsedCommandLine.toString();
		} else {
			if (autoAppend) {
				return commandLine.replaceAll("%%", "%") + ' '
						+ container.toOSString();
			} else {
				return commandLine.replaceAll("%%", "%");
			}
		}

	}

	/**
	 * <p>
	 * This runs an AppleScript that is read from the stream. It replaces the
	 * "%p" with the {@link IPath#toOSString()}. It will take the path component
	 * from the {@link IPath} (if it is a file then it will drop the file
	 * component).
	 * </p>
	 * <p>
	 * The stream is not created here but passed on because of class loader.
	 * </p>
	 * 
	 * @param scriptStream
	 *            stream for the script.
	 * @param path
	 *            path to use.
	 * @throws IOException
	 *             thrown when there is a problem dealing with the processes.
	 */
	public static void runAppleScript(final InputStream scriptStream,
			final IPath path) throws IOException {
		final IPath finalPath;
		if (path.toFile().isDirectory()) {
			finalPath = path;
		} else {
			finalPath = path.removeLastSegments(1);
		}
		final BufferedReader scriptReader = new BufferedReader(
				new InputStreamReader(scriptStream));
		final Process p = Runtime.getRuntime().exec("osascript");
		final PrintStream processStream = new PrintStream(p.getOutputStream());
		while (true) {
			String scriptLine = scriptReader.readLine();
			if (scriptLine == null) {
				break;
			}
			scriptLine = scriptLine.replace("%p", finalPath.toOSString());
			processStream.println(scriptLine);
		}
		scriptReader.close();
		processStream.close();

	}

	/**
	 * This executes the command line for running the command prompt. It will
	 * <b>not</b> append the path to the end of the string if the string "{}" is
	 * not present in the command line.
	 * 
	 * @param commandLine
	 *            command line to execute
	 * @param path
	 *            path to open
	 * @throws IOException
	 *             problem starting up the command line.
	 */
	public static void runCommandPrompt(final String commandLine,
			final IPath path) throws IOException {
		final String parsedCommandLine = parse(commandLine, path, false);
		Runtime.getRuntime().exec(parsedCommandLine, null,
				getContainingFolder(path).toFile());
	}

	/**
	 * This executes the command line for running the file explorer. It will
	 * append the path to the end of the string if the string "{}" is not
	 * present in the command line.
	 * 
	 * @param commandLine
	 *            command line to execute
	 * @param path
	 *            path to open
	 * @throws IOException
	 *             problem starting up the command line.
	 */
	public static void runFileExplorer(final String commandLine,
			final IPath path) throws IOException {
		final String parsedCommandLine = parse(commandLine, path, true);
		Runtime.getRuntime().exec(parsedCommandLine, null,
				getContainingFolder(path).toFile());
	}

	/**
	 * Prevent instantiation of utility class.
	 */
	private ExecUtil() {

	}
}
