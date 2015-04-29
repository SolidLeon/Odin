package com.solidleon.odin.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.solidleon.odin.beans.Business;
import com.solidleon.odin.controller.OdinController;

public class OdinGUI extends JFrame {

	private static NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	private OdinController con;
	private List<JComponent> continueRepaintList = new ArrayList<>();
	private List<JLabelUpdate> continueUpdateList = new ArrayList<>();
	
	class JLabelUpdate {
		public JLabel lbl;
		public JLabelUpdateAction action;
		public JLabelUpdate(JLabel lbl, JLabelUpdateAction action) {
			super();
			this.lbl = lbl;
			this.action = action;
		}
		
	}
	
	interface JLabelUpdateAction {
		void update(JLabel lbl);
	}
	public OdinGUI() {
		con = new OdinController();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(640, 480));
		JLabel lbl = new JLabel();
		continueUpdateList.add(new JLabelUpdate(lbl, e -> e.setText(String.format("%s (%s)", nf.format(con.world.cash), nf.format(con.getProfitPerSecond())))));
		continueRepaintList.add(lbl);
		add(lbl, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		add(new JScrollPane(centerPanel));
		
		JPanel businessNamePanel = new JPanel(new GridLayout(con.world.business.size(), 3));
		centerPanel.add(businessNamePanel);
		for (int i = 0; i < con.world.business.size(); i++) {
			Business b = con.world.business.get(i);
			JLabel lblBusiness = new JLabel(b.name) {
				protected void paintComponent(Graphics g) {
					g.setColor(Color.green);
					float w = getWidth() * ((float)b.elapsedTime / b.duration);
					g.fillRect(0, 0, (int)w, getHeight());
					super.paintComponent(g);
				}
			};
			lblBusiness.addMouseListener(new MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					b.running = true;
				}
			});
			continueUpdateList.add(new JLabelUpdate(lblBusiness, e -> e.setText(String.format("%s %s/%ds (%s/s)", b.name, nf.format(b.getProfit()), b.duration, nf.format(b.getProfitPerSecond())))));
			continueRepaintList.add(lblBusiness);
			businessNamePanel.add(lblBusiness);
			
			JCheckBox chkManager = new JCheckBox("Manager -" + (1.0 - b.managerMod.doubleValue()) + "%");
			chkManager.setSelected(b.isManager());
			chkManager.addActionListener(e -> b.setManager(chkManager.isSelected()));
			businessNamePanel.add(chkManager);
			
			JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
			businessNamePanel.add(buttonPanel);
			JButton buttonBuy1 = new JButton("1");
			new javax.swing.Timer(10, e -> buttonBuy1.setText("1 " + nf.format(b.getPrice(1)))).start();
			buttonBuy1.addActionListener(e -> buy(b, 1));
			buttonPanel.add(buttonBuy1);
			JButton buttonBuy5 = new JButton("5");
			new javax.swing.Timer(10, e -> buttonBuy5.setText("5 " + nf.format(b.getPrice(5)))).start();
			buttonBuy5.addActionListener(e -> buy(b, 5));
			buttonPanel.add(buttonBuy5);
			JButton buttonBuy100 = new JButton("100");
			new javax.swing.Timer(10, e -> buttonBuy100.setText("100 " + nf.format(b.getPrice(100)))).start();
			buttonBuy100.addActionListener(e -> buy(b, 100));
			buttonPanel.add(buttonBuy100);
			JButton buttonBuyMax = new JButton("Max");
			new javax.swing.Timer(10, e -> buttonBuyMax.setText("? " + nf.format(b.getPrice(1)))).start();
			buttonBuyMax.addActionListener(e -> buy(b, -1));
			buttonPanel.add(buttonBuyMax);
		}
		
		
		
		pack();
		setLocationRelativeTo(null);
		
		con.startWorld();
		System.out.println("DONE");
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					System.out.println(Thread.currentThread().getName());
					OdinGUI.this.repaint();
				}
			}
		});
		
		new javax.swing.Timer(10, e -> continueRepaintList.forEach(component -> component.repaint())).start();
		new javax.swing.Timer(10, e -> continueUpdateList.forEach(update -> update.action.update(update.lbl))).start();
	}
	
	private void buy(Business b, int count) {
		con.buy(b, count);
	}
	
}
