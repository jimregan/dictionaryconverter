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

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Target {
    List<TargetPiece> pieces;
    Target() {
        pieces = new ArrayList<TargetPiece>();
    }
    public class TargetPiece {
        String preNote;
        String first;
        String label;
        String second;
        String postNote;
        List<GrammarNote> notes;
        TargetPiece() {
            notes = new ArrayList<GrammarNote>();
        }
        TargetPiece(String preNote, String first, String label, String second, String postNote) {
            this();
            this.first = first;
            this.preNote = preNote;
            this.label = label;
            this.second = second;
            this.postNote = postNote;
        }
        TargetPiece(String preNote, String first, String label, String second, String postNote, List<GrammarNote> notes) {
            this();
            this.first = first;
            this.preNote = preNote;
            this.label = label;
            this.second = second;
            this.postNote = postNote;
            this.notes = notes;
        }
    }

    private static GrammarNote getNote(Node n) {
        if(n.getNodeName() == "noindex") {
            String label = "";
            String form = "";
            if(n.getChildNodes().getLength() == 3
               && n.getChildNodes().item(0).getNodeName().equals("#text")
               && n.getChildNodes().item(0).getTextContent().trim().equals("(")
               && n.getChildNodes().item(1).getNodeName().equals("label")
               && n.getChildNodes().item(1).getChildNodes().getLength() == 1
               && n.getChildNodes().item(1).getChildNodes().item(0).getNodeName().equals("#text")
               && n.getChildNodes().item(2).getNodeName().equals("#text")
               && n.getChildNodes().item(2).getTextContent().trim().endsWith(")")) {
                label = n.getChildNodes().item(1).getChildNodes().item(0).getTextContent();
                form = n.getChildNodes().item(2).getTextContent().trim();
                if(form.equals(")")) {
                    form = "";
                } else {
                    form = form.substring(0, form.length() - 1);
                }
            }
            return new GrammarNote(label, form);
        }
        return null;
    }
    /* FIXME
    public static Target fromNode(Node n) {
        if(n.getNodeName().equals("trg")) {
            String whole = n.getTextContent();
            String prenote;
            for(int i = 0; i < n.getChildNodes().getLength(); i++) {
                Node cur = n.getChildNodes().item(i);
                if(cur.getNodeName().equals("#text")) {
                    String tmps = cur.getTextContent();
                    if(i == 0 && tmps.trim().startsWith("(") && tmps.contains(")")) {
                        prenote = tmps.substring(tmps.indexOf('(') + 1  , tmps.indexOf(')'));
                        if(tmps.indexOf(')') + 1 < tmps.length()) {
                            tmps = tmps.substring(tmps.indexOf(')') + 1).trim();
                        }
                        if(tmps.contains(",")) {

                        }
                    }
                }
            }
        }
    } */
}
