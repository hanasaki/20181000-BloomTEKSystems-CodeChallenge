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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.DataLoader;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.Database;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.DatabaseException;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.DatabaseException.ISSUE_TYPE;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
abstract public class DataLoaderBase implements DataLoader {

    /**
     *
     */
    protected final String resourceName;

    /**
     *
     */
    protected Database db;

    /**
     *
     * @param resourceName
     */
    protected DataLoaderBase(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     *
     * @param inStream
     * @return
     */
    final protected DataLoader load(InputStream inStream) {
        if (inStream == null) {
            throw new DatabaseException("inStream must be !NULL", ISSUE_TYPE.LOAD);
        }
        //
        ObjectMapper mapper = new ObjectMapper();
        RestResponseWrapperModel responseWrapper;
        try {
            responseWrapper = mapper.readValue(inStream, RestResponseWrapperModel.class);
            createDatabaseFromDataModel(responseWrapper);
        } catch (IOException e) {
            throw new DatabaseException("failed to load " + resourceName,
                    ISSUE_TYPE.LOAD, e);
        }
        db = createDatabaseFromDataModel(responseWrapper);

        return this;
    }

    /**
     *
     * @param model
     * @return
     */
    final protected Database createDatabaseFromDataModel(RestResponseWrapperModel model) {
        Database retVal = Database.create(model.getResponseModel());
        retVal.reindex();
        return retVal;
    }

    /**
     *
     * @return
     */
    @Override
    final public Database getDatabase() {
        return db;
    }

    /**
     *
     * @return
     */
    @Override
    final public String getResourceName() {
        return resourceName;
    }

}
