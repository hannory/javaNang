package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.dao.UserDao;
import model.vo.User;

public class SignUp extends JPanel{
	MainFrame mf;
	JPanel signUp;           //회원가입 패널
	JButton button;          //뒤로가기 버튼
	JButton join;            //가입하기 버튼
	JTextField idtf;         //아이디 입력받는 텍스트필드
	JButton idbtn;           //아이디 중복체크 버튼
	JPasswordField pwpf;     //패스워드 입력받는 패스워드필드
	JLabel pwlb2;            //패스워드 유효성 검사 표시 라벨
	JPasswordField cpwpf;    //체크패스워드 입력받는 패스워드필드
	JLabel cpwlb2;           //패스워드 일치 확인 표시 라벨
	JTextField nicktf;       //닉네임 입력받는 텍스트필드
	JButton nickbtn;         //닉네임 중복체크 버튼
	JTextField emailtf;      //이메일 입력받는 텍스트필드
	JButton emailbtn;        //이메일 인증받기 버튼
	JTextField numtf;        //이메일 인증번호 입력받는 텍스트필드
	JButton numbtn;          //이메일 인증번호 체크 버튼
	
	User u = new User();
	UserDao ud = new UserDao();  //파일입출력 메소드 소환을 위한 객체 생성
	HashMap<String, User> umap = null;
	
	boolean cid = false;         //아이디 중복확인에 쓰일 값
	boolean cnick = false;       //닉네임 중복확인에 쓰일 값
	boolean cn = false;          //이메일 인증번호 확인에 쓰일 값
	
	String str = null;           //int배열인 이메일 인증번호를 String 타입으로 변환하여 저장할 변수
	
	public SignUp() {}       //기본 생성자 
	
	public SignUp(MainFrame mf) {
		this.mf = mf;
		this.signUp = this;
		
		this.setSize(445, 770);
		this.setLayout(null);
		
		//바 
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 204, 204));
		panel.setLocation(0, 0);
		panel.setSize(445, 70);
		panel.setLayout(null);
		
		Font font = new Font("맑은 고딕", Font.BOLD, 27);
	    Font font2 = new Font("맑은 고딕", Font.BOLD, 20);
		
		//바에 들어갈 제목
		JLabel label = new JLabel("회원가입");
		label.setLocation(80, 10);
		label.setSize(200, 50);
		label.setForeground(Color.WHITE);
		label.setFont(font);
		
		//바에 들어갈 뒤로가기 버튼
		Image backImg = new ImageIcon("images/back sky.PNG").getImage().getScaledInstance(50, 50, 0);
		button = new JButton(new ImageIcon(backImg));
		button.setLocation(10, 10);
		button.setSize(50, 50);
		
		
		//아이디 라벨
		JLabel idlb = new JLabel("ID");
		idlb.setLocation(43, 120);
		idlb.setSize(60, 30);
		
		//아이디 입력받는 텍스트필드
		idtf = new JTextField();
		idtf.setLocation(90, 120);
		idtf.setSize(210, 30);
		
		
		//아이디 중복체크 버튼
		idbtn = new JButton("중복체크");
		idbtn.setLocation(310, 120);
		idbtn.setSize(100, 30);
		
		idbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkId(u);
			}
		});
		
		//패스워드 라벨
		JLabel pwlb = new JLabel("PW");
		pwlb.setLocation(37, 220);
		pwlb.setSize(60, 30);
		
		//패스워드 입력받는 패스워드필드
		pwpf = new JPasswordField();
		pwpf.setLocation(90, 220);
		pwpf.setSize(210, 30);
		pwpf.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				//pwPattern2();
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				pwPattern2();
				checkPw2();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				//pwPattern2();
				
			}
		});
		
		pwlb2 = new JLabel("");
		pwlb2.setLocation(90, 255);
		pwlb2.setSize(340, 15);
		pwlb2.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		
		
		//체크패스워드 라벨
		JLabel cpwlb = new JLabel("PW 확인");
		cpwlb.setLocation(25, 290);
		cpwlb.setSize(60, 30);
		
		//체크패스워드 입력받는 패스워드필드
		cpwpf = new JPasswordField();
		cpwpf.setLocation(90, 290);
		cpwpf.setSize(210, 30);
		cpwpf.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				//checkPw2();
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				checkPw2();
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				//checkPw2();
				
			}
		});
		
		cpwlb2 = new JLabel("");
		cpwlb2.setLocation(90, 325);
		cpwlb2.setSize(340, 15);
		cpwlb2.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		
		//닉네임 라벨
		JLabel nicklb = new JLabel("닉네임");
		nicklb.setLocation(28, 390);
		nicklb.setSize(60, 30);
		
		//닉네임 입력받는 텍스트필드
		nicktf = new JTextField();
		nicktf.setLocation(90, 390);
		nicktf.setSize(210, 30);
		
		
		//닉네임 중복체크 버튼
		nickbtn = new JButton("중복체크");
		nickbtn.setLocation(310, 390);
		nickbtn.setSize(100, 30);
		nickbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkNick(u);
			}
		});
		
		
		//이메일 라벨
		JLabel emaillb = new JLabel("E-MAIL");
		emaillb.setLocation(27, 490);
		emaillb.setSize(60, 30);
		
		//이메일 입력받는 텍스트필드
		emailtf = new JTextField();
		emailtf.setLocation(90, 490);
		emailtf.setSize(210, 30);
		
		
		//이메일 인증받는 버튼
		emailbtn = new JButton("인증받기");
		emailbtn.setLocation(310, 490);
		emailbtn.setSize(100, 30);
		
		//인증받기 버튼을 누르면 이메일 유효성 검사하여 형식이 맞으면 이메일이 발송 됐다는 팝업창이 뜨고, 아니면 형식이 올바르지 않다고 팝업창이 뜬다.
		emailbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Pattern p = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(emailtf.getText());
				
				if(m.find() == true) {
					JOptionPane.showMessageDialog(null, "이메일이 발송되었습니다.", "CHECK_EMAIL", JOptionPane.INFORMATION_MESSAGE);
					sendEmail();
				}else {
					JOptionPane.showMessageDialog(null, "이메일 형식이 올바르지 않습니다.", "CHECK_EMAIL", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		
		//이메일 인증번호 확인
		JLabel numlb = new JLabel("인증번호");
		numlb.setLocation(25, 560);
		numlb.setSize(60, 30);
		
		numtf = new JTextField();
		numtf.setLocation(90, 560);
		numtf.setSize(210, 30);
		
		numbtn = new JButton("인증확인");
		numbtn.setLocation(310, 560);
		numbtn.setSize(100, 30);
		numbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkNum();
				
			}
		});
		
		
		
		
		
		//가입하기 버튼
		join = new JButton("가입하기");
		join.setLocation(150, 660);
		join.setSize(120, 50);
		
		
		//가입하기 버튼 누르면 회원가입(텍스트필드에 있는 정보들을 user.dat 파일에 저장) -> 로그인 패널로 이동
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				register();
				
			}
		});
		
		
		
		this.add(panel);
		panel.add(label);
		panel.add(button);
		
		this.add(idlb);
		this.add(idtf);
		this.add(idbtn);
		
		this.add(pwlb);
		this.add(pwpf);
		this.add(pwlb2);
		
		this.add(cpwlb);
		this.add(cpwpf);
		this.add(cpwlb2);
		
		this.add(nicklb);
		this.add(nicktf);
		this.add(nickbtn);
		
		this.add(emaillb);
		this.add(emailtf);
		this.add(emailbtn);
		
		this.add(numlb);
		this.add(numtf);
		this.add(numbtn);
		
		this.add(join);
		
		
		
		//뒤로가기 버튼
		button.addMouseListener(new MyMouseAdapter());
		
		
	}
	
	
	class MyMouseAdapter extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e) {//뒤로가기 버튼 누르면 약관동의 패널로 이동
			if(e.getSource() == button) {
				ChangePanel.changePanel(mf, signUp, new Agree(mf));
			}
		}
	}
	
	
	
	//텍스트필드, 패스워드필드로 입력받은 값들의 텍스트를 추출하여 setter로 User 클래스의 객체u를 생성..
	public User inputUser() {
		
		u.setUserId(idtf.getText());
		u.setUserPw(String.valueOf(pwpf.getPassword()));
		u.setNickname(nicktf.getText());
		u.setEmail(emailtf.getText());
		
		TreeSet ts = new TreeSet();
		ts.add(9999);
		u.setUserIngred(ts);
	
		return u;
	}
	
	
	//아이디 중복체크 & 유효성 검사
	public void checkId(User u) {
				ObjectInputStream ois = null;
				Pattern p = Pattern.compile("^[a-zA-Z0-9]{4,12}$");
				Matcher m = p.matcher(idtf.getText());
				try {
					
					ois = new ObjectInputStream(new FileInputStream("userList.dat"));
					
					//System.out.println("ois.readObject() : " + ois.readObject());
					umap = (HashMap) ois.readObject();
					
					Set uset = umap.keySet();
					
					
					if(!uset.contains(idtf.getText()) && m.find() == true){
						JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.", "CHECK_ID", JOptionPane.INFORMATION_MESSAGE);
						cid = true;
					}else if(uset.contains(idtf.getText())) {
						JOptionPane.showMessageDialog(null, "존재하는 아이디입니다.", "CHECK_ID", JOptionPane.ERROR_MESSAGE);
						cid = false;
					}else if(m.find() == false) {
						JOptionPane.showMessageDialog(null, "사용할 수 없는 아이디입니다.(영문, 숫자 조합, 4~12자)", "CHECK_ID", JOptionPane.ERROR_MESSAGE);
						cid = false;
					}
				
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.", "CHECK_ID", JOptionPane.INFORMATION_MESSAGE);
					cid = true;
				} catch (EOFException e1) {

					e1.printStackTrace();
				}catch(Exception e1){

					e1.printStackTrace();
				}finally {
					try {
						if(ois != null) {
							ois.close();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		
	
	//비밀번호 유효성 검사(영문자, 숫자, 특수문자 모두 포함하고 8~16글자 제한)
	public boolean pwPattern() {
		
		Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$");
		Matcher m = p.matcher(String.valueOf(pwpf.getPassword()));
		
		return m.find();
	}
	
	//비밀번로 유효성 검사에 따른 라벨 표시
	public void pwPattern2() {
		if(pwPattern() == true) {
			pwlb2.setText("사용가능한 비밀번호입니다.");
		}else {
			pwlb2.setText("사용불가한 비밀번호입니다.(영문자, 숫자, 특수문자 조합 8~16글자)");
		}
	}
	
	//비밀번호와 비밀번호 확인이 일치하는지 검사
	public boolean checkPw() {
		
		if(String.valueOf(pwpf.getPassword()).equals(String.valueOf(cpwpf.getPassword()))) {
			return true;
		}
		
		return false;
	}
	
	//비밀번호 일치 여부에 따른 라벨 표시
	public void checkPw2() {
		if(checkPw() == true) {
			cpwlb2.setText("비밀번호가 일치합니다.");
		}else {
			cpwlb2.setText("비밀번호가 일치하지 않습니다.");	
		}
	}
	
	//닉네임 중복체크
	public void checkNick(User u) {
		
		ObjectInputStream ois = null;
		Pattern p = Pattern.compile("^[a-zA-Z가-힣0-9]{2,10}$");
		Matcher m = p.matcher(nicktf.getText());
		try {
			ois = new ObjectInputStream(new FileInputStream("userList.dat"));
			
			HashMap umap = (HashMap) ois.readObject();
			
			Collection ucol = umap.values();
			
			Object[] uarr = ucol.toArray();

			if(m.find() == false) {
				JOptionPane.showMessageDialog(null, "사용할 수 없는 닉네임입니다.(한글, 영문, 숫자만 가능 2~10자)",
						"CHECK_NICKNAME", JOptionPane.ERROR_MESSAGE);
				cnick = false;
				return;
			}
			
			for(int i = 0; i < uarr.length; i++) {
				if(nicktf.getText().trim().equals(((User) uarr[i]).getNickname())) {
					JOptionPane.showMessageDialog(null, "존재하는 닉네임입니다.", "CHECK_NICKNAME", JOptionPane.ERROR_MESSAGE);
					cnick = false;
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "사용가능한 닉네임입니다.", "CHECK_NICKNAME", JOptionPane.INFORMATION_MESSAGE);
			cnick = true;
			
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "사용 가능한 닉네임입니다.", "CHECK_NICKNAME", JOptionPane.INFORMATION_MESSAGE);
			cnick = true;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	//이메일 형식이 유효한지 검사
	public boolean checkEmail() {
		
		Pattern p = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(emailtf.getText());
		
		return m.find();
		
	}
	
	//이메일 보내기
	public void sendEmail() {
		
		String user = "jvnmaster";
		String password = "Dnsdudwk123@";
		
		
		Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", 465);
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.ssl.enable", "true");
		p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		
		try {
			MimeMessage m = new MimeMessage(session);
			m.setFrom(new InternetAddress(user));
			
			//수신자 메일주소
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(emailtf.getText()));
			
			//메일 제목
			m.setSubject("java.nang 인증번호");
			
			//인증번호를 위한 난수 발생시켜 int배열로 받기
			int[] arr = new int[4];
			arr[0] = (int) (Math.random() * 10);
			arr[1] = (int) (Math.random() * 10);
			arr[2] = (int) (Math.random() * 10);
			arr[3] = (int) (Math.random() * 10);
			
			//int배열을 String으로 변환하기
			str = Arrays.toString(arr).replaceAll("[^0-9]", "");
			System.out.println(str);
			
			//메일 내용
			m.setText("안녕하세요 java.nang입니다.\n 이메일 인증번호 : " + str);
			
			
			//이메일 전송
			Transport.send(m); 
			System.out.println("메일 발송 완료");
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	//이메일 인증번호 확인
	public void checkNum() {
		if(numtf.getText().equals(str)) {
			JOptionPane.showMessageDialog(null, "이메일이 인증되었습니다.", "CHECK_EMAIL", JOptionPane.INFORMATION_MESSAGE);
			cn = true;
			return;
		}
		JOptionPane.showMessageDialog(null, "인증번호가 틀립니다.", "CHECK_EMAIL", JOptionPane.ERROR_MESSAGE);
		cn = false;
	}


	
	//가입하기 버튼을 눌러 가입(위의 메소드들이 true일 경우만 가능)
	public void register() {
		if(pwPattern() == true && checkPw() == true && cid == true && cnick == true && cn == true) {

			ud.writeUserList(signUp, inputUser());

			JOptionPane.showMessageDialog(null, "가입이 완료되었습니다.");
			ChangePanel.changePanel(mf, signUp, new LoginPage(mf));

		}else if(pwPattern() == false) {
			JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요.(영문자, 숫자, 특수문자 조합, 8~16자)", "CHECK_PASSWORD", JOptionPane.ERROR_MESSAGE);
		}else if(checkPw() == false) {
			JOptionPane.showMessageDialog(null, "입력하신 비밀번호가 일치하지 않습니다.", "CHECK_PASSWORD", JOptionPane.ERROR_MESSAGE);
		}else if(cid == false) {
			JOptionPane.showMessageDialog(null, "아이디 중복체크를 해주세요.", "CHECK_ID", JOptionPane.ERROR_MESSAGE);
		}else if(cnick == false){
			JOptionPane.showMessageDialog(null, "닉네임 중복체크를 해주세요.", "CHECK_NICKNAME", JOptionPane.ERROR_MESSAGE);
		}else if(cn == false ) {
			JOptionPane.showMessageDialog(null, "이메일 인증을 해주세요.", "CHECK_EMAIL", JOptionPane.ERROR_MESSAGE);
		}
	}



}



