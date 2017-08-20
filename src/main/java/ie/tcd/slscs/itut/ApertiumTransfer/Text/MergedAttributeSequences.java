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

import java.util.*;

public class MergedAttributeSequences {
    List<AttributeSequence> source;
    List<AttributeSequence> target;
    List<AttributeSequence> source_chunk;
    List<AttributeSequence> target_chunk;
    Map<String, Set<String>> sequences;
    Map<String, Set<String>> chunk_sequences;
    Map<String, Boolean> clippable;
    Map<String, Boolean> agreement;
    MergedAttributeSequences() {
        this.sequences = new HashMap<String, Set<String>>();
        this.chunk_sequences = new HashMap<String, Set<String>>();
        this.clippable = new HashMap<String, Boolean>();
        this.agreement = new HashMap<String, Boolean>();
    }
    public MergedAttributeSequences(List<AttributeSequence> source, List<AttributeSequence> target, List<AttributeSequence> source_chunk, List<AttributeSequence> target_chunk) {
        this();
        this.source = source;
        this.source_chunk = source_chunk;
        this.target = target;
        this.target_chunk = target_chunk;
        merge();
    }
    void merge() {
        for(AttributeSequence as : source) {
            Set<String> tags = new HashSet<String>();
            tags.addAll(as.tags);
            sequences.put(as.name, tags);
            clippable.put(as.name, true);
        }
        for(AttributeSequence as : target) {
            if(sequences.containsKey(as.name)) {
                sequences.get(as.name).addAll(as.tags);
            } else {
                clippable.put(as.name, false);
                Set<String> tags = new HashSet<String>();
                tags.addAll(as.tags);
                sequences.put(as.name, tags);
            }
        }
        for(AttributeSequence as : source_chunk) {
            mergeInnerAdd(as);
        }
        for(AttributeSequence as : target_chunk) {
            mergeInnerAdd(as);
        }
    }

    private void mergeInnerAdd(AttributeSequence as) {
        if(sequences.containsKey(as.name)) {
            agreement.put(as.name, true);
            sequences.get(as.name).addAll(as.tags);
        } else {
            clippable.put(as.name, false);
            Set<String> tags = new HashSet<String>();
            tags.addAll(as.tags);
            chunk_sequences.put(as.name, tags);
        }
    }
    public Map<String, Set<String>> getSequences() {
        return sequences;
    }
    public Map<String, Set<String>> getChunkSequences() {
        return chunk_sequences;
    }
    public Map<String, Boolean> getClippable() {
        return clippable;
    }
    public boolean isClippable(String s) {
        return clippable.get(s);
    }
    public boolean hasChunkAgreement(String s) {
        if(agreement.containsKey(s)) {
            return agreement.get(s);
        } else {
            return false;
        }
    }
}
