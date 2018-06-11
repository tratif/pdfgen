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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BigFileGenerator {

	public static File generateHtml(long kb) {
		String line = "<p>CONTENT LINE</p>\n";

		try {
			File file = File.createTempFile("pdfgen", ".html");
			FileWriter fw = new FileWriter(file);

			for (long i = 0; i < kb; i++) {
				for (int j = 0; j < 1024 / line.length(); j++) {
					fw.write(line);
				}

				fw.flush();
			}
			fw.close();
			return file;
		} catch (IOException e) {
			throw new RuntimeException("Failed creating file.", e);
		}
	}

}
