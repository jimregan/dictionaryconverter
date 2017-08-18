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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PatternContainer extends RulePattern {
    List<RulePattern> patterns;
    public PatternContainer() {
        this.patterns = new ArrayList<RulePattern>();
    }
    public PatternContainer(List<RulePattern> patterns) {
        this.patterns = patterns;
    }
    public String getPattern() {
        Iterator<RulePattern> it = patterns.iterator();
        StringBuilder sb = new StringBuilder();
        if(it.hasNext()) {
            sb.append(it.next().getPattern());
        }
        while(it.hasNext()) {
            sb.append(" *");
            sb.append(it.next().getPattern());
        }
        return sb.toString();
    }
    public static class Builder {
        private List<RulePattern> patterns;
        public Builder() {
        this.patterns = new ArrayList<RulePattern>();
        }
        public Builder addPattern(RulePattern pat) {
            this.patterns.add(pat);
            return this;
        }
        public PatternContainer build() {
            return new PatternContainer(this.patterns);
        }
    }

}
