package com.github.guoyj21.server_operator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.github.guoyj21.server_operator.commands.Blrpi;
import com.github.guoyj21.server_operator.commands.Exeme;
import com.github.guoyj21.server_operator.commands.Exrue;
import com.github.guoyj21.server_operator.commands.ICommand;
import com.github.guoyj21.server_operator.entry.Block;
import com.github.guoyj21.server_operator.io.CommandQueue;

public class Modules extends JFrame implements ActionListener {

	private JButton btnInstall;
	private JButton btnRemove;
	private JTable table;

	private DefaultTableModel model;

	private ArrayList<Block> blocks;
	private String rpNumber;

	public CommandQueue cq;
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public Modules(String text, String rpNumber) {
		this.rpNumber = rpNumber;

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnInstall = new JButton("Install");
		panel.add(btnInstall);

		btnRemove = new JButton("Remove");
		panel.add(btnRemove);

		if (this.model == null)
			this.model = new MyTableModel();

		if (this.table == null)
			this.table = new JTable(this.model);

		this.table.setModel(this.model);

		JScrollPane scrollPane = new JScrollPane(this.table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		if (this.blocks == null)
			this.blocks = new ArrayList<Block>();

		this.blockInfo(text);

		String[] columnNames = { "SUNAME", "SUID", "SUPTR", "CM", "EM" };
		Object[][] data = new Object[blocks.size()][5];

		for (int i = 0; i < blocks.size(); i++) {
			data[i][0] = blocks.get(i).getSuName();
			data[i][1] = blocks.get(i).getSuid();
			data[i][2] = blocks.get(i).getSuptr();
			int tmp = blocks.get(i).getCm();

			if (tmp == -1)
				data[i][3] = "";
			else
				data[i][3] = tmp;

			tmp = blocks.get(i).getEm();
			if (tmp == -1)
				data[i][4] = "";
			else
				data[i][4] = tmp;
		}

		this.model.setDataVector(data, columnNames);
		this.table.getColumnModel().getColumn(1).setPreferredWidth(250);

		this.btnRemove.addActionListener(this);

	}

	private void blockInfo(String text) {

		String lines[] = text.split("\n");

		boolean flag = false;

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].indexOf("END") != -1)
				break;

			if (lines[i].indexOf("SUNAME        SUID") != -1) {
				flag = true;
				continue;
			}

			if (flag) {
				String temps[] = lines[i].trim().split("\\s+");
				if (temps.length < 6)
					break;
				Block block = null;
				String suid = temps[1].trim() + " " + temps[2].trim() + " " + temps[3].trim() + "        "
						+ temps[4].trim();
				if (temps.length == 6)
					block = new Block(temps[0].trim(), suid, temps[5].trim(), -1, -1);
				if (temps.length == 7)
					block = new Block(temps[0].trim(), suid, temps[5].trim(), Integer.parseInt(temps[6]), -1);
				if (temps.length == 8)
					block = new Block(temps[0].trim(), suid, temps[5].trim(), Integer.parseInt(temps[6]),
							Integer.parseInt(temps[7]));

				block.setRpNumber(this.rpNumber);
				this.blocks.add(block);
			}
		}
	}

	public void init() {
		this.setBounds(300, 300, 600, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnRemove) {
			int[] rows = this.table.getSelectedRows();

			ICommand blrbi = new Blrpi(this.blocks.get(0).getRpNumber());
			this.cq.transferCommand(blrbi);

			for (int i = 0; i < rows.length; i++) {
				System.out.println("exeme   " + this.blocks.get(rows[i]).getSuName());
				if (this.cq == null)
					System.out.println("command queue in Modules null");

				ICommand com = new Exeme(this.blocks.get(rows[i]).getEm(), this.blocks.get(rows[i]).getRpNumber());
				this.cq.transferCommand(com);
				int rs = com.result();
				System.out.println(rs + "exeme");

				if (rs == 1 || rs == 11 || rs == 0) {
					ICommand com2 = new Exrue(this.blocks.get(rows[i]).getRpNumber(), this.blocks.get(rows[i])
							.getSuid());
					this.cq.transferCommand(com2);
					int rs2 = com2.result();
					if (rs2 == 1)
						System.out.println("ok");
				}
			}
		}
	}
}
