package com.sun.codemodel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben Fagin
 * @version 2013-04-01
 */
public class NameUtilities {

	public static String getFullName(Class c) {
		if (c == null) {
			throw new IllegalArgumentException("class cannot be null");
		}

		StringBuilder name = new StringBuilder();
		name.append(c.getPackage().getName()).append(".");

		Class klaus = c;
		List<Class> enclosingClasses = new ArrayList<Class>();

		while ((klaus = klaus.getEnclosingClass()) != null) {
			enclosingClasses.add(klaus);
		}

		for (int i = enclosingClasses.size() - 1; i >= 0; i--) {
			name.append(enclosingClasses.get(i).getSimpleName()).append(".");
		}

		name.append(c.getSimpleName());
		return name.toString();
	}
}
