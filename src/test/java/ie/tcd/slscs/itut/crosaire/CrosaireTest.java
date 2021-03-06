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

package ie.tcd.slscs.itut.crosaire;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static ie.tcd.slscs.itut.gramadanj.Utils.equalLists;

public class CrosaireTest extends TestCase {
    public void testStringDropsString() throws Exception {
        assertEquals("banlee", Crosaire.stringDropsString("bandoleer", "rod"));
        assertEquals("banlee", Crosaire.stringDropsString("ban dol eer", "ro d"));
        assertEquals("doleer", Crosaire.stringDropsString("bandoleer", "ban"));
        assertEquals("", Crosaire.stringDropsString("bandoleer", "tom"));
    }
    public void testAnagramify() {
        String exp1[] = new String[]{"bca", "acb", "abc", "cba", "bac", "cab"};
        String out1[] = Crosaire.anagramify("abc");
        List<String> expl = Arrays.asList(exp1);
        List<String> outl = Arrays.asList(out1);
        assertEquals(true, equalLists(expl, outl));
    }
}