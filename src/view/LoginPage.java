package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.function.ObjIntConsumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.IngredControl;
import model.vo.IngredAll;
import model.vo.Recipe;
import model.vo.User;
import view.manager.MainPage;

public class LoginPage extends JPanel {

	MainFrame mf;
	JPanel lp;
	private JTextField idField;
	private JTextField pwField;
	public static TreeSet ingredStatic = new TreeSet();	


	public LoginPage(MainFrame mf) {




		this.mf = mf;
		this.lp = this;

		this.setSize(445,770);
		this.setBackground(Color.white);
		setLayout(null);
		
		Image backImgage = new ImageIcon("images/background.png").getImage().getScaledInstance(445, 770,  0);
		JLabel background = new JLabel(new ImageIcon(backImgage));
		background.setSize(445, 770);
		this.add(background);

		JPanel bar = new JPanel();
		bar.setLocation(0, 0);
		bar.setSize(445, 70);
		bar.setLayout(null);
		bar.setBackground(new Color(102, 204, 204));
		bar.setLayout(null);
		background.add(bar);


		JLabel label = new JLabel("�α���");
		label.setFont(new Font("���� ����", Font.BOLD, 27));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.white);
		label.setBounds(70, 18, 146, 39);
		bar.add(label);

		JLabel logo = new JLabel(new ImageIcon(new ImageIcon("images/won/java.PNG").getImage().getScaledInstance(170, 100, 0)));
		logo.setBounds(145, 101, 170, 180);
		background.add(logo);

		JLabel idlb = new JLabel("ID");
		idlb.setBounds(80, 298, 62, 18);
		idlb.setFont(new Font("���� ����", Font.BOLD, 15));
		background.add(idlb);

		JLabel pwlb = new JLabel("PW");
		pwlb.setBounds(80, 366, 62, 18);
		pwlb.setFont(new Font("���� ����", Font.BOLD, 15));
		background.add(pwlb);

		idField = new JTextField();
		idField.setBounds(151, 294, 171, 32);
		background.add(idField);
		idField.setColumns(10);

		pwField = new JPasswordField();
		pwField.setColumns(10);
		pwField.setBounds(151, 359, 171, 32);
		background.add(pwField);

		JButton loginBtn = new JButton("�α���");
		loginBtn.setFont(new Font("���� ����", Font.BOLD, 15));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setBackground(new Color(102, 204, 204));
		loginBtn.setBounds(151, 525, 150, 40);
		background.add(loginBtn);


		JButton signUpBtn = new JButton("ȸ������");
		signUpBtn.setBounds(151, 605, 150, 40);
		signUpBtn.setFont(new Font("���� ����", Font.BOLD, 15));
		signUpBtn.setBackground(new Color(224, 224, 224));
		signUpBtn.setFocusPainted(false);
		background.add(signUpBtn);

		Image backImg = new ImageIcon("images/back sky.png").getImage().getScaledInstance(50,  50,  0);
		JButton back = new JButton(new ImageIcon(backImg));
		back.setLocation(10, 10);
		back.setSize(50, 50);
		bar.add(back);

		back.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseReleased(MouseEvent e) {
				ChangePanel.changePanel(mf, lp, new AllRecipe(mf, lp));

			}


		});


		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				ObjectInputStream objIn = null;

				try {
					objIn = new ObjectInputStream(new FileInputStream("userList.dat"));

					//�ؽ������� �о�� ��ü�� ����ȯ����
					HashMap map = (HashMap) objIn.readObject();
					String id = idField.getText();
					User u1 = (User) map.get(id);

					if (map.containsKey(id) && pwField.getText().equals(u1.getUserPw())) {

						if(!id.equals("manager")) {

							AllRecipe.login =  true;

							AllRecipe.loginId = idField.getText();

							TreeSet ts = (TreeSet) u1.getUserIngred().clone();
							ingredStatic = ts;

							//������� ��ü �ҷ���
							IngredAll.setIngredExpiryMap();
							new IngredControl().method();
							
							ChangePanel.changePanel(mf, lp, new MainMenu(mf));
						}else {
							
							ChangePanel.changePanel(mf, lp, new MainPage(mf));
						}

					}else if(!map.containsKey(id)||pwField.getText() != u1.getUserPw()) {
						JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ȯ�� ��\n�ٽ� �α������ּ���.");
						AllRecipe.login  = false;
						System.out.println("���̵��й�ȣƲ��");
					}

				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally {
					try {
						objIn.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}


			}
		});
		signUpBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ChangePanel.changePanel(mf, lp, new Agree(mf));
			}
		});

	}
}
