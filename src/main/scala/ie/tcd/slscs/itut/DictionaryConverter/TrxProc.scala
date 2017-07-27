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
                   macros: Map[String, List[Action]], rules: List[Rule]) {
  val variables = collection.mutable.Map.empty[String, String] ++ vars
  val validVariables: List[String] = vars.keys.toList
  def getVar(s: String): Option[String] = {
    if (validVariables.contains(s)) {
      variables.get(s)
    } else {
      None
    }
  }
  def setVar(s: String, v: String) {
    variables(s) = v
  }
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
}