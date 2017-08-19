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

package ie.tcd.slscs.itut.duckegg.lang.ga;

import ie.tcd.slscs.itut.duckegg.*;
import ie.tcd.slscs.itut.duckegg.Integer;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class FuzzyCurrency extends FuzzyRule {
    FuzzyCurrency() throws Exception {
        super();
        name = "currency";
        pattern = new PatternContainer
                .Builder()
                .addPattern(new FuzzyInteger().getRegex())
                .addPattern(new Regex("\\."))
                .addPattern(new FuzzyInteger().getRegex())
                .addPattern(new Regex("p|c"))
                .build();
        pat = Pattern.compile(getPattern());
    }
    @Override
    public Result setResult(Result res) throws Exception {
        if(res.getRawparts().size() < 4 || res.getRawparts().get(0) == null || res.getRawparts().get(2) == null) {
            throw new Exception("Empty result " + res.getRawparts().size());
        } else {
            NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
            Set<String> a = OCRIntStringNormaliser.normalise(res.getRawparts().get(0));
            Set<String> b = OCRIntStringNormaliser.normalise(res.getRawparts().get(2));
            for(String s : a) {
                Number numa = format.parse(s);
                int inta = numa.intValue();
                for(String t : b) {
                    Result toadd = new Result(res.getRawparts().get(0) + "." + res.getRawparts().get(2) + res.getRawparts().get(3));
                    Number numb = format.parse(t);
                    int intb = numb.intValue();
                    ie.tcd.slscs.itut.duckegg.Currency c = new ie.tcd.slscs.itut.duckegg.Currency(inta, intb);
                    if(res.getRawparts().get(3).equals("p")) {
                        c.setUnit(CurrencyUnit.Pound);
                    } else {
                        c.setUnit(CurrencyUnit.Euro);
                    }
                    toadd.setResult(c);
                    res.setType(ResultType.CURRENCY);
                    res.addFuzzyResult(toadd);
                }
            }
        }
        return res;
    }

}
