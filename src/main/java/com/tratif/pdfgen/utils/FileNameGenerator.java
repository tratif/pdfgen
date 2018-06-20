package com.tratif.pdfgen.utils;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class FileNameGenerator {

	public static String asString(String extension) {
		return "/tmp/" + UUID.randomUUID() + "." + extension;
	}

	public static File asFile(String extension) {
		return new File(asString(extension));
	}

	public static Path asPath(String extension) {
		return asFile(extension).toPath();
	}
}
