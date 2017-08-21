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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AttributeSequenceTest extends TestCase {
    String fakefile = "noun = a_noun strength gen num case mut emph\n" +
            "adj = a_adj strength gen num case mut\n" +
            "verb = a_verb tense pers num neg itg mut emph\n" +
            "det = a_det num gen\n" +
            "prn = a_prn pers num gen emph\n";
    public void testFromString() throws Exception {
        List<String> taglist = new ArrayList<String>();
        taglist.add("a_det");
        taglist.add("num");
        taglist.add("gen");
        String in = "det = a_det num gen";
        AttributeSequence out = AttributeSequence.fromString(in);
        AttributeSequence exp = new AttributeSequence("det", taglist);
        assertEquals(exp.name, out.name);
        assertEquals(exp.tags.size(), out.tags.size());
        assertEquals(exp.tags.get(0), out.tags.get(0));
    }

    public void testFromFile() throws Exception {
        List<AttributeSequence> out = AttributeSequence.fromFile(new ByteArrayInputStream(fakefile.getBytes()));
        assertEquals(5, out.size());
        assertEquals("noun", out.get(0).name);
        assertEquals("prn", out.get(4).name);
        assertEquals(5, out.get(4).tags.size());
    }

}