package org.example.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SecondTest {

    @Test
    void testRoundCheck() {
        AreaChecker checker = new AreaChecker();

        BigDecimal x = BigDecimal.ONE;
        BigDecimal y = new BigDecimal("67");
        BigDecimal r = new BigDecimal("2");
        double k = 0.5;

        boolean result = checker.roundCheck(x, y, r, k);
        assertFalse(result, "ошибочка");
    }
}
