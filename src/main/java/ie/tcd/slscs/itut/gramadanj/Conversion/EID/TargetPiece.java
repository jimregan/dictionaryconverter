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

package ie.tcd.slscs.itut.gramadanj.Conversion.EID;

import java.util.ArrayList;
import java.util.List;

public class TargetPiece {
    String preNote;
    String first;
    String label;
    String second;
    String postNote;
    List<GrammarNote> notes;
    TargetPiece() {
        notes = new ArrayList<GrammarNote>();
    }
    TargetPiece(String preNote, String first, String label, String second, String postNote) {
        this();
        this.first = first;
        this.preNote = preNote;
        this.label = label;
        this.second = second;
        this.postNote = postNote;
    }
    TargetPiece(String preNote, String first, String label, String second, String postNote, List<GrammarNote> notes) {
        this();
        this.first = first;
        this.preNote = preNote;
        this.label = label;
        this.second = second;
        this.postNote = postNote;
        this.notes = notes;
    }
}
