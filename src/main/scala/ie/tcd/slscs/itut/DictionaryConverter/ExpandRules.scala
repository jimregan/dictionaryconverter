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

package ie.tcd.slscs.itut.DictionaryConverter

import scala.util.matching.Regex

object ExpandRules {
  //"NP | <adj> <n> | <n> <adj> | 1-2 2-1 | agree:1,2 | check_human:1 | big dog | madra mór"
  case class Macro(name: String, params: List[Int])
  def stringToMacro(s: String): Option[Macro] = {
    val in = s.split(":")
    if(in.length != 2) {
      None
    }
    val params = in(1).split(",").map{_.trim}.map{_.toInt}.toList
    Some(Macro(in(0), params))
  }
  implicit def stringToMacroList(s: String): List[Macro] = s.split(" ").map{stringToMacro}.flatten.toList
  def splitAlignmentsSL(al: String): Map[Int, Array[Int]] = {
    def toTuple(i: Array[Int]): (Int, Int) = (i(0), i(1))
    val als = al.split(" ").map {_.split("-").map(_.toInt)}.map{toTuple}
    val almap = als.groupBy(_._1).map { case (k, v) => (k, v.map(_._2)) }
    almap
  }
  def splitAlignmentsTL(al: String): Map[Int, Array[Int]] = {
    def toTuple(i: Array[Int]): (Int, Int) = (i(1), i(0))
    val als = al.split(" ").map{_.split("-").map(_.toInt)}.map{toTuple}
    val almap = als.groupBy(_._1).map { case (k, v) => (k, v.map(_._2)) }
    almap
  }
  trait Token
  case class LemmaToken(lemma: String, tags: List[String]) extends Token
  case class TagsToken(tags: List[String]) extends Token
  def makeToken(s: String): Token = {
    val withLemma = new Regex("""([^<]*)<(.*)>""")
    val tagsOnly = new Regex("""<(.*)>""")
    s match {
      case withLemma(lem, tag) => LemmaToken(lem, tag.split("><").toList)
      case tagsOnly(tag) => TagsToken(tag.split("><").toList)
      case _ => throw new Exception("Unexpected value: " + s)
    }
  }
  def makeTokenList(s: String): List[Token] = s.split(" ").map{makeToken}.toList
  case class RulePiece(trg: List[Token], srcal: Map[Int, Array[Int]],
                       trgal: Map[Int, Array[Int]], srcmac: List[Macro],
                       trgmac: List[Macro], srceg: String, trgeg: String)
  case class MultiPartRule(tag: String, src: List[Token], parts: List[RulePiece])
  case class Rule(tag: String, src: List[Token], trg: List[Token],
                  srcal: Map[Int, Array[Int]], trgal: Map[Int, Array[Int]],
                  srcmac: List[Macro], trgmac: List[Macro], srceg: String,
                  trgeg: String)
  implicit def RuleToMultiPart(r: Rule): MultiPartRule = {
    val rp:RulePiece = RulePiece(r.trg, r.srcal, r.trgal, r.srcmac, r.trgmac, r.srceg, r.trgeg)
    MultiPartRule(r.tag, r.src, List[RulePiece](rp))
  }
  def stringToRule(parts: Array[String]): Rule = {
    val tag = parts(0)
    val src = makeTokenList(parts(1))
    val trg = makeTokenList(parts(2))
    val srcal = splitAlignmentsSL(parts(3))
    val trgal = splitAlignmentsTL(parts(3))
    val srcmac = stringToMacroList(parts(4))
    val trgmac = stringToMacroList(parts(5))
    val srceg = parts(6)
    val trgeg = parts(7)
    Rule(tag, src, trg, srcal, trgal, srcmac, trgmac, srceg, trgeg)
  }
  trait TrivialRule
  case class TrivialIdentity(tag: String, toks: List[Token]) extends TrivialRule
  case class TrivialDeletion(tag: String, toks: List[Token]) extends TrivialRule
  def makeTrivialRule(parts: Array[String]): TrivialRule = {
    if(parts(2) == "1-0") {
      TrivialDeletion(parts(0), makeTokenList(parts(1)))
    } else if(parts(2) == "1-1") {
      TrivialIdentity(parts(0), makeTokenList(parts(1)))
    } else {
      throw new Exception("Only 1-1 and 1-0 (deletion) alignments are currently handled")
    }
  }
  def mkRuleMap(in: List[Rule]): Map[String, Rule] = in.map{e => (e.tag, e)}.toMap
}

