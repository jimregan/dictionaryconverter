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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GroupTest extends TestCase {
    String in1 = "    <group n=\"add_e\" repeats=\"no\">\n" +
            "      <item>ch</item>\n" +
            "      <item>sh</item>\n" +
            "      <item>x</item>\n" +
            "    </group>\n";
    String in2 = "    <group n=\"add_e\" repeats=\"yes\">\n" +
            "      <item>ch</item>\n" +
            "      <item>sh</item>\n" +
            "      <item>x</item>\n" +
            "    </group>\n";
    String in3 = "    <group n=\"add_e\" optional=\"yes\">\n" +
            "      <item>ch</item>\n" +
            "      <item>sh</item>\n" +
            "      <item>x</item>\n" +
            "    </group>\n";
    String in4 = "    <group n=\"add_e\" repeats=\"yes\" optional=\"yes\">\n" +
            "      <item>ch</item>\n" +
            "      <item>sh</item>\n" +
            "      <item>x</item>\n" +
            "    </group>\n";

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

    public void testGetRegex() throws Exception {
        List<String> items = new ArrayList<String>();
        items.add("ch");
        items.add("sh");
        items.add("x");
        Group g = new Group("add_e");
        g.setOptional(false);
        g.setRepeated(false);
        g.setItems(items);
        assertEquals("(ch|sh|x)", g.getRegex());
    }

    public void testFromNodeName() throws Exception {
        assertEquals("add_e", out1.getName());
    }
    public void testFromNodeRepeated1() throws Exception {
        assertEquals(false, out1.isRepeated());
    }
    public void testFromNodeOptional1() throws Exception {
        assertEquals(false, out1.isOptional());
    }
    public void testFromNodeRepeated2() throws Exception {
        assertEquals(true, out2.isRepeated());
    }
    public void testFromNodeOptional2() throws Exception {
        assertEquals(false, out2.isOptional());
    }
    public void testFromNodeRepeated3() throws Exception {
        assertEquals(false, out3.isRepeated());
    }
    public void testFromNodeOptional3() throws Exception {
        assertEquals(true, out3.isOptional());
    }
    public void testFromNodeRepeated4() throws Exception {
        assertEquals(true, out4.isRepeated());
    }
    public void testFromNodeOptional4() throws Exception {
        assertEquals(true, out4.isOptional());
    }

}