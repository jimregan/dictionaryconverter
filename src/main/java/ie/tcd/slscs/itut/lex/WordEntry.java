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

public class WordEntry {
    String tags;
    List<TextLike> text;
    WordEntry() {
        text = new ArrayList<TextLike>();
    }
    WordEntry(String tags, List<TextLike> text) {
        this();
        this.tags = tags;
        this.text = text;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public List<TextLike> getText() {
        return text;
    }
    public void setText(List<TextLike> text) {
        this.text = text;
    }
    public static WordEntry fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("entry")) {
            if(n.getAttributes() == null || n.getAttributes().getLength() < 2) {
                throw new Exception("Attributes \"tags\" and \"text\" are required");
            }
            String tags = Utils.attrib(n, "tags");
            if(tags == null || tags.equals("")) {
                throw new Exception("Missing attribute tags");
            }
            String textatt = Utils.attrib(n, "text");
            if(textatt == null || textatt.equals("")) {
                throw new Exception("Missing attribute text");
            }
            List<TextLike> text = TextLike.fromString(textatt);
            if(!TextLike.matchesTags(text, tags)) {
                throw new Exception("Mismatch in number of breaks in " + textatt + " and " + tags);
            }
            if(!TextLike.isValidBreak(text)) {
                throw new Exception("Invalid word break in " + textatt + " (starts or ends with '+'");
            }
            WordEntry out = new WordEntry(tags, text);
            return out;
        } else {
            throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }

}
