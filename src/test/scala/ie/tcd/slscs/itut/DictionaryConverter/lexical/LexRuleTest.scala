/*
 *  The MIT License (MIT)
 *
 *  Copyright © 2017 Trinity College, Dublin
 *  Irish Speech and Language Technology Research Centre
 *  Cóipcheart © 2017 Coláiste na Tríonóide, Baile Átha Cliath
 *  An tIonad taighde do Theicneolaíocht Urlabhra agus Teangeolaíochta na Gaeilge
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package ie.tcd.slscs.itut.DictionaryConverter.lexical

import org.scalatest.FlatSpec
import scala.xml._

class LexRuleTest extends FlatSpec {
  val xmlfile = <rules>
    <group n="add_e" repeated="no">
      <item>ch</item>
      <item>sh</item>
      <item>x</item>
    </group>
    <chargroup name="double" repeated="no">bdgtnmp</chargroup>
    <chargroup name="vowel" repeated="no">aeiou</chargroup>
    <chargroup name="nonvowel" repeated="no" negates="vowel" />
    <word-rules>
      <rule name="adj.sint">
        <subrule match="~&vowel;&double;">
          <entry tags="-" ending="~" />
          <entry tags="comp" ending="~&double;er" />
          <entry tags="sup" ending="~&double;est" />
        </subrule>
        <subrule match="~&vowel;y">
          <entry tags="-" ending="~" />
          <entry tags="comp" ending="~er" />
          <entry tags="sup" ending="~est" />
        </subrule>
        <!-- a name is needed here, because happy:happier, but sly:slyer and shy:shier or shyer -->
        <subrule match="~y">
          <entry tags="-" ending="~" />
          <entry tags="comp" ending="-ier" />
          <entry tags="sup" ending="-iest" />
        </subrule>
        <subrule match="~e">
          <entry tags="-" ending="~" />
          <entry tags="comp" ending="~r" />
          <entry tags="sup" ending="~st" />
        </subrule>
        <subrule match="~&add_e;" equals="~&vowel;y" />
        <subrule default="yes" equals="~&vowel;y" />
      </rule>
      <rule name="n">
        <subrule match="~&vowel;y">
          <entry tags="sg" ending="~" />
          <entry tags="pl" ending="-s" />
        </subrule>
        <subrule match="~y">
          <entry tags="sg" ending="~" />
          <entry tags="pl" ending="-ies" />
        </subrule>
        <subrule match="~&add_e;">
          <entry tags="sg" ending="~" />
          <entry tags="pl" ending="~es" />
        </subrule>
        <subrule match="~is" name="prognosis">
          <entry tags="sg" ending="~" />
          <entry tags="pl" ending="-es" />
        </subrule>
      </rule>
      <rule name="v">
        <common>
          <entry tags="pres" equals="inf" />
          <entry tags="pprs" equals="ger" />
          <entry tags="subs" equals="ger" />
        </common>
        <subrule default="yes">
          <entry tags="inf" ending="~" />
          <entry tags="pri.p3.sg" ending="~s" />
          <entry tags="past" ending="~ed" />
          <entry tags="pp" equals="past" />
          <entry tags="ger" ending="~ing" />
        </subrule>
        <subrule match="~e">
          <entry tags="inf" ending="~" />
          <entry tags="pri.p3.sg" ending="~s" />
          <entry tags="past" ending="~d" />
          <entry tags="pp" equals="past" />
          <entry tags="ger" ending="-ing" />
        </subrule>
      </rule>
    </word-rules>
    <multiword-rules>
      <rule name="adj_n" phrase="np">
        <entry tags="sg">
          <word tags="adj"/>
          <word tags="n.sg"/>
        </entry>
        <entry tags="pl">
          <word tags="adj"/>
          <word tags="n.pl"/>
        </entry>
      </rule>
      <rule name="nsg_n" phrase="np">
        <entry tags="sg">
          <word tags="n.sg"/>
          <word tags="n.sg"/>
        </entry>
        <entry tags="pl">
          <word tags="n.sg"/>
          <word tags="n.pl"/>
        </entry>
      </rule>
      <rule name="n_pr_n" phrase="np">
        <entry tags="sg">
          <word tags="n.sg"/>
          <queue phrase="pp">
            <word tags="pr"/>
            <phrase n="np">
              <word tags="n.sg"/>
            </phrase>
          </queue>
        </entry>
        <entry tags="pl">
          <word tags="n.pl"/>
          <queue phrase="pp">
            <word tags="pr"/>
            <phrase n="np">
              <word tags="n.sg"/>
            </phrase>
          </queue>
        </entry>
      </rule>
    <!-- son of a bitch (sg); sons of bitches (pl) -->
      <rule name="son_of_a_bitch" phrase="np">
        <entry tags="sg">
          <word tags="n.sg"/>
          <queue phrase="pp">
            <word tags="pr"/>
            <phrase n="np">
              <word tags="det.ind.sg"/>
              <word tags="n.sg"/>
            </phrase>
          </queue>
        </entry>
        <entry tags="pl">
          <word tags="n.pl"/>
          <queue phrase="pp">
            <word tags="pr"/>
            <phrase n="np">
              <word tags="n.pl"/>
            </phrase>
          </queue>
        </entry>
      </rule>
    </multiword-rules>
  </rules>

  val cg_doublexml = <chargroup name="double" repeated="no">bdgtnmp</chargroup>
  val cg_vowelxml = <chargroup name="vowel" repeated="no">aeiou</chargroup>
  val cg_nonvowelxml = <chargroup name="nonvowel" repeated="no" negates="vowel" />
  val cg_double = CharGroup("double", "bdgtnmp", false)
  val cg_vowel = CharGroup("vowel", "aeiou", false)
  val cg_nonvowel = NegatedCharGroupRef("nonvowel", "vowel", false)

  "nodeToGrouping" should "test XML conversion" in {
    val outone = LexRule.nodeToGrouping(cg_doublexml)
    val outtwo = LexRule.nodeToGrouping(cg_vowelxml)
    val outthree = LexRule.nodeToGrouping(cg_nonvowelxml)
    assert(cg_double == outone)
    assert(cg_vowel == outtwo)
    assert(cg_nonvowel == outthree)
  }
  "testNegatedCharGroupConversion" should "convert a reference to a chargroup" in {
    val map = Map[String, Grouping]("vowel" -> cg_vowel)
    val exp = NegatedCharGroup("nonvowel", cg_vowel, false)
    val out = LexRule.negatedCharGroupRefToNegatedCharGroup(cg_nonvowel, map)
    assert(exp == out)
  }

}
