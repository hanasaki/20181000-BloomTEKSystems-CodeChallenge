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
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.DataLoader;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public class DataLoaderRestful extends DataLoaderBase implements DataLoader {

    private DataLoaderRestful(String resourceName) {
        super(resourceName);
    }

    /**
     *
     * @param resourceName
     * @return
     */
    static public DataLoader create(String resourceName) {
        DataLoader retVal = new DataLoaderRestful(resourceName);
        return retVal;
    }

    /**
     *
     * @return
     */
    @Override
    public DataLoader load() {
        if (resourceName == null) {
            throw new DatabaseException("resourceName must be !NULL", DatabaseException.ISSUE_TYPE.LOAD);
        }
        //
        try {
            RestTemplate restTemplate = new RestTemplate();
            RestResponseWrapperModel responseWrapper = restTemplate.getForObject(resourceName, RestResponseWrapperModel.class);
            db = createDatabaseFromDataModel(responseWrapper);
        } catch (Throwable e) {
            throw new DatabaseException("failed to load URL=" + resourceName
                    + " from restService", DatabaseException.ISSUE_TYPE.LOAD, e);
        }

        return this;
    }

}
