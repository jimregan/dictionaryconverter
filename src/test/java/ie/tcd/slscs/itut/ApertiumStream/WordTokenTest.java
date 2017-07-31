package ie.tcd.slscs.itut.ApertiumStream;
/*
 * Copyright © 2017 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
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

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class WordTokenTest extends TestCase {
    public void testFromStringSimple() throws Exception {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp = new WordToken("simple", "", tags);
        WordToken out = WordToken.fromString("^simple<n><sg>$");
        assertEquals(out.getLemq(), exp.getLemq());
        assertEquals(out.getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), out.getTags()));
    }

    public void testFromStringQueue() throws Exception {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp = new WordToken("simple", "# test", tags);
        WordToken out = WordToken.fromString("^simple# test<n><sg>$");
        assertEquals(out.getLemq(), exp.getLemq());
        assertEquals(out.getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), out.getTags()));
    }

    public void testFromStringEscaped() throws Exception {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp = new WordToken("simple/", "# test@", tags);
        WordToken out = WordToken.fromString("^simple\\/# test\\@<n><sg>$");
        assertEquals(out.getLemq(), exp.getLemq());
        assertEquals(out.getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), out.getTags()));
    }

    public void testFromStringNodelims() throws Exception {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp = new WordToken("simple/", "# test@", tags);
        WordToken out = WordToken.fromString("simple\\/# test\\@<n><sg>");
        assertEquals(out.getLemq(), exp.getLemq());
        assertEquals(out.getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), out.getTags()));
    }

    public void testGetTagsString() {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken in = new WordToken("simple", "# test", tags);
        String exp = "<n><sg>";
        assertEquals(exp, in.getTagsString());
    }
    public void testGetLemh() {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken in = new WordToken("simple", "# test", tags);
        String exp = "simple";
        assertEquals(exp, in.getLemh());
    }
    public void testGetLemq() {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken in = new WordToken("simple", "# test", tags);
        String exp = "# test";
        assertEquals(exp, in.getLemq());
    }
    public void testGetStringCtor() throws Exception {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp = new WordToken("simple", "# test", tags);
        WordToken out = new WordToken("^simple# test<n><sg>$");
        assertEquals(out.getLemq(), exp.getLemq());
        assertEquals(out.getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), out.getTags()));
    }
}
