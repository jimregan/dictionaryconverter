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
package ie.tcd.slscs.itut.DictionaryConverter.dix

import org.scalatest.FlatSpec

class DixTest extends FlatSpec {
  val simple_dix = <dictionary>
    <alphabet>abcdefghijklmonpqrstuvwxyz</alphabet>
    <sdefs>
      <sdef n="one"/>
      <sdef n="two" c="comment" />
    </sdefs>
    <pardefs>
      <pardef n="first">
        <e><i>foo</i></e>
        <e><p><l/><r><s n="one"/></r></p></e>
      </pardef>
      <pardef n="second">
        <e><p><l>foo</l><r>bar<s n="one"/></r></p></e>
      </pardef>
    </pardefs>
    <section id="main" type="standard">
      <e><p><l>foo</l><r>bar<s n="one"/></r></p></e>
      <e><p><l>bar</l><r>barbar<s n="one"/></r></p><par n="first"/></e>
      <e><p><l>foo</l><r>bars<s n="one"/></r></p><par n="second"/></e>
    </section>
  </dictionary>

  val simple_dix_nopardefs = <dictionary>
    <alphabet>abcdefghijklmonpqrstuvwxyz</alphabet>
    <sdefs>
      <sdef n="one"/>
      <sdef n="two" c="comment" />
    </sdefs>
    <section id="main" type="standard">
      <e><p><l>foo</l><r>bar<s n="one"/></r></p></e>
      <e><p><l>bar</l><r>barbar<s n="one"/></r></p><par n="first"/></e>
      <e><p><l>foo</l><r>bars<s n="one"/></r></p><par n="second"/></e>
    </section>
  </dictionary>

  val simple_dix_twosections = <dictionary>
    <alphabet>abcdefghijklmonpqrstuvwxyz</alphabet>
    <sdefs>
      <sdef n="one"/>
      <sdef n="two" c="comment" />
    </sdefs>
    <section id="main" type="standard">
      <e><p><l>foo</l><r>bar<s n="one"/></r></p></e>
      <e><p><l>bar</l><r>barbar<s n="one"/></r></p><par n="first"/></e>
      <e><p><l>foo</l><r>bars<s n="one"/></r></p><par n="second"/></e>
    </section>
    <section id="other" type="standard">
      <e><p><l>foo</l><r>bar<s n="one"/></r></p></e>
    </section>
  </dictionary>

  "e" should "read <e> element" in {
    val xml = <e>
      <i>txt</i>
    </e>
    val prs = E(List(I(List(Txt("txt")))))
    assert(prs == Dix.nodetoe(xml))
  }

  "pWithMWRules" should "read <p> with mwrule attributes" in {
    val in = <p><l mwrule="foo">a</l><r mwrule="baz">bar</r></p>
    val out = Dix.nodetocontainer(in)
    val exp = P(L(List[TextLike](Txt("a")), "foo"), R(List[TextLike](Txt("bar")), "baz"))
    assert(out == exp)
  }

  "whole" should "read whole dictionary" in {
    val dix = Dix.load(simple_dix)
    assert(dix.alphabet.length == 26)
    assert(dix.sdefs.size == 2)
    assert(dix.sdefs(0).n == "one")
    assert(dix.pardefs.size == 2)
    assert(dix.pardefs(0).name == "first")
    assert(dix.pardefs(0).entries.size == 2)
    assert(dix.sections.size == 1)
    assert(dix.sections(0).name == "main")
    assert(dix.sections(0).entries.size == 3)
    assert(dix.sections(0).entries(1).children.size == 2)
  }

  "wholeNoPardefs" should "read whole dictionary" in {
    val dix = Dix.load(simple_dix_nopardefs)
    assert(dix.alphabet.length == 26)
    assert(dix.sdefs.size == 2)
    assert(dix.sdefs(0).n == "one")
    assert(dix.pardefs.size == 0)
    assert(dix.sections.size == 1)
    assert(dix.sections(0).name == "main")
    assert(dix.sections(0).entries.size == 3)
    assert(dix.sections(0).entries(1).children.size == 2)
  }

  "wholeTwoSections" should "read whole dictionary" in {
    val dix = Dix.load(simple_dix_twosections)
    assert(dix.alphabet.length == 26)
    assert(dix.sdefs.size == 2)
    assert(dix.sdefs(0).n == "one")
    assert(dix.pardefs.size == 0)
    assert(dix.sections.size == 2)
    assert(dix.sections(0).name == "main")
    assert(dix.sections(0).entries.size == 3)
    assert(dix.sections(0).entries(1).children.size == 2)
    assert(dix.sections(1).name == "other")
    assert(dix.sections(1).entries.size == 1)
    assert(dix.sections(1).entries(0).children.size == 1)
  }
}
