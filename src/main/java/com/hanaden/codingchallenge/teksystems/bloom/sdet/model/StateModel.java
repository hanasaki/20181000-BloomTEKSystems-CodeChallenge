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

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */

/*
{
  "RestResponse" : {
    "messages" : [ "Total [55] records found." ],
    "result" : [ {
      "id" : 1,
      "country" : "USA",
      "name" : "Alabama",
      "abbr" : "AL",
      "area" : "135767SKM",
      "largest_city" : "Birmingham",
      "capital" : "Montgomery"
    }, {
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class StateModel {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("country")
    private String country;
    @JsonProperty("name")
    private String name;
    @JsonProperty("abbr")
    private String abbreviation;
    @JsonProperty("area")
    private String area;
    @JsonProperty("largest_city")
    private String cityLargest;
    @JsonProperty("capital")
    private String capital;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the name : String
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the abbreviation : String of 2 Char
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the cityLargest
     */
    public String getCityLargest() {
        return cityLargest;
    }

    /**
     * @param cityLargest the cityLargest to set
     */
    public void setCityLargest(String cityLargest) {
        this.cityLargest = cityLargest;
    }

    /**
     * @return the capital
     */
    public String getCapital() {
        return capital;
    }

    /**
     * @param capital the capital to set
     */
    public void setCapital(String capital) {
        this.capital = capital;
    }

    /**
     * Standard method for data POJO : needed for comparision/collections
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, "id");
    }

    /**
     * Standard method for data POJO : needed for comparision/collections
     *
     * @return
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id");
    }

    @Override
    public String toString() {
        return new StringBuilder("State ")
                .append(getAbbreviation())
                .append('/')
                .append(getName())
                .append(':')
                .append(getCountry())
                .append(" captial=")
                .append(getCapital())
                .append(" largestCity=")
                .append(getCityLargest())
                .append(" size=")
                .append(getArea())
                .toString();
    }

}
