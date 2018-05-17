/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.constants;

/**
 *
 * @author felahi
 */
public class TcfConstants {

      //Languages
    public static final String LANG_EN = "en";
    public static final String TCF_PARSING_TAGSET_PENNTB = "penntb";
    public static final String TCF_DEPPARSING_TAGSET_STANFORD =  "stanford";
    public static final String TCF_POSTAGS_TAGSET_PENNTB = "penntb";

   /* public static final BitValue TCF_SENTENCES = BitFeature.addValue("sentences", null);
    public static final BitValue TCF_SENTENCES_TOK_REF_TRUE = BitFeature.addValue("sentences.tokref", "true");
    public static final BitValue TCF_POSTAGS_TAGSET_STTS = BitFeature.addValue("postags.tagset", "stts");
    public static final BitValue TCF_POSTAGS_TAGSET_PENNTB = BitFeature.addValue("postags.tagset", "penntb");
    public static final BitValue TCF_POSTAGS_TAGSET_METUSABANCI = BitFeature.addValue("postags.tagset", "metu-sabanci");
    public static final BitValue TCF_POSTAGS_TAGSET_STEIN = BitFeature.addValue("postags.tagset", "stein");
    public static final BitValue TCF_NAMED_ENTITIES = BitFeature.addValue("namedentities", null);
    public static final BitValue TCF_NAMED_ENTITIES_TYPE_CONLL2002 = BitFeature.addValue("namedentities.type", "conll2002");
    public static final BitValue TCF_NAMED_ENTITIES_TYPE_OPENNLP = BitFeature.addValue("namedentities.type", "opennlp");
    public static final BitValue TCF_NAMED_ENTITIES_TYPE_STANFORD = BitFeature.addValue("namedentities.type", "stanford_ner/regexner");
    public static final BitValue TCF_CHUNK_TAGSET_STTS = BitFeature.addValue("chunks.tagset", "stts");
    public static final BitValue TCF_CHUNK_TAGSET_PENNTB = BitFeature.addValue("chunks.tagset", "penntb");
    public static final BitValue TCF_PARSING_TAGSET_TIGERTB = BitFeature.addValue("parsing.tagset", "tigertb");
    public static final BitValue TCF_PARSING_TAGSET_STTS = BitFeature.addValue("parsing.tagset", "stts");
    public static final BitValue TCF_PARSING_TAGSET_PENNTB = BitFeature.addValue("parsing.tagset", "penntb");
    public static final BitValue TCF_PARSING_TAGSET_METUSABANCI = BitFeature.addValue("parsing.tagset", "metu-sabancı");
    public static final BitValue TCF_DEPPARSING_EMPTYTOKS_FALSE = BitFeature.addValue("depparsing.emptytoks", "false");
    public static final BitValue TCF_DEPPARSING_MULTIGOVS_TRUE = BitFeature.addValue("depparsing.multigovs", "true");
    public static final BitValue TCF_DEPPARSING_TAGSET_STANFORD = BitFeature.addValue("depparsing.tagset", "stanford");
    public static final BitValue TCF_DEPPARSING_TAGSET_STANFORD_COLLAPSED = BitFeature.addValue("depparsing.tagset", "stanford-collapsed");
    public static final BitValue TCF_DEPPARSING_TAGSET_STANFORD_CCPROPAGATED = BitFeature.addValue("depparsing.tagset", "stanford-ccpropagated");
    public static final BitValue TCF_MORPHOLOGY_TAGSET_DEFAULT = BitFeature.addValue("morphology.tagset", null);
    public static final BitValue TCF_MORPHOLOGY_TAGSET_STTS = BitFeature.addValue("morphology.tagset", "stts");
    public static final BitValue TCF_SPEECHSIGNAL_1CH = BitFeature.addValue("ext.speechsignal.numberchannels", "1");
    public static final BitValue TCF_SPEECHSIGNAL_WAV = BitFeature.addValue("ext.speechsignal.type", "audio/wav");
    public static final BitValue TCF_TEXTSOURCE_TYPE_BBAW = BitFeature.addValue("textsource.type", "application/tei+xml; format-variant=tei-dta; tokenized=0");
    public static final BitValue TCF_LEMMAS = BitFeature.addValue("lemmas", null);
    public static final BitValue TCF_TEXT = BitFeature.addValue("text", null);
    public static final BitValue TCF_TOKENS = BitFeature.addValue("tokens", null);
    public static final BitValue TCF_CHUNKS = BitFeature.addValue("chunks", null);
    public static final BitValue TCF_MORPHOLOGY = BitFeature.addValue("morphology", null);
    public static final BitValue TCF_GEO_CAPITAL_FORMAT_NAME = BitFeature.addValue("geo.capitalformat", "name");
    public static final BitValue TCF_GEO_CONTINENT_FORMAT_NAME = BitFeature.addValue("geo.continentformat", "name");
    public static final BitValue TCF_GEO_COORD_FORMAT_DEG_DEC = BitFeature.addValue("geo.coordformat", "degdec");
    public static final BitValue TCF_GEO_COUNTRY_FORMAT_ISO3166_A2 = BitFeature.addValue("geo.countryformat", "iso3166_a2");
    public static final BitValue TCF_CORPUS_TUEBA_DDC = BitFeature.addValue("corpus", "tueba-ddc");
    public static final BitValue TCF_CORPUS_DEWIKI = BitFeature.addValue("corpus", "dewiki");
    public static final BitValue TCF_CORPUS_TUEPP = BitFeature.addValue("corpus", "tuepp");
    public static final BitValue TCF_CORPUS_WEB_DMOZ = BitFeature.addValue("corpus", "web_dmoz");
    public static final BitValue TCF_CORPUS_RESULTS_10 = BitFeature.addValue("results", "10");
    public static final BitValue TCF_CORPUS_RESULTS_50 = BitFeature.addValue("results", "50");
    public static final BitValue TCF_CORPUS_RESULTS_100 = BitFeature.addValue("results", "100");
    private static final Map<Value, String> VALUE_NAME_MAP = ImmutableMap.<Value, String>builder()
            //Languages
            .put(LANG_BG, "Bulgarian")
            .put(LANG_CS, "Czech")
            .put(LANG_DE, "German")
            .put(LANG_EN, "English")
            .put(LANG_FI, "Finnish")
            .put(LANG_FR, "French")
            .put(LANG_IT, "Italian")
            .put(LANG_LV, "Latvian")
            .put(LANG_NL, "Dutch")
            .put(LANG_PL, "Polish")
            .put(LANG_RO, "Romanian")
            .put(LANG_RU, "Russian")
            .put(LANG_SK, "Slovak")
            .put(LANG_ES, "Spanish")
            .put(LANG_TR, "Turkish")
            .put(LANG_SL, "Slovenian")
            .put(LANG_HR, "Croatian")
            .put(LANG_SR, "Serbian")
            .put(LANG_HU, "Hungarian")
            //Types
            .put(TYPE_APPLICATION_MSWORD, "Microsoft Word Document")
            .put(TYPE_APPLICATION_OOXML_DOCUMENT, "Office Open XML Document")
            .put(TYPE_APPLICATION_ODT, "OpenDocument Text")
            .put(TYPE_APPLICATION_PDF, "Portable Document Format(PDF)")
            .put(TYPE_APPLICATION_RTF, "Rich Text Format(RTF)")
            .put(TYPE_APPLICATION_XML, "XML Format")
            .put(TYPE_APPLICATION_XML_TEI_ISO_SPOKEN, "ISO/TEI for transcriptions of spoken language")
            .put(TYPE_APPLICATION_XML_CLAN_CHA, "Transcriber Transcription Format")
            .put(TYPE_APPLICATION_XML_EXMARALDA_EXB, "EXMARaLDA Basic Transcription Format")
            .put(TYPE_APPLICATION_XML_FOLKER_FLN, "FOLKER/OrthoNormal Transcription Format")
            .put(TYPE_APPLICATION_XML_TRANSCRIBER_TRS, "CLAN/CHAT")
            .put(TYPE_APPLICATION_XML_TEI_DTA, "DTA TEI")
            .put(TYPE_TEXT_HTML, "HyperText Markup Language(HTML)")
            .put(TYPE_TEXT_LEXICON_XML, "Lexicon Format")
            .put(TYPE_TEXT_MAF_XML, "Morphosyntactic Annotation Framework")
            .put(TYPE_TEXT_NEGRA, "NEGRA Format")
            .put(TYPE_TEXT_PLAIN, "Plain Text")
            .put(TYPE_TEXT_PRAAT_XML, "Praat Format")
            .put(TYPE_TEXT_QUERY_CQP, "CQP Query")
            .put(TYPE_TEXT_QUERY_CQL, "CQL Query")
            .put(TYPE_TEXT_QUERY_DDC, "DDC Query")
            .put(TYPE_TEXT_QUERY_DLEXDB, "DLEXDB Query")
            .put(TYPE_TEXT_TCF_XML, "TCF")
            //TCF
            .put(TCF_PARSING_TAGSET_TIGERTB, "Tiger Treebank Tagset")
            .put(TCF_PARSING_TAGSET_STTS, "STTS Tagset")
            .put(TCF_PARSING_TAGSET_PENNTB, "Penn Treebank Tagset")
            .put(TCF_PARSING_TAGSET_METUSABANCI, "METU-Sabancı treebank tagset")
            .put(TCF_DEPPARSING_EMPTYTOKS_FALSE, "No Empty Tokens")
            .put(TCF_DEPPARSING_MULTIGOVS_TRUE, "With Multi Govs")
            .put(TCF_DEPPARSING_TAGSET_STANFORD, "Stanford Dependencies")
            .put(TCF_DEPPARSING_TAGSET_STANFORD_COLLAPSED, "Stanford Dependencies (Collapsed)")
            .put(TCF_DEPPARSING_TAGSET_STANFORD_CCPROPAGATED, "Stanford Tagset (Conjunct Propagation)")
            .put(TCF_MORPHOLOGY_TAGSET_DEFAULT, "Default")
            .put(TCF_MORPHOLOGY_TAGSET_STTS, "STTS Tagset")
            .put(TCF_POSTAGS_TAGSET_STTS, "STTS Tagset")
            .put(TCF_POSTAGS_TAGSET_PENNTB, "Penn Treebank Tagset")
            .put(TCF_POSTAGS_TAGSET_METUSABANCI, "METU-Sabancı treebank tagset")
            .put(TCF_POSTAGS_TAGSET_STEIN, "Stein Tagset")
            .put(TCF_NAMED_ENTITIES_TYPE_CONLL2002, "CoNLL-2002")
            .put(TCF_NAMED_ENTITIES_TYPE_OPENNLP, "OpenNLP")
            .put(TCF_NAMED_ENTITIES_TYPE_STANFORD, "Stanford")
            .put(TCF_GEO_CAPITAL_FORMAT_NAME, "Name")
            .put(TCF_GEO_CONTINENT_FORMAT_NAME, "Name")
            .put(TCF_GEO_COORD_FORMAT_DEG_DEC, "Decimal Degrees")
            .put(TCF_GEO_COUNTRY_FORMAT_ISO3166_A2, "2-Letter Country Code")
            .put(TCF_SPEECHSIGNAL_1CH, "1 Channel")
            .put(TCF_SPEECHSIGNAL_WAV, "Waveform Audio File Format")
            .put(TCF_CORPUS_TUEBA_DDC, "TueBa-DDC")
            .put(TCF_CORPUS_DEWIKI, "DE Wiki")
            .put(TCF_CORPUS_TUEPP, "TuePP")
            .put(TCF_CORPUS_WEB_DMOZ, "Web DMOZ")
            .put(TCF_CORPUS_RESULTS_10, "  10")
            .put(TCF_CORPUS_RESULTS_50, "  50")
            .put(TCF_CORPUS_RESULTS_100, " 100")
            .build();
*/
   
   
}
