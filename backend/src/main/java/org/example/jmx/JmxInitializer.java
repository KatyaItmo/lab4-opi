package org.example.jmx;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.monitor.GaugeMonitor;
import java.lang.management.ManagementFactory;

@Singleton
@Startup
public class JmxInitializer {
    private final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    private GaugeMonitor monitor;
    private ObjectName monitorName;

    @PostConstruct
    public void initMBeans() {
        try {
            mbs.registerMBean(new CountMisses(), new ObjectName("org.example.jmx:type=CountMisses"));
            mbs.registerMBean(new Statistic(), new ObjectName("org.example.jmx:type=Statistic"));

            monitor = new GaugeMonitor();

            monitor.addObservedObject(new ObjectName("org.example.jmx:type=CountMisses"));
            monitor.setObservedAttribute("TotalMisses");
            monitor.setGranularityPeriod(1000);
            monitor.setThresholds(3, 1);
            monitor.setNotifyHigh(true);
            monitor.setNotifyLow(false);

            monitorName = new ObjectName("org.example.jmx:type=StreakGaugeMonitor");
            mbs.registerMBean(monitor, monitorName);
            monitor.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void deleteMBeans() {
        try {
            mbs.unregisterMBean(new ObjectName("org.example.jmx:type=CountMisses"));
            mbs.unregisterMBean(new ObjectName("org.example.jmx:type=Statistic"));

            monitor.stop();
            mbs.unregisterMBean(monitorName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
