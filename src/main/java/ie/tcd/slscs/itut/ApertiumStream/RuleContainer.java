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
import ie.tcd.slscs.itut.ApertiumTransfer.Text.SimpleMacroCall;

import java.util.ArrayList;
import java.util.List;

public class RuleContainer {
    private String tag;
    private RuleSide left;
    private RuleSide right;
    private List<AlignmentPair> alignments;
    private List<SimpleMacroCall> leftmacro;
    private List<SimpleMacroCall> rightmacro;
    private String left_example;
    private String right_example;
    private boolean simple = false;
    RuleContainer() {
        this.alignments = new ArrayList<AlignmentPair>();
        this.leftmacro = new ArrayList<SimpleMacroCall>();
        this.rightmacro = new ArrayList<SimpleMacroCall>();
    }
    public RuleContainer(RuleSide left, RuleSide right, List<AlignmentPair> alignments) {
        this();
        this.left = left;
        this.right = right;
        this.alignments = alignments;
    }
    public RuleContainer(String tag, RuleSide left, RuleSide right, List<AlignmentPair> alignments) {
        this();
        this.tag = tag;
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
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public List<SimpleMacroCall> getLeftMacrocalls() {
        return leftmacro;
    }
    public void setLeftMacrocalls(List<SimpleMacroCall> macrocalls) {
        this.leftmacro = macrocalls;
    }
    public List<SimpleMacroCall> getRightMacrocalls() {
        return rightmacro;
    }
    public void setRightMacrocalls(List<SimpleMacroCall> macrocalls) {
        this.rightmacro = macrocalls;
    }
    public String getLeftExample() {
        return left_example;
    }
    public void setLeftExample(String left_example) {
        this.left_example = left_example;
    }
    public String getRightExample() {
        return right_example;
    }
    public void setRightExample(String right_example) {
        this.right_example = right_example;
    }
    public void setExamples(String l, String r) {
        this.left_example = l;
        this.right_example = r;
    }
    public String getCommentString() {
        return left_example + " → " + right_example;
    }
    public boolean isSimple() {
        return simple;
    }
    public void setSimple(boolean simple) {
        this.simple = simple;
    }
    public static RuleContainer fromString(String s) throws Exception {
        String[] parts = s.split("\\|");
        if(parts.length < 7 || parts.length > 8) {
            throw new Exception("Incorrect number of fields in line: " + s);
        }
        if(parts.length == 8) {
            String tag = parts[0].trim();
            List<SimpleToken> left = SimpleToken.listFromString(parts[1].trim());
            List<SimpleToken> right = SimpleToken.listFromString(parts[2].trim());
            RuleSide rsleft = RuleSide.convertSimpleTokens(left);
            RuleSide rsright = RuleSide.convertSimpleTokens(right);
            List<AlignmentPair> align = AlignmentPair.listFromString(parts[3].trim());
            RuleContainer out = new RuleContainer(tag, rsleft, rsright, align);
            List<SimpleMacroCall> lmacros = SimpleMacroCall.listFromString(parts[4].trim());
            List<SimpleMacroCall> rmacros = SimpleMacroCall.listFromString(parts[5].trim());
            out.setLeftMacrocalls(lmacros);
            out.setRightMacrocalls(rmacros);
            String lefteg = parts[6].trim();
            String righteg = parts[7].trim();
            out.setExamples(lefteg, righteg);
            out.setSimple(true);
            return out;
        } else {
            List<StreamToken> left = ChunkToken.listFromString(parts[0].trim(), true);
            List<StreamToken> right = ChunkToken.listFromString(parts[1].trim(), true);
            RuleSide rsleft = RuleSide.convert(left);
            RuleSide rsright = RuleSide.convert(right);
            List<AlignmentPair> align = AlignmentPair.listFromString(parts[2].trim());
            RuleContainer out = new RuleContainer(rsleft, rsright, align);
            List<SimpleMacroCall> lmacros = SimpleMacroCall.listFromString(parts[3].trim());
            out.setLeftMacrocalls(lmacros);
            List<SimpleMacroCall> rmacros = SimpleMacroCall.listFromString(parts[4].trim());
            out.setRightMacrocalls(rmacros);
            String lefteg = parts[5].trim();
            String righteg = parts[6].trim();
            out.setExamples(lefteg, righteg);
            return out;
        }
    }
    public static RuleContainer swap(RuleContainer rc) {
        RuleContainer out = new RuleContainer();
        out.setTag(rc.getTag());
        out.setRight(rc.getLeft());
        out.setLeft(rc.getRight());
        out.setLeftExample(rc.getRightExample());
        out.setRightExample(rc.getLeftExample());
        out.setLeftMacrocalls(rc.getRightMacrocalls());
        out.setRightMacrocalls(rc.getLeftMacrocalls());
        out.setSimple(rc.isSimple());
        out.setAlignments(AlignmentPair.reverseList(rc.getAlignments()));
        return out;
    }

    public static RuleContainer insertAtAlignmentPoint(RuleContainer base, RuleContainer insert, AlignmentPair a) {
        return null;
    }
}
