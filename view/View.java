package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Controller;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public View() {

		final JTextField text = new JTextField();
		JButton start = new JButton("Start");
		JButton sort = new JButton("Sort");
		final JTextArea area = new JTextArea();

		JPanel p = new JPanel();
		JPanel gP = new JPanel(new GridLayout(0, 1));
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					area.setText( Controller.start( text.getText() ) + " " );
				} catch (IOException e) {
					area.setText("Ошибка");
				}
				
			}
		});
		
		sort.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					Controller.sort();
				} catch (IOException e) {
					area.setText("Ошибка");
				}
			}
			
		});
		
		text.setPreferredSize(new Dimension(200,25));
		area.setPreferredSize(new Dimension(200,25));
		
		p.add(text);
		gP.add(p);

		p = new JPanel();
		p.add(start);
		gP.add(p);

		
		
		p = new JPanel();
		p.add(sort);
		gP.add(p);
		
		p = new JPanel();
		p.add(area);
		gP.add(p);
		
		add(gP);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

}
