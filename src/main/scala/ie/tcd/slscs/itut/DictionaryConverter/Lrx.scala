/*
 *  The MIT License (MIT)
 *
 *  Copyright © 2017 Trinity College, Dublin
 *  Irish Speech and Language Technology Research Centre
 *  Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 *  An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package ie.tcd.slscs.itut.DictionaryConverter

import ie.tcd.slscs.itut.DictionaryConverter.dix.DixUtils

import scala.xml.Node

class Lrx {
  trait RulePart
  case class Or(children: List[Match]) extends RulePart
  trait Match extends RulePart
  case class SelectMatch(lemma: String = null, tags: String = null, select: Select) extends Match
  case class ItemMatch(lemma: String = null, tags: String = null) extends Match
  case class EmptyMatch() extends Match
  case class Select(lemma: String, tags: String)
  case class Rule(weight: String, children: List[RulePart])

  /**
   * Get attribute s from Node n
   */
  private def getattrib(n: Node, s: String): String = {
    val attr = n.attribute(s).getOrElse(scala.xml.Text(""))
    if (attr.text != "") {
      attr.text
    } else {
      null
    }
  }

  def nodeToMatch(n: Node): Match = n match {
    case <match/> => {
      val lemma = getattrib(n, "lemma")
      val tags = getattrib(n, "tags")
      if(lemma == null && tags == null) {
        EmptyMatch()
      } else {
        ItemMatch(lemma, tags)
      }
    }
    case <match><select/></match> => {
      val lemma = getattrib(n, "lemma")
      val tags = getattrib(n, "tags")
      if(n.child.size != 1) {
        throw new Exception("<match> contains more than one child: " + n.toString())
      }
      val sellemma = getattrib(n.child.head, "lemma")
      val seltags = getattrib(n.child.head, "tags")
      SelectMatch(lemma, tags, Select(sellemma, seltags))
    }
    case _ => throw new Exception("Expected <match>")
  }

  def selectToENoSlr(s: SelectMatch, lr: Boolean = true): dix.E = {
    if(lr) {
      val e = DixUtils.makeSimpleBilEntry(s.lemma + "__" + s.select.lemma, s.tags, s.select.lemma, s.select.tags)
      dix.E(e.children, null, "LR")
    } else {
      val e = DixUtils.makeSimpleBilEntry(s.select.lemma, s.select.tags, s.lemma + "__" + s.select.lemma, s.tags)
      dix.E(e.children, null, "RL")
    }
  }
  def selectToE(s: SelectMatch, lr: Boolean = true, slr: Boolean = false): dix.E = {
    if(slr) {
      selectToENoSlr(s, lr)
    } else {
      if(lr) {
        val e = DixUtils.makeSimpleBilEntry(s.lemma, s.tags, s.select.lemma, s.select.tags)
        dix.E(e.children, null, null, null, null, false, null, s.select.lemma)

      } else {
        val e = DixUtils.makeSimpleBilEntry(s.select.lemma, s.select.tags, s.lemma, s.tags)
        dix.E(e.children, null, null, null, null, false, null, null, s.select.lemma)
      }
    }
  }
}
