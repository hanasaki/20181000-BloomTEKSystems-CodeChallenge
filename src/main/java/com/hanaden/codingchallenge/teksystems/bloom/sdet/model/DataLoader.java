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

import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.Database;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public interface DataLoader {

    /**
     * builder method
     *
     * @return DataLoader
     */
    default public DataLoader build() {
        return load();
    }

    /**
     * Load the DB and index if needed convenience synonym for build
     *
     * @return DataLoader
     */
    public DataLoader load();

    /**
     *
     * @param inStream
     * @return DataLoader after loading from inputstream
     */
//    public DataLoader load(InputStream inStream);
    /**
     *
     * @return Dabase after loading
     */
    public Database getDatabase();

    /**
     * @return String - name of resource to load
     */
    public String getResourceName();

}
