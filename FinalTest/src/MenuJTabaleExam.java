
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
	JMenu file = new JMenu("파일");
	JMenuItem insert = new JMenuItem("저장하기");
	JMenuItem update = new JMenuItem("수정하기");
	JMenuItem delete = new JMenuItem("삭제하기");
	JMenuItem quit = new JMenuItem("종료하기");
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
	 * South 영역에 추가할 Componet들
	 */
	JPanel p = new JPanel();
	String[] comboName = { "  ID  ", "  name  ", "  age  ", "  positions  ", "  nation  ", "  Teamname  " };

	JComboBox combo = new JComboBox(comboName);
	JTextField jtf = new JTextField(20);
	JButton serach = new JButton("검색");

	UserDefaultJTableDAO dao = new UserDefaultJTableDAO();

	/**
	 * 화면구성 및 이벤트등록
	 */
	public MenuJTabaleExam() {

		super("GUI 축구선수정보프로그램 - DB연동");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\a\\OneDrive\\\uBC14\uD0D5 \uD654\uBA74\\premier-league-soccer.png"));

		// 메뉴아이템을 메뉴에 추가
		file.add(insert);
		file.add(update);
		file.add(delete);
		file.add(quit);
		// 메뉴를 메뉴바에 추가
		mb.add(file);

		// 윈도우에 메뉴바 세팅
		setJMenuBar(mb);
		p.setForeground(Color.BLACK);

		// South영역
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

		// 이벤트등록
		insert.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		quit.addActionListener(this);
		serach.addActionListener(this);

		// 모든레코드를 검색하여 DefaultTableModle에 올리기
		dao.userSelectAll(dt);

		// 첫번행 선택.
		if (dt.getRowCount() > 0)
			jt.setRowSelectionInterval(0, 0);
		jt.addMouseListener(new MyMouseListener());

	}// 생성자끝

	/**
	 * main메소드 작성
	 */
	public static void main(String[] args) {
		new MenuJTabaleExam();

	}

	/**
	 * 가입/수정/삭제/검색기능을 담당하는 메소드
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insert) {// 확인 메뉴아이템 클릭
			new UserJDailogGUI(this, "확인");

		} else if (e.getSource() == update) {// 수정 메뉴아이템 클릭
			new UserJDailogGUI(this, "수정");

		} else if (e.getSource() == delete) {// 삭제 메뉴아이템 클릭
			// 현재 Jtable의 선택된 행과 열의 값을 얻어온다.
			int row = jt.getSelectedRow();
			System.out.println("선택행 : " + row);

			Object obj = jt.getValueAt(row, 0);// 행 열에 해당하는 value
			System.out.println("값 : " + obj);

			if (dao.userDelete(obj.toString()) > 0) {
				UserJDailogGUI.messageBox(this, "레코드가 삭제되었습니다.");

				// 리스트 갱신
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);

			} else {
				UserJDailogGUI.messageBox(this, "레코드가 삭제되지 않았습니다.");
			}

		} else if (e.getSource() == quit) {// 종료 메뉴아이템 클릭
			System.exit(0);

		} else if (e.getSource() == serach) {// 검색 버튼 클릭
			// JComboBox에 선택된 value 가져오기
			String fieldName = combo.getSelectedItem().toString();
			System.out.println("필드명 " + fieldName);

			if (fieldName.trim().equals("ALL")) {// 전체검색
				dao.userSelectAll(dt);
				if (dt.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else {
				if (jtf.getText().trim().equals("")) {
					UserJDailogGUI.messageBox(this, "검색단어를 입력해주세요!");
					jtf.requestFocus();
				} else {// 검색어를 입력했을경우
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
				System.out.println("소고기사줘"); // 절대건들면 안됨!!!!
				Connection con;
				Statement st;
				ResultSet rs;
				flg = true;

				try {
					int row = jt.getSelectedRow();// 순서 값 불러오기
					String no = (String) jt.getValueAt(row, 0);// 순서값을 이용하여 포켓몬 넘버 불러오기
					con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:HS94915", "kosea32",
							"kosea2019a");
					st = con.createStatement();
					rs = st.executeQuery("select * from Info where ID like '%" + no + "%'");
					byte[] bytes = null;
					
					if (rs.next()) {
						// 라벨
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

						// 텍스트 필드
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
						jl.setIcon(new ImageIcon(image)); // 절대건들면 안됨!!!!

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
