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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AttributeSequenceClippable holds, per part of speech, whether or not
 * an individual attribute is ``clippable'' -- i.e., if it can be represented
 * in Apertium's transfer as a clip, or if it must be set to that part of
 * speech in another way, either by setting a variable, or by writing a
 * literal tag.
 */
public class AttributeSequenceClippable {
    Map<String, Map<String, Boolean>> clippable;
    AttributeSequenceClippable() {
        this.clippable = new HashMap<String, Map<String, Boolean>>();
    }
    public Map<String, Map<String, Boolean>> getClippable() {
        return clippable;
    }
    public void setClippable(Map<String, Map<String, Boolean>> clippable) {
        this.clippable = clippable;
    }
    public void setSourceLanguage(List<AttributeSequence> as) {
        for(AttributeSequence at : as) {
            String name = at.name;
            if(!clippable.containsKey(name)) {
                clippable.put(name, new HashMap<String, Boolean>());
            }
            for(String item : at.tags) {
                clippable.get(name).put(item, true);
            }
        }
    }
    public void setTargetLanguage(List<AttributeSequence> as) {
        for(AttributeSequence at : as) {
            String name = at.name;
            if(!clippable.containsKey(name)) {
                clippable.put(name, new HashMap<String, Boolean>());
                for(String item : at.tags) {
                    clippable.get(name).put(item, false);
                }
            } else {
                for(String item : at.tags) {
                    if(!clippable.get(name).containsKey(item)) {
                        clippable.get(name).put(item, false);
                    }
                }
            }
        }
    }
}
