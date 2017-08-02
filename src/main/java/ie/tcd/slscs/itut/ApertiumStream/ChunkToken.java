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

import java.util.ArrayList;
import java.util.List;

public class ChunkToken extends StreamToken {
    String lemma;
    List<String> tags;
    List<StreamToken> children;

    ChunkToken() {
        tags = new ArrayList<String>();
        children = new ArrayList<StreamToken>();
    }
    ChunkToken(String lemma, List<String> tags, List<StreamToken> children) {
        this();
        this.lemma = lemma;
        this.tags = tags;
        this.children = children;
    }
    private void fromWordToken(WordToken wt) {
        lemma = wt.getLemh();
        tags = wt.getTags();
    }
    public String getFirstTag() {
        if (tags.size() != 0) {
            return tags.get(0);
        } else {
            return "";
        }
    }
    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public String getLemma() {
        return lemma;
    }
    public List<String> getTags() {
        return tags;
    }
    public List<StreamToken> getChildren() {
        return children;
    }

    public static ChunkToken fromString(String s) throws Exception {
        if (s == null || s.length() == 0) {
            throw new Exception("Input cannot be empty");
        }
        int start = 0;
        if (s.charAt(1) == '^') {
            start++;
        }
        int end = s.length() - 1;
        if (s.contains("}$")) {
            end = s.lastIndexOf("}$");
        } else {
            throw new Exception("Input does not appear to contain a chunk");
        }
        int chunk_start = start;
        for (int i = chunk_start; i < end; i++) {
            if(s.charAt(i) == '{' && s.charAt(i - 1) != '\\') {
                chunk_start = i;
                break;
            }
        }
        WordToken chunk_lemma = WordToken.fromString(s.substring(start, chunk_start));
        List<StreamToken> tokens = MLUToken.listFromString(s.substring(chunk_start + 1, end), true);
        return new ChunkToken(chunk_lemma.getLemh(), chunk_lemma.getTags(), tokens);
    }
    static List<StreamToken> listFromString(String s, boolean space_only) throws Exception {
        List<StreamToken> out = new ArrayList<StreamToken>();
        int start = 0;
        String cur = "";
        if (s == null || s.length() == 0) {
            throw new Exception("Input cannot be empty");
        }
        int end = s.length() - 1;
        while(s.charAt(start) != '^') {
            start++;
        }
        boolean inToken = true;
        while(s.charAt(end) != '$') {
            end--;
        }
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) == '\\' && (i + 1) < end && ApertiumStream.isEscape(s.charAt(i + 1))) {
                cur += s.charAt(i + 1);
                i += 2;
            }
            if (inToken) {
                if (s.charAt(i) != '$') {
                    cur += s.charAt(i);
                } else {
                    cur += s.charAt(i);
                    inToken = false;
                    if (((i + 2) <= end) && s.charAt(i + 1) == '}' && s.charAt(i + 2) == '$') {
                        out.add(fromString(cur + "}$"));
                        i += 3;
                    } else {
                        out.add(MLUToken.fromString(cur));
                    }
                    cur = "";
                }
            } else {
                if (s.charAt(i) != '^') {
                    cur += s.charAt(i);
                } else {
                    inToken = true;
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
