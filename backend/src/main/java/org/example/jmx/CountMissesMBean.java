package org.example.jmx;

import javax.management.openmbean.TabularData;

public interface CountMissesMBean {
    TabularData getMissStats() throws Exception;

    void registerPoint(String username, boolean isHit);

    int getTotalMisses();
}
