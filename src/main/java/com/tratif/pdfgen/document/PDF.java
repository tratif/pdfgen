/**
 * Copyright 2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.document;

import com.tratif.pdfgen.helpers.Utils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PDF {

	private InputStream content;

	public PDF(InputStream content) {
		this.content = content;
	}

	public PDF(byte[] content) {
		this.content = new ByteArrayInputStream(content);
	}

	public byte[] toByteArray() {
		try {
			return IOUtils.toByteArray(content);
		} catch (IOException e) {
			throw new RuntimeException("Failed reading input stream", e);
		}
	}

	public void toFile(File destination) {
		if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
			throw new RuntimeException("Following path does not exist: " + destination.getParentFile().toString());
		}

		try {
			Utils.writeStreamToFile(destination, content);
//            FileUtils.copyToFile(content, destination);
		} catch (IOException e) {
			throw new RuntimeException("Failed saving to file.", e);
		}
	}

	public InputStream toInputStream() {
		return content;
	}
}
