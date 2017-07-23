/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Trinity College, Dublin
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
package FGB

//abstract class Entry
case class SeeAlso(src: String, trg: String) extends Entry
case class NounEquals(src: String, trg: String, gen: String) extends Entry
case class OtherEquals(src: String, trg: String) extends Entry
case class OtherEqualsLX(src: String, srcid: String, trg: String) extends Entry
case class OtherEqualsRX(src: String, trg: String, trgid: String) extends Entry
case class OtherEqualsXX(src: String, srcid: String, trg: String, trgid: String) extends Entry
case class OtherSimpleTrans(src: String, pos: String, trg: String) extends TranslationEntry(src, trg)

object FGB {
  import scala.xml._

  abstract class BaseXML()
  abstract class RawXML(s: String) extends BaseXML
  case class TitleElem(s: String) extends RawXML(s)
  case class TitleXElem(s: String, x: String) extends RawXML(s)
  case class AElem(s: String) extends RawXML(s)
  case class BElem(s: String) extends RawXML(s)
  case class CElem(s: String) extends RawXML(s)
  case class EElem(s: String) extends RawXML(s)
  case class GElem(s: String) extends RawXML(s)
  case class HElem(s: String) extends RawXML(s)
  case class IElem(s: String) extends RawXML(s)
  case class KElem(s: String) extends RawXML(s)
  case class LElem(s: String) extends RawXML(s)
  case class NElem(s: String) extends RawXML(s)
  case class OElem(s: String) extends RawXML(s)
  case class PElem(s: String) extends RawXML(s)
  case class RElem(s: String) extends RawXML(s)
  case class SElem(s: String) extends RawXML(s)
  case class VElem(s: String) extends RawXML(s)
  case class XElem(s: String) extends RawXML(s)
  case class TransElem(s: String) extends RawXML(s)
  case class SubentryElem(s: String) extends RawXML(s)
  case class Txt(s: String) extends RawXML(s)
  case class Comma() extends BaseXML
  case class Fullstop() extends BaseXML
  case class Colon() extends BaseXML
  case class CloseParen() extends BaseXML
  case class CloseParenStop() extends BaseXML
  case class OpenParen() extends BaseXML
  abstract class Filtered(s: String) extends RawXML(s)
  case class PosPiece(s: String) extends Filtered(s)
  case class GramPiece(s: String, t: String) extends Filtered(s)
  case class RefPieces(a: String, l: List[RefPiece]) extends Filtered(a)
  case class RefPiece(s: String, x: String, n: String, l: String) extends Filtered(s)

  def trim(s: String):String = s.replaceAll("[.,;]? *$", "").replaceAll("^ *", "")
  def trims(s: String):String = s.replaceAll(" *$", "").replaceAll("^ *", "")
  def noupper(s: String):Boolean = s.toLowerCase == s
  def fixtrg(src: String, trg: String):String = if(noupper(src)) trim(trg.toLowerCase) else trim(trg)

  def breakdownComplexEntry(e: Elem): List[BaseXML] = {
    def breakdownComplexEntryPiece(n: Node): BaseXML = n match {
      case <trans><r>{tr}</r></trans> => TransElem(tr.text)
      case <title>{title}</title> => TitleElem(title.text)
      case <a>{a}</a> => AElem(trims(a.text))
      case <b>{b}</b> => BElem(trims(b.text))
      case <c>{c}</c> => CElem(c.text)
      case <e>{e}</e> => EElem(e.text)
      case <g>{g @ _* }</g> => GElem(g.map{_.text}.mkString)
      case <h>{h}</h> => HElem(h.text)
      case <i>{i}</i> => IElem(i.text)
      case <k>{k}</k> => KElem(k.text)
      case <l>{l}</l> => LElem(l.text)
      case <n>{n}</n> => NElem(n.text)
      case <o>{o}</o> => OElem(o.text)
      case <p>{p}</p> => if (p == ", ") Comma() else PElem(p.text)
      case <r>{r}</r> => RElem(r.text)
      case <s>{s}</s> => SElem(s.text)
      case <v>{v}</v> => VElem(v.text)
      case <x>{x}</x> => XElem(x.text)
      case scala.xml.Text(", ") => Comma()
      case scala.xml.Text(". ") => Fullstop()
      case scala.xml.Text(" :") => Colon()
      case scala.xml.Text(" : ") => Colon()
      case scala.xml.Text("(") => OpenParen()
      case scala.xml.Text(")") => CloseParen()
      case scala.xml.Text(").") => CloseParenStop()
      case scala.xml.Text(t) => Txt(t)
    }
    e match {
      case <entry><title>{c @ _*}</title></entry> => c.map{breakdownComplexEntryPiece}.toList
      case <entry>{c @ _*}</entry> => c.map{breakdownComplexEntryPiece}.toList
    }
  }

  def nextIsNElem(l: List[BaseXML]): Boolean = l match {
    case NElem(n) :: xs => true
    case _ => false
  }

  def mkGramPiece(a: String, b: String): List[Filtered] = {
    if (a.contains("(")) {
        val out = a.split("\\(")
        return List[Filtered](PosPiece(trims(out(0))), GramPiece(out(1), b))
    } else {
        return List[Filtered](GramPiece(a, b))
    }
  }

  def consumeSeeAlso(a: String, list: List[BaseXML]): List[BaseXML] = {
    def consumeSeeAlsoInner(cur: RefPiece, p: RefPieces, list: List[BaseXML]): List[BaseXML] = list match {
      case SElem(s) :: xs => {
        val newrp = RefPiece(s, "", "", "")
        if(cur.s != "") {
          consumeSeeAlsoInner(newrp, RefPieces(p.a, p.l :+ cur), xs)
        } else {
          consumeSeeAlsoInner(newrp, RefPieces(p.a, p.l), xs)
        }
      }
      case XElem(x) :: xs => {
        val newrp = RefPiece(cur.s, x, "", "")
        if(cur.x != "") {
          consumeSeeAlsoInner(newrp, RefPieces(p.a, p.l :+ cur), xs)
        } else {
          consumeSeeAlsoInner(newrp, RefPieces(p.a, p.l), xs)
        }
      }
      case NElem(n) :: xs => {
        val newrp = RefPiece(cur.s, cur.x, n, "")
        val newrps = if(cur.n != "") RefPieces(p.a, p.l :+ cur) else RefPieces(p.a, p.l)
        if(nextIsNElem(xs)) {
          List[BaseXML](newrps) ++ xs
        } else {
          consumeSeeAlsoInner(newrp, newrps, xs)
        }
      }
      case LElem(l) :: xs => {
        val newrp = RefPiece(cur.s, cur.x, cur.n, l)
        if(cur.x != "") {
          consumeSeeAlsoInner(newrp, RefPieces(p.a, p.l :+ cur), xs)
        } else {
          consumeSeeAlsoInner(newrp, RefPieces(p.a, p.l), xs)
        }
      }
      case OpenParen() :: xs => consumeSeeAlsoInner(cur, p, xs)
      case Comma() :: xs => consumeSeeAlsoInner(cur, p, xs)
      case CloseParen() :: xs => consumeSeeAlsoInner(cur, p, xs)
      case CloseParenStop() :: xs => List[BaseXML](p) ++ xs
      case nil => List[BaseXML](p)
    }
    consumeSeeAlsoInner(RefPiece("", "", "", ""), RefPieces(a, List[RefPiece]()), list)
  }
  def mkWordSenses(seq: List[BaseXML]): List[BaseXML] = {
    /**
     * If the current element is <n>, and is followed directly by another,
     * the next <n> is the sense number of the next sense
     */
    //def fix
    def doWordSenses(l: List[BaseXML], acc: List[BaseXML]): List[BaseXML] = l match {
      case TitleElem(t) :: xs => xs match {
        case XElem(x) :: xs => doWordSenses(xs, acc :+ TitleXElem(t, x))
        case x :: xs => doWordSenses(List[BaseXML](x) ++ xs, acc :+ TitleElem(t))
        case Nil => acc :+ TitleElem(t)
      }
      case GElem(g) :: xs => xs match {
        case BElem(b) :: xs => doWordSenses(xs, acc ++ mkGramPiece(g, b))
        case x :: xs => doWordSenses(List[BaseXML](x) ++ xs, acc :+ GElem(g))
        case Nil => acc :+ GElem(g)
      }
      case AElem(a) :: xs => doWordSenses(consumeSeeAlso(a, xs), acc)
      case x :: xs => doWordSenses(xs, acc :+ x)
      case Nil => acc
    }
    doWordSenses(seq, List.empty[BaseXML])
  }


  def readSimpleEntry(n: Elem): Entry = {
    n match {
      case <entry><title>{src}</title><g>f. = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "f")
      case <entry><title>{src}</title><g>m. = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "m")
      case <entry><title>{src}</title><g>f = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "f")
      case <entry><title>{src}</title><g>m = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "m")
      case <entry><title>{src}</title><g>= </g><s>{trg}</s></entry> => OtherEquals(trim(src.text), fixtrg(src.text, trg.text))
      case <entry><title>{src}</title>= <s>{trg}</s></entry> => OtherEquals(trim(src.text), fixtrg(src.text, trg.text))
      case <entry><title>{src}</title><x>{x}</x>= <s>{trg}</s></entry> => OtherEqualsLX(trim(src.text), trim(x.text), fixtrg(src.text, trg.text))
      case <entry><title>{src}</title><x>{x}</x>= <s>{trg}</s>.</entry> => OtherEqualsLX(trim(src.text), trim(x.text), fixtrg(src.text, trg.text))
      case <entry><title>{src}</title>= <s>{trg}</s><x>{x}</x>.</entry> => OtherEqualsRX(trim(src.text), fixtrg(src.text, trg.text), trim(x.text))
      case <entry><title>{src}</title><x>{sx}</x> = <s>{trg}</s><x>{tx}</x>.</entry> => OtherEqualsXX(trim(src.text), trim(sx.text), fixtrg(src.text, trg.text), trim(tx.text))
      case <entry><title>{src}</title><x>{sx}</x> :<k> </k><s>{trg}</s><x>{tx}</x>.</entry> => OtherEqualsXX(trim(src.text), trim(sx.text), fixtrg(src.text, trg.text), trim(tx.text))
      case <entry><title>{src}</title><g>{gram}</g><trans><r>{trg}</r></trans></entry> => OtherSimpleTrans(trim(src.text), trim(gram.text), fixtrg(src.text, trg.text))

      case _ => throw new Exception("Failed to match input")
    }
  }
}
