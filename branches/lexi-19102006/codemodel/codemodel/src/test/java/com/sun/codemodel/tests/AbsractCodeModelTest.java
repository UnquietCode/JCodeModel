package com.sun.codemodel.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.tests.util.FileUtils;

public abstract class AbsractCodeModelTest extends TestCase {

	private File test;

	private File target;

	private File src;

	private File resources;

	@Override
	protected void setUp() throws Exception {
		test = getTestDir();
		target = getTargetDir();
		src = getSrcDir();
		src.mkdirs();
		resources = getResourcesDir();
		resources.mkdirs();
	}

	@Override
	protected void tearDown() throws Exception {
		FileUtils.deleteDirectory(target);
		FileUtils.deleteDirectory(src);
		FileUtils.deleteDirectory(resources);
	}

	public void testGeneration() throws Exception {
		final JCodeModel codeModel = new JCodeModel();
		generate(codeModel);
		build(codeModel);
		check();
	}

	protected void build(final JCodeModel codeModel) throws IOException {
		codeModel.build(src, resources);
	}

	protected abstract void generate(JCodeModel codeModel) throws Exception;

	protected void check() throws Exception {
		FileUtils.contentsEqual(target, test, true);
	}

	public Class getTestClass() {
		return getClass();
	}

	public File getTargetDir() {
		return new File(getBaseDir(), "target" + File.separator
				+ "generated-test-sources" + File.separator
				+ getTestClass().getName());
	}

	public File getTestDir() {
		return new File(getBaseDir(), "src" + File.separator + "test"
				+ File.separator + "test-sources" + File.separator
				+ getTestClass().getName());
	}

	public File getSrcDir() {
		return new File(getTargetDir(), "src");
	}

	public File getResourcesDir() {
		return new File(getTargetDir(), "resources");
	}

	protected File getBaseDir() {
		try {
			return (new File(getClass().getProtectionDomain().getCodeSource()
					.getLocation().toURI())).getParentFile().getParentFile()
					.getAbsoluteFile();
		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}
}
