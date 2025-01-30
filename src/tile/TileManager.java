package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Manages tiles in the game, including loading tile images, reading map data,
 * and rendering tiles on the screen.
 */
public class TileManager {

    /**
     * Reference to the main game panel.
     */
    GamePanel gp;

    /**
     * Array of tiles used in the game.
     */
    public Tile[] tile;

    /**
     * 2D array representing the map with tile numbers.
     */
    public int[][] mapTileNum;

    /**
     * Constructs a TileManager, initializes tile data, and loads the map.
     *
     * @param gp the main game panel
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/map02.txt");
    }

    /**
     * Loads tile images from resources and assigns them to the tile array.
     */
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map data from a file and populates the mapTileNum array.
     *
     * @param filePath the path to the map file
     */
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol && line != null) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Draws the visible tiles on the screen based on the ro's position.
     *
     * @param g2 the Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw only if the tile is within the visible screen area
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
//            int worldX = worldCol * gp.tileSize;
//            int worldY = worldRow * gp.tileSize;
//            int screenX = worldX - gp.goober.worldX + gp.goober.screenX;
//            int screenY = worldY - gp.goober.worldY + gp.goober.screenY;
//
//            // Draw only if the tile is within the visible screen area
//            if (worldX + gp.tileSize > gp.goober.worldX - gp.goober.screenX &&
//                    worldX - gp.tileSize < gp.goober.worldX + gp.goober.screenX &&
//                    worldY + gp.tileSize > gp.goober.worldY - gp.goober.screenY &&
//                    worldY - gp.tileSize < gp.goober.worldY + gp.goober.screenY) {
//                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
//            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
