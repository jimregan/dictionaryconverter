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

package ie.tcd.slscs.itut.RuleConverter

import scala.util.matching.Regex

object ExpandRules {
  //NP | <adj> <n> | <n> <adj> | 1-2 2-1 | agree:1,2 | check_human:1 | big dog | madra mór
  case class Macro(name: String, params: List[Int])
  def stringToMacro(s: String): Option[Macro] = {
    if(s == null || s == "") {
      None
    }
    val in = s.split(":")
    if(in.length != 2) {
      None
    }
    val params = in(1).split(",").map{_.trim}.map{_.toInt}.toList
    Some(Macro(in(0), params))
  }
  implicit def stringToMacroList(s: String): List[Macro] = {
    if(s == null || s == "") {
      List.empty[Macro]
    } else {
      s.split(" ").flatMap{stringToMacro}.toList
    }
  }
  def splitAlignmentsSL(al: String): Map[Int, List[Int]] = {
    def toTuple(i: Array[Int]): (Int, Int) = (i(0), i(1))
    val als = al.split(" ").map {_.split("-").map(_.toInt)}.map{toTuple}
    val almap = als.groupBy(_._1).map { case (k, v) => (k, v.map(_._2).toList) }
    almap
  }
  def splitAlignmentsTL(al: String): Map[Int, List[Int]] = {
    def toTuple(i: Array[Int]): (Int, Int) = (i(1), i(0))
    val als = al.split(" ").map{_.split("-").map(_.toInt)}.map{toTuple}
    val almap = als.groupBy(_._1).map { case (k, v) => (k, v.map(_._2).toList) }
    almap
  }
  abstract class Token(tags: List[String]) {
    def getTags = tags
  }
  case class LemmaToken(lemma: String, tags: List[String]) extends Token(tags)
  case class TagsToken(tags: List[String]) extends Token(tags)
  abstract class AlignedToken(tags: List[String], pos: Int) extends Token(tags)
  case class LemmaAlignedToken(lemma: String, tags: List[String], pos: Int) extends AlignedToken(tags, pos)
  case class TagsAlignedToken(tags: List[String], pos: Int) extends AlignedToken(tags, pos)
  def makeToken(s: String): Token = {
    val withLemma = new Regex("""^([^<]+)<(.*)>$""")
    val tagsOnly = new Regex("""^<(.*)>$""")
    s match {
      case withLemma(lem, tag) => LemmaToken(lem, tag.split("><").toList)
      case tagsOnly(tag) => TagsToken(tag.split("><").toList)
      case _ => throw new Exception("Unexpected value: " + s)
    }
  }
  def diffTags(la: List[String], lb: List[String]): List[String] = {
    val count = la.zip(lb).takeWhile(e => e._1 == e._2).length
    lb.drop(count)
  }
  case class TokenDifference(lemma: String, tags: List[String])
  def diffTokens(a: Token, b: Token): TokenDifference = {
    a match {
      case LemmaToken(l, t) => b match {
        case LemmaToken(ll, tt) => {
          val outlem = if(l != ll) ll else ""
          TokenDifference(outlem, diffTags(t, tt))
        }
        case TagsToken(_) => throw new Exception("Wrong order for comparison")
      }
      case TagsToken(t) => b match {
        case LemmaToken(l, tt) => TokenDifference(l, diffTags(t, tt))
        case TagsToken(tt) => TokenDifference("", diffTags(t, tt))
      }
    }
  }
  def makeTokenList(s: String): List[Token] = s.split(" ").map{makeToken}.toList
  abstract class TrRule(tag: String) {
    def getTag:String = tag
  }
  case class RulePiece(src: List[Token], trg: List[Token],
                       al: Map[Int, List[Int]], srcmac: List[Macro],
                       trgmac: List[Macro], srceg: String, trgeg: String)
  case class MultiPartRule(tag: String, parts: List[RulePiece]) extends TrRule(tag)
  case class Rule(tag: String, src: List[Token], trg: List[Token],
                  al: Map[Int, List[Int]], srcmac: List[Macro],
                  trgmac: List[Macro], srceg: String, trgeg: String) extends TrRule(tag)
  case class TaglessRule(src: List[Token], trg: List[Token],
                  al: Map[Int, List[Int]], srcmac: List[Macro],
                  trgmac: List[Macro], srceg: String, trgeg: String) extends TrRule("")
  abstract class ConvertedRule(tag: String) extends TrRule(tag)
  case class ConvertedSingleRule(tag: String, toks: List[TokenNode]) extends ConvertedRule(tag)
  case class ConvertedMultiRule(tag: String, toks: List[List[TokenNode]]) extends ConvertedRule(tag)
  implicit def TaglessToRulePiece(t: TaglessRule): RulePiece = RulePiece(t.src, t.trg, t.al, t.srcmac, t.trgmac, t.srceg, t.trgeg)
  implicit def RuleToRulePiece(r: Rule): RulePiece = RulePiece(r.src, r.trg, r.al, r.srcmac, r.trgmac, r.srceg, r.trgeg)
  def appendMultiPart(r: TrRule, t: TaglessRule): MultiPartRule = {
    def appendMultiPartR(r: Rule, t: TaglessRule): MultiPartRule = {
      val tag = r.tag
      val first: RulePiece = RulePiece(r.src, r.trg, r.al, r.srcmac, r.trgmac, r.srceg, r.trgeg)
      val second: RulePiece = TaglessToRulePiece(t)
      MultiPartRule(tag, List(first, second))
    }
    def appendMultiPartMP(r: MultiPartRule, t: TaglessRule):MultiPartRule = MultiPartRule(r.tag, r.parts :+ TaglessToRulePiece(t))
    r match {
      case m @ MultiPartRule(_, _) => appendMultiPartMP(m, t)
      case tr @ Rule(_, _, _, _, _, _, _, _) => appendMultiPartR(tr, t)
      case _ => throw new Exception("Unexpected type: " + r)
    }
  }

  def reparentTagless(l: List[TrRule]): List[TrRule] = {
    def reparentInner(l: List[TrRule], acc: List[TrRule]): List[TrRule] = l match {
      case Nil => acc
      case (c @ MultiPartRule(_, _)) :: xs => xs match {
        case (t: TaglessRule) :: xt => reparentInner(List(appendMultiPart(c, t)) ++ xt, acc)
      }
      case (r @ Rule(_, _, _, _, _, _, _, _)) :: xs => xs match {
        case (t: TaglessRule) :: xt => reparentInner(List(appendMultiPart(r, t)) ++ xt, acc)
      }
      case x :: xs => reparentInner(xs, acc :+ x)
    }
    reparentInner(l, List.empty[TrRule])
  }

  implicit def RuleToMultiPart(r: Rule): MultiPartRule = {
    val rp:RulePiece = RulePiece(r.src, r.trg, r.al, r.srcmac, r.trgmac, r.srceg, r.trgeg)
    MultiPartRule(r.tag, List[RulePiece](rp))
  }
  def flipMacro(pos: Int, mac: Macro): Macro = {
    Macro(mac.name, mac.params.map{e => if (e <= pos) e else -e})
  }
  abstract class TokenNode
  case class TerminalToken(pos: Int, align: List[Int], src: Token, trg: List[Token], macros: List[Macro]) extends TokenNode
  case class InsertionTerminalToken(pos: Int, child: Token, macros: List[Macro]) extends TokenNode
  case class DeletionTerminalToken(pos: Int, child: Token, macros: List[Macro]) extends TokenNode
  case class NonTerminalToken(pos: Int, align: Int, src: List[TrRule], macros: List[Macro]) extends TokenNode
  case class NTExpandable(pos: Int, align: Int, toks: List[List[TokenNode]])
  def convertInnerRule(pos: Int, align: Int, rule: TrRule, m: Map[String, List[TrRule]]): ConvertedRule = rule match {
    case TrivialDeletion(tag, toks) => ConvertedSingleRule(tag, List(DeletionTerminalToken(pos, toks.head, List.empty[Macro])))
    case TrivialIdentity(tag, toks) => ConvertedSingleRule(tag, List(TerminalToken(pos, List(align), toks.head, toks, List.empty[Macro])))
    case r @ Rule(tag,_,_,_,_,_,_,_) => ConvertedSingleRule(tag, expandRuleToSausage(r, m, pos, align))
    case MultiPartRule(tag, parts) => ConvertedMultiRule(tag, parts.map{r => expandRuleToSausage(r, m, pos, align)})
  }
  def convertNonTerminal(n: NonTerminalToken): NTExpandable = {
    NTExpandable(n.pos, n.align, List.empty[List[TokenNode]])
  }
  def macroListToMap(l: List[Macro]): Map[Int, List[Macro]] =
    l.map{e => e.params.head -> flipMacro(e.params.head, e)}.groupBy(_._1).map{case (k, v) => k -> v.map{_._2}}
  def realignMacro(m: Macro, pos: Int): Macro = Macro(m.name, m.params.map{e => if(e > 0) e + pos - 1 else e - pos + 1})
  def realignMacros(l: List[Macro], pos: Int) = l.map{e => realignMacro(e, pos)}
  def expandRuleToSausage(r: RulePiece, m: Map[String, List[TrRule]], startpos: Int = 1, startal: Int = 1): List[TokenNode] = {
    val srcMacroMap: Map[Int, List[Macro]] = macroListToMap(r.srcmac)
    val macromap = macroListToMap(r.srcmac)
    def rewriteToken(t: (Token, Int)): TokenNode = {
      val pos = t._2 + startpos - 1
      val tok: Token = t._1
      val isDelete: Boolean = r.al(t._2).length == 1 && r.al(t._2).head == 0
      val isInsert: Boolean = r.al(0).contains(t._2)
      val macros: List[Macro] = realignMacros(macromap(t._2), startpos)
      val align: List[Int] = r.al(t._2).map{e => e + startal - 1}
      val isNT: Boolean = align.size == 1 && tok.getTags.length == 1 && m.contains(tok.getTags.head)
      if(isDelete) {
        DeletionTerminalToken(pos, tok, macros)
      } else if(isInsert) {
        InsertionTerminalToken(pos, tok, macros)
      } else if(isNT) {
        NonTerminalToken(pos, align.head, m(tok.getTags.head), macros)
      } else {
        val trgs: List[Token] = align.map{e => r.trg(e - 1)}
        TerminalToken(pos, align, tok, trgs, macros)
      }
    }
    def filterTokenTL(t: TokenNode): Option[TokenNode] = t match {
      case InsertionTerminalToken(_, _, _) => Some(t)
      case _ => None
    }
    val left = r.src.zipWithIndex.map{e => (e._1, e._2 + 1)}.map{rewriteToken}
    val right = r.trg.zipWithIndex.map{e => (e._1, e._2 + 1)}.map{rewriteToken}.flatMap{filterTokenTL}
    left ++ right
  }
/*
  def expandNodes(l: List[TokenNode]): List[List[TokenNode]] = {
    def expandInner(n: List[TokenNode], acc: List[List[TokenNode]]): List[List[TokenNode]] = n match {
      case Nil => acc
      case TerminalToken(pos, align, tok, trgs, macros) :: xs => if(acc.isEmpty) {
        expandInner(xs, List(List(TerminalToken(pos, align, tok, trgs, macros))))
      } else {
        expandInner(xs, acc.map{ e => e :+ TerminalToken(pos, align, tok, trgs, macros) })
      }
      case NonTerminalToken(pos, align, rules, macros) :: xs => if(acc.isEmpty) {
        expandInner(xs, v)
      } else {
        expandInner(xs, acc.flatMap { e: List[TokenNode] => v.flatMap { f: List[TokenNode] => List(List(e, f).flatten) } })
      }
    }
    expandInner(l, List.empty[List[TokenNode]])
  }*/

  def stringToRule(s: String): TrRule = {
    stringToRule(s.split("\\|").map{_.trim})
  }
  def stringToRule(parts: Array[String]): TrRule = {
    val tag = parts(0)
    val src = makeTokenList(parts(1))
    val trg = makeTokenList(parts(2))
    val srcal = splitAlignmentsSL(parts(3))
    val trgal = splitAlignmentsTL(parts(3))
    val srcmac = stringToMacroList(parts(4))
    val trgmac = stringToMacroList(parts(5))
    val srceg = parts(6)
    val trgeg = parts(7)
    if(tag == "" || tag == null) {
      TaglessRule(src, trg, srcal, srcmac, trgmac, srceg, trgeg)
    } else {
      Rule(tag, src, trg, srcal, srcmac, trgmac, srceg, trgeg)
    }
  }
  abstract class TrivialRule(tag: String) extends TrRule(tag)
  case class TrivialIdentity(tag: String, toks: List[Token]) extends TrivialRule(tag)
  case class TrivialDeletion(tag: String, toks: List[Token]) extends TrivialRule(tag)
  def makeTrivialRule(parts: Array[String]): TrivialRule = {
    if(parts(2) == "1-0") {
      TrivialDeletion(parts(0), makeTokenList(parts(1)))
    } else if(parts(2) == "1-1") {
      TrivialIdentity(parts(0), makeTokenList(parts(1)))
    } else {
      throw new Exception("Only 1-1 and 1-0 (deletion) alignments are currently handled")
    }
  }
  def mkRuleMap(in: List[TrRule]): Map[String, List[TrRule]] = in.map{e => (e.getTag, e)}.groupBy(_._1).mapValues(_.map(_._2))

  def offsetPair(a: (Int, Int), b: (Int, Int)): (Int, Int) = (a._1 - 1 + b._1, a._2 - 1 + b._2)
  def checkSimpleAlignments(m: Map[Int, List[Int]]): Boolean = m.count{e => e._2.length == 1} == m.size
  def simplifyAlignments(m: Map[Int, List[Int]]): Option[Map[Int, Int]] = if(checkSimpleAlignments(m)) {
    Some(m.map{e => (e._1, e._2(0))})
  } else {
    None
  }
  def tokenIsBasis(t: Token): Boolean = t match {
    case LemmaToken(_, _) => false
    case LemmaAlignedToken(_, _, _) => false
    case TagsToken(l) => l.size == 1
    case TagsAlignedToken(l, _) => l.size == 1
    case _ => throw new Exception("Unexpected type")
  }
  def offsetMacro(m: Macro, offset: Int): Macro = Macro(m.name, m.params.map{e => e + offset})
  def offsetMacroList(l: List[Macro], offset: Int): List[Macro] = l.map{e => offsetMacro(e, offset)}
}
