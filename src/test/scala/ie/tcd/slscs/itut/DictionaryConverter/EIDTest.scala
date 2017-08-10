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

class EIDTest extends FlatSpec {
  "readSimpleEntry" should "check simple entry" in {
    val in = <entry><title xml:space="preserve"><src>Fu</src>, <label>s.</label> = FOO.</title></entry>
    val exp = EqualsEntry("Fu", "s.", "FOO.")
    val out = readSimpleEntry(in)
    assert(exp == out)
  }

  "readSimpleEntry1" should "check simple entry" in {
    val in = <entry><title xml:space="preserve"><src>Foo</src>, <label>a. &amp; s.</label> <trg>Fú <noindex>(<label>m</label>)</noindex></trg>.</title></entry>
    val exp = SimpleNounEntry("Foo", "a. & s.", "Fú", "m", true)
    val out = readSimpleEntry(in)
    val ent = out match {
      case SimpleNounEntry(a, b, c, d, e) => SimpleNounEntry(a, b, c, d, e)
      case _ => throw new Exception("Could not cast")
    }
    assert(exp == out)
    assert(ent.getLabels.toList == List("a.", "s."))
  }

  "check empty sense" should "read whole XML entry with empty sense" in {
    val in = <entry><title xml:space="preserve"><src>settle upon</src>, <label>v.i.</label></title></entry>
    val exp = EmptyEntry("settle upon", "v.i.")
    val out = readSimpleEntry(in)
    assert(exp == out)
  }

  "read simple noun" should "read whole XML with simple noun entry" in {
    val in = <entry><title xml:space="preserve"><src>sewing</src>, <label>s.</label> <trg>Fuáil <label>f</label></trg>.</title></entry>
    val exp = SimpleNounEntry("sewing", "s.", "fuáil", "f")
    val out = readSimpleEntry(in)
    assert(exp == out)
  }

  "read complex trg" should "read trg with complex contents" in {
    val in = <trg>Crios <label>m</label> (<label>g.</label> creasa)</trg>
    val exp = MultiTrg(List(Txt("Crios "), Label("m"), Txt(" ("), Label("g."), Txt(" creasa)")))
    val out = breakdownComplexEntry(in)
    assert(exp == out)
  }
}
