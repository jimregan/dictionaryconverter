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

import ie.tcd.slscs.itut.ApertiumStream.{ChunkToken, WordToken}
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
  /*
  TODO
  Inner tags for chunk:

   */
  def mkChunkTagTransfer(tag: String, pos: String, map: Map[String, String]): TagElement = {
    if(tag.startsWith("*")) {
      val name = tag.substring(1)
      if(map.contains(name)) {
        TagElement(ClipElement(pos, "tl", map.get(name).get, null, null, null))
      } else {
        TagElement(LitTagElement(name))
      }
      val part = map.getOrElse(name, name)
    } else {
      TagElement(LitTagElement(tag))
    }
  }
  def mkChunkTagsTransfer(list: List[String], pos: String, map: Map[String, String]): TagsElementType = {
    TagsElementType(list.map{e => mkChunkTagTransfer(e, pos, map)})
  }
  def mkChunkTransfer(chunkToken: ChunkToken, pos: String, map: Map[String, String]): ChunkElement = {
    val name = chunkToken.getLemma
    val tags = mkChunkTagsTransfer(chunkToken.getTags.asScala.toList, pos, map)
    ChunkElement(name, null, null, null, Some(tags), List.empty[ValueElement])
  }

//  def dixSectionToChoose(sect: Section): ChooseElement = {
//  }
  def isSimpleEntry(entry: E): Boolean = entry.children match {
    case P(_, _) :: nil => true
    case x :: xs => false
    case x :: nil => false
    case _ => false
  }
  def nonTagTextPiece(t: TextLike): Boolean = t match {
    case Txt(_) => true
    case Entity(_) => true
    case G(_) => true
    case B() => true
    case J() => true
    case Prm() => true
    case S(_) => false
    case _ => false
  }

  def getTextPieces(t: TextLikeContainer): String = t.getContent.takeWhile{nonTagTextPiece}.map{_.asText}.mkString
  def mkSLLemmaTest(s: String, clip: String, caseless: Boolean = true): TestElement = {
    TestElement(null, EqualElement(caseless, List[ValueElement](ClipElement(clip, "sl", "lemma", null, null, null), LitElement(s))))
  }
  def mkTLLemmaLet(s: String, clip: String): LetElement = {
    LetElement(ClipElement(clip, "tl", "lemma", null, null, null), LitElement(s))
  }
  // TODO: only checks the text. If even.
  def dixEntryToWhen(entry: E, clip: String, attrs: Map[String, String]): WhenElement = entry.children match {
    case P(l, r) :: nil => {
      val ltxt = getTextPieces(l)
      val ltag = l.content.dropWhile{DixUtils.isNotTag}.takeWhile{DixUtils.isTag}
      val rtxt = getTextPieces(r)
      val rtag = r.content.dropWhile{DixUtils.isNotTag}.takeWhile{DixUtils.isTag}
      val ltxtck = mkSLLemmaTest(ltxt, clip)
      val rtxtck = mkTLLemmaLet(rtxt, clip)
      WhenElement(null, ltxtck, List[SentenceElement](rtxtck))
    }
  }
}

