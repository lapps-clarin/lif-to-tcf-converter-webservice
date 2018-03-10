/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.exceptions;

import de.tuebingen.uni.sfs.clarind.utils.lapps.constants.ErrorMessage;

/**
 *
 * @author fazlehelahi
 */
public interface ConversionErrorMessage extends ErrorMessage{
    public static final String MESSAGE_CONVERSION_FAILED = "LIF to TCF CONVERSION ERROR: LIF to TCF conversion failed!!";
    public static final String MESSAGE_VOCABULARY_CONVERSION_FAILED = "LIF to TCF CONVERSION ERROR: Vocabulary conversion is failed!!";
    public static final String MESSAGE_LANGUAGE_CONVERSION_FAILED = "LIF to TCF CONVERSION ERROR: Language conversion failed from LIF to TCF failed!!";
    public static final String MESSAGE_TEXT_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: Text conversion failed from LIF to TCF failed!!";
    public static final String MESSAGE_TOKEN_LAYER_REQUIRED_FOR_SENTENCE_LAYER ="LIF to TCF CONVERSION ERROR: There is no token layer. A token layer is required for sentence layer";
    public static final String MESSAGE_STARTID_TOKEN_CONNECTION_NOT_FOUND ="LIF to TCF CONVERSION ERROR: no connected token found for this token-level-layer!!";
    public static final String MESSAGE_TOKEN_LAYER_REQUIRED_FOR_NAMEENTITY_LAYER ="LIF to TCF CONVERSION ERROR: There is no token layer in lif file. For conversion of LIF to TCF nameEntitty layer, a token layer is mandatory!!";
    public static final String MESSAGE_CONSTITUENT_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: the converion of constituent parser failed!!";
    public static final String MESSAGE_DEPENDENCY_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: the converion of dependency parser failed!!";
    public static final String MESSAGE_COREFERENCE_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: the converion of coreference parser failed!!";
}
