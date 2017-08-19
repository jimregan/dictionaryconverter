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

package ie.tcd.slscs.itut.duckegg.bitext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OpusIrish {
    static final Map<String, Character> remaplatin1;
    static {
        Map<String, Character> latin1utf8 = new HashMap<String, Character>();
        latin1utf8.put("\u00c3\u00a1", 'á');
        latin1utf8.put("\u00c3\u00a9", 'é');
        latin1utf8.put("\u00c3\u00ad", 'í');
        latin1utf8.put("\u00c3\u00b3", 'ó');
        latin1utf8.put("\u00c3\u00ba", 'ú');
        latin1utf8.put("\u00c3\u0081", 'Á');
        latin1utf8.put("\u00c3\u0089", 'É');
        latin1utf8.put("\u00c3\u008d", 'Í');
        latin1utf8.put("\u00c3\u0093", 'Ó');
        latin1utf8.put("\u00c3\u009a", 'Ú');
        remaplatin1 = Collections.unmodifiableMap(latin1utf8);
    }
}
