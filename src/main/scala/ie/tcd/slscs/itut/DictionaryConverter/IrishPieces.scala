/*
 * The MIT License (MIT)
 *
 * Copyright © 2017 Trinity College, Dublin
 * Irish Speech and Language Technology Research Centre
 * Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 * An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
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

object IrishPieces {
  def mkNoun(pos: String): LUElement = {
    LUElement(List(ClipElement(pos,"tl","lemh",null,null,null),
                   ClipElement(pos,"tl","a_nom",null,null,null),
                   ClipElement(pos,"tl","gen_no_mf",null,"3",null),
                   ClipElement(pos,"tl","gen_mf",null,null,null),
                   ClipElement(pos,"tl","nbr_no_sp",null,"4",null),
                   ClipElement(pos,"tl","nbr_sp",null,null,null),
                   LitElement("5"),
                   LitElement("6"),
                   ClipElement(pos,"tl","lemq",null,null,null)))
  }
  def mkAdj(pos: String): LUElement = {
    LUElement(List(ClipElement(pos,"tl","lemh",null,null,null),
                   ClipElement(pos,"tl","a_adj",null,null,null),
                   ClipElement(pos,"tl","gen_no_mf",null,"3",null),
                   ClipElement(pos,"tl","gen_mf",null,null,null),
                   ClipElement(pos,"tl","nbr_no_sp",null,"4",null),
                   ClipElement(pos,"tl","nbr_sp",null,null,null),
                   LitElement("5"),
                   LitElement("6"),
                   ClipElement(pos,"tl","lemq",null,null,null)))
  }
  def mkDet(pos: String): LUElement = {
    LUElement(List(ClipElement(pos,"tl","lem",null,null,null),
                   ClipElement(pos,"tl","a_det",null,null,null),
                   ClipElement(pos,"tl","gen_no_mf",null,"3",null),
                   ClipElement(pos,"tl","gen_mf",null,null,null),
                   ClipElement(pos,"tl","nbr_no_sp",null,"4",null),
                   ClipElement(pos,"tl","nbr_sp",null,null,null)))
  }
  def mkWhole(pos: String): LUElement = {
    LUElement(List(ClipElement(pos,"tl","whole",null,null,null)))
  }
}
