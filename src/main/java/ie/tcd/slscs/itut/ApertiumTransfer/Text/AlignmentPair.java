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

public class AlignmentPair {
    String left;
    String right;
    public AlignmentPair(String left, String right) {
        this.left = left;
        this.right = right;
    }
    public boolean canMakeIndices() {
        return left.matches("^([0-9]*)$") && right.matches("^([0-9]*)$");
    }

    public static AlignmentPair fromString(String s, char delim) throws Exception {
        String[] tmp = s.split(Character.toString(delim));
        if(tmp.length != 2) {
            throw new Exception("Alignment piece " + s + " does not contain one single " + delim);
        }
        return new AlignmentPair(tmp[0], tmp[1]);
    }
    public static AlignmentPair fromString(String s) throws Exception {
        return fromString(s, '-');
    }
    public static AlignmentPair fromATTString(String s) throws Exception {
        return fromString(s, ':');
    }
    public static List<AlignmentPair> listFromString(String s, char delim) throws Exception {
        List<AlignmentPair> out = new ArrayList<AlignmentPair>();
        for(String ap : s.split(" ")) {
            out.add(AlignmentPair.fromString(ap, delim));
        }
        return out;
    }
    public static List<AlignmentPair> listFromString(String s) throws Exception {
        return listFromString(s, '-');
    }
}