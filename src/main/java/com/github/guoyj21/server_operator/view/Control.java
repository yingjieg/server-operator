package com.github.guoyj21.server_operator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.github.guoyj21.server_operator.commands.Blrpe;
import com.github.guoyj21.server_operator.commands.Blrpi;
import com.github.guoyj21.server_operator.commands.CtrlD;
import com.github.guoyj21.server_operator.commands.Exrpp;
import com.github.guoyj21.server_operator.commands.Exrup;
import com.github.guoyj21.server_operator.commands.ICommand;
import com.github.guoyj21.server_operator.entry.RP;
import com.github.guoyj21.server_operator.entry.STP;
import com.github.guoyj21.server_operator.io.CommandQueue;
import com.github.guoyj21.server_operator.io.SessionOperation;
import com.github.guoyj21.server_operator.shell.Prompt;

public class Control extends JFrame implements ActionListener, MouseListener {

	public JTable table = null;

	private DefaultTableModel model = null;
	private JButton btnRefresh;
	private JButton btnInstall;
	private JButton btnBlock;
	private JButton btnDeblock;
	private JButton btnTerdi;

	public SessionOperation session;
	public CommandQueue cq;

	public ICommand com;

	private STP stp = null;

	public Control(STP stp) {
		this.stp = stp;

		JPanel panelDown = new JPanel();
		getContentPane().add(panelDown, BorderLayout.SOUTH);

		btnRefresh = new JButton("REFRESH");
		panelDown.add(btnRefresh);

		btnInstall = new JButton("INSTALL");
		panelDown.add(btnInstall);

		JButton btnRemove = new JButton("REMOVE");
		panelDown.add(btnRemove);

		btnBlock = new JButton("BLOCK");
		panelDown.add(btnBlock);

		btnDeblock = new JButton("DEBLOCK");
		panelDown.add(btnDeblock);

		btnTerdi = new JButton("TERDI");
		panelDown.add(btnTerdi);

		table = new JTable(model = new MyTableModel());

		table.setModel(model);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		this.btnRefresh.addActionListener(this);
		this.btnInstall.addActionListener(this);
		this.btnBlock.addActionListener(this);
		this.btnDeblock.addActionListener(this);
		this.btnTerdi.addActionListener(this);

		this.table.addMouseListener(this);

		this.session = stp.getSession();

	}

	public void refresh() {
		ArrayList<RP> rps = this.stp.rps;

		String[] columnNames = { "  ", "RP", "STATE", "TYPE", "TWIN", "STATE", "DS", "MAINT.STATE", "PIU" };
		Object[][] data = new Object[rps.size()][9];

		for (int i = 0; i < rps.size(); i++) {
			data[i][0] = Boolean.FALSE;
			data[i][1] = rps.get(i).getRpNumber();
			data[i][2] = rps.get(i).getState();
			data[i][3] = rps.get(i).getType();
			data[i][4] = "  ";
			data[i][5] = "  ";
			data[i][6] = "  ";
			data[i][7] = rps.get(i).getMaint();
			data[i][8] = rps.get(i).getPiu();
		}
		this.model.setDataVector(data, columnNames);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.btnRefresh == e.getSource()) {
			Prompt.setMML(this.session);
			com = new Exrpp();
			System.out.println(com.getCommandLine());
			this.cq.transferCommand(com);
			com.result();
			this.stp.init(Prompt.currentText.toString());
			this.refresh();
		}
		if (this.btnInstall == e.getSource()) {
			int[] rows = this.table.getSelectedRows();
			for (int i : rows) {
				System.out.println(i);
			}
		}

		if (this.btnBlock == e.getSource()) {
			Prompt.setMML(this.session);
			int[] rows = this.table.getSelectedRows();
			if (rows.length == 0) {
				System.out.println("please select rp");
				return;
			}

			for (int i = 0; i < rows.length; i++) {
				com = new Blrpi(this.table.getValueAt(rows[i], 1).toString());
				this.cq.transferCommand(com);
				int rs = com.result();
				// block rp success
				if (rs == 1) {
					com = new Exrpp(this.table.getValueAt(rows[i], 1).toString());
					this.cq.transferCommand(com);
					com.result();
					if (Prompt.currentText.indexOf("MB") != -1) {
						this.table.setValueAt("MB", rows[i], 2);
					}
				}
			}
		}

		if (this.btnDeblock == e.getSource()) {
			Prompt.setMML(this.session);
			int[] rows = this.table.getSelectedRows();
			if (rows.length == 0) {
				System.out.println("please select rp");
				return;
			}

			String[] rps = new String[rows.length];
			for (int i = 0; i < rows.length; i++) {
				rps[i] = this.table.getValueAt(rows[i], 1).toString();
			}

			com = new Blrpe(rps);
			this.cq.transferCommand(com);
			int count = 3;
			int rs = 0;
			try {
				rs = com.result();
				if (rs == 1) {
					com = new CtrlD();
					this.cq.transferCommand(com);
					while (true) {
						Thread.sleep(1000);
						rs = com.result();
						// blrpe success
						if (rs == 2) {
							System.out.println("ccccccccc");
							break;
						}
						// blrpe failed
						if (rs == 3) {
							System.out.println("deblock failed");
							break;
						}

						count--;
						if (count == 0)
							break;
					}
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}

		if (this.btnTerdi == e.getSource()) {
			String temp = "\004";
			byte[] bytes = temp.getBytes();
			for (byte b : bytes) {
				System.out.println(b);
			}
			this.cq.transferCommand(new CtrlD());

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			int i = this.table.getSelectedRow();
			String rpNumber = this.table.getValueAt(i, 1).toString();

			Exrup exrup = new Exrup(rpNumber);

			this.cq.transferCommand(exrup);

			int rs = exrup.result();
			if (rs == 1) {
				System.out.println("exrup ok");
			}

			Modules mo = new Modules(Prompt.currentText.toString(), rpNumber);
			mo.init();
			mo.cq = this.cq;

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
