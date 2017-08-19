/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Jim O'Regan
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

package ie.tcd.slscs.itut.duckegg.format.gaois;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TMX {
    //TMXHeader header;
    private List<TU> TUs;
    TMX() {
        this.TUs = new ArrayList<TU>();
    }
    public TMX(List<TU> tunits) {
        this.TUs = tunits;
    }
    public List<TU> getTUs() {
        return this.TUs;
    }
    public static TMX readFile(InputStreamReader isr) throws Exception {
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        int lineno = 0;
        String line;
        while((line = br.readLine()) != null) {
            lineno++;
            if(line.contains("<!DOCTYPE tmx")) {
                line = "";
            }
            if(line.contains("<prop ")) {
                line = line.replace(" & ", " &amp; ").replace("'s", "&apos;s");
            }
            sb.append(line);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(sb.toString()));
        Document doc = builder.parse(is);
        String root = doc.getDocumentElement().getNodeName();
        if(!root.equals("tmx")) {
            throw new Exception("Not a TMX document");
        }

        List<TU> tunits = new ArrayList<TU>();

        NodeList nl = doc.getDocumentElement().getChildNodes();
        for(int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if(n.getNodeName().equals("header")) {
                // do nothing
            } else if(n.getNodeName().equals("body")) {
                NodeList chnl = n.getChildNodes();
                for(int j = 0; j < chnl.getLength(); j++) {
                    Node cur = chnl.item(j);
                    if(cur.getNodeName().equals("tu")) {
                        tunits.add(TU.fromNode(cur));
                    } else if(n.getNodeType() == Element.COMMENT_NODE || n.getNodeType() == Element.CDATA_SECTION_NODE) {
                        //do nothing
                    } else if(n.getNodeName().equals("#text") && n.getTextContent().trim().equals("")) {
                        // do nothing
                    } else {
                        throw new Exception("Unexpected node: " + n.getNodeName());
                    }
                }
            } else if(n.getNodeType() == Element.COMMENT_NODE || n.getNodeType() == Element.CDATA_SECTION_NODE) {
                //do nothing
            } else if(n.getNodeName().equals("#text") && n.getTextContent().trim().equals("")) {
                // do nothing
            } else {
                throw new Exception("Unexpected node: " + n.getNodeName());
            }
        }
        return new TMX(tunits);
    }
    public static TMX readFile(InputStream is) throws Exception {
        return readFile(new InputStreamReader(is, "UTF-16"));
    }
    public static TMX readFile(FileInputStream fi) throws Exception {
        return readFile(new InputStreamReader(fi, "UTF-16"));
    }
    public static TMX readFile(File f) throws Exception {
        return readFile(new FileInputStream(f));
    }
    public static TMX readFile(String filename) throws Exception {
        return readFile(new File(filename));
    }

}
