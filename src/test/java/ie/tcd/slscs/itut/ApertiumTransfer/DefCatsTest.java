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

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DefCatsTest extends TestCase {
    DefCats dc;
    public void setUp() {
        List<CatItem> first = new ArrayList<CatItem>();
        first.add(new CatItem("", "adj"));
        first.add(new CatItem("", "adj.sint"));
        first.add(new CatItem("", "adj.sup"));
        List<CatItem> second = new ArrayList<CatItem>();
        second.add(new CatItem("", "n.m.*"));
        second.add(new CatItem("", "n.*.*"));
        List<DefCat> defcats = new ArrayList<DefCat>();
        defcats.add(new DefCat("adj", first));
        defcats.add(new DefCat("noun", second));
        dc = new DefCats(defcats);
    }
    public void testFindTagMatch() {
        assertEquals("adj", dc.findTagMatch(new String[]{"adj", "sint"}));
        assertEquals("noun", dc.findTagMatch("n.f.sg"));
    }
}