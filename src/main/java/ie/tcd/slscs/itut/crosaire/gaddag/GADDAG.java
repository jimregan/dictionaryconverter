/*
    Based on C# code from:
    https://nullwords.wordpress.com/2013/02/27/gaddag-data-structure/

    The MIT License (MIT)
    Copyright (c) 2013

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
    to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
    and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ie.tcd.slscs.itut.crosaire.gaddag;

import scala.Char;

import java.util.*;

public class GADDAG {
    public Node RootNode;
    public Node getRootNode() {
        return this.RootNode;
    }
    private void setRootNode(Node n) {
        this.RootNode = n;
    } 

    public GADDAG() {
        RootNode = new Node(Node.Root);
    }
 
    public void add(String word) {
        String w = word.toLowerCase();
        List<Node> prevNode = new ArrayList<Node>();
        for (int i = 1; i <= w.length(); i++) {
            String prefix = w.substring(0, i);
            String suffix = "";
            if (i != w.length()) {
                suffix = w.substring(i, w.length() - i);
            }
            String addword = _StringReverse(prefix) + Node.Break + suffix + Node.Eow;
 
            Node currentNode = RootNode;
            boolean breakFound = false;
            int j = 0;
            for (char c : addword.toCharArray()) {
                //Long words can be joined back together after the break point, cutting the tree size in half.
                if (breakFound && prevNode.size() > j) {
                    currentNode.addChild(c, prevNode.get(j));
                    break;
                }
 
                currentNode = currentNode.addChild(c);
 
                if (prevNode.size() == j) {
                    prevNode.add(currentNode);
                }
 
                if (c == Node.Break) {
                    breakFound = true;
                }
                j++;
            }
        }
    }

    private static String _GetWord(String str) {
        String word = "";
 
        for (int i = str.indexOf(Node.Break) - 1; i >= 0; i--) {
            word += str.charAt(i);
        }
 
        for (int i = str.indexOf(Node.Break) + 1; i < str.length(); i++) {
            word += str.charAt(i);
        }
 
        return word;
    }
 
    public List<String> ContainsHookWithRack(String hook, String rack) {
        hook = hook.toLowerCase();
        hook = _StringReverse(hook);
 
        Set<String> set = new HashSet<String>();
 
        _ContainsHookWithRackRecursive(RootNode, set, "", rack, hook);
        return new ArrayList<String>(set);
    }

    private static boolean isNullOrWhiteSpace(String s) {
        if(s == null) {
            return true;
        }
        for(char c : s.toCharArray()) {
            if(!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
    private static String remove(String s, int start, int end) {
        return s.substring(0, start) + s.substring(start + end);
    }
    private static void _ContainsHookWithRackRecursive(Node node, Set<String> rtn, String letters, String rack, String hook) {
        // Null nodes represent the EOW, so return the word.
        if (node == null) {
            String w = _GetWord(letters);
            if (!rtn.contains(w)) {
                rtn.add(w);
            }
            return;
        }
 
        // If the hook still contains letters, process those first.
        if (!isNullOrWhiteSpace(hook)) {
            letters += node.letter != Node.Root ? Character.toString(node.letter) : "";
 
            if (node.containsKey(hook.charAt(0))) {
                String h = hook.substring(1); //Pop the letter off the hook
                _ContainsHookWithRackRecursive(node.get(hook.charAt(0)), rtn, letters, rack, h);
            }
        } else {
            letters += node.letter != Node.Root ? Character.toString(node.letter) : "";

            List<Character> filt = new ArrayList<Character>();
            for(Character c : node.keys()) {
                if(rack.contains(Character.toString(c)) || c == Node.Eow || c == Node.Break) {
                    filt.add(c);
                }
            }
            for (char key : filt) {
                String r = (key != Node.Eow && key != Node.Break) ? remove(rack, rack.indexOf(key), 1) : rack; //Pull the letter from the rack
                _ContainsHookWithRackRecursive(node.get(key), rtn, letters, r, hook);
            }
        }
    }
 
    private static String _StringReverse(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
}
