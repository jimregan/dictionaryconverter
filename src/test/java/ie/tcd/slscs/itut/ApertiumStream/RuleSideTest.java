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

public class RuleSideTest extends TestCase {
    public void testConvert() throws Exception {
        String input = "^simple<n><sg>{^a<det>$^small<adj>+ish<blah>$ ^test<n><sg>$}$ ^test<n><sg>$";
        List<StreamToken> chunkt = ChunkToken.listFromString(input, true);
        RuleSide rs = RuleSide.convert(chunkt);

        List<WordToken> wtoks = new ArrayList<WordToken>();
        wtoks.add(new WordToken("^a<det>$"));
        wtoks.add(new WordToken("^small<adj>$"));
        wtoks.add(new WordToken("^ish<blah>$"));
        wtoks.add(new WordToken("^test<n><sg>$"));
        wtoks.add(new WordToken("^test<n><sg>$"));
        List<StreamToken> kids = new ArrayList<StreamToken>();
        kids.add(new LUReference(0));
        List<LUReference> mlukids = new ArrayList<LUReference>();
        mlukids.add(new LUReference(1));
        mlukids.add(new LUReference(2));
        kids.add(new MLUReference(mlukids));
        kids.add(new BlankToken(" "));
        kids.add(new LUReference(3));
        List<String> chtags = new ArrayList<String>();
        chtags.add("n");
        chtags.add("sg");
        List<StreamToken> exp = new ArrayList<StreamToken>();
        exp.add(new ChunkToken("simple", chtags, kids));
        exp.add(new BlankToken(" "));
        exp.add(new WordToken("test", "", chtags));

        assertEquals(rs.lus.size(), 5);
    }

}