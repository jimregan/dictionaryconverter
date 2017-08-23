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

import java.util.List;

public class MWEEntry extends MWEContainer {
    String tags;
    MWEEntry() {
        super();
    }
    MWEEntry(String tags) {
        this();
        this.tags = tags;
    }
    public static MWEEntry fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("entry")) {
            if(n.getAttributes() == null || n.getAttributes().getLength() == 0) {
                throw new Exception("No attribute \"tags\" found");
            }
            String phrase = Utils.attrib(n, "tags");
            if(phrase == null || phrase.equals("")) {
                throw new Exception("Missing attribute tags");
            }
            if(n.getChildNodes().getLength() == 0) {
                throw new Exception("Missing child elements");
            }
            List<MWEPart> parts = MWEContainer.fromNodeList(n.getChildNodes());
            MWEEntry out = new MWEEntry(phrase);
            out.setParts(parts);
            return out;
        } else {
            throw new Exception("Unexpected node: " + n.getNodeName());
        }
    }

}
