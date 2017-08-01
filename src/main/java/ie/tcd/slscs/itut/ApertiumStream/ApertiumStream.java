/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 * An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApertiumStream {
    public final static List<Character> escapes;
    static {
        escapes = Collections.unmodifiableList(toCharacterList("[]{}^$/\\@<>"));
    }
    static List<Character> toCharacterList(String s) {
        List<Character> ret = new ArrayList<Character>();
        for (char c : s.toCharArray()) {
            ret.add(new Character(c));
        }
        return ret;
    }
    public static boolean isEscape(Character c) {
        return escapes.contains(c);
    }
    public static boolean isEscape(char c) {
        return escapes.contains(new Character(c));
    }
    public static String escapeString(String s) {
        String out = "";
        for (int i = 0; i < s.length(); i++) {
            if (isEscape(s.charAt(i))) {
                out += '\\';
            }
            out += s.charAt(i);
        }
        return out;
    }
    public static List<String> escapeStringList(List<String> list) {
        List<String> out = new ArrayList<String>();
        for (String s : list) {
            out.add(escapeString(s));
        }
        return out;
    }
}
