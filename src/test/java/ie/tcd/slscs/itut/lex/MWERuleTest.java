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
import static org.junit.Assert.assertEquals;

public class MWERuleTest extends TestCase {
    public void testFromNode() throws Exception {
        String in = "<rule name=\"adj_n\" phrase=\"np\">\n" +
                "  <entry tags=\"sg\">\n" +
                "    <word tags=\"adj\"/>\n" +
                "    <word tags=\"n.sg\"/>\n" +
                "  </entry>\n" +
                "  <entry tags=\"pl\">\n" +
                "    <word tags=\"adj\"/>\n" +
                "    <word tags=\"n.pl\"/>\n" +
                "  </entry>\n" +
                "</rule>\n";
        Node innode = Utils.stringToNode(in);
        MWERule out = MWERule.fromNode(innode);
        assertEquals("adj_n", out.getName());
        assertEquals("np", out.getPhrase());
        assertEquals(2, out.getEntries().size());

    }

}