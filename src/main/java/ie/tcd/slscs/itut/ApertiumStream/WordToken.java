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
import java.util.Iterator;
import java.util.List;

public class WordToken extends StreamToken {
    List<String> tags;
    String lemh = "";
    String lemq = "";
    WordToken() {
        tags = new ArrayList<String>();
    }
    WordToken(String lemh, String lemq, List<String> tags) {
        this();
        this.lemh = lemh;
        this.lemq = lemq;
        this.tags = tags;
    }
    WordToken(String s) throws Exception {
        this();
        WordToken tmp = WordToken.fromString(s);
        this.lemh = tmp.lemh;
        this.lemq = tmp.lemq;
        this.tags = tmp.tags;
    }
    public String getLemma() {
        return lemh + lemq;
    }
    public String getLemh() {
        return lemh;
    }

    public String getLemq() {
        return lemq;
    }
    public List<String> getTags() {
        return tags;
    }
    public String getTagsString() {
        StringBuilder s = new StringBuilder();
        s.append('<');
        Iterator<String> it = tags.iterator();
        if (it.hasNext()) {
            s.append(it.next());
        }
        while (it.hasNext()) {
            s.append("><");
            s.append(it.next());
        }
        s.append('>');
        return s.toString();
    }

    @Override
    public String getContent() {
        return getLemh() + getTagsString() + getLemq();
    }

    @Override
    public String toString() {
        return "^" + getContent() + "$";
    }

    static WordToken fromString(String s) throws Exception {
        List<String> tags = new ArrayList<String>();
        String lemh = "";
        String lemq = "";
        char chars[] = s.toCharArray();
        String cur = "";
        boolean inLemma = true;
        boolean sawHash = false;
        int start = 0;
        if (chars[0] == '^') {
            start = 1;
        }
        if (chars.length == 0) {
            throw new Exception("Input cannot be an empty string");
        }
        int end = chars.length - 1;
        if (chars[end] == '$') {
            end -= 1;
        }
        for (int i = start; i <= end; i++) {
            if (chars[i] == '\\' && (i + 1) < end && ApertiumStream.isEscape(chars[i + 1])) {
                cur += chars[i + 1];
                i += 2;
            }
            if (inLemma) {
                if (chars[i] == '#') {
                    lemh = cur;
                    cur = "#";
                    sawHash = true;
                } else if (chars[i] == '<') {
                    inLemma = false;
                    if (sawHash) {
                        lemq = cur;
                    } else {
                        lemh = cur;
                    }
                    cur = "";
                } else {
                    cur += chars[i];
                }
            } else {
                if (chars[i] == '<') {
                    i++;
                }
                if (chars[i] == '>') {
                    tags.add(cur);
                    cur = "";
                } else {
                    cur += chars[i];
                }
            }
        }
        return new WordToken(lemh, lemq, tags);
    }

    static List<StreamToken> listFromString(String s, boolean space_only) throws Exception {
        List<StreamToken> out = new ArrayList<StreamToken>();
        char chars[] = s.toCharArray();
        int start = 0;
        String cur = "";
        if (s.length() == 0) {
            throw new Exception("Input cannot be an empty string");
        }
        int end = s.length() - 1;
        while(chars[start] != '^') {
            start++;
        }
        boolean inToken = true;
        int tokstart = start;
        while(chars[end] != '$') {
            end--;
        }
        for (int i = start; i <= end; i++) {
            if (chars[i] == '\\' && (i + 1) < end && ApertiumStream.isEscape(chars[i + 1])) {
                cur += chars[i + 1];
                i += 2;
            }
            if (inToken) {
                if (chars[i] != '$') {
                    cur += chars[i];
                } else {
                    cur += chars[i];
                    inToken = false;
                    out.add(new WordToken(cur));
                    cur = "";
                }
            } else {
                if (chars[i] != '^') {
                    cur += chars[i];
                } else {
                    inToken = true;
                    tokstart = i;
                    if (space_only && !cur.equals("")) {
                        cur = " ";
                    }
                    out.add(new BlankToken(cur));
                    cur = "";
                }
            }
        }
        return out;
    }
}
