package com.sun.codemodel.tests;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;

public class JDirectClassRefTest {

	@Test
	public void testDirectClassRef() throws JClassAlreadyExistsException {
		final JCodeModel codeModel = new JCodeModel();

		assertSame("Should be same JClass", codeModel.ref("org.test.ClassNotOnClasspath"), codeModel.ref("org.test.ClassNotOnClasspath"));
	}
}
