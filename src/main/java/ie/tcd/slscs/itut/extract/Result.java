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

package ie.tcd.slscs.itut.extract;
import java.lang.Integer;
import java.lang.Double;

public class Result {
    ResultType type;
    int int_result;
    double double_result;
    String string_result;
    String raw;
    public Result(String raw) {
        this.raw = raw;
    }
    public Result(ResultType rt) {
        this.type = rt;
    }
    public String getRaw() {
        return this.raw;
    }
    public ResultType getType() {
        return type;
    }
    public void setResult(int i) {
        type = ResultType.INT;
        this.int_result = i;
    }
    public void setResult(double d) {
        type = ResultType.DOUBLE;
        this.double_result = d;
    }
    public void setResult(String s) {
        type = ResultType.STRING;
        this.string_result = s;
    }
    public int getInt() {
        return int_result;
    }
    public double getDouble() {
        return double_result;
    }
    public String getString() {
        if(this.type == ResultType.STRING) {
            return this.string_result;
        } else if(this.type == ResultType.INT) {
            return Integer.toString(this.int_result);
        } else if(this.type == ResultType.DOUBLE) {
            return Double.toString(this.double_result);
        } else {
            return null;
        }
    }
}
