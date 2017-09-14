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
                        "+Part+Pat",
                        // "Arabacha": probably a one off thing in a corpus
                        "+Adj+Masc+Com+Pl"
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
                        "+Part+Ad" -> "adv",
                        "+Part+Vb+Rel+Direct" -> "rel.an.mf.sp",
                        "+Part+Sup" -> "adv",
                        "+Art+Sg+Def" -> "det.def.mf.sg",
                        "+Art+Pl+Def" -> "det.def.mf.pl",
                        "+Art+Gen+Sg+Def+Fem" -> "det.def.f.gen.sg",
                        "+Noun+Masc+Dat+Pl" -> "n.m.pl.dat"
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
                      "Slender" -> "slender",
                      "Pron" -> "pron",
                      // Fake addition
                      "Vbser" -> "vbser",
                      "Prep" -> "pr",
                      "Part" -> "part",
                      "Deg" -> "deg",
                      "Op" -> "op"
                      )
                      /*
+Part+Vb+Cmpl
+Part+Vb+Cmpl+Past
+Part+Vb+Neg+Cmpl
+Part+Vb+Neg+Cmpl+Past
+Part+Vb+Rel+Indirect
+Part+Vb+Rel+Indirect+Past
+Part+Vb+Rel+Indirect+Pro
+CC+Cop+Pro+Q
+Cop+Pro+Dem
+Cop+Pro+Q
+Cop+Pro+Q+Cop
+CU+Cop+Pro+Q
+Det+Q+Art+Sg
+Cop+Q+Art+Sg
+Cop+Pres+Pron+Pers+3P+Sg+Masc+Art+Sg+Def
+Cop+Pres+Pron+Pers+3P+Sg+Masc+Art+Sg+Def+Subst+Noun+Sg

                      */
  val crap_tags = List("VI", "VT", "Vow", "VTI", "VD", "Base", "Var", "Suf", "Vb", "NotSlen")
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
      case head :: tail => if(crap_tags.contains(head)) {
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
    } else if(tags.contains("+Emph") && (surface.endsWith("-s") || (surface.endsWith("-sa") || surface.endsWith("-se")))) {
      Some(Entry(surface, lemma, maptags(tags), "lr"))
    } else if(remap_whole.contains(tags)) {
      Some(Entry(surface, lemma, remap_whole(tags).split("\\.").toList))
    } else if(skip_whole.contains(tags)) {
      None
    } else if(tags.contains("Prop+Noun")) {
      None
    } else {
      val tagtweak = if(lemma == "bí" && tags.contains("+Verb")) tags.replace("+Verb", "+Vbser") else tags
      Some(Entry(surface, lemma, maptags(tagtweak)))
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

  def getMutation(e: EntryBasis): String = {
    val MUTATIONS = List("len", "ecl", "hpref", "defart")
    def checkForMutation(l: List[String]): String = {
      val o = l.filter(MUTATIONS.contains(_))
      if(o.length == 1) {
        o.head
      } else if(o.isEmpty) {
        ""
      } else {
        throw new Exception("List contains more than one mutation: " + l)
      }
    }
    e match {
      case Entry(_, _, t, _, _) => checkForMutation(t)
      case JoinedEntry(_, es, _, _) => checkForMutation(es.head.tags)
    }
  }
  val mutationStarts: Map[Char, List[String]] = Map('b' -> List("mb", "bh"), 'c' -> List("gc", "ch"), 'd' -> List("nd", "dh"),
    'f' -> List("bhf", "fh"), 'g' -> List("ng", "gh"), 'm' -> List("mh"), 'p' -> List("bp", "ph"), 's' -> List("ts", "sh"),
    't' -> List("dt", "th"), 'a' -> List("h-a", "t-a", "n-a"), 'e' -> List("h-e", "t-e", "n-e"),
    'i' -> List("h-i", "t-i", "n-i"), 'o' -> List("h-o", "t-o", "n-o"), 'u' -> List("h-u", "t-u", "n-u"),
    'á' -> List("h-á", "t-á", "n-á"), 'é' -> List("h-é", "t-é", "n-é"),
    'í' -> List("h-í", "t-í", "n-í"), 'ó' -> List("h-ó", "t-ó", "n-ó"), 'ú' -> List("h-ú", "t-ú", "n-ú"),
    'B' -> List("mB", "Bh"), 'C' -> List("gC", "Ch"), 'D' -> List("nD", "Dh"),
    'F' -> List("bhF", "Fh"), 'G' -> List("nG", "Gh"), 'M' -> List("Mh"), 'P' -> List("bP", "Ph"), 'S' -> List("tS", "Sh"),
    'T' -> List("dT", "Th"), 'A' -> List("hA", "tA", "nA"), 'E' -> List("hE", "tE", "nE"),
    'I' -> List("hI", "tI", "nI"), 'O' -> List("hO", "tO", "nO"), 'U' -> List("hU", "tU", "nU"),
    'Á' -> List("hÁ", "tÁ", "nÁ"), 'É' -> List("hÉ", "tÉ", "nÉ"),
    'Í' -> List("hÍ", "tÍ", "nÍ"), 'Ó' -> List("hÓ", "tÓ", "nÓ"), 'Ú' -> List("hÚ", "tÚ", "nÚ"))
  def isUpperVowel(v: Char): Boolean = {
    val vowels = List('A', 'E', 'I', 'O', 'U', 'Á', 'É', 'Í', 'Ó', 'Ú')
    return vowels.contains(v)
  }
  def isLowerVowel(v: Char): Boolean = {
    val vowels = List('a', 'e', 'i', 'o', 'u', 'á', 'é', 'í', 'ó', 'ú')
    return vowels.contains(v)
  }
  def isVowel(v: Char): Boolean = {
    isUpperVowel(v) || isLowerVowel(v)
  }
  def findBeginning(s: String, l: List[String]): Int = {
    val f = l.dropWhile(e => !s.startsWith(e))
    if(f.length == 0) {
      0
    } else {
      f.head.length
    }
  }

  def getMutation(lemma: String, surface: String): String = {
    val lenites: List[Char] = List('b', 'c', 'd', 'f', 'g', 'm', 'p', 's', 't')
    val eclcons: List[Char] = List('b', 'c', 'd', 'f', 'g', 'p', 't')
    if(lemma.charAt(0) == surface.charAt(0)) {
      if(lenites.contains(lemma.charAt(0).toLower) && surface(1) == 'h' && surface(1) != lemma(1)) {
        "len"
      } else {
        ""
      }
    } else if(isVowel(lemma.charAt(0))) {
      if(surface.charAt(0) == 't') {
        "defart"
      } else if(surface.charAt(0) == 'h') {
        "hpref"
      } else if(surface.charAt(0) == 'n') {
        "ecl"
      } else {
        ""
      }
    } else if(lemma.toLowerCase().charAt(0) == 's' && surface.charAt(0) == 't') {
      "defart"
    } else if(eclcons.contains(lemma.toLowerCase().charAt(0))) {
      val ecl_cons = Map('b' -> "mb", 'c' -> "gc", 'd' -> "nd", 'f' -> "bhf", 'g' -> "ng", 'p' -> "bp", 't' -> "dt")
      if(surface.toLowerCase.startsWith(ecl_cons(lemma.toLowerCase.charAt(0)))) {
        "ecl"
      } else {
        ""
      }
    } else {
      ""
    }
  }
  def mkPardefs(l: List[EntryBasis]): List[Pardef] = {
    val tup: Map[String, List[EntryBasis]] = l.map{e => (getMutation(e), e)}.groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}
    List.empty[Pardef]
  }

  def checkSame(a: EntryBasis, b: EntryBasis): Boolean = {
    def innerCompare(lem: String, tags: List[String], lemb: String, tagsb: List[String]): Boolean = {
      if(lem != lemb && tags(0) != tagsb(0)) {
        false
      } else {
        if(tags(0) == "n" || tags(0) == "np") {
          if(tags(1) != tagsb(1)) {
            false
          } else {
            true
          }
        } else {
          true
        }
      }
    }
    a match {
      case Entry(_, lem, tags, _, _) => b match {
        case Entry(_, lemb, tagsb, _, _) => {
          innerCompare(lem, tags, lemb, tagsb)
        }
        case JoinedEntry(_, pcs, _, _) => {
          innerCompare(lem, tags, pcs(0).lemma, pcs(0).tags)
        }
      }
      case JoinedEntry(_, apcs, _, _) => b match {
        case Entry(_, lemb, tagsb, _, _) => {
          innerCompare(apcs(0).lemma, apcs(0).tags, lemb, tagsb)
        }
        case JoinedEntry(_, pcs, _, _) => {
          innerCompare(apcs(0).lemma, apcs(0).tags, pcs(0).lemma, pcs(0).tags)
        }
      }
    }
  }

  val remap_whole_entry = Map(".i.+Abr\t.i." -> Entry(".i.", ".i.", List("adv")),
    "srl.+Abr\tsrl." -> Entry("srl.", "srl.", List("adv")),
    "m.sh.+Abr\tm.sh." -> Entry("m.sh.", "m.sh.", List("adv")),
    "e.g.+Abr\te.g." -> Entry("e.g.", "e.g.", List("adv")),
    "i.e.+Abr\ti.e." -> Entry("i.e.", "i.e.", List("adv")),
    "cá+Adv+Q+Wh+Past\tcár" -> Entry("cár", "cár", List("adv.itg")),
    ",+Punct+Int\t," -> Entry(",", ",", List("cm")),
    "'+Punct+Quo\t'" -> Entry("'", "'", List("apos")),
    "++Num+Op\t+" -> Entry("+", "+", List("num", "op")),
    "an+Part+Vb+Q\tan" -> Entry("an", "an", List("adv", "itg")),
    "níos+Subst+Noun+Sg+Part+Comp\tníos" -> Entry("níos", "níos", List("adv")),
    "ní+Subst+Noun+Sg+Part+Comp\tní" -> Entry("ní", "ní", List("adv")),
    "ní_ba+Subst+Noun+Sg+Part+Comp\tní ba" -> Entry("ní ba", "ní ba", List("adv")),
    "ní_b'+Subst+Noun+Sg+Part+Comp\tní b'" -> Entry("ní b'", "ní ba", List("adv"), "lr"),
    "i+Prep+Art+Pl\tsna" -> JoinedEntry("sna", List(RHS("i", List("pr")), RHS("an", List("det", "def", "mf", "pl")))),
    "de+Prep+Art+Pl\tdena" -> JoinedEntry("dena", List(RHS("de", List("pr")), RHS("an", List("det", "def", "mf", "pl")))),
    "i+Prep+Art+Sg\tinsa" -> JoinedEntry("insa", List(RHS("i", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "i+Prep+Art+Sg\tinsan" -> JoinedEntry("insan", List(RHS("i", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "i+Prep+Art+Sg\tins" -> JoinedEntry("ins", List(RHS("i", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "i+Prep+Art+Sg\tsan" -> JoinedEntry("san", List(RHS("i", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "i+Prep+Art+Sg\tsa" -> JoinedEntry("sa", List(RHS("i", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "de+Prep+Art+Sg\tden" -> JoinedEntry("den", List(RHS("de", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "do+Prep+Art+Sg\tdon" -> JoinedEntry("don", List(RHS("do", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "faoi+Prep+Art+Sg\tfaoin" -> JoinedEntry("faoin", List(RHS("faoi", List("pr")), RHS("an", List("det", "def", "mf", "sg")))),
    "faoi+Prep+Art+Sg\tfén" -> JoinedEntry("fén", List(RHS("faoi", List("pr")), RHS("an", List("det", "def", "mf", "sg"))), "lr"),
    "faoi+Prep+Art+Sg\tfán" -> JoinedEntry("fán", List(RHS("faoi", List("pr")), RHS("an", List("det", "def", "mf", "sg"))), "lr", "CU"),
    "de+Prep+Deg\tdá" -> JoinedEntry("dá", List(RHS("de", List("pr")), RHS("a", List("part", "deg")))),
    "de+Prep+Poss+3P+Sg+Masc\tdá" -> JoinedEntry("dá", List(RHS("de", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "de+Prep+Poss+3P+Sg+Fem\tdá" -> JoinedEntry("dá", List(RHS("de", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "de+Prep+Poss+3P+Pl\tdá" -> JoinedEntry("dá", List(RHS("de", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "do+Prep+Poss+3P+Sg+Masc\tdá" -> JoinedEntry("dá", List(RHS("de", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "do+Prep+Poss+3P+Sg+Fem\tdá" -> JoinedEntry("dá", List(RHS("de", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "do+Prep+Poss+3P+Pl\tdá" -> JoinedEntry("dá", List(RHS("de", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "do+Prep+Poss+3P+Pl+Obj\tá" -> JoinedEntry("á", List(RHS("do", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "do+Prep+Poss+3P+Sg+Fem+Obj\tá" -> JoinedEntry("á", List(RHS("do", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "do+Prep+Poss+3P+Sg+Masc+Obj\tá" -> JoinedEntry("á", List(RHS("do", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "faoi+Prep+Poss+3P+Pl+NG\tfána" -> JoinedEntry("fána", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl"))), "lr"),
    "faoi+Prep+Poss+3P+Pl+NG\tféna" -> JoinedEntry("féna", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl"))), "lr"),
    "faoi+Prep+Poss+3P+Pl\tfaoina" -> JoinedEntry("faoina", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "faoi+Prep+Poss+3P+Sg+Fem+NG\tfána" -> JoinedEntry("fána", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg"))), "lr"),
    "faoi+Prep+Poss+3P+Sg+Fem+NG\tféna" -> JoinedEntry("féna", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg"))), "lr"),
    "faoi+Prep+Poss+3P+Sg+Fem\tfaoina" -> JoinedEntry("faoina", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "faoi+Prep+Poss+3P+Sg+Masc+NG\tfána" -> JoinedEntry("fána", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg"))), "lr"),
    "faoi+Prep+Poss+3P+Sg+Masc+NG\tféna" -> JoinedEntry("féna", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg"))), "lr"),
    "faoi+Prep+Poss+3P+Sg+Masc\tfaoina" -> JoinedEntry("faoina", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "go+Prep+Poss+3P+Pl+NG\tgona" -> JoinedEntry("gona", List(RHS("go", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl"))), "lr"),
    "go+Prep+Poss+3P+Sg+Fem+NG\tgona" -> JoinedEntry("gona", List(RHS("go", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg"))), "lr"),
    "go+Prep+Poss+3P+Sg+Masc+NG\tgona" -> JoinedEntry("gona", List(RHS("go", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg"))), "lr"),
    "i+Prep+Poss+3P+Pl+NG\t'na" -> JoinedEntry("'na", List(RHS("i", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl"))), "lr"),
    "i+Prep+Poss+3P+Pl\tina" -> JoinedEntry("ina", List(RHS("i", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "i+Prep+Poss+3P+Sg+Fem+NG\t'na" -> JoinedEntry("'na", List(RHS("i", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg"))), "lr"),
    "i+Prep+Poss+3P+Sg+Fem\tina" -> JoinedEntry("ina", List(RHS("i", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "i+Prep+Poss+3P+Sg+Masc+NG\t'na" -> JoinedEntry("'na", List(RHS("i", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg"))), "lr"),
    "i+Prep+Poss+3P+Sg+Masc\tina" -> JoinedEntry("ina", List(RHS("i", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "le+Prep+Poss+3P+Pl\tlena" -> JoinedEntry("lena", List(RHS("le", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "le+Prep+Poss+3P+Sg+Fem\tlena" -> JoinedEntry("lena", List(RHS("le", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "le+Prep+Poss+3P+Sg+Masc\tlena" -> JoinedEntry("lena", List(RHS("le", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "ó+Prep+Poss+3P+Pl+NG\t'na" -> JoinedEntry("'na", List(RHS("ó", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl"))), "lr"),
    "ó+Prep+Poss+3P+Pl\tóna" -> JoinedEntry("óna", List(RHS("ó", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "ó+Prep+Poss+3P+Sg+Fem+NG\t'na" -> JoinedEntry("'na", List(RHS("ó", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg"))), "lr"),
    "ó+Prep+Poss+3P+Sg+Fem\tóna" -> JoinedEntry("óna", List(RHS("ó", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "ó+Prep+Poss+3P+Sg+Masc+NG\t'na" -> JoinedEntry("'na", List(RHS("ó", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg"))), "lr"),
    "ó+Prep+Poss+3P+Sg+Masc\tóna" -> JoinedEntry("óna", List(RHS("ó", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "trí+Prep+Poss+3P+Pl\ttrína" -> JoinedEntry("trína", List(RHS("trí", List("pr")), RHS("a", List("det", "pos", "p3", "mf", "pl")))),
    "trí+Prep+Poss+3P+Sg+Fem\ttrína" -> JoinedEntry("trína", List(RHS("trí", List("pr")), RHS("a", List("det", "pos", "p3", "f", "sg")))),
    "trí+Prep+Poss+3P+Sg+Masc\ttrína" -> JoinedEntry("trína", List(RHS("trí", List("pr")), RHS("a", List("det", "pos", "p3", "m", "sg")))),
    "i+Prep+Poss+1P+Pl\tinár" -> JoinedEntry("inár", List(RHS("i", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl")))),
    "ó+Prep+Poss+1P+Pl\tónár" -> JoinedEntry("ónár", List(RHS("ó", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl")))),
    "do+Prep+Poss+2P+Sg+NG\tdod" -> JoinedEntry("dod", List(RHS("do", List("pr")), RHS("a", List("det", "pos", "p2", "mf", "sg"))), "lr"),
    "do+Prep+Poss+2P+Sg+NG\tdod'" -> JoinedEntry("dod'", List(RHS("do", List("pr")), RHS("a", List("det", "pos", "p2", "mf", "sg"))), "lr"),
    "de+Prep+Poss+1P+Pl\tdár" -> JoinedEntry("dár", List(RHS("de", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl")))),
    "do+Prep+Poss+1P+Pl\tdár" -> JoinedEntry("dár", List(RHS("do", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl")))),
    "faoi+Prep+Poss+1P+Pl\tfaoinár" -> JoinedEntry("faoinár", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl")))),
    "faoi+Prep+Poss+1P+Pl+NG\tfénár" -> JoinedEntry("fénár", List(RHS("faoi", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl"))), "lr"),
    "go+Prep+Poss+1P+Pl+NG\tgonár" -> JoinedEntry("gonár", List(RHS("go", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl"))), "lr"),
    "le+Prep+Poss+1P+Pl\tlenár" -> JoinedEntry("lenár", List(RHS("le", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl"))), "lr"),
    "le+Prep+Poss+1P+Sg+NG\tlem" -> JoinedEntry("lem", List(RHS("le", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "sg"))), "lr"),
    "le+Prep+Poss+1P+Sg+NG\tlem'" -> JoinedEntry("lem'", List(RHS("le", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "sg"))), "lr"),
    "trí+Prep+Poss+1P+Pl\ttrínár" -> JoinedEntry("trínár", List(RHS("trí", List("pr")), RHS("a", List("det", "pos", "p1", "mf", "pl")))),
    // this is a noun, not an adjective, but we can catch the phrase all the same
    "céanna+Adj+Base+Ecl\tgcéanna" -> Entry("mar an gcéanna", "mar an gcéanna", List("adv"))
  )
  def mapper(s: String): Option[EntryBasis] = {
    if(remap_whole_entry.contains(s)) {
      remap_whole_entry.get(s)
    } else {
      val parts = s.split("\t")
      val first_plus = s.indexOf('+')
      val lemma = s.substring(0, first_plus)
      val tags = parts(0).substring(first_plus)
      val surface = parts(1)
      procWords(surface, lemma, tags)
    }
  }

}

object Mapper extends App {
  import scala.io.Source
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.Entry
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.EntryBasis
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.JoinedEntry
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert.mapper

  def makeEntryKey(a: EntryBasis): String = {
    def tagrep(tags: List[String]): String = {
      if(tags.head == "n" || tags.head == "np")
        tags.head + "_" + tags(1)
      else
        tags.head
    }
    a match {
      case Entry(_, lem, tags, _, _) => lem + "_" + tagrep(tags)
      case JoinedEntry(_, in, _, _) => in.head.lemma + "_" + tagrep(in.head.tags)
    }
  }

  if(args.length < 1) {
    throw new Exception("No filename specified")
  }
  val filename = args(0)

  val parts = Source.fromFile(filename).getLines.map{mapper}.flatten
  val partmap: Map[String, List[EntryBasis]] = parts.map(e => (makeEntryKey(e), e)).toList.groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}
  print(partmap)
}

