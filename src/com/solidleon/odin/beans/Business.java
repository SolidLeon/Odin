package com.solidleon.odin.beans;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Business {

	// TEMPLATE DATA
	public String name;	
	public BigDecimal basePrice;
	public BigDecimal baseProfit;
	public long duration;
	
	// RUNTIME DATA
	public long elapsedTime;
	private BigDecimal profitPerSecond;
	
	
	public BigDecimal getProfitPerSecond() {
		if (profitPerSecond == null) {
			BigDecimal biDuration = new BigDecimal(Double.toString(duration / 1000.0));
			profitPerSecond = baseProfit.divide(biDuration);
			System.out.println(baseProfit);
			System.out.println(biDuration);
			System.out.println(profitPerSecond);
		}
		return profitPerSecond;
	}
}
