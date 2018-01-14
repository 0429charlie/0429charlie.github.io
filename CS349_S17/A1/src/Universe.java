//package SpaceAdvanture;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class Universe extends JComponent {
	
	//Model model;
	private int FPS = 30;
	private Timer animationTimer;
	private LinkedList<Star> stars = new LinkedList<Star>();
	private Ship ship;
	Model model;
	
	public Universe(Model m) {
		// Initialize
		this.setFocusable(true);
		model = m;
		
		// Get the ship and stars
        for (Dimension ds: model.star_positions) {
        	Star st = new Star(model);
        	Dimension d = ds;
        	st.setpxy(((d.width * this.model.c_w) / this.model.w), ((d.height * this.model.c_h) / this.model.h));
        	
        	this.addMouseListener(new MouseAdapter() {	
        		public void mousePressed (MouseEvent e) { 
        			if ((e.getX() >= st.getpx()) && (e.getX() <= (st.getpx()+st.getstarwidth())) && (e.getY() >= st.getpy()) && (e.getY() <= (st.getpy()+st.getstarheigh()))) {
        				st.tempx = e.getX();
            			st.tempy = e.getY();
        			}
        		}
        		
        		public void mouseReleased (MouseEvent e) {
        			if ((st.tempx != 0) && (st.tempy != 0)) {
        				int nowx = e.getX();
            			int nowy = e.getY();
            			int movex = nowx - st.tempx;
            			int movey = nowy - st.tempy;
            			for (Dimension dds: model.star_positions) {
            				if ((dds.width == st.getpx()) && (dds.height == st.getpy())) {
            					dds.width = st.getpx()+movex;
            					dds.height = st.getpy()+movey;
            				}
            	        }
            			st.setpxy(st.getpx()+movex, st.getpy()+movey);
            			st.tempx = 0;
            			st.tempy = 0;
            			repaint();
        			}
        		}
        	});
        	
        	this.stars.add(st);
        }  
        this.ship = new Ship(model);
        
        
        // Key listener 
        this.addKeyListener(new KeyAdapter() {

        	//@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'w') {
					ship.moveup();
				} else if (e.getKeyChar() == 's') {
					ship.movedown();
				} else if (e.getKeyChar() == 'd') {
					ship.moveright();
				} else if (e.getKeyChar() == 'a') {
					ship.moveleft();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					ship.moveup();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					ship.movedown();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					ship.moveleft();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					ship.moveright();
				}
			} 
        });
        
        
		// Set up timer to work
		this.animationTimer = new Timer(1000/this.FPS, event -> {
			for (Star s: this.stars) {
				s.setx(getWidth());
				s.sety(getHeight());
				s.move();
			}
			this.ship.setx(getWidth());
			this.model.c_w = getWidth();
			this.ship.sety(getHeight());
			this.model.c_h = getHeight();
			this.repaint();
			if (ship.getpx() <= 0) {
				this.animationTimer.stop();
				// Losing Message
				JOptionPane.showMessageDialog(null, "You Lose. Press Reset to play again or close the window", "You Lose...", JOptionPane.INFORMATION_MESSAGE);
				this.model.noresume = 1;
			} else if (ship.getpy() <= 0) {
				this.animationTimer.stop();
				// Losing Message
				JOptionPane.showMessageDialog(null, "You Lose. Press Reset to play again or close the window", "You Lose...", JOptionPane.INFORMATION_MESSAGE);
				this.model.noresume = 1;
			} else if ((ship.getpy() + ship.getshipheigh()) >= ship.getuniversey()) {
				this.animationTimer.stop();
				// Losing Message
				JOptionPane.showMessageDialog(null, "You Lose. Press Reset to play again or close the window", "You Lose...", JOptionPane.INFORMATION_MESSAGE);
				this.model.noresume = 1;
			} else if ((ship.getpx() + ship.getshipwidth()) >= ship.getuniversex()) {
				this.animationTimer.stop();
				// Winning Message
				JOptionPane.showMessageDialog(null, "You Win!! Press Reset to play again or close the window", "You Win!!", JOptionPane.INFORMATION_MESSAGE);
				this.model.noresume = 1;
			}
			for (Star s: this.stars) {
				if ((s.getpx() <= (ship.getpx() + ship.getshipwidth())) && ((s.getpx() + s.getstarwidth()) >= ship.getpx())) {
					if (s.getpy() <= (ship.getpy() + ship.getshipheigh()) && ((s.getpy() + s.getstarheigh()) >= ship.getpy())) {
						this.animationTimer.stop();
						// Losing Message
						JOptionPane.showMessageDialog(null, "You Lose. Press Reset to play again or close the window", "You Lose...", JOptionPane.INFORMATION_MESSAGE);
						this.model.noresume = 1;
					}
				}
			}
        });
        //this.animationTimer.start();
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        // dBuff and gBuff are used for double buffering
        Image dBuff = new BufferedImage(this.getWidth(), this.getHeight(), TYPE_3BYTE_BGR);
        Graphics gBuff = dBuff.getGraphics();
        gBuff.setClip(0, 0, this.getWidth(), this.getHeight());
        gBuff.setColor(Color.BLACK);
        gBuff.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Star s : this.stars) {
            s.paint(gBuff);
        }
        this.ship.paint(gBuff);
        g.drawImage(dBuff, 0, 0, this.getWidth(), this.getHeight(), null);
    }
	
	public void starttimer() {
		this.animationTimer.start();
	}
	
	public void stoptimer() {
		this.animationTimer.stop();
	}
	
	public void shipmoveup() {
		this.ship.moveup();
	}
	
	public void shipmovedown() {
		this.ship.movedown();
	}
	
	public void shipmoveright() {
		this.ship.moveright();
	}
	
	public void shipmoveleft() {
		this.ship.moveleft();
	}
	
	public void shiporigin() {
		this.ship.setpxy(((this.model.px * this.model.c_w) / this.model.w), ((this.model.py * this.model.c_h) / this.model.h));
	}
	
	public void starorigin() {
		int i = 0;
		for (Star s: this.stars) {
			Dimension d = this.model.getstar(i);
			s.setpxy(((d.width * this.model.c_w) / this.model.w), ((d.height * this.model.c_h) / this.model.h));
			i++;
		}
	}
	
	public void update() {
		int ii = 0;
		for (Star ss: this.stars) {
			ss.update();
			Dimension dd = this.model.getstar(ii);
        	ss.setpxy(((dd.width * this.model.c_w) / this.model.w), ((dd.height * this.model.c_h) / this.model.h));	
        	ii++;
		}
		int j = 0;
		for (Dimension dds: model.star_positions) {
			if (j >= ii) {
				Star str = new Star(model);
				str.setpxy(((dds.width * this.model.c_w) / this.model.w), ((dds.height * this.model.c_h) / this.model.h));
				this.stars.add(str);
			}
			j++;
        }
		this.ship.update();
		this.repaint();
	}
	
	public void addstar() {
		Star st = new Star(model);
    	this.stars.add(st);
    	Dimension dnew = new Dimension(1, 1);
    	model.addstar(dnew);
    	
    	this.addMouseListener(new MouseAdapter() {	
    		public void mousePressed (MouseEvent e) { 
    			if ((e.getX() >= st.getpx()) && (e.getX() <= (st.getpx()+st.getstarwidth())) && (e.getY() >= st.getpy()) && (e.getY() <= (st.getpy()+st.getstarheigh()))) {
    				st.tempx = e.getX();
        			st.tempy = e.getY();
    			}
    		}
    		
    		public void mouseReleased (MouseEvent e) {
    			if ((st.tempx != 0) && (st.tempy != 0)) {
    				int nowx = e.getX();
        			int nowy = e.getY();
        			int movex = nowx - st.tempx;
        			int movey = nowy - st.tempy;
        			for (Dimension dds: model.star_positions) {
        				if ((dds.width == st.getpx()) && (dds.height == st.getpy())) {
        					dds.width = st.getpx()+movex;
        					dds.height = st.getpy()+movey;
        				}
        	        }
        			st.setpxy(st.getpx()+movex, st.getpy()+movey);
        			st.tempx = 0;
        			st.tempy = 0;
        			repaint();
    			}
    		}
    	});
    	
    	this.repaint();
	}
}
