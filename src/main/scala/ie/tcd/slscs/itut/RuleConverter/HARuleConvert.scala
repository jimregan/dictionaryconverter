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

import ie.tcd.slscs.itut.RuleConverter.ExpandRules.{Rule, Token}

import scala.util.matching.Regex

class HARuleConvert {
  abstract class SimpleCat
  case class LemmaCat(l: String, t: List[String]) extends SimpleCat
  case class TagsCat(t: List[String]) extends SimpleCat
  def SimpleCatFromString(s: String): SimpleCat = {
    val withLemma = new Regex("""^([^<]+)<(.*)>$""")
    val tagsOnly = new Regex("""^<(.*)>$""")
    s match {
      case withLemma(lem, tag) => LemmaCat(lem, tag.split("><").toList)
      case tagsOnly(tag) => TagsCat(tag.split("><").toList)
      case _ => throw new Exception("Unexpected value: " + s)
    }
  }
  def SimpleCatsFromString(s: String): (String, List[SimpleCat]) = {
    val parts = s.split("=").map{_.trim}
    if(parts.length != 2) {
      throw new Exception("Can only contain one '='")
    }
    (parts(0), parts(1).split(" ").map{SimpleCatFromString}.toList)
  }
  case class DataContainer(mode: String, cats: Map[String, SimpleCat])

  def ruleToXML(r: Rule, dc: DataContainer): RuleElement = {
    def tokenToPatternItem(t: Token): PatternItemElement = {
      return null
    }
    return null
  }

}

class ScratchPad {
  abstract class Node
  case class TNode(v: String) extends Node
  case class NTNode(v: List[List[Node]]) extends Node

  def mkNodes(l: List[String], b: Map[String,List[List[Node]]]): List[Node] = {
    def mkNode(s: String) = if(b.contains(s)) {
      NTNode(b(s))
    } else {
      TNode(s)
    }
    l.map{mkNode}
  }

  def toNodes(b: Map[String, List[List[String]]]): Map[String, List[List[Node]]] = b.map{e => (e._1, e._2.map{_.map{g => TNode(g)}})}

  def expandNodes(l: List[Node]): List[List[Node]] = {
    def expandInner(n: List[Node], acc: List[List[Node]]): List[List[Node]] = n match {
      case Nil => acc
      case TNode(s) :: xs => if(acc.isEmpty) {
        expandInner(xs, List(List(TNode(s))))
      } else {
        expandInner(xs, acc.map{ e => e :+ TNode(s) })
      }
      case NTNode(v) :: xs => if(acc.isEmpty) {
        expandInner(xs, v)
      } else {
        // instead of 'List(List(e, f).flatten)', a function can be used to do this part, also adjusting positions
        // probably need to stick all relevant parts into each node, then reorder TL side as a postprocessing step
        // maybe also keep track of initial position and final position, for macro adjustment
        // insert in the transformer function, create a map in a second pass, then adjust classes with proper positions
        // case class Foo(d: data, pos: Int) -> FooX(d, initPos, newPos) -> Foo(d, newPos)
        expandInner(xs, acc.flatMap { e: List[Node] => v.flatMap { f: List[Node] => List(List(e, f).flatten) } })
      }
    }
    expandInner(l, List.empty[List[Node]])
  }
}
