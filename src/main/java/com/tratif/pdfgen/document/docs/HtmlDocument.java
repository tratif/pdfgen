package com.tratif.pdfgen.document.docs;

import com.tratif.pdfgen.utils.FileNameGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.StringJoiner;

import static java.util.Objects.isNull;

public class HtmlDocument {

	private File file;
	private InputStream inputStream;

	public HtmlDocument(File file) {
		this.file = file;
	}

	public HtmlDocument(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File asFile() throws IOException {
		if (isNull(file)) {
			file = FileNameGenerator.asFile("html");
			FileUtils.copyInputStreamToFile(inputStream, file);
		}

		return file;
	}

	//todo verify if that is needed maybe File is enough	
	public String location() {
		return file.getAbsolutePath();
	}

	public InputStream asInputStream() throws FileNotFoundException {
		if (!isNull(inputStream)) {
			inputStream = new FileInputStream(file);
		}

		return inputStream;
	}

	public String asString() throws IOException {
		if (isNull(file)) {
			return IOUtils.toString(inputStream, "UTF-8");
		}

		StringJoiner sj = new StringJoiner("");

		Files.readAllLines(file.toPath()).forEach(sj::add);

		return sj.toString();
	}
}
