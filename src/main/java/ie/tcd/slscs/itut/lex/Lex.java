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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Lex {
    Map<String, CharGroup> chargroups;
    Lex() {
        chargroups = new HashMap<String, CharGroup>();
    }

    public static Lex loadXML(InputSource is) throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(is);
        String root = doc.getDocumentElement().getNodeName();

        Map<String, CharGroup> chargroups = new HashMap<String, CharGroup>();

        if (root != "rules") {
            throw new IOException("Expected root node " + root);
        }
        NodeList nl = doc.getDocumentElement().getChildNodes();
        for(int i = 0; i < nl.getLength(); i++) {
            Node itemi = nl.item(i);
            if(itemi.getNodeName().equals("chargroup")) {
                CharGroup cg = CharGroup.fromNode(itemi);
                if(cg.isNegated()) {
                    if(chargroups.containsKey(cg.getNegates())) {
                        cg.setRawCharacters(chargroups.get(cg.getNegates()).getRawCharacters());
                    } else {
                        throw new Exception("chargroup " + cg.getName() + " negates non-existent chargroup " + cg.getNegates());
                    }
                }
                chargroups.put(cg.getName(), cg);
            }
        }
        return null;
    }
    public static Lex loadXML(InputStream is) throws Exception {
        return loadXML(new InputSource(is));
    }
    public static Lex loadXML(File f) throws Exception {
        return loadXML(new FileInputStream(f));
    }
    public static Lex loadXML(String filename) throws Exception {
        return loadXML(new File(filename));
    }
}
