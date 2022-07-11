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

import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.DatabaseException;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.DatabaseException.ISSUE_TYPE;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.DataLoader;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public class DataLoaderClassPath extends DataLoaderBase implements DataLoader {

    private DataLoaderClassPath(String resourceName) {
        super(resourceName);
    }

    /**
     *
     * @param resourceName
     * @return
     */
    static public DataLoader create(String resourceName) {
        DataLoader retVal = new DataLoaderClassPath(resourceName);
        return retVal;
    }

    /**
     *
     * @return
     */
    @Override
    public DataLoader load() {
        if (resourceName == null) {
            throw new DatabaseException("resourceName must be !NULL", ISSUE_TYPE.LOAD);
        }
        //
        try ( InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            load(inStream);
        } catch (IOException e) {
            throw new DatabaseException("failed to load " + resourceName
                    + " from classpath", ISSUE_TYPE.LOAD, e);
        }

        return this;
    }

}
