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

package ie.tcd.slscs.itut.ApertiumStream;

import java.util.ArrayList;
import java.util.List;

public class SimpleToken extends WordToken {
    public SimpleToken() {
        super();
        this.lemq = null;
    }
    public SimpleToken(String lemh, List<String> tags) {
        this.lemh = lemh;
        this.tags = tags;
    }
    public static SimpleToken fromString(String s) {
        SimpleToken st = new SimpleToken();
        if(s.trim().startsWith("<")) {
            st.setTagsFromString(s);
        } else {
            String lemma = s.substring(0, s.indexOf('<'));
            st.setLemh(lemma.trim().replaceAll("_", " "));
            st.setTagsFromString(s.substring(s.indexOf('<') + 1, s.length() - 1));
        }
        return st;
    }
    public static List<SimpleToken> listFromString(String s) {
        List<SimpleToken> out = new ArrayList<SimpleToken>();
        for(String tok : s.trim().split(" ")) {
            out.add(fromString(tok));
        }
        return out;
    }
}
