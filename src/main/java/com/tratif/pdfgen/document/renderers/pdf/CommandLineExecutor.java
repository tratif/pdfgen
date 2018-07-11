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
package com.tratif.pdfgen.document.renderers.pdf;

import com.tratif.pdfgen.exceptions.PdfgenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

class CommandLineExecutor {

	private static final Logger log = LoggerFactory.getLogger(CommandLineExecutor.class);

	private String cmd;
	private List<String> args;

	CommandLineExecutor() {
		args = new ArrayList<>();
	}

	CommandLineExecutor command(String cmd) {
		args = new ArrayList<>();
		this.cmd = cmd;
		return this;
	}

	CommandLineExecutor withArgument(String arg) {
		args.add(arg);
		return this;
	}

	Process execute() {
		Runtime runtime = Runtime.getRuntime();
		StringJoiner joiner = new StringJoiner(" ");
		joiner.add(cmd);
		args.forEach(joiner::add);
		try {
			String command = joiner.toString();
			log.debug("Running command: {}", command);
			return runtime.exec(command);
		} catch (IOException e) {
			throw new PdfgenException("Running command has failed.", e);
		}
	}

	CommandLineExecutor withArguments(Map<String, String> properties) {
		properties.entrySet().stream()
				.map(this::formatOptionalArgument)
				.forEach(args::add);

		return this;
	}

	private String formatOptionalArgument(Map.Entry<String, String> entry) {
		if (entry.getValue().equals("")) {
			return entry.getKey();
		}

		return entry.getKey() + " " + entry.getValue();
	}
}
