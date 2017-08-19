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

package ie.tcd.slscs.itut.duckegg.format.gaois;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TMXTest extends TestCase {
    String in = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
            "<!DOCTYPE tmx SYSTEM \"tmx14.dtd\">\n" +
            "<tmx version=\"1.4\">\n" +
            "\n" +
            "<header creationtool=\"ParaDocs-tsv2tmx.py\" creationtoolversion=\"1.1\" segtype=\"sentence\" o-tmf=\"TSV\" adminlang=\"en\" srclang=\"en\" datatype=\"unknown\">\n" +
            "<prop type=\"name\">Irish legislation translation units from Fiontar & Scoil na Gaeilge's Parallel English-Irish corpus of legal texts: http://www.gaois.ie/crp/en/</prop>\n" +
            "</header>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<tu tuid=\"144020\">\n" +
            "<tuv xml:lang=\"en\">\n" +
            "<seg>\n" +
            "S.I. No. 171/1980:\n" +
            "</seg>\n" +
            "</tuv>\n" +
            "<tuv xml:lang=\"ga\">\n" +
            "<seg>\n" +
            "I.R. Uimh. l71 de 1980.\n" +
            "</seg>\n" +
            "</tuv>\n" +
            "</tu>\n" +
            "\n" +
            "</body>\n" +
            "</tmx>\n";
    public void testReadFile() throws Exception {
        ByteArrayInputStream bas = new ByteArrayInputStream(in.getBytes());
        Map<String, String> expm = new HashMap<String, String>();
        expm.put("en", "S.I. No. 171/1980:");
        expm.put("ga", "I.R. Uimh. l71 de 1980.");
        TU exp = new TU("144020", expm);

        TMX out = TMX.readFile(bas);
        assertEquals(1, out.getTUs().size());
        assertEquals(exp.getId(), out.getTUs().get(0).getId());
    }

}