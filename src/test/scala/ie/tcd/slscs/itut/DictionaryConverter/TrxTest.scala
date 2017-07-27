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

import org.scalatest.FlatSpec
import scala.xml._
import ie.tcd.slscs.itut.DictionaryConverter.Trx._

class TrxTest extends FlatSpec {
  "readDefAttr" should "read a def-attr node" in {
    val in = <def-attr n="nbr">
      <attr-item tags="sg"/>
      <attr-item tags="pl"/>
      <attr-item tags="sp"/>
      <attr-item tags="ND"/>
    </def-attr>
    val exp = AttrCat("nbr", List[AttrItem](AttrItem("sg"), AttrItem("pl"), AttrItem("sp"), AttrItem("ND")))
    val out = nodeToDefAttr(in)
    assert(exp == out)
  }

  "readAttrItem" should "read an attr-item node" in {
    val in = <attr-item tags="sg"/>
    val exp = AttrItem("sg")
    val out = nodeToAttrItem(in)
    assert(exp == out)
  }

  "callMacro" should "read a call-macro node" in {
    val in = <call-macro n="firstWord">
      <with-param pos="1"/>
    </call-macro>
    val exp = CallMacro("firstWord", List[WithParam](WithParam("1", "    ")), "  ")
    val out = nodeToCallMacro(in, "  ")
    assert(exp == out)
  }
}
