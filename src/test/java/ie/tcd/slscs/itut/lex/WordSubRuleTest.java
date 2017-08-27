package ie.tcd.slscs.itut.lex;

import ie.tcd.slscs.itut.gramadanj.Utils;
import junit.framework.TestCase;
import org.w3c.dom.Node;

import static org.junit.Assert.*;

public class WordSubRuleTest  extends TestCase {
    public void testFromNode() throws Exception {
        String in = "        <subrule name=\"verbdefault\" default=\"yes\">\n" +
                "          <entry tags=\"inf\" text=\"~\" />\n" +
                "          <entry tags=\"pri.p3.sg\" text=\"~s\" />\n" +
                "          <entry tags=\"past\" text=\"~ed\" />\n" +
                "          <entry tags=\"pp\" equals=\"past\" />\n" +
                "          <entry tags=\"ger\" text=\"~ing\" />\n" +
                "        </subrule>\n";
        Node innode = Utils.stringToNode(in);
        WordSubRule out = WordSubRule.fromNode(innode);
        assertEquals(true, out.isDefaultRule());
        assertEquals(5, out.entries.size());
    }
}