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

import ie.tcd.slscs.itut.ApertiumTransfer.DefAttr;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AttributesTest extends TestCase {
    public void testFromText() throws Exception {
        String in = "num = sg pl ND! sp?";
        Attributes exp = new Attributes("num", false, "sp", "ND", Arrays.asList(new String[]{"sg", "pl", "ND", "sp"}));
        Attributes out = Attributes.fromText(in);
        assertEquals(exp.name, out.name);
        assertEquals(exp.any, out.any);
        assertEquals(exp.undefined, out.undefined);
        assertEquals(exp.getItems().size(), out.getItems().size());
        assertEquals(exp.getItems().get(0), out.getItems().get(0));
    }
    public void testFromAttributes() throws Exception {
        String sin = "gen = m f mf? GD!";
        Attributes in = Attributes.fromText(sin);
        List<DefAttr> out = Attributes.fromAttributes(in);
        assertEquals(3, out.size());
    }

    public void testFromDictionary() throws Exception {
        String sin = "gen! = m f mf? GD!";
        Attributes out = Attributes.fromText(sin);
        assertEquals(true, out.fromDictionary);
        assertEquals("gen", out.name);
        assertEquals("mf", out.any);
        assertEquals("GD", out.undefined);
    }
    public void testToString() throws Exception {
        String sin = "gen! = m f mf? GD!";
        Attributes out = Attributes.fromText(sin);
        String sout = out.toString();
        assertEquals(sin, sout);
    }
}