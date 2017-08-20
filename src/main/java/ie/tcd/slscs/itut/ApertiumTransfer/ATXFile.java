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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ATXFile {
    String source;
    String target;
    List<LexicalisedWord> sourcelex;
    List<LexicalisedWord> targetlex;
    ATXFile() {
        this.sourcelex = new ArrayList<LexicalisedWord>();
        this.targetlex = new ArrayList<LexicalisedWord>();
    }
    ATXFile(String source, String target, List<LexicalisedWord> srclist, List<LexicalisedWord> trglist) {
        this();
        this.source = source;
        this.target = target;
        this.sourcelex = srclist;
        this.targetlex = trglist;
    }
    public boolean skipableNode(Node n) {
        if(n.getNodeName().equals("#text") && n.getTextContent().trim().equals("")) {
            return true;
        } else if(n.getNodeType() == Element.COMMENT_NODE) {
            return true;
        } else if(n.getNodeType() == Element.PROCESSING_INSTRUCTION_NODE) {
            return true;
        } else if(n.getNodeType() == Element.ELEMENT_NODE) {
            return false;
        } else {
            return false;
        }
    }
    public ATXFile loadXML(InputSource is) throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(is);
        String root = doc.getDocumentElement().getNodeName();
        if (root != "transfer-at") {
            throw new IOException("Expected root node " + root);
        }
        String srclang = doc.getDocumentElement().getAttribute("source").toString();
        String trglang = doc.getDocumentElement().getAttribute("target").toString();
        List<LexicalisedWord> srclist = new ArrayList<LexicalisedWord>();
        List<LexicalisedWord> trglist = new ArrayList<LexicalisedWord>();
        NodeList nl = doc.getDocumentElement().getChildNodes();
        for(int i = 0; i < nl.getLength(); i++) {
            Node itemi = nl.item(i);
            if(itemi.getNodeName().equals("source")) {
                for(int j = 0; j < itemi.getChildNodes().getLength(); j++) {
                    Node itemj = itemi.getChildNodes().item(j);
                    if(itemj.getNodeName().equals("lexicalized-words")) {
                        for(int k = 0; k < itemj.getChildNodes().getLength(); k++) {
                            Node itemk = itemj.getChildNodes().item(k);
                            if(itemk.getNodeName().equals("lexicalized-word")) {
                                srclist.add(LexicalisedWord.fromNode(itemk));
                            } else if(itemk.getNodeName().equals("#text") && itemk.getTextContent().trim().equals("")) {
                                // Skip
                            }
                        }
                    } else if(itemj.getNodeName().equals("#text") && itemj.getTextContent().trim().equals("")) {
                        // Skip
                    }
                }
            } else if(itemi.getNodeName().equals("target")) {
                for(int j = 0; j < itemi.getChildNodes().getLength(); j++) {
                    Node itemj = itemi.getChildNodes().item(j);
                    if(itemj.getNodeName().equals("lexicalized-words")) {
                        for(int k = 0; k < itemj.getChildNodes().getLength(); k++) {
                            Node itemk = itemj.getChildNodes().item(k);
                            if(itemk.getNodeName().equals("lexicalized-word")) {
                                trglist.add(LexicalisedWord.fromNode(itemk));
                            } else if(itemk.getNodeName().equals("#text") && itemk.getTextContent().trim().equals("")) {
                                // Skip
                            }
                        }
                    } else if(itemj.getNodeName().equals("#text") && itemj.getTextContent().trim().equals("")) {
                        // Skip
                    }
                }
            } else if(itemi.getNodeName().equals("#text") && nl.item(i).getTextContent().trim().equals("")) {
                // Nothing
            } else if(itemi.getNodeType() != Element.ELEMENT_NODE) {
                // I think this skips everything!
            } else {
                throw new Exception("Unexpected node: " + nl.item(i).getNodeName());
            }
        }
        return new ATXFile(srclang, trglang, srclist, trglist);
    }
}
