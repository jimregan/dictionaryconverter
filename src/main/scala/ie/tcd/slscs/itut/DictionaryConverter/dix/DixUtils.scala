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

package ie.tcd.slscs.itut.DictionaryConverter.dix

object DixUtils {
  def isTag(t: TextLike): Boolean = t match {
    case S(_, _) => true
    case _ => false
  }
  def isNotTag(t: TextLike): Boolean = t match {
    case S(_, _) => false
    case _ => true
  }
  def isSimpleEntry(entry: E): Boolean = entry.children match {
    case P(_, _) :: nil => true
    case _ => false
  }
  def makeSimpleBilEntry(src: String, srctags: String, trg: String, trgtags: String) = {
    val leftish: List[TextLike] = List[TextLike](Txt(src)) ++ srctags.split("\\.").map{e => S(e)}
    val rightish: List[TextLike] = List[TextLike](Txt(trg)) ++ trgtags.split("\\.").map{e => S(e)}
    E(List(P(L(leftish), R(rightish))))
  }
  def swapSimpleBilEntry(e: E): E = {
    if(!isSimpleEntry(e)) {
      e
    } else {
      val r = if(e.r == "LR") "RL" else if (e.r == "RL") "LR" else null
      val lcontent = e.children(0).getContent
      val rcontent = e.children(1).getContent
      val children = List(P(L(rcontent), R(lcontent)))
      E(children, e.lm, r, e.a, e.c, e.i, e.srl, e.slr, e.alt, e.v, e.vr, e.vl)
    }
  }
  def nonTagTextPiece(t: TextLike): Boolean = t match {
    case Txt(_) => true
    case Entity(_) => true
    case G(_) => true
    case B() => true
    case J() => true
    case Prm() => true
    case S(_, _) => false
    case _ => false
  }
  def getTextPieces(t: TextLikeContainer): String = t.getContent.takeWhile{nonTagTextPiece}.map{_.asText}.mkString
}
