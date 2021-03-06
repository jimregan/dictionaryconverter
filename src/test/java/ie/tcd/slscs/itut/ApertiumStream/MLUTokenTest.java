/*
 *  The MIT License (MIT)
 *
 *  Copyright © 2017 Trinity College, Dublin
 *  Irish Speech and Language Technology Research Centre
 *  Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 *  An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package ie.tcd.slscs.itut.ApertiumStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ie.tcd.slscs.itut.gramadanj.Utils;
import junit.framework.TestCase;

import static junit.framework.Assert.assertEquals;

public class MLUTokenTest extends TestCase {
    List<String> tags1 = Arrays.asList(new String[]{"adj"});
    List<String> tags2 = Arrays.asList(new String[]{"n", "sg"});
    public void testFromStringBackout() throws Exception {
        WordToken exp = new WordToken("simple", "", tags2);
        StreamToken out = MLUToken.fromString("^simple<n><sg>$");
        assertEquals((out instanceof WordToken), true);
        assertEquals(((WordToken) out).getLemq(), exp.getLemq());
        assertEquals(((WordToken) out).getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), ((WordToken) out).getTags()));
    }
    public void testFromStringBackoutEscaped() throws Exception {
        WordToken exp = new WordToken("sim+ple", "", tags2);
        StreamToken out = MLUToken.fromString("^sim+ple<n><sg>$");
        assertEquals((out instanceof WordToken), true);
        assertEquals(((WordToken) out).getLemq(), exp.getLemq());
        assertEquals(((WordToken) out).getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), ((WordToken) out).getTags()));
    }
    public void testGetContentSimple() throws Exception {
        WordToken wt1 = new WordToken("simple", "", tags1);
        WordToken wt2 = new WordToken("test", "", tags2);
        StreamToken out = MLUToken.fromString("^simple<adj>+test<n><sg>$");
        List<WordToken> wtlist = new ArrayList<WordToken>();
        wtlist.add(wt1);
        wtlist.add(wt2);
        assertEquals((out instanceof MLUToken), true);
        MLUToken mlu = (MLUToken) out;
        assertEquals(mlu.getContent(), "simple<adj>+test<n><sg>");
    }
    public void testFromStringSimple() throws Exception {
        WordToken wt1 = new WordToken("simple", "", tags1);
        WordToken wt2 = new WordToken("test", "", tags2);
        StreamToken out = MLUToken.fromString("^simple<adj>+test<n><sg>$");
        List<WordToken> wtlist = new ArrayList<WordToken>();
        wtlist.add(wt1);
        wtlist.add(wt2);
        assertEquals((out instanceof MLUToken), true);
        MLUToken mlu = (MLUToken) out;
        assertEquals(mlu.getLUs().size(), 2);
        assertEquals(mlu.getLUs().get(0).getLemh(), "simple");
        assertEquals(mlu.getLUs().get(1).getLemh(), "test");
    }
    public void testFromStringQueue() throws Exception {
        WordToken wt1 = new WordToken("simple", "# queue", tags1);
        WordToken wt2 = new WordToken("test", "", tags2);
        StreamToken out = MLUToken.fromString("^simple<adj>+test<n><sg># queue$");
        List<WordToken> wtlist = new ArrayList<WordToken>();
        wtlist.add(wt1);
        wtlist.add(wt2);
        assertEquals((out instanceof MLUToken), true);
        MLUToken mlu = (MLUToken) out;
        assertEquals(mlu.getLUs().size(), 2);
        assertEquals(mlu.getLUs().get(0).getLemh(), "simple");
        assertEquals(mlu.getLUs().get(0).getLemq(), "# queue");
        assertEquals(mlu.getLUs().get(1).getLemh(), "test");
    }
    public void testFromStringComplexQueue() throws Exception {
        WordToken wt1 = new WordToken("simple", "# queue", tags1);
        WordToken wt2 = new WordToken("basic", "", tags1);
        WordToken wt3 = new WordToken("test", "", tags2);
        StreamToken out = MLUToken.fromString("^simple<adj>+basic<adj># queue+test<n><sg>$");
        List<WordToken> wtlist = new ArrayList<WordToken>();
        wtlist.add(wt1);
        wtlist.add(wt2);
        wtlist.add(wt3);
        assertEquals((out instanceof MLUToken), true);
        MLUToken mlu = (MLUToken) out;
        assertEquals(mlu.getLUs().size(), 3);
        assertEquals(mlu.getLUs().get(0).getLemh(), "simple");
        assertEquals(mlu.getLUs().get(0).getLemq(), "# queue");
        assertEquals(mlu.getLUs().get(1).getLemh(), "basic");
        assertEquals(mlu.getLUs().get(2).getLemh(), "test");
    }

    public void testFromStringSecondQueue() throws Exception {
        WordToken wt1 = new WordToken("simple", "# queue", tags1);
        WordToken wt2 = new WordToken("basic", "", tags1);
        WordToken wt3 = new WordToken("test", "", tags2);
        StreamToken out = MLUToken.fromString("^simple<adj>+basic<adj># queue+test<n><sg># foo$");
        List<WordToken> wtlist = new ArrayList<WordToken>();
        wtlist.add(wt1);
        wtlist.add(wt2);
        wtlist.add(wt3);
        assertEquals((out instanceof MLUToken), true);
        MLUToken mlu = (MLUToken) out;
        assertEquals(mlu.getLUs().size(), 3);
        assertEquals(mlu.getLUs().get(0).getLemh(), "simple");
        assertEquals(mlu.getLUs().get(0).getLemq(), "# queue");
        assertEquals(mlu.getLUs().get(1).getLemh(), "basic");
        assertEquals(mlu.getLUs().get(2).getLemh(), "test");
    }
}