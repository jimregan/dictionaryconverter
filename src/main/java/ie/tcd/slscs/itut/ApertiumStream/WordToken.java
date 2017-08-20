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

import ie.tcd.slscs.itut.ApertiumTransfer.CatItem;
import ie.tcd.slscs.itut.ApertiumTransfer.LexicalisedWord;
import ie.tcd.slscs.itut.gramadanj.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class WordToken extends StreamToken {
    List<String> tags;
    String lemh = "";
    String lemq = "";
    boolean inMLU = false;
    private boolean simple = false;
    public WordToken() {
        tags = new ArrayList<String>();
    }
    public WordToken(String lemh, String lemq, List<String> tags) {
        this();
        this.lemh = lemh;
        this.lemq = lemq;
        this.tags = tags;
    }
    public WordToken(String s) throws Exception {
        this();
        WordToken tmp = WordToken.fromString(s);
        this.lemh = tmp.lemh;
        this.lemq = tmp.lemq;
        this.tags = tmp.tags;
    }
    public WordToken(SimpleToken st) {
        this.lemh = st.lemh;
        this.tags = st.tags;
        this.simple = true;
    }
    public String getLemma() {
        if(lemq == null || lemq.equals("")) {
            return lemh;
        } else if(!lemq.startsWith("#")) {
            return lemh + "#" + lemq;
        } else {
            return lemh + lemq;
        }
    }
    public String getLemh() {
        return lemh;
    }
    public void setLemh(String lemh) {
        this.lemh = lemh;
    }
    public String getLemq() {
        return lemq;
    }
    public void setLemq(String lemq) {
        this.lemq = lemq;
    }
    public boolean emptyLemq() {
        return (lemq.equals("") || lemq == null);
    }
    public boolean emptyLemh() {
        return (lemh.equals("") || lemh == null);
    }
    public boolean emptyLemma() {
        return emptyLemh() && emptyLemq();
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setTags(String[] tags) {
        this.tags = Arrays.asList(tags);
    }
    public void setTagsFromString(String s) {
        String trimmed = s.trim();
        if(trimmed.charAt(0) == '<' && trimmed.charAt(trimmed.length() - 1) == '>') {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
        }
        if(trimmed.contains("><")) {
            setTags(trimmed.split("><"));
        } else {
            tags.add(trimmed);
        }
    }
    public String getTagsString() {
        StringBuilder s = new StringBuilder();
        s.append('<');
        Iterator<String> it = tags.iterator();
        if (it.hasNext()) {
            s.append(ApertiumStream.escapeString(it.next()));
        }
        while (it.hasNext()) {
            s.append("><");
            s.append(ApertiumStream.escapeString(it.next()));
        }
        s.append('>');
        return s.toString();
    }
    public String getTagsStringTransfer() {
        return Utils.join(tags, ".");
    }

    @Override
    public String getContent() {
        String outlemq = "";
        if (getLemq() != null && getLemq().startsWith("#")) {
            outlemq = "#" + ApertiumStream.escapeString(getLemq().substring(1));
        }
        return ApertiumStream.escapeString(getLemh()) + getTagsString() + outlemq;
    }

    public String getContentMinusQueue() {
        return getLemh() + getTagsString();
    }
    @Override
    public String toString() {
        return "^" + getContent() + "$";
    }
    public String getFirstTag() {
        if (tags.size() > 0) {
            return tags.get(0);
        } else {
            return "";
        }
    }
    public void setInMLU() {
        this.inMLU = true;
    }
    public void setInMLU(boolean inMLU) {
        this.inMLU = inMLU;
    }
    public static WordToken fromString(String s) throws Exception {
        List<String> tags = new ArrayList<String>();
        String lemh = "";
        String lemq = "";
        if (s == null || s.length() == 0) {
            throw new Exception("Input cannot be empty");
        }
        char chars[] = s.toCharArray();
        String cur = "";
        boolean inLemma = true;
        boolean sawHash = false;
        int start = 0;
        if (chars[0] == '^') {
            start = 1;
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
        if (s == null || s.length() == 0) {
            throw new Exception("Input cannot be empty");
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

    /**
     * For apertium-transfer-tools like input: checks if the stream of tokens
     * is fully delexicalised (i.e., that the tokens only contain tags): if so,
     * the list of tokens may be used as the basis of a rule
     * @param tokens the stream of WordTokens and BlankTokens to be checked
     * @return true if fully delexicalised
     */
    static boolean isRuleBasis(List<StreamToken> tokens) {
        for (StreamToken st : tokens) {
            if (st instanceof BlankToken) {
                continue;
            } else if (st instanceof WordToken) {
                WordToken wt = (WordToken) st;
                if (!wt.emptyLemma()) {
                    return false;
                }
            }
        }
        return true;
    }
    static boolean isRuleBasis(List<StreamToken> tokens, List<LexicalisedWord> lexic) {
        for (StreamToken st : tokens) {
            if (st instanceof BlankToken) {
                continue;
            } else if (st instanceof WordToken) {
                WordToken wt = (WordToken) st;
                if (wt.emptyLemma()) {
                    return true;
                } else {
                    for(LexicalisedWord lw : lexic) {
                        WordToken tmp = lw.toWordToken();
                        if(tmp.emptyLemma() || tmp.getLemma().equals(wt.getLemma())) {
                            return CatItem.tagsStartWith(tmp.getTags(), wt.getTags());
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
