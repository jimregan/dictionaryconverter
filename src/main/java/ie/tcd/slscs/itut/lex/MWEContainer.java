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
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public abstract class MWEContainer implements MWEPart {
    String tags;
    List<MWEPart> parts;
    MWEContainer() {
        parts = new ArrayList<MWEPart>();
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public List<MWEPart> getParts() {
        return parts;
    }
    public void setParts(List<MWEPart> parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(tags);
        sb.append(' ');
        for(MWEPart part : parts) {
            sb.append(part.toString());
        }
        sb.append(']');
        return sb.toString();
    }

    public static List<MWEPart> fromNodeList(NodeList nl) throws Exception {
        List<MWEPart> parts = new ArrayList<MWEPart>();
        for(int i = 1; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if(n.getNodeName().equals("word")) {
                parts.add(MWEWord.fromNode(n));
            } else if(n.getNodeName().equals("queue")) {
                parts.add(MWEQueue.fromNode(n));
            } else if(n.getNodeName().equals("phrase")) {
                parts.add(MWEPhrase.fromNode(n));
            } else if(Utils.canSkipNode(n)) {

            } else {
                throw new Exception("Unexpected node " + n.getNodeName());
            }
        }
        return parts;
    }
}
