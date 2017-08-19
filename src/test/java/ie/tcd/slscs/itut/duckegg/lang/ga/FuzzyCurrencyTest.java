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

import ie.tcd.slscs.itut.duckegg.CurrencyUnit;
import ie.tcd.slscs.itut.duckegg.ResultType;
import junit.framework.TestCase;

import static org.junit.Assert.*;

public class FuzzyCurrencyTest extends TestCase {
    public void testFuzzyCurrency() throws Exception {
        String in = "/35.l5p";
        ie.tcd.slscs.itut.duckegg.Currency expc1 = new ie.tcd.slscs.itut.duckegg.Currency(135, 15);
        expc1.setUnit(CurrencyUnit.Pound);
        ie.tcd.slscs.itut.duckegg.Currency expc2 = new ie.tcd.slscs.itut.duckegg.Currency(735, 15);
        expc2.setUnit(CurrencyUnit.Pound);
        FuzzyCurrency fc = new FuzzyCurrency();
        assertNotEquals("", fc.getPattern());
        assertEquals(true, fc.matches(in));
        assertEquals(2, fc.getFuzzyResults().get(0).size());
        assertEquals(expc1, fc.getFuzzyResults().get(0).get(0));
        assertEquals(expc2, fc.getFuzzyResults().get(0).get(1));

    }

}