package com.tratif.pdfgen.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ToFileConverter {

	public static File convert(String content, String fileExtension) throws IOException {
		File file = FileNameGenerator.asFile(fileExtension);
		Files.write(file.toPath(), content.getBytes());
		return file;
	}
}
