/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core.impl;

import de.tuebingen.uni.sfs.lapps.library.layer.api.AnnotationLayerFinder;
import de.tuebingen.uni.sfs.lapps.library.layer.xb.AnnotationInterpreter;
import de.tuebingen.uni.sfs.lapps.library.layer.xb.LifAnnotationLayerFinderStored;
import de.tuebingen.uni.sfs.lapps.library.utils.xb.ProcessUtils;
import eu.clarin.weblicht.wlfxb.tc.xb.TextCorpusStored;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author felahi
 */
public class ConvertToTCFAnnotationsAllTest {

    private String ALL_LAYER_EXAMPLE = "karen-all.json";

    private String FILE_LIF = "json";
    ConvertToTCFAnnotations instance;
    private ClassLoader classLoader = getClass().getClassLoader();

    public ConvertToTCFAnnotationsAllTest() {
    }

    /**
     * Test of toText method, of class ConvertToTCFAnnotations.
     */
    @Test
    public void testAllLayer() throws Exception {
        File inputFile = new File(classLoader.getResource(ALL_LAYER_EXAMPLE).getFile());
        if (inputFile.getName().contains(FILE_LIF)) {
            LifAnnotationLayerFinderStored tool = ProcessUtils.fileProcessing(inputFile);
            Assert.assertEquals(tool.isTextLayer(), true);
            Assert.assertEquals(tool.isTokenLayer(), true);
            Assert.assertEquals(tool.isPosLayer(), true);
            Assert.assertEquals(tool.isSenetenceLayer(), true);
        }
    }
}
