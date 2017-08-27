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
    AttributeSequenceClippable clippable;
    AttributeSequenceClippable clippableChunk;
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
        this.clippable = new AttributeSequenceClippable();
        this.clippableChunk = new AttributeSequenceClippable();
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
    public AttributeSequenceClippable getClippable() {
        return clippable;
    }
    public void setClippable(AttributeSequenceClippable clippable) {
        this.clippable = clippable;
    }
    public AttributeSequenceClippable getClippableChunk() {
        return clippableChunk;
    }
    public void setClippableChunk(AttributeSequenceClippable clippableChunk) {
        this.clippableChunk = clippableChunk;
    }
    public void rewriteLUs() throws Exception {
        for(RuleContainer rc : rules) {
            rc.rewriteLUs(clippable, AttributeSequence.listToMap(getTargetSeq()));
        }
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
        AttributeSequenceClippable clippable;
        AttributeSequenceClippable clippableChunk;
        private boolean haveSourceAttr = false;
        private boolean haveTargetAttr = false;
        private boolean haveSourceAttrChunk = false;
        private boolean haveTargetAttrChunk = false;
        private boolean haveLists = false;
        private boolean haveSourceSeq = false;
        private boolean haveTargetSeq = false;
        private boolean haveSourceSeqChunk = false;
        private boolean haveTargetSeqChunk = false;
        private boolean haveMacros = false;
        private boolean haveRules = false;
        private boolean haveType = false;
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
            this.clippable = new AttributeSequenceClippable();
            this.clippableChunk = new AttributeSequenceClippable();
        }
        public Builder setType(TransferType type) {
            this.type = type;
            this.haveType = true;
            return this;
        }
        public Builder withMacros(List<SimpleTextMacro> macros) {
            this.macros = macros;
            this.haveMacros = true;
            return this;
        }
        public Builder withRules(List<RuleContainer> rules) {
            this.rules = rules;
            this.haveRules = true;
            return this;
        }
        public Builder withLists(List<SimpleList> lists) {
            this.lists = lists;
            this.haveLists = true;
            return this;
        }
        public Builder withAttributes(List<Attributes> src, List<Attributes> trg) {
            this.sourceAttr = src;
            this.targetAttr = trg;
            this.haveSourceAttr = true;
            this.haveTargetAttr = true;
            return this;
        }
        public Builder withSourceChunkAttributes(List<Attributes> src, List<Attributes> trg) {
            this.sourceAttrChunk = src;
            this.targetAttrChunk = trg;
            this.haveSourceAttrChunk = true;
            this.haveTargetAttrChunk = true;
            return this;
        }
        public Builder withSequences(List<AttributeSequence> src, List<AttributeSequence> trg) {
            this.sourceSeq = src;
            this.targetSeq = trg;
            this.haveSourceSeq = true;
            this.haveTargetSeq = true;
            this.clippable.setSourceLanguage(src);
            this.clippable.setTargetLanguage(trg);
            return this;
        }
        public Builder withSourceChunkSequence(List<AttributeSequence> src, List<AttributeSequence> trg) {
            this.sourceSeqChunk = src;
            this.targetSeqChunk = trg;
            this.haveSourceSeqChunk = true;
            this.haveTargetSeqChunk = true;
            this.clippableChunk.getClippable().putAll(this.clippable.getClippable());
            this.clippableChunk.setSourceLanguage(sourceSeqChunk);
            this.clippableChunk.setTargetLanguage(targetSeqChunk);
            return this;
        }
        public TextRuleManager build() throws Exception {
            TextRuleManager out = new TextRuleManager();
            out.setLists(this.lists);
            out.setMacros(this.macros);
            out.setSourceAttr(this.sourceAttr);
            out.setSourceAttrChunk(this.sourceAttrChunk);
            out.setTargetAttr(this.targetAttr);
            out.setTargetAttrChunk(this.targetAttrChunk);
            out.setSourceSeq(this.sourceSeq);
            out.setSourceSeqChunk(this.sourceSeqChunk);
            out.setTargetSeq(this.targetSeq);
            out.setTargetSeqChunk(this.targetSeqChunk);
            out.setClippable(this.clippable);
            out.setClippableChunk(this.clippableChunk);
            out.rewriteLUs();
            out.setRules(this.rules);
            return out;
        }
    }
    class TextFileBuilder extends Builder {
        public Builder setMacrosFromFile(String filename) throws Exception {
            setMacros(SimpleTextMacro.fromFile(filename));
            return this;
        }
        public Builder setRulesFromFile(String filename) throws Exception {
            setRules(RuleContainer.fromFile(filename));
            return this;
        }
        public Builder setListsFromFile(String filename) throws Exception {
            setLists(SimpleList.fromFile(filename));
            return this;
        }
        public Builder setAttributesFromFile(String src, String trg) throws Exception {
            this.sourceAttr = Attributes.fromFile(src);
            this.targetAttr = Attributes.fromFile(trg);
            return this;
        }
        public Builder setChunkAttributesFromFile(String src, String trg) throws Exception {
            this.sourceAttrChunk = Attributes.fromFile(src);
            this.targetAttrChunk = Attributes.fromFile(trg);
            return this;
        }
        public Builder setSequenceFromFile(String src, String trg) throws Exception {
            this.sourceSeq = AttributeSequence.fromFile(src);
            this.targetSeq = AttributeSequence.fromFile(trg);
            this.clippable.setSourceLanguage(sourceSeq);
            this.clippable.setTargetLanguage(targetSeq);
            return this;
        }
        public Builder setSourceChunkSequenceFromFile(String src, String trg) throws Exception {
            this.sourceSeqChunk = AttributeSequence.fromFile(src);
            this.targetSeqChunk = AttributeSequence.fromFile(trg);
            this.clippableChunk.getClippable().putAll(this.clippable.getClippable());
            this.clippableChunk.setSourceLanguage(sourceSeqChunk);
            this.clippableChunk.setTargetLanguage(targetSeqChunk);
            return this;
        }
    }
}
