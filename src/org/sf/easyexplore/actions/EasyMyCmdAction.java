package org.sf.easyexplore.actions;

import org.eclipse.jface.action.IAction;
import org.sf.easyexplore.EasyExplorePlugin;

import util.CmdUtil;

public class EasyMyCmdAction extends EasyBaseAction {
	public void runAction(IAction action) {
		try {
			String cmdPattern = EasyExplorePlugin.getDefault().getMyCmd();
			CmdUtil.exec(cmdPattern, getSelectedFile());
		} catch (Throwable e) {
			EasyExplorePlugin.log(e);
		}
	}
}
