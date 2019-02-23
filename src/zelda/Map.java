/* Author: Lori Kim
 * Assignment: Legend of Zelda Game
 * Course: CIS16
 * Date: 4/21/16
 */

package zelda;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Map {
	private ImageView link;
	private ImageView enemy[];
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;
	
	private ImageView level;
	private int levelOffsetX;
	private Rectangle bounds[];
	private Rectangle entrance[];
	
	
	public Map (int CANVAS_WIDTH, int CANVAS_HEIGHT, ImageView linkView, ImageView enemyView[]) {
		this.CANVAS_WIDTH = CANVAS_WIDTH;
		this.CANVAS_HEIGHT = CANVAS_HEIGHT;
		this.level = new ImageView(new Image("images/background.png", (CANVAS_WIDTH*4), CANVAS_HEIGHT, false, false));
		this.link = linkView;
		this.enemy = enemyView;
	}
	
	public void createMap(int levOffsetX, int linkSetX, int linkSetY, Rectangle bounds[], Rectangle ent[]) {
		this.level.setViewport(new Rectangle2D(CANVAS_WIDTH*levOffsetX, 0, CANVAS_WIDTH, CANVAS_HEIGHT));
		this.link.setX(linkSetX);
		this.link.setY(linkSetY);
		this.bounds = bounds;
		this.entrance = ent;
		this.levelOffsetX = levOffsetX;
	}
	
	public ImageView getMapView() {
		return this.level;
	}
	
	public int getMapOffsetX() {
		return this.levelOffsetX;
	}
	
	public Rectangle[] getMapEntrance() {
		return this.entrance;
	}
	
	public Rectangle[] getBounds() {
		return this.bounds;
	}
	
	//// CHECK COLLISION
	public void checkLinkCollision(int linkOffsetY) {
		for (int i = 0 ; i < bounds.length; i++) {
			if (link.getBoundsInParent().intersects(bounds[i].getBoundsInParent())) { // check collision between player and wall.
				moveLinkCol(linkOffsetY);
			}
		}
		
		for (int i = 0 ; i < enemy.length; i++) {
			if (link.getBoundsInParent().intersects(enemy[i].getBoundsInParent())) { // check collision between player and wall.
				level.setViewport(new Rectangle2D(CANVAS_WIDTH*3, 0, CANVAS_WIDTH, CANVAS_HEIGHT));
				link.setX(1000);
				link.setY(1000);
				for (int x = 0; x < enemy.length; x++) {
					enemy[x].setX(1000);
					enemy[x].setY(1000);
				}
			}
		}
   }
	
	public void checkEnemyCollision(int enemyOffsetY) {
		for (int i = 0 ; i < bounds.length; i++) {
			for (int x = 0 ; x < enemy.length; x++) {
				if (enemy[x].getBoundsInParent().intersects(bounds[i].getBoundsInParent())) { // check collision between player and wall.
					enemyCollision(enemyOffsetY, x);
				}
			}
		}
	}
	
	private void enemyCollision(int offsetY, int x) {
		switch (offsetY) {
			case 0: // down
				enemy[x].setY(enemy[x].getY() - 3);
				break;
			case 60: // left
				enemy[x].setX(enemy[x].getX() + 3);
				break;
			case 120: // up
				enemy[x].setY(enemy[x].getY() + 3);
				break;
			case 180: // right
				enemy[x].setX(enemy[x].getX() - 3);
				break;
			}
		}
	
	private void moveLinkCol(int spriteOffsetY) {
		// check collision on link right
		if (spriteOffsetY == 80) {
			link.setX(link.getX() - 3);
			// check collision on link left
		} else if (spriteOffsetY == 240) {
			link.setX(link.getX() + 3);
			// check collision on link up
		} else if (spriteOffsetY == 0) {
			link.setY(link.getY() + 3);
			// check collision on link down
		} else if (spriteOffsetY == 160) {
			link.setY(link.getY() - 3);
		}
	}
	
}

