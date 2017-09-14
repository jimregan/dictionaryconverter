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
                         "+Verb+VI+PresInd+Typo" -> "vbser.pri",
                         "+CM+Prep+Simp+NG" -> "pr",
                         "Prep+Simp+NG" -> "pr"

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
                        "+Pron+Pers+1P+Pl",
                        "+Pron+Pers+1P+Pl+Emph",
                        "+Pron+Pers+1P+Pl+Sbj",
                        "+Pron+Pers+1P+Pl+Sbj+Emph",
                        "+Pron+Pers+1P+Sg",
                        "+Pron+Pers+1P+Sg+Emph",
                        "+Pron+Pers+2P+Pl",
                        "+Pron+Pers+2P+Pl+Dem",
                        "+Pron+Pers+2P+Pl+Emph",
                        "+Pron+Pers+2P+Sg",
                        "+Pron+Pers+2P+Sg+Emph",
                        "+Pron+Pers+2P+Sg+Emph+Len",
                        "+Pron+Pers+2P+Sg+Len",
                        "+Pron+Pers+3P+Pl",
                        "+Pron+Pers+3P+Pl+Dem",
                        "+Pron+Pers+3P+Pl+Emph",
                        "+Pron+Pers+3P+Pl+Sbj",
                        "+Pron+Pers+3P+Pl+Sbj+Dem",
                        "+Pron+Pers+3P+Pl+Sbj+Emph",
                        "+Pron+Pers+3P+Sg",
                        "+Pron+Pers+3P+Sg+Fem",
                        "+Pron+Pers+3P+Sg+Fem+Emph",
                        "+Pron+Pers+3P+Sg+Fem+hPref",
                        "+Pron+Pers+3P+Sg+Fem+Sbj",
                        "+Pron+Pers+3P+Sg+Fem+Sbj+Emph",
                        "+Pron+Pers+3P+Sg+Masc",
                        "+Pron+Pers+3P+Sg+Masc+Emph",
                        "+Pron+Pers+3P+Sg+Masc+hPref",
                        "+Pron+Pers+3P+Sg+Masc+Sbj",
                        "+Pron+Pers+3P+Sg+Masc+Sbj+Emph",
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
                        "+Punct+Brack+Fin" -> "rpar",
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
                        "+Noun+Masc+Dat+Pl" -> "n.m.pl.dat",
                        "+Noun+Masc+Voc+Pl+Def+Len" -> "n.m.pl.voc.len"
                        )

  val tag_remap = Map("Masc" -> "m",
                      "Fem" -> "f",
                      "1P" -> "p1",
                      "2P" -> "p2",
                      "3P" -> "p3",
                      "Verb" -> "vblex",
                      "Noun" -> "n",
                      "Com" -> "com",
                      "Comp" -> "comp",
                      "NegQ" -> "itg.neg",
                      "RelInd" -> "rel.ind",
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
                      "Pron" -> "prn",
                      "Prep" -> "pr",
                      "Part" -> "part",
                      "Deg" -> "deg",
                      "Op" -> "op",
                      "Direct" -> "dir",
                      "Indirect" -> "indir",
                      // Fake addition
                      "Vbser" -> "vbser"
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

                      */
  val crap_tags = List("VI", "VT", "Vow", "VTI", "VD", "Base", "Var", "Suf", "Vb", "NotSlen")
  abstract class EntryBasis {
    def getLemma: String
    def getSurface: String
    def getTags: List[String]
  }
  abstract class StemmedEntryBasis extends EntryBasis
  case class Entry(surface: String, lemma: String, tags: List[String], r: String = null, variant: String = null) extends EntryBasis {
    override def getLemma: String = lemma
    override def getSurface: String = surface
    override def getTags: List[String] = tags
  }
  case class StemmedEntry(lcs: String, surface: String, lemma: String, tags: List[String], r: String = null, variant: String = null) extends StemmedEntryBasis {
    override def getLemma: String = lcs + lemma
    override def getSurface: String = lcs + surface
    override def getTags: List[String] = tags
  }
  case class RHS(lemma: String, tags: List[String])
  case class JoinedEntry(surface: String, parts: List[RHS], r: String = null, variant: String = null) extends EntryBasis {
    override def getLemma: String = parts.head.lemma
    override def getSurface: String = surface
    override def getTags: List[String] = parts.head.tags
  }
  case class StemmedJoinedEntry(lcs: String, surface: String, parts: List[RHS], r: String = null, variant: String = null) extends StemmedEntryBasis {
    override def getLemma: String = lcs + parts.head.lemma
    override def getSurface: String = lcs + surface
    override def getTags: List[String] = parts.head.tags
  }

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
        throw new Exception("Unmapped tag: " + head + "(" + str + ")")
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
  def getLR(surface: String, tags: String): String = {
    if(tags.contains("+Var") || tags.contains("+Suf")) {
      "lr"
    } else if(tags.contains("+Emph") && (surface.endsWith("-s") || (surface.endsWith("-sa") || surface.endsWith("-se")))) {
      "lr"
    } else {
      ""
    }
  }
  def procWords(surface: String, lemma: String, tags: String): Option[EntryBasis] = {
    val tagtweak = if(lemma == "bí" && tags.contains("+Verb")) tags.replace("+Verb", "+Vbser") else tags
    val lr = getLR(surface, tags)
    if(lronly_whole.contains(tags)) {
      Some(Entry(surface, lemma, lronly_whole(tags).split("\\.").toList, "lr"))
    } else if(remap_whole.contains(tags)) {
      Some(Entry(surface, lemma, remap_whole(tags).split("\\.").toList))
    } else if(skip_whole.contains(tags)) {
      None
    } else if(tags.contains("Prop+Noun")) {
      None
    } else if(tags.contains("Pron+Prep")) {
      Some(prepMaker(surface, lemma, tags))
    } else {
      Some(Entry(surface, lemma, maptags(tagtweak), lr))
    }
  }

  def prpersMaker(tags: String): List[String] = {
    def gender(s: String) = if(s.contains("Masc")) "m" else if(s.contains("Fem")) "f" else "mf"
    def number(s: String) = if(s.contains("Sg")) "sg" else if(s.contains("Pl")) "pl" else "sp"
    def person(s: String) = if(s.contains("1P")) "p1" else if(s.contains("2P")) "p2" else "p3"
    def emph(s: String): Option[String] = if(s.contains("Emph")) Some("emph") else None
    def addtags(s: String) = List[Option[String]](Some(person(s)), Some(gender(s)), Some(number(s)), emph(s))
    if(!tags.contains("Pron+Prep")) {
      List.empty[String]
    } else {
      val base = List("prn", "obj")
      base ++ addtags(tags).flatten
    }
  }
  def prepMaker(surface: String, lemma: String, tags: String): JoinedEntry = {
    val pr = RHS(lemma, List("pr"))
    val prn = RHS("prpers", prpersMaker(tags))
    val dem: Option[RHS] = if(surface.endsWith("-seo")) {
      Some(RHS("seo", List("det", "dem", "sp")))
    } else if(surface.endsWith("-sin")) {
      Some(RHS("sin", List("det", "dem", "sp")))
    } else {
      None
    }
    val dial = if(tags.contains("CC")) {
      "CC"
    } else if(tags.contains("CM")) {
      "CM"
    } else if(tags.contains("CU")) {
      "CU"
    } else {
      null
    }
    val l: List[Option[RHS]] = List(Some(pr), Some(prn), dem)
    JoinedEntry(surface, l.flatten, "LR", dial)
  }

  def getMutationFromEntryBasis(e: EntryBasis): String = {
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
      case StemmedEntry(_, _, _, t, _, _) => checkForMutation(t)
      case StemmedJoinedEntry(_, _, es, _, _) => checkForMutation(es.head.tags)
    }
  }
  val mutationStarts: Map[Char, List[String]] = Map('b' -> List("mb", "bh"), 'c' -> List("gc", "ch"), 'd' -> List("nd", "dh"),
    'f' -> List("bhf", "fh"), 'g' -> List("ng", "gh"), 'm' -> List("mh"), 'p' -> List("bp", "ph"), 's' -> List("ts", "sh"),
    't' -> List("dt", "th"), 'a' -> List("ha", "t-a", "n-a"), 'e' -> List("he", "t-e", "n-e"),
    'i' -> List("hi", "t-i", "n-i"), 'o' -> List("ho", "t-o", "n-o"), 'u' -> List("hu", "t-u", "n-u"),
    'á' -> List("há", "t-á", "n-á"), 'é' -> List("hé", "t-é", "n-é"),
    'í' -> List("hí", "t-í", "n-í"), 'ó' -> List("hó", "t-ó", "n-ó"), 'ú' -> List("hú", "t-ú", "n-ú"),
    'B' -> List("mB", "Bh"), 'C' -> List("gC", "Ch"), 'D' -> List("nD", "Dh"),
    'F' -> List("bhF", "Fh"), 'G' -> List("nG", "Gh"), 'M' -> List("Mh"), 'P' -> List("bP", "Ph"), 'S' -> List("tS", "Sh"),
    'T' -> List("dT", "Th"), 'A' -> List("hA", "tA", "nA"), 'E' -> List("hE", "tE", "nE"),
    'I' -> List("hI", "tI", "nI"), 'O' -> List("hO", "tO", "nO"), 'U' -> List("hU", "tU", "nU"),
    'Á' -> List("hÁ", "tÁ", "nÁ"), 'É' -> List("hÉ", "tÉ", "nÉ"),
    'Í' -> List("hÍ", "tÍ", "nÍ"), 'Ó' -> List("hÓ", "tÓ", "nÓ"), 'Ú' -> List("hÚ", "tÚ", "nÚ"))
  def isUpperVowel(v: Char): Boolean = {
    val vowels = List('A', 'E', 'I', 'O', 'U', 'Á', 'É', 'Í', 'Ó', 'Ú')
    vowels.contains(v)
  }
  def isLowerVowel(v: Char): Boolean = {
    val vowels = List('a', 'e', 'i', 'o', 'u', 'á', 'é', 'í', 'ó', 'ú')
    vowels.contains(v)
  }
  def isVowel(v: Char): Boolean = {
    isUpperVowel(v) || isLowerVowel(v)
  }
  def findBeginning(s: String, l: List[String]): String = {
    val f = l.dropWhile(e => !s.startsWith(e))
    if(f.isEmpty) {
      ""
    } else {
      f.head
    }
  }

  def mkSdefs(): List[Sdef] = {
    val frommap = tag_remap.values.filter { e => !e.contains(".") }
    val sdefadds = List("mf", "sp")
    val sdefs = frommap.toList ++ sdefadds
    sdefs.map { e => Sdef(e) }
  }

  def IrishLongestCommonPrefix(a: String, b: String): String = {
    if(a == "" || b == "" || a == null || b == null) {
      return ""
    }
    val first_char = a.charAt(0)
    val b_begin = if(mutationStarts.contains(first_char)) findBeginning(b, mutationStarts(first_char)) else ""
    val j = if(!a.startsWith(b_begin)) b_begin.length else 0
    val i = if(j == 0) 0 else 1

    val comp_a = a.substring(i)
    val comp_b = b.substring(j)
    val inner = (comp_a, comp_b).zipped.takeWhile(Function.tupled(_ == _)).map(_._1).mkString
    if(i != 0) {
      first_char + inner
    } else {
      inner
    }
  }

  def IrishLongestCommonPrefixList(lemma: String, surface_forms: List[String]): String = {
    val lcps = surface_forms.map{f => IrishLongestCommonPrefix(lemma, f)}
    val shortest = lcps.reduceLeft((a, b) => if(a.length < b.length) a else b)
    val shortest_forms = lcps.filter{_.length == shortest.length}.distinct
    if(shortest_forms.size == 1) {
      shortest_forms.head
    } else {
      throw new Exception("Shouldn't happen: " + lemma + surface_forms + shortest_forms)
    }
  }
  def stemEntry(e: EntryBasis, lcs: String): StemmedEntryBasis = {
    def lsuffix(s: String): String = s.substring(lcs.length)
    def ssuffix(s: String): String = s.substring(IrishLongestCommonPrefix(lcs, s).length)
    e match {
      case Entry(s, l, t, r, v) => StemmedEntry(lcs, ssuffix(s), lsuffix(l), t, r, v)
      case JoinedEntry(s, p, r, v) => {
        val pend = p.tail
        val pfirst = p.head
        val newplem = lsuffix(pfirst.lemma)
        val retp = List(RHS(newplem, pfirst.tags)) ++ pend
        StemmedJoinedEntry(lcs, ssuffix(s), retp, r, v)
      }
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
  def stemmedEntryToE(ent: StemmedEntry): E = {
    val restr = if(ent.r.toLowerCase == "lr") "LR" else null
    val tags = ent.tags.map{e => S(e)}
    val l = L(List(Txt(ent.surface)))
    val r = R(List(Txt(ent.lemma)) ++ tags)
    E(List(P(l, r)), null, restr, "irishfst", null, false, null, null, null, ent.variant)
  }
  def RHSToTextLike(rhs: RHS): List[TextLike] = List(Txt(rhs.lemma)) ++ rhs.tags.map{e => S(e)}
  def joinRHS(l: List[List[TextLike]]): List[TextLike] = {
    def joinInner(parts: List[List[TextLike]], acc: List[TextLike]): List[TextLike] = parts match {
      case x :: Nil => acc ++ x
      case x :: xs => joinInner(xs, acc ++ x ++ List(J()))
    }
    joinInner(l, List.empty[TextLike])
  }
  def stemmedJoinedEntryToE(ent: StemmedJoinedEntry): E = {
    val restr = if(ent.r.toLowerCase == "lr") "LR" else null
    val l = L(List(Txt(ent.surface)))
    val rhsparts = ent.parts.map{RHSToTextLike}
    val r = R(joinRHS(rhsparts))
    E(List(P(l, r)), null, restr, null, null, false, null, null, null, ent.variant)
  }
  def convertToE(ent: StemmedEntryBasis): E = ent match {
    case s: StemmedEntry => stemmedEntryToE(s)
    case s: StemmedJoinedEntry => stemmedJoinedEntryToE(s)
    case _ => throw new Exception("Nobody expects the Spanish Inquisition: " + ent)
  }
  def mkPardefs(l: List[EntryBasis]): (List[Pardef], List[E]) = {
    val name_basis = makeEntryKey(l.head)
    val lemmas: List[String] = l.map{_.getLemma}.distinct
    if(lemmas.length != 1) {
      throw new Exception("Expected one lemma, got: " + lemmas.mkString(", "))
    }
    val lemma = lemmas.head
    def checkMutation(): Unit = {
      for(ent <- l) {
        val mut = getMutation(lemma, ent.getSurface)
        if(!ent.getTags.contains(mut) && mut != "") {
          System.err.println("Error in " + ent.getSurface + "(" + lemma + "): expected " + mut + "(" + ent.getTags + ")")
        }
      }
    }
    checkMutation()

    val surfaceforms: List[String] = l.map{_.getSurface}
    val lcs = IrishLongestCommonPrefixList(lemma, surfaceforms)
    val pardefname = lcs + "/" + name_basis.substring(lcs.length)
    val stemmed_entries = l.map{e => stemEntry(e, lcs)}
    val tup: Map[String, List[StemmedEntryBasis]] = stemmed_entries.map{e => (getMutationFromEntryBasis(e), e)}.groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}
    val tup_conv: Map[String, List[E]] = tup.map{e => (e._1, e._2.map{convertToE})}
    val pardefmap: Map[String, Pardef] = tup_conv.map{ e => {
      val name = if(e._1 != "") pardefname + "__" + e._1 else pardefname
      (e._1, Pardef(name, null, e._2))
    }}
    val pardeflists: List[Pardef] = pardefmap.values.toList
    def entriesFromPardefs(m: Map[String, Pardef]): List[E] = {
      val lm = lemma
      val first = lm.charAt(0)

      def firstParts(mut: String): List[TextLikeContainer] = {
        val i = I(List(Txt(lcs)))
        val initpar = Par(first + "__" + mut)
        if (lcs == "") {
          if (mut == "") {
            List.empty[TextLikeContainer]
          } else {
            List(initpar)
          }
        } else {
          if (mut == "") {
            List(i)
          } else {
            List(initpar, i)
          }
        }
      }

      val entryl: List[E] = m.map{ e => {
        val fp = firstParts(e._1)
        val name: Par = Par(e._2.name)
        E(fp :+ name, lm, null, "irishfst")
      }}.toList
      entryl
    }
    val entrylists = entriesFromPardefs(pardefmap)
    (pardeflists, entrylists)
  }

  def checkSame(a: EntryBasis, b: EntryBasis): Boolean = {
    def innerCompare(lem: String, tags: List[String], lemb: String, tagsb: List[String]): Boolean = {
      if(lem != lemb && tags.head != tagsb.head) {
        false
      } else {
        if(tags.head == "n" || tags.head == "np") {
          tags(1) != tagsb(1)
        } else {
          true
        }
      }
    }
    a match {
      case Entry(_, lem, tags, _, _) => b match {
        case Entry(_, lemb, tagsb, _, _) => innerCompare(lem, tags, lemb, tagsb)
        case JoinedEntry(_, pcs, _, _) => innerCompare(lem, tags, pcs.head.lemma, pcs.head.tags)
      }
      case JoinedEntry(_, apcs, _, _) => b match {
        case Entry(_, lemb, tagsb, _, _) => innerCompare(apcs.head.lemma, apcs.head.tags, lemb, tagsb)
        case JoinedEntry(_, pcs, _, _) => innerCompare(apcs.head.lemma, apcs.head.tags, pcs.head.lemma, pcs.head.tags)

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
    "céanna+Adj+Base+Ecl\tgcéanna" -> Entry("mar an gcéanna", "mar an gcéanna", List("adv")),
    "cad+Pron+Q\tcad" -> Entry("cad", "cad", List("prn", "itg", "mf", "sp")),
    // it's a pronoun: http://www.teanglann.ie/en/fgb/cad
    // so using this entry to shoehorn in a related entry: it's possible this analysis is only for 'cad chuige' anyway
    "cad+Cop+Pro+Q\tcad" -> Entry("cad chuige", "cad chuige", List("adv", "itg")),
    // 'céard' is a contraction of 'cé an rud', but it means the same as 'cad'
    "cé+Cop+Pro+Q\tcéard" -> Entry("céard", "céard", List("prn", "itg", "mf", "sp"), null, "CM"),
    "cad_chuige+CC+Cop+Pro+Q\ttuige" -> Entry("tuige", "cad chuige", List("adv", "itg"), "lr", "CC"),
    "cés_moite+Conj+Subord\tcés moite" -> Entry("cés moite", "cé is moite", List("cnjsub"), "lr"),
    "cés_móite+Conj+Subord\tcés móite" -> Entry("cés moite", "cé is moite", List("cnjsub"), "lr"),
    "cé_is_moite+Conj+Subord\tcé is moite" -> Entry("cé is moite", "cé is moite", List("cnjsub")),
    "cé_is_móite+Conj+Subord\tcé is móite" -> Entry("cé is móite", "cé is moite", List("cnjsub"), "lr"),
    "ea+Pron+Pers+3P+Sg\tea" -> Entry("ea", "ea", List("prn", "subj", "p3", "nt", "sg")),
    "ea+Pron+Pers+3P+Sg\teadh" -> Entry("eadh", "ea", List("prn", "subj", "p3", "nt", "sg"), "lr"),
    // this is fixed in my fork, including both entries in case
    "ea+Pron+Pers+3P+Sg\thea" -> Entry("hea", "ea", List("prn", "subj", "p3", "nt", "sg", "hpref")),
    "ea+Pron+Pers+3P+Sg+hPref\thea" -> Entry("hea", "ea", List("prn", "subj", "p3", "nt", "sg", "hpref")),
    "seo+Pron+Dem\tseo" -> Entry("seo", "seo", List("prn", "dem", "sp")),
    "seo+Det+Dem\tseo" -> Entry("seo", "seo", List("det", "dem", "sp")),
    "siúd+Pron+Dem\tsiúd" -> Entry("siúd", "siúd", List("prn", "dem", "sp")),
    "siúd+Det+Dem\tsiúd" -> Entry("siúd", "siúd", List("det", "dem", "sp")),
    "sin+Pron+Dem\tsin" -> Entry("sin", "sin", List("prn", "dem", "sp")),
    "sin+Det+Dem\tsin" -> Entry("sin", "sin", List("det", "dem", "sp")),
    "eile+Det+Dem\teile" -> Entry("eile", "eile", List("det", "dem", "sp")),
    "úd+Det+Dem\túd" -> Entry("úd", "úd", List("det", "dem", "sp")),
    // seo?
    "s+Det+Dem\ts'" -> Entry("s'", "seo", List("det", "dem", "sp"), "lr"),
    "siúd+Pron+Dem\tiúd" -> Entry("iúd", "siúd", List("prn", "dem", "sp"), "lr"),
    "sin+Pron+Dem\tsan" -> Entry("san", "sin", List("prn", "dem", "sp"), "lr", "CM"),
    "sin+CM+Det+Dem\tsan" -> Entry("san", "sin", List("det", "dem", "sp"), "lr", "CM"),
    "sin+Pron+Dem\tshin" -> Entry("shin", "sin", List("prn", "dem", "sp"), "lr"),
    "seo+CM+Pron+Dem\tso" -> Entry("so", "seo", List("prn", "dem", "sp"), "lr", "CM"),
    "seo+CM+Det+Dem\tso" -> Entry("so", "seo", List("det", "dem", "sp"), "lr", "CM"),
    "sin+Pron+Dem\tin" -> Entry("in", "sin", List("prn", "dem", "sp"), "lr"),
    "is+Cop+Pres+Pron+Pers+3P+Sg+Masc+Art+Sg+Def+Subst+Noun+Sg\tséard" -> JoinedEntry("séard", List(RHS("is", List("cop", "pres")), RHS("prpers", List("prn", "obj", "p3", "m", "sg")), RHS("an", List("det", "def", "sg")), RHS("rud", List("n", "m", "sg", "com"))), "lr"),
    "is+Cop+Pres+Pron+Pers+3P+Sg+Masc+Art+Sg+Def\tsén" -> JoinedEntry("sén", List(RHS("is", List("cop", "pres")), RHS("prpers", List("prn", "obj", "p3", "m", "sg")), RHS("an", List("det", "def", "sg"))), "lr")
/*
      cad_é+CU+Cop+Pro+Q\tcaidé
      cé+Cop+Pro+Q\tcé
      cé+Cop+Pro+Q+Cop\tcér
      cé+Cop+Pro+Q+Cop\tcérbh

*/
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

  def makeEntryKey(a: EntryBasis): String = {
    def tagrep(tags: List[String]): String = {
      if(tags.head == "n" || tags.head == "np")
        tags.head + "_" + tags(1)
      else
        tags.head
    }
    a match {
      case Entry(_, lem, tags, _, _) => lem + "__" + tagrep(tags)
      case JoinedEntry(_, in, _, _) => in.head.lemma + "__" + tagrep(in.head.tags)
    }
  }
}

object Mapper extends App {
  import scala.io.Source
  import ie.tcd.slscs.itut.DictionaryConverter.dix.IrishFSTConvert._
  import ie.tcd.slscs.itut.DictionaryConverter.dix.Dix.nodetopardef
  import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}
  import java.nio.charset.Charset
  import scala.xml._

  val prsubj =     <pardef n="prsubj__prn">
    <e><p><l>mé</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="sg"/></r></p></e>
    <e><p><l>mise</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>tú</l><r>prpers<s n="prn"/><s n="subj"/><s n="p2"/><s n="mf"/><s n="sg"/></r></p></e>
    <e><p><l>tusa</l><r>prpers<s n="prn"/><s n="subj"/><s n="p2"/><s n="mf"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>sé</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="m"/><s n="sg"/></r></p></e>
    <e><p><l>seisean</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="m"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>sí</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="f"/><s n="sg"/></r></p></e>
    <e><p><l>sise</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="f"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>sinn</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="pl"/></r></p></e>
    <e><p><l>sinne</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>muid</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="pl"/></r></p></e>
    <e r="LR"><p><l>muidne</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>muide</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>muidinne</l><r>prpers<s n="prn"/><s n="subj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e><p><l>sibh</l><r>prpers<s n="prn"/><s n="subj"/><s n="p2"/><s n="mf"/><s n="pl"/></r></p></e>
    <e><p><l>sibhse</l><r>prpers<s n="prn"/><s n="subj"/><s n="p2"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>sibh-se</l><r>prpers<s n="prn"/><s n="subj"/><s n="p2"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>sibh-seo</l><r>prpers<s n="prn"/><s n="subj"/><s n="p2"/><s n="mf"/><s n="pl"/><j/>seo<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
    <e r="LR"><p><l>sibh-sin</l><r>prpers<s n="prn"/><s n="subj"/><s n="p2"/><s n="mf"/><s n="pl"/><j/>sin<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
    <e><p><l>siad</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="mf"/><s n="pl"/></r></p></e>
    <e><p><l>siadsan</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>siad-san</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>siad-seo</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="mf"/><s n="pl"/><j/>seo<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
    <e r="LR"><p><l>siad-sin</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="mf"/><s n="pl"/><j/>sin<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
  </pardef>
  val probj =     <pardef n="probj__prn">
    <e><p><l>mé</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="sg"/></r></p></e>
    <e><p><l>mise</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>thú</l><r>prpers<s n="prn"/><s n="obj"/><s n="p2"/><s n="mf"/><s n="sg"/></r></p></e>
    <e><p><l>thusa</l><r>prpers<s n="prn"/><s n="obj"/><s n="p2"/><s n="mf"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>é</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="m"/><s n="sg"/></r></p></e>
    <e><p><l>hé</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="m"/><s n="sg"/><s n="hpref"/></r></p></e>
    <e><p><l>eisean</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="m"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>í</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="f"/><s n="sg"/></r></p></e>
    <e><p><l>hí</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="f"/><s n="sg"/><s n="hpref"/></r></p></e>
    <e><p><l>ise</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="f"/><s n="sg"/><s n="emph"/></r></p></e>
    <e><p><l>sinn</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="pl"/></r></p></e>
    <e><p><l>sinne</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>muid</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="pl"/></r></p></e>
    <e r="LR"><p><l>muidne</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>muide</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>muidinne</l><r>prpers<s n="prn"/><s n="obj"/><s n="p1"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e><p><l>sibh</l><r>prpers<s n="prn"/><s n="obj"/><s n="p2"/><s n="mf"/><s n="pl"/></r></p></e>
    <e><p><l>sibhse</l><r>prpers<s n="prn"/><s n="obj"/><s n="p2"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>sibh-se</l><r>prpers<s n="prn"/><s n="obj"/><s n="p2"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>sibh-seo</l><r>prpers<s n="prn"/><s n="obj"/><s n="p2"/><s n="mf"/><s n="pl"/><j/>seo<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
    <e r="LR"><p><l>sibh-sin</l><r>prpers<s n="prn"/><s n="obj"/><s n="p2"/><s n="mf"/><s n="pl"/><j/>sin<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
    <e><p><l>iad</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="mf"/><s n="pl"/></r></p></e>
    <e><p><l>hiad</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="mf"/><s n="pl"/><s n="hpref"/></r></p></e>
    <e><p><l>iadsan</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>iad-san</l><r>prpers<s n="prn"/><s n="obj"/><s n="p3"/><s n="mf"/><s n="pl"/><s n="emph"/></r></p></e>
    <e r="LR"><p><l>iad-seo</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="mf"/><s n="pl"/><j/>seo<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
    <e r="LR"><p><l>iad-sin</l><r>prpers<s n="prn"/><s n="subj"/><s n="p3"/><s n="mf"/><s n="pl"/><j/>sin<s n="det"/><s n="dem"/><s n="sp"/></r></p></e>
  </pardef>


  if(args.length < 1) {
    throw new Exception("No filename specified")
  }
  val filename = args(0)
  val outname = filename + ".dix"

  lazy val parts = Source.fromFile(filename).getLines.flatMap{mapper}
  lazy val partmap: Map[String, List[EntryBasis]] = parts.map(e => (makeEntryKey(e), e)).toList.groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}
  val defaultpardefs: List[Pardef] = List(nodetopardef(prsubj), nodetopardef(probj))
  val defaultentries = List(E(List(I(List.empty[TextLike]), Par("prsubj__prn"))), E(List(I(List.empty[TextLike]), Par("probj__prn"))))
  val pieces = partmap.map{e => mkPardefs(e._2)}
  val pardefs: List[Pardef] = defaultpardefs ++ pieces.keys.flatten
  val entries: List[E] = defaultentries ++ pieces.values.flatten
  val section: Section = Section("main", "standard", entries)
  val alphabet = "abcdefghijklmnopqrstuvwxyzáéíóúABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ"
  val dix = Dix(alphabet, mkSdefs(), pardefs, List(section))
  val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outname), Charset.forName("UTF-8")))
  writer.write(dix.toXMLString)
  writer.close()
  System.exit(0)

  print(partmap)
}

