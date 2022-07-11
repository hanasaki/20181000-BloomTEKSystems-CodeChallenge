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

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public class DatabaseException extends RuntimeException {

    private ISSUE_TYPE type;
    private static final long serialVersionUID = 1L;

    /**
     * ENUMs of issues/casuses for Exception. Could use specific subclasses
     * however no need at the moment.
     */
    public enum ISSUE_TYPE {

        /**
         *
         */
        GENERAL,
        /**
         *
         */
        LOAD,
        /**
         *
         */
        DATA_VALIDATION
    }

    /**
     *
     * @param msg
     * @param cause
     */
    public DatabaseException(String msg, Throwable cause) {
        this(msg, ISSUE_TYPE.GENERAL, cause);
    }

    /**
     *
     * @param msg
     * @param type
     */
    public DatabaseException(String msg, ISSUE_TYPE type) {
        this(msg, type, null);
    }

    /**
     *
     * @param msg
     * @param type
     * @param cause
     */
    public DatabaseException(String msg, ISSUE_TYPE type, Throwable cause) {
        super(msg, cause);
        this.type = type;
    }

    /**
     *
     * @return
     */
    public ISSUE_TYPE getIssueType() {
        return type;
    }

    /**
     *
     * @return
     */
    @Override
    public String getLocalizedMessage() {
        return "Type=" + getIssueType() + " : " + super.getLocalizedMessage();
    }

}
