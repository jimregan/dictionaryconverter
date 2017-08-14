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

import java.util.ArrayList;
import java.util.List;

public class SimpleMacroCall {
    String name;
    List<String> params;
    SimpleMacroCall() {
        params = new ArrayList<String>();
    }
    public SimpleMacroCall(String name, List<String> params) {
        this();
        this.name = name;
        this.params = params;
    }
    public String getName() {
        return name;
    }
    public List<String> getParams() {
        return params;
    }
    public static SimpleMacroCall fromString(String s) throws Exception {
        String[] top = s.split(":");
        List<String> params = new ArrayList<String>();
        if(top.length != 2) {
            throw new Exception("Format error in " + s);
        }
        String name = top[0].trim();
        for(String prm : top[1].trim().split(",")) {
            params.add(prm.trim());
        }
        return new SimpleMacroCall(name, params);
    }
    public static List<SimpleMacroCall> listFromString(String s) throws Exception {
        List<SimpleMacroCall> out = new ArrayList<SimpleMacroCall>();
        for(String tmp : s.split(";")) {
            out.add(fromString(tmp));
        }
        return out;
    }
}
