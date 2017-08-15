package ie.tcd.slscs.itut.gramadanj;
/*
 * Copyright © 2016 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2016 Coláiste na Tríonóide, Baile Átha Cliath
 * An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import junit.framework.TestCase;
import ie.tcd.slscs.itut.gramadanj.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

public class UtilsTest extends TestCase {
    public void testExpandParentheticalVariants() throws Exception {
        String testin = "colo(u)r";
        String[] testout = {"color", "colour"};
        String[] run = Utils.expandParentheticalVariants(testin);
        assertArrayEquals(testout, run);
    }

    public void testCleanTrailingPunctuation() throws Exception {
        final String ina = "thing,";
        final String inb = "thing";
        assertEquals(inb, Utils.cleanTrailingPunctuation(ina));
        assertEquals(inb, Utils.cleanTrailingPunctuation(inb));
    }

    public void testEqualLists() {
        List<String> a = new ArrayList<String>();
        List<String> b = new ArrayList<String>();
        a.add("bee");
        a.add("cee");
        a.add("ay");
        b.add("cee");
        b.add("ay");
        b.add("bee");
        assertEquals(true, Utils.equalLists(a, b));
    }

    public void testListStartsWithList() {
        List<String> a = Arrays.asList(new String[]{"ay", "bee", "cee"});
        List<String> b = Arrays.asList(new String[]{"ay", "bee"});
        assertEquals(Utils.listStartsWithList(a, b), true);
        List<String> c = Arrays.asList(new String[]{"ay", "bee", "cee", "dee"});
        assertEquals(Utils.listStartsWithList(a, c), false);
        List<String> d = Arrays.asList(new String[]{"ay", "cee", "bee"});
        assertEquals(Utils.listStartsWithList(a, d), false);
    }
    public void testTrim() {
        assertEquals("", Utils.trim("     "));
        assertEquals("aaa", Utils.trim("  aaa   "));
        assertEquals("aaa", Utils.trim("  aaa"));
        assertEquals("aaa", Utils.trim("aaa  "));
    }

    public void testExpandFGB() {
        assertEquals("endings", Utils.expandFGB("ending", "~s"));
        assertEquals("chasing", Utils.expandFGB("chase", "-sing"));
        assertEquals("truncálann", Utils.expandFGB("truncáil", "-álann"));
    }

    public void testJoin() {
        List<String> in = Arrays.asList(new String[]{"foo", "bar", "baz"});
        String delim = "::";
        String exp = "foo::bar::baz";
        String out = Utils.join(in, delim);
        assertEquals(exp, out);
    }
    public void testListclone() {
        List<String> inp = new ArrayList<String>();
        inp.add("foo");
        inp.add("bar");
        List<String> out = Utils.listclone(inp);
        assertEquals(inp.get(0), out.get(0));
        inp.set(0, "baz");
        assertNotEquals(inp.get(0), out.get(0));
    }
}
