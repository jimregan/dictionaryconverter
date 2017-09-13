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
                         "+Art+Sg+Def+NG" -> "det.def.mf.sg",
                         "+Cop+Cond+Ecl+VF" -> "cop.cni",
                         "+Cop+Cond+VF" -> "cop.cni",
                         "+Cop+Past+Dep+Neg+Q+VF" -> "cop.past.itg.neg",
                         "+Cop+Past+Dep+Neg+VF" -> "cop.past.dep.neg",
                         "+Cop+Past+Dep+Q+VF" -> "cop.past.dep.itg",
                         "+Cop+Past+Dep+VF" -> "cop.past.dep",
                         "+Cop+Past+Ecl+VF" -> "cop.past",
                         "+Cop+Past+NegQ+VF" -> "cop.past.itg.neg",
                         "+Cop+Past+Neg+VF" -> "cop.past.neg",
                         "+Cop+Past+Q+VF" -> "cop.past.itg",
                         "+Cop+Past+RelInd+Neg+VF" -> "cop.past.rel.ind.neg",
                         "+Cop+Past+RelInd+VF" -> "cop.past.rel.ind",
                         "+Cop+Past+Rel+Neg+VF" -> "cop.past.rel.neg",
                         "+Cop+Past+Rel+VF" -> "cop.past.rel",
                         "+Cop+Past+VF" -> "cop.past",
                         "+Cop+Pres+Dep+VF" -> "cop.pres.dep",
                         "+Cop+Pres+RelInd+VF" -> "cop.pres.rel.ind",
                         "+Cop+PresSubj+Neg+VF" -> "cop.prs.neg",
                         "+Cop+PresSubj+VF" -> "cop.prs.subj",
                         "+Prep+Simp+DefArt" -> "pr",
                         "+Prep+CmpdNoGen" -> "pr.cmpd",
                         "+Verb+VI+PresInd+Rel+Typo" -> "vbser.pri.rel",
                         "+Verb+VI+PresInd+Typo" -> "vbser.pri"

                         )
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
                        "+Adj+Base+DeNom+Len",
                        "+Punct",
                        "+Verbal+Adj",
                        "+Verbal+Adj+Len",
                        "+Verbal+Noun+VI",
                        "+Verbal+Noun+VI+Ecl",
                        "+Verbal+Noun+VI+Gen",
                        "+Verbal+Noun+VI+Gen+Ecl",
                        "+Verbal+Noun+VI+Gen+hPref",
                        "+Verbal+Noun+VI+Gen+Len",
                        "+Verbal+Noun+VI+Gen+Len+Ecl",
                        "+Verbal+Noun+VI+Gen+Len+Len",
                        "+Verbal+Noun+VI+hPref",
                        "+Verbal+Noun+VI+Len",
                        "+Verbal+Noun+VI+Len+Ecl",
                        "+Verbal+Noun+VI+Len+Len",
                        "+Verbal+Noun+VT",
                        "+Verbal+Noun+VT+Ecl",
                        "+Verbal+Noun+VT+Gen",
                        "+Verbal+Noun+VT+Gen+Ecl",
                        "+Verbal+Noun+VT+Gen+hPref",
                        "+Verbal+Noun+VT+Gen+Len",
                        "+Verbal+Noun+VT+hPref",
                        "+Verbal+Noun+VTI",
                        "+Verbal+Noun+VTI+Ecl",
                        "+Verbal+Noun+VTI+Gen",
                        "+Verbal+Noun+VTI+Gen+Ecl",
                        "+Verbal+Noun+VTI+Gen+hPref",
                        "+Verbal+Noun+VTI+Gen+Len",
                        "+Verbal+Noun+VTI+hPref",
                        "+Verbal+Noun+VTI+Len",
                        "+Verbal+Noun+VT+Len",
                        "+Part+Pat"
)

  val remap_whole = Map("+Art+Gen+Sg+Def+Fem" -> "det.def.f.sg.gen",
                        "+Art+Pl+Def" -> "det.def.mf.pl",
                        "+Art+Sg+Def" -> "det.def.mf.sg",
                        "+Punct+Fin" -> "sent",
                        "+Punct+Fin+Q" -> "sent",
                        "+Punct+Int" -> "sent",
                        "+Punct+Quo" -> "quot",
                        "+Punct+Brack+St" -> "lpar",
                        "+Punct+Brack+End" -> "rpar",
                        "+Punct+Bar" -> "guio",
                        "+Adv+Q+Wh" -> "adv.itg",
                        "+Prep+Simp" -> "pr",
                        "+Conj+Coord" -> "cnjcoo",
                        "+Conj+Subord" -> "cnjsub",
                        "+Cmc" -> "ij",
                        "+Cmc+English" -> "ij",
                        "+Adv+Temp" -> "adv",
                        "+Adv+Gn" -> "adv",
                        "+Adv+Loc" -> "adv",
                        "+Adv+Its" -> "adv",
                        "+Adj+Its" -> "adj.phr",
                        "+Adv+Dir" -> "adv",
                        "+Cop+Cond+Ecl" -> "cop.cni",
                        "+Cop+Cond" -> "cop.cni",
                        "+Cop+Past+Dep+Neg+Q" -> "cop.past.itg.neg",
                        "+Cop+Past+Dep+Neg" -> "cop.past.dep.neg",
                        "+Cop+Past+Dep+Q" -> "cop.past.dep.itg",
                        "+Cop+Past+Dep" -> "cop.past.dep",
                        "+Cop+Past+Ecl" -> "cop.past",
                        "+Cop+Past+NegQ" -> "cop.past.itg.neg",
                        "+Cop+Past+Neg" -> "cop.past.neg",
                        "+Cop+Past+Q" -> "cop.past.itg",
                        "+Cop+Past+RelInd+Neg" -> "cop.past.rel.ind.neg",
                        "+Cop+Past+RelInd" -> "cop.past.rel.ind",
                        "+Cop+Past+Rel+Neg" -> "cop.past.rel.neg",
                        "+Cop+Past+Rel" -> "cop.past.rel",
                        "+Cop+Past" -> "cop.past",
                        "+Cop+Pres+Dep" -> "cop.pres.dep",
                        "+Cop+Pres+RelInd" -> "cop.pres.rel.ind",
                        "+Cop+PresSubj+Neg" -> "cop.prs.neg",
                        "+Cop+PresSubj" -> "cop.prs.subj",
                        "+Det+Poss+3P+Sg+Fem" -> "det.pos.p3.f.sg",
                        "+Det+Poss+3P+Sg+Masc" -> "det.pos.p3.m.sg",
                        "+Prep+Cmpd" -> "pr.cmpd",
                        "+Verbal+Noun+NStem" -> "vbnom",
                        "+Verbal+Noun+NStem+Ecl" -> "vbnom.ecl",
                        "+Verbal+Noun+NStem+Gen" -> "vbnom.gen",
                        "+Verbal+Noun+NStem+Gen+Ecl" -> "vbnom.gen.ecl",
                        "+Verbal+Noun+NStem+Gen+Len" -> "vbnom.gen.len",
                        "+Verbal+Noun+NStem+hPref" -> "vbnom.hpref",
                        "+Verbal+Noun+NStem+Len" -> "vbnom.len",
                        "+Num+Card+Def" -> "num.defart",
                        "+Num+Card" -> "num",
                        "+Num+Card+Ecl" -> "num.ecl",
                        "+Num+Card+hPref" -> "num.hpref",
                        "+Num+Card+Len" -> "num.len",
                        "+Num+Ord+Def" -> "det.ord.sp.defart",
                        "+Num+Ord" -> "det.ord.sp",
                        "+Num+Ord+Ecl" -> "det.ord.sp.ecl",
                        "+Num+Ord+hPref" -> "det.ord.sp.hpref",
                        "+Num+Ord+Len" -> "det.ord.sp.len",
                        "+Part+Ad" -> "adv"
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
                      "Obj" -> "obj",
                      "Poss" -> "pos",
                      "Sbj" -> "subj",
                      "Subj" -> "sub",
                      "Det" -> "det",
                      "Inf" -> "inf",
                      "Ord" -> "ord",
                      "Part" -> "part",
                      "Nm" -> "num",
                      "Num" -> "num",
                      "Cp" -> "cop",
                      // Fake addition
                      "Vbser" -> "vbser"
                      )
  val crap_tags = List("VI", "VT", "Vow", "VTI", "VD", "Base", "Var", "Suf", "Vb")
  abstract class EntryBasis
  case class Entry(surface: String, lemma: String, tags: List[String], r: String = null, variant: String = null) extends EntryBasis
  case class RHS(lemma: String, tags: List[String])
  case class JoinedEntry(surface: String, parts: List[RHS], r: String = null, variant: String = null) extends EntryBasis

  val DIALECTS = List("CC", "CM", "CU")

  def getDialect(tags: String): String = {
    val tagsw = if(tags.startsWith("+")) tags.substring(1) else tags
    tagsw.split("\\+").filter{DIALECTS.contains(_)}.mkString(",")
  }
  def stripDialect(tags: String): String = {
    val tagsw = if(tags.startsWith("+")) tags.substring(1) else tags
    tagsw.split("\\+").filter{!DIALECTS.contains(_)}.mkString("+")
  }
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
            } else if(head == "RelInd") {
              maptagsInner(l ++ List("rel", "ind"), tail)
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
    val chop = stripDialect(str)
    if(chop.startsWith("Subst+Noun+")) {
      maptagsInner(List("n", "mf"), chop.substring(11).split("\\+").toList)
    } else if(chop.startsWith("Prop+Noun+")) {
      // skipped outside
      maptagsInner(List("np"), chop.substring(10).split("\\+").toList)
    } else {
      maptagsInner(List.empty[String], chop.split("\\+").toList)
    }
  }
  def procWords(surface: String, lemma: String, tags: String): Option[Entry] = {
    if(lronly_whole.contains(tags)) {
      Some(Entry(surface, lemma, lronly_whole(tags).split("\\.").toList, "lr"))
    } else if(tags.contains("+Var") || tags.contains("+Suf")) {
      Some(Entry(surface, lemma, maptags(tags), "lr"))
    } else if(tags.contains("+Emph") && surface.endsWith("-s")) {
      Some(Entry(surface, lemma, maptags(tags), "lr"))
    } else if(remap_whole.contains(tags)) {
      Some(Entry(surface, lemma, remap_whole(tags).split("\\.").toList))
    } else if(skip_whole.contains(tags)) {
      None
    } else if(tags.contains("Prop+Noun")) {
      None
    } else {
      val tagtweak = if(lemma == "bí" && tags.contains("+Verb")) tags.replace("+Verb", "+Vbser") else tags
      Some(Entry(surface, lemma, maptags(tags)))
    }
  }

  def prpersMaker(tags: String): List[String] = {
    def gender(s: String) = if(s.contains("Masc")) "m" else if(s.contains("Fem")) "f" else "mf"
    def number(s: String) = if(s.contains("Sg")) "sg" else if(s.contains("Pl")) "pl" else "sp"
    def person(s: String) = if(s.contains("1P")) "p1" else if(s.contains("2P")) "p2" else "p3"
    def addtags(s: String) = List[String](person(s), gender(s), number(s))
    if(!tags.contains("Pron+Prep")) {
      List.empty[String]
    } else {
      val base = List("prn", "obj")
      base ++ addtags(tags)
    }
  }
}

object Mapper extends App {
  import scala.io.Source
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.Entry
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.procWords

  val remap_whole = Map(".i.+Abr\t.i." -> Entry(".i.", ".i.", List("adv")),
                        "cá+Adv+Q+Wh+Past\tcár" -> Entry("cár", "cár", List("adv.itg")),
                        ",+Punct+Int\t," -> Entry(",", ",", List("cm")),
                        "'+Punct+Quo\t'" -> Entry("'", "'", List("apos")),
                        "an+Part+Vb+Q\tan" -> Entry("an", "an", List("adv", "itg",)))
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

