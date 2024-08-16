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

import java.time.Duration;

public class RendererConfiguration {

    private Duration rendererTimeout;
    private Long memoryLimitInKb;

    public RendererConfiguration(Duration rendererTimeout, Long memoryLimitInKb) {
        this.rendererTimeout = rendererTimeout;
        this.memoryLimitInKb = memoryLimitInKb;
    }

    public Duration getRendererTimeout() {
        return rendererTimeout;
    }

    public Long getMemoryLimitInKb() {
        return memoryLimitInKb;
    }

    @Override
    public String toString() {
        return "RendererConfiguration{" +
                "rendererTimeout=" + rendererTimeout +
                ", memoryLimitInKb=" + memoryLimitInKb +
                '}';
    }
}
