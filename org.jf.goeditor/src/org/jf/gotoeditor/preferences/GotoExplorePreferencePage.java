package org.jf.gotoeditor.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.jf.gotoeditor.GotoExplorePlugin;

/**
 * @author Celinio Fernandes Preferences class for the commands executed by the
 *         Go plug-in
 *
 */

public class GotoExplorePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	// CF: the identifiers for the preferences
	public static final String P_EXPLORER = "org.jf.gotoeditor.targetPreference";
	public static final String P_EDITOR = "org.jf.gotoeditor.editorPreference";
	public static final String P_TERMINAL = "org.jf.gotoeditor.terminalPreference";

	/*
	 * CF: set a preference store for the preference page
	 */
	public GotoExplorePreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		setPreferenceStore(GotoExplorePlugin.getDefault().getPreferenceStore());
		setDescription("Set the operating system commands to open an Explorer and a Terminal . and a Editor to edit the selected file");
	}

	/**
	 * CF: Method to add the field editors to the page
	 *
	 */
	public void createFieldEditors() {
		addField(new StringFieldEditor(P_EXPLORER, "&Explorer:",
				getFieldEditorParent()));
		addField(new StringFieldEditor(P_TERMINAL, "&Terminal:",
				getFieldEditorParent()));

		addField(new StringFieldEditor(P_EDITOR, "&Editor:",
				getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}
}
