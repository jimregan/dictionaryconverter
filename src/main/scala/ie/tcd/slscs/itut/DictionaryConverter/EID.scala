/*
 * The MIT License (MIT)
 *
 * Copyright © 2016 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2016 Coláiste na Tríonóide, Baile Átha Cliath
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
import ie.tcd.slscs.itut.gramadanj.Conversion.EID.LabelMap

abstract class Entry
abstract class TranslationEntry(src: String, trg: String) extends Entry {
  def isAmbiguous: Boolean = trg.contains(",")
  def hasBrackets: Boolean = trg.contains("(")
}
trait Label {
  val lbl: String
  private lazy val _label: EID.LabelBase = EID.fixLabel(EID.Label(lbl))
  def getLabels: Array[String] = EID.labelToStringArray(_label)
}
trait LabelEntry extends Label
abstract class LabelTransEntry(src: String, lbl: String, trg: String) extends TranslationEntry(src, trg) with LabelEntry
case class SimpleEntry(src: String, lbl: String, trg: String) extends LabelTransEntry(src, lbl, trg)
case class SimpleEntryDomain(src: String, lbl: String, trg: String, domain: String) extends LabelTransEntry(src, lbl, trg)
case class SimpleNounEntry(src: String, lbl: String, trg: String, gen: String, opt: Boolean = true) extends LabelTransEntry(src, lbl, trg)
case class SimpleNounEntryDomain(src: String, lbl: String, trg: String, gen: String, domain: String) extends LabelTransEntry(src, lbl, trg)
case class EmptyEntry(src: String, lbl: String) extends Entry with LabelEntry
case class EqualsEntry(src: String, lbl: String, eq: String) extends Entry with LabelEntry

class EID {
}

object EID {
  import scala.xml._

  def trimp(s: String):String = s.trim.replaceAll("[.,;]?$", "")

  abstract class BaseXML()
  abstract class RawXML(s: String) extends BaseXML
  abstract class LabelBase(label: String) extends BaseXML {
    def s: String = label
  }
  trait Target
  case class Src(s: String) extends RawXML(s)
  case class Trg(s: String) extends RawXML(s) with Target
  case class Trg2(s: String, l: String, opt: Boolean = false) extends RawXML(s) with Target
  case class Trg3(s: String, l: String, t: String, opt: Boolean = false) extends RawXML(s) with Target
  case class MultiTrg(children: List[BaseXML]) extends BaseXML with Target
  case class Label(label: String) extends LabelBase(label)
  case class GrammaticalLabel(label: String) extends LabelBase(label)
  case class DomainLabel(label: String) extends LabelBase(label)
  case class GrammaticalLabels(raw: String, labels: Array[String]) extends LabelBase(raw)
  case class DomainLabels(raw: String, labels: Array[String]) extends LabelBase(raw)
  case class Gen(s: String) extends RawXML(s)
  case class Txt(s: String) extends RawXML(s)
  case class SATxt(s: String) extends RawXML(s)
  case class VerbalNoun(s: String) extends RawXML(s)
  case class Sense(s: String) extends RawXML(s)
  case class SuperSense(s: String) extends RawXML(s)
  case class Super(s: String) extends RawXML(s)
  case class SubSense(s: String) extends RawXML(s)
  case class SubSense2(s: String, sub: String) extends RawXML(s)
  case class Line(s: Seq[BaseXML]) extends BaseXML
  case class Title(s: Seq[BaseXML]) extends BaseXML
  case class WordSense(sense: String, subsense: String, line: Seq[BaseXML]) extends BaseXML
  case class EmptySense(s: String) extends RawXML(s)
  case class EmptySenseSub(s: String, sub: String) extends RawXML(s)
  case class EmptySubSense(s: String) extends RawXML(s)
  case class Valency(src: String, trg: String) extends BaseXML

  def noupper(s: String):Boolean = s.trim.toLowerCase == s
  def fixtrg(src: String, trg: String):String = if(noupper(src)) trimp(trg.toLowerCase) else trimp(trg)
  def readSimpleEntry(n: Elem): Entry = {
    n match {
      case <entry><title><src>{src}</src>, <label>{lbl @ _* }</label></title></entry> => EmptyEntry(src.toString, lbl.map{_.text}.mkString)
      case <entry><title><src>{src}</src>, <label>{lbl @ _* }</label> <trg>{trg}</trg>.</title></entry> => SimpleEntry(src.toString, lbl.map{_.text}.mkString, trg.toString)
      case <entry><title><src>{src}</src>, <label>{lbl @ _* }</label> <label>{lbl2}</label>: <trg>{trg}</trg>.</title></entry> => SimpleEntryDomain(src.toString, lbl.map{_.text}.mkString, trg.toString, lbl2.toString)
      case <entry><title><src>{src}</src>, <label>{lbl @ _* }</label> <label>{lbl2}</label>: <trg>{trg}<label>{gen}</label></trg>.</title></entry>  => SimpleNounEntryDomain(trimp(src.toString), lbl.map{_.text}.mkString, trimp(trg.toString), gen.toString, trimp(lbl2.toString))
      case <entry><title><src>{src}</src>, <label>{lbl @ _* }</label> <trg>{trg}<label>{gen}</label></trg>.</title></entry> => SimpleNounEntry(trimp(src.toString), lbl.map{_.text}.mkString, fixtrg(src.toString, trg.toString), trimp(gen.toString))
      case <entry><title><src>{src}</src>, <label>{lbl @ _* }</label> <trg>{trg}<noindex>(<label>{gen}</label>)</noindex></trg>.</title></entry> => SimpleNounEntry(trimp(src.toString), lbl.map{_.text}.mkString, trimp(trg.toString), gen.toString)
      case <entry><title><src>{src}</src>, <label>{lbl}</label>{txt}</title></entry> => if(txt.text.trim.startsWith("=")) {
        EqualsEntry(trimp(src.toString), lbl.toString.trim, txt.toString.trim.substring(1).trim)
      } else {
        throw new Exception("Check entry: " + src.toString + " " + lbl.toString + " " + txt.toString)
      }
      case _ => throw new Exception("Failed to match input")
    }
  }

  def breakdownComplexEntry(e: Elem): List[BaseXML] = {
    def optVNTrimmer(txt: String): String = if(txt.trim.endsWith(")")) {
      txt.trim.substring(0, txt.trim.length-1)
    } else {
      txt.trim
    }
    def breakdownComplexEntryPiece(n: Node): BaseXML = n match {
      case <src>{src}</src> => Src(src.text)
/*
      case <trg>{trg}<label>{lbl}</label></trg> => Trg2(trg.text, lbl.text)
      case <trg>{trg}<noindex>(<label>{lbl}</label>)</noindex></trg> => Trg2(trg.text, lbl.text, true)
      case <trg>{trg}<label>{lbl}</label>{trg2}</trg> => Trg3(trg.text, lbl.text, trg2.text)
      case <trg>{trg}<noindex>(<label>{lbl}</label>)</noindex>{trg2}</trg> => Trg3(trg.text, lbl.text, trg2.text, true)
      */
      case <trg>{trg @ _*}</trg> => MultiTrg(trg.map{breakdownComplexEntryPiece}.toList)
      case <noindex>(<label>v.n.</label> <trg>{vn}</trg>)</noindex> => VerbalNoun(vn.text)
      case <noindex>(<label>v.n.</label>{vn}</noindex> => VerbalNoun(optVNTrimmer(vn.text))
      case <noindex>(<src>{src}</src>, <trg>{trg}</trg>)</noindex> => Valency(src.text, trg.text)
      case <label>{lbl @ _* }</label> => fixLabel(Label(lbl.map{_.text}.mkString))
      case <sense>{sns}</sense> => Sense(sns.text)
      case <subsense>{sns}</subsense> => SubSense(sns.text)
      case <supersense>{sns}</supersense> => SuperSense(sns.text)
      case <super>{sns}</super> => Super(sns.text)
      case <line>{c @ _*}</line> => Line(c.map{breakdownComplexEntryPiece})
      case <title>{c @ _*}</title> => Title(c.map{breakdownComplexEntryPiece})
      case scala.xml.Text(t) => if(t.startsWith(". S.a.")) {
        SATxt(t.substring(2))
      } else if(t.startsWith("S.a.") || t.startsWith("Cp.")) {
        SATxt(t)
      } else {
        Txt(t)
      }
    }
    e match {
      case <entry><title>{c @ _*}</title></entry> => c.map{breakdownComplexEntryPiece}.toList
      case <entry>{c @ _*}</entry> => c.map{breakdownComplexEntryPiece}.toList
    }
  }
  def fixLabel(l: LabelBase): LabelBase = {
    val getpos = LabelMap.getPoS(l.s)
    val getlbl = LabelMap.fixMultipartTags(l.s)
    if(getpos != null) {
      if(getpos.length != 1) {
        GrammaticalLabels(l.s, getpos)
      } else {
        GrammaticalLabel(getpos(0))
      }
    } else if(getlbl != null) {
      if(getlbl.length != 1) {
        DomainLabels(l.s, getlbl)
      } else {
        DomainLabel(getlbl(0))
      }
    } else {
      l
    }
  }
  def labelToStringArray(l: LabelBase): Array[String] = l match {
    case DomainLabels(t, a) => a
    case GrammaticalLabels(t, a) => a
    case DomainLabel(t) => Array[String](t)
    case GrammaticalLabel(t) => Array[String](t)
    case Label(t) => t.split(",")
    case _ => null
  }
  def mkWordSenses(seq: List[BaseXML]): List[BaseXML] = {
    def doWordSenses(in: String, acc: List[BaseXML], l: List[BaseXML]): List[BaseXML] = l match {
      case Sense(s) :: xs => xs match {
        case Sense(t) :: xx => doWordSenses(t, acc :+ EmptySense(s), xx)
        case Line(l) :: xx => doWordSenses("", acc :+ WordSense(s, "", l), xx)
        case SubSense(sub) :: xx => xx match {
          case Line(l) :: xy => doWordSenses("", acc :+ WordSense(in, sub, l), xy)
          case SubSense(subb) :: xx => doWordSenses("", acc :+ EmptySenseSub(s, sub), xx)
          case Nil => acc :+ EmptySenseSub(s, sub)
        }
        case Nil => acc :+ EmptySense(s)
      }
      case SubSense(sub) :: xs => xs match {
        case Line(l) :: xx => doWordSenses(in, acc :+ WordSense(in, sub, l), xx)
        case SubSense(subb) :: xx => doWordSenses(in, acc :+ EmptySubSense(sub), xs)
        case Nil => acc :+ EmptySubSense(sub)
      }
      case x :: xs => doWordSenses("", acc :+ x, xs)
      case Nil => acc
    }
    doWordSenses("", List.empty[BaseXML], seq)
  }

  /*
  def cleanInner(seq: Seq[BaseXML]): Seq[BaseXML] = {
    val l = seq.toList
    l match {
      case 
    }
  }
*/
}

