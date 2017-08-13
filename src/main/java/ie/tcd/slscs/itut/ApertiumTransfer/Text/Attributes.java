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

import javax.xml.stream.events.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attributes {
    String name;
    String undefined;
    String any;
    List<String> items;
    Attributes() {
        items = new ArrayList<String>();
    }
    Attributes(String name, String any, String undefined, List<String> items) {
        this();
        this.name = name;
        this.any = any;
        this.undefined = undefined;
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
    public static Attributes fromText(String s) throws Exception {
        String[] tmppait = s.split("=");
        if(tmppait.length != 2) {
            throw new Exception("Error reading line: " + s + " - expected 2 parts, got " + tmppait.length);
        }
        String name = tmppait[0].trim();
        String any = "";
        String undefined = "";
        List<String> items = new ArrayList<String>();
        for(String tag : tmppait[1].trim().split(" ")) {
            if(tag.endsWith("?")) {
                if(!any.equals("")) {
                    throw new Exception("Tag <" + tag + "> marked as \"any\" tag, but already have <" + any + ">");
                }
                any = tag.substring(0, tag.length() - 1);
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
        return new Attributes(name, any, undefined, items);
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
}
