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
public class Goober extends Entity {

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
    public Goober(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 2 * gp.scale;
        solidArea.y = 4 * gp.scale;
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
    public int setSpeed(int n) {
        return Math.max(1, Math.round(4f * (80f / n)));
    }

    /**
     * Loads the ro's directional images for animation.
     */
    public void getPlayerImage() {
        try {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-f-1.png.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-f-2.png.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-f-3.png.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-b-1.png.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-b-2.png.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-b-3.png.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-l-1.png.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-l-2.png.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-l-3.png.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-l-4.png.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-r-1.png.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-r-2.png.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-r-3.png.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ro/Goober-r-4.png.png")));
        } catch (IOException | NullPointerException e) {
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
            if (spriteCounter > 6) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteNum2++;
                if(spriteNum2 == 5){
                    spriteNum2 = 1;
                }
                spriteCounter = 0;
            }
        }

        // Check for collisions
        collisionOn = false;
        gp.cChecker.checkTile(this);
        if(collisionOn){
        }
        // Move ro if no collision and a key is pressed
        if (!collisionOn && isMoving) {
            int deltaX = 0, deltaY = 0;
            // Adjust movement for diagonal directions
            if (keyH.upPressed){
                deltaY -= speed;
            }

            if (keyH.downPressed){
                deltaY += speed;
            }

            if (keyH.leftPressed){
                deltaX -= speed;
            }

            if (keyH.rightPressed){
                deltaX += speed;
            }
            if (deltaX != 0 && deltaY != 0) {
                deltaX = (int) Math.round(((double) deltaX) / Math.sqrt(2));
                deltaY = (int) Math.round(((double) deltaY) / Math.sqrt(2));
            }


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

        g2.drawImage(gettingDaImage(direction), screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
    public BufferedImage gettingDaImage(String input){
        switch (input) {
            case "up":
                switch (spriteNum2) {
                    case 1:
                        return up1;
                    case 2:
                    case 4:
                        return up2;
                    case 3:
                        return up3;
                }
            case "down":
                switch (spriteNum2) {
                    case 1:
                        return down1;
                    case 2:
                    case 4:
                        return down2;
                    case 3:
                        return down3;
                }
            case "left":
                switch (spriteNum2) {
                    case 1:
                        return left1;
                    case 2:
                        return left2;
                    case 3:
                        return left3;
                    case 4:
                        return left4;
                }
            case "right":
                switch (spriteNum2) {
                    case 1:
                        return right1;
                    case 2:
                        return right2;
                    case 3:
                        return right3;
                    case 4:
                        return right4;
                }
            default:
                return down1;
        }
    }
}
