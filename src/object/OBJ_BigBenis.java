package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_BigBenis extends SuperObject{

    public OBJ_BigBenis(){

        name = "Big Benis";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/benis_brown.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
