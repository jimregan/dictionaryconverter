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

public class SimpleTextMacroAttr {
    String key;
    String value;
    boolean not;
    boolean list;
    boolean beginslist;
    boolean endslist;
    int position;
    String appliesTo;
    public SimpleTextMacroAttr(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public SimpleTextMacroAttr(String key, String value, boolean not) {
        this.key = key;
        this.value = value;
        this.not = not;
    }
    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
    public boolean getNot() {
        return not;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public boolean isNot() {
        return not;
    }
    public void setNot(boolean not) {
        this.not = not;
    }
    public boolean isList() {
        return list;
    }
    public void setList(boolean list) {
        this.list = list;
    }
    public boolean isBeginsList() {
        return beginslist;
    }
    public void setBeginsList(boolean list) {
        this.beginslist = list;
    }
    public boolean isEndsList() {
        return endslist;
    }
    public void setEndsList(boolean list) {
        this.endslist = list;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public String getAppliesTo() {
        return appliesTo;
    }
    public void setAppliesTo(String appliesTo) {
        this.appliesTo = appliesTo;
    }
    public static SimpleTextMacroAttr createLemma(String s) {
        return new SimpleTextMacroAttr("lemma", s);
    }
    public static SimpleTextMacroAttr fromSimpleText(String s) throws Exception {
        int start = 0;
        int end = s.length();
        if(s.startsWith("<")) {
            start = 1;
        }
        if(s.endsWith(">")) {
            end--;
        }
        String splitter = "=";
        boolean not = false;
        if(s.substring(start, end).contains("!=")) {
            not = true;
            splitter = "!=";
        }
        String[] pieces = s.substring(start, end).split(splitter);
        if(pieces.length > 2) {
            throw new Exception("Tag can only contain one '='");
        }
        String k = pieces[0];
        String v = "";
        if(pieces.length == 2) {
            v = pieces[1];
        }
        boolean list = false;
        boolean beginlist = false;
        boolean endlist = false;
        if(k.contains(":")) {
            String[] tmp = k.split(":");
            if(tmp.length > 2) {
                throw new Exception("Tag can only contain one ':'");
            }
            k = tmp[1];
            if(tmp[0].equals("list")) {
                list = true;
            } else if (tmp[0].equals("beginslist")) {
                beginlist = true;
            } else if (tmp[0].equals("endslist")) {
                endlist = true;
            } else {
                throw new Exception("Unexpected value: " + tmp[0] + " in " + s);
            }
        }
        if(v == null || pieces.length == 1) {
            v = "";
        }
        SimpleTextMacroAttr out = new SimpleTextMacroAttr(k, v, not);
        if(list) {
            out.setList(true);
        }
        if(beginlist) {
            out.setBeginsList(true);
        }
        if(endlist) {
            out.setEndsList(true);
        }
        return out;
    }

    @Override
    public String toString() {
        String out = "<" + key;
        if(value != null && !value.equals("")) {
            if(not) {
                out += "!";
            }
            out += "=" + value;
        }
        out += ">";
        return out;
    }
}
