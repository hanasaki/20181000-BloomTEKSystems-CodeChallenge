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

import org.assertj.core.api.SoftAssertions;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @param <T>
 * @since 2018-10-01
 */
public class ValidatorBase<T> {

    /**
     *
     * @param data
     * @return
     */
    final public SoftAssertions validate(T data) {
        SoftAssertions softAssertion = new SoftAssertions();
        return validate(data, softAssertion, true);
    }

    /**
     *
     * @param data
     * @param softAssertion
     * @param throwOnFail
     * @return
     */
    public SoftAssertions validate(T data, SoftAssertions softAssertion, boolean throwOnFail) {
        softAssertion.assertThat(softAssertion).isNotNull();
        softAssertion.assertThat(data).isNotNull();
        //
        if (throwOnFail) {
            softAssertion.assertAll();
        }
        return softAssertion;
    }
}
