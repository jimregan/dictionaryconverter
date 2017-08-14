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

package ie.tcd.slscs.itut.ApertiumTransfer.Text;

import ie.tcd.slscs.itut.gramadanj.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * As a macro can contain multiple operations, this groups the parts
 * together, and contains the common parts: name, and tokens the
 * macro applies to, for use in generating the number of expected
 * parameters, and for matching the macro to the rule it can
 * apply to.
 *
 * For example:
 * <pre>
 * adj_grade | <adj> | <grade=comp> | níos<adv> <grade=comp> | 0-1 1-2
 *  | | <grade=sup> | is<adv> <grade=comp> | 0-1 1-2
 * </pre>
 * The macro "adj_grade" applies to &lt;adj&gt; and has two actions:
 * one which inserts "níos" in front of the adjective, the other
 * which inserts "is", and sets the tag from sup to comp.
 * Insertion is marked with a 0 in the alignment piece.
 *
 * Example 2:
 * <pre>
 * det_type | <det> | no<det> | <negative=NEG> | 1-C
 * | | the<det> | <det_type=DEFART> | 1-C
 * | | a<det> | <det_type=UNDET> | 1-C
 * </pre>
 *
 * Here, all of the operations are to be applied to the chunk lemma,
 * which is denoted by 'C' in the alignment part.
 */
public class SimpleTextMacro {
    String name;
    List<String> appliesTo;
    List<SimpleTextMacroPart> parts;
    SimpleTextMacro() {
        appliesTo = new ArrayList<String>();
        parts = new ArrayList<SimpleTextMacroPart>();
    }

    public static SimpleTextMacro fromString(String s) throws Exception {
        String[] sp = s.split("\\|");
        if(sp.length != 5) {
            throw new Exception("Incorrect number of fields");
        }
        String name = sp[0].trim();
        List<String> appliesTo = new ArrayList<String>();
        for (String apply : sp[1].trim().split(" ")) {
            appliesTo.add(apply);
        }
        List<List<SimpleTextMacroAttr>> lhs = extractSimpleTokens(sp[2]);
        List<List<SimpleTextMacroAttr>> rhs = extractSimpleTokens(sp[3]);

        return new SimpleTextMacro();
    }

    public static List<List<SimpleTextMacroAttr>> extractSimpleTokens(String s) throws Exception {
        List<List<SimpleTextMacroAttr>> side = new ArrayList<List<SimpleTextMacroAttr>>();
        List<SimpleTextMacroAttr> tmp = new ArrayList<SimpleTextMacroAttr>();
        for (String apply : s.trim().split(" ")) {
            if(apply.startsWith("<") && apply.endsWith(">")) {
                for(String inner : apply.substring(1, apply.length() - 1).split("><")) {
                    tmp.add(SimpleTextMacroAttr.fromSimpleText(inner));
                }
            } else if(apply.endsWith(">")) {
                int idx = apply.indexOf("<");
                tmp.add(SimpleTextMacroAttr.createLemma(apply.substring(0, idx)));
                for(String inner : apply.substring(idx + 1).split("><")) {
                    tmp.add(SimpleTextMacroAttr.fromSimpleText(inner));
                }
            } else {
                throw new Exception("Error reading tag");
            }
            side.add(Utils.listclone(tmp));
            tmp.clear();
        }
        return side;
    }

    public static List<SimpleTextMacro> fromFile(BufferedReader br) throws IOException {
        List<SimpleTextMacro> out = new ArrayList<SimpleTextMacro>();
        String line;
        int lineno = 0;
        while((line = br.readLine()) != null) {
            lineno++;
            SimpleTextMacro tmp = new SimpleTextMacro();
            try {
                tmp = fromString(line);
            } catch (Exception e) {
                throw new IOException(e.getMessage() + " on line " + lineno);
            }
        }
        return out;
    }
}
