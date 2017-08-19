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

import ie.tcd.slscs.itut.gramadanj.Utils;

import java.util.ArrayList;
import java.util.List;

// TODO: fromString
public class AnalysedToken extends StreamToken {
    String surface;
    List<StreamToken> analyses;

    AnalysedToken() {
        analyses = new ArrayList<StreamToken>();
    }
    AnalysedToken(String s, List<StreamToken> list) {
        surface = s;
        analyses = list;
    }
    public String getSurface() {
        return surface;
    }
    public List<StreamToken> getAnalyses() {
        return analyses;
    }
    public static boolean validAnalyses(List<StreamToken> list) {
        for (StreamToken st : list) {
            if(!(st instanceof WordToken) && !(st instanceof MLUToken)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(surface);
        sb.append('/');
        List<String> tokcontent = new ArrayList<String>();
        for (StreamToken st : analyses) {
            tokcontent.add(st.getContent());
        }
        sb.append(Utils.join(tokcontent, "/"));
        return sb.toString();
    }

    @Override
    public String toString() {
        return "^" + getContent() + "$";
    }
}
