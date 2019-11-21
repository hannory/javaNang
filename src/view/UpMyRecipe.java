package view;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class UpMyRecipe extends JPanel {
	MainFrame mf;
	JPanel mp;
	JTextArea recipeCont2;
	JTextArea recipeName2;
	String str;
	String str2;
	


	public UpMyRecipe(MainFrame mf) {
		
		//필드값 초기화
		this.mf=mf;  
		this.mp=this;

		//전체 패널(제일 큰 패널) 기본 설정 
		this.setSize(445,770);
		this.setBackground(new Color(190,190,190));
		setLayout(null);
		mf.getContentPane().add(mp);

		//상단 패널 설정
		JPanel panel = new JPanel();
		panel.setBackground(new Color(70, 130, 180));
		panel.setBounds(0,0,445,70);
		mp.add(panel);
		panel.setLayout(null);

		//하단 패널 설정 
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		panel2.setBounds(0,70,445,700);
		mp.add(panel2);
		panel.setLayout(null);

		JButton button0 = new JButton("<<");
		button0.setBounds(29,22,58,33);
		panel.add(button0);
		button0.setBackground(new Color(70,130,180));

		JTextPane textpane4 = new JTextPane();
		panel.add(textpane4);
		textpane4.setBounds(133,15,177,43);
		textpane4.setEditable(false);
		textpane4.setBackground(new Color(70,130,180));
		textpane4.setForeground(Color.white);
		textpane4.setFont(new Font("굴림",Font.PLAIN,32));
		textpane4.setText("\uB098\uC758 \uB808\uC2DC\uD53C" );
		panel2.setLayout(null);

		JTextPane textpane1= new JTextPane();
		textpane1.setBounds(28,266,69,35);
		panel2.add(textpane1);
		textpane1.setText("요리명"); 
		textpane1.setFont(new Font("맑은고딕",Font.BOLD,20));

		JTextPane textpane2 = new JTextPane();
		textpane2.setBounds(28,320,69,35);
		panel2.add(textpane2);
		textpane2.setText("레시피");
		textpane2.setFont(new Font("맑은고딕",Font.BOLD,20));

		JTextPane textpane3= new JTextPane();
		textpane3.setBounds(28,12,48,35);
		panel2.add(textpane3);
		textpane3.setText("사진");
		textpane3.setFont(new Font("맑은고딕",Font.BOLD,20));


		
		
		
		ObjectInputStream oip = null;

		
		try {
			oip = new ObjectInputStream(new FileInputStream("recipeCont.txt"));
			this. str = (String) oip.readObject().toString(); 

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}finally {

		}try {
			oip.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	
		ObjectInputStream oip2 = null;
		
		try {
			oip2= new ObjectInputStream(new FileInputStream("recipeTitle.txt"));
			this.str2 = (String) oip2.readObject().toString();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}catch(ClassNotFoundException e1) {
			e1.printStackTrace();
		}finally {
			try {
				oip2.close();
			}catch(IOException e1) {
				e1.printStackTrace();
			}
		}

		//텍스트 필드 추가
		
		JTextArea recipeName2 = new JTextArea(this.str2);
		recipeName2.setBounds(111, 282, 306, 35);
		panel2.add(recipeName2);
		
		JTextArea recipeCont2 = new JTextArea(this.str);
		recipeCont2.setBounds(111, 338, 320, 290);
		panel2.add(recipeCont2);


		JTextField recipefile2= new JTextField();
		recipefile2.setBounds(104,24,327,224);
		panel2.add(recipefile2);
		
		JButton btnNewButton = new JButton("<<");
		btnNewButton.setBounds(60, 661, 105, 27);
		panel2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(">>");
		btnNewButton_1.setBounds(283, 661, 105, 27);
		panel2.add(btnNewButton_1);
		

		// 뒤로가기 버튼 
		button0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChangePanel.changePanel(mf, mp, new UpRecipe(mf)); 
			}
		});

		mf.repaint();
	}
}
//	public void saveFile() {
//
//		ObjectOutputStream oop =null;
//
//		try {
//
//			oop = new ObjectOutputStream (new FileOutputStream("recipeCont.txt"));
//			oop.writeObject(this.recipeCont2.getText());
//
//			oop.flush();
//
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		}finally {
//
//			try {
//				oop.close();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	
//	public void saveFile2() {
//		ObjectOutputStream oop2 = null;
//		try {
//			oop2 =new ObjectOutputStream (new FileOutputStream("recipeTitle"));
//			oop2.writeObject(this.recipeName2.getText());
//			oop2.flush();
//			
//		} catch (FileNotFoundException e) {
//		
//			e.printStackTrace();
//		} catch (IOException e) {
//		
//			e.printStackTrace();
//		}finally {
//			try{
//				oop2.close();
//			}catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
//	
//	
//	public void openFile() {
//		
//		ObjectInputStream oip = null;
//
//		try {
//			oip =new ObjectInputStream(new FileInputStream ("recipeCont.txt"));
//			//콘솔창에서 잘 출력이 되는지 보기 위함
//			oip.readObject().toString();
//
//		}catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				oip.close();
//			}catch (IOException e) {
//				e.printStackTrace();
//			}
//		}	
//	}
//	public void openFile2() {
//		
//		ObjectInputStream oip2 = null;
//		try {
//			oip2 = new ObjectInputStream (new FileInputStream("recipeTitle"));
//			oip2.readObject().toString();
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//	
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//		
//			e.printStackTrace();
//		}finally {
//			try {
//				oip2.close();
//			}catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
//
//}

