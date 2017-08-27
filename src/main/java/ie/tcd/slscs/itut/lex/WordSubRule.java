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

public class WordSubRule {
    String name;
    String equals;
    List<WordEntry> entries;
    List<TextLike> match;
    boolean defaultRule;
    WordSubRule() {
        entries = new ArrayList<WordEntry>();
        match = new ArrayList<TextLike>();
    }
    WordSubRule(String name) {
        this();
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<WordEntry> getEntries() {
        return entries;
    }
    public void setEntries(List<WordEntry> entries) {
        this.entries = entries;
    }
    public List<TextLike> getMatch() {
        return match;
    }
    public void setMatch(List<TextLike> match) {
        this.match = match;
    }
    public String getEquals() {
        return equals;
    }
    public void setEquals(String equals) {
        this.equals = equals;
    }
    public boolean isDefaultRule() {
        return defaultRule;
    }
    public void setDefaultRule(boolean defaultRule) {
        this.defaultRule = defaultRule;
    }
    private void setDefaultRule(String s) {
        this.defaultRule = (s != null && s.toLowerCase().equals("yes"));
    }
    public Map<String, WordEntry> getEntryMap() {
        Map<String, WordEntry> out = new HashMap<String, WordEntry>();
        for(WordEntry we : this.entries) {
            out.put(we.tags, we);
        }
        return out;
    }
    public static WordSubRule fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("subrule") || n.getNodeName().equals("common")) {
            if(n.getAttributes() == null || n.getAttributes().getLength() == 0) {
                throw new Exception("Attribute \"name\" not found");
            }
            String name = Utils.attrib(n, "name");
            if(name == null || name.equals("")) {
                throw new Exception("Missing attribute name");
            }
            WordSubRule rule = new WordSubRule(name);
            String matchtxt = Utils.attrib(n, "match");
            if(matchtxt != null) {
                List<TextLike> text = TextLike.fromString(matchtxt);
                rule.setMatch(text);
            }
            String equals = Utils.attrib(n, "equals");
            if(equals != null) {
                rule.setEquals(equals);
            }
            String defaultrl = Utils.attrib(n, "default");
            rule.setDefaultRule(defaultrl);
            if(n.getChildNodes().getLength() == 0) {
                throw new Exception("Missing child elements");
            }
            List<WordEntry> entries = new ArrayList<WordEntry>();
            for(int i = 0; i < n.getChildNodes().getLength(); i++) {
                Node itemi = n.getChildNodes().item(i);
                if(itemi.getNodeName().equals("entry")) {
                    WordEntry entry = WordEntry.fromNode(itemi);
                    entries.add(entry);
                } else if(Utils.canSkipNode(itemi)) {
                    // do nothing
                } else {
                    throw new Exception("Unexpected node " + itemi.getNodeName());
                }
            }
            rule.setEntries(entries);
            return rule;
        } else {
            throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }
}