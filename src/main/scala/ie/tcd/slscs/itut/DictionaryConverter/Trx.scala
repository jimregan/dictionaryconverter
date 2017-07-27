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
  def toXMLString: String = toXML.toString + "\n"
}
trait Indentable extends TransferElement {
  val indent: String
  val myindent: String = indent + "  "
  override def toXMLString: String = myindent + toXML.toString + "\n"
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
  def toXML: Node = <FIXME/>
}
case class TrxProc(kind: String, defcats: Map[String, List[CatItem]],
                   defattrs: Map[String, List[String]],
                   vars: Map[String, String],
                   lists: Map[String, List[String]],
                   macros: Map[String, List[Action]], rules: List[Rule]) {
  val variables = collection.mutable.Map.empty[String, String] ++ vars
  val validVariables: List[String] = vars.keys.toList
  def getVar(s: String): Option[String] = {
    if (validVariables.contains(s)) {
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
    val da = t.defattrs.map{e => (e.n, e.l.map{_.tags})}.toMap
    val dv = t.vars.map{e => (e.name, e.value)}.toMap
    val dl = t.lists.map{e => (e.name, e.items.map{_.value})}.toMap
    val dm = t.macros.map{e => (e.name, e.actions)}.toMap
    TrxProc(t.kind, dc, da, dv, dl, dm, t.rules)
  }
}
case class CatItem(tags: String, lemma: String = null) extends TransferElement {
  def toXML: Node = <cat-item lemma={lemma} tags={tags} />
  override def toXMLString: String = "      " + toXML.toString
}
case class DefCat(n: String, l: List[CatItem]) extends TransferElement {
  def toXML: Node = {
    <def-cat n={n}>
    { l.map { c => c.toXML } }
    </def-cat>
  }
  override def toXMLString: String = "    <def-cat n=\"$n\">\n" +
    l.map{_.toXMLString}.mkString("\n") +
    "    </def-cat>\n"
}
case class AttrItem(tags: String) extends TransferElement {
  def toXML: Node = <attr-item tags={tags} />
  override def toXMLString: String = "      " + toXML.toString
}
case class AttrCat(n: String, l: List[AttrItem]) extends TransferElement {
  def toXML: Node = {
    <def-attr n={n}>
    { l.map { c => c.toXML } }
    </def-attr>
  }
  override def toXMLString: String = "    <def-attr n=\"$n\">\n" +
    l.map{_.toXMLString}.mkString("\n") +
    "    </def-attr>\n"
}
case class DefVar(name: String, value: String = null) extends TransferElement {
  def toXML: Node = <def-var n={name} v={value} />
  override def toXMLString: String = "    " + toXML.toString
}
case class Rule(comment: String, pattern: List[PatternItem], action: Action)
case class PatternItem(n: String)
case class Action(c: List[SentenceElement]) extends TransferElement {
  def toXML: Node = <action>
    { c.map{_.toXML} }
  </action>
}
trait ConditionElement extends TransferElement
trait ContainerElement extends TransferElement
trait SentenceElement extends TransferElement
trait ValueElement extends TransferElement
trait StringValueElement extends ValueElement
case class VarElement(name: String) extends ContainerElement with StringValueElement {
  def toXML: Node = <var n={name}/>
}
case class DefMacro(name: String, numparams: String, comment: String,
                    actions: List[Action]) extends TransferElement {
  def toXML: Node = <def-macro n={name} npar={numparams} c={comment}>
    { actions.map{_.toXML} }
  </def-macro>
}
case class DefList(name: String, items: List[ListItem]) extends TransferElement {
  override def toXML: Node = <def-list n={name}>
    { items.map{_.toXML} }
  </def-list>
  override def toXMLString: String = "    <def-list n=\"$name\">\n" +
    items.map{_.toXMLString}.mkString("\n") +
    "    </def-list>\n"
}
case class ListItem(value: String) extends TransferElement {
  def toXML: Node = <list-item v={value}/>
  override def toXMLString: String = "      " + toXML.toString
}
case class BeginsWithListElem(v: ValueElement, caseless: Boolean = false, l: List[ListItem]) extends ConditionElement {
  def toXML: Node = <FIXME/>
}
trait OutputElement extends TransferElement
case class BElement(pos: String) extends OutputElement {
  def toXML = <b pos={pos}/>
}

case class ChunkElement(children: List[ValueElement]) extends Indentable {
  def toXML = <chunk>{children.map{_.toXML}}</chunk>
}

object Trx {
  import scala.xml._
  def nodeToCatItem(n: Node): CatItem = {
    val lemmaRaw = (n \ "@lemma").text
    val lemma = if (lemmaRaw != "") lemmaRaw else null
    val tags = (n \ "@tags").text
    CatItem(tags, lemma)
  }
  def nodeToDefCat(n: Node): DefCat = {
    val name = (n \ "@n").text
    val children = (n \ "cat-item").toList.map{nodeToCatItem}
    DefCat(name, children)
  }
  def nodeToVar(n: Node): VarElement = {
    val name = (n \ "@n").text
    VarElement(name)
  }
  def nodeToDefVar(n: Node): DefVar = {
    val name = (n \ "@n").text
    val value = (n \ "@v").text
    DefVar(name, value)
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

  def mkAttrCat(s: String, l: List[String]): AttrCat = {
    AttrCat(s, l.map{e => AttrItem(e)})
  }

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
    val defcats = (xml \ "section-def-cats" \ "def-cat").map{nodeToDefCat}.toList
    val defattrs = (xml \ "section-def-attrs" \ "def-attr").map{nodeToDefAttr}.toList
    val defvars = (xml \ "section-def-vars" \ "def-var").map{nodeToDefVar}.toList
    val deflists = (xml \ "section-def-lists" \ "def-list").map{nodeToDefList}.toList
    TopLevel(kind, defcats, defattrs, defvars, deflists, List.empty[DefMacro], List.empty[Rule])
  }
}
