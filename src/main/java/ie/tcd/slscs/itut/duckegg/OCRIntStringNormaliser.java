/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Jim O'Regan
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

package ie.tcd.slscs.itut.duckegg;

import java.util.*;

public class OCRIntStringNormaliser {
    static final Map<Character, List<Character>> charmap;
    static {
        Map<Character, List<Character>> chartmp = new HashMap<Character, List<Character>>();
        chartmp.put('o', Arrays.asList(new Character[]{'0'}));
        chartmp.put('/', Arrays.asList(new Character[]{'1', '7'}));
        chartmp.put('l', Arrays.asList(new Character[]{'1'}));
        chartmp.put('I', Arrays.asList(new Character[]{'1'}));
        chartmp.put('i', Arrays.asList(new Character[]{'1'}));
        chartmp.put('0', Arrays.asList(new Character[]{'0'}));
        chartmp.put('1', Arrays.asList(new Character[]{'1'}));
        chartmp.put('2', Arrays.asList(new Character[]{'2'}));
        chartmp.put('3', Arrays.asList(new Character[]{'3'}));
        chartmp.put('4', Arrays.asList(new Character[]{'4'}));
        chartmp.put('5', Arrays.asList(new Character[]{'5'}));
        chartmp.put('6', Arrays.asList(new Character[]{'6'}));
        chartmp.put('7', Arrays.asList(new Character[]{'7'}));
        chartmp.put('8', Arrays.asList(new Character[]{'8'}));
        chartmp.put('9', Arrays.asList(new Character[]{'9'}));
        charmap =  Collections.unmodifiableMap(chartmp);
    }
    public static Set<String> normalise(String s) {
        return char_permuter(charmap, new HashSet<String>(), s);
    }
    public static Set<String> char_permuter(Map<Character, List<Character>> map, Set<String> sset, String s) {
        if(s == null || s.equals("")) {
            return sset;
        }
        Set<String> out = new HashSet<String>();
        String rest = "";
        if(s.length() > 1) {
            rest = s.substring(1);
        }
        char cur = s.charAt(0);
        for(Character c : map.get(cur)) {
            if(sset.size() != 0) {
                for (String scur : sset) {
                    out.add(scur + c.toString());
                }
            } else {
                out.add(c.toString());
            }
        }
        return char_permuter(map, out, rest);
    }
    /**
     * join a set of characters with the specified delimiter
     * @param set the list of strings
     * @param delim the delimiter to join them with
     * @return
     */
    public static String join(Set<Character> set, String delim) {
        StringBuilder s = new StringBuilder();
        Iterator<Character> it = set.iterator();
        if (it.hasNext()) {
            s.append(it.next());
        }
        while (it.hasNext()) {
            s.append(delim);
            s.append(it.next());
        }
        return s.toString();
    }
    public static String makeCharacterGroup(Set<Character> set) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(join(set, ""));
        sb.append(']');
        return sb.toString();
    }

}
