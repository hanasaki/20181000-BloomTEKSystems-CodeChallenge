/*
 * Copyright 2010-** : Frederick Bloom
 *
 * Licensed under : TBD
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hanaden.codingchallenge.teksystems.bloom.sdet.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client.RestResponseWrapperModel;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.DataLoader;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.restws.client.DataLoaderClassPath;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.Database;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.StateModel;
import java.io.InputStream;
import java.util.Set;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
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
public class DataLoaderBaseTest {

    /**
     *
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(DataLoaderBaseTest.class);

    /**
     *
     */
    protected final String resourceFileName = "testDataSets/state.get.USA.all_actual.json";

    /**
     *
     */
    protected final String resourceFileNameFail1 = "testDataSets/state.get.USA.all_parseIssue.1.json";

    /**
     *
     */
    protected final String resourceFileNameFail2 = "testDataSets/state.get.USA.all_parseIssue.2.json";

    /**
     *
     */
    protected final String resourceFileNameFail3 = "testDataSets/state.get.fail-badIndexNumber.json";

    /**
     *
     */
    protected final String resourceFileNameFail4 = "testDataSets/state.get.USA.badAreaUnits.json";

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
    public void stateModelObjectMethodsTest() throws Throwable {
        final int expectedResultRecordCount = 55;
        DataLoader loader = DataLoaderClassPath.create(resourceFileName);
        //
        Database db = loader.build().getDatabase();
        //
        assertThat(db.size()).isGreaterThan(0);
        // known value from static test data
        assertThat(db.size()).isEqualTo(expectedResultRecordCount);
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void happyPathTest() throws Throwable {
        final int expectedResultRecordCount = 55;
        DataLoader loader = DataLoaderClassPath.create(resourceFileName);
        //
        Database db = loader.build().getDatabase();
        //
        assertThat(db.size()).isGreaterThan(0);
        // known value from static test data
        assertThat(db.size()).isEqualTo(expectedResultRecordCount);
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void stateModelObjectMethodTest() throws Throwable {
        DataLoader loader = DataLoaderClassPath.create(resourceFileName);
        //
        Database db = loader.build().getDatabase();
        //
        Set<StateModel> states = db.findState("AR");
        assertThat(states.size() > 0).isTrue().as("expected at least one state");
        //
        StateModel state = states.iterator().next();
        assertThat(state).isEqualTo(state);
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void failBadAreaUnitsTest() throws Throwable {
        final int expectedResultRecordCount = 55;
        DataLoader loader = DataLoaderClassPath.create(resourceFileNameFail4);
        //
        Database db = null;
        //
        Throwable thrown = catchThrowable(() -> {
            loader.build();
        });
        assertThat(thrown).hasStackTraceContaining("unexpected units");
        assertThat(thrown).hasStackTraceContaining("SMILES");
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void failNoMessagesTest() throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        RestResponseWrapperModel response;
        //
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceFileNameFail1);
        assertThat(inStream).isNotNull().withFailMessage("inputstream NULL {}", resourceFileNameFail1);
        //
        response = mapper.readValue(inStream, RestResponseWrapperModel.class);
        assertThat(response).isNotNull();
        //
        Throwable thrown = catchThrowable(() -> {
            RestResponseWrapperModel.VALIDATOR.validate(response);
        });
        //
        assertThat(thrown).hasStackTraceContaining("no messages");
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void failNoMessagesAndNoResultsTest() throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        RestResponseWrapperModel response;
        //
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceFileNameFail2);
        assertThat(inStream).isNotNull().withFailMessage("inputstream NULL {}", resourceFileNameFail2);
        //
        response = mapper.readValue(inStream, RestResponseWrapperModel.class);
        assertThat(response).isNotNull();
        //
        Throwable thrown = catchThrowable(() -> {
            RestResponseWrapperModel.VALIDATOR.validate(response);
        });
        //
        assertThat(thrown).hasStackTraceContaining("not even");
        assertThat(thrown).hasStackTraceContaining("no messages");
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void failCheckForMissingIndexNumberTest() throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        RestResponseWrapperModel response;
        //
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceFileNameFail3);
        assertThat(inStream).isNotNull().withFailMessage("inputstream NULL {}", resourceFileNameFail3);
        //
        response = mapper.readValue(inStream, RestResponseWrapperModel.class);
        assertThat(response).isNotNull();
        //
        // check known hardcoded values from test file
        assertThat(response.getResponseModel().getResults()[0].getId()).isNotNull();
        assertThat(response.getResponseModel().getResults()[1].getId()).isNull(); // expected NULL
        assertThat(response.getResponseModel().getResults()[2].getId()).isNotNull();
    }
}
