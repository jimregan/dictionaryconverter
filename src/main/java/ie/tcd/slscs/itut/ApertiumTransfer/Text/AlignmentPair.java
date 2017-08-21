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

import scala.Int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlignmentPair {
    String left;
    String right;
    public AlignmentPair(String left, String right) {
        this.left = left;
        this.right = right;
    }
    public boolean canMakeLeftIndex() {
        return left.matches("^([0-9]+)[Cc]?$");
    }
    public boolean canMakeRightIndex() {
        return right.matches("^([0-9]+)[Cc]?$");
    }
    public boolean canMakeIndices() {
        return canMakeLeftIndex() && canMakeRightIndex();
    }
    public int getLeftPosition() {
        if(left.toLowerCase().endsWith("c")) {
            return Integer.parseInt(left.substring(0, left.length() - 1));
        } else {
            return Integer.parseInt(left);
        }
    }
    public int getRightPosition() {
        if(right.toLowerCase().endsWith("c")) {
            return Integer.parseInt(right.substring(0, right.length() - 1));
        } else {
            return Integer.parseInt(right);
        }
    }
    public boolean isSimpleLeft() {
        return (left.equals("0") || left.toLowerCase().endsWith("c"));
    }
    public boolean isSimpleRight() {
        return (right.equals("0") || right.toLowerCase().endsWith("c"));
    }
    public boolean isSimple() {
        return isSimpleLeft() && isSimpleRight();
    }
    public boolean leftChunkAlignment() {
        return left.toLowerCase().endsWith("c");
    }
    public boolean rightChunkAlignment() {
        return right.toLowerCase().endsWith("c");
    }
    public static Map<String, List<String>> getMapFromList(List<AlignmentPair> in) throws Exception {
        Map<String, List<String>> out = new HashMap<String, List<String>>();
        for(AlignmentPair al : in) {
            if(!out.containsKey(al.left)) {
                out.put(al.left, new ArrayList<String>());
            }
            out.get(al.left).add(al.right);
        }
        return out;
    }
    public static Map<String, List<String>> getReverseMapFromList(List<AlignmentPair> in) {
        Map<String, List<String>> out = new HashMap<String, List<String>>();
        for(AlignmentPair al : in) {
            out.get(al.right).add(al.left);
        }
        return out;
    }
    public static AlignmentPair reverse(AlignmentPair a) {
        return new AlignmentPair(a.right, a.left);
    }
    public static List<AlignmentPair> reverseList(List<AlignmentPair> l) {
        List<AlignmentPair> out = new ArrayList<AlignmentPair>();
        for(AlignmentPair a : l) {
            out.add(reverse(a));
        }
        return out;
    }
    public static boolean simpleAlignments(Map<String, List<String>> list) {
        for(String s : list.keySet()) {
            if(s.equals("0") || s.toLowerCase().endsWith("c")) {
                continue;
            }
            if(list.get(s).size() > 1) {
                return false;
            }
        }
        return true;
    }
    public static boolean simpleAlignments(List<AlignmentPair> list) throws Exception {
        return simpleAlignments(getMapFromList(list));
    }
    public static AlignmentPair fromString(String s, char delim) throws Exception {
        String[] tmp = s.trim().split(Character.toString(delim));
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
        for(String ap : s.trim().split(" ")) {
            out.add(AlignmentPair.fromString(ap, delim));
        }
        return out;
    }
    public static List<AlignmentPair> listFromString(String s) throws Exception {
        return listFromString(s, '-');
    }
    public boolean insertion() {
        return left.equals("0");
    }
    public boolean deletion() {
        return right.equals("0");
    }
    public boolean leftIsChunk() {
        return left.toLowerCase().endsWith("c");
    }
    public boolean rightIsChunk() {
        return right.toLowerCase().endsWith("c");
    }
    public static AlignmentPair offsetPair(AlignmentPair a, AlignmentPair b) {
        if(!a.isSimple() || !b.isSimple()) {
            return null;
        }
        int al = Integer.parseInt(a.left);
        int ar = Integer.parseInt(a.right);
        int bl = Integer.parseInt(b.left);
        int br = Integer.parseInt(b.right);
        return new AlignmentPair(Integer.toString(al - 1 + bl), Integer.toString(ar - 1 + br));
    }
    @Override
    public String toString() {
        return left + "-" + right;
    }
}
