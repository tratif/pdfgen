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
