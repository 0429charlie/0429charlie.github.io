//package SpaceAdvanture;

import java.awt.Dimension;
import java.util.*;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
	
	// Data for the display
	String curCard;
	
	// Data for gameplay(ing)
	int play;
	
    // Enable or disable resume button
    int noresume;
	
	// Initial star
	List<Dimension> star_positions;

	// Initial ship
	int px;
	int py;
	
	// Original windows width and height
	int w;
	int h;
	
	// Current windows width and height
	int c_w;
	int c_h;
	
    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
        // Stars
        this.star_positions = new ArrayList();       
        // Resume button
        this.noresume = 0;
        // Size of the universe
        this.w = 1600;
        this.h = (1000*9)/10;
        // Current size of the universe
        this.c_w = this.w;
        this.c_h = this.h;
        // Starting point of the ship
        this.px = this.w/12;
        this.py = this.h/2;
        // Starting point of the stars (we have these two stars by default)
        Dimension d = new Dimension((this.w*1)/3, (this.h*1)/4);
    	addstar(d);
    	Dimension d2 = new Dimension((this.w*9)/10, (this.h*2)/3);
    	addstar(d2);
    	// playing?
    	this.play = 0;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
        
    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this); 
        }
    }
	
	// Method to change the card
	public void ChangeCard(String card) {
		curCard = card;
		notifyObservers();
	}
	
	// Method that return the current card
	public String getCard() {
		return curCard;
	}
	
	// Method to record the stars on model
	public void addstar(Dimension d) {
		star_positions.add(d);
	}
	
	// Method to read the stars on model
	public Dimension getstar(int i) {
		return star_positions.get(i);
	}
}
