package com.sun.codemodel.tests.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileUtils {

	/**
	 * Recursively delete a directory.
	 * 
	 * @param directory
	 *            directory to delete
	 * @throws IOException
	 *             in case deletion is unsuccessful
	 */
	public static void deleteDirectory(File directory) throws IOException {
		if (!directory.exists()) {
			return;
		}

		cleanDirectory(directory);
		if (!directory.delete()) {
			String message = "Unable to delete directory " + directory + ".";
			throw new IOException(message);
		}
	}

	/**
	 * Clean a directory without deleting it.
	 * 
	 * @param directory
	 *            directory to clean
	 * @throws IOException
	 *             in case cleaning is unsuccessful
	 */
	public static void cleanDirectory(File directory) throws IOException {
		if (!directory.exists()) {
			String message = directory + " does not exist";
			throw new IllegalArgumentException(message);
		}

		if (!directory.isDirectory()) {
			String message = directory + " is not a directory";
			throw new IllegalArgumentException(message);
		}

		File[] files = directory.listFiles();
		if (files == null) { // null if security restricted
			throw new IOException("Failed to list contents of " + directory);
		}

		IOException exception = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				forceDelete(file);
			} catch (IOException ioe) {
				exception = ioe;
			}
		}

		if (null != exception) {
			throw exception;
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Delete a file. If file is a directory, delete it and all sub-directories.
	 * <p>
	 * The difference between File.delete() and this method are:
	 * <ul>
	 * <li>A directory to be deleted does not have to be empty.</li>
	 * <li>You get exceptions when a file or directory cannot be deleted.
	 * (java.io.File methods returns a boolean)</li>
	 * </ul>
	 * 
	 * @param file
	 *            file or directory to delete, not null
	 * @throws NullPointerException
	 *             if the directory is null
	 * @throws IOException
	 *             in case deletion is unsuccessful
	 */
	public static void forceDelete(File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectory(file);
		} else {
			if (!file.exists()) {
				throw new FileNotFoundException("File does not exist: " + file);
			}
			if (!file.delete()) {
				String message = "Unable to delete file: " + file;
				throw new IOException(message);
			}
		}
	}

	public static boolean contentsEqual(File f1, File f2,
			boolean ignoreWhitespace) throws IOException {
		if (f1 == f2)
			return true;

		if (f1 == null && f2 == null)
			return true;

		if (f1 == null || f2 == null)
			return false;

		if (f1.exists() != f2.exists() || f1.isFile() != f2.isFile()
				|| f1.isDirectory() != f2.isDirectory())
			return false;

		if (!f1.exists())
			return true;

		if (f1.isDirectory()) {
			final File[] files1 = f1.listFiles();
			for (final File file1 : files1) {
				final File file2 = new File(f2, file1.getName());
				if (!contentsEqual(file1, file2, ignoreWhitespace))
					return false;
			}
			// TODO same quantity of files
			return true;
		} else {
			Reader r1 = null;
			Reader r2 = null;
			try {
				r1 = new FileReader(f1);
				r2 = new FileReader(f2);
				if (!IOUtils.contentsEqual(r1, r2, ignoreWhitespace))
					return false;
			} finally {
				try {
					if (r1 != null)
						r1.close();
				} catch (IOException ignored) {
				}
				try {
					if (r2 != null)
						r2.close();
				} catch (IOException ignored) {
				}
			}
			return true;
		}
	}
}
