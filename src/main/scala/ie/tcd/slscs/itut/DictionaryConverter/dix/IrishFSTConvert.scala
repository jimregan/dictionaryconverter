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

object IrishFSTConvert {
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
                        "+Event",
                        "+Adj+Base+DeNom",
                        "+Adj+Base+DeNom+Com+Sg",
                        "+Adj+Base+DeNom+Com+Sg+Ecl",
                        "+Adj+Base+DeNom+Com+Sg+Len",
                        "+Adj+Base+DeNom+DefArt",
                        "+Adj+Base+DeNom+Ecl",
                        "+Adj+Base+DeNom+Gen+Sg+Ecl",
                        "+Adj+Base+DeNom+Gen+Sg+Len",
                        "+Adj+Base+DeNom+Len"
)

  val remap_whole = Map("+Art+Gen+Sg+Def+Fem" -> "det.def.f.sg.gen",
                        "+Art+Pl+Def" -> "det.def.mf.pl",
                        "+Art+Sg+Def" -> "det.def.mf.sg",
                        "+Punct+Fin" -> "sent",
                        "+Adv+Q+Wh" -> "adv.itg",
                        "+Prep+Simp" -> "pr",
                        "+Conj+Coord" -> "cnjcoo",
                        "+Conj+Subord" -> "cnjsub",
                        "+Cmc" -> "ij",
                        "+Cmc+English" -> "ij",
                        "+Adv+Temp" -> "adv",
                        "+Adv+Gn" -> "adv",
                        "+Adv+Loc" -> "adv"
                        )

  val tag_remap = Map("Masc" -> "m",
                      "Fem" -> "f",
                      "1P" -> "p1",
                      "2P" -> "p2",
                      "3P" -> "p3",
                      "Verb" -> "vblex",
                      "Noun" -> "n",
                      "Com" -> "com",
                      "Gen" -> "gen",
                      "hPref" -> "hpref",
                      "DefArt" -> "defart",
                      "Ecl" -> "ecl",
                      "Sg" -> "sg",
                      "Len" -> "len",
                      "Adj" -> "adj",
                      "Adv" -> "adv",
                      "Voc" -> "voc",
                      "Dat" -> "dat",
                      "Pl" -> "pl",
                      "Cond" -> "cni",
                      "Imper" -> "imp",
                      "FutInd" -> "fti",
                      "Q" -> "itg",
                      "Neg" -> "neg",
                      "Auto" -> "aut",
                      "Rel" -> "rel",
                      "Emph" -> "emph",
                      "PresInd" -> "pri",
                      "PresSubj" -> "prs",
                      "PresImp" -> "imp",
                      "Cop" -> "cop",
                      "Pres" -> "pres",
                      "Strong" -> "strong",
                      "PastImp" -> "pii",
                      "PastInd" -> "ifi",
                      "PastSubj" -> "ifs",
                      "Past" -> "past",
                      "Ref" -> "ref",
                      "Pers" -> "pers",
                      "FutInd" -> "fti",
                      "Qty" -> "qnt",
                      "Imp" -> "imp",
                      "Idf" -> "ind",
                      "Dep" -> "dep",
                      "Weak" -> "weak",
                      "Conj" -> "cnjadv",
                      "Itj" -> "ij",
                      "Dem" -> "dem",
                      "Obj" -> "obj"
                      )
  val crap_tags = List("VI", "VT", "Vow", "VTI", "VD")
  case class Entry(surface: String, lemma: String, tags: List[String], r: String = null, variant: String = null)

  def maptags(str: String): List[String] = {
    val number = List("Sg", "Pl")
    val cases = List("Com", "Gen", "Voc", "Dat")
    def maptagsInner(l: List[String], cur: List[String]): List[String] = cur match {
      case head :: tail => {
        if(crap_tags.contains(head)) {
          maptagsInner(l, tail)
        } else if(tag_remap.contains(head)) {
          if(cases.contains(head)) {
            tail match {
              case x :: xs => if(number.contains(x)) {
                maptagsInner(l ++ List(tag_remap(x), tag_remap(head)), xs)
              } else {
                maptagsInner(l :+ tag_remap(head), tail)
              }
            }
          } else {
            if(head == "NegQ") {
              maptagsInner(l ++ List("itg", "neg"), tail)
            } else {
              maptagsInner(l :+ tag_remap(head), tail)
            }
          }
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

object Mapper extends App {
  import scala.io.Source
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.Entry
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.procWords

  val remap_whole = Map(".i.+Abr\t.i." -> Entry(".i.", ".i.", List("adv")))
  if(args.length < 1) {
    throw new Exception("No filename specified")
  }
  val filename = args(0)

  def mapper(s: String): Option[Entry] = {
    if(remap_whole.contains(s)) {
      remap_whole.get(s)
    } else {
      val parts = s.split("\t")
      val first_plus = s.indexOf('+')
      val lemma = s.substring(0, first_plus)
      val tags = parts(0).substring(first_plus)
      val surface = parts(1)
      procWords(surface, lemma, tags)
    }
  }
  val parts = Source.fromFile(filename).getLines.map{mapper}
}

