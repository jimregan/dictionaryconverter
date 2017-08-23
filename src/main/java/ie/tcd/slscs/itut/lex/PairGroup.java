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

import ie.tcd.slscs.itut.gramadanj.Utils;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class PairGroup extends Grouping {
    Map<String, String> pairs;
    PairGroup() {
        this.pairs = new HashMap<String, String>();
    }

    public Map<String, String> getPairs() {
        return pairs;
    }
    public void setPairs(Map<String, String> pairs) {
        this.pairs = pairs;
    }
    @Override
    public String getRegex() {
        setOpt();
        String inner = Utils.join(pairs.keySet(), "|");
        String start = "(";
        String end = ")";
        if(!opt.equals("")) {
            start = "((?:";
            end = ")" + opt + ")";
        }
        return start + inner + end;
    }
    public static PairGroup fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("pairgroup")) {
            if(n.getAttributes() == null || n.getAttributes().getLength() == 0) {
                throw new Exception("No attribute \"name\" found");
            }
            PairGroup out = new PairGroup();
            String name = Utils.attrib(n, "name");
            String repeated_str = Utils.attrib(n, "repeated");
            boolean repeated = (repeated_str != null && repeated_str.equals("yes"));
            String optional_str = Utils.attrib(n, "optional");
            boolean optional = (optional_str != null && optional_str.equals("yes"));
            Map<String, String> pairs = new HashMap<String, String>();
            for(int i = 0; i < n.getChildNodes().getLength(); i++) {
                Node itemi = n.getChildNodes().item(i);
                if(itemi.getNodeName().equals("pair")) {
                    String left = "";
                    String right = "";
                    for(int j = 0; j < itemi.getChildNodes().getLength(); j++) {
                        Node itemj = itemi.getChildNodes().item(j);
                        if(itemj.getNodeName().equals("left")) {
                            if(left.equals("")) {
                                left = itemj.getFirstChild().getTextContent().trim();
                            } else {
                                throw new Exception("Pair can only contain one left node");
                            }
                        } else if(itemj.getNodeName().equals("right")) {
                            if(right.equals("")) {
                                right = itemj.getFirstChild().getTextContent().trim();
                            } else {
                                throw new Exception("Pair can only contain one right node");
                            }
                        } else if(Utils.canSkipNode(itemj)) {

                        } else {
                            throw new Exception("Unexpected node: " + itemj.getNodeName());
                        }
                    }
                    pairs.put(left, right);
                } else if(Utils.canSkipNode(itemi)) {

                } else {
                    throw new Exception("Unexpected node: " + itemi.getNodeName() + " (" + itemi.getTextContent() + ")");
                }
            }
            out.setPairs(pairs);
            return out;
        } else {
            throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }
}
