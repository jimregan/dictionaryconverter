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

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTextMacroAttrTest extends TestCase {
    public void testGetKey() throws Exception {
        SimpleTextMacroAttr in = new SimpleTextMacroAttr("k", "v");
        assertEquals("k", in.getKey());
    }

    public void testGetValue() throws Exception {
        SimpleTextMacroAttr in = new SimpleTextMacroAttr("k", "v");
        assertEquals("v", in.getValue());
    }

    public void testCreateLemma() throws Exception {
        SimpleTextMacroAttr in = SimpleTextMacroAttr.createLemma("foo");
        assertEquals("foo", in.getValue());
        assertEquals("lemma", in.getKey());
    }

    public void testFromSimpleText() throws Exception {
        SimpleTextMacroAttr a = SimpleTextMacroAttr.fromSimpleText("<thing!=b>");
        SimpleTextMacroAttr b = SimpleTextMacroAttr.fromSimpleText("<thing=b>");
        SimpleTextMacroAttr c = SimpleTextMacroAttr.fromSimpleText("<justb>");
        SimpleTextMacroAttr d = SimpleTextMacroAttr.fromSimpleText("<list:justb=foo>");
        SimpleTextMacroAttr e = SimpleTextMacroAttr.fromSimpleText("<beginslist:justb=bar>");
        SimpleTextMacroAttr f = SimpleTextMacroAttr.fromSimpleText("<endslist:justb=baz>");
        assertEquals("thing", a.getKey());
        assertEquals("b", a.getValue());
        assertEquals(true, a.getNot());
        assertEquals("thing", b.getKey());
        assertEquals("b", b.getValue());
        assertEquals(false, b.getNot());
        assertEquals("justb", c.getKey());
        assertEquals("", c.getValue());
        assertEquals(false, c.isNot());
        assertEquals("justb", d.getKey());
        assertEquals("foo", d.getValue());
        assertEquals(true, d.isList());
        assertEquals("justb", e.getKey());
        assertEquals("bar", e.getValue());
        assertEquals(true, e.isBeginsList());
        assertEquals("justb", f.getKey());
        assertEquals("baz", f.getValue());
        assertEquals(true, f.isEndsList());
    }
}