/*
 *  The MIT License (MIT)
 *
 *  Copyright © 2017 Trinity College, Dublin
 *  Irish Speech and Language Technology Research Centre
 *  Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 *  An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package ie.tcd.slscs.itut.crosaire;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crosaire {
    public static List<Character> makeCharList(String s) {
        List<Character> out = new ArrayList<Character>();
        for(char c : s.toCharArray()) {
            out.add(new Character(c));
        }
        return out;
    }
    public static String stringDropsString(String haystack, String needle) {
        String last = haystack;
        int sought = haystack.length() - needle.length();
        for(char c : needle.toCharArray()) {
            String cur = "";
            int i = 0;
            for(char d : last.toCharArray()) {
                i++;
                if(c != d) {
                    cur += d;
                    if(i == haystack.length()) {
                        return "";
                    }
                } else {
                    last = cur + last.substring(i);
                    break;
                }
            }
        }
        if(last.length() == sought) {
            return last;
        } else {
            return "";
        }
    }

    public static String[] anagramify(String s) {
        Set<String> perms = permuter(s);
        String[] out = perms.toArray(new String[perms.size()]);
        return out;
    }
    public static void print_anagram(String s) {
        for(String anagram : permuter(s)) {
            System.out.println(anagram);
        }
    }
    /**
     * permutes the input
     * @see https://stackoverflow.com/questions/4240080/generating-all-permutations-of-a-given-string
     * @param s string to permute
     * @return set of permutations of the string
     */
    private static Set<String> permuter(String s) {
        Set<String> out = new HashSet<String>();
        if(s == null || s.equals("")) {
            return out;
        }
        if(s.length() > 1) {
            String next = s.substring(1);
            char first = s.charAt(0);
            Set<String> rest = permuter(next);
            for(String permutation : rest) {
                for(int i = 0; i <= permutation.length(); i++) {
                    String pfx = permutation.substring(0, i);
                    String sfx = permutation.substring(i);
                    out.add(pfx + first + sfx);
                }
            }
        } else {
            out.add(s);
        }
        return out;
    }
}
