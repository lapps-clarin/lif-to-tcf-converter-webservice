/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.datamodel.configurations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class TcfVocabularies {

    private TcfVocabularies() {
    }

    public static class GeneralParameters {

        public static final String UNICODE = "UTF-8";
        public static final String PARAMETER_SEPERATOR_REG = "\\=";
        public static final String PARAMETER_SEPERATOR = "=";
    }

    public static class TCF {

        public static class TcfTagSets {

            public static final String DEPENDENCY_TAGSETS = "penntb";
            public static final String DEPARSING_TAGSETS = "stanford-simple";
            public static final String CONSTITUENT_TAGSETS = "penntb";
            public static final String POS_TAGSETS = "penntb";
        }

        public static class TcfTreeSets {

            public static final String CONSTITUENT_ROOT = "ROOT";
        }

        public static class TcfConstants {

            public static final String DEFAULT_LANGUAGE = "en";
        }

    }
}
