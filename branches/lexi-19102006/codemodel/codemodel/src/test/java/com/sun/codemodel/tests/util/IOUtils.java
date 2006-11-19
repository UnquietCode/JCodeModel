package com.sun.codemodel.tests.util;

import java.io.IOException;
import java.io.Reader;

public class IOUtils {

	public static boolean contentsEqual(Reader r1, Reader r2, boolean ignoreWhitespace)
			throws IOException {
		if (r1 == r2)
			return true;

		if (r1 == null && r2 == null) // no byte contents
			return true;

		if (r1 == null || r2 == null) // only one has
			// contents
			return false;

		while (true) {
			int c1 = r1.read();
			while (ignoreWhitespace && isWhitespace(c1))
				c1 = r1.read();
			int c2 = r2.read();
			while (ignoreWhitespace && isWhitespace(c2))
				c2 = r2.read();
			if (c1 == -1 && c2 == -1)
				return true;
			if (c1 != c2)
				break;
		}

		return false;
	}

	private static boolean isWhitespace(int c) {
		if (c == -1)
			return false;
		return Character.isWhitespace((char) c);
	}

}
