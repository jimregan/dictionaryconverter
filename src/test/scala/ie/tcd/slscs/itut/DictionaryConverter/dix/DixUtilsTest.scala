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

package ie.tcd.slscs.itut.DictionaryConverter.dix

import org.scalatest.FlatSpec

class DixUtilsTest extends FlatSpec {

  behavior of "DixUtilsTest"

  it should "isNotTag" in {
    val tag = S("foo")
    val txt = Txt("bar")
    assert(DixUtils.isNotTag(tag) == false)
    assert(DixUtils.isNotTag(txt) == true)
  }

  it should "isTag" in {
    val tag = S("foo")
    val txt = Txt("bar")
    assert(DixUtils.isTag(tag) == true)
    assert(DixUtils.isTag(txt) == false)
  }

  it should "makeSimpleBilEntry" in {
    val out = DixUtils.makeSimpleBilEntry("foo", "n.sg", "bar", "n.m.sg")
    val exp = E(List(P(L(List[TextLike](Txt("foo"), S("n"), S("sg"))), R(List[TextLike](Txt("bar"), S("n"), S("m"), S("sg"))))))
    assert(out == exp)
  }
  "isSimpleEntry p" should "return true for simple dix <e> entry with p" in {
    val pair = <e><p><l>test<s n="n"/></l><r>todo<g><b/>item</g><s n="n"/><s n="foo"/></r></p></e>
    val epair = Dix.nodetoe(pair)
    val outpair = DixUtils.isSimpleEntry(epair)
    assert(outpair == true)
  }

  "isSimpleEntry i" should "return false for simple dix <e> entry with i" in {
    val ident = <e><i>test<s n="n"/></i></e>
    val eident = Dix.nodetoe(ident)
    val outident = DixUtils.isSimpleEntry(eident)
    assert(outident == false)
  }

  "get text pieces" should "return text from entry" in {
    val testl = L(List[TextLike](Txt("word"), G(List[TextLike](B(), Txt("woo")))))
    val expl = "word# woo"
    val out = DixUtils.getTextPieces(testl)
    assert(expl == out)
  }

  "get tags" should "extract tags from entry piece" in {
    val testl = L(List[TextLike](Txt("word"), G(List[TextLike](B(), Txt("woo"))), S("n"), S("m")))
    val exp = Some(List[S](S("n"), S("m")))
    val out = DixUtils.getTags(testl)
    assert(exp == out)
  }

  "get simplebil" should "convert simple entry to SimpleBil" in {
    val inraw = <e><p><l>foo<s n="n"/><s n="sg"/></l><r>bar<b/>bar<s n="n"/></r></p></e>
    val in = Dix.nodetoe(inraw)
    val exp = SimpleBil("foo", "n.sg", "bar bar", "n")
    val out = DixUtils.getSimpleBilEntry(in)
    assert(exp == out)
  }

}
