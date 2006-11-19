package com.sun.codemodel.tests;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

public class JAnnotationArrayMemberTest extends AbsractCodeModelTest {

	@Override
	protected void generate(JCodeModel codeModel) throws Exception {

		final JDefinedClass test = codeModel._class("Test");
		final JAnnotationUse a = test.annotate(A.class);

		a.paramArray("booleanArrayField").param(true).param(false);
		a.paramArray("byteArrayField").param((byte) 0).param((byte) 1);
		a.paramArray("charArrayField").param('a').param('b');
		a.paramArray("doubleArrayField").param(0.1).param(2.3);
		a.paramArray("floatArrayField").param(0.1f).param(2.3f);
		a.paramArray("intArrayField").param(4).param(5);
		a.paramArray("longArrayField").param(4L).param(5L);
		a.paramArray("shortArrayField").param((short) 6).param((short) 7);
		a.paramArray("enumArrayField").param(B.ONE).param(B.TWO);
		a.paramArray("stringArrayField").param("three").param("four");
		a.paramArray("classArrayField").param(String.class).param(Long.class);

	}

	public static @interface A {

		public boolean[] booleanArrayField();

		public byte[] byteArrayField();

		public char[] charArrayField();

		public double[] doubleArrayField();

		public float[] floatArrayField();

		public int[] intArrayField();

		public long[] longArrayField();

		public short[] shortArrayField();

		public B[] enumArrayField();

		public String[] stringArrayField();

		public Class[] classArrayField();
	}

	public static enum B {
		ONE, TWO;
	}
}
