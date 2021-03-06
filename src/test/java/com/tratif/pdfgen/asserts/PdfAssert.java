/**
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.asserts;

import com.tratif.pdfgen.asserts.helpers.PDFContentProvider;
import com.tratif.pdfgen.exceptions.PdfgenException;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PdfAssert extends AbstractAssert<PdfAssert, byte[]> {

	private static final String TEMP_FILE_PREFIX = "pdfgen";

	PdfAssert(byte[] bytes) {
		super(bytes, PdfAssert.class);
	}

	public static PdfAssert assertThat(byte[] bytes) {
		return new PdfAssert(bytes);
	}

	public PdfAssert hasMinimumLength(int length) {
		isNotNull();
		Assertions.assertThat(actual.length).as("Unexpected PDF content length")
				.isGreaterThan(length);

		return this;
	}

	public PdfAssert isProperPdfFile() {
		isNotNull();
		hasMinimumLength(4);

		//header
		Assertions.assertThat(actual[0]).isEqualTo((byte) 0x25); // %
		Assertions.assertThat(actual[1]).isEqualTo((byte) 0x50); // P
		Assertions.assertThat(actual[2]).isEqualTo((byte) 0x44); // D
		Assertions.assertThat(actual[3]).isEqualTo((byte) 0x46); // F
		Assertions.assertThat(actual[4]).isEqualTo((byte) 0x2D); // -

		if (actual[5] == (byte) 0x31 && actual[6] == (byte) 0x2E && actual[7] == (byte) 0x33) // version is 1.3 ?
		{
			// file terminator
			Assertions.assertThat(actual[actual.length - 7]).isEqualTo((byte) 0x25); // %
			Assertions.assertThat(actual[actual.length - 6]).isEqualTo((byte) 0x25); // %
			Assertions.assertThat(actual[actual.length - 5]).isEqualTo((byte) 0x45); // E
			Assertions.assertThat(actual[actual.length - 4]).isEqualTo((byte) 0x4F); // O
			Assertions.assertThat(actual[actual.length - 3]).isEqualTo((byte) 0x46); // F
			Assertions.assertThat(actual[actual.length - 2]).isEqualTo((byte) 0x20); // SPACE
			Assertions.assertThat(actual[actual.length - 1]).isEqualTo((byte) 0x0A); // EOL
			return this;
		}

		if (actual[5] == (byte) 0x31 && actual[6] == (byte) 0x2E && actual[7] == (byte) 0x34) // version is 1.4 ?
		{
			// file terminator
			Assertions.assertThat(actual[actual.length - 6]).isEqualTo((byte) 0x25); // %
			Assertions.assertThat(actual[actual.length - 5]).isEqualTo((byte) 0x25); // %
			Assertions.assertThat(actual[actual.length - 4]).isEqualTo((byte) 0x45); // E
			Assertions.assertThat(actual[actual.length - 3]).isEqualTo((byte) 0x4F); // O
			Assertions.assertThat(actual[actual.length - 2]).isEqualTo((byte) 0x46); // F
			Assertions.assertThat(actual[actual.length - 1]).isEqualTo((byte) 0x0A); // EOL
			return this;
		}

		Assertions.fail("Not proper pdf file");
		return this;
	}

	public PdfAssert contains(String str) {
		isNotNull();

		File file = writeActualToTempPdf();
		try (PDFContentProvider provider = new PDFContentProvider(file)) {
			Assertions.assertThat(provider.getText())
					.containsSubsequence(str);
		}

		file.delete();
		return this;
	}

	public PdfAssert hasPagesCount(int count) {
		isNotNull();

		File file = writeActualToTempPdf();
		try (PDFContentProvider provider = new PDFContentProvider(file)) {
			Assertions.assertThat(provider.getPagesCount())
					.isEqualTo(count);
		}

		file.delete();
		return this;
	}

	public PdfAssert containsImage(BufferedImage img) {
		isNotNull();

		File file = writeActualToTempPdf();
		List<BufferedImage> images;
		try (PDFContentProvider provider = new PDFContentProvider(file)) {
			images = provider.getAllImages();
		}

		boolean contains = false;

		for (BufferedImage pdfImg : images) {
			if (compareImages(pdfImg, img))
				contains = true;
		}

		assertTrue(contains);

		file.delete();
		return this;
	}

	private File writeActualToTempPdf() {
		File file;
		try {
			file = File.createTempFile(TEMP_FILE_PREFIX, ".pdf");
			Files.write(file.toPath(), actual);
		} catch (IOException e) {
			throw new PdfgenException("Failed to withPage temp file or write to it.", e);
		}

		return file;
	}

	private boolean compareImages(BufferedImage first, BufferedImage second) {
		if (first.getWidth() != second.getWidth() || first.getHeight() != second.getHeight())
			return false;

		int width = first.getWidth();
		int height = first.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (first.getRGB(x, y) != second.getRGB(x, y)) {
					return false;
				}
			}
		}

		return true;
	}
}
