/* Lori Kim
 * Date: 4/21/16
 * Assignment: Final project
 * Course: CIS14 Java
 * Ref.: An extenstion of the GameLoopExample3
 */

package zelda;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class GameClass extends Application {
	////////// VARIABLE DECLARATIONS
	// named constants
	static final int CANVAS_WIDTH = 1008;
	static final int CANVAS_HEIGHT = 690;
	public static final String TITLE = "Legend of Zelda";
	
	//// MUSIC
	String musicFile = "music/Sacred_Grove.mp3";     // For example
	
	//// BACKGROUND
    private int level1OffsetX = 0;
    private int level2OffsetX = 1;
    private int level3OffsetX = 2;
    
    //////// LINK
    
    //////// LINK'S VARYING OFFSETY
    //// LINK WALKING offset_Y variations
    private int walkRight = 1; // this is the consistent offsetY for link's sprite image
    private int walkLeft = 3;
    private int walkUp = 0;
    private int walkDown = 2;
    
	//// LINK SHOOTING offset_Y variations
    private int shootRight = 6;
    private int shootLeft = 7;
    private int shootUp = 4;
    private int shootDown = 5;
    
    //// LINK STARTING LOCATION
    private int lev1linkX = 5; 
    private int lev1linkY = 300; 
    private int Blev1linkX = CANVAS_WIDTH - 550; // Blev1linkY = link's back level starting position for X
    private int Blev1linkY = CANVAS_HEIGHT - 100; // Blev1linkY = link's back level starting position for Y
    private int lev2linkX = 450; // lev1linkY = link's next level starting position for Y
    private int lev2linkY = 240; // lev1linkX = link's next level starting position for X
    private int Blev2linkX = CANVAS_WIDTH - 150; // Blev1linkY = link's back level starting position for X
    private int Blev2linkY = 410; // Blev1linkY = link's back level starting position for Y
    private int lev3linkX = 10; // lev1linkY = link's next level starting position for Y
    private int lev3linkY = 380; // lev1linkX = link's next level starting position for X
    
    // common variables for enemy sprite image
 	private int aWidth = 50;
 	private int aHeight = 50;
 	private int aOffset_X = -10;
 	
 	private int aRight = -10;
 	private int aLeft = 20;
 	private int aUp = 100;
 	private int aDown = 60;
    
    //// ENEMY
    
    private int enemyAmt = 3;
    
    // randomly generated enemy x and y start location
    private int randEnemyX = 0; // ((Math.random() * (max width - min width)) + min width)
    private int randEnemyY = 0; // ((Math.random() * (max height - min height)) + min height)
	    
	// common variables for enemy sprite image
	private int enemyCount = 2;
	private int enemyColumns = 2;
	private int enemyWidth = 60;
	private int enemyHeight = 60;
	private int Eoffset_X = 0;
	private int Eoffset_Y = 60;
	
	//// ENEMY offset_Y variations
	private int eRight = 3;
	private int eLeft = 1;
	private int eUp = 2;
	private int eDown = 0;
   
    // enemies' collision direction
    private int[] eKey = new int[enemyAmt];
    
    //////// MAP BORDERS FOR 1ST LEVEL (boundaries' x and y locations are in relation to the canvas)
    //// Rectangle topBush
    // y = CANVAS_HEIGHT - 460
    // width = CANVAS_WIDTH
    private double bushLen = 1;
    
    //// Rectangle rightBush
    // x = CANVAS_WIDTH - 420
    // y = CANVAS_HEIGHT - 80
    private double rightBushWid = 420;
    private double rightBushLen = 80;
    
    //// small top Rectangle bushes
    private double smBushWid = 30;
    private double smBushLen = 30;
    
    //// small middle Rectangle bushes
    private double midBushWid = 10;
    private double midBushLen = 10;
    
    //// Rectangle lake
    // x = bushLakeX
    // y = CANVAS_HEIGHT - 225
    private double lakeWid = 425;
    private double lakeLen = 225;
    
    //// level for the enemies
    private Rectangle[] levelStage;
    //private Rectangle levExit;
    
    ImageView arrow = new ImageView(new Image("images/arrows.png"));
    
    int arrowNum;
    
    //// CREATE THE VISUALIZATION OF THE GAME
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// music
		String musicFile = "Sacred_Grove.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(Integer.MAX_VALUE);
		
		// create scene and panes and add nodes
    	Pane pane = new Pane();
		StackPane stackPane = new StackPane(); // stackpane to store background
        Scene scene = new Scene(pane, CANVAS_WIDTH, CANVAS_HEIGHT); 
        Canvas canvas = new Canvas(CANVAS_WIDTH,  CANVAS_HEIGHT);
        stackPane.getChildren().add(canvas);
        pane.getChildren().add(stackPane);
        
        //// SET BACKGROUND IMAGES AND BOUNDARIES
        
        // topBush boundary
        Rectangle[] bounds = {new Rectangle(0, CANVAS_HEIGHT - 460, CANVAS_WIDTH, bushLen), new Rectangle(CANVAS_WIDTH - 420, CANVAS_HEIGHT - 80, rightBushWid, rightBushLen), 
        		new Rectangle(CANVAS_WIDTH - 110, CANVAS_HEIGHT - 440, smBushWid, smBushLen), new Rectangle(CANVAS_WIDTH - 110, CANVAS_HEIGHT - 125, smBushWid, smBushLen),
        new Rectangle(CANVAS_WIDTH - 295, CANVAS_HEIGHT - 360, midBushWid, midBushLen), new Rectangle(CANVAS_WIDTH - 170, CANVAS_HEIGHT - 315, midBushWid, midBushLen),
        new Rectangle(CANVAS_WIDTH - 295, CANVAS_HEIGHT - 265, midBushWid, midBushLen), new Rectangle(CANVAS_WIDTH - 170, CANVAS_HEIGHT - 220, midBushWid, midBushLen),
        new Rectangle(CANVAS_WIDTH - 295, CANVAS_HEIGHT - 170, midBushWid, midBushLen), new Rectangle(0, CANVAS_HEIGHT - 225, lakeWid, lakeLen),
        new Rectangle(CANVAS_WIDTH, 0, 1, CANVAS_HEIGHT), new Rectangle(0, 0, 1, CANVAS_HEIGHT)};
        
        Rectangle[] level1Ent = {new Rectangle(0,0,0,0) ,new Rectangle(CANVAS_WIDTH - 575, CANVAS_HEIGHT - 5, 150, 1)};
        
        
        levelStage = bounds; // for enemies for now
        
        Rectangle[] bounds2 = {new Rectangle( 0, 385, 70, 10), new Rectangle(90, 365, 30, 10), new Rectangle(120, 345, 30, 10),
        			new Rectangle(150, 325, 20, 10), new Rectangle(170, 305, 10, 10), new Rectangle(180, 290, 20, 10),
        			new Rectangle(230, 220, 10, 40), new Rectangle(240, 200, 75, 10), new Rectangle(350, 200, 10, 40), new Rectangle(380, 200, 40, 30), 
        			new Rectangle(125, CANVAS_HEIGHT - 65, CANVAS_WIDTH - 250, 10), new Rectangle(0, 475, 85, 10), new Rectangle (100, 500, 10, 100), new Rectangle(CANVAS_WIDTH - 100, 475, 100, 10),
        			new Rectangle(CANVAS_WIDTH - 110, 500, 10, 130), new Rectangle(CANVAS_WIDTH - 375, 375, 375, 10), new Rectangle(CANVAS_WIDTH - 420, 175, 20, 200),
        			new Rectangle(0, 0, 1, CANVAS_HEIGHT)
        			};
        
        Rectangle[] level2Ent = {new Rectangle(420, 150, 150, 1), new Rectangle(CANVAS_WIDTH, 400, 1, 100)};
        
        Rectangle[] bounds3 = {new Rectangle(CANVAS_WIDTH, 0, 1, CANVAS_HEIGHT), new Rectangle(0, 180, CANVAS_WIDTH, 1),
        		new Rectangle(0, 180, 120, 125), new Rectangle(0, 305, 60, 50), 
        		new Rectangle(0, CANVAS_HEIGHT-225, 40, 200), new Rectangle(0, CANVAS_HEIGHT-170, 115, 150), new Rectangle(115, CANVAS_HEIGHT-100, CANVAS_WIDTH - 115, 100),
        		new Rectangle(140, 180, 30, 70), new Rectangle(140*2, 180, 20, 70), new Rectangle(135*3, 180, 20, 70),
        		new Rectangle(145*4, 180, 20, 70), new Rectangle(143*5, 180, 20, 70), new Rectangle(140*6, 180, 20, 70), new Rectangle(140*7, 180, 20, 70), new Rectangle(150, 370, 10, 10), new Rectangle(140*2, 370, 10, 10),
        		new Rectangle(150, 375 + 80, 10, 10), new Rectangle(140*2, 375 + 80, 10, 10),
        		new Rectangle(135*3, 325, 10, 10), new Rectangle(135*3, 415, 10, 10), new Rectangle(135*3, 505, 10, 10),
        		new Rectangle(148*4, 325, 10, 10), new Rectangle(148*4, 415, 10, 10), new Rectangle(148*4, 505, 10, 10),
        		new Rectangle(145*5, 325, 10, 10), new Rectangle(145*5, 415, 10, 10), new Rectangle(145*5, 505, 10, 10),
        		new Rectangle(140*6, 325, 10, 10), new Rectangle(140*6, 415, 10, 10), new Rectangle(140*6, 505, 10, 10)};
        
        Rectangle[] level3Ent = {new Rectangle(0, 375, 1, 100), new Rectangle(0,0,0,0)};
        
        //// CREATE ENEMY SPRITES
        SpriteAnimation[] enemy = new SpriteAnimation[enemyAmt];
        ImageView[] enemyView = new ImageView[enemyAmt];
        
        for (int i = 0; i < enemy.length; i++) {
        	enemy[i] = new SpriteAnimation(enemyCount, enemyColumns, Eoffset_X,
            		Eoffset_Y, enemyWidth, enemyHeight);
        	enemy[i].setCycleCount(Animation.INDEFINITE);
        	enemyView[i] = enemy[i].getViewPort();
        }
        
        //// CREATE LINK SPRITE
        SpriteAnimation link = new SpriteAnimation();
        
        ImageView linkView = link.getViewPort();
        
        link.setCycleCount(Animation.INDEFINITE);
        
        //// CREATE MAP
        Map map = new Map(CANVAS_WIDTH, CANVAS_HEIGHT, linkView, enemyView);
        map.createMap(level1OffsetX, lev1linkX, lev1linkY, bounds, level1Ent);
        
        pane.getChildren().addAll(map.getMapView(), linkView, arrow);
        pane.getChildren().addAll(enemyView);
        
        //// SPRITE ANIMATION CONTROL
        // timer for the enemy animation to go different directions 
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        	public void run() {
        		randMoveEnemyAni(enemy);
        	}
        }, 0, 5000);
        
        
    	arrow.setY(-1000);
    	
        enemySpawn(enemyView,200,430,150,370);
        
        
        link.getViewPort().setOnKeyPressed(e -> {
	    	switch (e.getCode()) {
	    	case RIGHT: 
	    		linkView.setX(linkView.getX() + 10);
	    		link.setLinkOffsetY(walkRight);
	    		break;
	    	case LEFT: 
	    		linkView.setX(linkView.getX() - 10);
	    		link.setLinkOffsetY(walkLeft);
	    		break; 
	    	case UP:
	    		linkView.setY(linkView.getY() - 10);
	    		link.setLinkOffsetY(walkUp);
	    		break; 
	    	case DOWN:
	    		linkView.setY(linkView.getY() + 10);
	    		link.setLinkOffsetY(walkDown);
	    		break;
	    	case SPACE:
	    		switch (link.getOffsetY()) {
	    			case 80:
	    				link.setLinkOffsetY(shootRight);
	    				createArrow(arrow, linkView, aRight);
	    				arrowNum = 0;
	    				break;
	    			case 240:
	    				link.setLinkOffsetY(shootLeft);
	    				createArrow(arrow, linkView, aLeft);
	    	    		arrowNum = 1;
	    				break;
	    			case 0:
	    				link.setLinkOffsetY(shootUp);
	    				createArrow(arrow, linkView, aUp);
	    	    		arrowNum = 2;
	    				break;
	    			case 160:
	    				link.setLinkOffsetY(shootDown);
	    				createArrow(arrow, linkView, aDown);
	    	    		arrowNum = 3;
	    				break;
	    		}
	    	default:
	    		break;
	    	}
	    	link.play();
	    });
        
        // pause link animation
        linkView.setOnKeyReleased(e -> {
        	if(e.getCode() != KeyCode.SPACE) {
        		link.pause();
        	}
	    });
        
        // set focus back on link for keyboard events
	    linkView.requestFocus();
        
        //// PLACE INTO SCENE
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //// TEXT FOR DEBUGGING
        // sets collided text
        Text text = new Text();
        text.setFill(Color.RED);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        pane.getChildren().add(text);
        
        //// CHECK COLLISIONS AND IMPLEMENT GAME LOOP
        
        // This is the all important game loop.
        // All the action happens here.
        // All the important methods are called from here.
        
        AnimationTimer gameLoop = new AnimationTimer() {
        	@Override
        	public void handle(long now) {
        		
        		moveEnemy(enemyView);
        		moveArrows(arrow, arrowNum);
        		arrowCollision(arrow, enemyView, map.getBounds());
        		map.checkLinkCollision(link.getOffsetY());;
        		
        		if (linkView.getBoundsInParent().intersects(map.getMapEntrance()[1].getBoundsInParent())) { // check collision between player and wall.
        			// going next level
        			if (map.getMapOffsetX() == level1OffsetX){
    					map.createMap(level2OffsetX, lev2linkX, lev2linkY, bounds2, level2Ent);
    					enemySpawn(enemyView,300,150,200,300);
    					
    				} else if (map.getMapOffsetX() == level2OffsetX) {
    					map.createMap(level3OffsetX, lev3linkX, lev3linkY, bounds3, level3Ent);
    					enemySpawn(enemyView,200,300,300,200);
    				}
        		}
        		
        		if (linkView.getBoundsInParent().intersects(map.getMapEntrance()[0].getBoundsInParent())) { // check collision between player and wall.
        			// going back a level
        			if (map.getMapOffsetX() == level2OffsetX) {
        				map.createMap(level1OffsetX, Blev1linkX, Blev1linkY, bounds, level1Ent);
        				enemySpawn(enemyView,200,430,150,370);
        			} else if(map.getMapOffsetX() == level3OffsetX) {
     					map.createMap(level2OffsetX, Blev2linkX, Blev2linkY, bounds2, level2Ent);
     					enemySpawn(enemyView,300,150,200,300);
     				}
        		}
        		
        		for (int i = 0; i < enemy.length; i++){
        			map.checkEnemyCollision(enemy[i].getOffsetY());
        		}
        	}
        		
        	
        	
        };
        gameLoop.start();
	}
	
	
	private void createArrow(ImageView arrow, ImageView linkView, int aOffsetY) {
		arrow.setX(linkView.getX());
		arrow.setY(linkView.getY());
		arrow.setViewport(new Rectangle2D(aOffset_X, aOffsetY, aWidth, aHeight));
	}
	
	private void moveArrows(ImageView arrows, int arrowNum) {
			switch (arrowNum) {
			case 0:
				arrows.setX(arrows.getX() + 10);
				break;
			case 1:
				arrows.setX(arrows.getX() - 10);
				break;
			case 2:
				arrows.setY(arrows.getY() - 10);
				break;
			case 3:
				arrows.setY(arrows.getY() + 10);
				break;
		}
	}
	
	////METHOD THAT CHECKS FOR COLLISIONS
		private void arrowCollision(ImageView arrow, ImageView[] enemyView, Rectangle[] bounds) {
			
			for (int i = 0; i < enemyView.length; i++) {
				if (arrow.getBoundsInParent().intersects(enemyView[i].getBoundsInParent())) {
						arrow.setX(CANVAS_WIDTH + 100);
						arrow.setY(CANVAS_HEIGHT + 100);
						enemyView[i].setX(enemyView[i].getX() + 1000);
						enemyView[i].setY(enemyView[i].getY() + 1000);
				}
			}
			
			for (int i = 0; i < bounds.length; i++) {
				if (arrow.getBoundsInParent().intersects(bounds[i].getBoundsInParent())) {
					arrow.setX(1000);
					arrow.setY(1000);
				}
			}
	   }
		

	private void enemySpawn (ImageView enemyView[], int x1, int x2, int y1, int y2) {
		// randomly set enemy starting location
	       for (int i = 0; i < enemyAmt; i++) {
	       		randEnemyX = (int)((Math.random() * x1) + x2); // ((Math.random() * (max width - min width)) + min width)
	       		randEnemyY = (int)((Math.random() * y1) + y2); // ((Math.random() * (max height - min height)) + min height)
	       		enemyView[i].setX(randEnemyX);
	       		enemyView[i].setY(randEnemyY);
	        }
		}
	
	///METHOD TO MOVE ENEMIES
	private void moveEnemy(ImageView[] enemyView){
			for (int i = 0; i < enemyAmt; i++) {
				switch (eKey[i]) {
				case 1:
					enemyView[i].setX(enemyView[i].getX() + 0.25);
					break;
				case 2:
					enemyView[i].setX(enemyView[i].getX() - 0.25);
					break;
				case 3:
					enemyView[i].setY(enemyView[i].getY() - 0.25);
					break;
				case 4:
					enemyView[i].setY(enemyView[i].getY() + 0.25);
					break;
				}
			}
	  }
	
	private void randMoveEnemyAni (SpriteAnimation[] enemy) {
		for (int i = 0; i < enemyAmt; i++) {
			eKey[i] = (int)((Math.random() * 3) + 1);
			// play enemy animation
			switch (eKey[i]) {
			case 1:
				enemy[i].setEnemyOffsetY(eRight);
				break;
			case 2:
				enemy[i].setEnemyOffsetY(eLeft);
				break;
			case 3:
				enemy[i].setEnemyOffsetY(eUp);
				break;
			case 4:
				enemy[i].setEnemyOffsetY(eDown);
				break;
			}
			enemy[i].play();
			}
	}
	

    // main method
    public static void main(String[] args) {
        launch(args);
    } // main
}
