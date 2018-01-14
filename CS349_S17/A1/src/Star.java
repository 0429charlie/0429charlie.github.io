//package SpaceAdvanture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static java.lang.Math.*;

public class Star implements Displayable {

	private int width;
	private int height;
	private int x;
	private int y;
	private int oldx;
	private int oldy;
	private int px;
	private int py;
	private int resized;
	Model model;
	int tempx;
	int tempy;

    public Star(Model m) {
    	model = m;
    	this.x = model.w;
    	this.y = model.h;
    	this.oldx = x;
    	this.oldy = y;
    	this.width = StarImage.getImage().getWidth();
    	this.height = StarImage.getImage().getHeight();
    	this.resized = 0;
    	this.px = 1;
    	this.py = 1;
    	this.tempx = 0;
    	this.tempy = 0;
    }
	
	@Override
	public void paint(Graphics g) {
		if (StarImage.getImage() != null) {
            Graphics2D g2 = (Graphics2D) g;
            if (this.resized == 1) {
            	this.width = (this.width * this.x) / this.oldx;
            	this.height = (this.height * this.y) / this.oldy;
            	this.py = (this.py * this.y) / this.oldy;
            	this.px = (this.px * this.x) / this.oldx;
            	this.oldx = this.x;
            	this.oldy = this.y;
            	this.resized = 0;
            }
            g2.drawImage(StarImage.getImage(), this.px, this.py, this.width, this.height, null);
        }
	}
	
	public void move() {
		if (this.px + this.width <= 0) {
			this.px = x;
		} else {
			this.px = px-5;
		}
	}	
	
	public void setx(int x) {
		if (this.x != x) {
			this.oldx = this.x;
			this.x = x;
			this.resized = 1;
		}
	}
	
	public void sety(int y) {
		if (this.y != y) {
			this.oldy = this.y;
			this.y = y;
			this.resized = 1;
		}
	}
	
	public int getpx() {
		return this.px;
	}
	
	public int getpy() {
		return this.py;
	}
	
	public int getstarwidth() {
		return this.width;
	}
	
	public int getstarheigh() {
		return this.height;
	}
	
	public void setpxy(int x, int y) {
		this.px = x;
		this.py = y;
	}
	
	public void update() {
		this.x = model.w;
    	this.y = model.h;
    	this.oldx = x;
    	this.oldy = y;
    	this.width = StarImage.getImage().getWidth();
    	this.height = StarImage.getImage().getHeight();
    	this.resized = 0;
    	this.px = 1;
    	this.py = 1;
	}
}

class StarImage {
    private static BufferedImage theImage;

    public static BufferedImage getImage() {
        if (StarImage.theImage == null) {
            try {
            	System.out.println("In the try");
                StarImage.theImage = ImageIO.read(new File("star.png"));
            } catch (IOException e) {
                e.printStackTrace();
            //} catch (URISyntaxException e) {
            //    e.printStackTrace();
            }
        }
        return StarImage.theImage;
    }
}