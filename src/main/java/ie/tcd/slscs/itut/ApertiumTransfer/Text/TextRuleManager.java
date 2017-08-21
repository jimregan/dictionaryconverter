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

import ie.tcd.slscs.itut.ApertiumStream.RuleContainer;
import ie.tcd.slscs.itut.ApertiumTransfer.TransferType;

import java.util.ArrayList;
import java.util.List;

public class TextRuleManager {
    TransferType type;
    List<Attributes> sourceAttr;
    List<Attributes> targetAttr;
    List<Attributes> sourceAttrChunk;
    List<Attributes> targetAttrChunk;
    List<AttributeSequence> sourceSeq;
    List<AttributeSequence> targetSeq;
    List<AttributeSequence> sourceSeqChunk;
    List<AttributeSequence> targetSeqChunk;
    List<SimpleList> lists;

    List<SimpleTextMacro> macros;
    List<RuleContainer> rules;
    TextRuleManager() {
        this.macros = new ArrayList<SimpleTextMacro>();
        this.rules = new ArrayList<RuleContainer>();
        this.lists = new ArrayList<SimpleList>();
        this.sourceAttr = new ArrayList<Attributes>();
        this.targetAttr = new ArrayList<Attributes>();
        this.sourceAttrChunk = new ArrayList<Attributes>();
        this.targetAttrChunk = new ArrayList<Attributes>();
        this.sourceSeq = new ArrayList<AttributeSequence>();
        this.sourceSeqChunk = new ArrayList<AttributeSequence>();
        this.targetSeq = new ArrayList<AttributeSequence>();
        this.targetSeqChunk = new ArrayList<AttributeSequence>();
    }
    TextRuleManager(List<SimpleTextMacro> macros, List<RuleContainer> rules) {
        this.macros = macros;
        this.rules = rules;
    }

    public List<Attributes> getSourceAttr() {
        return sourceAttr;
    }
    public void setSourceAttr(List<Attributes> sourceAttr) {
        this.sourceAttr = sourceAttr;
    }
    public List<Attributes> getTargetAttr() {
        return targetAttr;
    }
    public void setTargetAttr(List<Attributes> targetAttr) {
        this.targetAttr = targetAttr;
    }
    public List<Attributes> getSourceAttrChunk() {
        return sourceAttrChunk;
    }
    public void setSourceAttrChunk(List<Attributes> sourceAttrChunk) {
        this.sourceAttrChunk = sourceAttrChunk;
    }
    public List<Attributes> getTargetAttrChunk() {
        return targetAttrChunk;
    }
    public void setTargetAttrChunk(List<Attributes> targetAttrChunk) {
        this.targetAttrChunk = targetAttrChunk;
    }
    public List<AttributeSequence> getSourceSeq() {
        return sourceSeq;
    }
    public void setSourceSeq(List<AttributeSequence> sourceSeq) {
        this.sourceSeq = sourceSeq;
    }
    public List<AttributeSequence> getTargetSeq() {
        return targetSeq;
    }
    public void setTargetSeq(List<AttributeSequence> targetSeq) {
        this.targetSeq = targetSeq;
    }
    public List<AttributeSequence> getSourceSeqChunk() {
        return sourceSeqChunk;
    }
    public void setSourceSeqChunk(List<AttributeSequence> sourceSeqChunk) {
        this.sourceSeqChunk = sourceSeqChunk;
    }
    public List<AttributeSequence> getTargetSeqChunk() {
        return targetSeqChunk;
    }
    public void setTargetSeqChunk(List<AttributeSequence> targetSeqChunk) {
        this.targetSeqChunk = targetSeqChunk;
    }
    public List<SimpleList> getLists() {
        return lists;
    }
    public void setLists(List<SimpleList> lists) {
        this.lists = lists;
    }
    public List<SimpleTextMacro> getMacros() {
        return macros;
    }
    public void setMacros(List<SimpleTextMacro> macros) {
        this.macros = macros;
    }
    public List<RuleContainer> getRules() {
        return rules;
    }
    public void setRules(List<RuleContainer> rules) {
        this.rules = rules;
    }
    public TransferType getType() {
        return type;
    }
    public void setType(TransferType type) {
        this.type = type;
    }

    public class Builder {
        List<Attributes> sourceAttr;
        List<Attributes> targetAttr;
        List<Attributes> sourceAttrChunk;
        List<Attributes> targetAttrChunk;
        List<AttributeSequence> sourceSeq;
        List<AttributeSequence> targetSeq;
        List<AttributeSequence> sourceSeqChunk;
        List<AttributeSequence> targetSeqChunk;
        List<SimpleList> lists;
        List<SimpleTextMacro> macros;
        List<RuleContainer> rules;
        TransferType type;
        public Builder() {
            this.macros = new ArrayList<SimpleTextMacro>();
            this.rules = new ArrayList<RuleContainer>();
            this.lists = new ArrayList<SimpleList>();
            this.sourceAttr = new ArrayList<Attributes>();
            this.targetAttr = new ArrayList<Attributes>();
            this.sourceAttrChunk = new ArrayList<Attributes>();
            this.targetAttrChunk = new ArrayList<Attributes>();
            this.sourceSeq = new ArrayList<AttributeSequence>();
            this.sourceSeqChunk = new ArrayList<AttributeSequence>();
            this.targetSeq = new ArrayList<AttributeSequence>();
            this.targetSeqChunk = new ArrayList<AttributeSequence>();
        }
        public Builder setType(TransferType type) {
            this.type = type;
            return this;
        }
        public Builder withMacros(List<SimpleTextMacro> macros) {
            this.macros = macros;
            return this;
        }
        public Builder withRules(List<RuleContainer> rules) {
            this.rules = rules;
            return this;
        }
        public Builder withLists(List<SimpleList> lists) {
            this.lists = lists;
            return this;
        }
        public Builder withSourceAttributes(List<Attributes> src) {
            this.sourceAttr = src;
            return this;
        }
        public Builder withSourceChunkAttributes(List<Attributes> src) {
            this.sourceAttrChunk = src;
            return this;
        }
        public Builder withTargetAttributes(List<Attributes> src) {
            this.targetAttr = src;
            return this;
        }
        public Builder withTargetChunkAttributes(List<Attributes> src) {
            this.targetAttrChunk = src;
            return this;
        }
        public Builder withSourceSequence(List<AttributeSequence> src) {
            this.sourceSeq = src;
            return this;
        }
        public Builder withSourceChunkSequence(List<AttributeSequence> src) {
            this.sourceSeqChunk = src;
            return this;
        }
        public Builder withTargetSequence(List<AttributeSequence> src) {
            this.targetSeq = src;
            return this;
        }
        public Builder withTargetChunkSequence(List<AttributeSequence> src) {
            this.targetSeqChunk = src;
            return this;
        }
        public TextRuleManager build() {
            TextRuleManager out = new TextRuleManager();
            out.setLists(this.lists);
            out.setRules(this.rules);
            out.setMacros(this.macros);
            out.setSourceAttr(this.sourceAttr);
            out.setSourceAttrChunk(this.sourceAttrChunk);
            out.setTargetAttr(this.targetAttr);
            out.setTargetAttrChunk(this.targetAttrChunk);
            out.setSourceSeq(this.sourceSeq);
            out.setSourceSeqChunk(this.sourceSeqChunk);
            out.setTargetSeq(this.targetSeq);
            out.setTargetSeqChunk(this.targetSeqChunk);
            return out;
        }
    }
    class TextFileBuilder extends Builder {
        public Builder setMacrosFromFile(String filename) throws Exception {
            macros = SimpleTextMacro.fromFile(filename);
            return this;
        }
        public Builder setRulesFromFile(String filename) throws Exception {
            this.rules = RuleContainer.fromFile(filename);
            return this;
        }
        public Builder setListsFromFile(String filename) throws Exception {
            this.lists = SimpleList.fromFile(filename);
            return this;
        }
        public Builder setSourceAttributesFromFile(String filename) throws Exception {
            this.sourceAttr = Attributes.fromFile(filename);
            return this;
        }
        public Builder setSourceChunkAttributesFromFile(String filename) throws Exception {
            this.sourceAttrChunk = Attributes.fromFile(filename);
            return this;
        }
        public Builder setTargetAttributesFromFile(String filename) throws Exception {
            this.targetAttr = Attributes.fromFile(filename);
            return this;
        }
        public Builder setTargetChunkAttributesFromFile(String filename) throws Exception {
            this.targetAttrChunk = Attributes.fromFile(filename);
            return this;
        }
        public Builder setSourceSequenceFromFile(String filename) throws Exception {
            this.sourceSeq = AttributeSequence.fromFile(filename);
            return this;
        }
        public Builder setSourceChunkSequenceFromFile(String filename) throws Exception {
            this.sourceSeqChunk = AttributeSequence.fromFile(filename);
            return this;
        }
        public Builder setTargetSequenceFromFile(String filename) throws Exception {
            this.targetSeq = AttributeSequence.fromFile(filename);
            return this;
        }
        public Builder setTargetChunkSequenceFromFile(String filename) throws Exception {
            this.targetSeqChunk = AttributeSequence.fromFile(filename);
            return this;
        }
    }
}
