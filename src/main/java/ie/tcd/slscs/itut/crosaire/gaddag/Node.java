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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {
    public static final char Break = '>';
    public static final char Eow = '$';
    public static final char Root = ' ';

    public char letter;
    public char getLetter() {
        return this.letter;
    }
    public void setLetter(char c) {
        this.letter = c;
    }

    public Map<Character, Node> children;

    public Node() {
        this.children = new HashMap<Character, Node>();
    }

    public Node(char letter) {
        this.letter = letter;
    }

    public boolean containsKey(char key) {
        return children.containsKey(key);
    }

    public Node addChild(char letter) {
        if (children == null)
            children = new HashMap<Character, Node>();

        if (!children.containsKey(letter)) {
            Node node = letter != Eow ? new Node(letter) : null;
            children.put(letter, node);
            return node;
        }

        return (Node) children.get(letter);
    }

    public Node addChild(char letter, Node node) {
        if (children == null)
            children = new HashMap<Character, Node>();

        if (!children.containsKey(letter))
        {
            children.put(letter, node);
            return node;
        }

        return (Node) children.get(letter);
    }

    @Override
    public String toString() {
        return Character.toString(this.letter);
    }
}