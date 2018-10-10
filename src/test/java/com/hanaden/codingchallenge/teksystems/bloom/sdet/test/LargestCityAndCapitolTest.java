package com.hanaden.codingchallenge.teksystems.bloom.sdet.test;

import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.DataLoader;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.Database;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client.DataLoaderClassPath;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.service.FinderService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
@ExtendWith(SpringExtension.class)
//@SpringBootTest()
public class LargestCityAndCapitolTest {

    private final String resourceFileName = "testDataSets/state.get.USA.all_actual.json";

    /**
     *
     */
    @BeforeEach
    public void setUpTestMethod() {
    }

    /**
     *
     */
    @BeforeAll
    static public void setUpTestRun() {
    }

    /**
     *
     */
    @AfterEach
    public void tearDownTestMethod() {
    }

    /**
     *
     */
    @AfterAll
    static public void tearDownTestRun() {
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void happyPathTestFind_AR_Test() throws Throwable {
        //
        int expectedResultRecordCount = 1;
        //
        Database.CityCapitalResults results = findCityCapitalForState(resourceFileName, "AR");
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);
    }

    @Test
    public void happyPathTestFind_Arkansas_Test() throws Throwable {
        //
        int expectedResultRecordCount = 1;
        //
        Database.CityCapitalResults results = findCityCapitalForState(resourceFileName, "ARkansas");
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);

    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void happyPathTestFind_AK_Test() throws Throwable {
        //
        int expectedResultRecordCount = 1;
        //
        Database.CityCapitalResults results = findCityCapitalForState(resourceFileName, "AR");
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);
    }

    @Test
    public void happyPathTestFind_Alaska_Test() throws Throwable {
        //
        int expectedResultRecordCount = 1;
        //
        Database.CityCapitalResults results = findCityCapitalForState(resourceFileName, "aLASka");
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);
        //
        assertThat(results.city.contains("Anchorage")).isTrue();
        assertThat(results.capital.contains("Juneau")).isTrue();
    }

    /**
     * This is a known test case where the city is NULL : not in JSON and is
     * thus converted to ""
     *
     * @throws Throwable
     */
    @Test
    public void happyPathTestFind_AmericanSamoa_Test() throws Throwable {
        //
        int expectedResultRecordCount = 1;
        //
        Database.CityCapitalResults results = findCityCapitalForState(resourceFileName, "American Samoa");
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);
        //
        assertThat(results.city.contains("")).isTrue();
        assertThat(results.capital.contains("Pago Pago")).isTrue();
    }

    @Test
    public void failPathTestFind_notFound_Test() throws Throwable {
        //
        int expectedResultRecordCount = 0;
        String state = "not Found";
        //
        FinderService.CityCapitalResults results = findCityCapitalForState(resourceFileName, "notFound");
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);
    }

    private FinderService.CityCapitalResults findCityCapitalForState(String resourceFileName, String state) {
        DataLoader loader = DataLoaderClassPath.create(resourceFileName);
        //
        Database db = loader.build().getDatabase();
        //
        Database.CityCapitalResults retVal = db.findCityCapitalForState(state);
        //
        return retVal;
    }

}
