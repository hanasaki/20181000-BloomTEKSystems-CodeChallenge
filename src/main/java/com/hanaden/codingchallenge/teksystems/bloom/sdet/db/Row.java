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
package com.hanaden.codingchallenge.teksystems.bloom.sdet.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @param <T> data type of the row data
 * @since 2018-10-01
 */
public class Row<T> {

//    record Range(int lo, int hi) {
//        public Range  {
//            if (lo > hi) /* referring here to the implicit constructor parameters */ {
//                throw new IllegalArgumentException(String.format("(%d,%d)", lo, hi));
//            }
//        }
//    }
    final private static Logger LOGGER = LoggerFactory.getLogger(Row.class);
    private final T rowData;
    private final Long index;

    /**
     *
     * @param index
     * @param rowData
     */
    public Row(long index, T rowData) {
        this.rowData = rowData;
        this.index = index;
    }

    /**
     * @return the rowData
     */
    public T getRowData() {
        return rowData;
    }

    /**
     * @return the index
     */
    public Long getIndex() {
        return index;
    }

}
