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
import java.util.Iterator;
import java.util.List;

public class SimpleTextMacroEntry {
    int position;
    boolean chunkAlignment;
    boolean isDeletion;
    boolean isInsertion;
    List<SimpleTextMacroAttr> attrs;
    List<SimpleTextMacroEntry> target;
    SimpleTextMacroEntry() {
        attrs = new ArrayList<SimpleTextMacroAttr>();
        target = new ArrayList<SimpleTextMacroEntry>();
    }
    SimpleTextMacroEntry(int pos, List<SimpleTextMacroAttr> attrs, boolean isChunk) {
        this();
        this.position = pos;
        this.attrs = attrs;
        this.chunkAlignment = isChunk;
    }
    SimpleTextMacroEntry(int pos, List<SimpleTextMacroAttr> attrs) {
        this(pos, attrs, false);
    }
    SimpleTextMacroEntry(int pos, List<SimpleTextMacroAttr> attrs, boolean isChunk, List<SimpleTextMacroEntry> target) {
        this(pos, attrs, isChunk);
        this.setTarget(target);
    }
    public int getPosition() {
        return position;
    }
    public List<SimpleTextMacroAttr> getAttrs() {
        return attrs;
    }
    public List<SimpleTextMacroEntry> getTarget() {
        return target;
    }
    public void setTarget(List<SimpleTextMacroEntry> target) {
        this.target = target;
    }
    public boolean hasTarget() {
        return target.size() != 0;
    }
    public boolean isDeletion() {
        return isDeletion;
    }
    public void setDeletion(boolean deletion) {
        isDeletion = deletion;
    }
    public boolean isInsertion() {
        return isInsertion;
    }
    public void setInsertion(boolean insertion) {
        isInsertion = insertion;
    }
    public boolean isChunkAlignment() {
        return chunkAlignment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append(position);
        if(chunkAlignment) {
            sb.append("(C)");
        }
        Iterator<SimpleTextMacroAttr> sait = attrs.iterator();
        boolean sait_next = sait.hasNext();
        if(sait_next) {
            sb.append('[');
            sb.append(sait.next().toString());
        }
        while(sait.hasNext()) {
            sb.append(',');
            sb.append(sait.next().toString());
        }
        if(sait_next) {
            sb.append(']');
        }
        if(target.size() == 0) {
            sb.append("[--]");
        } else {
            sb.append('[');
            Iterator<SimpleTextMacroEntry> smtit = target.iterator();
            sb.append(smtit.next().toString());
            while (smtit.hasNext()) {
                sb.append(',');
                sb.append(smtit.next());
            }
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }
}
