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

import ie.tcd.slscs.itut.DictionaryConverter.TrxProc;

import java.util.ArrayList;
import java.util.List;

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
}
