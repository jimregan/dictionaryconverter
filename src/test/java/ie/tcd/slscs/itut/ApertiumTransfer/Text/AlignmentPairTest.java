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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AlignmentPairTest extends TestCase {
    AlignmentPair exp = new AlignmentPair("1", "2");
    AlignmentPair exp1 = new AlignmentPair("3", "4");
    public void testFromString() throws Exception {
        String in = "1-2";
        AlignmentPair out = AlignmentPair.fromString(in);
        assertEquals(exp.left, out.left);
        assertEquals(exp.right, out.right);
        assertEquals(true, out.canMakeIndices());
    }

    public void testFromATTString() throws Exception {
        String in = "1:2";
        AlignmentPair out = AlignmentPair.fromATTString(in);
        assertEquals(exp.left, out.left);
        assertEquals(exp.right, out.right);
        assertEquals(true, out.canMakeIndices());
    }

    public void testListFromString() throws Exception {
        List<AlignmentPair> expl = new ArrayList<AlignmentPair>();
        expl.add(exp);
        expl.add(exp1);
        String in = "1-2 3-4";
        List<AlignmentPair> out = AlignmentPair.listFromString(in);
        assertEquals(2, out.size());
        assertEquals(expl.get(0).right, out.get(0).right);
    }
    public void testSimpleAlignments() throws Exception {
        String yes = "1-1 2-2 3-3 4-4";
        List<AlignmentPair> out = AlignmentPair.listFromString(yes);
        assertEquals(true, AlignmentPair.simpleAlignments(out));
        String no = "1-1 1-2 3-3 4-4";
        List<AlignmentPair> out1 = AlignmentPair.listFromString(no);
        assertEquals(false, AlignmentPair.simpleAlignments(out1));
    }

    public void testOffset() throws Exception {
        AlignmentPair a = new AlignmentPair("1", "2");
        AlignmentPair b = new AlignmentPair("4", "1");
        AlignmentPair exp = new AlignmentPair("4", "2");
        AlignmentPair out = AlignmentPair.offsetPair(a, b);
        if(out.left == null || out.right == null) {
            throw new Exception("failed to run test");
        }
        assertEquals(exp.left, out.left);
        assertEquals(exp.right, out.right);
    }
}