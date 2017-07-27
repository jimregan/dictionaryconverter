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

/**
 * 'condition' elements: and, or, not, equal, begins-with, begins-with-list,
 * ends-with, ends-with-list, contains-substring, in
 */
trait ConditionElement extends TransferElement
/** 'container' elements: var and clip */
trait ContainerElement extends TransferElement
/** 'sentence' elements: let, out, choose, modify-case, call-macro, append, and reject-current-rule */
trait SentenceElement extends TransferElement
/** 'value' elements: b, clip, lit, lit-tag, var, get-case-from, case-of, concat, lu, mlu, and chunk */
trait ValueElement extends Indentable
/** 'stringvalue' elements: clip, lit, var, get-case-from, lu-count, and case-of */
trait StringValueElement extends ValueElement
/** Elements that can be contained by <out>: mlu, lu, var, b, and chunk */
trait OutElementType extends ValueElement
case class VarElement(name: String, indent: String) extends ContainerElement with OutElementType {
  def toXML: Node = <var n={name}/>
}
case class BElement(pos: String, indent: String) extends OutElementType {
  def toXML = <b pos={pos}/>
}
case class ChunkElement(children: List[ValueElement], indent: String) extends OutElementType {
  def toXML = <chunk>{children.map{_.toXML}}</chunk>
}
case class ConcatElement(children: List[ValueElement], indent: String) extends ValueElement {
  def toXML = <concat>{children.map{_.toXML}}</concat>
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
    VarElement(name, "")
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
  def nodeToWithParam(n: Node): WithParam = WithParamElement((n \ "@pos").text)
  def nodeToCallMacro(n: Node): CallMacro = {
    val name = (n \ "@n").text
    val children = (n \ "with-param").map{nodeToWithParam}.toList
    CallMacro(name, children)
  }

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
