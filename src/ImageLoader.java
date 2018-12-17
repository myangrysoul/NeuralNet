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
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet[i] = c;
            i++;
        }
        int len = alphabet.length;
        for (int c = 0; c < len; c++) {
            images.add(loadImg(Character.toString(alphabet[c])));
        }
        i = 0;
        rgbArray = new int[len][400];
        for (BufferedImage image:images) {
            rgbArray[i]=image.getRGB(0,0,20,20,null,0,20);
            i++;
        }
    }

    public double[][] getArray() {
        return intArtoDouble(rgbArray);
    }
    BufferedImage loadImg(String imgname) {
        BufferedImage image = null;
        BufferedImage blank=null;
        try {
            image = ImageIO.read(new File("src/Images/" + imgname + ".png"));
            blank=ImageIO.read(new File("src/Images/blank.png"));

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        //System.out.println(Arrays.toString(image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth())));
        ArrayList<BufferedImage> subImages = getSubImages(image);
        ArrayList<Pair> pairs = coord(subImages, 2,image.getHeight(),image.getWidth());
        ArrayList<Pair> pairs1 = coord(subImages, 1,image.getHeight(),image.getWidth());
        int[] coord = getCoord(pairs, pairs1);
        Image dimg=image.getSubimage(coord[0],coord[1],coord[2]-coord[0],coord[3]-coord[1]);
        BufferedImage image1 = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        image1.getGraphics().drawImage(blank,0,0,null);
        image1.getGraphics().drawImage(dimg,4,2,null);
        return image1;
    }
    double[][] intArtoDouble(int[][] arr) {
        double[][] array = new double[arr.length][400];
        for (int x = 0; x < alphabet.length; x++) {
            for (int y = 0; y < arr[x].length; y++) {
                array[x][y] = arr[x][y];
            }
        }
        return array;
    }
    ArrayList<BufferedImage> getSubImages(BufferedImage image) {
        ArrayList<BufferedImage> subs = new ArrayList<BufferedImage>();
        for (int y = 0; y <= image.getHeight()-2; y += 1) {
            for (int x = 0; x <= image.getWidth()-2; x += 1) {
                subs.add(image.getSubimage(x, y, 2, 2));
            }
        }
        return subs;
    }
    ArrayList<Pair> coord(ArrayList<BufferedImage> subImages, int number,int h, int w) {
        int x;
        int y;
        ArrayList<Pair> coord = new ArrayList<>();
        for (BufferedImage sub : subImages) {
            int counter = 0;
            int[] rgb = sub.getRGB(0, 0, 2, 2, null, 0, 2);
            for (int pixel : rgb) {
                if (pixel != -1) {
                    counter++;
                }
            }
            if (counter > number) {
                int var=subImages.indexOf(sub);
                x = var%(w-1);
                y = var/(w-1);
                coord.add(new Pair(x, y));
            }
        }
        return coord;
    }
    int[] getCoord(ArrayList<Pair> pairs, ArrayList<Pair> pairs2) {
        int xmin = 20, ymin, xmax = 0, ymax;
        ymax = pairs2.get(pairs2.size() - 1).getVar2();
        ymin=pairs.get(0).getVar2();
        for (Pair pair : pairs) {
            if (xmin > pair.getVar1()) {
                xmin = pair.getVar1();
            }
        }
        for (Pair pair : pairs2) {
            xmax = xmax < pair.getVar1()? pair.getVar1() : xmax;
        }
        return new int[] { xmin, ymin, xmax+1, ymax+1 };
    }
}



