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
package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.docs.HtmlTemplate;

import java.io.Writer;

public interface HtmlRenderer {

//	String render(String htmlTemplate, Map<String, Object> params);
//
//	void render(String templateName, Map<String, Object> params, FileWriter fileWriter);

	void render(HtmlTemplate page, Writer writer);
}
