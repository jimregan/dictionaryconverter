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

package ie.tcd.slscs.itut.ApertiumTransfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatItem {
    public String name; // post-chunk
    public String lemma;
    public String tags;

    public List<String> getTagsList() {
        List<String> out = new ArrayList<String>();
        if(this.tags == "") {
            return out;
        } else {
            out = Arrays.asList(tags.split("\\."));
            return out;
        }
    }
    public String getFirstTag() {
        return getTagsList().get(0);
    }
    public boolean tagsMatch(List<String> other_tags) {
        if(other_tags.size() != getTagsList().size()) {
            return false;
        }
        for(int i = 0; i < getTagsList().size(); i++) {
            if(!getTagsList().get(i).equals(other_tags.get(i)) && !getTagsList().get(i).equals("*")) {
                return false;
            }
        }
        return true;
    }
    public boolean isValidForTransferType(TransferType t) {
        if(t == TransferType.Postchunk) {
            if(name.equals("")) {
                return false;
            } else if(!"".equals(tags) || !"".equals(lemma)) {
                return false;
            } else {
                return true;
            }
        } else if(!name.equals("")) {
            return false;
        } else if(tags.equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
