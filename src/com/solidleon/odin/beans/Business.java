package com.solidleon.odin.beans;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Business {

	// TEMPLATE DATA
	public String name;	
	public BigDecimal basePrice;
	public BigDecimal baseProfit;
	public long duration;
	public BigDecimal managerMod;
	
	// RUNTIME DATA
	public boolean manager = false;
	public boolean running = false;
	public long elapsedTime;
	private BigDecimal profitPerSecond;
	
	public BigDecimal getProfit() {
		BigDecimal profit = baseProfit;
		if (manager)
			profit = profit.multiply(managerMod);
		return profit;
	}
	
	
	public BigDecimal getProfitPerSecond() {
		if (profitPerSecond == null) {
			BigDecimal biDuration = new BigDecimal(Double.toString(duration / 1000.0));
			profitPerSecond = getProfit().divide(biDuration);
		}
		return profitPerSecond;
	}
}
