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
package com.hanaden.codingchallenge.teksystems.bloom.sdet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ResponseModel {

    /**
     *
     */
    public static final Validator VALIDATOR = new Validator();

    @JsonProperty("messages")
    private String messages[];
    @JsonProperty("result")
    private StateModel results[];

    /**
     * @return the messages
     */
    public String[] getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    /**
     * @return the results
     */
    public StateModel[] getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(StateModel[] results) {
        this.results = results;
    }

    /**
     *
     * @author FrederickBloom+TEKS201809@gmail.com
     * @version 1.0.0
     * @since 2018-10-01
     */
    public static class Validator extends ValidatorBase<ResponseModel> {

        /**
         *
         * @param data
         * @param assertions
         * @param throwOnFail
         * @return
         */
        @Override
        public SoftAssertions validate(ResponseModel data, SoftAssertions assertions, boolean throwOnFail) {
            super.validate(data, assertions, false);
            //
            assertions.assertThat(data.getMessages())
                    .as("no messages provided in response").isNotNull();
            assertions.assertThat(data.getResults())
                    .as("no results in response - not even empty list").isNotNull();
            //
            super.validate(data, assertions, false);
            //
            return assertions;
        }

    }
}
