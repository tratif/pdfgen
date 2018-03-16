# pdfgen

The library for creating PDF files from html documents.

### System requirements ###

This library requires `wkhtmltopdf 0.12.4 with patched Qt` to be installed on your machine. It should be added to system path. You can download it from [here](https://wkhtmltopdf.org/).

`WARNING: Some Linux distributions may have old, not patched versions of wkhtmltopdf in their repositiories.`

Basic usage
---------------

The following call will convert the provided HTML document and produce a PDF file: 

```java
byte[] pdf = Document.fromStaticHtml(html)
                        .toPdf();
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
DocumentBuilder document = Document.fromStaticHtml(html);
document.parameters()
    .portrait()
    .marginLeft("2cm")
    .marginRight("2cm")
    .grayscale();
byte[] pdf = db.toPdf();
```

Thymeleaf templates
---------------

* [Thymeleaf webpage](https://www.thymeleaf.org/index.html)
* [Thymeleaf documentation](https://www.thymeleaf.org/documentation.html)
* [Thymeleaf GitHub](https://github.com/thymeleaf)

Working with Thymeleaf templates is as easy as with static html. All you have to do is call a proper method:
```java
DocumentBuilder document = Document.fromHtmlTemplate(htmlTemplate, args)
document.parameters()
    .portrait()
    .marginLeft("2cm")
    .marginRight("2cm")
    .grayscale()
byte[] pdf = db.toPdf();
```

where `args` is a `Map<String, Object>` object. It is used as typical Thymeleaf parameters.

### Producing HTML from templates ###

It is also possible to use `pdfgen` as simple Thymeleaf parsing engine:
```java
String parsedHtml = Document.fromHtmlTemplate(htmlTemplate, args)
                      .toHtml();
```