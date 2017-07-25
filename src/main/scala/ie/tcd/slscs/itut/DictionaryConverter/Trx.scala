/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Trinity College, Dublin
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

import scala.xml._

trait TransferElement {
  def toXML: scala.xml.Node
}
case class CatItem(tags: String, lemma: String = null) extends TransferElement {
  def toXML = <cat-item lemma={lemma} tags={tags} />
}
case class DefCat(n: String, l: List[CatItem]) extends TransferElement {
  def toXML = {
    <def-cat n={n}>
    { l.map { c => c.toXML } }
    </def-cat>
  }
}
case class AttrItem(tags: String) extends TransferElement {
  def toXML = <attr-item tags={tags} />
}
case class AttrCat(n: String, l: List[AttrItem]) extends TransferElement {
  def toXML = {
    <def-attr n={n}>
    { l.map { c => c.toXML } }
    </def-attr>
  }
}
case class DefVar(name: String, value: String = null) extends TransferElement {
  def toXML = <def-var n={name} v={value} />
}


object Trx {
  import scala.xml._
  def nodeToCatItem(n: Node): CatItem = {
    val lemmaraw = (n \ "@lemma").text
    val lemma = if (lemmaraw != "") lemmaraw else null
    val tags = (n \ "@tags").text
    CatItem(tags, lemma)
  }
  def nodeToDefCat(n: Node): DefCat = {
    val name = (n \ "@n").text
    val children = (n \ "cat-item").toList.map{nodeToCatItem}
    DefCat(name, children)
  }
}
