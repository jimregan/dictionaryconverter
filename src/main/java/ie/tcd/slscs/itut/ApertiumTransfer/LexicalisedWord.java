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

import ie.tcd.slscs.itut.ApertiumStream.WordToken;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.List;

public class LexicalisedWord {
    String lemma;
    String tags;
    LexicalisedWord(String lemma, String tags) {
        this.lemma = lemma;
        this.tags = tags;
    }
    public WordToken toWordToken() {
        String[] atags = this.tags.split("\\.");
        List<String> tags = Arrays.asList(atags);
        return new WordToken(this.lemma, "", tags);
    }
    public static LexicalisedWord fromNode(Node n) throws Exception {
        if(n.getNodeName().equals("lexicalized-word")) {
            String tags = "";
            String lemma = "";
            if(n.getAttributes().getLength() != 0) {
                if(n.getAttributes().getNamedItem("tags") != null) {
                    tags = n.getAttributes().getNamedItem("tags").getTextContent();
                }
                if(n.getAttributes().getNamedItem("lemma") != null) {
                    lemma = n.getAttributes().getNamedItem("lemma").getTextContent();
                }
            }
            return new LexicalisedWord(lemma, tags);
        } else {
            throw new Exception("Node does not contain lexicalized-word");
        }
    }

}
