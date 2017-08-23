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

package ie.tcd.slscs.itut.DictionaryConverter;
import ie.tcd.slscs.itut.ApertiumTransfer.Text.{AttributeSequenceClippable, SimpleTextMacroAttr, SimpleTextMacro => JSTMacro, SimpleTextMacroEntry => JSTMEntry}

import scala.collection.JavaConverters._
import scala.collection.JavaConversions.mapAsScalaMap
import scala.collection.mutable

object TextMacro {
  trait MacroAttr
  trait NotMacroAttr extends MacroAttr
  case class LemmaMacroAttr(s: String) extends MacroAttr
  case class NotLemmaMacroAttr(s: String) extends NotMacroAttr
  case class ListMacroAttr(s: String) extends MacroAttr
  case class NotListMacroAttr(s: String) extends NotMacroAttr
  case class BeginListMacroAttr(s: String) extends MacroAttr
  case class NotBeginListMacroAttr(s: String) extends NotMacroAttr
  case class EndListMacroAttr(s: String) extends MacroAttr
  case class NotEndListMacroAttr(s: String) extends NotMacroAttr
  case class KVMacroAttr(k: String, v: String) extends MacroAttr
  case class NotKVMacroAttr(k: String, v: String) extends NotMacroAttr
  case class KeyOnlyMacroAttr(k: String) extends MacroAttr
  case class NotKeyOnlyMacroAttr(k: String) extends NotMacroAttr
  def convertSimpleTextMacroAttr(in: SimpleTextMacroAttr): MacroAttr = {
    if(in.getKey == "lemma") {
      if(in.isNot) {
        NotLemmaMacroAttr(in.getValue)
      } else {
        LemmaMacroAttr(in.getValue)
      }
    } else if(in.isList) {
      if(in.isNot) {
        NotListMacroAttr(in.getValue)
      } else {
        ListMacroAttr(in.getValue)
      }
    } else if(in.isBeginsList) {
      if(in.isNot) {
        NotBeginListMacroAttr(in.getValue)
      } else {
        BeginListMacroAttr(in.getValue)
      }
    } else if(in.isEndsList) {
      if(in.isNot) {
        NotEndListMacroAttr(in.getValue)
      } else {
        EndListMacroAttr(in.getValue)
      }
    } else if(in.getValue == "" || in.getValue == null) {
      if(in.isNot) {
        NotKeyOnlyMacroAttr(in.getKey)
      } else {
        KeyOnlyMacroAttr(in.getKey)
      }
    } else {
      if(in.isNot) {
        NotKVMacroAttr(in.getKey, in.getValue)
      } else {
        KVMacroAttr(in.getKey, in.getValue)
      }
    }
  }
  def convertAttributeSequenceClippable(in: AttributeSequenceClippable): Map[String, Map[String, Boolean]] = {
    in.getClippable.asScala.toMap.map{e => (e._1.toString, e._2.asScala.toMap.map{f => (f._1.toString, f._2.booleanValue)})}
  }
  // TODO: kv -> assign to variable, or clip
  // a map with valid attributes for that pos is required, should be passed from elsewhere
  // this is all hardwired so if it was on the left, it's sl; on the right, tl
  def convertMacroAttrToLet(in: MacroAttr, pos: Int): LetElement = in match {
    case LemmaMacroAttr(s) => LetElement(ClipElement(pos.toString, "tl", "lem", null, null, null), LitElement(s))
    case KVMacroAttr(k, v) => if (v != "") {
      LetElement(ClipElement(pos.toString, "tl", k, null, null, null), LitTagElement(v))
    } else {
      LetElement(ClipElement(pos.toString, "tl", k, null, null, null), LitElement(""))
    }
    case _ => throw new Exception("Can't convert this tag")
  }
  def convertMacroAttrToTest(in: MacroAttr, pos: Int): TestElement = in match {
    case LemmaMacroAttr(s) => TestElement(null, EqualElement(true, List[ValueElement](ClipElement(pos.toString, "sl", "lem", null, null, null), LitElement(s))))
    case NotLemmaMacroAttr(s) => TestElement(null, NotElement(EqualElement(true, List[ValueElement](ClipElement(pos.toString, "sl", "lem", null, null, null), LitElement(s)))))
    case ListMacroAttr(s) => TestElement(null, InElement(ClipElement(pos.toString, "sl", "lem", null, null, null), ListElement(s), true))
    case NotListMacroAttr(s) => TestElement(null, NotElement(InElement(ClipElement(pos.toString, "sl", "lem", null, null, null), ListElement(s), true)))
    case BeginListMacroAttr(s) => TestElement(null, BeginsWithListElement(ClipElement(pos.toString, "sl", "lem", null, null, null), ListElement(s), true))
    case NotBeginListMacroAttr(s) => TestElement(null, NotElement(BeginsWithListElement(ClipElement(pos.toString, "sl", "lem", null, null, null), ListElement(s), true)))
    case EndListMacroAttr(s) => TestElement(null, EndsWithListElement(ClipElement(pos.toString, "sl", "lem", null, null, null), ListElement(s), true))
    case NotEndListMacroAttr(s) => TestElement(null, NotElement(EndsWithListElement(ClipElement(pos.toString, "sl", "lem", null, null, null), ListElement(s), true)))
    case KVMacroAttr(k, v) => if (v != "") {
      TestElement(null, EqualElement(true, List[ValueElement](ClipElement(pos.toString, "sl", k, null, null, null), LitTagElement(v))))
    } else {
      TestElement(null, EqualElement(true, List[ValueElement](ClipElement(pos.toString, "sl", k, null, null, null), LitElement(""))))
    }
    case NotKVMacroAttr(k, v) => if (v != "") {
      TestElement(null, NotElement(EqualElement(true, List[ValueElement](ClipElement(pos.toString, "sl", k, null, null, null), LitTagElement(v)))))
    } else {
      TestElement(null, NotElement(EqualElement(true, List[ValueElement](ClipElement(pos.toString, "sl", k, null, null, null), LitElement("")))))
    }
    case _ => throw new Exception("Can't convert this tag")
  }
  abstract class BaseTextMacroEntry
  abstract class SrcTextMacroEntry extends BaseTextMacroEntry
  abstract class TrgTextMacroEntry extends BaseTextMacroEntry
  case class SrcBaseMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class SrcInsertionMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class SrcDeletionMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class SrcChunkMacroEntry(pos: Int, matches: List[MacroAttr]) extends SrcTextMacroEntry
  case class TrgBaseMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry
  case class TrgInsertionMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry
  case class TrgDeletionMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry
  case class TrgChunkMacroEntry(pos: Int, matches: List[MacroAttr], targets: List[SrcTextMacroEntry]) extends TrgTextMacroEntry
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
      TrgDeletionMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList, in.getTarget.asScala.map{convertSrcTextMacroEntry}.toList)
    } else if(in.isChunkAlignment) {
      TrgChunkMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList, in.getTarget.asScala.map{convertSrcTextMacroEntry}.toList)
    } else {
      TrgBaseMacroEntry(in.getPosition, in.getAttrs.asScala.map{convertSimpleTextMacroAttr}.toList, in.getTarget.asScala.map{convertSrcTextMacroEntry}.toList)
    }
  }

}