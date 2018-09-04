/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tuebingen.uni.sfs.lapps.lifconverter.api;

import de.tuebingen.uni.sfs.lapps.core.api.LifErrorMessage;

/**
 *
 * @author fazlehelahi
 */
public interface ErrorMessage extends LifErrorMessage{
    public static final String MESSAGE_CONVERSION_FAILED = "LIF to TCF CONVERSION ERROR: LIF to TCF conversion failed!!";
    public static final String MESSAGE_VOCABULARY_CONVERSION_FAILED = "LIF to TCF CONVERSION ERROR: Vocabulary conversion is failed!!";
    public static final String MESSAGE_LANGUAGE_CONVERSION_FAILED = "LIF to TCF CONVERSION ERROR: Language conversion failed from LIF to TCF failed!!";
    public static final String MESSAGE_TEXT_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: Text conversion failed from LIF to TCF failed!!";
    public static final String MESSAGE_TOKEN_LAYER_REQUIRED ="LIF to TCF CONVERSION ERROR: There is no token layer. A token layer is required!!";
    public static final String MESSAGE_SENTENCE_LAYER_REQUIRED ="LIF to TCF CONVERSION ERROR: There is no sentence layer. A sentence layer is required!!";
    public static final String MESSAGE_SENTENCE_BOUNDERY_NOT_GIVEN ="LIF to TCF CONVERSION ERROR: the sentence boundery is not given to in lif sentence layer";
    public static final String MESSAGE_STARTID_TOKEN_CONNECTION_NOT_FOUND ="LIF to TCF CONVERSION ERROR: no connected token found for this token-level-layer!!";
    public static final String MESSAGE_TOKEN_LAYER_REQUIRED_FOR_NAMEENTITY_LAYER ="LIF to TCF CONVERSION ERROR: There is no token layer in lif file. For conversion of LIF to TCF nameEntitty layer, a token layer is mandatory!!";
    public static final String MESSAGE_CONSTITUENT_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: the converion of constituent parser failed!!";
    public static final String MESSAGE_DEPENDENCY_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: the converion of dependency parser failed!!";
    public static final String MESSAGE_COREFERENCE_CONVERSION_FAILED ="LIF to TCF CONVERSION ERROR: the converion of coreference parser failed!!";
}