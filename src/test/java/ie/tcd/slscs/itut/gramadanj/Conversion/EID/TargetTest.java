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

package ie.tcd.slscs.itut.gramadanj.Conversion.EID;

import ie.tcd.slscs.itut.gramadanj.Utils;
import junit.framework.TestCase;
import org.w3c.dom.Node;

import static org.junit.Assert.*;

public class TargetTest extends TestCase {
    public void testGetNote() throws Exception {
        String t1 = "<noindex>(<label>g.</label> mná)</noindex>";
        Node n1 = Utils.stringToNode(t1);
        GrammarNote exp = new GrammarNote("g.", "mná", false);
        GrammarNote out = Target.getNote(n1);
        assertEquals(exp.label, out.label);
        assertEquals(exp.wordform, out.wordform);
        assertEquals(exp.optional, out.optional);
    }

    public void testGetNote2() throws Exception {
        String t1 = "<noindex>(<label>m</label>)</noindex>";
        Node n1 = Utils.stringToNode(t1);
        GrammarNote exp = new GrammarNote("m", "", true);
        GrammarNote out = Target.getNote(n1);
        assertEquals(exp.label, out.label);
        assertEquals(exp.wordform, out.wordform);
        assertEquals(exp.optional, out.optional);
    }

    public void testGetNote3() throws Exception {
        String t1 = "<blah><label>m</label></blah>";
        Node n1 = Utils.stringToNode(t1);
        GrammarNote out = Target.getNote(n1);
        assertEquals(null, out);
    }
}