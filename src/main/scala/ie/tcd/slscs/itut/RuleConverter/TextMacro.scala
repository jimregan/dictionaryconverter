/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 * An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
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

package ie.tcd.slscs.itut.RuleConverter

import ie.tcd.slscs.itut.ApertiumTransfer.Text.{SimpleTextMacroAttr, SimpleTextMacro => JSTMacro, SimpleTextMacroEntry => JSTMEntry}

import scala.collection.JavaConverters._

object TextMacro {
  abstract class MacroAttr(pos: Int, appliesto: String)
  abstract class NotMacroAttr(pos: Int, appliesto: String) extends MacroAttr(pos, appliesto)
  case class LemmaMacroAttr(s: String, pos: Int, appliesto: String) extends MacroAttr(pos, appliesto)
  case class NotLemmaMacroAttr(s: String, pos: Int, appliesto: String) extends NotMacroAttr(pos, appliesto)
  case class ListMacroAttr(s: String, pos: Int, appliesto: String) extends MacroAttr(pos, appliesto)
  case class NotListMacroAttr(s: String, pos: Int, appliesto: String) extends NotMacroAttr(pos, appliesto)
  case class BeginListMacroAttr(s: String, pos: Int, appliesto: String) extends MacroAttr(pos, appliesto)
  case class NotBeginListMacroAttr(s: String, pos: Int, appliesto: String) extends NotMacroAttr(pos, appliesto)
  case class EndListMacroAttr(s: String, pos: Int, appliesto: String) extends MacroAttr(pos, appliesto)
  case class NotEndListMacroAttr(s: String, pos: Int, appliesto: String) extends NotMacroAttr(pos, appliesto)
  case class KVMacroAttr(k: String, v: String, pos: Int, appliesto: String) extends MacroAttr(pos, appliesto)
  case class NotKVMacroAttr(k: String, v: String, pos: Int, appliesto: String) extends NotMacroAttr(pos, appliesto)
  case class KeyOnlyMacroAttr(k: String, pos: Int, appliesto: String) extends MacroAttr(pos, appliesto)
  case class NotKeyOnlyMacroAttr(k: String, pos: Int, appliesto: String) extends NotMacroAttr(pos, appliesto)
  def convertSimpleTextMacroAttr(in: SimpleTextMacroAttr): MacroAttr = {
    if(in.getKey == "lemma") {
      if(in.isNot) {
        NotLemmaMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      } else {
        LemmaMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      }
    } else if(in.isList) {
      if(in.isNot) {
        NotListMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      } else {
        ListMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      }
    } else if(in.isBeginsList) {
      if(in.isNot) {
        NotBeginListMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      } else {
        BeginListMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      }
    } else if(in.isEndsList) {
      if(in.isNot) {
        NotEndListMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      } else {
        EndListMacroAttr(in.getValue, in.getPosition, in.getAppliesTo)
      }
    } else if(in.getValue == "" || in.getValue == null) {
      if(in.isNot) {
        NotKeyOnlyMacroAttr(in.getKey, in.getPosition, in.getAppliesTo)
      } else {
        KeyOnlyMacroAttr(in.getKey, in.getPosition, in.getAppliesTo)
      }
    } else {
      if(in.isNot) {
        NotKVMacroAttr(in.getKey, in.getValue, in.getPosition, in.getAppliesTo)
      } else {
        KVMacroAttr(in.getKey, in.getValue, in.getPosition, in.getAppliesTo)
      }
    }
  }

  def litSetter(v: String) = if(v != "") LitTagElement(v) else LitElement("")
  def MacroAttrToLitTag(m: MacroAttr): LitTagElement = m match {
    case KVMacroAttr(k, v, pos, apto) => LitTagElement(v)
    case KeyOnlyMacroAttr(k, pos, apto) => LitTagElement(k)
    case _ => throw new Exception("Cannot convert this type of MacroAttr " + m.toString)
  }
  def mkAppend(varname: String, kvs: List[MacroAttr]): AppendElement = {
    val tags = kvs.map{MacroAttrToLitTag}
    val tagend = tags :+ LitElement("$ ")
    AppendElement(varname, tagend)
  }
  private def simpleClip(pos: Int, sl: Boolean, part: String): ClipElement = {
    val side = if(sl) "sl" else "tl"
    ClipElement(pos.toString, side, part, null, null, null)
  }
  private def dumbClip(pos: Int) = simpleClip(pos, true, "lem")
  private def simpleEquals(a: ValueElement, b: ValueElement) = EqualElement(true, List[ValueElement](a, b))
  def convertMacroAttrToTest(in: MacroAttr): TestElement = in match {
    case LemmaMacroAttr(s, pos, apto) => TestElement(null, simpleEquals(dumbClip(pos), LitElement(s)))
    case NotLemmaMacroAttr(s, pos, apto) => TestElement(null, NotElement(simpleEquals(dumbClip(pos), LitElement(s))))
    case ListMacroAttr(s, pos, apto) => TestElement(null, InElement(dumbClip(pos), ListElement(s), true))
    case NotListMacroAttr(s, pos, apto) => TestElement(null, NotElement(InElement(dumbClip(pos), ListElement(s), true)))
    case BeginListMacroAttr(s, pos, apto) => TestElement(null, BeginsWithListElement(dumbClip(pos), ListElement(s), true))
    case NotBeginListMacroAttr(s, pos, apto) => TestElement(null, NotElement(BeginsWithListElement(dumbClip(pos), ListElement(s), true)))
    case EndListMacroAttr(s, pos, apto) => TestElement(null, EndsWithListElement(dumbClip(pos), ListElement(s), true))
    case NotEndListMacroAttr(s, pos, apto) => TestElement(null, NotElement(EndsWithListElement(dumbClip(pos), ListElement(s), true)))
    case KVMacroAttr(k, v, pos, apto) => if (v != "") {
      TestElement(null, simpleEquals(simpleClip(pos, true, k), LitTagElement(v)))
    } else {
      TestElement(null, simpleEquals(simpleClip(pos, true, k), LitElement("")))
    }
    case NotKVMacroAttr(k, v, pos, apto) => if (v != "") {
      TestElement(null, NotElement(simpleEquals(simpleClip(pos, true, k), LitTagElement(v))))
    } else {
      TestElement(null, NotElement(simpleEquals(simpleClip(pos, true, k), LitElement(""))))
    }
    case _ => throw new Exception("Can't convert this tag")
  }
  def convertMacroAttrList(in: List[MacroAttr]): TestElement = {
    if(in.length == 1) {
      convertMacroAttrToTest(in(0))
    } else {
      TestElement(null, AndElement(in.map{convertMacroAttrToTest}.map{e => e.cond}))
    }
  }
  abstract class BaseTextMacroEntry
  abstract class SrcTextMacroEntry extends BaseTextMacroEntry
  abstract class TrgTextMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends BaseTextMacroEntry {
    def getPos: Int = pos
    def getMatches: List[MacroAttr] = matches
    def getTargets: List[SrcTextMacroEntry] = targets
  }
  case class SrcBaseMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class SrcInsertionMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class SrcDeletionMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class SrcChunkMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class TrgBaseMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry(pos, matches, targets)
  case class TrgInsertionMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry(pos, matches, targets)
  case class TrgDeletionMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry(pos, matches, targets)
  case class TrgChunkMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry(pos, matches, targets)
  case class SimpleTextMacro(name: String, appliesTo: List[String], entries: List[TrgTextMacroEntry])
  def convertTextMacroEntry(in: JSTMEntry): BaseTextMacroEntry = {
    if(in.hasTarget) {
      convertTrgTextMacroEntry(in)
    } else {
      convertSrcTextMacroEntry(in)
    }
  }
  def convertSrcTextMacroEntry(in: JSTMEntry): SrcTextMacroEntry = {
    if(in.isInsertion) {
      SrcInsertionMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList)
    } else if(in.isDeletion) {
      SrcDeletionMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList)
    } else if(in.isChunkAlignment) {
      SrcChunkMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList)
    } else {
      SrcBaseMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList)
    }
  }
  def convertTrgTextMacroEntry(in: JSTMEntry): TrgTextMacroEntry = {
    if(in.isInsertion) {
      TrgInsertionMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList, in.getTarget.asScala.map{convertSrcTextMacroEntry}.toList)
    } else if(in.isDeletion) {
      //TrgDeletionMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList, in.getTarget.asScala.map{convertSrcTextMacroEntry}.toList)
      throw new Exception("Cannot process deletions")
    } else if(in.isChunkAlignment) {
      TrgChunkMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList, in.getTarget.asScala.map{convertSrcTextMacroEntry}.toList)
    } else {
      TrgBaseMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList, in.getTarget.asScala.map{convertSrcTextMacroEntry}.toList)
    }
  }

  def convertJSTMacro(m: JSTMacro): SimpleTextMacro = {
    SimpleTextMacro(m.getName, m.getAppliesTo.asScala.toList, m.getParts.asScala.map{convertTrgTextMacroEntry}.toList)
  }
  def convertJSTMacroFile(f: String): List[SimpleTextMacro] = {
    val out = JSTMacro.fromFile(f)
    out.asScala.map{convertJSTMacro}.toList
  }
  def convertJSTMacroToXML(m: JSTMacro, clippables: Map[String, Map[String, Boolean]]): DefMacroElement = SimpleTextMacroToXML(convertJSTMacro(m), clippables)
  def SimpleTextMacroToXML(m: SimpleTextMacro, clippables: Map[String, Map[String, Boolean]]): DefMacroElement = {
    def mkClearVar(name: String): LetElement = {
      LetElement(VarElement(name), LitElement(""))
    }
    def posvarpart(pos: Int): String = {
      m.appliesTo(pos - 1).replaceAll("><", "_").replace("<", "").replace(">", "")
    }
    def convertTrgTextMacroEntryToXML(in: TrgTextMacroEntry): SentenceElement = {
      def convertMacroAttrToLet(in: MacroAttr, clippable: Boolean = false, varnm: String = "", tpl: String = "var_"): (LetElement, Option[String]) = in match {
        case LemmaMacroAttr(s, pos, apto) => {
          val varstr = if(tpl.startsWith("_")) varnm + tpl else tpl + varnm
          val varout = if(varnm == "") None else Some(varstr)
          (LetElement(ClipElement(pos.toString, "tl", "lem", null, null, null), LitElement(s)), varout)
        }
        case KVMacroAttr(k, v, pos, apto) => {
          val litpart = litSetter(v)
          if(clippable) {
            val clip = ClipElement(pos.toString, "tl", k, null, null, null)
            (LetElement(clip, litpart), None)
          } else {
            val varbase: String = if(varnm == "") k else varnm + "_" + k
            val varname: String = if(tpl.startsWith("_")) varbase + tpl else tpl + varbase
            val clip = VarElement(varname)
            (LetElement(clip, litpart), Some(varname))
          }
        }
        case KeyOnlyMacroAttr(k, pos, apto) => {
          if(clippable) {
            val clip = ClipElement(pos.toString, "tl", k, null, null, null)
            (LetElement(clip, LitElement("")), None)
          } else {
            val varbase: String = if(varnm == "") k else varnm + "_" + k
            val varname: String = if(tpl.startsWith("_")) varbase + tpl else tpl + varbase
            val clip = VarElement(varname)
            (LetElement(clip, LitElement("")), Some(varname))
          }
        }
        case _ => throw new Exception("Can't convert this tag")
      }
      def convertMacroAttrToLetClip(in: MacroAttr, varname: String, tpl: String = "var_"): (LetElement, Option[String]) = {
        val clippable: Boolean = in match {
          case KVMacroAttr(k, v, pos, apto) => clippables(apto) != null && clippables(apto)(k)
          case KeyOnlyMacroAttr(k, pos, apto) => clippables(apto) != null && clippables(apto)(k)
          case _ => false
        }
        convertMacroAttrToLet(in, clippable, varname, tpl)
      }
      def convertSrcMacroEntry(s: SrcTextMacroEntry): (List[SentenceElement], Map[String, List[Option[String]]]) = s match {
        case SrcInsertionMacroEntry(_, matches) => {
          val first = matches(0) match {
            case LemmaMacroAttr(s, pos, apto) => LemmaMacroAttr(s, pos, apto)
            case _ => throw new Exception("Insertion macro entry missing lemma " + s.toString)
          }
          val letpart = convertMacroAttrToLet(first, false, m.name)
          val rest = matches.tail
          val appendname = if(letpart._2 == None) "" else letpart._2.get
          val appendpart = mkAppend(appendname, rest)
          (List[SentenceElement](letpart._1, appendpart), Map("insert" -> List(letpart._2)))
        }
        case SrcChunkMacroEntry(_, matches) => {
          val tmp = matches.map{e => convertMacroAttrToLet(e, false, "", "_chunk")}
          (tmp.map{e => e._1}, Map("chunk" -> tmp.map{e => e._2}))
        }
        case SrcBaseMacroEntry(pos, matches) => {
          val posstr: String = posvarpart(pos)
          val tmp = matches.map{e => convertMacroAttrToLetClip(e, posstr)}
          (tmp.map{e => e._1}, Map(posstr -> tmp.map{e => e._2}))
        }
        case _ => throw new Exception("Cannot convert this " + s.toString)
      }

      val whens: List[TestElement] = in.getMatches.map{convertMacroAttrToTest}
      val srcmacout = in.getTargets.map{convertSrcMacroEntry}
      if(whens.length != srcmacout.length) {
        throw new Exception("Size mismatch")
      }
      val sents = srcmacout.map{e => e._1}
      val varlists = srcmacout.map{e => e._2}
      val whensxml: List[WhenElement] = whens.zip(sents).map{e => WhenElement(null, e._1, e._2)}
      in match {
        case TrgBaseMacroEntry(p, src, trg) => ChooseElement(null, whensxml, None)
        // FIXME: should behave differently?
        case TrgInsertionMacroEntry(p, src, trg) => ChooseElement(null, whensxml, None)
        case TrgChunkMacroEntry(p, src, trg) => ChooseElement(null, whensxml, None)
        case _ => throw new Exception("Unhandled")
      }
    }
    DefMacroElement(m.name, m.appliesTo.size.toString, null, m.entries.map{convertTrgTextMacroEntryToXML})
  }
  def JSTMacroFromString(s: String): List[JSTMacro] = {
    import java.io.ByteArrayInputStream
    JSTMacro.fromFile(new ByteArrayInputStream(s.getBytes)).asScala.toList
  }
}