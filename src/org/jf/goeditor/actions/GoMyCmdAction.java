package org.jf.goeditor.actions;

import org.eclipse.jface.action.IAction;
import org.jf.goeditor.GoExplorePlugin;

import util.CmdUtil;

public class GoMyCmdAction extends GoBaseAction {
	public void runAction(IAction action) {
		try {
			String cmdPattern = GoExplorePlugin.getDefault().getMyCmd();
			CmdUtil.exec(cmdPattern, getSelectedFile());
		} catch (Throwable e) {
			GoExplorePlugin.log(e);
		}
	}
}
