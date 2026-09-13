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
    private PercentMiss percentBean;

    public TotalMissStats getMissBean() {
        return missBean;
    }

    public PercentMiss getPercentBean() {
        return percentBean;
    }

    @PostConstruct
    public void registerMBeans() {
        try {
            missBean = new TotalMissStats();
            mbs.registerMBean(missBean, new ObjectName("org.example.jmx:type=TotalMissStats"));

            percentBean = new PercentMiss();
            mbs.registerMBean(percentBean, new ObjectName("org.example.jmx:type=PercentMiss"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void unregisterMBeans() {
        try {
            mbs.unregisterMBean(new ObjectName("org.example.jmx:type=TotalMissStats"));
            mbs.unregisterMBean(new ObjectName("org.example.jmx:type=PercentMiss"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
