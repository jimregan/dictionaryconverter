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

import ie.tcd.slscs.itut.ApertiumTransfer.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RuleSideTest extends TestCase {
    DefCats dc;
    List<SimpleToken> stoks;
    List<SimpleToken> stoksnolem;
    public void setUp() {
        List<CatItem> first = new ArrayList<CatItem>();
        first.add(new CatItem("", "adj"));
        first.add(new CatItem("", "adj.sint"));
        first.add(new CatItem("", "adj.sup"));
        List<CatItem> second = new ArrayList<CatItem>();
        second.add(new CatItem("", "n.*"));
        List<DefCat> defcats = new ArrayList<DefCat>();
        defcats.add(new DefCat("adj", first));
        defcats.add(new DefCat("noun", second));
        dc = new DefCats(defcats);

        stoks = new ArrayList<SimpleToken>();
        stoks.add(SimpleToken.fromString("simple<adj>"));
        stoks.add(SimpleToken.fromString("test<n><sg>"));

        stoksnolem = new ArrayList<SimpleToken>();
        stoksnolem.add(SimpleToken.fromString("<adj>"));
        stoksnolem.add(SimpleToken.fromString("<n><sg>"));
    }

    public void testConvert() throws Exception {
        String input = "^simple<n><sg>{^a<det>$^small<adj>+ish<blah>$ ^test<n><sg>$}$ ^test<n><sg>$";
        List<StreamToken> chunkt = ChunkToken.listFromString(input, true);
        RuleSide rs = RuleSide.convert(chunkt);

        List<WordToken> wtoks = new ArrayList<WordToken>();
        wtoks.add(new WordToken("^a<det>$"));
        wtoks.add(new WordToken("^small<adj>$"));
        wtoks.add(new WordToken("^ish<blah>$"));
        wtoks.add(new WordToken("^test<n><sg>$"));
        wtoks.add(new WordToken("^test<n><sg>$"));
        List<StreamToken> kids = new ArrayList<StreamToken>();
        kids.add(new LUReference(0));
        List<LUReference> mlukids = new ArrayList<LUReference>();
        mlukids.add(new LUReference(1));
        mlukids.add(new LUReference(2));
        kids.add(new MLUReference(mlukids));
        kids.add(new BlankToken(" "));
        kids.add(new LUReference(3));
        List<String> chtags = new ArrayList<String>();
        chtags.add("n");
        chtags.add("sg");
        List<StreamToken> exp = new ArrayList<StreamToken>();
        exp.add(new ChunkToken("simple", chtags, kids));
        exp.add(new BlankToken(" "));
        exp.add(new WordToken("test", "", chtags));

        assertEquals(rs.lus.size(), 5);
        assertEquals(rs.tokens.size(), 3);
        assertEquals((rs.tokens.get(0) instanceof ChunkToken), true);
        ChunkToken ckt = (ChunkToken) rs.tokens.get(0);
        assertEquals((ckt.getChildren().get(0) instanceof LUReference), true);
        assertEquals((ckt.getChildren().get(1) instanceof BlankToken), true);
        assertEquals((ckt.getChildren().get(2) instanceof MLUReference), true);
        MLUReference mlur = (MLUReference) ckt.getChildren().get(2);
        assertEquals(mlur.children.get(0).position, 1);
        assertEquals((rs.tokens.get(2) instanceof LUReference), true);
        LUReference luref = (LUReference) rs.tokens.get(2);
        assertEquals(luref.position, 4);
    }
    public void testConvertSimpleTokens() throws Exception {
        RuleSide rs = RuleSide.convertSimpleTokens(stoks);
        assertEquals(2, rs.getLUs().size());
        // check that positioned blank has been added
        assertEquals(3, rs.getTokens().size());
        assertEquals("1", rs.getTokens().get(1).getContent());
        assertEquals("test<n><sg>", rs.getLUs().get(1).getContent());
        assertEquals(true, (rs.getLUs().get(1) instanceof SimpleToken));
        assertEquals(true, (rs.getTokens().get(2) instanceof LUReference));
    }
    public void testToPattern() throws Exception {
        List<PatternItem> lpi = new ArrayList<PatternItem>();
        lpi.add(new PatternItem("adj"));
        lpi.add(new PatternItem("noun"));
        Pattern exp = new Pattern(lpi);

        RuleSide rs = RuleSide.convertSimpleTokens(stoksnolem);
        Pattern pout = RuleSide.toPattern(rs, dc);
        assertEquals(2, pout.getItems().size());
        assertEquals("adj", pout.getItems().get(0).getName());
    }
}