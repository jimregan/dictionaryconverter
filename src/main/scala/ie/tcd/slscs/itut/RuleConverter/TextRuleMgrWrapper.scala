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

import ie.tcd.slscs.itut.ApertiumTransfer.Text._
import ie.tcd.slscs.itut.RuleConverter.TextRuleMgrWrapper._

import scala.collection.JavaConverters._

case class TextRuleMgrWrapper(trm: TextRuleManager) {
  def getLists = trm.getLists.asScala.map{convertSimpleList}.toMap
  def getCats = trm.getCategories.asScala.map{convertSimpleCats}.toMap
  val defaultAttribs: Map[String, String] = getDefaultAttributes(trm.getTargetAttr.asScala.toList)
  val transferType = trm.getTypeText
  val clippables = TextMacro.convertAttributeSequenceClippable(trm.getClippable)
  val clippablesChunk = TextMacro.convertAttributeSequenceClippable(trm.getClippableChunk)
  val sourceSeq: Map[String, List[String]] = trm.getSourceSeq.asScala.map{convertAttributeSequence}.toMap
  val targetSeq: Map[String, List[String]] = trm.getTargetSeq.asScala.map{convertAttributeSequence}.toMap
  val sourceSeqChunk: Map[String, List[String]] = trm.getSourceSeqChunk.asScala.map{convertAttributeSequence}.toMap
  val targetSeqChunk: Map[String, List[String]] = trm.getTargetSeqChunk.asScala.map{convertAttributeSequence}.toMap
}
object TextRuleMgrWrapper {
  def apply(arr: Array[String]): TextRuleMgrWrapper = {
    val trm: TextRuleManager = new TextRuleManager()
    TextRuleMgrWrapper(trm.getTextFileBuilder.buildFromStringArray(arr))
  }
  def convertAttributeSequence(as: AttributeSequence): (String, List[String]) = {
    (as.getName, as.getTags.asScala.toList)
  }
  def getDefaultAttributes(l: List[Attributes]): Map[String, String] = {
    l.map{e => (e.getName, e.getUndefined)}.toMap
  }
  def convertSimpleCats(in: SimpleCats): (String, List[String]) = (in.getName, in.getItems.asScala.toList)
  def convertSimpleList(in: SimpleList): (String, List[String]) = (in.getName, in.getItems.asScala.toList)
}
