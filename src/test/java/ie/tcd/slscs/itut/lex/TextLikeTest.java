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
}