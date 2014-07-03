package org.jf.goeditor.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
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
            File selectedFile =getSelectedFile();
            if (selectedFile==null) {
                MessageDialog.openInformation(new Shell(), "Goto Terminal",
                                              "Unable to run this command (please select a file or directory first)" );
                return;
            }
			CmdUtil.exec(cmdPattern,selectedFile );
		} catch (Throwable e) {
			GoExplorePlugin.log(e);
		}
	}

}
