/**
 *
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.token;

import de.tuebingen.uni.sfs.lapps.library.model.DataModelLif;
import de.tuebingen.uni.sfs.lapps.lifconverter.core.FormatConverterTool;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.conversion.DataModelConverter;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model.DataModelTcf;
import eu.clarin.weblicht.wlfxb.io.TextCorpusStreamed;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

/**
 * @author Mohammad Fazleh Elahi
 *
 */
public class TokenConversionTest extends AbstractTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String INPUT_LIF_FILE = "/data/tokens/inputTok.json";
    private static final String EXPECTED_OUTPUT_TCF_FILE = "/data/tokens/output-expected.tcf";
    private static final String OUTPUT_FILE = "output.xml";

    private DataModelConverter tool;

    @Test
    public void testReadInput() throws Exception {
        InputStream input = read(INPUT_LIF_FILE);
        DataModelLif dataModelLif = new DataModelLif(input);
        Assert.assertEquals(true, dataModelLif.isValid());
    }

    @Ignore
    public void testConversion() throws Exception {
        InputStream input = read(INPUT_LIF_FILE);
        DataModelLif dataModelLif = new DataModelLif(input);
        Assert.assertEquals(true, dataModelLif.isValid());

        String outfile = testFolder.getRoot() + File.separator + OUTPUT_FILE;
        //TextCorpusStreamed tc = open(INPUT_FILE_WITHOUT_LAYER, outfile, layersToReadBeforeTokenization);
        //System.out.println(tc);

        try {
            tool = new FormatConverterTool();
            DataModelTcf tcfDataModel = tool.convertModel(dataModelLif, input);
            tcfDataModel.process(output);
            assertEqualXml(EXPECTED_OUTPUT_TCF_FILE, outfile);

        } finally {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        }

    }
    
     protected void assertEqualXml(String expectedOutputResource, String outputFile) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        InputStream expected = this.getClass().getResourceAsStream(expectedOutputResource);
        junit.framework.Assert.assertNotNull("can't open expected output resource", expected);
        InputStream actual = new FileInputStream(outputFile);
        junit.framework.Assert.assertNotNull("can't open actual output resource", actual);
        TestUtils.assertEqualXml(expected, actual);
    }

}
