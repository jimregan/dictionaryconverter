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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public static AttributeSequence fromString(String s) throws Exception {
        List<String> tags = new ArrayList<String>();
        boolean fromDictionary = false;
        String[] tmp = s.split("=");
        if(tmp.length != 2) {
            throw new Exception("Single '=' expected, got: " + s);
        }
        String name = tmp[0].trim();
        tags = Arrays.asList(tmp[2].split(" "));
        return new AttributeSequence(name, tags);
    }
    public static List<AttributeSequence> fromFile(BufferedReader br) throws IOException {
        List<AttributeSequence> out = new ArrayList<AttributeSequence>();
        String line;
        int lineno = 0;
        while((line = br.readLine()) != null) {
            lineno++;
            SimpleTextMacro tmp = new SimpleTextMacro();
            try {
                out.add(fromString(line));
            } catch (Exception e) {
                throw new IOException(e.getMessage() + " on line " + lineno);
            }
        }
        return out;
    }

    /**
     * Checks if the name of the AttributeSequence has been defined in Attributes
     * @param as the AttributeSequence to check
     * @param map map of names to Attributes
     * @return true if name has been defined
     */
    public static boolean isValidAttributeSequenceName(AttributeSequence as, Map<String, Attributes> map) {
        return map.containsKey(as.name);
    }
}
