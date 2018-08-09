# pdfgen

The library for creating PDF files from html documents.

### System requirements ###

This library requires `wkhtmltopdf 0.12.4 with patched Qt` to be installed on your machine. It should be added to system path. You can download it from [here](https://wkhtmltopdf.org/).

`WARNING: Some Linux distributions may have old, not patched versions of wkhtmltopdf in their repositiories.`

Basic usage
---------------

The following call will convert the provided HTML document and produce a PDF file: 

```java
byte[] pdfDocument = Document.fromStaticHtml(html)
                             .and()
                             .toPdf()
                             .toByteArray();
```

where `html` can be one of: `String`, `InputStream`, `Reader`, `URL`, `File`, `Path`. 

Customization
---------------

It is possible to customize the generated PDF using the following parameters:
* Orientation
    * Portrait
    * Landscape
* Page dimensions
* Page size
    * A4
    * Letter
* Zoom
* Quality
* Margins
* Minimum font size
* No background
* Grayscale
* Enable/Disable smart shrinking

### Setting parameters ###

The following call will set portrait orientation, left margin, right margin and grayscale:
```java
Document.fromStaticHtml("<html>")
        .and()
        .withParameters()
            .portrait()
            .marginLeft("2cm")
            .marginRight("2cm")
            .grayscale()
        .and()
        .toPdf();
```

Thymeleaf templates
---------------

**Thymeleaf is default templating engine.**

* [Thymeleaf webpage](https://www.thymeleaf.org/index.html)
* [Thymeleaf documentation](https://www.thymeleaf.org/documentation.html)
* [Thymeleaf GitHub](https://github.com/thymeleaf)

Working with template engines is as easy as with static html. All you have to do is call a proper method:
```java
Document.fromHtmlTemplate(template, params)
        .withTemplateEngine(HtmlTemplateEngine.THYMELEAF)
        .and()
        .withParameters()
            .portrait()
            .marginLeft("2cm")
            .marginRight("2cm")
            .grayscale()
        .and()
        .toPdf();
```

where `args` is a `Map<String, Object>` object. It is used as typical Thymeleaf parameters.

FreeMarker templates
---------------

* [FreeMarker webpage](https://freemarker.apache.org/)
* [FreeMarker documentation](https://freemarker.apache.org/docs/index.html)
* [FreeMarker GitHub](https://github.com/apache/freemarker)

You can also use FreeMarker templates.

```java
Document.fromHtmlTemplate(template, params)
        .withTemplateEngine(HtmlTemplateEngine.FreeMarker)
        .and()
        .withParameters()
            .portrait()
            .marginLeft("2cm")
            .marginRight("2cm")
            .grayscale()
        .and()
        .toPdf();
```

### Producing HTML from templates ###

It is also possible to use `pdfgen` as template parsing engine:
```java
HtmlDocument parsedHtml = Document.fromHtmlTemplate(template, params)
                                  .and()
                                  .toHtml();
```

Java8TimeDialect
---------------
`pdfgen` is able to resolve temporal formatting in Thymeleaf's templates. For example, this template:
```html
<div th:text="${#temporals.format(time, 'YYYY-MM-dd HH:mm:ss')}"></div>
```
assuming `time` is a `LocalDateTime` object of date `2018-06-13 12:09:30` will generate following output:
```html
<div>2018-06-13 12:09:30</div>
```
