package com.github.guoyj21.server_operator.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.guoyj21.server_operator.commands.Command;
import com.github.guoyj21.server_operator.commands.ICommand;
import com.github.guoyj21.server_operator.entry.STP;
import com.github.guoyj21.server_operator.io.CommandQueue;
import com.github.guoyj21.server_operator.io.SessionOperation;
import com.trilead.ssh2.SCPClient;

public class MainFrame extends JFrame implements ActionListener, Runnable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private JTextField txtPath;
	private JCheckBox cbRP;
	private JCheckBox cbCP;
	private JTextField txtIP;
	private JTextField txtUserName;
	private JPasswordField txtPwd;
	private JTextField txtDefaultLocation;
	private JButton btnEdit;
	private JButton btnBrowse;
	private JButton btnUpload;
	private JButton btnInstall;
	private String separator_ = System.getProperty("file.separator");
	private JTextField txtCommand;
	private JButton btnEnter;

	private SessionOperation session = null;

	private Thread sessionThread = null;
	private Console console = null;
	private CommandQueue cq = null;
	private File files[];

	public ICommand com;

	public MainFrame() {
		this.initControls();
		this.addListener();
		this.setBounds(300, 300, 400, 550);
		this.com = new Command();
	}

	private void initControls() {
		getContentPane().setLayout(null);

		txtPath = new JTextField();
		txtPath.setBounds(25, 25, 269, 20);
		getContentPane().add(txtPath);
		txtPath.setColumns(30);

		btnBrowse = new JButton("synchronize");
		btnBrowse.setBounds(306, 24, 80, 23);
		getContentPane().add(btnBrowse);

		JLabel lblType = new JLabel("Module Type :");
		lblType.setBounds(25, 74, 80, 14);
		getContentPane().add(lblType);

		cbRP = new JCheckBox("RP");
		cbRP.setBounds(130, 70, 42, 23);
		getContentPane().add(cbRP);

		cbCP = new JCheckBox("CP");
		cbCP.setBounds(186, 70, 42, 23);
		getContentPane().add(cbCP);

		txtIP = new JTextField();
		txtIP.setBounds(25, 124, 240, 20);
		getContentPane().add(txtIP);
		txtIP.setColumns(10);
		txtIP.setText("tp008ap1.uab.ericsson.se");

		txtUserName = new JTextField();
		txtUserName.setBounds(25, 165, 136, 20);
		getContentPane().add(txtUserName);
		txtUserName.setText("administrator");
		txtUserName.setColumns(10);

		txtPwd = new JPasswordField();
		txtPwd.setBounds(25, 208, 136, 20);
		getContentPane().add(txtPwd);
		txtPwd.setColumns(10);

		JLabel lblUserName = new JLabel("user name");
		lblUserName.setBounds(170, 168, 95, 14);
		getContentPane().add(lblUserName);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(170, 211, 60, 14);
		getContentPane().add(lblPassword);

		JLabel lblIP = new JLabel("IP Address");
		lblIP.setBounds(275, 127, 76, 14);
		getContentPane().add(lblIP);

		txtDefaultLocation = new JTextField();
		txtDefaultLocation.setBounds(25, 254, 240, 20);
		txtDefaultLocation.setEditable(false);
		Properties prop = System.getProperties();

		txtDefaultLocation.setText("k:" + separator_ + "ftpvol" + separator_ + prop.getProperty("user.name") + "\\");
		getContentPane().add(txtDefaultLocation);
		txtDefaultLocation.setColumns(10);

		JLabel lblDefaultLocation = new JLabel("default location");
		lblDefaultLocation.setBounds(275, 257, 95, 14);
		getContentPane().add(lblDefaultLocation);

		btnEdit = new JButton("Edit");
		btnEdit.setBounds(25, 285, 91, 23);
		getContentPane().add(btnEdit);

		btnUpload = new JButton("upload");
		btnUpload.setBounds(25, 418, 91, 23);
		getContentPane().add(btnUpload);

		btnInstall = new JButton("install");
		btnInstall.setBounds(158, 418, 91, 23);
		getContentPane().add(btnInstall);

		txtCommand = new JTextField();
		txtCommand.setFont(new Font("Segoe UI", Font.BOLD, 12));
		txtCommand.setBounds(25, 330, 345, 20);
		getContentPane().add(txtCommand);
		txtCommand.setColumns(10);

		btnEnter = new JButton("Enter");
		btnEnter.setBounds(25, 361, 91, 23);
		getContentPane().add(btnEnter);
	}

	private void addListener() {
		this.btnBrowse.addActionListener(this);
		this.btnEdit.addActionListener(this);
		this.btnInstall.addActionListener(this);
		this.btnUpload.addActionListener(this);
		this.btnEnter.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBrowse) {
			JFileChooser jf = new JFileChooser("select the sw");
			jf.setMultiSelectionEnabled(true);
			jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnValue = jf.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				files = jf.getSelectedFiles();
			}
			if (returnValue == JFileChooser.CANCEL_OPTION) {
				System.out.println("cccccccccc");
			}
		}
		if (e.getSource() == btnEdit) {
			txtDefaultLocation.setEditable(true);
		}
		if (e.getSource() == btnUpload) {
			if (files != null) {
				if (this.session != null) {
					// Upload up = new Upload(this.session.getConnection());
					for (int i = 0; i < files.length; i++) {
						try {
							SCPClient scp = this.session.getConnection().createSCPClient();
							scp.put(files[i].getPath(), ".");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				this.session.runCmd("copy LO-* k:\\ftpvol\\eyieguo\r\n");
			}
		}
		if (e.getSource() == btnInstall) {
			// Prompt.setOSmon(this.session, 16);
			// Prompt.setMML(this.session);
			STP stp = new STP(this.session);

			// stp.init(Prompt.currentText.toString());
			Control ct = new Control(stp);
			ct.cq = this.cq;
			ct.setVisible(true);
			ct.setBounds(200, 200, 660, 700);
			ct.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		if (e.getSource() == btnEnter) {
			if (this.cq == null)
				System.out.println("error , cq is null");

			((Command) this.com).setCommandLine(txtCommand.getText().toString());
			this.cq.transferCommand(this.com);
		}

	}

	public void initConsole() {
		if (this.console == null) {
			this.console = new Console();
		}
	}

	public void run() {
		if (this.session == null) {
			this.session = new SessionOperation(this.console, this.txtIP.getText(), this.txtUserName.getText(),
					"Sommar.2004");
			if (sessionThread == null)
				sessionThread = new Thread(this.session);
			sessionThread.start();
		}

		if (this.cq == null) {
			this.cq = new CommandQueue(this.session);
			Thread t3 = new Thread(this.cq);
			t3.start();
		}
	}

	public CommandQueue getCq() {
		return cq;
	}

	public void setCq(CommandQueue cq) {
		this.cq = cq;
	}

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.initConsole();
		Thread t2 = new Thread(mf);
		t2.start();

	}
}
