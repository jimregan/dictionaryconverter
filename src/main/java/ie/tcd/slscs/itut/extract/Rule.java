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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
    String name;
    List<RulePattern> patterns;
    Rule() {
        this.patterns = new ArrayList<RulePattern>();
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
    public List<MatchIndex> match(String s) {
        Pattern pat = Pattern.compile(getPattern());
        List<MatchIndex> matches = new ArrayList<MatchIndex>();
        Matcher m = pat.matcher(s);
        while(m.matches()) {
            matches.add(new MatchIndex(m.start(), m.end()));
        }
        return matches;
    }
    public String name() {
        return name;
    }
    public static abstract class Builder<T extends Rule> {
        private List<RulePattern> patterns;
        Builder() {
            this.patterns = new ArrayList<RulePattern>();
        }

        public Builder<T> addPattern(RulePattern pat) {
            this.patterns.add(pat);
            return this;
        }

        public abstract T build();
    }

    public static Builder<?> builder() {
        return new Builder<Rule>() {
            @Override
            public Rule build() {
                return new Rule(this);
            }
        };
    }
    protected Rule(Builder<?> builder) {
        this.patterns = builder.patterns;
    }


}
