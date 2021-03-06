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

import java.util.ArrayList;
import java.util.List;

public class Group extends Grouping {
    List<String> items;
    Group() {
        this.items = new ArrayList<String>();
    }
    public Group(String name) {
        this.name = name;
    }
    public List<String> getItems() {
        return items;
    }
    public void setItems(List<String> items) {
        this.items = items;
    }
    @Override
    public String getRegex() {
        setOpt();
        String inner = Utils.join(items, "|");
        String start = "(";
        String end = ")";
        if(!opt.equals("")) {
            start = "((?:";
            end = ")" + opt + ")";
        }
        return start + inner + end;
    }
    public static Group fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("group")) {
            if(n.getAttributes() == null || n.getAttributes().getLength() == 0) {
                throw new Exception("No attribute \"name\" found");
            }
            Group out = new Group();
            String name = Utils.attrib(n, "name");
            String repeated_str = Utils.attrib(n, "repeats");
            boolean repeated = (repeated_str != null && repeated_str.equals("yes"));
            String optional_str = Utils.attrib(n, "optional");
            boolean optional = (optional_str != null && optional_str.equals("yes"));
            List<String> items = new ArrayList<String>();
            for(int i = 0; i < n.getChildNodes().getLength(); i++) {
                Node itemi = n.getChildNodes().item(i);
                if(itemi.getNodeName().equals("item")) {
                    String text = itemi.getTextContent();
                    items.add(text);
                } else if(Utils.canSkipNode(itemi)) {

                } else {
                    throw new Exception("Unexpected node: " + itemi.getNodeName() + " (" + itemi.getTextContent() + ")");
                }
            }
            out.setName(name);
            out.setOptional(optional);
            out.setRepeated(repeated);
            out.setItems(items);
            return out;
        } else {
            throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }
}
