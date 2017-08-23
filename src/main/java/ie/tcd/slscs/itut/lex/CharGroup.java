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

public class CharGroup extends Grouping {
    String raw_characters;
    String negates;
    CharGroup() {

    }
    CharGroup(String name) {
        this.name = name;
    }
    public String getRawCharacters() {
        return raw_characters;
    }
    public void setRawCharacters(String raw_characters) {
        this.raw_characters = raw_characters;
    }
    public String getNegates() {
        return negates;
    }
    public void setNegates(String negates) {
        this.negates = negates;
    }
    public boolean isNegated() {
        return !(negates == null || negates.equals(""));
    }

    @Override
    public String getRegex() {
        setOpt();
        String neg = "";
        if(!(negates == null) && !negates.equals("")) {
            neg = "^";
        }
        return "([" + neg + raw_characters + "]" + opt + ")";
    }
    public static CharGroup fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("chargroup")) {
            if(n.getAttributes() == null || n.getAttributes().getLength() == 0) {
                throw new Exception("No attribute \"name\" found");
            }
            String name = Utils.attrib(n, "name");
            String repeated_str = Utils.attrib(n, "repeated");
            boolean repeated = (repeated_str != null && repeated_str.equals("yes"));
            String optional_str = Utils.attrib(n, "optional");
            boolean optional = (optional_str != null && optional_str.equals("yes"));
            String negates = Utils.attrib(n, "negates");
            String content = "";
            if(n.getChildNodes().getLength() == 1 && n.getChildNodes().item(0).getNodeName().equals("#text")) {
                content = n.getChildNodes().item(0).getTextContent();
                if(negates != null && !negates.trim().equals("")) {
                    throw new Exception("chargroup " + name + " contains unexpected content");
                }
            }
            CharGroup out = new CharGroup(name);
            out.setNegates(negates);
            out.setOptional(optional);
            out.setRepeated(repeated);
            out.setRawCharacters(content);
            return out;
        } else {
            throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }
}
