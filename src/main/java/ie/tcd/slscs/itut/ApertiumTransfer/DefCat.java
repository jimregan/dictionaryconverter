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

public class DefCat {
    String name;
    List<CatItem> items;
    DefCat() {
        items = new ArrayList<CatItem>();
    }
    public DefCat(String name, List<CatItem> items) {
        this();
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }
    public List<CatItem> getItems() {
        return items;
    }
    /**
     * Checks if any of the cat-items contained by this def-cat
     * match the set of tags provided
     * @param tags list of strings containing the tags to match
     * @return true if there is a match, false otherwise
     */
    public boolean tagsMatch(List<String> tags) {
        for(CatItem ci : items) {
            if(ci.tagsMatch(tags)) {
                return true;
            }
        }
        return false;
    }
    public boolean tagsMatch(String[] s) {
        return tagsMatch(Arrays.asList(s));
    }
    public boolean tagsMatch(String s) {
        return tagsMatch(s.split("\\."));
    }
    public boolean tagsStartWith(List<String> tags) {
        for(CatItem ci : items) {
            if(ci.tagsStartWith(tags)) {
                return true;
            }
        }
        return false;
    }
    public boolean tagsStartWith(String[] s) {
        return tagsStartWith(Arrays.asList(s));
    }
    public boolean tagsStartWith(String s) {
        return tagsStartWith(s.split("\\."));
    }
}
