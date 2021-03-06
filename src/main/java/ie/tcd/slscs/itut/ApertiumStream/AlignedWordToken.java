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

package ie.tcd.slscs.itut.ApertiumStream;

public class AlignedWordToken extends WordToken {
    private int alignment;
    private String prevariable;
    private String postvariable;
    private AlignedWordToken() {
        super();
    }
    AlignedWordToken(WordToken wt, int alignment) {
        this();
        this.lemh = wt.getLemh();
        this.lemq = wt.getLemq();
        this.tags = wt.getTags();
        this.alignment = alignment;
    }
    public int getAlignment() {
        return alignment;
    }
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }
    public String getPrevariable() {
        return prevariable;
    }
    public void setPrevariable(String prevariable) {
        this.prevariable = prevariable;
    }
    public String getPostvariable() {
        return postvariable;
    }
    public void setPostvariable(String postvariable) {
        this.postvariable = postvariable;
    }

    @Override
    public String toString() {
        return super.toString() + ":" + alignment;
    }
}
