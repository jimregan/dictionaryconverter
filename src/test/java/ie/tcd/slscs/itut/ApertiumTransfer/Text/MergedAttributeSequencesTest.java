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

import static org.junit.Assert.*;

public class MergedAttributeSequencesTest extends TestCase {
    String insrc = "grade = comp sup\n" +
            "gen = m f nt mf GD\n" +
            "num = sg pl sp ND\n" +
            "pers = p1 p2 p3 PD\n" +
            "rel_type = nn an adv\n" +
            "a_noun = n a.acr np.top np.ant np.cog np.al\n" +
            "a_adj = adj adj.sint\n" +
            "tense = pri pres past inf pp ger pprs subs";
    String intrg = "grade = comp\n" +
            "gen = m f mf GD\n" +
            "num = sg pl sp ND\n" +
            "case = com gen voc dat\n" +
            "mut = len hpref ecl defart\n" +
            "pers = p1 p2 p3 PD";
    String insrcch = "gen = m f mf GD\n" +
            "num = sg pl sp ND\n" +
            "det_chunk = DEFART NODET DET\n" +
            "neg_chunk = negative";
    String intrgch = "mut = len hpref ecl defart LENGAN LENPREP\n" +
            "gen = m f mf GD\n" +
            "num = sg pl sp ND\n" +
            "case = case = com gen voc dat GEN2\n" +
            "det_chunk = DEFART NODET DET RMART";
    public void testMerge() throws Exception {

    }
    public void getChunkSequences() throws Exception {
    }

}