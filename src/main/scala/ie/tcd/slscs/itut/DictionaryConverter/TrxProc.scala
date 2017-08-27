/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Jim O'Regan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
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
package ie.tcd.slscs.itut.DictionaryConverter

import ie.tcd.slscs.itut.ApertiumStream._
import ie.tcd.slscs.itut.ApertiumTransfer.Text.{AttributeSequence, AttributeSequenceClippable, Attributes, RuleSide, SimpleCats, SimpleList, SimpleTextMacroAttr, TextRuleManager, SimpleMacroCall => JSMacroCall, SimpleTextMacro => JSTMacro, SimpleTextMacroEntry => JSTMEntry}
import ie.tcd.slscs.itut.ApertiumTransfer.{AttrItem, DefAttr, Pattern, CatItem => JCatItem, DefCat => JDefCat, DefCats => JDefCats, PatternItem => JPatternItem}
import ie.tcd.slscs.itut.DictionaryConverter.TrxProc.RuleBody

import scala.collection.JavaConverters._

trait RuleContainer
case class RuleElementContainer(re: RuleElement) extends RuleContainer

case class TrxProc(kind: String,
                   var defcats: Map[String, List[CatItem]],
                   var defattrs: Map[String, List[String]],
                   var vars: Map[String, String],
                   var lists: Map[String, List[String]],
                   var macros: Map[String, List[SentenceElement]],
                   var rules: List[RuleContainer]) {
  private val categories = collection.mutable.Map.empty[String, List[CatItem]] ++ defcats
  def validCategories = categories.keys.toList
  def getCat(s: String): Option[List[CatItem]] = categories.get(s)
  def hasCat(s: String): Boolean = categories.contains(s)
  def setCat(s: String, v: List[CatItem]) {
    categories(s) = v
  }
  def safeSetCat(s: String, v: List[CatItem]) {
    if(!hasCat(s)) {
      setCat(s, v)
    }
  }
  def getCatMap = categories
  def safeSetCats(cats: Map[String, List[CatItem]]) {
    cats.map{e => safeSetCat(e._1, e._2)}
  }
  def addToCat(s: String, c: CatItem) {
    if(!hasCat(s)) {
      categories.put(s, List(c))
    } else {
      val tmp = getCat(s).get :+ c
      setCat(s, tmp)
    }
  }
  private val attributes = collection.mutable.Map.empty[String, List[String]] ++ defattrs
  def validAttributes = attributes.keys.toList
  def getAttr(s: String): Option[List[String]] = attributes.get(s)
  def hasAttr(s: String): Boolean = attributes.contains(s)
  def setAttr(s: String, v: List[String]) {
    attributes(s) = v
  }
  def safeSetAttr(s: String, v: List[String]) {
    if(!hasAttr(s)) {
      safeSetAttr(s, v)
    }
  }
  def getAttrMap = attributes
  def safeSetAttrs(attrs: Map[String, List[String]]) {
    attrs.map{e => safeSetAttr(e._1, e._2)}
  }
  def addToAttr(s: String, a: String) {
    if(!hasAttr(s)) {
      attributes.put(s, List(a))
    } else {
      val tmp = getAttr(s).get :+ a
      setAttr(s, tmp)
    }
  }
  private val variables = collection.mutable.Map.empty[String, String] ++ vars
  def validVariables: List[String] = variables.keys.toList
  def getVar(s: String): Option[String] = variables.get(s)
  def hasVar(s: String): Boolean = variables.contains(s)
  def setVar(s: String, v: String) {
    variables(s) = v
  }
  def safeSetVar(s: String, v: String) {
    if(!hasVar(s)) {
      setVar(s, v)
    }
  }
  def getVarMap = variables
  def safeSetVars(vars: Map[String, String]) {
    vars.map{e => safeSetVar(e._1, e._2)}
  }
  private val _lists = collection.mutable.Map.empty[String, List[String]] ++ lists
  def validLists: List[String] = _lists.keys.toList
  def getList(s: String): Option[List[String]] = _lists.get(s)
  def hasList(s: String): Boolean = _lists.contains(s)
  def setList(s: String, v: List[String]) {
    _lists(s) = v
  }
  def safeSetList(s: String, v: List[String]) {
    if(!hasList(s)) {
      setList(s, v)
    }
  }
  def safeSetLists(lists: Map[String, List[String]]) {
    lists.map{e => safeSetList(e._1, e._2)}
  }
  def getLists: List[List[String]] = _lists.values.toList
  def getListsMap = _lists
}
object TrxProc {
  implicit def parentRuleElement(re: List[RuleElement]): List[RuleContainer] = re.map{e => RuleElementContainer(e)}
  def fromTopLevel(t: TopLevel): TrxProc = {
    val dc = t.defcats.map{e => (e.n, e.l)}.toMap
    val da = t.defattrs.map{e => (e.n, e.l.map{_.tags})}.toMap
    val dv = t.vars.map{e => (e.name, e.value)}.toMap
    val dl = t.lists.map{e => (e.name, e.items.map{_.value})}.toMap
    val dm = t.macros.map{e => (e.name, e.actions)}.toMap
    TrxProc(t.kind, dc, da, dv, dl, dm, t.rules)
  }
  def convertCatItem(in: JCatItem): CatItem = CatItem(in.getTags, in.getLemma, in.getName)
  def convertCatItems(in: JDefCats): Map[String, List[CatItem]] = in.getCategories.asScala.map{convertDefCat}.toMap
  def convertDefCat(in: JDefCat): (String, List[CatItem]) = (in.getName, in.getItems.asScala.toList.map{convertCatItem})
  def convertSimpleList(in: SimpleList): (String, List[String]) = (in.getName, in.getItems.asScala.toList)
  def convertSimpleCats(in: SimpleCats): (String, List[String]) = (in.getName, in.getItems.asScala.toList)
  def convertAttrItem(in: AttrItem): AttrItemElement = AttrItemElement(in.getTags)
  def convertDefAttr(in: DefAttr): DefAttrElement = DefAttrElement(in.getName, in.getItems.asScala.map{convertAttrItem}.toList)

  def merge(a: TopLevel, b: TopLevel): TrxProc = {
    val tmpa = fromTopLevel(a)
    val tmpb = fromTopLevel(b)
    val out = merge(tmpa, tmpb)
    out
  }
  def merge(a: TrxProc, b: TrxProc): TrxProc = {
    val out = a
    out.safeSetCats(b.getCatMap.toMap)
    out.safeSetAttrs(b.getAttrMap.toMap)
    out.safeSetVars(b.getVarMap.toMap)
    out.safeSetLists(b.getListsMap.toMap)
    out
  }
  def mkDefAttr(name: String, attrs: List[String]): DefAttrElement = {
    DefAttrElement(name, attrs.map{e => AttrItemElement(e)})
  }
  def mkDefVar(name: String, value: String): DefVarElement = DefVarElement(name, value)
  def patternToStringList(p: PatternElement): List[String] = p.children.map{_.n}

  case class RuleMetadata(ruleid: String, rulecomment: String)
  case class RuleProc(meta: RuleMetadata)
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
  implicit def catItemToJCatItem(c: CatItem): JCatItem = if(c.name == "") new JCatItem(c.lemma, c.tags) else new JCatItem(c.name)
  implicit def DefCatTupleToJDefCat(dc: (String, List[CatItem])): JDefCat = new JDefCat(dc._1, dc._2.map{catItemToJCatItem}.asJava)
  implicit def DefCatsToJava(m: Map[String, List[CatItem]]): JDefCats = new JDefCats(m.toList.map{DefCatTupleToJDefCat}.asJava)
  implicit def JPatternItemToPatternItem(pi: JPatternItem): PatternItemElement = PatternItemElement(pi.getName)
  implicit def PatternToPatternElement(p: Pattern): PatternElement = PatternElement(p.getItems.asScala.map{JPatternItemToPatternItem}.toList)
  def convertRuleSideToPattern(rs: RuleSide, m: Map[String, List[CatItem]]): PatternElement = PatternToPatternElement(RuleSide.toPattern(rs, m))
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
  def convertAttributeSequence(as: AttributeSequence): (String, List[String]) = {
    (as.getName, as.getTags.asScala.toList)
  }
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
  }

  def getDefaultAttributes(l: List[Attributes]): Map[String, String] = {
    l.map{e => (e.getName, e.getUndefined)}.toMap
  }
}