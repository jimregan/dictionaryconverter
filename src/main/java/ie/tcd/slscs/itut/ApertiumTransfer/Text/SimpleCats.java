/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 * An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ie.tcd.slscs.itut.ApertiumTransfer.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleCats {
    String name;
    List<String> items;
    SimpleCats() {
        this.items = new ArrayList<String>();
    }
    SimpleCats(String name, List<String> items) {
        this();
        this.name = name;
        this.items = items;
    }
    public String getName() {
        return name;
    }
    public List<String> getItems() {
        return items;
    }
    public static SimpleCats fromString(String s) throws Exception {
        List<String> tags = new ArrayList<String>();
        String[] tmp = s.split("=");
        if(tmp.length != 2) {
            throw new Exception("Single '=' expected, got: " + s);
        }
        String name = tmp[0].trim();
        tags = Arrays.asList(tmp[1].trim().split(" "));
        return new SimpleCats(name, tags);
    }
    public static List<SimpleCats> fromFile(BufferedReader br) throws IOException {
        List<SimpleCats> out = new ArrayList<SimpleCats>();
        String line;
        int lineno = 0;
        while((line = br.readLine()) != null) {
            lineno++;
            try {
                out.add(fromString(line));
            } catch (Exception e) {
                throw new IOException(e.getMessage() + " on line " + lineno);
            }
        }
        return out;
    }
    public static List<SimpleCats> fromFile(InputStreamReader isr) throws Exception {
        return fromFile(new BufferedReader(isr));
    }
    public static List<SimpleCats> fromFile(InputStream is) throws Exception {
        return fromFile(new InputStreamReader(is, "UTF-8"));
    }
    public static List<SimpleCats> fromFile(FileInputStream fi) throws Exception {
        return fromFile(new InputStreamReader(fi, "UTF-8"));
    }
    public static List<SimpleCats> fromFile(File f) throws Exception {
        return fromFile(new FileInputStream(f));
    }
    public static List<SimpleCats> fromFile(String s) throws Exception {
        return fromFile(new File(s));
    }

}
