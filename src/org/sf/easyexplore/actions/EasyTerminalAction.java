package org.sf.easyexplore.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionDelegate;
import org.sf.easyexplore.EasyExplorePlugin;

import util.CmdUtil;

public class EasyTerminalAction extends EasyBaseAction {

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void runAction(IAction action) {
		try {
			String cmdPattern = EasyExplorePlugin.getDefault().getTerminal();
			CmdUtil.exec(cmdPattern, getSelectedFile());
		} catch (Throwable e) {
			EasyExplorePlugin.log(e);
		}
	}

}
