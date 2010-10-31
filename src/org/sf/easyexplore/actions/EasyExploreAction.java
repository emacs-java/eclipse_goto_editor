package org.sf.easyexplore.actions;

import org.eclipse.jface.action.IAction;
import org.sf.easyexplore.EasyExplorePlugin;

import util.CmdUtil;

public class EasyExploreAction extends EasyBaseAction {

	public void runAction(IAction action) {
		try {
			String cmdPattern = EasyExplorePlugin.getDefault().getExplorer();
			CmdUtil.exec(cmdPattern, getSelectedFile());
		} catch (Throwable e) {
			EasyExplorePlugin.log(e);
		}
	}
}
