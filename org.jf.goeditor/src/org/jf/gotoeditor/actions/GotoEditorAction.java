package org.jf.gotoeditor.actions;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.jf.gotoeditor.GotoExplorePlugin;

import util.CmdUtil;

/**
 *
 * @author jixiuf
 *
 */
public class GotoEditorAction extends GotoBaseAction {

	public void runAction(IAction action) {
		try {
			String cmdPattern = GotoExplorePlugin.getDefault().getEditorCmd();
			File selectedFile = getSelectedFile();
            if (selectedFile==null) {
            	MessageDialog.openInformation(new Shell(), "GotoEditor",
    						"Unable to run this command (please select a file first)" );         	
                return;
            }

			// usually editor in windows can't edit a dir ,so ,if you select a
			// dir but file ,do noting
			// if
			// (System.getProperty("os.name").toLowerCase().contains("windows")
			// && selectedFile != null && selectedFile.exists()
			// && selectedFile.isDirectory()) {
			// return;
			// }

            GotoExplorePlugin.log(selectedFile.toString());
			Process p = CmdUtil.exec(cmdPattern, selectedFile);
			process.add(p);
            
			new Thread(new WaitForExitOfEditorProcess(p, selectedFile)).start();
			new Thread(new RefreshWorkSpaceBeforeEditorProcessExit(
					selectedFile, p)).start();
		} catch (Throwable e) {
			GotoExplorePlugin.log(e);
		}

	}

	/**
	 * refresh f ,<br/> f can be a file ,or if it is a file ,reload it ,if it
	 * is a directory ,reload all files under this directory
	 *
	 * @param f
	 */
	public void refresh(File f) {
		try {
			if (f.isFile()) {
				IFile[] iFiles = ResourcesPlugin.getWorkspace().getRoot()
						.findFilesForLocationURI(f.toURI());
				for (IFile ifile : iFiles) {
					ifile.refreshLocal(IResource.DEPTH_ZERO, null);
				}
			} else {
				IContainer[] containers = ResourcesPlugin.getWorkspace()
						.getRoot().findContainersForLocationURI(f.toURI());
				for (IContainer ct : containers) {
					ct.refreshLocal(IResource.DEPTH_INFINITE, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Set<Process> process = new HashSet<Process>();

	// start a thread wait for the exit of the new process
	// after that ,it will try refresh the project and rebuild
	class WaitForExitOfEditorProcess implements Runnable {
		Process p = null;

		File selectedFile;

		public WaitForExitOfEditorProcess(Process p, File selectedFile) {
			this.p = p;
			this.selectedFile = selectedFile;
		}

		public void run() {
			try {
				p.waitFor();
				refresh(selectedFile);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				process.remove(p);
			}
		}
	}

	// while the editorProcess is still running
	// refresh workspace every 2s
	class RefreshWorkSpaceBeforeEditorProcessExit implements Runnable {
		int frequence = 2000;

		Process p;

		File selectedFile;

		public RefreshWorkSpaceBeforeEditorProcessExit(int seconds,
				File selectedfile, Process p) {
			this(selectedfile, p);
			this.frequence = seconds * 1000;
		}

		public RefreshWorkSpaceBeforeEditorProcessExit(File selectedFile,
				Process p) {
			this.selectedFile = selectedFile;
			this.p = p;
		}

		public void run() {
			while (process.contains(p)) {
				try {
                  
					refresh(selectedFile);
                
					// ResourcesPlugin.getWorkspace().getRoot().findMember()
					// ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
					// IResource.DEPTH_INFINITE, null);
					if (selectedFile.isDirectory()) {
						Thread.sleep(frequence * 5);
					} else {
						Thread.sleep(frequence);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
