package com.sun.codemodel.tests;

import org.junit.Test;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

public class DefaultMethodTest {

	@Test
	public void defaultMethodOnAnInterface() throws Exception {
		JCodeModel cm = new JCodeModel();
		JDefinedClass cls = cm._class(JMod.PUBLIC, "TestInterface", ClassType.INTERFACE);

		cls.method(JMod.DEFAULT, cm.VOID, "defaultFoo");

		cm.build(new SingleStreamCodeWriter(System.out));
	}
}
