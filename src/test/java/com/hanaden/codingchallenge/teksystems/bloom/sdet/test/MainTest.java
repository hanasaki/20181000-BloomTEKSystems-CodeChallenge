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
package com.hanaden.codingchallenge.teksystems.bloom.sdet.test;

import com.hanaden.codingchallenge.teksystems.bloom.sdet.main.Main;
import com.hanaden.codingchallenge.teksystems.bloom.sdet.main.MainCmdLineHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
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
public class MainTest {

    final private static Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

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

    @Test
    public void doMainFastFailTest() {
        MainCmdLineHandler cmd = MainCmdLineHandler.build(new String[]{});
        cmd.doMain();
    }

    @Test
    public void doMainFastFailNullTest() {
        MainCmdLineHandler cmd = MainCmdLineHandler.build(null);
        cmd.doMain();
    }

    private void doMain(String args[], boolean shouldFail) throws Throwable {
        try {
            Main.main(args);
        } catch (NullPointerException e) {
            if (!shouldFail) {
                // rethrow if it shouldn't have failed
                throw e;
            } else {
                LOGGER.debug("expected and got : ", e);
            }
        }
    }

    @TestFactory
    Stream<DynamicTest> testDynamicTestStream() throws Throwable {
        List<DynamicTest> tests = new ArrayList<>();
        //
        String testParamSuite[][][] = new String[][][]{
            // fail
            {
                {"--url", "http://localhost"}
            },
            {
                {"--url", "localhost"}
            },
            {
                {"--url", "default"}
            },
            {
                {"--url", "internal"}
            },
            // not fail
            {
                {"-s", "AK", "--url", "default"}
            },
            {
                {"--state", "AK", "--url", "internal"}
            },
            {
                {"-s", "AK"}
            }
        };
        boolean shouldFail[] = new boolean[]{
            true,
            true,
            true,
            true,
            true,//false,
            false,
            false
        };
        //
        for (int i = 0; i < shouldFail.length; i++) {
            final int iFinal = i;  // compiler workaround for lambda and final
            DynamicTest dTest;
            // setup pass tests : by state name
            dTest = DynamicTest.dynamicTest("TEST:doMain:" + Arrays.toString(testParamSuite[iFinal]),
                    () -> doMain(testParamSuite[iFinal][0], shouldFail[iFinal])
            );
            tests.add(dTest);
        }

        //
        LOGGER.debug("created " + tests.size() + " dynamic tests.");
        //
        return Stream.of(tests.toArray(new DynamicTest[tests.size()]));
    }
}
