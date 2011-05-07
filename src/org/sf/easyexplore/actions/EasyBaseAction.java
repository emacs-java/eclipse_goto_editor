package org.sf.easyexplore.actions;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.sf.easyexplore.EasyExplorePlugin;

public  abstract  class   EasyBaseAction     implements IObjectActionDelegate,
		IWorkbenchWindowActionDelegate   {

	protected Object selected = null;

	protected Class  selectedClass = null;

	public void run(IAction action) {
	 
	 	runAction(action);
	}

 

	public void setActivePart(IAction action, IWorkbenchPart workbenchpart) {

	}

 	public abstract void runAction(IAction action);

	// if the selection is a file ,then return the file ,if not return the file
	// in actived editor ,or null

	public File getSelectedFile() {
 
		File file = null;
		if (selected instanceof IResource) {
			file = new File(((IResource) selected).getLocation().toOSString());
		} else if (selected instanceof File) {
			file = (File) selected;
		}
		if ("unknown".equals(selected) || null == selected) {
			IFile activedEditorFile = getActiveEditorFile();
			
			
			if (activedEditorFile != null) {
				file = activedEditorFile.getLocation().toFile();

				return file;
			} else {
				MessageDialog.openInformation(new Shell(), "EasyExplore",
						"Unable to run command " + selectedClass.getName());
				EasyExplorePlugin.log("Unable to run command " + selectedClass);
				return null;
			}
		}
		return file;

	}

	// get the Ifile represent the file in actived editor
	public IFile getActiveEditorFile() {
		IFile file = null;
		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor != null) {
			IEditorInput input = editor.getEditorInput();
			FileEditorInput i = (FileEditorInput) input;
			file = i.getFile();
		}
		return file;
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		try {
			IAdaptable adaptable = null;
			this.selected = "unknown";
			if (selection instanceof IStructuredSelection) {
				adaptable = (IAdaptable) ((IStructuredSelection) selection)
						.getFirstElement();
				if (adaptable != null) {

					this.selectedClass = adaptable.getClass();
					if (adaptable instanceof IResource) {
						this.selected = (IResource) adaptable;
					} else if (adaptable instanceof PackageFragment
							&& ((PackageFragment) adaptable)
									.getPackageFragmentRoot() instanceof JarPackageFragmentRoot) {
						this.selected = getJarFile(((PackageFragment) adaptable)
								.getPackageFragmentRoot());
					} else if (adaptable instanceof JarPackageFragmentRoot) {
						this.selected = getJarFile(adaptable);
					} else {
						this.selected = (IResource) adaptable
								.getAdapter(IResource.class);
					}
				}

			}
		} catch (Throwable e) {
			EasyExplorePlugin.log(e);
		}
	}

	protected File getJarFile(IAdaptable adaptable) {
		JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot) adaptable;
		File selected = (File) jpfr.getPath().makeAbsolute().toFile();
		if (!((File) selected).exists()) {
			File projectFile = new File(jpfr.getJavaProject().getProject()
					.getLocation().toOSString());
			selected = new File(projectFile.getParent() + selected.toString());
		}
		return selected;
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow arg0) {
		 
	}	 

}
