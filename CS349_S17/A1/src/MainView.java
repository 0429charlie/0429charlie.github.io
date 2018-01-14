//package SpaceAdvanture;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MainView extends JFrame implements Observer {
	
	private Model model;
	private JPanel mainPanel;

    public MainView(Model model) {
		// Set up windows
    	setTitle("Space Advanture");
        setMinimumSize(new Dimension(800, 600));
		setSize(1600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add views
		JPanel WelcomeView = new WelcomeView(model);
		JPanel SelectionView = new SelectionView(model);
		JPanel PlaygroundView = new PlaygroundView(model);
		mainPanel = new JPanel(new CardLayout());
		
		mainPanel.add(WelcomeView, "Welcome");
		mainPanel.add(SelectionView, "Selection");
		mainPanel.add(PlaygroundView, "Playground");
		
		// Hook up this observer so that it will be notified when the model changes.
        this.model = model;
        model.addObserver(this);
		
		getContentPane().add(mainPanel);
		this.setLocationRelativeTo(null);
    	setVisible(true);
	}
    
	@Override
	public void update(Model m) {
		String card = m.getCard();
		CardLayout cl = (CardLayout)(mainPanel.getLayout());
	    cl.show(mainPanel, card);
	}
}
