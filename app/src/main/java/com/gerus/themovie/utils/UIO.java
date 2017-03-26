package com.gerus.themovie.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * Created by gerus-mac on 22/03/17.
 */

public class UIO {

    public static String fncsConvertInputToString (InputStream poInputStream) throws IOException {
        if (poInputStream != null) {
            Writer voWriter = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(poInputStream, Charset.forName("UTF-8")));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    voWriter.write(buffer, 0, n);
                }
                voWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                poInputStream.close();
            }
            return voWriter.toString();
        } else {
            return "";
        }
    }

}
