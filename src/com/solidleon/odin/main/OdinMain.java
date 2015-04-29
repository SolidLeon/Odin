package com.solidleon.odin.main;

import javax.swing.SwingUtilities;

import com.solidleon.odin.gui.OdinGUI;

public class OdinMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new OdinGUI().setVisible(true));
	}

}
