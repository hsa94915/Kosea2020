
//MenuJTabaleExam.java

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MenuJTabaleExam extends JFrame implements ActionListener {
	JMenu file = new JMenu("����");
	JMenuItem insert = new JMenuItem("�����ϱ�");
	JMenuItem update = new JMenuItem("�����ϱ�");
	JMenuItem delete = new JMenuItem("�����ϱ�");
	JMenuItem quit = new JMenuItem("�����ϱ�");
	JMenuBar mb = new JMenuBar();

	static String[] name = { "ID", "Name", "BackNumber", "Age", "Positions", "Nation", "MarketValue", "AnnualSalary", "TeamName" };

	static DefaultTableModel dt = new DefaultTableModel(name, 0) {
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};
	static JTable jt = new JTable(dt);
	JScrollPane jsp = new JScrollPane(jt);

	/*
	 * South ������ �߰��� Componet��
	 */
	JPanel p = new JPanel();
	String[] comboName = { "  ID  ", "  name  ", "  age  ", "  positions  ", "  nation  ", "  Teamname  " };

	JComboBox combo = new JComboBox(comboName);
	JTextField jtf = new JTextField(20);
	JButton serach = new JButton("�˻�");

	UserDefaultJTableDAO dao = new UserDefaultJTableDAO();

	/**
	 * ȭ�鱸�� �� �̺�Ʈ���
	 */
	public MenuJTabaleExam() {

		super("GUI �౸�����������α׷� - DB����");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\a\\OneDrive\\\uBC14\uD0D5 \uD654\uBA74\\premier-league-soccer.png"));

		// �޴��������� �޴��� �߰�
		file.add(insert);
		file.add(update);
		file.add(delete);
		file.add(quit);
		// �޴��� �޴��ٿ� �߰�
		mb.add(file);

		// �����쿡 �޴��� ����
		setJMenuBar(mb);
		p.setForeground(Color.BLACK);

		// South����
		p.setBackground(Color.PINK);
		p.add(combo);
		p.add(jtf);
		p.add(serach);

		getContentPane().add(jsp, "Center");
		getContentPane().add(p, "South");

		setSize(1024, 720);
		setVisible(false);
		setLocation(setCenterX(1024), setCenterY(720));

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �̺�Ʈ���
		insert.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		quit.addActionListener(this);
		serach.addActionListener(this);

		// ��緹�ڵ带 �˻��Ͽ� DefaultTableModle�� �ø���
		dao.userSelectAll(dt);

		// ù���� ����.
		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);
		jt.addMouseListener(new MyMouseListener());

	}// �����ڳ�

	/**
	 * main�޼ҵ� �ۼ�
	 */
	public static void main(String[] args) {
		new MenuJTabaleExam();

	}

	/**
	 * ����/����/����/�˻������ ����ϴ� �޼ҵ�
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insert) {// Ȯ�� �޴������� Ŭ��
			new UserJDailogGUI(this, "Ȯ��");

		} else if (e.getSource() == update) {// ���� �޴������� Ŭ��
			new UserJDailogGUI(this, "����");

		} else if (e.getSource() == delete) {// ���� �޴������� Ŭ��
			// ���� Jtable�� ���õ� ��� ���� ���� ���´�.
			int row = jt.getSelectedRow();
			System.out.println("������ : " + row);

			Object obj = jt.getValueAt(row, 0);// �� ���� �ش��ϴ� value
			System.out.println("�� : " + obj);

			if (dao.userDelete(obj.toString()) > 0) {
				UserJDailogGUI.messageBox(this, "���ڵ尡 �����Ǿ����ϴ�.");

				// ����Ʈ ����
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);

			} else {
				UserJDailogGUI.messageBox(this, "���ڵ尡 �������� �ʾҽ��ϴ�.");
			}

		} else if (e.getSource() == quit) {// ���� �޴������� Ŭ��
			System.exit(0);

		} else if (e.getSource() == serach) {// �˻� ��ư Ŭ��
			// JComboBox�� ���õ� value ��������
			String fieldName = combo.getSelectedItem().toString();
			System.out.println("�ʵ�� " + fieldName);

			if (fieldName.trim().equals("ALL")) {// ��ü�˻�
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else {
				if (jtf.getText().trim().equals("")) {
					UserJDailogGUI.messageBox(this, "�˻��ܾ �Է����ּ���!");
					jtf.requestFocus();
				} else {// �˻�� �Է��������
					dao.getUserSearch(dt, fieldName, jtf.getText());
					if (dt.getRowCount() > 0)
						jt.setRowSelectionInterval(0, 0);
				}
			}
		}

	}// actionPerformed()----------

	public static class MyMouseListener extends MouseAdapter {

		static private boolean flg;

		static JFrame pf1;

		public void mouseClicked(java.awt.event.MouseEvent arg0) {

			if (arg0.getClickCount() > 1) {

				if (flg) {
					flg = false;
					pf1.dispose();
				}

				pf1 = new JFrame("Player Data");

				JLabel jl = new JLabel();
				JLabel lable_PlayerData = new JLabel("PLAYER DATA");
				JLabel lable_BackNumber = new JLabel("BackNumber");
				JLabel lable_Name = new JLabel("Name");
				JLabel lable_Nation = new JLabel("Nationality");
				JLabel lable_Age = new JLabel("Age");
				JTextField backNum = new JTextField();
				JTextField name = new JTextField();
				JTextField nation = new JTextField();
				JTextField age = new JTextField();
				System.out.println("�Ұ�����"); // ����ǵ�� �ȵ�!!!!
				Connection con;
				Statement st;
				ResultSet rs;
				flg = true;

				try {
					int row = jt.getSelectedRow();// ���� �� �ҷ�����
					String no = (String) jt.getValueAt(row, 0);// �������� �̿��Ͽ� ���ϸ� �ѹ� �ҷ�����
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:HS94915", "kosea32",
							"kosea2019a");
					st = con.createStatement();
					rs = st.executeQuery("select * from Info where ID like '%" + no + "%'");
					byte[] bytes = null;
					
					if (rs.next()) {
						// ��
						lable_PlayerData.setBounds(488, 34, 164, 23);
						lable_PlayerData.setFont(new Font("Lucida Fax", Font.PLAIN, 19));
						pf1.add(lable_PlayerData);
						lable_BackNumber.setBounds(488, 93, 114, 15);
						lable_BackNumber.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
						pf1.add(lable_BackNumber);
						lable_Name.setBounds(488, 170, 64, 15);
						lable_Name.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
						pf1.add(lable_Name);

						lable_Nation.setBounds(488, 245, 110, 18);
						lable_Nation.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
						pf1.add(lable_Nation);

						lable_Age.setBounds(488, 325, 104, 18);
						lable_Age.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
						pf1.add(lable_Age);

						// �ؽ�Ʈ �ʵ�
						backNum.setBounds(488, 117, 388, 33);
						pf1.add(backNum);
						backNum.setText(rs.getString("BACKNUMBER"));
						backNum.setEditable(false);
						name.setBounds(488, 195, 388, 33);
						pf1.add(name);
						name.setText(rs.getString("NAME"));
						name.setEditable(false);
						nation.setBounds(488, 270, 388, 33);
						nation.setText(rs.getString("NATION"));
						nation.setEditable(false);
						pf1.add(nation);
						age.setBounds(488, 350, 388, 33);
						age.setText(rs.getString("Age"));
						age.setEditable(false);
						pf1.add(age);

						jl.setBounds(0, 0, 500, 500);
						pf1.getContentPane().add(jl);
						pf1.getContentPane().setLayout(null);
						pf1.setSize(900, 550);
						pf1.setVisible(true);
					}

					rs = st.executeQuery("select image from Info where ID like '%" + no + "%'");

					if (rs.next()) {
						bytes = rs.getBytes(1);
						Image image = jl.getToolkit().createImage(bytes);
						jl.setIcon(new ImageIcon(image)); // ����ǵ�� �ȵ�!!!!

					}
				} catch (Exception e1) {
					System.out.println(e1);

				}

			}
		}

	}

	public int setCenterX(int xLength) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();

		int setPointX = screen.width / 2 - xLength / 2;

		return setPointX;

	}

	public int setCenterY(int yLength) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();

		int setPointX = screen.height / 2 - yLength / 2;

		return setPointX;
	}
}
