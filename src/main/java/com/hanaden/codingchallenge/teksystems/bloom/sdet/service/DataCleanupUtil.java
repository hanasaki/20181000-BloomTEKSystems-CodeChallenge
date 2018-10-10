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
package com.hanaden.codingchallenge.teksystems.bloom.sdet.service;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public interface DataCleanupUtil {

    /**
     *
     * @return
     */
    default public long getUnits() {
        return 0;
    }

    /**
     *
     * @return
     */
    default public long getValue() {
        return 0;
    }

}
