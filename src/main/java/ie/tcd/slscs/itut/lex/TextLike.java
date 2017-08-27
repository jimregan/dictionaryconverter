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

package ie.tcd.slscs.itut.lex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TextLike {
    String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public static List<TextLike> fromString(String s) {
        Character special[] = new Character[]{'\\', '{', '}', '+'};
        List<Character> chars = Arrays.asList(special);
        List<TextLike> out = new ArrayList<TextLike>();
        boolean reading = false;
        String cur = "";
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '\\' && (i + 1 < s.length()) && chars.contains(s.charAt(i + 1))) {
                cur += s.charAt(i + 1);
                i += 2;
            }
            if(!reading && s.charAt(i) == '{') {
                out.add(new TextPiece(cur));
                reading = true;
                cur = "";
            } else if(!reading && s.charAt(i) == '+') {
                    out.add(new TextPiece(cur));
                    out.add(new WordBreak());
                    cur = "";
            } else if(reading && s.charAt(i) == '}') {
                out.add(new EntityPiece(cur));
                cur = "";
                reading = false;
                if(i + 1 < s.length() && s.charAt(i + 1) == '{') {
                    i++;
                    reading = true;
                }
            } else {
                cur += s.charAt(i);
            }
        }
        if(reading) {
            out.add(new EntityPiece(cur));
        } else if(s.charAt(s.length() - 1) != '}'){
            out.add(new TextPiece(cur));
        }
        return out;
    }
    public static int getNumberOfWordBreaks(List<TextLike> in) {
        int out = 0;
        if(in.size() == 0) {
            return 0;
        }
        for(TextLike t : in) {
            if(t instanceof WordBreak) {
                out++;
            }
        }
        return out;
    }
    public static boolean isValidBreak(List<TextLike> in) {
        int last = in.size() - 1;
        if(in.get(0) instanceof WordBreak || in.get(last) instanceof WordBreak) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean matchesTags(List<TextLike> in, String tags) {
        int count1 = getNumberOfWordBreaks(in);
        int count2 = 0;
        for(int i = 0; i < tags.length(); i++) {
            if(tags.charAt(i) == '+') {
                count2++;
            }
        }
        return count1 == count2;
    }
}
