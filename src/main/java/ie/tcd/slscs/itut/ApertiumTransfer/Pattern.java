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

package ie.tcd.slscs.itut.ApertiumTransfer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    List<PatternItem> items;
    Pattern() {
        items = new ArrayList<PatternItem>();
    }
    public Pattern(List<PatternItem> list) {
        this();
        this.items = list;
    }

    public List<PatternItem> getItems() {
        return items;
    }
    public static Pattern fromNode(Node n) throws Exception {
        List<PatternItem> children = new ArrayList<PatternItem>();
        if(n.getNodeName().equals("pattern")) {
            for(int i = 0; i < n.getChildNodes().getLength(); i++) {
                Node ch = n.getChildNodes().item(i);
                if(ch.getNodeName().equals("pattern-item")) {
                    children.add(PatternItem.fromNode(ch));
                } else if(ch.getNodeType() == Element.COMMENT_NODE
                        || (ch.getNodeType() == Element.TEXT_NODE
                            && ch.getTextContent().toString().trim().equals(""))) {
                    // skip
                } else {
                    throw new Exception("Unexpected child node: " + ch.getNodeName());
                }
            }
            return new Pattern(children);
        } else {
            throw new Exception("Node does not contain pattern");
        }
    }
}
