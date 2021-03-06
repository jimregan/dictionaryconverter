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

import ie.tcd.slscs.itut.ApertiumStream.WordToken;

import java.util.*;

public class DefCats {
    List<DefCat> categories;
    DefCats() {
        categories = new ArrayList<DefCat>();
    }
    public DefCats(List<DefCat> in) {
        this();
        this.categories = in;
    }
    public Map<String, DefCat> getMap() {
        Map<String, DefCat> out = new HashMap<String, DefCat>();
        for (DefCat dc : categories) {
            out.put(dc.name, dc);
        }
        return out;
    }
    public String findTagMatch(List<String> tags) {
        for(DefCat dc : categories) {
            if(dc.tagsMatch(tags)) {
                return dc.name;
            }
        }
        return null;
    }
    public String findTagMatch(String[] tags) {
        return findTagMatch(Arrays.asList(tags));
    }
    public String findTagMatch(String tags) {
        return findTagMatch(tags.split("\\."));
    }
    public void add(DefCat dc) {
        this.categories.add(dc);
    }
    public DefCat get(String s) {
        return getMap().get(s);
    }
    public String getNewName(String s) {
        Map<String, DefCat> map = getMap();
        if(!map.containsKey(s)) {
            return s;
        } else {
            int i = 1;
            String new_name = s + Integer.toString(i);
            while(map.containsKey(new_name)) {
                i++;
                new_name = s + Integer.toString(i);
            }
            return new_name;
        }
    }

    public String findWordToken(WordToken wt) {
            for(DefCat dc : categories) {
                if(dc.wordtokenMatches(wt)) {
                    return dc.name;
                }
            }
            return null;
    }
    public List<DefCat> getCategories() {
        return categories;
    }
}
