//package SpaceAdvanture;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class PlaygroundView extends JPanel implements Observer {
	
	Model model;
	JPanel header;
	Universe universe;
	
	public PlaygroundView(Model m) {
        // Initialize
		model = m;
		model.addObserver(this);
		
		// Set the layout
		setLayout(new GridBagLayout());

		// Have two panels for this view
	    header = new JPanel(); 
	    universe = new Universe(model);

	    
	    // Modify the header
	    header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
	    header.add(Box.createHorizontalGlue());
	    JButton Resume = new JButton("Resume");
	    Resume.setFont(new Font("Serif", Font.BOLD, 25));
		header.add(Resume);
		JButton Stop = new JButton("Stop");
		Stop.setFont(new Font("Serif", Font.BOLD, 25));
		header.add(Box.createHorizontalGlue());
		header.add(Stop);
		JButton Reset = new JButton("Reset");
		Reset.setFont(new Font("Serif", Font.BOLD, 25));
		header.add(Box.createHorizontalGlue());
		header.add(Reset);
		header.add(Box.createHorizontalGlue());
		
		// Add Mouse Listener to the button in header
		Resume.addMouseListener(new MouseAdapter() {	
			public void mouseClicked (MouseEvent e) { 
				if (model.noresume == 1) {
					JOptionPane.showMessageDialog(null, "Resume button can not be press in this state\nPlease press reset first.", "Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					universe.requestFocus();
					universe.starttimer();
				}
			}
		});
		Stop.addMouseListener(new MouseAdapter() {	
			public void mouseClicked (MouseEvent e) { 
				universe.stoptimer();
			}
		});
		Reset.addMouseListener(new MouseAdapter() {	
			public void mouseClicked (MouseEvent e) { 
				// Reset the value in the universe (.ship and .star) - maybe make a reset function in all 3 files
				universe.stoptimer();
				universe.shiporigin();
				universe.starorigin();
				universe.repaint();
				model.noresume = 0;
			}
		});
		
	    // Apply the layout
	    GridBagConstraints c = new GridBagConstraints();
	    // we want the layout to stretch the components in both directions
	    c.fill = GridBagConstraints.BOTH;
	    // if the total X weight is 0, then it won't stretch horizontally.
	    c.weightx = 1; 
	    // Vertical space is divided in proportion to the Y weights of the components
	    c.weighty = 0.1;
	    c.gridy = 0;
	    this.add(header, c);
	    // It's fine to reuse the constraints object; add makes a copy.
	    c.weighty = 0.9;
	    c.gridy = 1;
	    this.add(universe, c);
    }
	
	@Override
	public void update(Model m) {
		if (model.play == 1) {
			this.universe.update();
		}
	}

}
