package util;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.sf.easyexplore.EasyExplorePlugin;

public class CmdUtil {

	/**
	 * check the syntax of the cmdPattern from Preference Page
	 * 
	 * @param cmdPattern
	 */
	private static void check(String cmdPattern) {
		if ((!cmdPattern.contains("%f")) && (!cmdPattern.contains("%d"))) {
			cmdPattern += "  %f";
		}
	}

	/**
	 * return null if selectFile don't exists on disk ��if selectedFile is a file
	 * then return it's parent�� if selectFile is a dir then return itself
	 * actually this method will get get real File that %d means
	 * 
	 * @param selectedFile
	 * @return
	 */
	private static File getDir(File selectedFile) {
		if (selectedFile != null && selectedFile.exists()) {
			if (selectedFile.isFile()) {
				return selectedFile.getParentFile();
			} else {
				return selectedFile;
			}
		}
		return null;
	}

	/**
	 * 
	 * this method will replace the %f and %d with the real FilePath
	 * 
	 * @param cmdPattern
	 *            the command get from preference page like "notepad %f"
	 * @param selectedFile
	 * @return a String array ,so that Runtime.exec(string array) can be
	 *         executed
	 */
	public static String[] parseCmdPattern(String cmdPattern, File selectedFile) {
		check(cmdPattern);
		String[] cmdAndParams = cmdPattern.split("\\s+");
		for (int i = 0; i < cmdAndParams.length; i++) {
			if (cmdAndParams[i].contains("%f")) {
				cmdAndParams[i] = cmdAndParams[i].replace("%f", selectedFile
						.getAbsolutePath());
			}
			if (cmdAndParams[i].contains("%d")) {
				cmdAndParams[i] = cmdAndParams[i].replace("%d", getDir(
						selectedFile).getAbsolutePath());
			}
		}
		EasyExplorePlugin.log(cmdAndParams);
		return cmdAndParams;
	}

	/**
	 * execute the command
	 * 
	 * @param cmdPattern
	 * @param selectedFile
	 */
	public static Process exec(String cmdPattern, File selectedFile) {
		if (selectedFile == null) {
			MessageDialog.openInformation(new Shell(), " Easy Explorer",
					"please select the file or dir to handled by");
			return null;
		}
		String[] cmd = parseCmdPattern(cmdPattern, selectedFile);
		try {
			return Runtime.getRuntime().exec(cmd);
		} catch (Throwable t) {
			MessageDialog.openInformation(new Shell(), "EasyExplore",
					"Unable to execute " +cmdPattern);
			EasyExplorePlugin.log(t);
		}

		return null;
	}
}
