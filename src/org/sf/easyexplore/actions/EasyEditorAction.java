package org.sf.easyexplore.actions;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.sf.easyexplore.EasyExplorePlugin;

import util.CmdUtil;

/**
 * 有个小问题，当同时用系统的编辑器打开两个以上项目中的文件时
 * 
 * @author jixiuf
 *
 */
public class EasyEditorAction extends EasyBaseAction {
 
	public void runAction(IAction action) {
		try {
			String cmdPattern = EasyExplorePlugin.getDefault().getEditorCmd();
			File selectedFile = getSelectedFile();

			// usually editor in windows can't edit a dir ,so ,if you select a
			// dir but file ,do noting
			if (System.getProperty("os.name").toLowerCase().contains("windows")
					&& selectedFile != null && selectedFile.exists()
					&& selectedFile.isDirectory()) {
				return;
			}

			Process p = CmdUtil.exec(cmdPattern, getSelectedFile());
			isEditorProcessRunning = true;
			new Thread(new WaitForExitOfEditorProcess(p)).start();
			new Thread(new RefreshWorkSpaceBeforeEditorProcessExit()).start();
		} catch (Throwable e) {
			EasyExplorePlugin.log(e);
		}

	}

	boolean isEditorProcessRunning = false;

	// start a thread wait for the exit of the new process
	// after that ,it will try refresh the project and rebuild
	class WaitForExitOfEditorProcess implements Runnable {
		Process p = null;

		public WaitForExitOfEditorProcess(Process p) {
			this.p = p;
		}

		public void run() {
			try {
				p.waitFor();
				isEditorProcessRunning = false;
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
						IResource.DEPTH_INFINITE, null);
				ResourcesPlugin.getWorkspace().build(
						IncrementalProjectBuilder.CLEAN_BUILD, null);
			} catch (InterruptedException e) {
				isEditorProcessRunning = false;
				e.printStackTrace();
			} catch (CoreException e) {
				isEditorProcessRunning = false;
				e.printStackTrace();
			} finally {
				isEditorProcessRunning = false;
			}

		}

	}

	// while the editorProcess is still running
	// refresh workspace every 30s
	class RefreshWorkSpaceBeforeEditorProcessExit implements Runnable {
		int frequence = 30 * 1000;

		public RefreshWorkSpaceBeforeEditorProcessExit(int seconds) {
			this.frequence = seconds * 1000;
		}

		public RefreshWorkSpaceBeforeEditorProcessExit() {

		}

		public void run() {
			while (isEditorProcessRunning) {
				try {
					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
							IResource.DEPTH_INFINITE, null);
					Thread.sleep(frequence);
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}



}
