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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
    public String name;
    public PatternContainer pattern;
    public List<Result> results = null;
    public Pattern pat = null;
    public Rule() {
        this.pattern = new PatternContainer();
        this.results = new ArrayList<Result>();
    }
    public String name() {
        return name;
    }
    public boolean matches(String s) throws Exception {
        Result res = new Result();
        if(this.pat == null) {
            pat = Pattern.compile(pattern.getPattern());
        }
        Matcher m = pat.matcher(s);
        boolean matched = false;
        while(m.find()) {
            matched = true;
            String portion = m.group(0);
            Matcher n = pat.matcher(portion);
            if(!n.matches()) {
                throw new Exception("Failed to match already matched string: " + portion);
            }
            res = new Result(portion);
            for(int i = 1; i <= pattern.patterns.size(); i++) {
                System.err.println(pattern.patterns.size());
                if(m.group(i) != null) {
                    System.err.println(i + " " + n.group(i) + " " + n.group(0));
                    res.addRawResult(m.group(i));
                } else {
                    System.err.println(pattern.getPattern() + " failed at " + i + " " + n.group(0));
                    res.addRawResult(null);
                }
                results.add(setResult(res));
            }
        }
        if(!matched) {
            results.add(new Result(ResultType.EMPTY));
        }
        return matched;
    }
    public Result setResult(Result res) throws Exception {
        return res;
    }
    public List<Result> getResults() {
        return results;
    }
    public String getPattern() {
        return pattern.getPattern();
    }
    public Regex getRegex() {
        return new Regex(getPattern());
    }
}
