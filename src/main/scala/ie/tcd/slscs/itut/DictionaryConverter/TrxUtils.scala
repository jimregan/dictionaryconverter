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

import ie.tcd.slscs.itut.ApertiumStream.WordToken
import ie.tcd.slscs.itut.DictionaryConverter.dix._

import scala.collection.JavaConverters._

object TrxUtils {
  def defvarToLet(defvar: DefVarElement): LetElement = {
    val to = if(defvar.value == null) "" else defvar.value
    val name = defvar.name
    LetElement(VarElement(name), LitElement(to))
  }
  def mkResetVarsMacro(vars: List[DefVarElement], name: String = "resetVars") = {
    val out = vars.map{defvarToLet}
    DefMacroElement(name, "0", null, out)
  }
  def WordTokenToLiteralLU(wt: WordToken): LUElement = {
    val lemhlit = LitElement(wt.getLemh)
    val lemqlit = LitElement(wt.getLemq)
    val tags = wt.getTags.asScala.toList.map{e => LitTagElement(e)}
    val outlist = List[StringValueElement](lemhlit) ++ tags
    if(wt.getLemq != "") {
      LUElement(outlist ++ List[StringValueElement](lemqlit))
    } else {
      LUElement(outlist)
    }
  }

//  def dixSectionToChoose(sect: Section): ChooseElement = {

//  }
  def isSimpleEntry(entry: E): Boolean = entry.children match {
    case P(_, _) :: nil => true
    case _ => false
  }
  def nonTagTextPiece(t: TextLike): Boolean = t match {
    case Txt(_) => true
    case G(_) => true
    case S(_) => false
    case _ => false
  }
  def getTextFromTextLike(t: TextLike): String = t match {
    case Txt(t) => t
    case B() => " "
    case Entity(t) => t
    case G(l) => "# " + l.map{getTextFromTextLike}.mkString
    case _ => throw new Exception("Not a text-like piece")
  }

  def dixEntryToWhen(entry: E, clip: String, attrs: Map[String, String]): WhenElement = entry.children match {
    case P(l, r) :: nil => {
      val ltxt = l.content.takeWhile{nonTagTextPiece}.map{getTextFromTextLike}.mkString
      val ltag = l.content.dropWhile{DixUtils.isNotTag}.takeWhile{DixUtils.isTag}
      val rtxt = r.content.takeWhile{nonTagTextPiece}.map{getTextFromTextLike}.mkString
      val rtag = r.content.dropWhile{DixUtils.isNotTag}.takeWhile{DixUtils.isTag}
      val ltxtck = TestElement(null, EqualElement(true, List[ValueElement](ClipElement(clip, "sl", "lemma", null, null, null), LitElement(ltxt))))
      val rtxtck = LetElement(ClipElement(clip, "tl", "lemma", null, null, null), LitElement(rtxt))
    }
  }
}
