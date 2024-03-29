package view;

import java.awt.Color;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.vo.User;
import model.dao.UserDao;

public class ModiMember extends JPanel{

	MainFrame mf;
	JPanel modiMember;

	private JPasswordField jpf1;
	private JPasswordField jpf2;
	private JPasswordField jpf3;
	private JTextField jtf1;
	private JTextField jtf2;
	private JTextField jtf3;

	public ModiMember(MainFrame mf) {

		this.mf = mf;
		this.modiMember = this;

		String id = AllRecipe.loginId; //LoginId값을 id에 담음 

		this.setBounds(0,0,445,770);
		this.setLayout(null);
		JLabel lb1 = new JLabel("회원정보");
		lb1.setBounds(25, 70, 200 ,100);
		lb1.setFont(new Font("Serif", Font.BOLD, 20));  
		JLabel lb2 = new JLabel("ID");
		lb2.setBounds(60, 155, 27, 50);
		lb2.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel lb3 = new JLabel("현재 PW");
		lb3.setBounds(26, 205, 92 ,50);
		lb3.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel lb4 = new JLabel("변경 PW");
		lb4.setBounds(26, 255, 92 ,50);
		lb4.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel lb5 = new JLabel("확인 PW");
		lb5.setBounds(26, 305, 92 ,50);
		lb5.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel lb6 = new JLabel("닉네임");
		lb6.setBounds(36, 355, 83 ,50);
		lb6.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel lb7 = new JLabel("E-MAIL");
		lb7.setBounds(30, 405, 83 ,50);
		lb7.setFont(new Font("Serif", Font.BOLD, 20));

		this.add(lb1);
		this.add(lb2);
		this.add(lb3);
		this.add(lb4);
		this.add(lb5);
		this.add(lb6);
		this.add(lb7);

		JPanel panel = new JPanel(); 
		panel.setSize(90,50);
		panel.setLocation(191,570);
		panel.setLayout(null);
		panel.setBackground(Color.orange);

		this.add(panel);

		JButton jbt1 = new JButton("확인");
		jbt1.setBounds(0,0,90,50);	    	    
		jbt1.setForeground(Color.white);
		jbt1.setBackground(Color.orange);


		jbt1.addMouseListener(new MyMouseAdapter1() {//확인 클릭 후

			@Override        
			public void mouseReleased(MouseEvent e) { 

				char[] input = jpf2.getPassword();
				String input2 = new String(input);

				String JPF1 = String.valueOf(jpf1.getPassword());
				String JPF2 = String.valueOf(jpf2.getPassword());
				String JPF3 = String.valueOf(jpf3.getPassword()); //PasswordField를 String형으로 변환(문자열로 비교 하기위해)

				Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
				Matcher m = p.matcher(input2);        //문자+특수문자or특수문자+문자의 조합으로 생성될 수 있도록

				HashMap asds = new HashMap();
			
				ObjectInputStream objIn = null;
			
				try {

					objIn = new ObjectInputStream(new FileInputStream("userList.dat"));
					    
					try {
						asds = (HashMap) objIn.readObject();

						User u1 = (User) asds.get(id);

						
						if(JPF1.equals(u1.getUserPw()) && 
								JPF2.equals(JPF3) &&
								JPF2.length() <= 16 &&
								JPF2.length() >= 8  &&    
								m.find()){ 
							u1.setUserPw(JPF2); 
							asds.put(id, u1); 
							
							JOptionPane.showMessageDialog(null, "수정이 완료되었습니다. 다시 로그인 해주세요.");
							
							ChangePanel.changePanel(mf, modiMember, new LoginPage(mf));
					
							//변경 PW와 확인 PW이 같고 &&
							//변경 PW이 8~16글자 사이이고
							//특수문자와영문자를 적어도 하나씩 포함하고 있으면

							//여기서 변경한 PW(JPF2orJPF3)로 로그인 한 유저의 PW(u1.getUserPw)를 변경해줘야 한다
							//oosOut파일에 넣기 위해서 해쉬맵 asds에 덮어쓰기형식으로 u1을 다시 넣었다.

							//System.out.println("변경한 PW : " + u1.getUserPw());

						}else if (!JPF1.equals(u1.getUserPw())) {
							JOptionPane.showMessageDialog(null, "현재 PW를 정확하게 입력해주세요");
						}else if(!JPF2.equals(JPF3)) {
							JOptionPane.showMessageDialog(null, "변경할 PW를 정확하게 입력해주세요");
						}else if(JPF2.length() > 16 || JPF3.length() > 16
								|| JPF3.length() < 8 || JPF3.length() < 8) {
							JOptionPane.showMessageDialog(null, "변경할 PW는 8글자이상 16자 이하로 입력해주세요");
						}
						else {
							JOptionPane.showMessageDialog(null, "변경할 PW는 영문자와 특수문자를 적어도 하나씩 포함하고 있어야 합니다.");
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						objIn.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				try {
					ObjectOutputStream oosOut = new ObjectOutputStream(new FileOutputStream("userList.dat"));
					oosOut.writeObject(asds); //PW를 수정한 회원의 정보를 다시 파일에 저장하기
					oosOut.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		});

		panel.add(jbt1);

		JPanel panel2 = new JPanel(); //상단 패널
		panel2.setLocation(0,0);  
		panel2.setSize(445,70);
		panel2.setLayout(null);
		panel2.setBackground(new Color(102, 204, 204));
		this.add(panel2);


		JLabel label = new JLabel("회원정보 수정");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 27)); 
		label.setForeground(Color.WHITE);
		label.setBounds(95, 11 ,207, 46);
		panel2.add(label);

		Image person = new ImageIcon("images/person sky.png").getImage().getScaledInstance(50,50,0);
		JButton logIn = new JButton(new ImageIcon(person));
		logIn.setLocation(380,10);
		logIn.setSize(50,50);
		logIn.addMouseListener(new MyMouseAdapter1());
		panel2.add(logIn);



		Image backImg = new ImageIcon("images/back sky.png").getImage().getScaledInstance(50,50,0);
		JButton back = new JButton(new ImageIcon(backImg));
		back.setLocation(10,10);
		back.setSize(50,50);
		back.addMouseListener(new MyMouseAdapter2());
		panel2.add(back);

		jtf1 = new JTextField();

		jtf1.setBounds(135,162,239,35);
		this.add(jtf1);
		jtf1.setEditable(false);

		JLabel Label = new JLabel(id); //static 선언했던 로그인한 사용자의 id 
		Label.setBounds(5,0,239,35);
		jtf1.add(Label);

		jpf1 = new JPasswordField();
		jpf1.setBounds(135, 212, 239, 35);
		this.add(jpf1);

		jpf2 = new JPasswordField();
		jpf2.setBounds(135, 262, 239, 35);
		this.add(jpf2);

		jpf3 = new JPasswordField();
		jpf3.setBounds(135,312,239,35);
		this.add(jpf3);
	
		ObjectInputStream objIn = null;
		
		try {

			objIn = new ObjectInputStream(new FileInputStream("userList.dat"));

			try {
				HashMap asds = (HashMap) objIn.readObject();

				User u1 = (User) asds.get(id);//키가 id인 해쉬맵을 u1에 담았다. 

				JLabel Label2 = new JLabel(u1.getNickname()); //로그인한 유저의 닉네임
				Label2.setBounds(5,0,239,35);

				jtf2 = new JTextField();
				jtf2.setBounds(135,362,239,35);
				this.add(jtf2);        
				jtf2.setEditable(false); // 닉네임 변경 불가
				jtf2.add(Label2);

				jtf3 = new JTextField();
				jtf3.setBounds(135,412,239,35);
				this.add(jtf3);
				jtf3.setEditable(false); //이메일 변경 불가

				JLabel Label3 = new JLabel(u1.getEmail()); //로그인한 유저의 이메일
				Label3.setBounds(5,0,239,35);
				jtf3.add(Label3);
				//각 필드들은 회원정보가 들어있는 파일을 불러와서 써야한다. 
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				objIn.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		JLabel label2 = new JLabel("");
		label2.setBounds(176, 220, 62, 18);
		this.add(label2);

		JButton jbt3 = new JButton("회원 탈퇴");
		jbt3.setBounds(325, 700, 96, 35);
		jbt3.setBackground(Color.LIGHT_GRAY);
		jbt3.addMouseListener(new MyMouseAdapter3());
		this.add(jbt3);
	}

	//값 안 받아질때 아래처럼 텍스트필드로 받자 
	//TextField passwordText = new TextField(6);
	//passwordText.setEchoChar('*'); 안정성떨어질거같음   

	class MyMouseAdapter1 extends MouseAdapter{//메인메뉴로
		@Override
		public void mouseReleased(MouseEvent e) {
			ChangePanel.changePanel(mf, modiMember, new MainMenu(mf));
		}
	}
	class MyMouseAdapter2 extends MouseAdapter{//My냉장고로 돌아가기(뒤로가기)
		@Override
		public void mouseReleased(MouseEvent e) {
			ChangePanel.changePanel(mf, modiMember, new MyFridge(mf));
		}
	}
	class MyMouseAdapter3 extends MouseAdapter{//회원탈퇴로
		@Override
		public void mouseReleased(MouseEvent e) {
			ChangePanel.changePanel(mf, modiMember, new QuitMember(mf));
		}
	}
}




