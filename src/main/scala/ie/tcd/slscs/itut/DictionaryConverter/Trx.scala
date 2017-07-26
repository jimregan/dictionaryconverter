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

trait TransferElement {
  def toXML: scala.xml.Node
  def toXMLString = toXML.toString
}
trait Indentable {
  val indent: String
}
case class TopLevel(kind: String, defcats: List[DefCat],
                    defattrs: List[AttrCat], vars: List[DefVar],
                    lists: List[DefList], macros: List[DefMacro],
                    rules: List[Rule]) extends TransferElement {
  def getOpen: String = if(kind == "chunk") {
    "<transfer default=\"chunk\">"
  } else {
    "<" + kind + ">"
  }
  def getClose: String = if(kind == "chunk") {
    "</transfer>"
  } else {
    "</" + kind + ">"
  }
  def toXML = <FIXME/>
}
case class TrxProc(kind: String, defcats: Map[String, List[CatItem]],
                   defattrs: Map[String, List[AttrItem]],
                   vars: Map[String, String],
                   lists: Map[String, List[ListItem]],
                   macros: Map[String, List[Action]], rules: List[Rule]) {
  val variables = collection.mutable.Map.empty ++ vars
  val validvariables: List[String] = vars.map(_._1).toList
  def getVar(s: String): Option[String] = {
    if (validvariables.contains(s)) {
      variables.get(s)
    } else {
      None
    }
  }
  def setVar(s: String, v: String) {
    variables(s) = v
  }
}
object TrxProc {
  def fromTopLevel(t: TopLevel): TrxProc = {
    val dc = t.defcats.map{e => (e.n, e.l)}.toMap
    val da = t.defattrs.map{e => (e.n, e.l)}.toMap
    val dv = t.vars.map{e => (e.name, e.value)}.toMap
    val dl = t.lists.map{e => (e.name, e.items)}.toMap
    val dm = t.macros.map{e => (e.name, e.actions)}.toMap
    TrxProc(t.kind, dc, da, dv, dl, dm, t.rules)
  }
}
case class CatItem(tags: String, lemma: String = null) extends TransferElement {
  def toXML = <cat-item lemma={lemma} tags={tags} />
}
case class DefCat(n: String, l: List[CatItem]) extends TransferElement {
  def toXML = {
    <def-cat n={n}>
    { l.map { c => c.toXML } }
    </def-cat>
  }
}
case class AttrItem(tags: String) extends TransferElement {
  def toXML = <attr-item tags={tags} />
}
case class AttrCat(n: String, l: List[AttrItem]) extends TransferElement {
  def toXML = {
    <def-attr n={n}>
    { l.map { c => c.toXML } }
    </def-attr>
  }
}
case class DefVar(name: String, value: String = null) extends TransferElement {
  def toXML = <def-var n={name} v={value} />
}
case class Rule(comment: String, pattern: List[PatternItem], action: Action)
case class PatternItem(n: String)
case class Action(c: List[Sentence]) extends TransferElement {
  def toXML = <action>
    { c.map{_.toXML} }
  </action>
}
trait Condition extends TransferElement {
  def evaluate: Boolean
}
trait Container extends TransferElement
trait Sentence extends TransferElement
trait Value extends TransferElement {
  var value: String
  def getValue: String = value
}
trait StringValue extends Value
case class Var(name: String) extends Container with StringValue {
  def toXML = <var n={name}/>
  var value = null
}
case class DefMacro(name: String, numparams: String, comment: String, actions: List[Action]) extends TransferElement {
  override def toXML: Node = <def-macro n={name} npar={numparams} c={comment}>
    { actions.map{_.toXML} }
  </def-macro>
}
case class DefList(name: String, items: List[ListItem]) extends TransferElement {
  override def toXML: Node = <def-list n={name}>
    { items.map{_.toXML} }
  </def-list>

  override def toXMLString: String = s"""
    <def-list n="$name">
${ items.map{_.toXMLString} }
    </def-list>
"""
}
case class ListItem(value: String) extends TransferElement {
  def toXML = <list-item v={value}/>
  override def toXMLString = "      " + toXML.toString() + "\n"
}
case class BeginsWithList(v: Value, caseless: Boolean = false, l: List[ListItem]) extends Condition {
  def evaluate: Boolean = {
    true
  }
  def toXML = <FIXME/>
}

object Trx {
  import scala.xml._
  def nodeToCatItem(n: Node): CatItem = {
    val lemmaraw = (n \ "@lemma").text
    val lemma = if (lemmaraw != "") lemmaraw else null
    val tags = (n \ "@tags").text
    CatItem(tags, lemma)
  }
  def nodeToDefCat(n: Node): DefCat = {
    val name = (n \ "@n").text
    val children = (n \ "cat-item").toList.map{nodeToCatItem}
    DefCat(name, children)
  }
  def nodeToVar(n: Node): Var = {
    val name = (n \ "@n").text
    Var(name)
  }
  def nodeToAttrItem(n: Node): AttrItem = {
    val tags = (n \ "@tags").text
    AttrItem(tags)
  }
  def nodeToDefAttr(n: Node): AttrCat = {
    val name = (n \ "@n").text
    val items = (n \ "attr-item").map{nodeToAttrItem}.toList
    AttrCat(name, items)
  }
  def nodeToDefList(n: Node): DefList = {
    val name = (n \ "@n").text
    val items = (n \ "list-item").map{nodeToListItem}.toList
    DefList(name, items)
  }
  def nodeToListItem(n: Node): ListItem = ListItem((n \ "@v").text)

  def load(file: String): TopLevel = {
    val xml = XML.loadFile(file)
    def rootElementToTrxType(e: Elem): String = {
      val valid = List("transfer", "interchunk", "postchunk")
      if (e.label == "transfer" && e.attribute("default").get.text == "chunk") {
        "chunk"
      } else if (valid.contains(e.label)) {
        e.label
      } else {
        throw new Exception("File " + file + " not recognised as TRX: top-level element is " + e.label)
      }
    }
    val kind = rootElementToTrxType(xml)
    val defcats = (xml \ "section-def-cats" \ "def-cat").map{nodeToDefCat}
    val defattrs = (xml \ "section-def-attrs" \ "def-attr").map{nodeToDefAttr}
    val defvars = (xml \ "section-def-vars" \ "def-var").map{nodeToVar}
    val deflists = (xml \ "section-def-lists" \ "def-list").map(nodeToDefList)
  }
}
