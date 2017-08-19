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

package ie.tcd.slscs.itut.duckegg.bitext;

import java.util.HashMap;
import java.util.Map;

public class TranslationFindAndReplace extends Rule {
    Map<String, Map<String, String>> needles;
    TranslationFindAndReplace() {
        this.name = "translation-find-and-replace";
        this.needles = new HashMap<String, Map<String, String>>();
    }
    public TranslationFindAndReplace(Map<String, Map<String, String>> needles) {
        this();
        this.needles = needles;
    }
    @Override
    public SLTLPair replace(SLTLPair input) {
        SLTLPair out = replace(input, needles, false);
        if(!out.target.equals(input.target)) {
            this.replacement = true;
        }
        return out;
    }
    public static SLTLPair replace(SLTLPair haystack, Map<String, Map<String, String>> needles, boolean regex) {
        SLTLPair out = new SLTLPair(haystack.id, haystack.source, haystack.target);
        for(String slside : needles.keySet()) {
            for(String trside : needles.get(slside).keySet()) {
                out = replace(out, slside, trside, needles.get(slside).get(trside), regex);
            }
        }
        return out;
    }
    public static SLTLPair replace(SLTLPair haystack, String sl_needle, String tl_needle, String tl_replacement, boolean regex) {
        if(regex) {
            if(haystack.source.matches(sl_needle) && haystack.target.matches(tl_needle)) {
                String out = haystack.target.replaceAll(sl_needle, tl_replacement);
                return new SLTLPair(haystack.id, haystack.source, out);
            } else {
                return haystack;
            }
        } else {
            if(haystack.source.contains(sl_needle) && haystack.target.contains(tl_needle)) {
                int tlpos = haystack.target.indexOf(tl_needle);
                String pre = haystack.target.substring(0, tlpos);
                String post = haystack.target.substring(tlpos + tl_needle.length());
                return new SLTLPair(haystack.id, haystack.source, pre + tl_replacement + post);
            } else {
                return haystack;
            }
        }
    }
}
