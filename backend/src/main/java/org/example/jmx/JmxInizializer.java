package org.example.jmx;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.*;
import java.lang.management.ManagementFactory;

@Startup
@Singleton
public class JmxInizializer {
    private MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    private TotalMissStats missBean;

    public TotalMissStats getMissBean() {
        return missBean;
    }

    @PostConstruct
    public void registerMBeans() {
        try {
            missBean = new TotalMissStats();
            mbs.registerMBean(missBean, new ObjectName("org.example.jmx:type=TotalMissStats"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void unregisterMBeans() {
        try {
            mbs.unregisterMBean(new ObjectName("org.example.jmx:type=TotalMissStats"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
