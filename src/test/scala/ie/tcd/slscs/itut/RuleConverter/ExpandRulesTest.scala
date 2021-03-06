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

import org.scalatest.FlatSpec
import ie.tcd.slscs.itut.RuleConverter.ExpandRules.{Macro, Rule, TagsToken, Token, TrivialDeletion, TrivialIdentity}

class ExpandRulesTest extends FlatSpec {
  "splitAlignmentsSL" should "generate a map of SL to TL alignments" in {
    val al1 = "1-1 1-2"
    val al1out = ExpandRules.splitAlignmentsSL(al1)
    assert (al1out(1) === Array(1, 2))
    val al2 = "1-1 2-3 3-2"
    val al2out = ExpandRules.splitAlignmentsSL(al2)
    assert (al2out(1) === Array(1))
    assert (al2out(2) === Array(3))
    assert (al2out(3) === Array(2))
  }

  "splitAlignmentsTL" should "generate a map of TL to SL alignments" in {
    val al1 = "1-1 1-2"
    val al1out = ExpandRules.splitAlignmentsTL(al1)
    assert (al1out(1) === Array(1))
    assert (al1out(2) === Array(1))
    val al2 = "1-1 2-2 3-2"
    val al2out = ExpandRules.splitAlignmentsTL(al2)
    assert (al2out(1) === Array(1))
    assert (al2out(2) === Array(2, 3))
  }
  "makeTrivialRule" should "make trivial rules" in {
    val expdel = TrivialDeletion("GEN", List[TagsToken](TagsToken(List[String]("gen"))))
    val expident = TrivialIdentity("comma", List[TagsToken](TagsToken(List[String]("cm"))))
    val delin = Array("GEN", "<gen>", "1-0")
    val identin = Array("comma", "<cm>", "1-1")
    val outdel = ExpandRules.makeTrivialRule(delin)
    val outident = ExpandRules.makeTrivialRule(identin)
    assert (expdel === outdel)
    assert (expident === outident)
  }

  "stringToRule" should "expand string to rule" in {
    val testin = "NP | <adj> <n> | <n> <adj> | 1-2 2-1 | agree:1,2 | check_human:1 | big dog | madra mór"
    val tag = "NP"
    val al = Map(1 -> List(2), 2 -> List(1))
    val ltok: List[Token] = List(TagsToken(List("adj")), TagsToken(List("n")))
    val rtok: List[Token] = List(TagsToken(List("n")), TagsToken(List("adj")))
    val lmac = List(Macro("agree", List(1, 2)))
    val rmac = List(Macro("check_human", List(1)))
    val leg = "big dog"
    val reg = "madra mór"

    val exp = Rule(tag, ltok, rtok, al, lmac, rmac, leg, reg)
    val out = ExpandRules.stringToRule(testin)
    assert(exp == out)
  }

  "stringToRule2" should "expand string to rule" in {
    val testin = "NP | <adj> <n> | <n> <adj> | 1-2 2-1 | |  | big dog | madra mór"
    val tag = "NP"
    val al = Map(1 -> List(2), 2 -> List(1))
    val ltok: List[Token] = List(TagsToken(List("adj")), TagsToken(List("n")))
    val rtok: List[Token] = List(TagsToken(List("n")), TagsToken(List("adj")))
    val lmac = List.empty[Macro]
    val rmac = List.empty[Macro]
    val leg = "big dog"
    val reg = "madra mór"

    val exp = Rule(tag, ltok, rtok, al, lmac, rmac, leg, reg)
    val out = ExpandRules.stringToRule(testin)
    assert(exp == out)
  }

  "tokenIsBasis" should "check if token is basis for rule" in {
    assert(true == ExpandRules.tokenIsBasis(TagsToken(List("n"))))
    assert(false == ExpandRules.tokenIsBasis(TagsToken(List("n", "m"))))
  }
}
