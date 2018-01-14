//package SpaceAdvanture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static java.lang.Math.*;

public class Ship implements Displayable {

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
	
	public Ship(Model m) {
		model = m;
		this.x = model.w;
    	this.y = model.h;
    	this.oldx = x;
    	this.oldy = y;
    	this.px = (model.px * this.model.c_w) / this.model.w;
    	this.py = (model.py * this.model.c_h) / this.model.h;
    	this.width = ShipImage.getImage().getWidth();
    	this.height = ShipImage.getImage().getHeight();
    	this.resized = 0;
	}
	
	@Override
	public void paint(Graphics g) {
		if (ShipImage.getImage() != null) {
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
            g2.drawImage(ShipImage.getImage(), this.px, this.py, this.width, this.height, null);
        }
	}
	
	public void moveright() {
		this.px = px+5;
	}
	
	public void moveleft() {
		this.px = px-5;
	}
	
	public void moveup() {
		this.py = py-5;
	}
	
	public void movedown() {
		this.py = py+5;
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
	
	public int getuniversex() {
		return this.x;
	}
	
	public int getuniversey() {
		return this.y;
	}
	
	public int getshipwidth() {
		return this.width;
	}
	
	public int getshipheigh() {
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
    	this.px = (model.px * this.model.c_w) / this.model.w;
    	this.py = (model.py * this.model.c_h) / this.model.h;
    	this.width = ShipImage.getImage().getWidth();
    	this.height = ShipImage.getImage().getHeight();
    	this.resized = 0;
	}
}

class ShipImage {
    private static BufferedImage theImage;

    public static BufferedImage getImage() {
        if (ShipImage.theImage == null) {
            try {
                ShipImage.theImage = ImageIO.read(new File("ship.png"));
            } catch (IOException e) {
                e.printStackTrace();
            //} catch (URISyntaxException e) {
              //  e.printStackTrace();
            }
        }
        return ShipImage.theImage;
    }
}