package com.tratif.pdfgen.document.docs;

import com.tratif.pdfgen.utils.FileNameGenerator;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Map;

import static java.util.Objects.isNull;

public class HtmlTemplate {

	private File file;
	private InputStream inputStream;
	private Map<String, Object> params;

	public HtmlTemplate(File file, Map<String, Object> params) {
		this.file = file;
		this.params = params;
	}

	public HtmlTemplate(InputStream inputStream, Map<String, Object> params) {
		this.inputStream = inputStream;
		this.params = params;
	}

	public File asFile() throws IOException {
		if (isNull(file)) {
			file = FileNameGenerator.asFile("html");
			FileUtils.copyInputStreamToFile(inputStream, file);
		}

		return file;
	}

	public InputStream asInputStream() throws FileNotFoundException {
		if (!isNull(inputStream)) {
			inputStream = new FileInputStream(file);
		}

		return inputStream;
	}

	public Map<String, Object> getParams() {
		return params;
	}
}
