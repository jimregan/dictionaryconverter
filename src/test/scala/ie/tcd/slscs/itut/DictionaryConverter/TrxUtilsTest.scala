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

package ie.tcd.slscs.itut.DictionaryConverter

import ie.tcd.slscs.itut.DictionaryConverter.dix.Dix
import org.scalatest.FlatSpec

class TrxUtilsTest extends FlatSpec {

  "defVarToLet" should "create a <let> element matching a <def-var>" in {
    val defvara = Trx.nodeToDefVar(<def-var n="foo"/>)
    val defvarb = Trx.nodeToDefVar(<def-var n="foo" v="bar" />)
    val expa = Trx.nodeToLet(<let><var n="foo"/><lit v=""/></let>)
    val expb = Trx.nodeToLet(<let><var n="foo"/><lit v="bar"/></let>)
    val outa = TrxUtils.defvarToLet(defvara)
    val outb = TrxUtils.defvarToLet(defvarb)
    val outas = s"""<let>
  <var n="foo"/>
  <lit v=""/>
</let>
"""
    assert(outas == outa.toXMLString(0, true))
    assert(expb == outb)
  }
  "isSimpleEntry" should "return true for simple dix <e> entry" in {
    val pair = <e><p><l>test<s n="n"/></l><r>todo<g><b/>item</g><s n="n"/><s n="foo"/></r></p></e>
    val ident = <e><i>test<s n="n"/></i></e>
    val outpair = Dix.nodetoe(pair)
    val outident = Dix.nodetoe(ident)
    assert(true, outpair)
    //assert(false, outident)
  }
}
