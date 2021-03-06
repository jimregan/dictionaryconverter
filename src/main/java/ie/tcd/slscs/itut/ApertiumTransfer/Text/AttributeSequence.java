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
import java.util.*;

/**
 * Define the sequence of attributes for a part of speech/chunk type;
 * e.g., noun = gen num
 * where 'gen' and 'num' are set as Attributes.
 */
public class AttributeSequence {
    String name;
    List<String> tags;
    AttributeSequence() {
        this.tags = new ArrayList<String>();
    }
    AttributeSequence(String name, List<String> tags) {
        this();
        this.name = name;
        this.tags = tags;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public static AttributeSequence fromString(String s) throws Exception {
        List<String> tags = new ArrayList<String>();
        boolean fromDictionary = false;
        String[] tmp = s.split("=");
        if(tmp.length != 2) {
            throw new Exception("Single '=' expected, got: " + s);
        }
        String name = tmp[0].trim();
        tags = Arrays.asList(tmp[1].trim().split(" "));
        return new AttributeSequence(name, tags);
    }
    public static List<AttributeSequence> fromFile(BufferedReader br) throws IOException {
        List<AttributeSequence> out = new ArrayList<AttributeSequence>();
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
    public static List<AttributeSequence> fromFile(InputStreamReader isr) throws Exception {
        return fromFile(new BufferedReader(isr));
    }
    public static List<AttributeSequence> fromFile(InputStream is) throws Exception {
        return fromFile(new InputStreamReader(is, "UTF-8"));
    }
    public static List<AttributeSequence> fromFile(FileInputStream fi) throws Exception {
        return fromFile(new InputStreamReader(fi, "UTF-8"));
    }
    public static List<AttributeSequence> fromFile(File f) throws Exception {
        return fromFile(new FileInputStream(f));
    }
    public static List<AttributeSequence> fromFile(String s) throws Exception {
        return fromFile(new File(s));
    }

    /**
     * Checks if the name of the AttributeSequence has been defined in Attributes
     * @param as the AttributeSequence to check
     * @param map map of names to Attributes
     * @return true if name has been defined
     */
    public static boolean isValidAttributeSequenceName(AttributeSequence as, Map<String, Attributes> map) {
        return map.containsKey(as.getName());
    }
    public static Map<String, AttributeSequence> listToMap(List<AttributeSequence> list) {
        Map<String, AttributeSequence> out = new HashMap<String, AttributeSequence>();
        for(AttributeSequence as : list) {
            if(!out.containsKey(as.getName())) {
                System.err.println("No attribute sequence for: " + as.getName());
            } else {
                out.put(as.name, as);
            }
        }
        return out;
    }
}
