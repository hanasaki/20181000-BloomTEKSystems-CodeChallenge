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

import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.ResponseModel;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.StateModel;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.service.FinderService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public class Database implements FinderService {

    final private static Logger LOGGER = LoggerFactory.getLogger(Database.class);
    private final static String EXPECTED_UNITS = "SKM";

    private List<StateModel> statesArray;
    private List<Row<StateModel>> rows;
    private final AtomicLong maxAbsRowNumber = new AtomicLong(0);

    private Database() {
    }

    /**
     *
     * @param responseModel
     * @return
     */
    public static Database create(ResponseModel responseModel) {
        ResponseModel.VALIDATOR.validate(responseModel);
        //
        Database retVal = new Database();
        StateModel statesRawData[] = responseModel.getResults();
        // as a collection
        retVal.statesArray = Arrays.asList(statesRawData);
        return retVal;
    }

    /**
     *
     */
    public void reindex() {
        // prealloc known size to avoid resizing
        rows = new ArrayList<>(statesArray.size());
        maxAbsRowNumber.set(0); // reset row counter
        statesArray.forEach((stateModel) -> {
            Row<StateModel> row = new Row<>(maxAbsRowNumber.getAndAdd(1), stateModel);
            //
            String stateAbr = getAbbreviationClean(stateModel);
            String stateName = getNameClean(stateModel);
            Long stateArea = getAreaClean(stateModel);
            //
            rows.add(row);
        });

    }

    private String getAbbreviationClean(StateModel model) {
        return StringUtils.trimToEmpty(model.getAbbreviation());
    }

    private String getNameClean(StateModel model) {
        return StringUtils.trimToEmpty(model.getName());
    }

    private Long getAreaClean(StateModel model) {
        Long retVal = 0L;
        String areaString = StringUtils.trimToEmpty(model.getArea());
        //
        Pattern p = Pattern.compile("([0-9]*)([^0-9]*)");
        Matcher m = p.matcher(areaString);
        //
        if (m.matches()) {
            String magnitudeString = m.group(1);
            String unitsString = m.group(2).toUpperCase();
            if (!EXPECTED_UNITS.equals(unitsString)) {
                throw new DatabaseException("unexpected units = " + unitsString, DatabaseException.ISSUE_TYPE.DATA_VALIDATION);
            }
        } else {
            // no match
            throw new DatabaseException("Unparsable area data = " + model.getArea(), DatabaseException.ISSUE_TYPE.DATA_VALIDATION);
        }

        return retVal;
    }

    /**
     *
     * @return
     */
    public int size() {
        return statesArray.size();
    }

    /**
     * find city and capital for a state. NULL values of city and/or capital are
     * converted to ""
     *
     * @param stateToFind - if NULL, assume ""
     * @return
     */
    @Override
    public CityCapitalResults findCityCapitalForState(String stateToFind) {
        CityCapitalResults retVal = new CityCapitalResults();
        //
        final String stateToFindFixup = StringUtils.trimToEmpty(stateToFind); // assume default ""
        //
        // len=2 means abreviation
        if (stateToFind.length() == 2) { // yes... its a magic number :)
            this.rows
                    .stream()
                    .filter(row -> row.getRowData().getAbbreviation().equalsIgnoreCase(stateToFindFixup) == Boolean.TRUE)
                    .forEach((Row<StateModel> row) -> {
                        retVal.city.add(row.getRowData().getCityLargest());
                        retVal.capital.add(row.getRowData().getCapital());
                    });
            //
        } else {
            // is state name
            this.rows
                    .stream()
                    .filter(row -> row.getRowData().getName().equalsIgnoreCase(stateToFindFixup) == Boolean.TRUE)
                    .forEach((Row<StateModel> row) -> {
                        StateModel stateModel = row.getRowData();
                        retVal.city.add(StringUtils.trimToEmpty(stateModel.getCityLargest()));
                        retVal.capital.add(StringUtils.trimToEmpty(stateModel.getCapital()));
                    });
        }
        //
        return retVal;
    }

    @Override
    public Set<StateModel> findState(String stateToFind) {
        Set<StateModel> retVal = new HashSet<>();
        //
        final String stateToFindFixup = StringUtils.trimToEmpty(stateToFind); // assume default ""
        this.rows.stream()
                .filter(row -> row.getRowData().getAbbreviation().equalsIgnoreCase(stateToFindFixup) == Boolean.TRUE)
                .forEach((Row<StateModel> row) -> {
                    retVal.add(row.getRowData());
                });
        //
        this.rows.stream()
                .filter(row -> row.getRowData().getName().equalsIgnoreCase(stateToFindFixup) == Boolean.TRUE)
                .forEach((Row<StateModel> row) -> {
                    retVal.add(row.getRowData());
                });

        return retVal;
    }

}
