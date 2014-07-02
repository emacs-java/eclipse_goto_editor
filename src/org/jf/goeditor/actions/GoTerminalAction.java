package org.jf.goeditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionDelegate;
import org.jf.goeditor.GoExplorePlugin;

import util.CmdUtil;

public class GoTerminalAction extends GoBaseAction {

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void runAction(IAction action) {
		try {
			String cmdPattern = GoExplorePlugin.getDefault().getTerminal();
			CmdUtil.exec(cmdPattern, getSelectedFile());
		} catch (Throwable e) {
			GoExplorePlugin.log(e);
		}
	}

}
