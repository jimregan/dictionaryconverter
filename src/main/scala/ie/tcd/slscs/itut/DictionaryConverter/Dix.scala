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

abstract class Dictionary {
  val Alphabet: String
  val Sdefs: List[Sdef]
  val Pardefs: List[Pardef]
  val Sections: List[Section]
}

class Sdef(n: String, c: String = null) {
  def toXML = <sdef n={n} c={c} />
}

class I(c: List[TextLike]) {
//  def toXML = <i>{i}</i>
}

class P(l: List[TextLike], r: List[TextLike]) {
//  def toXML = <p><l>{l}
}

abstract class TextLike
class B extends TextLike {
  def toXML = <b/>
}
class Text(s: String) extends TextLike {
  def toXML = new scala.xml.Text(s)
}

abstract class EntryContainer(name: String, entries: List[E])
class Pardef(name: String, entries: List[E]) extends EntryContainer(name, entries)
class Section(name: String, `type`: String, entries: List[E]) extends EntryContainer(name, entries)

class E(children: List[Parts], lm: String = null)

abstract class Parts()
//class P(left: L, right: R) extends Parts()
//class I(content: String) extends Parts()
class RE(content: String) extends Parts()

abstract class TextLikeContainer(content: List[TextLike])
class L(content: List[TextLike]) extends TextLikeContainer(content)
class R(content: List[TextLike]) extends TextLikeContainer(content)
class G(content: List[TextLike]) extends TextLikeContainer(content)
