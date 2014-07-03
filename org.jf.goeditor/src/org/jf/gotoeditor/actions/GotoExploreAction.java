package org.jf.gotoeditor.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.jf.gotoeditor.GotoExplorePlugin;

import util.CmdUtil;

public class GotoExploreAction extends GotoBaseAction {

	public void runAction(IAction action) {
		try {
			String cmdPattern = GotoExplorePlugin.getDefault().getExplorer();
			File selectedFile = getSelectedFile();
            if (selectedFile==null) {
            	MessageDialog.openInformation(new Shell(), "Goto Explorer",
                                              "Unable to run this command (please select a file or directory first)" );
                return;
            }
			if (System.getProperty("os.name").toLowerCase().contains("windows")
                && cmdPattern.toLowerCase().contains("explorer")
                && selectedFile != null && selectedFile.exists()) {
				if (selectedFile.isDirectory()) {
					cmdPattern = "explorer.exe /n, /e, %d";
				} else {
					cmdPattern = "explorer.exe /n, /e, /select, %f";
				}
			}
			CmdUtil.exec(cmdPattern, getSelectedFile());
		} catch (Throwable e) {
			GotoExplorePlugin.log(e);
		}
	}
}
