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

package ie.tcd.slscs.itut.ApertiumStream;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleTokenTest extends TestCase {
    public void testFromString() throws Exception {
        List<String> tags = new ArrayList<String>();
        tags.add("n");
        tags.add("sg");
        SimpleToken exp = new SimpleToken("foo bar", tags);
        SimpleToken out = SimpleToken.fromString("foo_bar<n><sg>");
        assertEquals(exp.getLemma(), out.getLemma());
        assertEquals(exp.getTags().size(), out.getTags().size());
        assertEquals(exp.getTags().get(0), out.getTags().get(0));
    }

    public void testListFromString() throws Exception {
        List<String> tags = new ArrayList<String>();
        tags.add("n");
        tags.add("sg");
        List<String> tags2 = new ArrayList<String>();
        tags2.add("adj");
        tags2.add("sg");
        SimpleToken exp1 = new SimpleToken("foo bar", tags);
        SimpleToken exp2 = new SimpleToken("baz", tags2);
        List<SimpleToken> exp = new ArrayList<SimpleToken>();
        exp.add(exp1);
        exp.add(exp2);
        List<SimpleToken> out = SimpleToken.listFromString("foo_bar<n><sg> baz<adj><sg>");
        assertEquals(exp.size(), out.size());
        assertEquals(exp.get(1).getTags().get(0), out.get(1).getTags().get(0));
    }

}