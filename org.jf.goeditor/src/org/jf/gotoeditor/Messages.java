package org.jf.gotoeditor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Markus Gebhard
 */
public class Messages {
  private static final String BUNDLE_NAME = "org.jf.gotoeditor.messages";//$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private Messages() {
  }

  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
         }
  }
}
