package org.jf.goeditor.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.jf.goeditor.GoExplorePlugin;

import util.CmdUtil;

public class GoExploreAction extends GoBaseAction {

	public void runAction(IAction action) {
		try {
			String cmdPattern = GoExplorePlugin.getDefault().getExplorer();
			File selectedFile = getSelectedFile();
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
			GoExplorePlugin.log(e);
		}
	}
}