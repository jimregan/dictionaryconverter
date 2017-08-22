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

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Match {
    List<TextLike> pieces;
    String comment;
    Match() {
        this.pieces = new ArrayList<TextLike>();
    }
    public Match(List<TextLike> pieces, String comment) {
        this.pieces = pieces;
        this.comment = comment;
    }
    public List<TextLike> getPieces() {
        return pieces;
    }
    public void setPieces(List<TextLike> pieces) {
        this.pieces = pieces;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public static Match fromNode(Node n) throws Exception {
        List<TextLike> parts = new ArrayList<TextLike>();
        String comment = "";
        if(n.getNodeName().equals("match")) {
            if(n.getAttributes().getLength() != 0) {
                if(n.getAttributes().getNamedItem("c") != null) {
                    comment = n.getAttributes().getNamedItem("c").getTextContent();
                }
            }
            for(int i = 0; i < n.getChildNodes().getLength(); i++) {
                Node m = n.getChildNodes().item(i);
                if(m.getNodeName().equals("#text")) {
                    parts.add(new TextPiece(m.getTextContent()));
                } else if(m.getNodeType() == Element.ENTITY_NODE) {
                    parts.add(new EntityPiece(m.getNodeName()));
                } else {
                    throw new Exception("Unexpected node: " + m.getNodeName());
                }
            }
            return new Match(parts, comment);
        } else {
            throw new Exception("Node does not contain match");
        }
    }

}
