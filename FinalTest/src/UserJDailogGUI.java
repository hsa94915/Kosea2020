
//UserJDailogGUI.java
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class UserJDailogGUI extends JDialog implements ActionListener {

	JPanel pw = new JPanel();
	JPanel pc = new JPanel();
	JPanel ps = new JPanel();

	JLabel label = new JLabel(new ImageIcon("‪C:\\Users\\a\\premier-league-soccer.png"));
	JLabel lable_Id = new JLabel("ID");
	JLabel lable_Name = new JLabel("Name");
	JLabel lable_Age = new JLabel("Age");
	JLabel lable_Positions = new JLabel("Positions");
	JLabel lable_Nation = new JLabel("Nation");
	JLabel lable_TeamName = new JLabel("TeamName");

	JTextField id = new JTextField();
	JTextField name = new JTextField();
	JTextField backnumber = new JTextField();
	JTextField age = new JTextField();
	JTextField positions = new JTextField();
	JTextField nation = new JTextField();
	JTextField MarketValue = new JTextField();
	JTextField AnnualSalary = new JTextField();
	JTextField TeamName = new JTextField();

	JButton confirm = new JButton("확인");
	JButton reset = new JButton("취소");

	Custmoer_App cs;
	MenuJTabaleExam me;

	JPanel idCkP = new JPanel();
	JButton idCkBtn = new JButton("IDCheck");

	UserDefaultJTableDAO dao = new UserDefaultJTableDAO();
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("New label");
//	private final JButton btnNewButton = new JButton("확인");
//	private final JButton btnNewButton_1 = new JButton("취소");
	private final JLabel lblNewLabel_1 = new JLabel("New label");
	private final JLabel lbel_1 = new JLabel("Premier League");
	private final JLabel lblKeepItUp = new JLabel("Keep it up");

	public UserJDailogGUI(MenuJTabaleExam me, String index) {
		super(me, "선수정보추가");
		this.me = me;
		getContentPane().setLayout(null);
		if (index.equals("확인")) {
			confirm = new JButton(index);
		} else {
			confirm = new JButton("수정");

			// text박스에 선택된 레코드의 정보 넣기
			int row = me.jt.getSelectedRow();// 선택된 행
			id.setText(me.jt.getValueAt(row, 0).toString());
			name.setText(me.jt.getValueAt(row, 1).toString());
			age.setText(me.jt.getValueAt(row, 2).toString());
			positions.setText(me.jt.getValueAt(row, 3).toString());

			// id text박스 비활성
			id.setEditable(false);

			// IDCheck버튼 비활성화
			idCkBtn.setEnabled(false);
		}
		pc.setBounds(0, 0, 707, 404);
		pc.setLayout(null);
		idCkP.setBounds(388, 45, 283, 28);
		idCkP.setLayout(null);

		// TextField 추가
		pc.add(idCkP);
		idCkBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idCkBtn.setBounds(196, 0, 87, 28);
		idCkP.add(idCkBtn);
		id.setBounds(0, -1, 199, 28);
		idCkP.add(id);
		idCkBtn.addActionListener(this);
		name.setBounds(388, 113, 283, 28);
		pc.add(name);
		age.setBounds(388, 179, 283, 28);
		pc.add(age);
		positions.setBounds(388, 245, 283, 28);
		pc.add(positions);
		nation.setBounds(388, 303, 283, 28);
		pc.add(nation);
		TeamName.setBounds(388, 359, 283, 28);
		pc.add(TeamName);
		ps.setBackground(Color.DARK_GRAY);
		ps.setBounds(0, 0, 344, 404);

		ps.add(confirm);
		ps.setLayout(null);
		reset.setBounds(0, 0, 0, 0);
		ps.add(reset);
		pw.setBounds(0, 400, 707, 46);

		pw.setLayout(null);
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(100, 134, 276, 384);
		
		pw.add(panel);
		panel.setLayout(null);
		lblNewLabel.setBounds(0, 0, 357, 361);
		
		panel.add(lblNewLabel);
		lable_Name.setBounds(388, 87, 54, 14);
		lable_Name.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pc.add(lable_Name);
		lable_Age.setBounds(388, 153, 54, 16);
		lable_Age.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pc.add(lable_Age);
		lable_Positions.setBounds(388, 219, 68, 14);
		lable_Positions.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pc.add(lable_Positions);
		lable_Nation.setBounds(388, 285, 54, 14);
		lable_Nation.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pc.add(lable_Nation);
		lable_TeamName.setBounds(388, 343, 83, 14);
		lable_TeamName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pc.add(lable_TeamName);
		lable_Id.setBounds(388, 12, 54, 30);
		lable_Id.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pc.add(lable_Id);

		setSize(725, 493);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// 이벤트등록
		confirm.addActionListener(this); // 가입/수정 이벤트등록
		reset.addActionListener(this); // 취소 이벤트등록
		
		getContentPane().add(ps);
		lblNewLabel_1.setIcon(new ImageIcon(UserJDailogGUI.class.getResource("/images/pngegg.png")));
		lblNewLabel_1.setBounds(-18, 0, 373, 297);
		
		ps.add(lblNewLabel_1);
		lbel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbel_1.setForeground(new Color(240, 248, 255));
		lbel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbel_1.setBounds(144, 305, 130, 27);
		
		ps.add(lbel_1);
		lblKeepItUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblKeepItUp.setForeground(new Color(240, 248, 255));
		lblKeepItUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKeepItUp.setBounds(171, 344, 103, 27);
		
		ps.add(lblKeepItUp);
		getContentPane().add(pw);
		confirm.setBounds(245, 12, 105, 27);
		
		pw.add(confirm);
		reset.setBounds(364, 12, 105, 27);
		
		pw.add(reset);
		getContentPane().add(pc);
		
//		getContentPane().add(ps, "South");
//		getContentPane().add(pw, "West");
//		getContentPane().add(pc, "Center");

	}// 생성자끝

	/**
	 * 가입/수정/삭제 기능에 대한 부분
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String btnLabel = e.getActionCommand();// 이벤트주체 대한 Label 가져오기

		if (btnLabel.equals("확인")) {
			if (dao.userListInsert(this) > 0) {// 확인성공
				messageBox(this, name.getText() + "님 정보가 추가되었습니다.");
				dispose();// JDialog 창닫기

				dao.userSelectAll(me.dt);// 모든레코드가져와서 DefaultTableModel에 올리기

				if (me.dt.getRowCount() > 0)
					me.jt.setRowSelectionInterval(0, 0);// 첫번째 행 선택

			} else {// 확인실패
				messageBox(this, "추가되지 않았습니다.");
			}

		} else if (btnLabel.equals("수정")) {

			if (dao.userUpdate(this) > 0) {
				messageBox(this, "수정완료되었습니다.");
				dispose();
				dao.userSelectAll(me.dt);
				if (me.dt.getRowCount() > 0)
					me.jt.setRowSelectionInterval(0, 0);

			} else {
				messageBox(this, "수정되지 않았습니다.");
			}

		} else if (btnLabel.equals("취소")) {
			dispose();

		} else if (btnLabel.equals("IDCheck")) {
			// id텍스트박스에 값 없으면 메세지 출력 있으면 DB연동한다.
			if (id.getText().trim().equals("")) {
				messageBox(this, "ID를 입력해주세요.");
				id.requestFocus();// 포커스이동
			} else {

				if (dao.getIdByCheck(id.getText())) { // 중복아니다.(사용가능)
					messageBox(this, id.getText() + "는 사용가능합니다.");
				} else { // 중복이다.
					messageBox(this, id.getText() + "는 중복입니다.");

					id.setText("");// text박스지우기
					id.requestFocus();// 커서놓기
				}

			}

		}

	}// actionPerformed끝

	/**
	 * 메시지박스 띄워주는 메소드 작성 
	 */
	public static void messageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component) obj, message);
	}
}// 클래스끝