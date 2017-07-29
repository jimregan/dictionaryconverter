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
  val indent_text: String = "  "
  override def toXMLString: String = toXML.toString
  def toXMLString(i: Int, newline: Boolean): String = {
    val nl = if(newline) "\n" else ""
    (indent_text * i) + toXMLString + nl
  }
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
trait ConditionElement extends Indentable
/** 'container' elements: var and clip */
trait ContainerElement extends ValueElement
/** 'sentence' elements: let, out, choose, modify-case, call-macro, append, and reject-current-rule */
trait SentenceElement extends TransferElement
/** 'value' elements: b, clip, lit, lit-tag, var, get-case-from, case-of, concat, lu, mlu, and chunk */
trait ValueElement extends Indentable
/** 'stringvalue' elements: clip, lit, var, get-case-from, lu-count, and case-of */
trait StringValueElement extends ValueElement
/** Elements that can be contained by <out>: mlu, lu, var, b, and chunk */
trait OutElementType extends ValueElement
/** Elements that can be contained by <get-case-from>: clip, lit, var */
trait CaseFromElementType extends StringValueElement
/** Elements that can be contained by <chunk>: mlu, lu, b, var */
trait ChunkElementType extends OutElementType
case class VarElement(name: String) extends ContainerElement with ChunkElementType with CaseFromElementType {
  def toXML: Node = <var n={name}/>
}
case class BElement(pos: String) extends ChunkElementType {
  def toXML: Node = <b pos={pos}/>
}
case class ConcatElement(children: List[ValueElement]) extends ValueElement {
  def toXML: Node = <concat>{children.map{_.toXML}}</concat>
}
case class MLUElement(children: List[LUElement]) extends ChunkElementType {
  def toXML: Node = <mlu>{children.map{_.toXML}}</mlu>
}
case class LUElement(children: List[ValueElement]) extends ChunkElementType {
  def toXML: Node = <lu>{children.map{_.toXML}}</lu>
}
case class LUCountElement() extends StringValueElement {
  def toXML: Node = <lu-count/>
}
case class ChunkElement(tags: TagsElementType, children: List[ValueElement]) extends OutElementType {
  def toXML: Node = <lu>{children.map{_.toXML}}</lu>
}
case class TagsElementType(children: List[TagElement]) extends Indentable {
  def toXML: Node = <tags>{children.map{_.toXML}}</tags>
}
case class TagElement(child: ValueElement) extends Indentable {
  def toXML: Node = <tag>{child.toXML}</tag>
}
case class WithParam(pos: String) extends Indentable {
  def toXML: Node = <with-param pos={pos}/>
}
case class CallMacro(name: String, params: List[WithParam]) extends Indentable {
  def toXML: Node = <call-macro n={name}>{params.map{_.toXML}}</call-macro>
}
case class ClipElement(pos: String, side: String, part: String, queue: String, linkto: String, c: String) extends ContainerElement with StringValueElement {
  def toXML: Node = <clip pos={pos} side={side} part={part} queue={queue} link-to={linkto} c={c} />
}
case class TestElement(cond: ConditionElement) extends Indentable {
  def toXML: Node = <test>{cond.toXML}</test>
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
case class BeginsWithListElement(v: ValueElement, l: ListElement, caseless: Boolean = false) extends ConditionElement {
  def toXML: Node = <begins-with-list>{v.toXML}{l.toXML}</begins-with-list>
}
case class BeginsWithElement(l: ValueElement, r: ValueElement, caseless: Boolean = false) extends ConditionElement {
  def toXML: Node = <begins-with>{l.toXML}{r.toXML}</begins-with>
}
case class ContainsSubstringElement(l: ValueElement, r: ValueElement, caseless: Boolean = false) extends ConditionElement {
  def toXML: Node = <contains-substring>{l.toXML}{r.toXML}</contains-substring>
}
case class InElement(l: ValueElement, r: ListElement, caseless: Boolean = false) extends ConditionElement {
  def toXML: Node = <in>{l.toXML}{r.toXML}</in>
}
case class EndsWithListElement(v: ValueElement, l: ListElement, caseless: Boolean = false) extends ConditionElement {
  def toXML: Node = <begins-with-list>{v.toXML}{l.toXML}</begins-with-list>
}
case class EndsWithElement(l: ValueElement, r: ValueElement, caseless: Boolean = false) extends ConditionElement {
  def toXML: Node = <ends-with>{l.toXML}{r.toXML}</ends-with>
}
case class NotElement(v: ValueElement) extends ConditionElement {
  def toXML: Node = <not>{v.toXML}</not>
}
case class AndElement(children: List[ValueElement]) extends ConditionElement {
  def toXML: Node = <and>{children.map{_.toXML}}</and>
}
case class OrElement(children: List[ValueElement]) extends ConditionElement {
  def toXML: Node = <or>{children.map{_.toXML}}</or>
}
case class ListElement(name: String) extends Indentable {
  def toXML: Node = <list n={name}/>
}
case class LitElement(value: String) extends StringValueElement {
  def toXML: Node = <lit v={value}/>
}
case class LitTagElement(value: String) extends StringValueElement {
  def toXML: Node = <lit-tag v={value}/>
}
case class EqualElement(caseless: Boolean, children: List[ValueElement]) extends ConditionElement {
  def toXML: Node = <equal caseless={caseless}>{children.map{_.toXML}}</equal>
}
case class GetCaseFromElement(pos: String, child: StringValueElement) extends StringValueElement {
  def toXML: Node = <get-case-from pos={pos}>{child.toXML}</get-case-from>
}

(pos, child)

object Trx {
  import scala.xml._
  private def indLevel(l: Int, t: String = "  ") = (t * l)
  private def nullify(s: String): String = if(s != "") s else null
  private def getattrib(n: Node, s: String, nullify: Boolean = true): String = {
    val attr = n.attribute(s).getOrElse(scala.xml.Text(""))
    if (nullify && attr.text != "") {
      attr.text
    } else {
      null
    }
  }

  def nodeToCatItem(n: Node): CatItem = {
    val lemmaRaw = (n \ "@lemma").text
    val lemma = nullify((n \ "@lemma").text)
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
    val value = getattrib(n, "value")
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
  def nodeToList(n: Node): ListElement = ListElement((n \ "@n").text)
  def nodeToLit(n: Node): LitElement = LitElement((n \ "@v").text)
  def nodeToLitTag(n: Node): LitTagElement = LitTagElement((n \ "@v").text)
  def nodeToWithParam(n: Node): WithParam = WithParam((n \ "@pos").text)
  def nodeToCallMacro(n: Node): CallMacro = {
    val name = (n \ "@n").text
    val children = (n \ "with-param").map{nodeToWithParam}.toList
    CallMacro(name, children)
  }
  def inlistornull(s: String, l: List[String]): String = if(l.contains(s)) s else null
  def nodeToClip(n: Node): ClipElement = {
    val validside = List[String]("sl", "tl")
    val pos = (n \ "@pos").text
    val side = inlistornull((n \ "@side").text, validside)
    val part = (n \ "@part").text
    val queue = nullify((n \ "@queue").text)
    val linkto = nullify((n \ "@link-to").text)
    val c = nullify((n \ "@c").text)
    ClipElement(pos, side, part, queue, linkto, c)
  }
  def nodeToGetCaseFrom(n: Node): GetCaseFromElement = {
    val pos = getattrib(n, "pos")
    if (n.child.length != 1) {
      throw new Exception("<get-case-from> can only contain one element")
    }
    val childn = n.child.head
    val child = childn match {
      case <clip/> => nodeToClip(childn)
      case <lit/> => LitElement(getattrib(childn, "v"))
      case <var/> => VarElement(getattrib(childn, "n"))
      case _ => throw new Exception("Unrecognised element: " + childn.label)
    }
    GetCaseFromElement(pos, child)
  }
  def nodeToValue(n: Node): ValueElement = n match {
    case <b/> => BElement(getattrib(n, "pos"))
    case <clip/> => nodeToClip(n)
    case <lit/> => LitElement(getattrib(n, "v"))
    case <lit-tag/> => LitTagElement(getattrib(n, "v"))
    case <var/> => VarElement(getattrib(n, "n"))
    case <get-case-from>{_*}</get-case-from> => nodeToGetCaseFrom(n)
    case <lu-count/> => LUCountElement()

    case _ => throw new Exception("Unrecognised element: " + n.label)
  }
  def nodeToStringValue(n: Node): StringValueElement = n match {
    case <clip/> => nodeToClip(n)
    case <lit/> => LitElement(getattrib(n, "v"))
    case <var/> => VarElement(getattrib(n, "n"))
    case <get-case-from>{_*}</get-case-from> => nodeToGetCaseFrom(n)
    case <lu-count/> => LUCountElement()

    case _ => throw new Exception("Unrecognised element: " + n.label)
  }
  def nodeToConditional(n: Node): ConditionElement = n match {
    case <and>{_*}</and> => AndElement(n.child.map{nodeToValue}.toList)
    case <or>{_*}</or> => OrElement(n.child.map{nodeToValue}.toList)
    case <not>{_*}</not> => NotElement(nodeToValue(n.child.head))
    case <equal>{_*}</equal> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      EqualElement(caseless, n.child.map{nodeToValue}.toList)
    }
    case <begins-with>{_*}</begins-with> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val children = n.child.map{nodeToValue}.toList
      if(children.length != 2) {
        throw new Exception("<begins-with> can only contain two elements")
      }
      BeginsWithElement(children(0), children(1), caseless)
    }
    case <begins-with-list>{_*}</begins-with-list> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      if(n.child.length != 2) {
        throw new Exception("<begins-with-list> can only contain two elements")
      }
      val value = nodeToValue(n.child.head)
      val listelem = nodeToList(n.child(1))
      BeginsWithListElement(value, listelem, caseless)
    }
    case <ends-with>{_*}</ends-with> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val children = n.child.map{nodeToValue}.toList
      if(children.length != 2) {
        throw new Exception("<ends-with> can only contain two elements")
      }
      EndsWithElement(children(0), children(1), caseless)
    }
    case <ends-with-list>{_*}</ends-with-list> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      if(n.child.length != 2) {
        throw new Exception("<ends-with-list> can only contain two elements")
      }
      val value = nodeToValue(n.child.head)
      val listelem = nodeToList(n.child(1))
      EndsWithListElement(value, listelem, caseless)
    }
    case <contains-substring>{_*}</contains-substring> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      if(n.child.length != 2) {
        throw new Exception("<contains-substring> can only contain two elements")
      }
      val left = nodeToValue(n.child(0))
      val right = nodeToValue(n.child(1))
      ContainsSubstringElement(value, listelem, caseless)
    }
    case <in>{_*}</in> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      if(n.child.length != 2) {
        throw new Exception("<in> can only contain two elements")
      }
      val value = nodeToValue(n.child.head)
      val listelem = nodeToList(n.child(1))
      InElement(value, listelem, caseless)
    }
    case _ => throw new Exception("Unrecognised element: " + n.label)
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
