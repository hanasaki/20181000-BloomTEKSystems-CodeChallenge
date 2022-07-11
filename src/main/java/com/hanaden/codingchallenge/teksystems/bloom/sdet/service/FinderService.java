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

import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.StateModel;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
public interface FinderService {

    /**
     *
     * @param state - len=2 : assume abbreviation, else assume full
     * @return
     */
    public CityCapitalResults findCityCapitalForState(final String state);

    /**
     *
     * @param state
     * @return
     */
    public Set<StateModel> findState(final String state);

    /**
     *
     */
    public static class CityCapitalResults {

        /**
         *
         */
        public Set<String> capital = new TreeSet<>();

        /**
         *
         */
        public Set<String> city = new TreeSet<>();

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            String capitals = "[" + capital.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
            String cities = "[" + city.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
            return "capitals=" + capitals + " : cities=" + cities;
        }

    }
}
