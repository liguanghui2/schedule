package com.yhd.gps;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.spring.annotation.SpringApplicationContext;

@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext({"classpath*:com/yhd/gps/config/spring/applicationContext-busy-test-deploy.xml" })
public abstract class GpsBaseSpringTest extends AbstractDependencyInjectionSpringContextTests {
    // @SpringBean("busyDataCache")
    // private BusyDataCache busyDataCache;

    static {
        String globalPath = System.getProperty("global.config.path");
        if(StringUtils.isBlank(globalPath)) {
            globalPath = System.getenv("global.config.path");
            if(StringUtils.isBlank(globalPath)) {
                System.setProperty("global.config.path", "/var/www/webapps/config/");
            }
        }
    }

    @Before
    public void onSetUp() throws Exception {
        super.onSetUp();
    }

    @After
    public void onTearDown() throws Exception {
    }
}