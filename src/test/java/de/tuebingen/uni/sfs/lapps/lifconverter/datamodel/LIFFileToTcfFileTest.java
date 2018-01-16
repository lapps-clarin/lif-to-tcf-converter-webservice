/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel;

import de.tuebingen.uni.sfs.lapps.library.annotation.layer.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.annotation.layer.xb.AnnotationLayersStored;
import de.tuebingen.uni.sfs.lapps.library.utils.xb.ProcessUtils;
import de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.model.DataModelTcf;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class LIFFileToTcfFileTest {

    private String CORFERENCE_EXAMPLE = "inputCorfer.json";
    private String CONTSTITUENT_EXAMPLE = "inputCon.json";
    private String DEPENDENCY_EXAMPLE = "inputDep.json";
    private String MULTILAYER_EXAMPLE = "inputMulti.json";
    private String NAMEENTITY_EXAMPLE = "inputNer.json";
    private String SENTENCE_EXAMPLE = "inputSen.json";
    private String POS_EXAMPLE = "inputPos.json";
    private String TEXT_EXAMPLE = "inputText.json";
    private String TOKEN_EXAMPLE = "inputTok.json";
    private String FILE_LIF = "json";
    DataModelTcf instance;
    private ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void testTextLayer() throws Exception {
        System.out.println("toText");
        File inputFile = new File(classLoader.getResource(TEXT_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isTextLayer()) {
                Assert.assertEquals(tool.isTextLayer(), true);
                instance = new DataModelTcf(null);
                instance.toText("Karen flew to New York.");
                System.out.println("TextLayer exists:" + instance.getTextCorpusStored().getTextLayer().toString());
                assertEquals("text : Karen flew to New York.", instance.getTextCorpusStored().getTextLayer().toString());
            }
        }
    }

    @Test
    public void testTokenLayer() throws Exception {
        System.out.println("toToken");
        File inputFile = new File(classLoader.getResource(TOKEN_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isTokenLayer()) {
                Assert.assertEquals(tool.isTokenLayer(), true);
                instance = new DataModelTcf(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toToken();
                System.out.println("TokenLayer exists:" + instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
                assertEquals("0: t_0 -> Karen", instance.getTextCorpusStored().getTokensLayer().getToken(0).toString());
            }
        }

    }

    @Test
    public void testPosLayer() throws Exception {
        System.out.println("toPos");
        File inputFile = new File(classLoader.getResource(POS_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isPosLayer()) {
                Assert.assertEquals(tool.isPosLayer(), true);
                instance = new DataModelTcf(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toPos();
                System.out.println("PosLayer exists:" + instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
                assertEquals("NNP [t_0]", instance.getTextCorpusStored().getPosTagsLayer().getTag(0).toString());
            }
        }
    }

    @Test
    public void testSentenceLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(SENTENCE_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isSenetenceLayer()) {
                Assert.assertEquals(tool.isSenetenceLayer(), true);
                instance = new DataModelTcf(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toToken();
                instance.toSentences();
                System.out.println("SentenceLayer exists:" + instance.getTextCorpusStored().getSentencesLayer().getSentence(0));
                assertEquals("[t_0]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
            }
        }
    }

    @Ignore
    public void testNamedEntirtyLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(NAMEENTITY_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isNamedEntityLayer()) {
                Assert.assertEquals(tool.isNamedEntityLayer(), true);
                instance = new DataModelTcf(null);
                
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                //instance.toLayers(Discriminators.Uri.NE.toString(), tokenAnnotations);
                //instance.setGivenAnnotations(tokenAnnotations);
                instance.toToken();
                instance.toNameEntity();
                System.out.println("NamedEntirtyLayer exists:" + instance.getTextCorpusStored().getNamedEntitiesLayer().getEntity(0));
                //assertEquals("[t_0]", instance.getTextCorpusStored().getSentencesLayer().getSentence(0).toString());
            }
        }
    }

    @Test
    public void testDependencyLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(DEPENDENCY_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isDependencyLayer()) {
                Assert.assertEquals(tool.isDependencyLayer(), true);
                instance = new DataModelTcf(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toDependencyParser();
                System.out.println("DependencyLayer exists:" + instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0));
                assertEquals("[nn [t_3] <- [t_4], pobj [t_4] <- [t_2], nsubj [t_0] <- [t_1], prep [t_2] <- [t_1]]", instance.getTextCorpusStored().getDependencyParsingLayer().getParse(0).toString());
            }
        }

    }

    @Test
    public void testConstituentLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(CONTSTITUENT_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isConstituentLayer()) {
                Assert.assertEquals(tool.isConstituentLayer(), true);
                instance = new DataModelTcf(null);
                List<AnnotationInterpreter> tokenAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(tokenAnnotations);
                instance.toConstituentParser();
                System.out.println("ConstituentLayer exists:" + instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot());
                assertEquals("c_17 -> ROOT ( c_16 -> S ( c_15 -> NP ( c_14 -> NNP ( c_13 -> NNP [t_0] ) ) c_12 -> VP ( c_11 -> VBD ( c_10 -> VBD [t_1] ) c_9 -> PP ( c_8 -> TO ( c_7 -> TO [t_2] ) c_6 -> NP ( c_5 -> NNP ( c_4 -> NNP [t_3] ) c_3 -> NNP ( c_2 -> NNP [t_4] ) ) ) ) c_1 -> . ( c_0 -> . [t_5] ) ) )", instance.getTextCorpusStored().getConstituentParsingLayer().getParse(0).getRoot().toString());

            }
        }

    }

    @Ignore
    public void testCorferenceLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(CORFERENCE_EXAMPLE).getFile());
        DataModelTcf instance = new DataModelTcf(null);
        if (inputFile.getName().contains(FILE_LIF)) {
            AnnotationLayersStored tool = ProcessUtils.fileProcessing(inputFile);
            if (tool.isCorferenceLayer()) {
                Assert.assertEquals(tool.isCorferenceLayer(), true);
                List<AnnotationInterpreter> coreferenceResolverAnnotations = tool.getGivenDataModel().getAnnotationLayerData(0);
                instance.setGivenAnnotations(coreferenceResolverAnnotations);
                instance.toCoreferenceResolver();
                System.out.println("CorferenceLayer exists:" + instance.getTextCorpusStored().getReferencesLayer().getReferencedEntity(0));
            }

        }

    }

}
