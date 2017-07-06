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
import scala.xml._

class Dictionary(alphabet: String, sdefs: List[Sdef], pardefs: List[Pardef], sections: List[Section]) {
  def toXML = {
    <dictionary>
      <alphabet>{alphabet}</alphabet>
      <sdefs>
        { sdefs.map { sdef => sdef.toXML } }
      </sdefs>
      <pardefs>
        { pardefs.map { pardef => pardef.toXML } }
      </pardefs>
      { sections.map { section => section.toXML } }
    </dictionary>
  }
}

abstract class DixElement {
  def toXML
}

case class Sdef(n: String, c: String = null) {
  def toXML = <sdef n={n} c={c} />
}

trait TextLike extends DixElement
case class B extends TextLike {
  def toXML = <b/>
}
case class Txt(s: String) extends TextLike {
  def toXML = new scala.xml.Text(s)
}
case class S(n: String) extends TextLike {
  def toXML = <s n={n}/>
}

abstract class EntryContainer(name: String, entries: List[E])
case class Pardef(name: String, comment: String = null, entries: List[E]) extends EntryContainer(name, entries) {
  def toXML = {
    <pardef name={name} c={comment}>
      { entries.map{e => e.toXML} }
    </pardef>
  }
}
case class Section(name: String, stype: String, entries: List[E]) extends EntryContainer(name, entries) {
  def toXML = {
    <section id={name} type={stype}>
      { entries.map{e => e.toXML} }
    </section>
  }
}

case class E(children: List[Parts], lm: String = null) {
  def toXML = {
    <e>{ children.map{c => c.toXML} }</e>
  }
}

abstract class Parts() extends DixElement

abstract class TextLikeContainer(content: List[TextLike]) extends DixElement
case class L(content: List[TextLike]) extends TextLikeContainer(content) {
  def toXML = <l>{ content.map{c => c.toXML} }</l>
}
case class R(content: List[TextLike]) extends TextLikeContainer(content) {
  def toXML = <r>{ content.map{c => c.toXML} }</r>
}
case class G(content: List[TextLike]) extends TextLikeContainer(content) with TextLike {
  def toXML = <g>{ content.map{c => c.toXML} }</g>
}
case class Par(name: String, sa: String = null, prm: String = null) extends TextLikeContainer(List[TextLike]()) {
  def toXML = <par n={name} sa={sa} prm={prm} />
}
case class RE(regex: String) extends TextLikeContainer(List[TextLike]()) {
  def toXML = <re>{regex}</re>
}
case class I(content: List[TextLike]) extends TextLikeContainer(content) {
  def toXML = <i>{ content.map{c => c.toXML} }</i>
}
case class P(l: L, r: R) extends TextLikeContainer(List[TextLike]()) {
  def toXML = <p><l>{l.toXML}</l><r>{r.toXML}</r></p>
}

object Dictionary {
  import scala.xml.XML
  def nodetocontent(node: Node): TextLike = node match {
    case scala.xml.Text(t) => Txt(t)
    case <b/> => B()
    case s @ <s/> => S(s.attribute("n").get(0).text)
    case _ => throw new Exception("Error reading sdef")
  }
  def nodetocontainer(node: Node): TextLikeContainer = node match {
    case <i>{cnt @ _*}</i> => I(cnt.toList.map{nodetocontent})
    case <p><l>{l @ _*}</l><r><g>{g @ _*}</g></r></p> => P(L(l.toList.map{nodetocontent}), R(List[TextLike](G(g.toList.map{nodetocontent}))))
    case <p><l>{l @ _*}</l><r>{r @ _*}</r></p> => P(L(l.toList.map{nodetocontent}), R(r.toList.map{nodetocontent}))
    case par @ <par/> => {
      val satxt = par.attribute("sa").get(0).text
      val sa = if (satxt != "") satxt else null
      val prmtxt = par.attribute("prm").get(0).text
      val prm = if (prmtxt != "") prmtxt else null
      val name = par.attribute("n").get(0).text
      Par(name, sa, prm)
    }
    case <re>{re}</re> => RE(re.text)
    case <g>{foo @ _*}</g> => throw new Exception("Error reading <g>: cannot appear outside of <r>")
    case <l>{foo @ _*}</l> => throw new Exception("Error reading <l>: cannot appear outside of <p>")
    case <r>{foo @ _*}</r> => throw new Exception("Error reading <r>: cannot appear outside of <p>")
    case _ => throw new Exception("Error reading e")
  }
  def nodetosdef(node: Node): Sdef = node match {
    case <sdef/> => {
      val name = node \ "@n"
      val comment = node \ "@c"
      val n = name.text
      val c = if (comment.text != "") comment.text else null
      Sdef(n, c)
    }
    case _ => throw new Exception("Error reading sdef")
  }
  def load(file: String) = {
    //def toplevel(l: List[Node]) = l match {
    val xml = XML.loadFile(file)
    val alph = (xml \ "alphabet")(0).text
    val sdefs = (xml \ "sdefs" \ "sdef").toList.map{nodetosdef}
    val pardefs = (xml \ "pardefs" \ "pardef").toList
  }
}
