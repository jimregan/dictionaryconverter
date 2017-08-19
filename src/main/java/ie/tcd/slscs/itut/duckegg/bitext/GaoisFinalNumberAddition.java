/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Jim O'Regan
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

package ie.tcd.slscs.itut.duckegg.bitext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GaoisFinalNumberAddition extends Rule {
    public GaoisFinalNumberAddition() {
        this.name = "gaois-add-final-numbers";
    }
    @Override
    public SLTLPair replace(SLTLPair input) throws Exception {
        int start = 0;
        String target = input.target;
        Pattern ptrg = Pattern.compile("^[0-9]+\\) ?");
        Matcher mtrg = ptrg.matcher(input.target);
        Matcher msrc = ptrg.matcher(input.source);
        Pattern pend = Pattern.compile("No\\. ([0-9]+\\)?)$");
        Matcher mend = pend.matcher(input.source);
        if(mtrg.find() && !msrc.find()) {
            start = mtrg.end();
            target = input.target.substring(start);
            this.replacement = true;
        }
        if(target.endsWith("Uimh.") && mend.find()) {
            String add = mend.group(1);
            this.replacement = true;
            return new SLTLPair(input.id, input.source, target + " " + add);
        } else {
            return new SLTLPair(input.id, input.source, target);
        }
    }
}
