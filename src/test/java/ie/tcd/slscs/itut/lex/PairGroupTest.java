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

package ie.tcd.slscs.itut.lex;

import ie.tcd.slscs.itut.gramadanj.Utils;
import junit.framework.TestCase;
import org.junit.Test;
import org.w3c.dom.Node;

import static org.junit.Assert.*;

public class PairGroupTest extends TestCase {
    String in1 = "<pairgroup name=\"broaden\" repeats=\"no\">\n" +
            "    <pair><left>ói</left><right>ó</right></pair>\n" +
            "    <pair><left>ei</left><right>ea</right></pair>\n" +
            "    <pair><left>éi</left><right>éa</right></pair>\n" +
            "    <pair><left>i</left><right>ea</right></pair>\n" +
            "    <pair><left>aí</left><right>aío</right></pair>\n" +
            "    <pair><left>í</left><right>ío</right></pair>\n" +
            "    <pair><left>ui</left><right>o</right></pair>\n" +
            "    <pair><left>io</left><right>ea</right></pair>\n" +
            "</pairgroup>";
    String in2 = "<pairgroup name=\"slenderise\" repeats=\"yes\">\n" +
            "    <pair><left>ea</left><right>i</right></pair>\n" +
            "    <pair><left>éa</left><right>éi</right></pair>\n" +
            "    <pair><left>ia</left><right>éi</right></pair>\n" +
            "    <pair><left>ío</left><right>í</right></pair>\n" +
            "    <pair><left>io</left><right>i</right></pair>\n" +
            "    <pair><left>iu</left><right>i</right></pair>\n" +
            "    <pair><left>ae</left><right>aei</right></pair>\n" +
            "</pairgroup>";
    String in3 = "<pairgroup name=\"slenderise\" optional=\"yes\">\n" +
            "    <pair><left>ea</left><right>i</right></pair>\n" +
            "    <pair><left>éa</left><right>éi</right></pair>\n" +
            "    <pair><left>ia</left><right>éi</right></pair>\n" +
            "    <pair><left>ío</left><right>í</right></pair>\n" +
            "    <pair><left>io</left><right>i</right></pair>\n" +
            "    <pair><left>iu</left><right>i</right></pair>\n" +
            "    <pair><left>ae</left><right>aei</right></pair>\n" +
            "</pairgroup>";
    String in4 = "<pairgroup name=\"slenderise\" optional=\"yes\" repeats=\"yes\">\n" +
            "    <pair><left>ea</left><right>i</right></pair>\n" +
            "    <pair><left>éa</left><right>éi</right></pair>\n" +
            "    <pair><left>ia</left><right>éi</right></pair>\n" +
            "    <pair><left>ío</left><right>í</right></pair>\n" +
            "    <pair><left>io</left><right>i</right></pair>\n" +
            "    <pair><left>iu</left><right>i</right></pair>\n" +
            "    <pair><left>ae</left><right>aei</right></pair>\n" +
            "</pairgroup>";

    Node innode1;
    Node innode2;
    Node innode3;
    Node innode4;
    PairGroup out1;
    PairGroup out2;
    PairGroup out3;
    PairGroup out4;
    public void setUp() throws Exception {
        innode1 = Utils.stringToNode(in1);
        innode2 = Utils.stringToNode(in2);
        innode3 = Utils.stringToNode(in3);
        innode4 = Utils.stringToNode(in4);
        out1 = PairGroup.fromNode(innode1);
        out2 = PairGroup.fromNode(innode2);
        out3 = PairGroup.fromNode(innode3);
        out4 = PairGroup.fromNode(innode4);
    }
    public void testFromNodeName() throws Exception {
        assertEquals("broaden", out1.getName());
        assertEquals("slenderise", out2.getName());
        assertNotEquals("slenderise", out1.getName());
    }
    public void testFromNodeRepeated1() throws Exception {
        assertEquals(false, out1.isRepeated());
    }
    public void testFromNodeOptional1() throws Exception {
        assertEquals(false, out1.isOptional());
    }
    public void testFromNodeRepeated2() throws Exception {
        assertEquals(false, out2.isRepeated());
    }
    public void testFromNodeOptional2() throws Exception {
        assertEquals(false, out2.isOptional());
    }
    public void testFromNodeRepeated3() throws Exception {
        assertEquals(false, out3.isRepeated());
    }
    public void testFromNodeOptional3() throws Exception {
        assertEquals(false, out3.isOptional());
    }
    public void testFromNodeRepeated4() throws Exception {
        assertEquals(false, out4.isRepeated());
    }
    public void testFromNodeOptional4() throws Exception {
        assertEquals(false, out4.isOptional());
    }
}