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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author FrederickBloom+TEKS201809@gmail.com
 * @version 1.0.0
 * @since 2018-10-01
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.hanaden")
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private MainCmdLineHandler cmdLineHandler;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainCmdLineHandler cmdLineHandler = MainCmdLineHandler.build(args);
        if (!cmdLineHandler.isIsValid()) {
            // System.exit(1); // need to catch shutdown hook for this in tests
            return;
        }
        //
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(Main.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);

        Main app = context.getBean(Main.class);
        app.cmdLineHandler = cmdLineHandler;
        LOGGER.debug("starting => " + app.getClass().getCanonicalName());
        app.start();
    }

    private void start() {
        cmdLineHandler.doMain();
    }

    /**
     *
     * @param ctx
     * @return
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            System.out.println(MessageFormat.format("*********** Running: {0}", ctx.getApplicationName()));
        };
    }
}
