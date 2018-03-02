package com.tratif.pdfgen.document;

import java.util.Map;

public class PropertiesBuilder {
    private Map<String, String> properties;

    PropertiesBuilder(Map<String, String> properties) {
        this.properties = properties;
    }

    public PropertiesBuilder landscape() {
        properties.put("orientation", "Landscape");
        return this;
    }

    public PropertiesBuilder portrait() {
        properties.put("orientation", "Portrait");
        return this;
    }

    public PropertiesBuilder pageHeight(String height) {
        properties.put("--page-height", height);
        return this;
    }

    public PropertiesBuilder pageWidth(String width) {
        properties.put("--page-width", width);
        return this;
    }

    public PropertiesBuilder pageSize(String size) {
        properties.put("--page-size", size);
        return this;
    }

    public PropertiesBuilder zoom(double zoom) {
        properties.put("--zoom", Double.toString(zoom));
        return this;
    }

    public PropertiesBuilder lowQuality() {
        properties.put("--lowquality", "");
        return this;
    }

    public PropertiesBuilder marginLeft(String size) {
        properties.put("--margin-left", size);
        return this;
    }

    public PropertiesBuilder marginTop(String size) {
        properties.put("--margin-top", size);
        return this;
    }

    public PropertiesBuilder marginRight(String size) {
        properties.put("--margin-right", size);
        return this;
    }

    public PropertiesBuilder marginBottom(String size) {
        properties.put("--margin-bottom", size);
        return this;
    }

    public PropertiesBuilder minimumFontSize(int size) {
        properties.put("--minimum-font-size", Integer.toString(size));
        return this;
    }

    public PropertiesBuilder noBackground() {
        properties.put("--no-background", "");
        return this;
    }

    public PropertiesBuilder grayscale() {
        properties.put("--grayscale", "");
        return this;
    }
}
