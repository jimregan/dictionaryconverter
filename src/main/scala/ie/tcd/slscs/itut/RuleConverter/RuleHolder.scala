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

import ie.tcd.slscs.itut.ApertiumStream._
import ie.tcd.slscs.itut.ApertiumTransfer.Text.{RuleSide, RuleContainer => JRuleContainer, SimpleMacroCall => JSMacroCall}
import ie.tcd.slscs.itut.ApertiumTransfer.{AttrItem, DefAttr, Pattern, CatItem => JCatItem, DefCat => JDefCat, DefCats => JDefCats, PatternItem => JPatternItem}
import ie.tcd.slscs.itut.RuleConverter.TrxProc.{JPatternItemToPatternItem, PatternToPatternElement}

import scala.collection.JavaConverters._

case class RuleHolder() {

}
object RuleHolder {
  trait StreamItem
  trait LexicalUnit extends StreamItem
  trait SingleLexicalUnit extends LexicalUnit
  case class LUProc(lemh: String, lemq: String, tags: List[String]) extends SingleLexicalUnit
  def wordTokenToLUProc(wt: WordToken): LUProc = LUProc(wt.getLemh, wt.getLemq, wt.getTags.asScala.toList)
  case class SimpleLU(lemh: String, lemq: String, tags: List[String]) extends SingleLexicalUnit
  def simpleTokenToSimpleLU(st: SimpleToken): SimpleLU = SimpleLU(st.getLemh, st.getLemq, st.getTags.asScala.toList)
  case class LURef(index: Int) extends SingleLexicalUnit
  def luReferenceToLURef(ref: LUReference): LURef = LURef(ref.position)
  case class MLU(children: List[LURef]) extends LexicalUnit
  def mluReferenceToMLU(mlu: MLUReference): MLU = MLU(mlu.getChildren.asScala.map{luReferenceToLURef}.toList)
  case class Chunk(lemma: String, tags: List[String], contents: List[StreamItem]) extends LexicalUnit
  def chunkTokenToChunk(ch: ChunkToken): Chunk = Chunk(ch.getLemma, ch.getTags.asScala.toList, ch.getChildren.asScala.map{convertStreamToken}.toList.flatten)
  case class RuleBody(lexicalUnits: List[LUProc], contents: List[StreamItem])
  def convertRuleSideToRuleBody(rs: RuleSide): RuleBody = {
    RuleBody(rs.getLUs.asScala.map{wordTokenToLUProc}.toList,
      rs.getTokens.asScala.map{convertStreamToken}.toList.flatten)
  }
  case class Blank() extends StreamItem
  case class PositionBlank(pos: Int) extends StreamItem
  def positionedBlankToPositionBlank(b: PositionedBlank): PositionBlank = PositionBlank(b.getPosition)
  def convertBlanks(blankToken: BlankToken): Option[Blank] = {
    if(blankToken.getContent == null || blankToken.getContent == "") {
      return None
    } else {
      return Some(Blank())
    }
  }
  // a list of SimpleLUs, from SimpleToken, have implicit blanks - but this is already handled!
  def simpleStreamDropLastBlank(l: List[StreamItem]): List[StreamItem] = {
    def dropLastBlank(l: List[StreamItem], acc: List[StreamItem]): List[StreamItem] = l match {
      case SimpleLU(a,b,c) :: Nil => acc :+ SimpleLU(a,b,c)
      case PositionBlank(b) :: Nil => acc
      case SimpleLU(a,b,c) :: xs => dropLastBlank(xs, acc :+ SimpleLU(a,b,c))
      case PositionBlank(b) :: xs =>  dropLastBlank(xs, acc :+ PositionBlank(b))
      case _ => throw new Exception("Unexpected class")
    }
    dropLastBlank(l, List.empty[StreamItem])
  }
  def listSimpleToStream(l: List[SimpleLU]): List[StreamItem] = {
    simpleStreamDropLastBlank(l.zipWithIndex.map{e => List(e._1, PositionBlank(e._2 + 1))}.flatten)
  }
  def convertStreamToken(st: StreamToken): Option[StreamItem] = st match {
    case c: ChunkToken => Some(chunkTokenToChunk(c))
    case b: BlankToken => convertBlanks(b)
    case m: MLUReference => Some(mluReferenceToMLU(m))
    case w: WordToken => Some(wordTokenToLUProc(w))
    case l: LUReference => Some(luReferenceToLURef(l))
  }

  case class SimpleMacroCall(name: String, params: List[String])
  def convertMacroCalls(in: JSMacroCall): SimpleMacroCall = {
    SimpleMacroCall(in.getName, in.getParams.asScala.toList)
  }
  def convertMacroCallToCallMacro(in: SimpleMacroCall): CallMacroElement = {
    CallMacroElement(in.name, in.params.map{e => WithParamElement(e)})
  }
  implicit def catItemToJCatItem(c: CatItem): JCatItem = if(c.name == "") new JCatItem(c.lemma, c.tags) else new JCatItem(c.name)
  implicit def DefCatTupleToJDefCat(dc: (String, List[CatItem])): JDefCat = new JDefCat(dc._1, dc._2.map{catItemToJCatItem}.asJava)
  implicit def DefCatsToJava(m: Map[String, List[CatItem]]): JDefCats = new JDefCats(m.toList.map{DefCatTupleToJDefCat}.asJava)
  implicit def JPatternItemToPatternItem(pi: JPatternItem): PatternItemElement = PatternItemElement(pi.getName)
  implicit def PatternToPatternElement(p: Pattern): PatternElement = PatternElement(p.getItems.asScala.map{JPatternItemToPatternItem}.toList)
  def convertRuleSideToPattern(rs: RuleSide, m: Map[String, List[CatItem]]): PatternElement = PatternToPatternElement(RuleSide.toPattern(rs, m))

}