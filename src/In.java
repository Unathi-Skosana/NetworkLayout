/******************************************************************************
 *  Compilation:  javac In.java
 *  Execution:    java In   (basic test --- see source for required files)
 *  Dependencies: none
 *
 *  Reads in data of various types from standard input, files, and URLs.
 *
 ******************************************************************************/

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.Socket;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *  <i>Input</i>. This class provides methods for reading strings
 *  and numbers from standard input, file input, URLs, and sockets.
 *  <p>
 *  The Locale used is: language = English, country = US. This is consistent
 *  with the formatting conventions with Java floating-point literals,
 *  command-line arguments (via {@link Double#parseDouble(String)})
 *  and standard output.
 *  <p>
 *  For additional documentation, see
 *  <a href="http://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
 *  <i>Computer Science: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 *  <p>
 *  Like {@link Scanner}, reading a token also consumes preceding Java
 *  whitespace, reading a full line consumes
 *  the following end-of-line delimeter, while reading a character consumes
 *  nothing extra.
 *  <p>
 *  Whitespace is defined in {@link Character#isWhitespace(char)}. Newlines
 *  consist of \n, \r, \r\n, and Unicode hex code points 0x2028, 0x2029, 0x0085;
 *  see <a href="http://www.docjar.com/html/api/java/util/Scanner.java.html">
 *  Scanner.java</a> (NB: Java 6u23 and earlier uses only \r, \r, \r\n).
 *
 *  @author David Pritchard
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class In {

    ///// begin: section (1 of 2) of code duplicated from In to StdIn.

    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";

    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.US;

    // the default token separator; we maintain the invariant that this value
    // is held by the scanner's delimiter between calls
    private static final Pattern WHITESPACE_PATTERN
        = Pattern.compile("\\p{javaWhitespace}+");

    // makes whitespace characters significant
    private static final Pattern EMPTY_PATTERN
        = Pattern.compile("");

    // used to read the entire input. source:
    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    private static final Pattern EVERYTHING_PATTERN
        = Pattern.compile("\\A");

    //// end: section (1 of 2) of code duplicated from In to StdIn.

    private Scanner scanner;


   /**
     * Initializes an input stream from a filename or web page name.
     *
     * @param  name the filename or web page name
     * @throws IllegalArgumentException if cannot open {@code name} as
     *         a file or URL
     * @throws IllegalArgumentException if {@code name} is {@code null}
     */
    public In(String name) {
        if (name == null) throw new IllegalArgumentException("argument is null");
        try {
            // first try to read file from local file system
            File file = new File(name);
            if (file.exists()) {
                // for consistency with StdIn, wrap with BufferedInputStream instead of use
                // file as argument to Scanner
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }

            // next try for files included in jar
            URL url = getClass().getResource(name);

            // or URL from web
            if (url == null) {
                url = new URL(name);
            }

            URLConnection site = url.openConnection();

            // in order to set User-Agent, replace above line with these two
            // HttpURLConnection site = (HttpURLConnection) url.openConnection();
            // site.addRequestProperty("User-Agent", "Mozilla/4.76");

            InputStream is     = site.getInputStream();
            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + name + " make sure the filename specified is a valid file.");
        }
    }

   /**
     * Returns true if input stream is empty (except possibly whitespace).
     * Use this to know whether the next call to {@link #readString()},
     * {@link #readDouble()}, etc will succeed.
     *
     * @return {@code true} if this input stream is empty (except possibly whitespace);
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return !scanner.hasNext();
    }

   /**
     * Returns true if this input stream has a next line.
     * Use this method to know whether the
     * next call to {@link #readLine()} will succeed.
     * This method is functionally equivalent to {@link #hasNextChar()}.
     *
     * @return {@code true} if this input stream has more input (including whitespace);
     *         {@code false} otherwise
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

   /**
     * Reads and returns the next line in this input stream.
     *
     * @return the next line in this input stream; {@code null} if no such line
     */
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

   /**
     * Reads the next token from this input stream, parses it as a {@code int},
     * and returns the {@code int}.
     *
     * @return the next {@code int} in this input stream
     */
    public int readInt() {
        return scanner.nextInt();
    }

}


/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
