package ie.tcd.slscs.itut.gramadanj;

/*
 * Copyright 2016 Jim O'Regan <jaoregan@tcd.ie>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class Utils {

    /**
     * As close as I can get to Perl's s///g operator
     */
    static String s(String text, String pattern, String replacement) {
        String ret=text;
        if (text.matches(pattern)) {
            ret=text.replaceAll(pattern, replacement);
        }
        return ret;
    }

    public static String trim(String s) {
        int start = 0;
        int end = s.length() - 1;
        for(int i = start; i < end; i++) {
            if(s.charAt(i) == ' ' || s.charAt(i) == '\n' || s.charAt(i) == '\t') {
                start++;
            } else {
                break;
            }
        }
        for(int i = end; i > start; i--) {
            if(s.charAt(i) == ' ' || s.charAt(i) == '\n' || s.charAt(i) == '\t') {
                end--;
            } else {
                break;
            }
        }
        if(start == end) {
            return "";
        }
        return s.substring(start, end + 1);
    }

    public static String cleanTrailingPunctuation(String s) {
        char last = s.charAt(s.length() - 1);
        if (last == '.' || last == ',' || last == ';' || last == ':') {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    /**
     * Expands an abbreviated grammatical entry of the kind used in
     * Foclóir Gaeilge-Béarla (Ó Dónaill).
     * @param base The headword to use as a basis for the expanded entry
     * @param mut the abbreviated ending
     * @return The expanded wordform
     */
    public static String expandFGB(String base, String mut) {
        String ret = trim(mut);
        if(ret.charAt(0) == '~') {
            return ret.replaceFirst("~", base);
        } else if(ret.charAt(0) == '-') {
            int offset = base.lastIndexOf(mut.charAt(1));
            return base.substring(0, offset) + ret.substring(1);
        } else {
            return base;
        }
    }

    /**
     * Expands parenthetical variants of the kind used in FGB:
     * "colo(u)r" to "color" and "colour"
     * @param s The string to expand
     * @return A string array containing both alternatives
     */
    public static String[] expandParentheticalVariants(String s) {
        String[] out = new String[2];
        String first = "";
        String second = "";
        boolean inside = false;
        for(char c : s.toCharArray()) {
            if(inside) {
                if(c == ')') {
                    inside = false;
                } else {
                    second += c;
                }
            } else {
                if(c == '(') {
                    inside = true;
                } else {
                    first += c;
                    second += c;
                }
            }
        }
        out[0] = first;
        out[1] = second;
        return out;
    }

    public static <T extends Comparable<? super T>> boolean equalLists(List<T> a, List<T> b) {
        if(a == null && b == null) {
            return true;
        }
        if(a == null && b != null) {
            return false;
        }
        if(a != null && b == null) {
            return false;
        }
        if(a.size() != b.size()) {
            return false;
        }
        a = new ArrayList<T>(a);
        b = new ArrayList<T>(b);
        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }

    public static <T> List<T> listclone(List<T> l) {
        List<T> out = new ArrayList<T>();
        for(T t : l) {
            out.add(t);
        }
        return out;
    }

    public static <T extends Comparable<? super T>> boolean listStartsWithList(List<T> a, List<T> b) {
        if(a == null && b == null) {
            return true;
        }
        if(a == null && b != null) {
            return false;
        }
        if(a != null && b == null) {
            return false;
        }
        if(a.size() <= b.size()) {
            return false;
        }
        for (int i = 0; i < b.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts a string to a Node, mainly for use with tests
     * @param s The XML string to convert
     * @return an XML Node
     * @throws Exception
     */
    public static Node stringToNode(String s) throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new InputSource(new StringReader(s)));
        String root = doc.getDocumentElement().getNodeName();
        Node n = doc.getDocumentElement().cloneNode(true);
        return n;
    }

    /**
     * Slurp a .tsv file into a map
     */
    public static Map<String, String> readTSV(InputStreamReader isr) throws Exception {
        Map<String, String> ret = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(isr);
        String line;
        int lineno = 0;
        while((line = br.readLine()) != null) {
            lineno++;
            String[] sp = line.split("\\t");
            if(sp.length != 2) {
                throw new IOException("Error reading file at line: " + lineno);
            }
            ret.put(sp[0], sp[1]);
        }
        return ret;
    }
    public static Map<String, String> readTSV(InputStream is) throws Exception {
        return readTSV(new InputStreamReader(is, "UTF-8"));
    }
    public static Map<String, String> readTSV(FileInputStream fi) throws Exception {
        return readTSV(new InputStreamReader(fi, "UTF-8"));
    }
    public static Map<String, String> readTSV(File f) throws Exception {
        return readTSV(new FileInputStream(f));
    }

    /**
     * Checks if a text node contains the given string
     * @param n the Node to check
     * @param s the String to check for
     * @return false if node is not a text node, or does not contain s
     */
    public static boolean textNodeContains(Node n, String s) {
        if(!n.getNodeName().equals("#text")) {
            return false;
        } else {
            if(n.getTextContent().contains(s)) {
                return true;
            } else {
                return false;
            }
        }
    }
    public static boolean trimmedTextNodeEquals(Node n, String s) {
        if(!n.getNodeName().equals("#text")) {
            return false;
        } else {
            if(trim(n.getTextContent()).equals(s)) {
                return true;
            } else {
                return false;
            }
        }
    }
    public static boolean trimmedTextNodeEquals(Node n, String[] sl) {
        if(!n.getNodeName().equals("#text")) {
            return false;
        } else {
            for(String s : sl) {
                if(trim(n.getTextContent()).equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean trimmedTextNodeEquals(Node n, List<String> sl) {
        if(!n.getNodeName().equals("#text")) {
            return false;
        } else {
            for(String s : sl) {
                if(trim(n.getTextContent()).equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * join a list of strings with the specified delimiter
     * @param l the list of strings
     * @param delim the delimiter to join them with
     * @return
     */
    public static String join(List<String> l, String delim) {
        StringBuilder s = new StringBuilder();
        Iterator<String> it = l.iterator();
        if (it.hasNext()) {
            s.append(it.next());
        }
        while (it.hasNext()) {
            s.append(delim);
            s.append(it.next());
        }
        return s.toString();
    }
    public boolean canSkipNode(Node n) {
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

}
