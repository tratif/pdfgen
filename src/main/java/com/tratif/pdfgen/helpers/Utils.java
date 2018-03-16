package com.tratif.pdfgen.helpers;

import java.io.*;

public class Utils {

    public static void writeStreamToFile(File file, InputStream inputStream) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            int b;
            int i = 1;
            while((b = inputStream.read()) != -1) {
                fos.write(b);

                if (i % 1000 == 0) {
                    fos.flush();
                }

                i++;
            }
        }
    }
}
