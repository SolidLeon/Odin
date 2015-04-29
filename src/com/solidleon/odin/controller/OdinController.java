package com.solidleon.odin.controller;

import java.math.BigDecimal;

import com.solidleon.odin.beans.Business;
import com.solidleon.odin.beans.World;

public class OdinController {

	public World world;
	private Thread worldThread;
	private boolean worldRunning = false;
	
	public OdinController() {
		world = new World();
		Business b = new Business();
		b.basePrice = new BigDecimal("10");
		b.baseProfit = new BigDecimal("10");
		b.name = "Lemonade Stand";
		b.duration = 1000;
		world.business.add(b);
		Business b2 = new Business();
		b2.basePrice = new BigDecimal("10");
		b2.baseProfit = new BigDecimal("500");
		b2.name = "News Stand";
		b2.duration = 2000;
		world.business.add(b2);
	}
	
	public void startWorld() {
		worldThread = new Thread(() -> wtWorldUpdate());
		worldThread.start();
	}
	
	//------------------------------------------
	// WORLD THREAD METHODS
	// These methods run inside the world thread
	//------------------------------------------
	
	private void wtWorldUpdate() {
		worldRunning = true;
		long lastUpdate = System.currentTimeMillis();
		while (worldRunning) {
			long now = System.currentTimeMillis();
			long delta = now - lastUpdate;
			lastUpdate = now;
			for (int i = 0; i < world.business.size(); i++) {
				Business b = world.business.get(i);
				wtBusinessUpdate(b, delta);
			}
		}
	}

	private void wtBusinessUpdate(Business b, long delta) {
		b.elapsedTime += delta;
		if (b.elapsedTime > b.duration) {
			b.elapsedTime -= b.duration;
			world.cash = world.cash.add(b.baseProfit);
		}
	}

	public BigDecimal getProfitPerSecond() {
		BigDecimal profitPerSecond = new BigDecimal("0");
		
		for (Business b : world.business)
			profitPerSecond = profitPerSecond.add(b.getProfitPerSecond());
		
		return profitPerSecond;
	}
	
}