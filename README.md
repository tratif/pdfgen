# pdfgen

An API for creating PDF files both from static HTML and Thymeleaf templates.

### System requirements ###


This API requires `wkhtmltopdf` installed on your machine. It should be added to system path. You can download it from [here](https://wkhtmltopdf.org/).


Basic usage
---------------

The following call will produce basic PDF file converted from HTML:

```java
byte[] pdf = Document.fromStaticHtml(html)
                        .toPdf();
```

where `html` can be one of: `String`, `InputStream`, `Reader`, `URL`, `File`, `Path`. 

Customization
---------------

It is possible to customize bunch of parameters such as:
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
DocumentBuilder db = Document.fromStaticHtml(html);
db.parameters()
    .portrait()
    .marginLeft("2cm")
    .marginRight("2cm")
    .grayscale();
byte[] pdf = db.toPdf();
```

Thymeleaf templates
---------------

Working with Thymeleaf templates is as easy as with static html. All you have to do is call a proper method:
```java
DocumentBuilder db = Document.fromHtmlTemplate(htmlTemplate, args)
db.parameters()
    .portrait()
    .marginLeft("2cm")
    .marginRight("2cm")
    .grayscale()
byte[] pdf = db.toPdf();
```

where `args` is a `Map<String, Object>` object. It is used as typical Thymeleaf parameters.

### Getting parsed content ###

It is also possible to use `pdfgen` as simple Thymeleaf parsing engine:
```java
String parsedHtml = Document.fromHtmlTemplate(htmlTemplate, args)
                      .toHtml();
```

Multiple page support
---------------

You can merge multiple pages together into one PDF file. Additionally, you can choose separate parameters for each page.
```java
PDF pdf = new PDF();
Page pageOne = new Page(new File("firstOne.html"));
pageOne.parameters()
    .noBackground()
    .zoom(1.5);
Page pageTwo new Page(new File("thymeleafTemplate.html"), bindMap);
pdf.addPages(pageOne, pageTwo);
pdf.save(new File("mergedPdf.pdf"));
```