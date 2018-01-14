//package SpaceAdvanture;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SelectionView extends JPanel implements Observer {
	
	Model model;
	
	Universe universe;
	JPanel Footer;

    /**
     * Create a new View.
     */
    public SelectionView(Model m) {
        // Initialize
		model = m;
		model.addObserver(this);
		
		// Set the layout
		setLayout(new GridBagLayout());

		// Have two panels for this view
		universe = new Universe(model);
		Footer = new JPanel();
		
		// Apply the layout
	    GridBagConstraints c = new GridBagConstraints();
	    // we want the layout to stretch the components in both directions
	    c.fill = GridBagConstraints.BOTH;
	    // if the total X weight is 0, then it won't stretch horizontally.
	    c.weightx = 1; 
	    // Vertical space is divided in proportion to the Y weights of the components
	    c.weighty = 0.9;
	    c.gridy = 0;
	    this.add(universe, c);
	    // It's fine to reuse the constraints object; add makes a copy.
	    c.weighty = 0.1;
	    c.gridy = 1;
	    this.add(Footer, c);
		
		
		
		
		/*
		// Set the layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(Box.createVerticalGlue());
		*/
		
	    Footer.setLayout(new BoxLayout(Footer, BoxLayout.X_AXIS));
	    Footer.setBorder(new EmptyBorder(10, 30, 10, 30));
	    JButton Help = new JButton("Help");
		Help.setFont(new Font("Serif", Font.PLAIN, 25));
		Help.setAlignmentX(Component.LEFT_ALIGNMENT);
		Footer.add(Help);
	    Footer.add(Box.createHorizontalGlue());
	    JButton Addstar = new JButton("Add Star");
		Addstar.setFont(new Font("Serif", Font.PLAIN, 25));
		Addstar.setAlignmentX(Component.LEFT_ALIGNMENT);
		Footer.add(Addstar);
	    Footer.add(Box.createHorizontalGlue());
	    Footer.add(Box.createHorizontalGlue());
		JButton takeoff = new JButton("Take Off");
		takeoff.setFont(new Font("Serif", Font.PLAIN, 25));
		takeoff.setAlignmentX(Component.RIGHT_ALIGNMENT);
		Footer.add(takeoff);
		
		
		takeoff.addMouseListener(new MouseAdapter() {	
			public void mouseClicked (MouseEvent e) {
				model.play = 1;
				model.ChangeCard("Playground");
			}
		});
		
		Help.addMouseListener(new MouseAdapter() {	
			public void mouseClicked (MouseEvent e) { 
				JOptionPane.showMessageDialog(null, "Press 'Take Off' to play the game\nPress 'Add star' to add obstacles\nDrag the star to change their position", "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		Addstar.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e) {
				universe.addstar();
			}
		});
    }

	@Override
	public void update(Model m) {
		// TODO Auto-generated method stub
		
	}
}