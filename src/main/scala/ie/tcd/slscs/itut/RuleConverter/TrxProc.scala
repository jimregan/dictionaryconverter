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

import ie.tcd.slscs.itut.ApertiumTransfer.{AttrItem, DefAttr, CatItem => JCatItem, DefCat => JDefCat, DefCats => JDefCats}

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

}