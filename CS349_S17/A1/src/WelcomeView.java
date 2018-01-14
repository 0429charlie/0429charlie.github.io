//package SpaceAdvanture;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WelcomeView extends JPanel implements Observer {
	
	Model model;

    /**
     * Create a new View.
     */
    public WelcomeView(Model m) {

		// Initialize
		model = m;
		model.addObserver(this);
		
		// Set the backgroud
		setBackground(Color.BLACK);
		
		// Set the layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Add Label and Button
		this.add(Box.createVerticalGlue());
		JLabel L = new JLabel("Welcome to the Space!!");
		L.setAlignmentX(Component.CENTER_ALIGNMENT);
		L.setFont(new Font("Serif", Font.BOLD, 40));
		L.setForeground(Color.WHITE);
		this.add(L);
		this.add(Box.createVerticalGlue());
		JButton Ready = new JButton("Stand By...");
		Ready.setAlignmentX(Component.CENTER_ALIGNMENT);
		Ready.setFont(new Font("Serif", Font.PLAIN, 30));
		this.add(Ready);
		this.add(Box.createVerticalGlue());
		JButton Play = new JButton("Emergency Dispatch");
		Play.setAlignmentX(Component.CENTER_ALIGNMENT);
		Play.setFont(new Font("Serif", Font.PLAIN, 30));
		this.add(Play);
		this.add(Box.createVerticalGlue());
		JButton Help = new JButton("Help");
		Help.setAlignmentX(Component.CENTER_ALIGNMENT);
		Help.setFont(new Font("Serif", Font.PLAIN, 30));
		this.add(Help);
		this.add(Box.createVerticalGlue());
		
		// Add action for the button
		Ready.addMouseListener(new MouseAdapter() {	
			public void mouseClicked (MouseEvent e) { 
				model.ChangeCard("Selection");
			}
		});
		
		Play.addMouseListener(new MouseAdapter() {	
			public void mouseClicked (MouseEvent e) { 
				model.play = 1;
				model.ChangeCard("Playground");
			}
		});
		
		Help.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Press 'Stand By...' for level selesction\n" + "Press 'Emergency Dispatch' to play with default setting\n", "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});
    }

	@Override
	public void update(Model m) {
		// TODO Auto-generated method stub
		
	}
	
	
} 
