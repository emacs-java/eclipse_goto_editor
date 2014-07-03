package org.jf.gotoeditor.actions;

import org.eclipse.jface.action.IAction;
import org.jf.gotoeditor.GotoExplorePlugin;

import util.CmdUtil;

public class GotoMyCmdAction extends GotoBaseAction {
	public void runAction(IAction action) {
		try {
			String cmdPattern = GotoExplorePlugin.getDefault().getMyCmd();
			CmdUtil.exec(cmdPattern, getSelectedFile());
		} catch (Throwable e) {
			GotoExplorePlugin.log(e);
		}
	}
}
