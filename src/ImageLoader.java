import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageLoader implements Serializable {

    private char[] alphabet;
    transient ArrayList<BufferedImage> images = new ArrayList<>();
    private int[][] rgbArray;

    ImageLoader() {
        alphabet = new char[26];
        int i = 0;
        for (char c = 'A'; c<='Z'; c++) {
            alphabet[i] = c;
            i++;
        }
        int len = alphabet.length;
        for (int c = 0; c < len; c++) {
            images.add(loadImg(Character.toString(alphabet[c])));
        }
        i = 0;
        rgbArray = new int[len][images.get(0).getHeight() * images.get(0).getHeight()];
        for (BufferedImage image : images) {
            image.getRGB(0, 0, 20, 20, rgbArray[i], 0, 20);
            System.out.println(Arrays.toString(rgbArray[i]));
            i++;
        }
    }

    public double[][] getArray() {
        return intArtoDouble(rgbArray);
    }


    BufferedImage loadImg(String imgname) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/Images/" + imgname + ".png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        Image dimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        BufferedImage image1 = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        image1.getGraphics().drawImage(dimg, 0, 0, null);
        return image1;
    }

    double[][] intArtoDouble(int[][] arr) {
        double[][] array = new double[arr.length][arr[0].length];
        for (int x = 0; x < alphabet.length; x++) {
            for (int y = 0; y < 400; y++) {
                array[x][y] = arr[x][y];
            }
        }
        return array;
    }


}

