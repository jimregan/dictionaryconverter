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

public class ChunkTokenTest extends TestCase {
    public void testFromString() throws Exception {
        List<String> tags1 = Arrays.asList(new String[]{"n", "sg"});
        List<String> tags2 = Arrays.asList(new String[]{"det"});
        List<String> tags3 = Arrays.asList(new String[]{"adj"});

        List<StreamToken> kids = new ArrayList<StreamToken>();
        kids.add(new WordToken("a", "", tags2));
        kids.add(new BlankToken(""));
        kids.add(new WordToken("small", "", tags3));
        kids.add(new BlankToken(" "));
        kids.add(new WordToken("test", "", tags1));
        ChunkToken out = ChunkToken.fromString("^simple<n><sg>{^a<det>$^small<adj>$ ^test<n><sg>$}$");
        ChunkToken exp = new ChunkToken("simple", tags1, kids);
        assertEquals(exp.getLemma(), out.getLemma());
        assertEquals(5, out.getChildren().size());
    }

}