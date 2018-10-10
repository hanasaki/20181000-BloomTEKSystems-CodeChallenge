/**
 *
 * Copyright (C) 2018-*, Frederick Bloom. All rights reserved.
 *
 * Licensed: "As-Is" WITHOUT WARRANTIES ANY KIND,
 * either express or implied.  Permission given to reuse source/object code
 * so long as credit provided to original author in copied/derived source and
 * all visible license/credits to the consumer of this code.
 *
 */
package com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.ResponseModel;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.ValidatorBase;
import org.assertj.core.api.SoftAssertions;

/*
{
  "RestResponse" : {
    "messages" : [ "Total [55] records found." ],
    "result" : [ {
      "id" : 1,
      "country" : "USA",
      "name" : "Alabama",
      "abbr" : "AL",
      "area" : "135767SKM",
      "largest_city" : "Birmingham",
      "capital" : "Montgomery"
    }, {
 */
/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponseWrapperModel {

    /**
     *
     */
    public static final Validator VALIDATOR = new Validator();

    @JsonProperty("RestResponse")
    private ResponseModel responseModel;

    /**
     * @return the responseModel
     */
    public ResponseModel getResponseModel() {
        return responseModel;
    }

    /**
     * @param responseModel the responseModel to set
     */
    public void setResponseModel(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    /**
     *
     * @author FrederickBloom+TEKS201809@gmail.com
     * @version 1.0.0
     * @since 2018-10-01
     */
    public static class Validator extends ValidatorBase<RestResponseWrapperModel> {

        /**
         *
         * @param data
         * @param assertions
         * @param throwOnFail
         * @return
         */
        @Override
        public SoftAssertions validate(RestResponseWrapperModel data, SoftAssertions assertions, boolean throwOnFail) {
            super.validate(data, assertions, false);
            //
            ResponseModel rrm = data.getResponseModel();
            ResponseModel.VALIDATOR.validate(rrm, assertions, false);
            //
            super.validate(data, assertions, throwOnFail);
            //
            return assertions;
        }
    }
}
