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
    public SLTLPair replace(SLTLPair input) {
        int start = 0;
        String target = input.target;
        if(input.target.matches("^[0-9]+\\) ?") && !input.source.matches("^[0-9]+\\) ?")) {
            Pattern p = Pattern.compile("(^[0-9]+\\) ?)");
            Matcher m = p.matcher(input.target);
            start = m.end();
            target = input.target.substring(start);
            this.replacement = true;
        }
        if(target.endsWith("Uimh.") && input.source.matches("No\\. [0-9]+\\)?$")) {
            Pattern p = Pattern.compile(".*No\\. ([0-9]+\\)?)$");
            Matcher m = p.matcher(input.source);
            String add = m.group(1);
            this.replacement = true;
            return new SLTLPair(input.id, input.source, target + " " + add);
        } else {
            return new SLTLPair(input.id, input.source, target);
        }
    }
}
