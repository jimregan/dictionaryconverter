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

package ie.tcd.slscs.itut.DictionaryConverter

import ie.tcd.slscs.itut.ApertiumStream.SimpleToken
import ie.tcd.slscs.itut.DictionaryConverter.TrxProc.{PositionBlank, SimpleLU, StreamItem}
import org.scalatest.{FlatSpec, FunSuite}

import scala.collection.JavaConverters._

class TrxProcTest extends FlatSpec {

  "listSimpleToStream" should "convert a list of SimpleTokens to a stream with blanks" in {
    val st1 = new SimpleLU("foo", "", List[String]("n", "sg"))
    val st2 = new SimpleLU("bar", "", List[String]("n", "sg"))
    val exp: List[StreamItem] = List(SimpleLU("foo", "", List("n", "sg")), PositionBlank(1), SimpleLU("bar", "", List("n", "sg")))
    val out = TrxProc.listSimpleToStream(List(st1, st2))
    assert(exp == out)
  }
}
