package com.hft.util;

import java.math.BigDecimal;

public class MathUtil {

	public static double round(double n, int digits) {
		return BigDecimal.valueOf(n).setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
