package com.sun.codemodel.tests;

import com.sun.codemodel.writer.SingleStreamCodeWriter;
import org.junit.Assert;
import org.junit.Test;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.tests.util.CodeModelTestsUtils;

import java.io.IOException;

public class InnerClassTest {

	@Test
	public void innerClassesAreImported() throws JClassAlreadyExistsException, IOException {
		JCodeModel codeModel = new JCodeModel();
		JDefinedClass aClass = codeModel._class("org.test.DaTestClass");
		JDefinedClass daInner = aClass._class("Inner");
		daInner.method(JMod.PUBLIC, aClass, "doSomething");

		Assert.assertEquals("org.test.DaTestClass.Inner", daInner.fullName());
		Assert.assertEquals("org.test.DaTestClass$Inner", daInner.binaryName());
		Assert.assertEquals("Inner", daInner.name());

		aClass.method(JMod.PUBLIC, daInner, "getInner");

		final JDefinedClass otherClass = codeModel._class("org.test.OtherClass");
		otherClass.method(JMod.PUBLIC, daInner, "getInner");
		otherClass.method(JMod.PUBLIC, aClass, "getOuter");

		codeModel.build(new SingleStreamCodeWriter(System.out));
	}
}
