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
package com.tratif.pdfgen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfgenAssumptions {

	private static final Logger log = LoggerFactory.getLogger(PdfgenAssumptions.class);

	public static boolean isPdfEngineAvailable() {
		try {
			int exitCode = Runtime.getRuntime().exec("which chromehtml2pdf").waitFor();
			return exitCode == 0;
		} catch (Exception e) {
			log.debug("Exception raised when checking pdf engine presence", e);
			return false;
		}
	}
}
