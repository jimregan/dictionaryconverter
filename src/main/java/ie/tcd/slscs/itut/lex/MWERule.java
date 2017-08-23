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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MWERule {
    String name;
    String phrase;
    List<MWEEntry> entries;
    MWERule() {
        entries = new ArrayList<MWEEntry>();
    }
    MWERule(String name, String phrase) {
        this();
        this.name = name;
        this.phrase= phrase;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    public List<MWEEntry> getEntries() {
        return entries;
    }
    public void setEntries(List<MWEEntry> entries) {
        this.entries = entries;
    }
    public static MWERule fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("rule")) {
            if(n.getAttributes() == null || n.getAttributes().getLength() == 0) {
                throw new Exception("Attributes \"name\" and \"tags\" not found");
            }
            String name = Utils.attrib(n, "name");
            if(name == null || name.equals("")) {
                throw new Exception("Missing attribute tags");
            }
            String phrase = Utils.attrib(n, "phrase");
            if(phrase == null || phrase.equals("")) {
                throw new Exception("Missing attribute phrase");
            }
            if(n.getChildNodes().getLength() == 0) {
                throw new Exception("Missing child elements");
            }
            List<MWEEntry> entries = new ArrayList<MWEEntry>();
            for(int i = 0; i < n.getChildNodes().getLength(); i++) {
                Node itemi = n.getChildNodes().item(i);
                if(itemi.getNodeName().equals("entry")) {
                    MWEEntry entry = MWEEntry.fromNode(itemi);
                    entries.add(entry);
                } else if(Utils.canSkipNode(itemi)) {
                    // do nothing
                } else {
                    throw new Exception("Unexpected node " + itemi.getNodeName());
                }
            }
            MWERule rule = new MWERule(name, phrase);
            rule.setEntries(entries);
            return rule;
        } else {
            throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }

}
