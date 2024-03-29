package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import model.vo.Recipe;

public class RecipePage3 extends JPanel {
	
	private MainFrame mf;
	private ChangePanel cp;
	private JPanel rp3;
	
	public RecipePage3(MainFrame mf, Recipe rc) {
		
		this.mf = mf;
		rp3 = this;
		
		this.setSize(445, 770);
		this.setLayout(null);
		
		JPanel bar = new JPanel();
		bar.setLocation(0, 0);
		bar.setSize(445, 70);
		bar.setLayout(null);
		bar.setBackground(new Color(102, 204, 204));
		
		Font font = new Font("맑은 고딕", Font.BOLD, 27);
		Font font2 = new Font("맑은 고딕", Font.BOLD, 20);
		Font font3 = new Font("맑은 고딕", Font.BOLD, 15);
		
		JLabel barTitle = new JLabel(rc.getRecipeName());
		barTitle.setLocation(80, 10);
		barTitle.setSize(200, 50);
		barTitle.setForeground(Color.WHITE);
		barTitle.setFont(font);
		
		Image backImg = new ImageIcon("images/back sky.png").getImage().getScaledInstance(50,  50,  0);
		JButton back = new JButton(new ImageIcon(backImg));
		back.setLocation(10, 10);
		back.setSize(50, 50);
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cp.changePanel(mf, rp3, new Recommend(mf));
				
			}
			
		});

		Image person = new ImageIcon("images/person sky.png").getImage().getScaledInstance(50, 50, 0);
		JButton logIn = new JButton(new ImageIcon(person));
		logIn.setLocation(380, 10);
		logIn.setSize(50, 50);
		
		logIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cp.changePanel(mf, rp3, new MyFridge(mf));
				
			}
			
		});
		
		//사진 패널
		JPanel panel = new JPanel();
		panel.setLocation(0, 70);
		panel.setSize(445, 307);
		panel.setLayout(null);

		Image photo = new ImageIcon(rc.getRecipePicAdr()).getImage().getScaledInstance(445, 307, 0);
		JLabel label = new JLabel(new ImageIcon(photo));
		label.setSize(445, 307);

		//먹었어요 버튼
		JButton ate = new JButton("먹었어요");
		ate.setSize(145, 50);
		ate.setLocation(150, 377);
		ate.setFont(font2);
		
		//"레시피 순서" 글씨
		JLabel contTitle = new JLabel("레시피 순서");
		contTitle.setSize(150, 50);
		contTitle.setLocation(40, 427);
		contTitle.setFont(font2);
		
		
		//txt파일 레시피 설명
		JTextPane tp = new JTextPane();
		tp.setEditable(false);
		TextFromFile(tp, rc);
		
		//스크롤바
		JScrollPane scrollPane = new JScrollPane(tp, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(40, 477, 365, 170);
		scrollPane.setVisible(true);
		
		//"추가 필요 재료" 글씨
		JLabel addRcTitle = new JLabel("추가 필요 재료");
		addRcTitle.setSize(150, 50);
		addRcTitle.setLocation(40, 647);
		addRcTitle.setFont(font3);
		
		//add 영역
		bar.add(barTitle);
		bar.add(back);
		bar.add(logIn);
		
		panel.add(label);
		
		this.add(bar);
		this.add(panel);
		this.add(ate);
		this.add(contTitle);
		this.add(scrollPane);
		//this.add(addRcTitle);
		
		mf.add(this);		
	}
	
	private void TextFromFile(JTextPane tp, Recipe rc) {
		FileReader fr = null;
		try {
			String terms3 = rc.getRecipeCont();
			File file = new File(terms3);
			fr = new FileReader(terms3);
			while(fr.read() != -1) {
				tp.read(fr, null);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			try {
				fr.close();
			} catch (IOException e) {
		
				e.printStackTrace();
			}
		}
		
	}
}
