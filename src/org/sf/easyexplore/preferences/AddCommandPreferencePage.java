package org.sf.easyexplore.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sf.easyexplore.EasyExplorePlugin;

/**
 * @author Celinio Fernandes New command class
 * 
 */

public class AddCommandPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	// CF: the identifiers for the preferences
	public static final String P_MyCmd = "org.sf.easyexplore.myCmdPreference";

	public static final String P_MyCmd_TITLE = "org.sf.easyexplore.myCmdPreference";

	public AddCommandPreferencePage() {
		super(GRID);
		setPreferenceStore(EasyExplorePlugin.getDefault().getPreferenceStore());
		setDescription("Define your own command here,\nfor example <notepad %f>  or <explorer %d>\n%f is the file you selected (maybe a dir),\n%d is the dir your selected ,but when you select a file not dir ,then %d means the parent dir\n"
				+ "\nwhen you select nothing ,\n       %f means the file opened in the actived editor\n       %d means the parent dir of the file,\nwhen no editor is actived it do nothing\n" +
						"\nwhen both %f and %d don't exists in the command filed ,%f will be appended ");

	}

	/**
	 * CF: Method to add the field editors to the page
	 * 
	 */
	public void createFieldEditors() {
		addField(new StringFieldEditor(P_MyCmd_TITLE, "&Title:",
				getFieldEditorParent()));
		addField(new StringFieldEditor(P_MyCmd, "&Cmd:", getFieldEditorParent()));

	}

	public void init(IWorkbench workbench) {
	}
}