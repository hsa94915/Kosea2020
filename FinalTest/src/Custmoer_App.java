import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Custmoer_App {

	public JFrame frmLogin;
	public JTextField idField;
	public JPasswordField passwordField;
	public JLabel lbld;
	public JLabel lblPs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Custmoer_App window = new Custmoer_App();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Custmoer_App() {
		super();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login\r\n");
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\a\\OneDrive\\\uBC14\uD0D5 \uD654\uBA74\\premier-league-soccer.png"));
		ImagePanel welcomePanel = new ImagePanel(
				new ImageIcon("C:/Users/a/eclipse-workspace/FinalTest/image/Main2.jpg").getImage());
		welcomePanel.setBounds(0, 0, 1060, 565);
		frmLogin.setSize(welcomePanel.getWidth(), welcomePanel.getHeight());
		frmLogin.getContentPane().setLayout(null);

		JPanel mainpanel = new JPanel();
		mainpanel.setBackground(Color.WHITE);
		mainpanel.setBounds(0, 0, 1060, 565);
		frmLogin.getContentPane().add(mainpanel);
		mainpanel.setLayout(null);
		mainpanel.setVisible(false);

		JLabel lblNewLabel_1 = new JLabel("Welcome This is Main Panel");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel_1.setBounds(385, 26, 367, 53);
//		mainpanel.add(lblNewLabel_1);
		frmLogin.getContentPane().add(welcomePanel);
		welcomePanel.setLayout(null);

		// 아이디 넣는 곳
		idField = new JTextField();
		idField.setBounds(360, 250, 280, 30);
		idField.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
		idField.setToolTipText("Enter ID");
		welcomePanel.add(idField);
		idField.setColumns(10);
		String idHint = "ID";
		

		idField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (idField.getText().equals(idHint)) {
					idField.setText("");
				} else {
					idField.setText(idField.getText());
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (idField.getText().equals(idHint) || idField.getText().length() == 0) {
					idField.setText(idHint);
				} else {
					idField.setText(idField.getText());
				}
			}
		});

		
		// 비번 넣는 곳
		passwordField = new JPasswordField();
		passwordField.setBounds(360, 300, 280, 30);
		welcomePanel.add(passwordField);
		
		String pwHint = "PASSWORD";
		passwordField.setText(pwHint);
		passwordField.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
		passwordField.setEchoChar('\u0000');
		

		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (passwordField.getText().equals(pwHint)) {
					passwordField.setText("");
					passwordField.setEchoChar('*');
				} else {
					passwordField.setText(passwordField.getText());
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (passwordField.getText().equals(pwHint) || passwordField.getText().length() == 0) {
					passwordField.setText(pwHint);
					passwordField.setEchoChar('\u0000');
				} else {
					passwordField.setText(passwordField.getText());
				}
			}
		});

//		lbld = new JLabel("ID");
//		lbld.setBounds(208, 250, 62, 30);
//		lbld.setFont(new Font("Lucida Fax", Font.PLAIN, 20));
//		welcomePanel.add(lbld);
//
//		lblPs = new JLabel("PS");
//		lblPs.setBounds(524, 250, 62, 30);
//		lblPs.setFont(new Font("Lucida Fax", Font.PLAIN, 20));
//		welcomePanel.add(lblPs);

		JButton btnLogin = new JButton("");
		btnLogin.setBounds(435, 360, 114, 35);
		btnLogin.setIcon(new ImageIcon("C:\\Users\\a\\OneDrive\\\uBC14\uD0D5 \uD654\uBA74\\Premier League Player image\\[\uD06C\uAE30\uBCC0\uD658]login-button-png-4.jpg"));
		btnLogin.setPressedIcon(new ImageIcon("C:\\Users\\a\\OneDrive\\\uBC14\uD0D5 \uD654\uBA74\\Premier League Player image\\[\uD06C\uAE30\uBCC0\uD658]login-button-png-4.jpg"));
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idField.getText().equals("hs94915")
						&& Arrays.equals(passwordField.getPassword(), "1234".toCharArray())) {
					System.out.println("Welcome Henry");
					frmLogin.dispose();
					MenuJTabaleExam mj = new MenuJTabaleExam();
					mj.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "You failed to log in");
				}

			}

		});

		idField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) { // keyCode() == 9는 Tab, 10은 Enter
					Toolkit.getDefaultToolkit().beep();

					btnLogin.doClick();
				}
			}
		});

		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) { // keyCode() == 9는 Tab, 10은 Enter
					Toolkit.getDefaultToolkit().beep();

					btnLogin.doClick();
				}
			}
		});

		welcomePanel.add(btnLogin);
		frmLogin.setResizable(false);
		frmLogin.setLocationRelativeTo(null);

		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class ImagePanel extends JPanel {
	public Image img;

	public ImagePanel(Image img) {
		this.img = img;
		setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
		setLayout(null);
	}

	public int getWidth() {
		return img.getWidth(null);
	}

	public int getHeight() {
		return img.getHeight(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
