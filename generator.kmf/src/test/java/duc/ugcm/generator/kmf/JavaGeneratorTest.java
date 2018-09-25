package duc.ugcm.generator.kmf;

import duc.ugcm.ast.Model;
import duc.ugcm.parser.Parser;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class JavaGeneratorTest {

    @Test
    public void testGeneration() throws IOException, URISyntaxException {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("Cloud.ugcm");

        Model model = new Parser().parse(CharStreams.fromStream(in));

        File mmFile = new File(getClass().getClassLoader().getResource("Cloud.ugcm").toURI());
        File out = new File(mmFile.getParentFile().getParent() + File.separator + "generated-ugcm-test");
        JavaGenerator.generate(model, out,"MyMetaModel");
    }
}
