/* Amanda Chung 03/26/2015
 * CIS016 Java Programming - Assignment 10 - Part 1 - Sprite Animation
 * 
 * This program animates a ghost image and makes it move diagonally down the screen.
 */

package zelda;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

	private final ImageView imageView;
    private int count;
    private int columns;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;
    private Duration duration = Duration.millis(800);
    
    private int lastIndex;

    public SpriteAnimation (
            ImageView imageView, 
            int count,   int columns,
            int offsetX, int offsetY,
            int width,   int height) {
    	
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }
    
    public SpriteAnimation(int count, int columns, int offsetX, int offsetY, int width, int height) {
    	this(new ImageView(new Image("images/enemy.png")), count, columns, offsetX, offsetY, width, height);
    }
    
    public SpriteAnimation() {
    	this(new ImageView(new Image("images/LinkSprite.png")), 6, 6, -10, 80, 80, 60);
    }
    
   protected void interpolate(double k) {
        int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            int x = (index % columns) * width  + offsetX;
            int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
        
    }
    
    public int getLastIndex() {
    	return lastIndex;
    }
    
    private void setViewPort() {
    	this.imageView.setViewport(new Rectangle2D(this.offsetX, this.offsetY, this.width, this.height));
    }
    
    public ImageView getViewPort() {
    	setViewPort();
    	return this.imageView;
    }
    
    public void setLinkOffsetY(int offset_Y) {
    	int initOffset = 80;
    	this.offsetY = initOffset * offset_Y;
    }
    
    public int getOffsetY() {
    	return this.offsetY;
    }
    
    public void setEnemyOffsetY(int offset_Y) {
    	int initOffset = 60;
    	this.offsetY = initOffset * offset_Y;
    }
    
    public void setArrowOffsetY(int offset_Y) {
    	int initOffset = 50;
    	this.offsetY = initOffset * offset_Y;
    }
}



// SpriteAnimation

/* REFERENCES:
 * 
 * SpriteAnimation.java borrowed from the following site:
 * Creating a Sprite Animation with JavaFX | Mike's Blog
 * http://blog.netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
 */