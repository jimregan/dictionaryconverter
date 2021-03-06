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

package ie.tcd.slscs.itut.ApertiumTransfer.Text;

import ie.tcd.slscs.itut.ApertiumTransfer.AttrItem;
import ie.tcd.slscs.itut.ApertiumTransfer.DefAttr;

import java.io.*;
import java.util.*;

public class Attributes {
    String name;
    boolean fromDictionary = false;
    String undefined;
    String any;
    List<String> items;
    Attributes() {
        items = new ArrayList<String>();
    }
    Attributes(String name, boolean fromDictionary, String any, String undefined, List<String> items) {
        this();
        this.name = name;
        this.any = any;
        this.undefined = undefined;
        this.fromDictionary = fromDictionary;
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }
    public static Map<String, Attributes> listToMap(List<Attributes> list) {
        Map<String, Attributes> out = new HashMap<String, Attributes>();
        for (Attributes a : list) {
            out.put(a.name, a);
        }
        return out;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isFromDictionary() {
        return fromDictionary;
    }
    public void setFromDictionary(boolean fromDictionary) {
        this.fromDictionary = fromDictionary;
    }
    public String getUndefined() {
        return undefined;
    }
    public void setUndefined(String undefined) {
        this.undefined = undefined;
    }
    public String getAny() {
        return any;
    }
    public void setAny(String any) {
        this.any = any;
    }
    public void setItems(List<String> items) {
        this.items = items;
    }
    public static Attributes fromText(String s) throws Exception {
        String[] tmppait = s.split("=");
        if(tmppait.length != 2) {
            throw new Exception("Error reading line: " + s + " - expected 2 parts, got " + tmppait.length);
        }
        String name = tmppait[0].trim();
        boolean fromDictionary = false;
        if(name.endsWith("!")) {
            fromDictionary = true;
            name = name.substring(0, name.length() - 1);
        }
        String any = "";
        String undefined = "";
        List<String> items = new ArrayList<String>();
        for(String tag : tmppait[1].trim().split(" ")) {
            if(tag.endsWith("?")) {
                if(!any.equals("")) {
                    throw new Exception("Tag <" + tag + "> marked as \"any\" tag, but already have <" + any + ">");
                }
                any = tag.substring(0, tag.length() - 1);
                items.add(any);
            } else if(tag.endsWith("!")) {
                if(!undefined.equals("")) {
                    throw new Exception("Tag <" + tag + "> marked as \"to be defined\" tag, but already have <" + undefined + ">");
                }
                undefined = tag.substring(0, tag.length() - 1);
                items.add(undefined);
            } else {
                items.add(tag);
            }
        }
        return new Attributes(name, fromDictionary, any, undefined, items);
    }
    public static List<Attributes> fromFile(BufferedReader br) throws IOException {
        List<Attributes> out = new ArrayList<Attributes>();
        String line;
        int lineno = 0;
        while((line = br.readLine()) != null) {
            lineno++;
            try {
                if(!"".equals(line) && !line.startsWith("#")) {
                    out.add(fromText(line));
                }
            } catch (Exception e) {
                throw new IOException(e.getMessage() + " on line " + lineno);
            }
        }
        return out;
    }
    public static List<Attributes> fromFile(InputStreamReader isr) throws Exception {
        return fromFile(new BufferedReader(isr));
    }
    public static List<Attributes> fromFile(InputStream is) throws Exception {
        return fromFile(new InputStreamReader(is, "UTF-8"));
    }
    public static List<Attributes> fromFile(FileInputStream fi) throws Exception {
        return fromFile(new InputStreamReader(fi, "UTF-8"));
    }
    public static List<Attributes> fromFile(File f) throws Exception {
        return fromFile(new FileInputStream(f));
    }
    public static List<Attributes> fromFile(String s) throws Exception {
        return fromFile(new File(s));
    }
    public static List<DefAttr> defAttrFromFile(BufferedReader br) throws IOException {
        List<DefAttr> out = new ArrayList<DefAttr>();
        for(Attributes a : fromFile(br)) {
            out.addAll(fromAttributes(a));
        }
        return out;
    }
    public static List<DefAttr> defAttrFromFile(InputStreamReader isr) throws Exception {
        return defAttrFromFile(new BufferedReader(isr));
    }
    public static List<DefAttr> defAttrFromFile(InputStream is) throws Exception {
        return defAttrFromFile(new InputStreamReader(is, "UTF-8"));
    }
    public static List<DefAttr> defAttrFromFile(FileInputStream fi) throws Exception {
        return defAttrFromFile(new InputStreamReader(fi, "UTF-8"));
    }
    public static List<DefAttr> defAttrFromFile(File f) throws Exception {
        return defAttrFromFile(new FileInputStream(f));
    }
    public static List<DefAttr> defAttrFromFile(String s) throws Exception {
        return defAttrFromFile(new File(s));
    }
    public static List<AttrItem> convertItems(List<String> in) {
        List<AttrItem> out = new ArrayList<AttrItem>();
        for(String s : in) {
            out.add(new AttrItem(s));
        }
        return out;
    }
    public static List<DefAttr> fromAttributes(Attributes a) {
        List<DefAttr> out = new ArrayList<DefAttr>();
        List<AttrItem> base = convertItems(a.getItems());
        if(!"".equals(a.any)) {
            String none_name = a.name + "_no_" + a.any;
            String only_name = a.name + "_" + a.any;
            out.add(new DefAttr(none_name, base));
            AttrItem any_attr = new AttrItem(a.any);
            base.add(any_attr);
            List<AttrItem> anylist = new ArrayList<AttrItem>();
            anylist.add(any_attr);
            out.add(new DefAttr(only_name, anylist));
        }
        out.add(new DefAttr(a.name, base));
        return out;
    }

    private String itemToString(String s) {
        if(s.equals(any)) {
            return s + "?";
        } else if(s.equals(undefined)) {
            return s + "!";
        } else {
            return s;
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if(fromDictionary) {
            sb.append('!');
        }
        sb.append(" = ");
        Iterator<String> it = items.iterator();
        if(it.hasNext()) {
            sb.append(itemToString(it.next()));
        }
        while(it.hasNext()) {
            sb.append(' ');
            sb.append(itemToString(it.next()));
        }
        return sb.toString();
    }
}
