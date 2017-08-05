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
package dix

import scala.xml._

case class Dix(alphabet: String, sdefs: List[Sdef], pardefs: List[Pardef], sections: List[Section]) {
  def toXML = {
<dictionary>
  <alphabet>{alphabet}</alphabet>
  <sdefs>
    { sdefs.map(_.toXML) }
  </sdefs>
  <pardefs>
    { pardefs.map(_.toXML) }
  </pardefs>
  { sections.map(_.toXML) }
</dictionary>
  }

  def toXMLString: String = s"""
<dictionary>
  <alphabet>$alphabet</alphabet>
  <sdefs>
${sdefs.map{e => "    " + e.toXMLString}.mkString("\n") }
  </sdefs>
  <pardefs>
${pardefs.map{_.toXMLString}.mkString }
  </pardefs>
${sections.map{_.toXMLString}.mkString }
</dictionary>
"""
}

abstract class DixElement {
  def toXML: scala.xml.Node
  def toXMLString: String = toXML.toString
}

case class Sdef(n: String, c: String = null) {
  def toXML = <sdef n={n} c={c} />
  def toXMLString = toXML.toString
}

trait TextLike extends DixElement {
  def asText: String
}
case class B() extends TextLike {
  def toXML = <b/>
  def asText: String = " "
}
case class J() extends TextLike {
  def toXML = <j/>
  def asText: String = "+"
}
case class Prm() extends TextLike {
  def toXML = <prm/>
  def asText = ""
}
case class Txt(s: String) extends TextLike {
  def toXML = new scala.xml.Text(s)
  def asText = s
}
case class Entity(s: String) extends TextLike {
  def toXML = new scala.xml.EntityRef(s)
  def asText = s
}
case class S(n: String) extends TextLike {
  def toXML = <s n={n}/>
  def asText = "<" + n + ">"
}

abstract class EntryContainer(name: String, entries: List[E])
case class Pardef(name: String, comment: String = null, entries: List[E]) extends EntryContainer(name, entries) {
  def toXML = {
    <pardef n={name} c={comment}>
      { entries.map{_.toXML} }
    </pardef>
  }
  def toXMLString: String = s"""
    <pardef n="$name"${if (comment != null) " c=\"$comment\"" else ""}>
${entries.map{e => "      " + e.toXMLString}.mkString("\n") }
    </pardef>
"""
}
case class Section(name: String, stype: String, entries: List[E]) extends EntryContainer(name, entries) {
  def toXML: scala.xml.Elem = {
    <section id={name} type={stype}>
      { entries.map{_.toXML} }
    </section>
  }

  def toXMLString: String = s"""
  <section id="$name" type="$stype">
${entries.map{e => "    " + e.toXMLString}.mkString("\n") }
  </section>
"""
}

case class E(children: List[TextLikeContainer], lm: String = null,
             r: String = null, a: String = null, c: String = null,
             i: Boolean = false, slr: String = null, srl: String = null,
             alt: String = null, v: String = null, vr: String = null,
             vl: String = null, trule: String = null, tokenise: String = null) {
  def toXML = {
    val itxt = if(i) "yes" else null
    <e lm={lm} r={r} a={a} c={c} i={itxt} slr={slr} srl={srl} v={v} vr={vr} vl={vl} alt={alt}>{ children.map{_.toXML} }</e>
  }
  def toXMLString = toXML.toString
}

abstract class Parts() extends DixElement

abstract class TextLikeContainer(content: List[TextLike]) extends DixElement {
  def getContent: List[TextLike] = content
}
case class L(content: List[TextLike]) extends TextLikeContainer(content) {
  def toXML = { <l>{ content.map{c => c.toXML} }</l> }
}
case class R(content: List[TextLike]) extends TextLikeContainer(content) {
  def toXML = { <r>{ content.map{c => c.toXML} }</r> }
}
case class G(content: List[TextLike]) extends TextLikeContainer(content) with TextLike {
  override def asText: String = "# " + content.map{_.asText}.mkString
  def toXML = { <g>{ content.map{c => c.toXML} }</g> }
}
case class Par(name: String, sa: String = null, prm: String = null, content: List[TextLike] = List.empty[TextLike]) extends TextLikeContainer(content) {
  def toXML = { <par n={name} sa={sa} prm={prm} /> }
}
case class RE(regex: String, content: List[TextLike]) extends TextLikeContainer(content) {
  def this(regex: String) = this(regex, List[TextLike](Txt(regex)))
  def toXML = { <re>{regex}</re> }
}
object RE {
  def apply(regex: String): RE = new RE(regex)
}
case class I(content: List[TextLike]) extends TextLikeContainer(content) {
  def toXML = { <i>{ content.map{c => c.toXML} }</i> }
}
case class P(l: L, r: R) extends TextLikeContainer(List[TextLike]()) {
  def toXML = { <p>{l.toXML}{r.toXML}</p> }
}

object Dix {
  import scala.xml.XML
  def nodetocontent(node: Node): TextLike = node match {
    case scala.xml.Text(t) => Txt(t)
    case scala.xml.EntityRef(e) => Entity(e)
    case <b/> => B()
    case <j/> => J()
    case <prm/> => Prm()
    case <s/> => S(getattrib(node, "n"))
    case <g>{_*}</g> => G(node.child.map{nodetocontent}.toList)
    case _ => throw new Exception("Error reading content " + node.toString)
  }
  /**
   * Get attribute s from Node n
   */
  def getattrib(n: Node, s: String, nullify: Boolean = true): String = {
    val attr = n.attribute(s).getOrElse(scala.xml.Text(""))
    if (nullify && attr.text != "") {
      attr.text
    } else {
      null
    }
  }
  def pruneNodes(l: List[Node]): List[Node] = {
    def pruneinner(l: List[Node], acc: List[Node]): List[Node] = l match {
      case scala.xml.Text(t) :: xs => {
        if(t.trim != "") {
          pruneinner(xs, acc :+ scala.xml.Text(t))
        } else {
          pruneinner(xs, acc)
        }
      }
      case scala.xml.Comment(_) :: xs => pruneinner(xs, acc)
      case scala.xml.ProcInstr(_, _) :: xs => pruneinner(xs, acc)
      case x :: xs => pruneinner(xs, acc :+ x)
      case nil => acc
    }
    pruneinner(l, List.empty[Node])
  }
  def pruneNodes(n: Node): List[Node] = pruneNodes(n.child.toList)
  def nodetoe(e: Node): E = e match {
    case <e>{_*}</e> => {
      val lm = getattrib(e, "lm")
      val rtxt = getattrib(e, "r")
      val r = if (rtxt == "LR" || rtxt == "RL") rtxt else null
      val itxt = getattrib(e, "i")
      val i = if (itxt == "yes") true else false
      val c = getattrib(e, "c")
      val alt = getattrib(e, "alt")
      val a = getattrib(e, "a")
      val v = getattrib(e, "v")
      val vr = getattrib(e, "vr")
      val vl = getattrib(e, "vl")
      val slr = getattrib(e, "slr")
      val srl = getattrib(e, "srl")
      val trule = getattrib(e, "trule")
      val tokenise = getattrib(e, "tokenise")
      E(pruneNodes(e).map{nodetocontainer}, lm, r, a, c, i, slr, srl, alt, v, vr, vl, trule, tokenise)
    }
    case _ => throw new Exception("Expected <e>" + e.toString)
  }
  def nodetocontainer(node: Node): TextLikeContainer = node match {
    case <i>{cnt @ _*}</i> => I(cnt.toList.map{nodetocontent})
    case p @ Elem(ns, "p", attribs, scope, children @ _*) => {
      val tmp = Elem(ns, "p", attribs, scope, pruneNodes(children.toList) :_*)
      tmp match {
        case <p><l>{l @ _*}</l><r><g>{g @ _*}</g></r></p> => P(L(l.toList.map{nodetocontent}), R(List[TextLike](G(g.toList.map{nodetocontent}))))
        case <p><l>{l @ _*}</l><r>{r @ _*}</r></p> => P(L(l.toList.map{nodetocontent}), R(r.toList.map{nodetocontent}))
        case _ => throw new Exception("Error reading p: " + tmp.toString)
      }
    }
    case <par/> => {
      val sa = getattrib(node, "sa", true)
      val prm = getattrib(node, "prm", true)
      val name = getattrib(node, "n", true)
      Par(name, sa, prm)
    }
    case <re>{re}</re> => RE(re.text)
    case <g>{foo @ _*}</g> => throw new Exception("Error reading <g>: cannot appear outside of <r>")
    case <l>{foo @ _*}</l> => throw new Exception("Error reading <l>: cannot appear outside of <p>")
    case <r>{foo @ _*}</r> => throw new Exception("Error reading <r>: cannot appear outside of <p>")
    case _ => throw new Exception("Error reading e: " + node.toString)
  }
  def nodetopardef(node: Node): Pardef = node match {
    case <pardef>{_*}</pardef> => {
      val name = node \ "@n"
      val comment = node \ "@c"
      val n = name.text
      val c = if (comment.text != "") comment.text else null
      val children = pruneNodes(node.child.toList).map{nodetoe}
      Pardef(n, c, children)
    }
    case _ => throw new Exception("Error reading pardef")
  }
  def nodetosection(node: Node): Section = node match {
    case <section>{_*}</section> => {
      val id = (node \ "@id").text
      val kind = (node \ "@type").text
      if (id == "") throw new Exception("Attribute `id' cannot be missing")
      if (kind == "") throw new Exception("Attribute `type' cannot be missing")
      val children = (node \ "e").toList.map{nodetoe}
      if(children.isEmpty) throw new Exception("Failed to read section " + id)
      Section(id, kind, children)
    }
    case _ => throw new Exception("Expected <section>")
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
  def load(file: String): Dix = {
    val xml = XML.loadFile(file)
    load(xml)
  }
  def loadString(s: String): Dix = {
    val xml = XML.loadString(s)
    load(xml)
  }

  def load(xml: Elem) = {
    val alph = (xml \ "alphabet").head.text
    val sdefs = (xml \ "sdefs" \ "sdef").toList.map {nodetosdef}
    val pardefs = (xml \ "pardefs" \ "pardef").toList.map {nodetopardef}
    val sections = (xml \ "section").toList.map {nodetosection}
    Dix(alph, sdefs, pardefs, sections)
  }

  def save(file: String, d: Dix) {
    import java.io._
    val outfile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))
    outfile.write(d.toXMLString)
    outfile.close()
  }
}
// set tabstop=2
