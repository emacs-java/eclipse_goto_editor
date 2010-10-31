package org.sf.easyexplore;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Markus Gebhard
 */
public class Messages {
  private static final String BUNDLE_NAME = "rg.sf.easyexplore.messages";//$NON-NLS-1$

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
