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

import ie.tcd.slscs.itut.ApertiumStream.*;
import ie.tcd.slscs.itut.ApertiumTransfer.DefCats;
import ie.tcd.slscs.itut.ApertiumTransfer.Pattern;
import ie.tcd.slscs.itut.ApertiumTransfer.PatternItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RuleSide {
    List<WordToken> lus;
    List<StreamToken> tokens;

    RuleSide() {
        lus = new ArrayList<WordToken>();
        tokens = new ArrayList<StreamToken>();
    }
    RuleSide(List<WordToken> lus, List<StreamToken> tokens) {
        this.lus = lus;
        this.tokens = tokens;
    }

    /**
     * Rewrite the word tokens to include source language alignment, and tag properties
     * @param clippable for the associated AttributeSequence, contains a map of boolean values denoting whether or
     *                  not the entries in the attribute sequence can be written as clips
     * @param alignments map from which to attach the source language alignment
     * @param aseq The AttributeSequence associated with the first tag of the WordToken
     * @throws Exception if the WordToken has multiple alignments, an exception is thrown
     */
    public void rewriteLUs(AttributeSequenceClippable clippable, Map<String, List<String>> alignments, Map<String, AttributeSequence> aseq) throws Exception {
        List<WordToken> newlus = new ArrayList<WordToken>();
        for(int i = 0; i < lus.size(); i++) {
            WordToken cur = lus.get(i);
            WritableWordToken wcur = new WritableWordToken(cur);
            String alignkey = Integer.toString(i + 1);
            List<String> curalign = alignments.get(alignkey);
            if(curalign.size() != 1) {
                throw new Exception("Cannot currently handle multiple alignments");
            }
            wcur.setAlignment(curalign.get(0));
            wcur.setClippable(clippable.getClippable().get(cur.getFirstTag()));
            if(aseq.containsKey(cur.getFirstTag())) {
                wcur.setAttribseq(aseq.get(cur.getFirstTag()));
            }
        }
    }
    public class Builder {
        private List<WordToken> lus;
        private List<StreamToken> tokens;
        public Builder() {
            this.lus = new ArrayList<WordToken>();
            this.tokens = new ArrayList<StreamToken>();
        }
        public Builder withLUs(List<WordToken> lus) {
            this.lus = lus;
            return this;
        }
        public Builder withTokens(List<StreamToken> tokens) {
            this.tokens = tokens;
            return this;
        }
        public Builder addToken(StreamToken input) {
            int lucount = this.lus.size();
            if(input instanceof WordToken) {
                lus.add((WordToken) input);
                LUReference ref = new LUReference(lucount);
                tokens.add(ref);
                lucount++;
            } else if(input instanceof BlankToken) {
                tokens.add((BlankToken) input);
            } else if(input instanceof MLUToken) {
                MLUReference mluref = MLUReference.fromMLUToken((MLUToken) input, lucount);
                for (WordToken wt : ((MLUToken) input).getLUs()) {
                    wt.setInMLU();
                    lus.add(wt);
                    lucount++;
                }
                tokens.add(mluref);
            } else if(input instanceof ChunkToken) {
                List<StreamToken> chtokens = new ArrayList<StreamToken>();
                ChunkToken ch = (ChunkToken) input;
                for(StreamToken st1 : ch.getChildren()) {
                    if(st1 instanceof WordToken) {
                        lus.add((WordToken) st1);
                        LUReference ref = new LUReference(lucount);
                        chtokens.add(ref);
                        lucount++;
                    } else if(st1 instanceof BlankToken) {
                        chtokens.add((BlankToken) st1);
                    } else if(st1 instanceof MLUToken) {
                        MLUReference mluref = MLUReference.fromMLUToken((MLUToken) st1, lucount);
                        for (WordToken wt : ((MLUToken) st1).getLUs()) {
                            lus.add(wt);
                            lucount++;
                        }
                        chtokens.add(mluref);
                    }
                }
                tokens.add(new ChunkToken(ch.getLemma(), ch.getTags(), chtokens));
            }
            return this;
        }
        public RuleSide build() {
            return new RuleSide(this.lus, this.tokens);
        }
    }

    public List<WordToken> getLUs() {
        return lus;
    }
    public List<StreamToken> getTokens() {
        return tokens;
    }
    public static RuleSide convert(List<StreamToken> input) {
        List<WordToken> lus = new ArrayList<WordToken>();
        List<StreamToken> tokens = new ArrayList<StreamToken>();
        int lucount = 0;
        for(StreamToken st : input) {
            if(st instanceof WordToken) {
                lus.add((WordToken) st);
                LUReference ref = new LUReference(lucount);
                tokens.add(ref);
                lucount++;
            } else if(st instanceof BlankToken) {
                tokens.add((BlankToken) st);
            } else if(st instanceof MLUToken) {
                MLUReference mluref = MLUReference.fromMLUToken((MLUToken) st, lucount);
                for (WordToken wt : ((MLUToken) st).getLUs()) {
                    wt.setInMLU();
                    lus.add(wt);
                    lucount++;
                }
                tokens.add(mluref);
            } else if(st instanceof ChunkToken) {
                List<StreamToken> chtokens = new ArrayList<StreamToken>();
                ChunkToken ch = (ChunkToken) st;
                for(StreamToken st1 : ch.getChildren()) {
                    if(st1 instanceof WordToken) {
                        lus.add((WordToken) st1);
                        LUReference ref = new LUReference(lucount);
                        chtokens.add(ref);
                        lucount++;
                    } else if(st1 instanceof BlankToken) {
                        chtokens.add((BlankToken) st1);
                    } else if(st1 instanceof MLUToken) {
                        MLUReference mluref = MLUReference.fromMLUToken((MLUToken) st1, lucount);
                        for (WordToken wt : ((MLUToken) st1).getLUs()) {
                            lus.add(wt);
                            lucount++;
                        }
                        chtokens.add(mluref);
                    }
                }
                tokens.add(new ChunkToken(ch.getLemma(), ch.getTags(), chtokens));
            }
        }
        return new RuleSide(lus, tokens);
    }
    public static RuleSide convertSimpleTokens(List<SimpleToken> input) {
        List<WordToken> lus = new ArrayList<WordToken>();
        List<StreamToken> tokens = new ArrayList<StreamToken>();
        Iterator<SimpleToken> it = input.iterator();
        int lucount = 0;
        if(it.hasNext()) {
            SimpleToken st = it.next();
            lus.add(st);
            tokens.add(new LUReference(lucount));
        }
        lucount++;
        int blankidx = 1;
        while(it.hasNext()) {
            tokens.add(new PositionedBlank(blankidx));
            blankidx++;
            SimpleToken st = it.next();
            lus.add(st);
            tokens.add(new LUReference(lucount));
            lucount++;
        }
        return new RuleSide(lus, tokens);
    }
    public static Pattern toPattern(RuleSide rs, DefCats dc) {
        List<PatternItem> items = new ArrayList<PatternItem>();
        String name = "";
        for(WordToken wt : rs.getLUs()) {
            name = dc.findWordToken(wt);
            if(name != null) {
                items.add(new PatternItem(name));
            } else {
                System.err.println("No pattern-item found for token " + wt.toString());
            }
        }
        return new Pattern(items);
    }
}
