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
    val in1 = <entry><a>S.a. </a><s>bocht</s><x>1</x>, <s>cleith</s><x>1</x> <n>1. </n><n>2. </n></entry>
    val exp1 = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("bocht", "1", "", ""), RefPiece("cleith", "1", "1", ""))), NElem("2."))
    val out1 = consumeSeeAlso("S.a.", breakdownComplexEntry(in1))
    assert(exp1 == out1)

    val in2 = <entry><a>S.a. </a><s>beag</s><x>1</x>, <s>dath</s><x>1</x> <n>2</n>.</entry>
    val exp2 = List[BaseXML](RefPieces("S.a.", List[RefPiece](RefPiece("beag", "1", "", ""), RefPiece("dath", "1", "2", ""))))
    val out2 = consumeSeeAlso("S.a.", breakdownComplexEntry(in2))
    assert(exp2 == out2)
  }
}
