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

import ie.tcd.slscs.itut.ApertiumTransfer.Text.AlignmentPair;

import java.util.ArrayList;
import java.util.List;

public class RuleContainer {
    private RuleSide left;
    private RuleSide right;
    private List<AlignmentPair> alignments;
    RuleContainer() {
        this.alignments = new ArrayList<AlignmentPair>();
    }
    public RuleContainer(RuleSide left, RuleSide right, List<AlignmentPair> alignments) {
        this();
        this.left = left;
        this.right = right;
        this.alignments = alignments;
    }
    public RuleSide getLeft() {
        return left;
    }
    public void setLeft(RuleSide left) {
        this.left = left;
    }
    public RuleSide getRight() {
        return right;
    }
    public void setRight(RuleSide right) {
        this.right = right;
    }
    public List<AlignmentPair> getAlignments() {
        return alignments;
    }
    public void setAlignments(List<AlignmentPair> alignments) {
        this.alignments = alignments;
    }
}
