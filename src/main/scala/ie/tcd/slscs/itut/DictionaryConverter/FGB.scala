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

//abstract class Entry
case class SeeAlso(src: String, trg: String) extends Entry
case class NounEquals(src: String, trg: String, gen: String) extends Entry
case class OtherEquals(src: String, trg: String) extends Entry
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


  def trim(s: String):String = s.replaceAll("[.,;]? *$", "").replaceAll("^ *", "")
  def noupper(s: String):Boolean = s.toLowerCase == s
  def fixtrg(src: String, trg: String):String = if(noupper(src)) trim(trg.toLowerCase) else trim(trg)

  def breakdownComplexEntry(e: Elem): List[BaseXML] = {
    def breakdownComplexEntryPiece(n: Node): BaseXML = n match {
      case <trans><r>{tr}</r></trans> => TransElem(tr.text)
      case <title>{title}</title> => TitleElem(title.text)
      case <a>{a}</a> => AElem(a.text)
      case <b>{b}</b> => BElem(b.text)
      case <c>{c}</c> => CElem(c.text)
      case <e>{e}</e> => EElem(e.text)
      case <g>{g @ _* }</g> => GElem(g.map{_.text}.mkString)
      case <h>{h}</h> => HElem(h.text)
      case <i>{i}</i> => IElem(i.text)
      case <k>{k}</k> => KElem(k.text)
      case <l>{l}</l> => LElem(l.text)
      case <n>{n}</n> => NElem(n.text)
      case <o>{o}</o> => OElem(o.text)
      case <p>{p}</p> => PElem(p.text)
      case <r>{r}</r> => RElem(r.text)
      case <s>{s}</s> => SElem(s.text)
      case <v>{v}</v> => VElem(v.text)
      case <x>{x}</x> => XElem(x.text)
      case scala.xml.Text(", ") => Comma()
      case scala.xml.Text(". ") => Fullstop()
      case scala.xml.Text(" :") => Colon()
      case scala.xml.Text(t) => Txt(t)
    }
    e match {
      case <entry><title>{c @ _*}</title></entry> => c.map{breakdownComplexEntryPiece}.toList
      case <entry>{c @ _*}</entry> => c.map{breakdownComplexEntryPiece}.toList
    }
  }


  def readSimpleEntry(n: Elem): Entry = {
    n match {
      case <entry><title>{src}</title><g>f. = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "f")
      case <entry><title>{src}</title><g>m. = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "m")
      case <entry><title>{src}</title><g>f = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "f")
      case <entry><title>{src}</title><g>m = </g><s>{trg}</s></entry> => NounEquals(trim(src.text), fixtrg(src.text, trg.text), "m")
      case <entry><title>{src}</title><x>{sx}</x> = <s>{trg}</s><x>{tx}</x>.</entry> => OtherEqualsXX(trim(src.text), trim(sx.text), fixtrg(src.text, trg.text), trim(tx.text))
      case <entry><title>{src}</title><x>{sx}</x> :<k> </k><s>{trg}</s><x>{tx}</x>.</entry> => OtherEqualsXX(trim(src.text), trim(sx.text), fixtrg(src.text, trg.text), trim(tx.text))
      case <entry><title>{src}</title><g>{gram}</g><trans><r>{trg}</r></trans></entry> => OtherSimpleTrans(trim(src.text), trim(gram.text), fixtrg(src.text, trg.text))

      case _ => throw new Exception("Failed to match input")
    }
  }
}
