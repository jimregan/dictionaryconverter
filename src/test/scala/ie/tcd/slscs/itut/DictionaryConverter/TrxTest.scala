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
    val exp = DefAttrElement("nbr", List[AttrItemElement](AttrItemElement("sg"), AttrItemElement("pl"), AttrItemElement("sp"), AttrItemElement("ND")))
    val out = nodeToDefAttr(in)
    assert(exp == out)
  }

  "readAttrItem" should "read an attr-item node" in {
    val in = <attr-item tags="sg"/>
    val exp = AttrItemElement("sg")
    val out = nodeToAttrItem(in)
    assert(exp == out)
  }

  "callMacro" should "read a call-macro node" in {
    val in = <call-macro n="firstWord">
      <with-param pos="1"/>
    </call-macro>
    val exp = CallMacroElement("firstWord", List[WithParamElement](WithParamElement("1")))
    val out = nodeToCallMacro(in)
    assert(exp == out)
  }

  "action" should "read an action node" in {
    val in = <action c="test">
      <call-macro n="firstWord">
        <with-param pos="1"/>
      </call-macro>
      <out>
        <chunk name="ant" case="caseFirstWord">
          <tags>
            <tag><lit-tag v="SN"/></tag>
            <tag><clip pos="2" side="sl" part="gen"/></tag>
            <tag><clip pos="2" side="tl" part="nbr"/></tag>
          </tags>
          <lu>
            <clip pos="2" side="tl" part="whole"/>
          </lu>
        </chunk>
      </out>
    </action>
    val cm = CallMacroElement("firstWord", List[WithParamElement](WithParamElement("1")))

    val exp = ActionElement("test", List[SentenceElement](cm))
    val out = nodeToAction(in)
    assert(exp == out)
  }
}
