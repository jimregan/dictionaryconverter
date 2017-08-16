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
    private List<SimpleMacroCall> macrocalls;
    private String left_example;
    private String right_example;
    RuleContainer() {
        this.alignments = new ArrayList<AlignmentPair>();
        this.macrocalls = new ArrayList<SimpleMacroCall>();
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
    public List<SimpleMacroCall> getMacrocalls() {
        return macrocalls;
    }
    public void setMacrocalls(List<SimpleMacroCall> macrocalls) {
        this.macrocalls = macrocalls;
    }
    public String getLeft_example() {
        return left_example;
    }
    public void setLeft_example(String left_example) {
        this.left_example = left_example;
    }
    public String getRight_example() {
        return right_example;
    }
    public void setRight_example(String right_example) {
        this.right_example = right_example;
    }
    public void setExamples(String l, String r) {
        this.left_example = l;
        this.right_example = r;
    }
    public String getCommentString() {
        return left_example + " → " + right_example;
    }
    public static RuleContainer fromString(String s) throws Exception {
        String[] parts = s.split("\\|");
        boolean simple = false;
        if(parts.length < 6 || parts.length > 7) {
            throw new Exception("Incorrect number of fields in line: " + s);
        }
        if(parts.length == 7) {
            simple = true;
            String tag = parts[0].trim();
            List<SimpleToken> left = SimpleToken.listFromString(parts[1].trim());
            List<SimpleToken> right = SimpleToken.listFromString(parts[2].trim());
            List<AlignmentPair> align = AlignmentPair.listFromString(parts[3].trim());
            List<SimpleMacroCall> macros = SimpleMacroCall.listFromString(parts[4].trim());
            String lefteg = parts[5].trim();
            String righteg = parts[6].trim();
        } else {
            simple = false;
            List<StreamToken> left = ChunkToken.listFromString(parts[0].trim(), true);
            List<StreamToken> right = ChunkToken.listFromString(parts[1].trim(), true);
            List<AlignmentPair> align = AlignmentPair.listFromString(parts[2].trim());
            List<SimpleMacroCall> macros = SimpleMacroCall.listFromString(parts[3].trim());
            String lefteg = parts[4].trim();
            String righteg = parts[5].trim();
        }

        return new RuleContainer();
    }
}
