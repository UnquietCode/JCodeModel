package com.sun.codemodel.tests;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

public class JAnnotationUseTest extends AbsractCodeModelTest {

	@Override
	protected void generate(JCodeModel codeModel) throws Exception {

		final JDefinedClass test = codeModel._class("Test");
		final JAnnotationUse a = test.annotate(A.class);

		a.param("classField", String.class);

		a.paramArray("classArrayField").param(String.class).param(Long.class);

	}

	public static @interface A {
		public Class<?> classField();

		public Class<?>[] classArrayField();
	}
}
