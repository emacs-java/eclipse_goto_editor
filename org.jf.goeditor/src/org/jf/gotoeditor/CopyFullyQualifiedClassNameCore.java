package org.jf.gotoeditor;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * @author Markus Gebhard
 */
public class CopyFullyQualifiedClassNameCore {

  public static String getFullyQualifiedClassName(Object selectedObject) throws JavaModelException {
    IType selectedType = getType(selectedObject);
    if (selectedType == null) {
      return null;
    }
    return selectedType.getFullyQualifiedName();
  }

  private static IType getType(Object selectedObject) throws JavaModelException {
    if (selectedObject instanceof ICompilationUnit) {
      ICompilationUnit compilationUnit = (ICompilationUnit) selectedObject;
      IType[] types = compilationUnit.getTypes();
      if (types.length > 0) {
        return types[0];
      }
    }
    else if (selectedObject instanceof IType) {
      return (IType) selectedObject;
    }
    else if (selectedObject instanceof IMethod && ((IMethod) selectedObject).isConstructor()) {
      IMethod method = ((IMethod) selectedObject);
      return method.getDeclaringType();
    }

    //WorkbenchPlugin.log("CLASS: "+selectedObject.getClass());
    return null;
  }
}
