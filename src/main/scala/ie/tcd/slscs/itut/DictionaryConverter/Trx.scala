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
case class Rule(comment: String, pattern: List[PatternItem], action: ActionElement)
case class PatternItem(n: String)
case class ActionElement(c: String, children: List[SentenceElement]) extends Indentable {
  def toXML: Node = <action c={c}>
    { children.map{_.toXML} }
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
case class ChunkElement(name: String, namefrom: String, ccase: String, c: String,
                        tags: Option[TagsElementType], children: List[ValueElement]) extends OutElementType {
  def toXML: Node = <lu>{children.map{_.toXML}}</lu>
}
case class TagsElementType(children: List[TagElement]) extends Indentable {
  def toXML: Node = <tags>{children.map{_.toXML}}</tags>
}
case class TagElement(child: ValueElement) extends Indentable {
  def toXML: Node = <tag>{child.toXML}</tag>
}
case class WithParamElement(pos: String) extends Indentable {
  def toXML: Node = <with-param pos={pos}/>
}
case class CallMacroElement(name: String, params: List[WithParamElement]) extends SentenceElement {
  def toXML: Node = <call-macro n={name}>{params.map{_.toXML}}</call-macro>
}
case class ClipElement(pos: String, side: String, part: String, queue: String, linkto: String, c: String) extends ContainerElement with StringValueElement {
  def toXML: Node = <clip pos={pos} side={side} part={part} queue={queue} link-to={linkto} c={c} />
}
case class TestElement(comment: String, cond: ConditionElement) extends Indentable {
  def toXML: Node = <test c={comment}>{cond.toXML}</test>
}
case class RejectCurrentRuleElement(shifting: Boolean = true) extends SentenceElement {
  private val shifting_text: String = if(shifting) "yes" else "no"

  def toXML: Node = <reject-current-rule shifting={shifting_text}/>
}

case class DefMacro(name: String, numparams: String, comment: String,
                    actions: List[ActionElement]) extends TransferElement {
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
  val clstring = if(caseless) "yes" else "no"
  def toXML: Node = <begins-with-list caseless={clstring}>{v.toXML}{l.toXML}</begins-with-list>
}
case class BeginsWithElement(l: ValueElement, r: ValueElement, caseless: Boolean = false) extends ConditionElement {
  val clstring = if(caseless) "yes" else "no"
  def toXML: Node = <begins-with caseless={clstring}>{l.toXML}{r.toXML}</begins-with>
}
case class ContainsSubstringElement(l: ValueElement, r: ValueElement, caseless: Boolean = false) extends ConditionElement {
  val clstring = if(caseless) "yes" else "no"
  def toXML: Node = <contains-substring caseless={clstring}>{l.toXML}{r.toXML}</contains-substring>
}
case class InElement(l: ValueElement, r: ListElement, caseless: Boolean = false) extends ConditionElement {
  val clstring = if(caseless) "yes" else "no"
  def toXML: Node = <in caseless={clstring}>{l.toXML}{r.toXML}</in>
}
case class EndsWithListElement(v: ValueElement, l: ListElement, caseless: Boolean = false) extends ConditionElement {
  val clstring = if(caseless) "yes" else "no"
  def toXML: Node = <begins-with-list caseless={clstring}>{v.toXML}{l.toXML}</begins-with-list>
}
case class EndsWithElement(l: ValueElement, r: ValueElement, caseless: Boolean = false) extends ConditionElement {
  val clstring = if(caseless) "yes" else "no"
  def toXML: Node = <ends-with caseless={clstring}>{l.toXML}{r.toXML}</ends-with>
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
  val clstring = if(caseless) "yes" else "no"
  def toXML: Node = <equal caseless={clstring}>{children.map{_.toXML}}</equal>
}
case class GetCaseFromElement(pos: String, child: StringValueElement) extends StringValueElement {
  def toXML: Node = <get-case-from pos={pos}>{child.toXML}</get-case-from>
}
case class CaseOfElement(pos: String, part: String) extends StringValueElement {
  def toXML: Node = <case-of pos={pos} part={part}/>
}
case class LetElement(c: ContainerElement, v: ValueElement) extends SentenceElement {
  def toXML: Node = <let>{c.toXML}{v.toXML}</let>
}
case class OutElement(c: String, children: List[OutElementType]) extends SentenceElement {
  def toXML: Node = <out c={c}>{children.map{_.toXML}}</out>
}
case class ChooseElement(c: String, when: List[WhenElement], other: Option[OtherwiseElement]) extends SentenceElement {
  def toXML: Node = <choose c={c}>{when.map{_.toXML}}{if (other != None) other.get.toXML}</choose>
}
case class WhenElement(c: String, test: TestElement, children: List[SentenceElement]) extends Indentable {
  def toXML: Node = <when c={c}>{test.toXML}{children.map{_.toXML}}</when>
}
case class OtherwiseElement(c: String, children: List[SentenceElement]) extends Indentable {
  def toXML: Node = <otherwise c={c}>{children.map{_.toXML}}</otherwise>
}
case class ModifyCaseElement(c: ContainerElement, s: StringValueElement) extends SentenceElement {
  def toXML: Node = <modify-case>{c.toXML}{s.toXML}</modify-case>
}
case class AppendElement(name: String, value: ValueElement) extends SentenceElement {
  def toXML: Node = <append n={name}>{value.toXML}</append>
}

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
  def pruneNodes(l: List[Node]): List[Node] = {
    def pruneinner(l: List[Node], acc: List[Node]): List[Node] = l match {
      case scala.xml.Text(_) :: xs => pruneinner(xs, acc)
      case scala.xml.Comment(_) :: xs => pruneinner(xs, acc)
      case scala.xml.ProcInstr(_, _) :: xs => pruneinner(xs, acc)
      case x :: xs => pruneinner(xs, acc :+ x)
      case nil => acc
    }
    pruneinner(l, List.empty[Node])
  }
  def pruneNodes(n: Node): List[Node] = pruneNodes(n.child.toList)

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
  def nodeToWithParam(n: Node): WithParamElement = WithParamElement((n \ "@pos").text)
  def nodeToCallMacro(n: Node): CallMacroElement = {
    val name = (n \ "@n").text
    val children = (n \ "with-param").map{nodeToWithParam}.toList
    CallMacroElement(name, children)
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
    val pruned = pruneNodes(n.child.toList)
    if (pruned.length != 1) {
      throw new Exception(incorrect("get-case-from"))
    }
    val childn = pruned.head
    val child = childn match {
      case <clip/> => nodeToClip(childn)
      case <lit/> => LitElement(getattrib(childn, "v"))
      case <var/> => VarElement(getattrib(childn, "n"))
      case _ => throw new Exception("Unrecognised element: " + childn.label)
    }
    GetCaseFromElement(pos, child)
  }
  def incorrect(name: String): String = "<{name}> contains incorrect number of elements"
  def nodeToTag(n: Node): TagElement = {
    val pruned = pruneNodes(n.child.toList)
    if(pruned.length != 1) {
      throw new Exception(incorrect("tag"))
    }
    TagElement(nodeToValue(pruned.head))
  }
  def nodeToChunk(n: Node): ChunkElement = {
    val name = getattrib(n, "name")
    val namefrom = getattrib(n, "namefrom")
    val ccase = getattrib(n, "case")
    val comment = getattrib(n, "c")
    val pruned = pruneNodes(n.child.toList)
    if (pruned.length != 1 || pruned.length != 2) {
      throw new Exception(incorrect("chunk"))
    }
    if(pruned.length == 2) {
      val tags = (n \ "tags" \ "tag").map{nodeToTag}.toList
      val values = pruned.tail.map{nodeToValue}.toList
      ChunkElement(name, namefrom, ccase, comment, Some(TagsElementType(tags)), values)
    } else {
      val value = nodeToValue(pruned.head)
      ChunkElement(name, namefrom, ccase, comment, None, List[ValueElement](value))
    }
  }
  def nodeToLU(n: Node) = LUElement(pruneNodes(n.child.toList).map {nodeToValue}.toList)
  def nodeToValue(n: Node): ValueElement = n match {
    case <b/> => BElement(getattrib(n, "pos"))
    case <clip/> => nodeToClip(n)
    case <lit/> => LitElement(getattrib(n, "v"))
    case <lit-tag/> => LitTagElement(getattrib(n, "v"))
    case <var/> => VarElement(getattrib(n, "n"))
    case <get-case-from>{_*}</get-case-from> => nodeToGetCaseFrom(n)
    case <case-of/> => CaseOfElement(getattrib(n, "pos"), getattrib(n, "part"))
    case <lu-count/> => LUCountElement()
    case <concat>{_*}</concat> => ConcatElement(pruneNodes(n.child.toList).map{nodeToValue}.toList)
    case <lu>{_*}</lu> => nodeToLU(n)
    case <mlu>{_*}</mlu> => MLUElement(pruneNodes(n.child.toList).map{nodeToLU}.toList)
    case <chunk>{_*}</chunk> => nodeToChunk(n)

    case _ => throw new Exception("Unrecognised element: " + n.label)
  }
  def nodeToOutType(n: Node): OutElementType = n match {
    case <b/> => BElement(getattrib(n, "pos"))
    case <var/> => VarElement(getattrib(n, "n"))
    case <lu>{_*}</lu> => nodeToLU(n)
    case <mlu>{_*}</mlu> => MLUElement(pruneNodes(n.child.toList).map{nodeToLU}.toList)
    case <chunk>{_*}</chunk> => nodeToChunk(n)

    case _ => throw new Exception("Unrecognised element: " + n.label)
  }
  def nodeToContainer(n: Node): ContainerElement = n match {
    case <var/> => VarElement(getattrib(n, "n"))
    case <clip/> => nodeToClip(n)

    case _ => throw new Exception("Unrecognised element: " + n.label)
  }
  def nodeToLet(n: Node): LetElement = {
    if (n.child.length != 2) {
      throw new Exception(incorrect("let"))
    }
    val pruned = pruneNodes(n.child.toList)
    LetElement(nodeToContainer(pruned(0)), nodeToValue(pruned(1)))
  }
  def nodeToOut(n: Node): OutElement = {
    val c = getattrib(n, "c")
    val pruned = pruneNodes(n.child.toList)
    val children = pruned.map{nodeToOutType}.toList
    OutElement(c, children)
  }
  def nodeToWhen(n: Node): WhenElement = {
    val c = getattrib(n, "c")
    val pruned = pruneNodes(n.child.toList)
    val test = nodeToTest(pruned.head)
    val actions = pruned.tail.map{nodeToSentence}.toList
    WhenElement(c, test, actions)
  }
  def nodeToOtherwise(n: Node): OtherwiseElement = {
    val c = getattrib(n, "c")
    val pruned = pruneNodes(n.child.toList)
    val actions = pruned.map{nodeToSentence}.toList
    OtherwiseElement(c, actions)
  }
  def nodeToTest(n: Node): TestElement = {
    val c = getattrib(n, "c")
    val pruned = pruneNodes(n.child.toList)
    if(pruned.length != 1) {
      throw new Exception(incorrect("test"))
    }
    val children = nodeToConditional(pruned.head)
    TestElement(c, children)
  }
  def nodeToChoose(n: Node): ChooseElement = {
    val c = getattrib(n, "c")
    val when = (n \ "when").map{nodeToWhen}.toList
    val othernode = (n \ "otherwise")
    if(othernode.length > 1) {
      throw new Exception("<choose> can only contain one <otherwise>")
    }
    val other = if(othernode.length == 1) Some(nodeToOtherwise(othernode.head)) else None
    ChooseElement(c, when, other)
  }
  def nodeToModifyCase(n: Node): ModifyCaseElement = {
    val pruned = pruneNodes(n.child.toList)
    if(pruned.length != 2) {
      throw new Exception(incorrect("modify-case"))
    }
    val cont = nodeToContainer(pruned(0))
    val value = nodeToStringValue(pruned(1))
    ModifyCaseElement(cont, value)
  }
  def nodeToAppend(n: Node): AppendElement = {
    val pruned = pruneNodes(n.child.toList)
    if(pruned.length != 1) {
      throw new Exception(incorrect("append"))
    }
    val name = getattrib(n, "n")
    val value = nodeToValue(pruned(0))
    AppendElement(name, value)
  }
  def nodeToAction(n: Node): ActionElement = {
    val comment = getattrib(n, "c")
    val pruned = pruneNodes(n.child.toList)
    val values = pruned.map{nodeToSentence}.toList
    ActionElement(comment, values)
  }
  def nodeToSentence(n: Node): SentenceElement = n match {
    case <let>{_*}</let> => nodeToLet(n)
    case <out>{_*}</out> => nodeToOut(n)
    case <choose>{_*}</choose> => nodeToChoose(n)
    case <modify-case>{_*}</modify-case> => nodeToModifyCase(n)
    case <call-macro>{_*}</call-macro> => nodeToCallMacro(n)
    case <append>{_*}</append> => nodeToAppend(n)
    case <reject-current-rule/> => {
      val shift: Boolean = getattrib(n, "shifting") == "yes"
      RejectCurrentRuleElement(shift)
    }

    case _ => throw new Exception("Unrecognised element: " + n.label)
  }
  def nodeToStringValue(n: Node): StringValueElement = n match {
    case <clip/> => nodeToClip(n)
    case <lit/> => LitElement(getattrib(n, "v"))
    case <var/> => VarElement(getattrib(n, "n"))
    case <get-case-from>{_*}</get-case-from> => nodeToGetCaseFrom(n)
    case <lu-count/> => LUCountElement()
    case <case-of/> => CaseOfElement(getattrib(n, "pos"), getattrib(n, "part"))
    case _ => throw new Exception("Unrecognised element: " + n.label)
  }
  def nodeToConditional(n: Node): ConditionElement = n match {
    case <and>{_*}</and> => AndElement(pruneNodes(n.child.toList).map{nodeToValue})
    case <or>{_*}</or> => OrElement(pruneNodes(n.child.toList).map{nodeToValue})
    case <not>{_*}</not> => NotElement(nodeToValue(pruneNodes(n.child.toList).head))
    case <equal>{_*}</equal> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      EqualElement(caseless, pruneNodes(n.child.toList).map{nodeToValue})
    }
    case <begins-with>{_*}</begins-with> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val pruned = pruneNodes(n.child.toList)
      val children = pruned.map{nodeToValue}.toList
      if(children.length != 2) {
        throw new Exception(incorrect("begins-with"))
      }
      BeginsWithElement(children(0), children(1), caseless)
    }
    case <begins-with-list>{_*}</begins-with-list> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val pruned = pruneNodes(n.child.toList)
      if(pruned.length != 2) {
        throw new Exception(incorrect("begins-with-list"))
      }
      val value = nodeToValue(pruned.head)
      val listelem = nodeToList(pruned(1))
      BeginsWithListElement(value, listelem, caseless)
    }
    case <ends-with>{_*}</ends-with> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val pruned = pruneNodes(n.child.toList)
      val children = pruned.map{nodeToValue}
      if(children.length != 2) {
        throw new Exception(incorrect("ends-with"))
      }
      EndsWithElement(children(0), children(1), caseless)
    }
    case <ends-with-list>{_*}</ends-with-list> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val pruned = pruneNodes(n.child.toList)
      if(pruned.length != 2) {
        throw new Exception(incorrect("ends-with-list"))
      }
      val value = nodeToValue(n.child.head)
      val listelem = nodeToList(n.child(1))
      EndsWithListElement(value, listelem, caseless)
    }
    case <contains-substring>{_*}</contains-substring> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val pruned = pruneNodes(n.child.toList)
      if(pruned.length != 2) {
        throw new Exception(incorrect("contains-substring"))
      }
      val left = nodeToValue(n.child(0))
      val right = nodeToValue(n.child(1))
      ContainsSubstringElement(left, right, caseless)
    }
    case <in>{_*}</in> => {
      val caseless: Boolean = n.attribute("caseless").get.text == "yes"
      val pruned = pruneNodes(n.child.toList)
      if(pruned.length != 2) {
        throw new Exception(incorrect("in"))
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
