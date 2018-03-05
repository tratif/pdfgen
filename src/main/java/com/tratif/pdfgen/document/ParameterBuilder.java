package com.tratif.pdfgen.document;

import java.util.Map;

public class ParameterBuilder {
    private Map<String, String> params;

    ParameterBuilder(Map<String, String> params) {
        this.params = params;
    }

    public ParameterBuilder landscape() {
        params.put("orientation", "Landscape");
        return this;
    }

    public ParameterBuilder portrait() {
        params.put("orientation", "Portrait");
        return this;
    }

    public ParameterBuilder pageHeight(String height) {
        params.put("--page-height", height);
        return this;
    }

    public ParameterBuilder pageWidth(String width) {
        params.put("--page-width", width);
        return this;
    }

    public ParameterBuilder pageSize(String size) {
        params.put("--page-size", size);
        return this;
    }

    public ParameterBuilder a4() {
        params.put("--page-size", "A4");
        return this;
    }

    public ParameterBuilder zoom(double zoom) {
        params.put("--zoom", Double.toString(zoom));
        return this;
    }

    public ParameterBuilder lowQuality() {
        params.put("--lowquality", "");
        return this;
    }

    public ParameterBuilder marginLeft(String size) {
        params.put("--margin-left", size);
        return this;
    }

    public ParameterBuilder marginTop(String size) {
        params.put("--margin-top", size);
        return this;
    }

    public ParameterBuilder marginRight(String size) {
        params.put("--margin-right", size);
        return this;
    }

    public ParameterBuilder marginBottom(String size) {
        params.put("--margin-bottom", size);
        return this;
    }

    public ParameterBuilder minimumFontSize(int size) {
        params.put("--minimum-font-size", Integer.toString(size));
        return this;
    }

    public ParameterBuilder noBackground() {
        params.put("--no-background", "");
        return this;
    }

    public ParameterBuilder grayscale() {
        params.put("--grayscale", "");
        return this;
    }
}
