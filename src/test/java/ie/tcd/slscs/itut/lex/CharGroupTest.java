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

public class CharGroupTest extends TestCase {
    public void testFromNode() throws Exception {
        String in1 = "<chargroup name=\"vowel\" repeated=\"no\">aeiou</chargroup>";
        String in2 = "<chargroup name=\"nonvowel\" repeated=\"no\" negates=\"vowel\" />";
        String in3 = "<chargroup name=\"vowel\" repeated=\"yes\">aeiou</chargroup>";
        String in4 = "<chargroup name=\"vowel\" optional=\"yes\" repeated=\"yes\">aeiou</chargroup>";

        Node innode1 = Utils.stringToNode(in1);
        Node innode2 = Utils.stringToNode(in2);
        Node innode3 = Utils.stringToNode(in3);
        Node innode4 = Utils.stringToNode(in4);

        CharGroup out1 = CharGroup.fromNode(innode1);
        CharGroup out2 = CharGroup.fromNode(innode2);
        CharGroup out3 = CharGroup.fromNode(innode3);
        CharGroup out4 = CharGroup.fromNode(innode4);

        assertEquals(false, out1.isNegated());
        assertEquals(true, out2.isNegated());
        assertEquals("vowel", out2.getNegates());
        assertEquals(false, out1.isRepeated());
        assertEquals(false, out1.isOptional());
        assertEquals(true, out3.isRepeated());
        assertEquals(false, out3.isOptional());
        assertEquals(true, out4.isRepeated());
        assertEquals(true, out4.isOptional());
        assertEquals("aeiou", out1.getRawCharacters());
        assertEquals("([aeiou])", out1.getRegex());
        assertEquals("([aeiou]+)", out3.getRegex());
        assertEquals("([aeiou]*)", out4.getRegex());
    }

}