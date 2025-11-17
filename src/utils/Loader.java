package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Loader {//para cargar imagenes y sonidos

    public static BufferedImage CargadorImagenes(String ruta){
        try {
            return ImageIO.read(Loader.class.getResource(ruta));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
