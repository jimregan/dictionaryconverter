/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Trinity College, Dublin
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
import ie.tcd.slscs.itut.DictionaryConverter.FGB.FGB._

class StackSpec extends FlatSpec {
  "consumeSeeAlso" should "consume seealso elements" in {
    val in = <entry><s>bocht</s><x>1</x><n>2. </n></entry>
    val exp = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("bocht", "1", "2.", ""))))
    val out = consumeSeeAlso("S.a.", breakdownComplexEntry(in))
    assert(exp == out)
  }

  "consumeSeeAlso1" should "consume seealso elements" in {
    val in = <entry><s>bocht</s><x>1</x>, <s>cleith</s><x>1</x> <n>1. </n><n>2. </n></entry>
    val exp = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("bocht", "1", "", ""), RefPiece("cleith", "1", "1.", ""))), NElem("2."))
    val out = consumeSeeAlso("S.a.", breakdownComplexEntry(in))
    assert(exp == out)
  }

  "consumeSeeAlso2" should "consume seealso elements" in {
    val in = <entry><s>dath</s><x>1</x> <n>2</n>.</entry>
    val exp = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("dath", "1", "2", ""))))
    val out = consumeSeeAlso("S.a.", breakdownComplexEntry(in))
    assert(exp == out)
  }

  "consumeSeeAlso3" should "consume seealso elements" in {
    val in = <entry><s>beag</s><x>1</x>, <x>1</x> <n>2</n>.</entry>
    val exp = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("beag", "1", "", ""), RefPiece("beag", "1", "2", ""))))
    val out = consumeSeeAlso("S.a.", breakdownComplexEntry(in))
    assert(exp == out)
  }

  "consumeSeeAlso4" should "consume seealso elements" in {
    val in = <entry><s>beag</s>, <s>dath</s>.</entry>
    val exp = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("beag", "", "", ""), RefPiece("dath", "", "", ""))))
    val out = consumeSeeAlso("S.a.", breakdownComplexEntry(in))
    assert(exp == out)
  }

  "consumeSeeAlso5" should "consume seealso elements" in {
    val in = <entry><s>beag</s><x>1</x>, <s>dath</s><x>1</x> <n>2</n>.</entry>
    val exp = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("beag", "1", "", ""), RefPiece("dath", "1", "2", ""))))
    val out = consumeSeeAlso("S.a.", breakdownComplexEntry(in))
    assert(exp == out)
  }
}
