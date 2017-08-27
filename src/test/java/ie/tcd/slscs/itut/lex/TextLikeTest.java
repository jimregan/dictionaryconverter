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
package ie.tcd.slscs.itut.lex;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TextLikeTest extends TestCase {
    public void testFromString1() throws Exception {
        String in = "~{vowel}y";
        List<TextLike> exp = new ArrayList<TextLike>();
        exp.add(new TextPiece("~"));
        exp.add(new EntityPiece("vowel"));
        exp.add(new TextPiece("y"));
        List<TextLike> out = TextLike.fromString(in);
        assertEquals(exp.size(), out.size());
        assertEquals(exp.get(0).getValue(), out.get(0).getValue());
        assertEquals(exp.get(1).getValue(), out.get(1).getValue());
        assertEquals(exp.get(2).getValue(), out.get(2).getValue());
    }
    public void testFromString2() throws Exception {
        String in = "~+y";
        List<TextLike> exp = new ArrayList<TextLike>();
        exp.add(new TextPiece("~"));
        exp.add(new WordBreak());
        exp.add(new TextPiece("y"));
        List<TextLike> out = TextLike.fromString(in);
        assertEquals(exp.size(), out.size());
        assertEquals(exp.get(0).getValue(), out.get(0).getValue());
        assertEquals(true, (out.get(1) instanceof WordBreak));
        assertEquals(exp.get(2).getValue(), out.get(2).getValue());
    }
    public void testFromString3() throws Exception {
        String in = "~{vowel}{letter}";
        List<TextLike> exp = new ArrayList<TextLike>();
        exp.add(new TextPiece("~"));
        exp.add(new EntityPiece("vowel"));
        exp.add(new EntityPiece("letter"));
        List<TextLike> out = TextLike.fromString(in);
        assertEquals(exp.size(), out.size());
        assertEquals(exp.get(0).getValue(), out.get(0).getValue());
        assertEquals(exp.get(1).getValue(), out.get(1).getValue());
        assertEquals(exp.get(2).getValue(), out.get(2).getValue());
    }
    public void testFromString4() throws Exception {
        String in = "~\\{vowel{letter}";
        List<TextLike> exp = new ArrayList<TextLike>();
        exp.add(new TextPiece("~{vowel"));
        exp.add(new EntityPiece("letter"));
        List<TextLike> out = TextLike.fromString(in);
        assertEquals(exp.size(), out.size());
        assertEquals(exp.get(0).getValue(), out.get(0).getValue());
        assertEquals(exp.get(1).getValue(), out.get(1).getValue());
    }
}