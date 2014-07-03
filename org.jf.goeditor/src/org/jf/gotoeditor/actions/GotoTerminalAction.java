package org.jf.gotoeditor.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.jf.gotoeditor.GotoExplorePlugin;

import util.CmdUtil;

public class GotoTerminalAction extends GotoBaseAction {

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void runAction(IAction action) {
		try {
			String cmdPattern = GotoExplorePlugin.getDefault().getTerminal();
            File selectedFile =getSelectedFile();
            if (selectedFile==null) {
                MessageDialog.openInformation(new Shell(), "Goto Terminal",
                                              "Unable to run this command (please select a file or directory first)" );
                return;
            }
			CmdUtil.exec(cmdPattern,selectedFile );
		} catch (Throwable e) {
			GotoExplorePlugin.log(e);
		}
	}

}
