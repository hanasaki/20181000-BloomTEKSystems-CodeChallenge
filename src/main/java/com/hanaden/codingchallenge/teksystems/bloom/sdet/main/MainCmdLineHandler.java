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
package com.hanaden.codingchallenge.teksystems.bloom.sdet.main;

import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.Database;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.DatabaseException;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.DataLoader;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client.DataLoaderClassPath;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client.DataLoaderRestful;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.service.FinderService;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public class MainCmdLineHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainCmdLineHandler.class);
    public static String CLASSPATH_JSON_DATA_FILENAME = "data/cannedJSONdata.json";
    public static String FLAG_USE_REST_URL_DEFAULT = "default";
    public static final String REST_URL_DEFAULT = "http://services.groupkt.com/state/get/USA/all";
    public static final String FLAG_USE_INTERANL_JAR_DATAFILE = "internal";

    @Option(name = "--url", usage = "URL of restful data service [internal=useCannedDataInJAR, notSet(=internal), value=http://services.groupkt.com/state/get/USA/all]")
    private String url;

    @Option(name = "-s", required = true, usage = "2 letter state abbreviation full state name", aliases = "--state")
    private String state;
    private boolean isValid = false;
    private DataLoader dataLoader;
    private boolean useClasspath;

    private MainCmdLineHandler() {

    }

    /**
     *
     */
    public static MainCmdLineHandler build(String args[]) {
        MainCmdLineHandler me = new MainCmdLineHandler();
        CmdLineParser parser = new CmdLineParser(me);
        //
        try {
            parser.parseArgument(args);
            if (me.url == null || FLAG_USE_INTERANL_JAR_DATAFILE.equalsIgnoreCase(me.url)) {
                // USE INTERNAL IF NO URL OR "--url INTERNAL"
                me.useClasspath = true;
                me.url = CLASSPATH_JSON_DATA_FILENAME;
                me.dataLoader = DataLoaderClassPath.create(CLASSPATH_JSON_DATA_FILENAME);
            } else if (FLAG_USE_REST_URL_DEFAULT.equalsIgnoreCase(me.url)) {
                // USE "--url "default"
                me.useClasspath = false;
                me.url = REST_URL_DEFAULT;
                me.dataLoader = DataLoaderRestful.create(me.url);
            } else {
                // use "--url [cmdLine]"
                me.useClasspath = false;
                me.dataLoader = DataLoaderRestful.create(me.url);
            }
            //
            me.isValid = true;
        } catch (NullPointerException | CmdLineException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("java [jarFileName] [options...]");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
        }

        return me;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    public DataLoader getDataLoader() {
        return dataLoader;
    }

    public boolean isLoadFromClasspath() {
        return useClasspath;
    }

    public boolean isLoadFromRestful() {
        return !useClasspath;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @return the isValid
     */
    public boolean isIsValid() {
        return isValid;
    }

    public void doMain() {
        if (!isValid) {
            System.out.println("errors in setup / config");
            return;
        }
        //
        System.out.println("\n==>finding 'largest city and capital' for state='"
                + getState()
                + "' restData@URL=" + getUrl()
                + (isLoadFromClasspath() ? (" using classpathLoader resource '" + getDataLoader().getResourceName()) : ""));
        //
        try {
            dataLoader.build();
            Database db = dataLoader.getDatabase();

            FinderService.CityCapitalResults results = db.findCityCapitalForState(state);
            if (results == null || (results.capital.isEmpty() & results.city.isEmpty())) {
                LOGGER.warn("state=" + state + " NOT FOUND");
                System.out.println("state=" + state + " NOT FOUND");
            } else {
                LOGGER.info("state = " + state + " : " + results.toString());
                System.out.println("state = " + state + " : " + results.toString());
            }
        } catch (NullPointerException | DatabaseException e) {
            System.out.println(e.getLocalizedMessage());
            LOGGER.warn(e.getLocalizedMessage());
        }
    }
}
