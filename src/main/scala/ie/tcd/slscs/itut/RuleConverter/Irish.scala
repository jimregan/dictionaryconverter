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

class Irish {
  val ga_attribs = s"""
grade = comp
gen! = m f mf GD
num = sg pl sp ND
case = com gen voc dat
mut = len hpref ecl defart
pers = p1 p2 p3 PD
strength = strong
"""
  val en_attribs = s"""
grade = comp sup
gen = m f nt mf GD
num = sg pl sp ND
pers = p1 p2 p3 PD
rel_type = nn an adv
a_noun = n a.acr np.top np.ant np.cog np.al
a_adj = adj adj.sint
tense = pri pres past inf pp ger pprs subs
"""
  val en_attribs_chunk = s"""
gen = m f mf GD
num = sg pl sp ND
det_chunk = DEFART NODET DET
neg_chunk = negative
"""
  val en_attribs_seq = s"""
noun = a_noun num
adj = a_adj grade
prn = a_prn pers gen num
verb = a_verb tense pers num
rel = a_rel gen num
det = a_det num
"""
  val ga_attribs_seq = s"""
noun = a_noun strength gen num case mut emph
adj = a_adj strength gen num case mut
verb = a_verb tense pers num neg itg mut emph
det = a_det num gen
prn = a_prn pers num gen emph
"""
  val ga_attribs_chunk = s"""
mut = len hpref ecl defart LENGAN LENPREP
gen = m f mf GD
num = sg pl sp ND
case = com gen voc dat GEN2
det_chunk = DEFART NODET DET RMART
"""
}
