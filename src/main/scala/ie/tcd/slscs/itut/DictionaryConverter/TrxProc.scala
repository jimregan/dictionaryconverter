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

case class TrxProc(kind: String, defcats: Map[String, List[CatItem]],
                   defattrs: Map[String, List[String]],
                   vars: Map[String, String],
                   lists: Map[String, List[String]],
                   macros: Map[String, List[SentenceElement]], rules: List[RuleElement]) {
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
  //def getVars: List[String]
  private val _lists = collection.mutable.Map.empty[String, List[String]] ++ lists
  def validLists: List[String] = _lists.keys.toList
  def getList(s: String): Option[List[String]] = _lists.get(s)
  def hasList(s: String): Boolean = _lists.contains(s)
  def setList(s: String, v: List[String]) {
    _lists(s) = v
  }
  def safeSetLit(s: String, v: List[String]) {
    if(!hasList(s)) {
      setList(s, v)
    }
  }
  def getLists: List[List[String]] = _lists.values.toList
  def getListsMap = _lists
}
object TrxProc {
  def fromTopLevel(t: TopLevel): TrxProc = {
    val dc = t.defcats.map{e => (e.n, e.l)}.toMap
    val da = t.defattrs.map{e => (e.n, e.l.map{_.tags})}.toMap
    val dv = t.vars.map{e => (e.name, e.value)}.toMap
    val dl = t.lists.map{e => (e.name, e.items.map{_.value})}.toMap
    val dm = t.macros.map{e => (e.name, e.actions)}.toMap
    TrxProc(t.kind, dc, da, dv, dl, dm, t.rules)
  }
  def merge(a: TopLevel, b: TopLevel): TrxProc = {
    val out = fromTopLevel(a)
    val tmp = fromTopLevel(b)

    out
  }
}