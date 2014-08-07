package com.sun.codemodel;

import com.sun.codemodel.writer.SingleStreamCodeWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertTrue;

/**
 * @author Ben Fagin
 */
public class ImportClassTest {


	@Test
	public void testCollisionDetectionForInnerClassNames() throws Exception {
		JCodeModel model = new JCodeModel();


		/*
			package my.test;

			public class AnotherThing {

				public interface Nothing() { }
			}
		 */
		JDefinedClass AnotherThing = model._class("my.test.AnotherThing");
		JDefinedClass AnotherThing_Nothing = AnotherThing._interface("Nothing");

		/*
			package my.test;

			public class Something {

				AnotherThing.Nothing hitIt();

				public interface Nothing { }
			}
		*/
		JDefinedClass Something = model._class("my.test.Something");
		JDefinedClass Something_Nothing = Something._interface("Nothing");
		JMethod method = Something.method(0, AnotherThing_Nothing, "hitIt");

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		model.build(new SingleStreamCodeWriter(os));
		String source = os.toString();


		// check that the method was generated using the FQCN
		assertTrue(source.contains("my.test.AnotherThing.Nothing hitIt()"));
	}

}


