package org.example.model;

import java.math.BigDecimal;

public class AreaChecker {
	
	public static boolean checkHit(String xStr, String yStr, String rStr, boolean isClick) {
		try {
			BigDecimal x = new BigDecimal(xStr.replace(',', '.'));
            BigDecimal y = new BigDecimal(yStr.replace(',', '.'));
            BigDecimal r = new BigDecimal(rStr.replace(',', '.'));
            
            if (!validateForm(x, y, r, isClick)) {
            	return false;
            }
            
            if (r.compareTo(BigDecimal.ZERO) < 0) {
            	x = x.negate();
            	y = y.negate();
            	r = r.negate();
            }
            
            if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            	return rectangleCheck(x, y, r, 0.5, 1);
            }
            
            if (x.compareTo(BigDecimal.ZERO) < 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            	return triangleCheck(x, y, r, 0.5, 1);
            }
            
            if (x.compareTo(BigDecimal.ZERO) < 0 && y.compareTo(BigDecimal.ZERO) < 0) {
            	return false;
            }
            
            if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) < 0) {
            	return roundCheck(x, y, r, 0.5);
            }
            
            return true;
		} catch (Exception ex) {
			System.err.println("Ошибка во время проверки точки: " + ex.getMessage());
			return false;
		}
	}

	private static boolean rectangleCheck(BigDecimal x, BigDecimal y, BigDecimal r, double kx, double ky) {
		return x.abs().compareTo(r.multiply(new BigDecimal(kx)).abs()) <= 0 &&
				y.abs().compareTo(r.multiply(new BigDecimal(ky)).abs()) <= 0;
	}

	private static boolean triangleCheck(BigDecimal x, BigDecimal y, BigDecimal r, double kx, double ky) {
		BigDecimal a = r.multiply(BigDecimal.valueOf(kx)).abs();
		BigDecimal b = r.multiply(BigDecimal.valueOf(ky)).abs();
		
		return (x.abs().multiply(b).add(y.abs().multiply(a))).compareTo(a.multiply(b)) <= 0;
	}

	protected static boolean roundCheck(BigDecimal x, BigDecimal y, BigDecimal r, double k) {
		return x.pow(2).add(y.pow(2)).compareTo(r.multiply(new BigDecimal(k)).pow(2)) <= 0;
	}

	protected static boolean validateForm(BigDecimal x, BigDecimal y, BigDecimal r, boolean isClick) {
		try {
			boolean xValid;
			boolean yValid;
			boolean rValid;
			
			if (isClick) {
				xValid = x.compareTo(new BigDecimal("-5")) >= 0 && x.compareTo(new BigDecimal("5")) <= 0;
                yValid = y.compareTo(new BigDecimal("-5")) >= 0 && y.compareTo(new BigDecimal("5")) <= 0;
			} else {
				xValid = x.compareTo(new BigDecimal("-3")) >= 0 && x.compareTo(new BigDecimal("5")) <= 0;
                yValid = y.compareTo(new BigDecimal("-3")) >= 0 && y.compareTo(new BigDecimal("5")) <= 0;
			}
			
			rValid = r.compareTo(new BigDecimal("-3")) >= 0 && r.compareTo(new BigDecimal("5")) <= 0;
			
			return xValid && yValid && rValid;
		} catch (Exception ex) {
			System.err.println("Ошибка валидации: " + ex.getMessage());
			return false;
		}
	}

	/*private static boolean doubleValidation(BigDecimal num, int i, int j) {
		BigDecimal minValue = new BigDecimal(i);
		BigDecimal maxValue = new BigDecimal(j);
		
		return num.compareTo(minValue) >= 0 && num.compareTo(maxValue) <= 0;
	}

	private static boolean halfValidation(BigDecimal num, double i, double j) {
		while (i <= j) {
			if (num.equals(new BigDecimal(i))) {
				return true;
			}
			i += 0.5;
		}
		
		return false;
	}
	
	private static boolean integerValidation(BigDecimal num, int i, int j) {
		try {
			int numInt = num.intValueExact();
			
			return numInt >= i && numInt <= j;
		} catch (ArithmeticException ex) {
			System.err.println("Арифметическая ошибка во время проверки диапазона: " + ex.getMessage());
			return false;
		}
		
		
	}*/
}
