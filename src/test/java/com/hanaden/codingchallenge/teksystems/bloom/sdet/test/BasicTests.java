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
import com.hanaden.codingchallenge.teksystems.bloom.sdet.db.DatabaseException;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.model.StateModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
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
//@SpringBootTest(classes = Main.class)
@ExtendWith(SpringExtension.class)
public class BasicTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicTests.class);

    private final String resourceFileName = "testDataSets/state.get.USA.all_actual.json";
    private final String resourceFileNameFail1 = "testDataSets/state.get.USA.all_parseIssue.1.json";
    private final String resourceFileNameFail2 = "testDataSets/state.get.USA.all_parseIssue.2.json";
    private final String resourceFileNameFail3 = "testDataSets/state.get.fail-badIndexNumber.json";

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
     */
    @Test
    public void databaseExceptionFullTest() {
        String msg = "exception msg @ " + new Date().toInstant().toString();
        try {
            throw new DatabaseException(msg, (Throwable) null);
        } catch (DatabaseException e) {
            assertThat(e.getLocalizedMessage()).contains(msg);
        }
    }

    /**
     *
     */
    @Test
    public void databaseExceptionDefaultCTorTest() {
        String msg = "exception msg @ " + new Date().toInstant().toString();
        try {
            throw new DatabaseException(msg, DatabaseException.ISSUE_TYPE.LOAD);
        } catch (DatabaseException e) {
            assertThat(e.getLocalizedMessage()).contains(msg);
            assertThat(e.getIssueType() == DatabaseException.ISSUE_TYPE.LOAD).isTrue();
        }
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void happyPathTest() throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        RestResponseWrapperModel response;
        //
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceFileName);
        assertThat(inStream).isNotNull();
        //
        response = mapper.readValue(inStream, RestResponseWrapperModel.class);
        //
        RestResponseWrapperModel.VALIDATOR.validate(response);
        LOGGER.debug("success");
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void failNoMessagesTest() throws Throwable {
        RestResponseWrapperModel response;
        //
        response = load(resourceFileNameFail1);
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
        RestResponseWrapperModel response;
        //
        response = load(resourceFileNameFail2);
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
        RestResponseWrapperModel response;
        //
        response = load(resourceFileNameFail3);

        // check known hardcoded values from test file
        assertThat(response.getResponseModel().getResults()[0].getId()).isNotNull();
        assertThat(response.getResponseModel().getResults()[1].getId()).isNull(); // expected NULL
        assertThat(response.getResponseModel().getResults()[2].getId()).isNotNull();
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void stateModelEqualTest() throws Throwable {
        StateModel lhs = new StateModel();
        StateModel rhs;
        //
        lhs.setId(1);
        lhs.setAbbreviation("ab");
        lhs.setArea("area");
        lhs.setCapital("capitol");
        lhs.setCityLargest("biggest city");
        lhs.setCountry("USA");
        lhs.setName("myName");
        //
        rhs = (StateModel) BeanUtils.cloneBean(lhs);
        //
        assertThat(EqualsBuilder.reflectionEquals(lhs, rhs)).isTrue();
        //
        assertThat(lhs.getId().equals(rhs.getId()));
        assertThat(lhs.getAbbreviation().equals(rhs.getAbbreviation()));
        assertThat(lhs.getArea().equals(rhs.getArea()));
        assertThat(lhs.getCapital().equals(rhs.getCapital()));
        assertThat(lhs.getCityLargest().equals(rhs.getCityLargest()));
        assertThat(lhs.getCountry().equals(rhs.getCountry()));
        assertThat(lhs.getName().equals(rhs.getName()));
        //
        assertThat(lhs.equals(rhs));
        // now change the ID and assure it is ignored in compare
        rhs.setId(lhs.getId() + 1);
        assertThat(lhs.getId()).isNotEqualTo(rhs.getId());
        assertThat(lhs).isEqualTo(rhs); // check equals
        assertThat(lhs.hashCode()).isEqualTo(rhs.hashCode()); // check hascode
        // no change something that should make them not equal
        rhs.setId(lhs.getId()); // reest the ID's to equal
        assertThat(lhs.getId()).isEqualTo(rhs.getId());
        rhs.setName(rhs.getName() + new Date().toString());
        assertThat(lhs).isNotEqualTo(rhs); // check equals
        assertThat(lhs.hashCode()).isNotEqualTo(rhs.hashCode()); // check hashcode
    }

    private RestResponseWrapperModel load(String resourceName) {
        ObjectMapper mapper = new ObjectMapper();
        RestResponseWrapperModel retVal;
        //
        try (InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            assertThat(inStream).isNotNull().withFailMessage("inputstream NULL {}", resourceFileNameFail3);
            retVal = mapper.readValue(inStream, RestResponseWrapperModel.class);
            assertThat(retVal).isNotNull();
        } catch (IOException e) {
            throw new DatabaseException("load fail " + resourceName, DatabaseException.ISSUE_TYPE.LOAD);
        }
        //
        return retVal;
    }
}
