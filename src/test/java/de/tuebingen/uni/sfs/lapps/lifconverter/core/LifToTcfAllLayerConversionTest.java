/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.core;

import org.junit.Test;
import de.tuebingen.uni.sfs.lapps.lifconverter.tests.utils.LifToTcfConversionAssertUtils;
import java.io.InputStream;
import org.junit.Ignore;

/**
 *
 * @author Mohammad Fazleh Elahi
 */
public class LifToTcfAllLayerConversionTest {

    private String LIF_EXAMPLE = "/data/karen-all.json";
    private String TCF_EXAMPLE = "/data/karen-all-output-expected.xml";

    /**
     * Test of LIF to TCF converter webservice. The web service use the
     * lif to tcf conversion library. This test only checks whether web service works fine or not?
     * Note: Tests of all kinds of LIF TO TCF will be found in conversion library
     */
    @Test
    public void test_whenSingleViewSingleLayer_LifDocument() throws Exception {
        InputStream input = this.getClass().getResourceAsStream(LIF_EXAMPLE);
        InputStream expectedOutput = this.getClass().getResourceAsStream(TCF_EXAMPLE);
        LifToTcfConversionAssertUtils.lifToTcfAssertEqual(input, expectedOutput);
    }

}
