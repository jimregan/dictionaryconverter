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
import ie.tcd.slscs.itut.RuleConverter.TextMacro._

import scala.collection.JavaConverters._

case class TextRuleMgrWrapper(trm: TextRuleManager) {
  val lists: Map[String, List[String]] = trm.getLists.asScala.map{convertSimpleList}.toMap
  def listsToXML: List[DefListElement] = lists.map(e => listToXML(e._1, e._2)).toList
  val cats: Map[String, List[String]] = trm.getCategories.asScala.map{convertSimpleCats}.toMap
  val catItems: Map[String, List[CatItem]] = cats.map{e => (e._1, e._2.map{f => CatItem(f, null, null)})}
  val defaultAttribs: Map[String, String] = getDefaultAttributes(trm.getTargetAttr.asScala.toList)
  val transferType: String = trm.getTypeText
  val clippables: Map[String, Map[String, Boolean]] = convertAttributeSequenceClippable(trm.getClippable)
  val clippablesChunk: Map[String, Map[String, Boolean]] = convertAttributeSequenceClippable(trm.getClippableChunk)
  val sourceSeq: Map[String, List[String]] = trm.getSourceSeq.asScala.map{convertAttributeSequence}.toMap
  val targetSeq: Map[String, List[String]] = trm.getTargetSeq.asScala.map{convertAttributeSequence}.toMap
  def getSeq(s: String): List[String] = if(targetSeq.get(s).isEmpty) List.empty[String] else targetSeq(s)
  val sourceMacros: List[DefMacroElement] = trm.getMacros.asScala.map{e => convertJSTMacroToXML(e, clippables)}.toList
  def macrosToXML = sourceMacros
  val sourceSeqChunk: Map[String, List[String]] = trm.getSourceSeqChunk.asScala.map{convertAttributeSequence}.toMap
  val targetSeqChunk: Map[String, List[String]] = trm.getTargetSeqChunk.asScala.map{convertAttributeSequence}.toMap
  val rules: List[RuleHolder] = trm.getRules.asScala.map{e => RuleHolder.convertRuleContainer(e, catItems)}.toList
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
  def convertAttributeSequenceClippable(in: AttributeSequenceClippable): Map[String, Map[String, Boolean]] = {
    in.getClippable.asScala.toMap.map{e => (e._1.toString, e._2.asScala.toMap.map{f => (f._1.toString, f._2.booleanValue)})}
  }
  def asClippableLookup(clip: AttributeSequenceClippable, pos: String, attseq: String): Boolean = {
    clip.getClippable.get(pos).get(attseq)
  }
  def listToXML(s: String, l: List[String]): DefListElement = {
    val children = l.map{e => ListItemElement(e)}
    DefListElement(s, children)
  }
}
