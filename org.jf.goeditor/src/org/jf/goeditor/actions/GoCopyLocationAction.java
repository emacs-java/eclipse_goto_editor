package org.jf.goeditor.actions;


import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.jf.goeditor.CopyFullyQualifiedClassNameCore;
import org.jf.goeditor.Messages;

/**
 * @author Markus Gebhard
 */
public abstract class GoCopyLocationAction implements IActionDelegate {

  private IWorkbenchPart activePart;
  private Clipboard clipboard;

  protected final void setActivePart(IWorkbenchPart activePart) {
    this.activePart = activePart;
  }

  public final void run(IAction action) {
    String fullyQualifiedClassName;
    try {
      fullyQualifiedClassName = CopyFullyQualifiedClassNameCore
          .getFullyQualifiedClassName(getSelectedObject());
    } catch (JavaModelException e) {
      MessageDialog
          .openError(
              getShell(),
              Messages.getString("CopyFullyQualifiedClassNameAction.error.dialog.title"), //$NON-NLS-1$
              Messages
                  .getString("CopyFullyQualifiedClassNameAction.error.dialog.unable.to.retrieve.classname.message")); //$NON-NLS-1$
      return;
    }
    if (fullyQualifiedClassName == null) {
      MessageDialog
          .openError(
              getShell(),
              Messages.getString("CopyFullyQualifiedClassNameAction.error.dialog.title"), //$NON-NLS-1$
              Messages
                  .getString("CopyFullyQualifiedClassNameAction.error.dialog.select.java.type.message")); //$NON-NLS-1$
      return;
    }
    copyToClipboard(fullyQualifiedClassName);
  }

  private void copyToClipboard(String text) {
    Clipboard clipboard = getClipboard();
    try {
      clipboard.setContents(new Object[]{ text }, new Transfer[]{ TextTransfer.getInstance() });
    } catch (SWTError e) {
      if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
        throw e;
      }
      if (MessageDialog.openQuestion(getShell(), Messages
          .getString("CopyToClipboardProblemDialog.title"), Messages //$NON-NLS-1$
          .getString("CopyToClipboardProblemDialog.message"))) { //$NON-NLS-1$
        copyToClipboard(text);
      }
    }
  }

  private synchronized Clipboard getClipboard() {
    if (clipboard == null) {
      clipboard = new Clipboard(getShell().getDisplay());
    }
    return clipboard;
  }

  protected abstract Object getSelectedObject() throws JavaModelException;

  protected Shell getShell() {
    return activePart.getSite().getShell();
  }
}
