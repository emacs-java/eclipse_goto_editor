package org.sf.easyexplore.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.sf.easyexplore.EasyExplorePlugin;

import util.CmdUtil;

public class EasyExploreAction extends EasyBaseAction {

	public void runAction(IAction action) {
		try {
			String cmdPattern = EasyExplorePlugin.getDefault().getExplorer();
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
			EasyExplorePlugin.log(e);
		}
	}
}
