package com.yhd.gps;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:com/yhd/gps/config/spring/applicationContext-busy-test-deploy.xml" })
public abstract class BaseSpringTest extends AbstractDependencyInjectionSpringContextTests {

    static {
        String globalPath = System.getProperty("global.config.path");
        if(StringUtils.isBlank(globalPath)) {
            globalPath = System.getenv("global.config.path");
            if(StringUtils.isBlank(globalPath)) {
                System.setProperty("global.config.path", "/var/www/webapps/config/");
            }
        }
    }

    protected static final Long PM_INFO_ID_0 = 10000000L;
    protected static final Long PM_INFO_ID_1 = 10000001L;
    protected static final Long PM_INFO_ID_9 = 10000009L;

    protected static final List<Long> PM_INFO_IDS = new ArrayList<Long>(3);
    static {
        PM_INFO_IDS.add(PM_INFO_ID_0);
        PM_INFO_IDS.add(PM_INFO_ID_1);
        PM_INFO_IDS.add(PM_INFO_ID_9);
    }

    @Before
    public void onSetUp() throws Exception {
        super.onSetUp();
    }

    @After
    public void onTearDown() throws Exception {
    }

}