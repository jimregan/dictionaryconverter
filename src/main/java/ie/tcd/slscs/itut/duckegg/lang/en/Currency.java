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

package ie.tcd.slscs.itut.duckegg.lang.en;

import ie.tcd.slscs.itut.duckegg.*;
import ie.tcd.slscs.itut.duckegg.Integer;

import java.util.regex.Pattern;

public class Currency extends Rule {
    Currency() throws Exception {
        super();
        name = "currency";
        pattern = new PatternContainer
                .Builder()
                .addPattern(new Regex("[€£$]"))
                .addPattern(new Integer().getRegex())
                .addPattern(new Regex("\\."))
                .addPattern(new Integer().getRegex())
                .addPattern(new Regex("p|c"))
                .build();
        pat = Pattern.compile(getPattern());
    }
    @Override
    public Result setResult(Result res) throws Exception {
        if(res.getRawparts().size() < 5 || res.getRawparts().get(1) == null || res.getRawparts().get(3) == null) {
            throw new Exception("Empty result " + res.getRawparts().size());
        } else {
            ie.tcd.slscs.itut.duckegg.Currency c = new ie.tcd.slscs.itut.duckegg.Currency(res.getRawparts().get(0), res.getRawparts().get(1), res.getRawparts().get(3));
        }
        return res;
    }

}
