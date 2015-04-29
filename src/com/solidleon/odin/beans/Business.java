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
	private int count = 0;
	private boolean manager = false;
	public boolean running = false;
	public long elapsedTime;
	private BigDecimal profitPerSecond;
	private BigDecimal currentProfit = null;
	
	public void setManager(boolean manager) {
		this.manager = manager;
		calculateCurrentProfit();
	}
	public void setCount(int count) {
		this.count = count;
		calculateCurrentProfit();
	}
	public int getCount() {
		return count;
	}
	public boolean isManager() {
		return manager;
	}
	
	private void calculateCurrentProfit() {
		currentProfit = baseProfit;
		currentProfit = currentProfit.multiply(new BigDecimal(Integer.toString(count)));
		if (manager)
			currentProfit = currentProfit.multiply(managerMod);
	}
	
	public BigDecimal getProfit() {
		if (currentProfit == null)
			calculateCurrentProfit();
		return currentProfit;
	}
	
	
	public BigDecimal getProfitPerSecond() {
		if (profitPerSecond == null) {
			BigDecimal biDuration = new BigDecimal(Double.toString(duration / 1000.0));
			profitPerSecond = getProfit().divide(biDuration);
		}
		return profitPerSecond;
	}
	public BigDecimal getPrice(int amount) {
		BigDecimal price = basePrice;
		//c = count
		//p(x) = basePrice * (1.10^(c+x))
		price = price.multiply(new BigDecimal("1.1").pow((count + amount)));
		return price;
	}
}
