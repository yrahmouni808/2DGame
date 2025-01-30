package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents the ro entity in the game, handling movement, rendering, and animations.
 */
public class Player extends Entity {

    /**
     * Reference to the main game panel.
     */
    GamePanel gp;

    /**
     * Handles keyboard input for ro movement.
     */
    KeyHandler keyH;

    /**
     * The X-coordinate of the ro's position on the screen.
     */
    public final int screenX;

    /**
     * The Y-coordinate of the ro's position on the screen.
     */
    public final int screenY;

    /**
     * Constructs a Player entity, initializes default values, and loads ro images.
     *
     * @param gp   the main game panel
     * @param keyH the key handler for capturing keyboard inputs
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 2 * gp.scale;
        solidArea.y = 4 * gp.scale;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 8 * gp.scale;
        solidArea.height = 8 * gp.scale;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the ro's default attributes such as position, speed, and direction.
     */
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = setSpeed(gp.FPS);
        direction = "down";
    }
    public int setSpeed(int n){
        int bruh = Math.round(4f * (80f/((float)  n)));
        if(bruh == 0){
            return 1;
        }
        return bruh;
    }
    /**
     * Loads the ro's directional images for animation.
     */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/blueboy/boy_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the ro's position, direction, and animation state based on keyboard input.
     */
    public void update() {
        // Determine movement direction
        boolean isMoving = false;
        if (keyH.upPressed) {
            direction = "up";
            isMoving = true;
        }
        if (keyH.downPressed) {
            direction = "down";
            isMoving = true;
        }
        if (keyH.leftPressed) {
            direction = "left";
            isMoving = true;
        }
        if (keyH.rightPressed) {
            direction = "right";
            isMoving = true;
        }

        // Increment sprite animation if moving
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > ((float) gp.FPS * 0.25f)) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }

        // Check for collisions
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // Move ro if no collision and a key is pressed
        if (!collisionOn && isMoving) {
            int deltaX = 0, deltaY = 0;

            // Adjust movement for diagonal directions
            if (keyH.upPressed) deltaY -= speed;
            if (keyH.downPressed) deltaY += speed;
            if (keyH.leftPressed) deltaX -= speed;
            if (keyH.rightPressed) deltaX += speed;

            // Apply movement
            worldX += deltaX;
            worldY += deltaY;
        }
    }


    /**
     * Draws the ro's current frame on the screen.
     *
     * @param g2 the Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNum == 1) ? up1 : up2;
            case "down" -> (spriteNum == 1) ? down1 : down2;
            case "left" -> (spriteNum == 1) ? left1 : left2;
            case "right" -> (spriteNum == 1) ? right1 : right2;
            default -> null;
        };

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
