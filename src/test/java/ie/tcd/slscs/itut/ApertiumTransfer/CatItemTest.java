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

package ie.tcd.slscs.itut.ApertiumTransfer;

import ie.tcd.slscs.itut.ApertiumStream.SimpleToken;
import ie.tcd.slscs.itut.ApertiumStream.WordToken;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CatItemTest extends TestCase {
    public void testSimpleConstructor() {
        CatItem ci = new CatItem("foo", "n.m.sg");
        assertEquals(3, ci.getTagsList().size());
        assertEquals("n", ci.getFirstTag());
    }
    public void testTagsMatch() {
        List<String> comp = new ArrayList<String>();
        comp.add("n");
        comp.add("m");
        comp.add("sg");
        CatItem ci = new CatItem("foo", "n.m.sg");
        assertEquals(true, ci.tagsMatch(comp));
    }
    public void testTagsStartWith() {
        List<String> comp = new ArrayList<String>();
        comp.add("n");
        comp.add("f");
        CatItem ci = new CatItem("foo", "n.*.*");
        assertEquals(true, ci.tagsStartWith(comp));
    }
    public void testTagsStartWith2() {
        List<String> comp = new ArrayList<String>();
        comp.add("n");
        CatItem ci = new CatItem("", "n");
        assertEquals(true, ci.tagsStartWith(comp));
    }

    public void testWordTokenMatches1() throws Exception {
        WordToken wt = WordToken.fromString("^simple<n>$");
        CatItem ci = new CatItem("simple", "n");
        assertEquals(true, ci.wordtokenMatches(wt));
    }

    public void testWordTokenMatches2() throws Exception {
        WordToken wt = WordToken.fromString("^<n>$");
        CatItem ci = new CatItem("", "n");
        assertEquals(true, ci.wordtokenMatches(wt));
    }

    public void testWordTokenMatches3() throws Exception {
        WordToken wt = WordToken.fromString("^<adj>$");
        CatItem ci = new CatItem("", "adj");
        assertEquals(true, ci.wordtokenMatches(wt));
    }

    public void testWordTokenMatches4() throws Exception {
        SimpleToken st = SimpleToken.fromString("<adj>");
        CatItem ci = new CatItem("", "adj");
        assertEquals(true, ci.wordtokenMatches(st));
    }
}