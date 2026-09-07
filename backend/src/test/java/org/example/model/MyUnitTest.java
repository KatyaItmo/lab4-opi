package org.example.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyUnitTest {

    @Test
    void testPositive() {
        AreaChecker checker = new AreaChecker();

        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal r = BigDecimal.ONE;
        boolean isClick = false;

        boolean result = checker.validateForm(x, y, r, isClick);
        assertTrue(result, "Форма должна быть валидной для точки (0,0) и R = 1");
    }

    @Test
    void testNegative() {
        AreaChecker checker = new AreaChecker();

        BigDecimal x = new BigDecimal("67");
        BigDecimal y = new BigDecimal("676767");
        BigDecimal r = new BigDecimal("1984");
        boolean isClick = true;

        boolean result = checker.validateForm(x, y, r, isClick);
        assertFalse(result, "Форма невалидна для 67");
    }
}