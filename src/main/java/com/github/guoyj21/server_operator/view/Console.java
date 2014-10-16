package com.github.guoyj21.server_operator.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console extends JFrame {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private JTextArea txtConsole;

	public Console() {

		txtConsole = new JTextArea();
		getContentPane().add(new JScrollPane(txtConsole), BorderLayout.CENTER);
		txtConsole.setEditable(false);
		txtConsole.setFont(new Font("Courier New", Font.TRUETYPE_FONT, 15));
		this.init();
	}

	private void init() {
		this.setVisible(true);
		this.setBounds(800, 100, 700, 800);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setText(String strs) {
		this.txtConsole.append(strs);
		this.txtConsole.setSelectionStart(this.txtConsole.getText().length());
	}

	public static void main(String[] args) {
		Console console = new Console();
		console.setText("xxxxxxxxxxxxxxxxxxxxxxxxxxx\nxxxxxxxxxxxxxxxxxxxxxx\nxxxxxxxxxxxxxxxxxxxx\nxxxxxxxxxxxxxxxxxxxxx\n");
		console.setText("yyyyyyyyyyyyyyyyyyyyyyy\nyyyyyyyyyyyyyyyyyyyyyyyyyy\n");
	}
}
