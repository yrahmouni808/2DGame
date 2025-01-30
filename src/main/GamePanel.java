package main;

import entity.Goober;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Represents the main game panel where all game elements are managed and rendered.
 * Implements the game loop to control game updates and rendering.
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    /**
     * The original size of a tile in pixels (before scaling).
     */
    final int originalTileSize = 16;

    /**
     * The scale factor applied to the original tile size.
     */
    public final int scale = 3;

    /**
     * The size of a tile after scaling (in pixels).
     */
    public final int tileSize = originalTileSize * scale; // 48x48 pixels

    /**
     * The number of tiles horizontally on the screen.
     */
    public final int maxScreenCol = 16;

    /**
     * The number of tiles vertically on the screen.
     */
    public final int maxScreenRow = 12;

    /**
     * The width of the screen in pixels.
     */
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels

    /**
     * The height of the screen in pixels.
     */
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World settings
    /**
     * The number of tiles horizontally in the game world.
     */
    public final int maxWorldCol = 50;

    /**
     * The number of tiles vertically in the game world.
     */
    public final int maxWorldRow = 50;

    /**
     * The width of the game world in pixels.
     */
    public final int worldWidth = tileSize * maxWorldCol;

    /**
     * The height of the game world in pixels.
     */
    public final int WorldWidth = tileSize * maxWorldRow;

    /**
     * The desired frames per second (FPS) for the game loop.
     */
    public int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this,keyH);
    public Goober goober = new Goober(this,keyH);
    public SuperObject[] obj = new SuperObject[10];

    /**
     * Constructs the game panel and initializes its properties.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        assetSetter.setObject();
    }

    /**
     * Starts the game thread and begins the game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The main game loop, responsible for updating and rendering the game.
     */
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Updates the game state, including the ro.
     */
    public void update() {
//        goober.update();
        player.update();
    }

    /**
     * Paints the game components, including the tiles and the ro, onto the screen.
     *
     * @param g the Graphics object used for rendering
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //background
        tileM.draw(g2);

        //objects
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        //player
        player.draw(g2);
//       goober.draw(g2);


        g2.dispose();
    }
}
