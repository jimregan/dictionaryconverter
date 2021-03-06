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

import java.util.ArrayList;
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

    public void testFromStringEmptyLemma() throws Exception {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp = new WordToken("", "", tags);
        WordToken out = WordToken.fromString("^<n><sg>$");
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
    public void testGetLemma() {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken in = new WordToken("simple", "# test", tags);
        String exp = "simple# test";
        assertEquals(exp, in.getLemma());
    }
    public void testGetStringCtor() throws Exception {
        List<String> tags = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp = new WordToken("simple", "# test", tags);
        WordToken out = new WordToken("^simple# test<n><sg>$");
        assertEquals(out.getLemq(), exp.getLemq());
        assertEquals(out.getLemh(), exp.getLemh());
        assert(Utils.equalLists(exp.getTags(), out.getTags()));
    }

    public void testListFromString() throws Exception {
        List<String> tags1 = Arrays.asList(new String[]{"adj"});
        WordToken exp1 = new WordToken("simple", "", tags1);
        List<String> tags2 = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp2 = new WordToken("test", "", tags2);
        BlankToken b = new BlankToken(" ");
        List<StreamToken> exp = new ArrayList<StreamToken>();
        exp.add(exp1);
        exp.add(b);
        exp.add(exp2);
        List<StreamToken> out = WordToken.listFromString("^simple<adj>$ ^test<n><sg>$", true);
        assertEquals(out.size(), 3);
        assertEquals(((WordToken) out.get(0)).getLemq(), exp1.getLemq());
        assertEquals(((WordToken) out.get(0)).getLemh(), exp1.getLemh());
        assert(Utils.equalLists(((WordToken) out.get(0)).getTags(), exp1.getTags()));
        assertEquals(((BlankToken) out.get(1)).getContent(), b.getContent());
        assertEquals(((WordToken) out.get(2)).getLemq(), exp2.getLemq());
        assertEquals(((WordToken) out.get(2)).getLemh(), exp2.getLemh());
        assert(Utils.equalLists(((WordToken) out.get(2)).getTags(), exp2.getTags()));
    }

    public void testListFromStringSuper() throws Exception {
        List<String> tags1 = Arrays.asList(new String[]{"adj"});
        WordToken exp1 = new WordToken("simple", "", tags1);
        List<String> tags2 = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp2 = new WordToken("test", "", tags2);
        BlankToken b = new BlankToken("[blah foo bar]");
        List<StreamToken> exp = new ArrayList<StreamToken>();
        exp.add(exp1);
        exp.add(b);
        exp.add(exp2);
        List<StreamToken> out = WordToken.listFromString("^simple<adj>$[blah foo bar]^test<n><sg>$", false);
        assertEquals(out.size(), 3);
        assertEquals(((WordToken) out.get(0)).getLemq(), exp1.getLemq());
        assertEquals(((WordToken) out.get(0)).getLemh(), exp1.getLemh());
        assert(Utils.equalLists(((WordToken) out.get(0)).getTags(), exp1.getTags()));
        assertEquals(((BlankToken) out.get(1)).getContent(), b.getContent());
        assertEquals(((WordToken) out.get(2)).getLemq(), exp2.getLemq());
        assertEquals(((WordToken) out.get(2)).getLemh(), exp2.getLemh());
        assert(Utils.equalLists(((WordToken) out.get(2)).getTags(), exp2.getTags()));
    }

    public void testListFromStringSuperTrim() throws Exception {
        List<String> tags1 = Arrays.asList(new String[]{"adj"});
        WordToken exp1 = new WordToken("simple", "", tags1);
        List<String> tags2 = Arrays.asList(new String[]{"n", "sg"});
        WordToken exp2 = new WordToken("test", "", tags2);
        BlankToken b = new BlankToken(" ");
        List<StreamToken> exp = new ArrayList<StreamToken>();
        exp.add(exp1);
        exp.add(b);
        exp.add(exp2);
        List<StreamToken> out = WordToken.listFromString("^simple<adj>$[blah foo bar]^test<n><sg>$", true);
        assertEquals(out.size(), 3);
        assertEquals(((WordToken) out.get(0)).getLemq(), exp1.getLemq());
        assertEquals(((WordToken) out.get(0)).getLemh(), exp1.getLemh());
        assert(Utils.equalLists(((WordToken) out.get(0)).getTags(), exp1.getTags()));
        assertEquals(((BlankToken) out.get(1)).getContent(), b.getContent());
        assertEquals(((WordToken) out.get(2)).getLemq(), exp2.getLemq());
        assertEquals(((WordToken) out.get(2)).getLemh(), exp2.getLemh());
        assert(Utils.equalLists(((WordToken) out.get(2)).getTags(), exp2.getTags()));
    }

    public void testIsRuleBasis() throws Exception {
        List<StreamToken> out1 = WordToken.listFromString("^simple<adj>$[blah foo bar]^test<n><sg>$", true);
        List<StreamToken> out2 = WordToken.listFromString("^<adj>$ ^<n><sg>$", true);
        List<StreamToken> out3 = WordToken.listFromString("^<adj>$ ^test<n><sg>$", true);
        assertEquals(WordToken.isRuleBasis(out1), false);
        assertEquals(WordToken.isRuleBasis(out2), true);
        assertEquals(WordToken.isRuleBasis(out3), false);
    }
    public void testTagsFromString() {
        WordToken wt = new WordToken();
        wt.setTagsFromString("<foo>");
        assertEquals(1, wt.getTags().size());
        assertEquals("foo", wt.getTags().get(0));
        WordToken wt2 = new WordToken();
        wt.setTagsFromString("<foo><bar><baz>");
        assertEquals(3, wt.getTags().size());
        assertEquals("baz", wt.getTags().get(2));
    }
}
