/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 * An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
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
package ie.tcd.slscs.itut.DictionaryConverter

class LexRule {
  abstract class Grouping(name: String, repeated: Boolean) {
    def getName = name
    def getRegex: String
  }
  case class Groups(children: List[Grouping]) {
    lazy val mapinner: Map[String, Grouping] = children.foldLeft(Map[String, Grouping]()) { (m, c) => m + (c.getName -> c) }
    def getMap = mapinner
  }
  case class CharGroup(g: String, name: String, repeated: Boolean) extends Grouping(name, repeated) {
    private def getRegexInner(neg: Boolean): String = {
      val negs = if (neg) "^" else ""
      val rep = if (repeated) "+" else ""
      val out = "[" + negs + g + "]" + rep
      out
    }
    def getRegex: String = getRegexInner(false)
    def getNegatedRegex: String = getRegexInner(true)
  }
  case class Item(content: String)
  case class Group(name: String, repeated: Boolean, items: List[Item]) extends Grouping(name, repeated) {
    def getRegex: String = {
      val strings = items.map{i => i.content}
      val out = "(" + strings.sortWith(_.length > _.length).mkString("|") + ")"
      out
    }
  }
  case class WordRules(children: List[Rule])
  case class Rule(name: String, children: List[SubRule])
  case class SubRule(name: String, matcher: String, children: List[Entry])
  case class Entry(tags: String, text: String)
}
object LexRule {

}
