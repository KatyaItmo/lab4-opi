package org.example.jmx;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.*;
import javax.management.monitor.GaugeMonitor;
import java.lang.management.ManagementFactory;

@Startup
@Singleton
public class JmxInizializer {
    private MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    private ObjectName missName;
    private ObjectName percentName;
    private ObjectName monitorName;

    private TotalMissStats missBean;
    private PercentMiss percentBean;
    private GaugeMonitor monitor;

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
            missName = new ObjectName("org.example.jmx:type=TotalMissStats");
            mbs.registerMBean(missBean, missName);

            percentBean = new PercentMiss();
            percentName = new ObjectName("org.example.jmx:type=PercentMiss");
            mbs.registerMBean(percentBean, percentName);

            initGaugeMonitor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGaugeMonitor() {
        try {
            monitor = new GaugeMonitor();
            monitorName = new ObjectName("org.example.jmx:type=StreakGaugeMonitor");

            mbs.registerMBean(monitor, monitorName);

            monitor.addObservedObject(missName);

            monitor.setObservedAttribute("StreakMiss");
            monitor.setThresholds(3, 1);
            monitor.setNotifyLow(false);
            monitor.setNotifyHigh(true);
            monitor.setGranularityPeriod(1000);

            monitor.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void unregisterMBeans() {
        try {
            mbs.unregisterMBean(missName);
            mbs.unregisterMBean(percentName);

            monitor.stop();
            mbs.unregisterMBean(monitorName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
