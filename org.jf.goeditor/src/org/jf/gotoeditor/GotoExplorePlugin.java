package org.jf.gotoeditor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.jf.gotoeditor.preferences.AddCommandPreferencePage;
import org.jf.gotoeditor.preferences.GotoExplorePreferencePage;
import org.osgi.framework.BundleContext;

/**
 * Added support for a comamnd prompt and updated the default for openining
 * Explorer in right folder. Thanks to Chris Gittings and anonymous on page :
 * http://eclipse-plugins.info/eclipse/plugin_comments.jsp?id=192
 */
public class GotoExplorePlugin extends AbstractUIPlugin {
    // The shared instance.
    private static GotoExplorePlugin plugin;

    private ResourceBundle resourceBundle;

    public GotoExplorePlugin() {
        plugin = this;
        try {
            resourceBundle = ResourceBundle
                    .getBundle("org.jf.goeditor.GoExplorePluginResources");
        } catch (MissingResourceException x) {
            // throw new NullPointerException("unnable to load resources
            // org.jf.goeditor.GoExplorePluginResources");
        }
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static GotoExplorePlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the workspace instance.
     */
    public static IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = GotoExplorePlugin.getDefault()
                .getResourceBundle();
        String res = null;
        try {
            res = bundle.getString(key);
        } catch (MissingResourceException e) {
            res = key;
        }
        return res;
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    static public void log(Object msg) {
        ILog log = GotoExplorePlugin.getDefault().getLog();
        Status status = new Status(IStatus.INFO, GotoExplorePlugin.getDefault()
                .getBundle().getSymbolicName(), IStatus.ERROR, msg + "\n", null);

        log.log(status);
    }

    static public void log(String[] msg) {
        StringBuffer strb = new StringBuffer();
        for (String m : msg) {
            strb.append(m);
        }
        ILog log = GotoExplorePlugin.getDefault().getLog();
        Status status = new Status(IStatus.INFO, GotoExplorePlugin.getDefault()
                .getBundle().getSymbolicName(), IStatus.ERROR, strb.toString()
                + "\n", null);
        log.log(status);
    }

    static public void log(Throwable ex) {
        ILog log = GotoExplorePlugin.getDefault().getLog();
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        String msg = stringWriter.getBuffer().toString();
        Status status = new Status(IStatus.ERROR, GotoExplorePlugin
                .getDefault().getBundle().getSymbolicName(), IStatus.ERROR,
                msg, null);
        log.log(status);
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeDefaultPreferences(org.eclipse.jface.preference.IPreferenceStore)
     * @author Celinio Fernandes : method to set up the default values for the
     *         preferences for the plug-in
     *
     */
    protected void initializeDefaultPreferences(IPreferenceStore store) {
        String osName = System.getProperty("os.name");

        String defaultExplorer = "filemanager_open_command %d";
        if (osName.indexOf("Windows") != -1) {
            defaultExplorer = "explorer.exe /n, /e, %d";
        } else if (osName.indexOf("Mac") != -1) {
            defaultExplorer = "open %d";
        } else if (osName.indexOf("nux") != -1) {
            defaultExplorer = "nautilus %d";

        }
        store.setDefault(org.jf.gotoeditor.preferences.GotoExplorePreferencePage.P_EXPLORER, defaultExplorer);
        String defaultEditor = "SystemEditorName %f";
        if (osName.indexOf("Windows") != -1) {
            defaultEditor = "notepad.exe %f";
        } else if (osName.indexOf("Mac") != -1) {
            defaultEditor = "open -W -n -a /Applications/TextEdit.app %f";
        } else if (osName.indexOf("nux") != -1) {
            String editor = System.getenv("EDITOR");
            if (editor == null || "".equals(editor))
                editor = "gvim";
            defaultEditor = editor + "   %f";
        }
        store.setDefault(org.jf.gotoeditor.preferences.GotoExplorePreferencePage.P_EDITOR, defaultEditor);

        String defaultTerminal = "shell_open_command %d";
        if (osName.indexOf("Windows") != -1) {
            defaultTerminal = "cmd /C start cmd /K cd /d %d";
        } else if (osName.indexOf("Mac") != -1) {
            defaultTerminal = "open -a iTerm %d";
        } else if (osName.indexOf("nux") != -1) {
            defaultTerminal = "urxvtc -cd  %d";

        }
        store.setDefault(GotoExplorePreferencePage.P_TERMINAL, defaultTerminal);

        String defaultMyCmd = "Type  here your command";
        store.setDefault(AddCommandPreferencePage.P_MyCmd, defaultMyCmd);
    }

    /**
     * Return the target explorer program configured in
     * GotoExplorePreferencePage.
     *
     * @return String
     */
    public String getExplorer() {
        return getPreferenceStore().getString(
                GotoExplorePreferencePage.P_EXPLORER);
    }

    /**
     * Return the target command program configured in
     * GotoExplorePreferencePage.
     *
     * @return String
     */
    public String getTerminal() {
        String cmdPattern = getPreferenceStore().getString(
                GotoExplorePreferencePage.P_TERMINAL);

        return cmdPattern;
    }

    public String getEditorCmd() {
        String cmdPattern = getPreferenceStore().getString(
                GotoExplorePreferencePage.P_EDITOR);
        return cmdPattern;
    }

    /**
     * @author: Celinio Fernandes Return the target random program configured in
     *          GotoExplorePreferencePage.
     *
     * @return String
     */
    public String getMyCmd() {
        String cmdPattern = getPreferenceStore().getString(
                AddCommandPreferencePage.P_MyCmd);
        return cmdPattern;
    }

    /**
     * Tells whether this platform is currently supported. The implementation of
     * this method must be in sync with the implementation of <a
     * href="#initializeDefaultPreferences(IPreferenceStore)"
     * >initializeDefaultPreferences(IPreferenceStore)</a>.
     */
    // public boolean isSupported() {
    // String osName = System.getProperty("os.name").toLowerCase();
    // return ((osName.indexOf("windows") != -1) || (osName.indexOf("mac") !=
    // -1));
    // }
}
