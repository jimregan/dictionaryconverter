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

package ie.tcd.slscs.itut.duckegg;

import java.lang.Integer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Currency {
    public static final Map<String, CurrencyUnit> SYMBOL_MAP;
    static {
        Map<String, CurrencyUnit> tmpsym = new HashMap<String, CurrencyUnit>();
        tmpsym.put("€", CurrencyUnit.Euro);
        tmpsym.put("£", CurrencyUnit.Pound);
        tmpsym.put("$", CurrencyUnit.Dollar);
        SYMBOL_MAP = Collections.unmodifiableMap(tmpsym);
    }
    CurrencyUnit unit;
    int amount;
    int decimal;

    public Currency(String unit, String amount, String decimal) {
        this.unit = SYMBOL_MAP.get(unit);
        this.amount = Integer.parseInt(amount);
        this.decimal = Integer.parseInt(decimal);
    }
    public Currency(String amount, String decimal) {
        this.amount = Integer.parseInt(amount);
        this.decimal = Integer.parseInt(decimal);
    }
    public Currency(int amount, int decimal) {
        this.amount = amount;
        this.decimal = decimal;
    }
    public CurrencyUnit getUnit() {
        return unit;
    }
    public void setUnit(CurrencyUnit unit) {
        this.unit = unit;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getDecimal() {
        return decimal;
    }
    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }
    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(this == o) {
            return true;
        }
        if(!(o instanceof Currency)) {
            return false;
        }
        final Currency c = (Currency) o;
        if(c.getUnit() != getUnit()) {
            return false;
        }
        if(c.getAmount() != getAmount()) {
            return false;
        }
        if(c.getDecimal() != getDecimal()) {
            return false;
        }
        return true;
    }

}
