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

package ie.tcd.slscs.itut.DictionaryConverter.dix

class IrishFSTConvert {
  val lronly_whole = Map("+Adv+Temp+Gen+Sg" -> "adv",
                         "+Adv+Temp+NG" -> "adv",
                         "+Adv+Gn+NG" -> "adv",
                         "+Adv+Loc+NG" -> "adv",
                         "+Art+Sg+Def+NG" -> "det.def.mf.sg")
  val skip_whole = List("+Abr",
                        "+Abr+Title",
                        "+Adj+Pref",
                        "+Filler",
                        "+Xxx",
                        "+Foreign+English+Adj",
                        "+Foreign+English+Adv",
                        "+Foreign+English+Conj",
                        "+Foreign+English+Det",
                        "+Foreign+English+Noun",
                        "+Foreign+English+Noun+Prop",
                        "+Foreign+English+Noun+Prop+Def",
                        "+Foreign+English+Num",
                        "+Foreign+English+Prep",
                        "+Foreign+English+Pron",
                        "+Foreign+English+Verb",
                        "+XMLTag",
                        "+Event")

  val remap_whole = Map("+Art+Gen+Sg+Def+Fem" -> "det.def.f.sg.gen",
                        "+Art+Pl+Def" -> "det.def.mf.pl",
                        "+Art+Sg+Def" -> "det.def.mf.sg",
                        "+Punct+Fin" -> "sent")

  val tag_remap = Map("Masc" -> "m",
                      "Fem" -> "f",
                      "1P" -> "p1",
                      "2P" -> "p2",
                      "3P" -> "p3",
                      "Verb" -> "vblex",
                      "Noun" -> "n"
                      )
  val crap_tags = List("VI", "VT", "Vow", "VTI", "VD")
  case class Entry(surface: String, lemma: String, tags: List[String], r: String = null, variant: String = null)

  def maptags(str: String): List[String] = {
    def maptagsInner(l: List[String], cur: List[String]): List[String] = cur match {
      case head :: tail => {
        if(crap_tags.contains(head)) {
          maptagsInner(l, tail)
        } else if(remap_whole.contains(head)) {
          maptagsInner(l :+ head, tail)
        } else {
          throw new Exception("Unmapped tag: " + head)
        }
      }
      case Nil => l
    }
    val chop = if(str.charAt(0) == '+') str.substring(1) else str
    if(chop.startsWith("Subst+Noun+")) {
      maptagsInner(List("n", "mf"), chop.substring(11).split("\\+").toList)
    } else if(chop.startsWith("Prop+Noun+")) {
      maptagsInner(List("np"), chop.substring(10).split("\\+").toList)
    } else {
      maptagsInner(List.empty[String], chop.split("\\+").toList)
    }
  }
  def procWords(surface: String, lemma: String, tags: String): Option[Entry] = {
    if(lronly_whole.contains(tags)) {
      Some(Entry(surface, lemma, lronly_whole(tags).split("\\.").toList, "lr"))
    } else if(remap_whole.contains(tags)) {
      Some(Entry(surface, lemma, remap_whole(tags).split("\\.").toList))
    } else if(skip_whole.contains(tags)) {
      None
    } else {
      Some(Entry(surface, lemma, maptags(tags)))
    }
  }
}
