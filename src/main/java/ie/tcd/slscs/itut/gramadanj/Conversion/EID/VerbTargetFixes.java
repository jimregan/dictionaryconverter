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

package ie.tcd.slscs.itut.gramadanj.Conversion.EID;

import ie.tcd.slscs.itut.gramadanj.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerbTargetFixes {
    private static final InputStream is;
    final static Map<String, List<VerbRulePair>> replacements;
    private static final InputStream isvlr;
    final static Map<String, String> verb_lemma_replacements;
    static {
        is = VerbTargetFixes.class.getClassLoader().getResourceAsStream("ie/tcd/slscs/itut/gramadanj/Conversion/EID/manual-verb-tweaks.tsv");
        isvlr = VerbTargetFixes.class.getClassLoader().getResourceAsStream("ie/tcd/slscs/itut/gramadanj/Conversion/EID/verb-lemmas.tsv");
        try {
            replacements = fromInputStream(is);
            verb_lemma_replacements = Utils.readTSV(isvlr);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }

    }
    private static Map<String, List<VerbRulePair>> fromInputStream(InputStream ins) throws IOException {
        Map<String, List<VerbRulePair>> out = new HashMap<String, List<VerbRulePair>>();
        BufferedReader br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
        String line;
        int lineno = 0;
        while((line = br.readLine()) != null) {
            lineno++;
            String[] sp = line.split("\\t");
            if(sp.length != 3) {
                throw new IOException("Error reading file at line: " + lineno);
            }

            String src = sp[0].trim();
            String[] rules = sp[1].split(",");
            String[] verbs = sp[2].split(",");
            if(rules.length != verbs.length && rules.length != 1) {
                throw new IOException("Error reading entry at line: " + lineno + " - mismatch in fields 2 and 3");
            }
            String vtmp = rules[0].trim();
            List<VerbRulePair> curvrp = new ArrayList<VerbRulePair>();
            for(int i = 0; i < rules.length; i++) {
                String rtmp = rules[i].trim();
                if(rules.length != verbs.length) {
                    vtmp = verbs[i].trim();
                }
                curvrp.add(new VerbRulePair(rtmp, vtmp));
            }
            out.put(src, curvrp);
        }
        return out;
    }
    public static List<String> getReplacementVerbs(String s) {
        List<String> verbs = new ArrayList<String>();
        if(replacements.containsKey(s)) {
            List<VerbRulePair> vrp = replacements.get(s);
            for(VerbRulePair vp : vrp) {
                verbs.add(vp.getVerb());
            }
        }
        return verbs;
    }
    public static String getVerbLemma(String s) {
        return verb_lemma_replacements.get(s.toLowerCase().trim());
    }
}
