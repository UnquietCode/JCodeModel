package com.sun.codemodel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ben Fagin
 * @version 2013-04-01
 */
public class NameUtilitiesTest {


	@Test
	public void testInnerClassNaming() {
		String expected =
			NameUtilitiesTest.class.getPackage().getName()+"."
		  + NameUtilitiesTest.class.getSimpleName()+"."
		  + "Inner"
		;

		String name = NameUtilities.getFullName(Inner.class);
		Assert.assertEquals(expected, name);
	}

	public static class Inner { }
}
