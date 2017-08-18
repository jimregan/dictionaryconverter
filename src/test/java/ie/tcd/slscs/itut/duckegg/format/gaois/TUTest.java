/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Jim O'Regan
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
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TUTest extends TestCase {
    String input = "<tu tuid=\"144020\">\n" +
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
            "</tu>\n";
    public void testFromNode() throws Exception {
        String in1 = "S.I. No. 171/1980:";
        String in2 = "I.R. Uimh. l71 de 1980.";
        Node inxml = Utils.stringToNode(input);
        TU out = TU.fromNode(inxml);
        Map<String, String> expm = new HashMap<String, String>();
        expm.put("en", in1);
        expm.put("ga", in2);
        TU exp = new TU("144020", expm);
        assertEquals(exp.getId(), out.getId());
        assertEquals(exp.getSegments().get("en"), out.getSegments().get("en"));
    }

}