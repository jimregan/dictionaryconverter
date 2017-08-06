/*
 *  The MIT License (MIT)
 *
 *  Copyright © 2017 Trinity College, Dublin
 *  Irish Speech and Language Technology Research Centre
 *  Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 *  An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package ie.tcd.slscs.itut.DictionaryConverter.lexical

import org.scalatest.FlatSpec
import scala.xml._

class LexRuleTest extends FlatSpec {
  val cg_doublexml = <chargroup name="double" repeated="no">bdgtnmp</chargroup>
  val cg_vowelxml = <chargroup name="vowel" repeated="no">aeiou</chargroup>
  val cg_nonvowelxml = <chargroup name="nonvowel" repeated="no" negates="vowel" />
  val cg_double = CharGroup("double", "bdgtnmp", false)
  val cg_vowel = CharGroup("vowel", "aeiou", false)
  val cg_nonvowel = NegatedCharGroupRef("nonvowel", "vowel", false)

  "nodeToGrouping" should "test XML conversion" in {
    val outone = LexRule.nodeToGrouping(cg_doublexml)
    val outtwo = LexRule.nodeToGrouping(cg_vowelxml)
    val outthree = LexRule.nodeToGrouping(cg_nonvowelxml)
    assert(cg_double == outone)
    assert(cg_vowel == outtwo)
    assert(cg_nonvowel == outthree)
  }
  "testNegatedCharGroupConversion" should "convert a reference to a chargroup" in {
    val map = Map[String, Grouping]("vowel" -> cg_vowel)
    val exp = NegatedCharGroup("nonvowel", cg_vowel, false)
    val out = LexRule.negatedCharGroupRefToNegatedCharGroup(cg_nonvowel, map)
    assert(exp == out)
  }

}
