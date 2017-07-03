/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weblicht.format_converter.datamodel.configurations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.lappsgrid.discriminator.Discriminators;

/**
 *
 * @author felahi
 */
public class Vocabularies {

    private Vocabularies() {
    }

    public static class GeneralParameters {

        public static final String UNICODE = "UTF-8";
        public static final String PARAMETER_SEPERATOR_REG = "\\=";
        public static final String PARAMETER_SEPERATOR = "=";
    }

    public static class TCF {

        public static class TcfTagSets {

            public static final String DEPENDENCY_TAGSETS = "stts";
            public static final String CONSTITUENT_TAGSETS = "tigertb";
            public static final String POS_TAGSETS = "penntb";
        }

        public static class TcfTreeSets {

            public static final String CONSTITUENT_ROOT = "ROOT";
        }

        public static class TcfVocabularies {

            public static final String DEFAULT_LANGUAGE = "en";
        }

    }

    public static class LIF {

        public static class DiscriminitorsExtended {

            public static final String PENN_TREE = "penntree";
            public static final String CONSTITUENTS = "constituents";
        }

        public static class TreeSets {

            public static final String CONSTITUENT_ROOT = "ROOT";
        }

        public static class Document {

            public static final String DISCRIMINATOR_KEY_JSON = "discriminator";
            public static final String PAYLOAD_KEY_JSON = "payload";
            public static String WEB_EXCHANGE_VOCABULARY = "http://vocab.lappsgrid.org/lapps-vocabulary.owl";

            public static class LifDocumentTopLevel {

                public static final String CONTEXT_KEY_LIF = "@context";
                public static final String TEXT_KEY_LIF = "text";
                public static final String VIEWS_KEY_LIF = "views";
                public static final String METADATA_KEY_LIF = "metadata";
            }

            public static class LifDocumentDeepLevel {
                Set<String> NAME_ENTITY_ANNOTATIONS = new HashSet<String>(Arrays.asList(
                        Discriminators.Uri.PERSON, 
                        Discriminators.Uri.LOCATION, 
                        Discriminators.Uri.DATE, 
                        Discriminators.Uri.ORGANIZATION));
            }

        }
    }

}
