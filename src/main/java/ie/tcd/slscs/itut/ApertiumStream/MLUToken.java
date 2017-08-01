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

package ie.tcd.slscs.itut.ApertiumStream;

import ie.tcd.slscs.itut.gramadanj.Utils;

import java.util.ArrayList;
import java.util.List;

public class MLUToken extends StreamToken {
    List<WordToken> lus;
    MLUToken() {
        lus = new ArrayList<WordToken>();
    }
    MLUToken(List<WordToken> l) {
        this();
        this.lus = l;
    }
    @Override
    public String toString() {
        return "^" + getContent() + "$";
    }

    @Override
    public String getContent() {
        List<String> tmp = new ArrayList<String>();
        String queue = "";
        if (lus.size() == 0) {
            return "";
        }
        queue = lus.get(0).getLemq();
        for (WordToken lu : lus) {
            tmp.add(lu.getContentMinusQueue());
        }
        return Utils.join(tmp, "+") + queue;
    }
    public List<WordToken> getLUs() {
        return lus;
    }
    /**
     * Creates either a WordToken or MLUToken from the input
     * @param s the string to convert
     * @return a WordToken if the input contains a single lexical unit,
     * or an MLUToken if the input contains a multi lexical unit (two or
     * more LUs joined with '+').
     * @throws Exception if the input is empty
     */
    static StreamToken fromString(String s) throws Exception {
        if (s == null || s.length() == 0) {
            throw new Exception("Input cannot be empty");
        }
        if (!s.contains("+") && !s.contains("~")) {
            return WordToken.fromString(s);
        }
        List<WordToken> lus = new ArrayList<WordToken>();
        boolean inLU = true;
        int start = 0;
        if (s.charAt(start) == '^') {
            start++;
        }
        int end = s.length() - 1;
        if (s.charAt(end) == '$') {
            end--;
        }
        String cur = "";
        String queue = "";
        boolean haveQueue = false;
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) == '\\' && (i + 1) < end && ApertiumStream.isEscape(s.charAt(i + 1))) {
                cur += s.charAt(i + 1);
                i += 2;
            }
            if (inLU) {
                if ((s.charAt(i) == '+' || s.charAt(i) == '~') && s.charAt(i - 1) == '>') {
                    lus.add(new WordToken(cur));
                    cur = "";
                } else if (s.charAt(i) == '#') {
                    lus.add(new WordToken(cur));
                    if (haveQueue) {
                        System.err.println("Second queue found in input: " + s);
                    }
                    haveQueue = true;
                    cur = "#";
                    inLU = false;
                } else if (i == end) {
                    cur += s.charAt(i);
                    lus.add(new WordToken(cur));
                    cur = "";
                } else {
                    cur += s.charAt(i);
                }
            } else {
                if (s.charAt(i) == '+') {
                    inLU = true;
                    if ("".equals(queue)) {
                        queue = cur;
                    }
                    cur = "";
                } else if (i == end) {
                    cur += s.charAt(i);
                    if ("".equals(queue)) {
                        queue = cur;
                    }
                } else {
                    cur += s.charAt(i);
                }
            }
        }
        if (lus.size() > 0 && !"".equals(queue)) {
            lus.get(0).setLemq(queue);
        }
        if (lus.size() == 1) {
            return lus.get(0);
        }
        return new MLUToken(lus);
    }
}
