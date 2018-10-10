package com.hanaden.codingchallenge.teksystems.bloom.sdet.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.DataLoader;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.Database;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.StateModel;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client.DataLoaderClassPath;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client.RestResponseWrapperModel;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.service.FinderService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
@ExtendWith(SpringExtension.class)
//@SpringBootTest()
//@SpringBootTest(classes = Main.class)
public class DynamicDataDrivenSetTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataDrivenSetTest.class);

    private final String resourceFileNameActual = "testDataSets/state.get.USA.subsetToFail_actual.json";
    private final String resourceFileNameExpected = "testDataSets/state.get.USA.subsetToFail_expected.json";

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
     * @param resoureName
     * @param stateToFind
     * @param expected
     * @throws Throwable
     */
    public void happyPathTest(String resoureName, String stateToFind, FinderService.CityCapitalResults expected) throws Throwable {
        //
        int expectedResultRecordCount = 1;
        //
        Database.CityCapitalResults results = findCityCapitalForState(resoureName, stateToFind);
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);
        //
        assertThat(results.city.contains(expected.city.iterator().next())).isTrue();
        assertThat(results.capital.contains(expected.capital.iterator().next())).isTrue();
        //
//        LOGGER.debug("success : state=" + stateToFind);
    }

    /**
     *
     * @param resoureName
     * @param stateToFind
     * @param expected
     * @throws Throwable
     */
    public void failPathTest(String resoureName, String stateToFind, FinderService.CityCapitalResults expected) throws Throwable {
        //
        int expectedResultRecordCount = 0;
        //
        Database.CityCapitalResults results = findCityCapitalForState(resoureName, stateToFind);
        //
        assertThat(results.city.size()).isEqualTo(expectedResultRecordCount);
        assertThat(results.capital.size()).isEqualTo(expectedResultRecordCount);
        //
        LOGGER.debug("success : state=" + stateToFind);
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

    @TestFactory
    Stream<DynamicTest> testDynamicTestStream() throws Throwable {
        //
        LOGGER.debug("******loading tests");
        List<DynamicTest> tests = new ArrayList<>();
        //
        ObjectMapper mapper = new ObjectMapper();
        RestResponseWrapperModel response;
        //
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceFileNameExpected);
        assertThat(inStream).isNotNull().withFailMessage("inputstream NULL {}", resourceFileNameExpected);
        //
        response = mapper.readValue(inStream, RestResponseWrapperModel.class);
        assertThat(response).isNotNull();
        //
        for (StateModel stateModel : response.getResponseModel().getResults()) {
            try {
                FinderService.CityCapitalResults expectedResults = new FinderService.CityCapitalResults();
                expectedResults.capital.add(stateModel.getCapital());
                expectedResults.city.add(stateModel.getCityLargest());
                DynamicTest dTest;
                // setup pass tests : by state name
                dTest = DynamicTest.dynamicTest("TEST:happyPath-byStateName:" + stateModel.toString(), ()
                        -> happyPathTest(resourceFileNameActual, stateModel.getName(), expectedResults)
                );
                tests.add(dTest);
                // setup pass tests : by state abbreviation
                dTest = DynamicTest.dynamicTest("TEST:happyPath-byStateAbbreviation" + stateModel.toString(), ()
                        -> happyPathTest(resourceFileNameActual, stateModel.getAbbreviation(), expectedResults)
                );
                tests.add(dTest);
                // setup fail tests
//                dTest = DynamicTest.dynamicTest("TEST:failPath:" + stateModel.toString(), ()
//                        // will fail becuase actual results will be found and expected is empty
//                        -> failPathTest(resourceFileNameActual, stateModel.getName(), new FinderService.CityCapitalResults())
//                );
//                tests.add(dTest);
            } catch (NullPointerException e) {
                LOGGER.debug("skipping state with NULL data " + stateModel.toString());
            }
        }
        LOGGER.debug("******loading tests - found " + tests.size() + " test count.");
        //
        return Stream.of(tests.toArray(new DynamicTest[tests.size()]));
    }

}
