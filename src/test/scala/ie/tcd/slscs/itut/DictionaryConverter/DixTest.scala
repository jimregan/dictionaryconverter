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
import ie.tcd.slscs.itut.DictionaryConverter.EID._
import ie.tcd.slscs.itut.DictionaryConverter.dix.{Dix, E, I, Txt}

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

  "e" should "read <e> element" in {
    val xml = <e><i>txt</i></e>
    val prs = E(List(I(List(Txt("txt")))))
    assert(prs == Dix.nodetoe(xml))
  }

  "whole" should "read whole dictionary" in {
    val dix = Dix.load(simple_dix)
  }
}
