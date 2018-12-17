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
    private int lenght=0;

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
      /*  i = 0;
        rgbArray = new int[len][300];
        for (BufferedImage image:images) {
            rgbArray[i]=rgbArr(image);
            i++;
        }
        System.out.println(lenght);*/

    }

    public double[][] getArray() {
        return intArtoDouble(rgbArray);
    }


     int[] rgbArr(BufferedImage image){
            ArrayList<BufferedImage> subImages = getSubImages(image);
            ArrayList<Pair> pairs = coord(subImages, 3,20,20);
            ArrayList<Pair> pairs1 = coord(subImages, 1,20,20);
            int[] coord = getCoord(pairs, pairs1);
            System.out.println(Arrays.toString(coord));
            ArrayList<Integer> rgb=new ArrayList<>();
           for(int y=coord[1];y<coord[3];y++){
               for (int x=coord[0];x<coord[2];x++){
                   int i=image.getRGB(x,y);
                   if(i==-1){
                       rgb.add(0);
                   }
                   else {
                       rgb.add(1);
                   }
               }
           }
           System.out.println(rgb.size());
           System.out.println(rgb);
           int [] rgbAr=new int[rgb.size()];
           int i=0;
         for (Integer integer:rgb) {
             rgbAr[i]=integer;
             i++;
         }
         return rgbAr;
    }


    BufferedImage loadImg(String imgname) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/Images/" + imgname + ".png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        File f=new File("C:/Users/BORAF/Desktop/Neuro/sohranit/"+imgname+"1.png");
        //Image dimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        System.out.println(Arrays.toString(image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth())));
        ArrayList<BufferedImage> subImages = getSubImages(image);
        ArrayList<Pair> pairs = coord(subImages, 2,image.getHeight(),image.getWidth());
        ArrayList<Pair> pairs1 = coord(subImages, 1,image.getHeight(),image.getWidth());
        int[] coord = getCoord(pairs, pairs1);
        System.out.println(Arrays.toString(coord));
        Image dimg=image.getSubimage(coord[0],coord[1],coord[2]-coord[0],coord[3]-coord[1]);
        BufferedImage image1 = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        try {
            image1.getGraphics().drawImage(ImageIO.read(new File("src/Images/blank.png")),0,0,null);
        }
        catch (IOException e){
            e.getCause();
        }
        image1.getGraphics().drawImage(dimg,4,2,null);
        try {
            ImageIO.write(image1, "png", f);
        }
        catch (IOException e){
            e.getCause();
        }
        return image1;
    }


    double[][] intArtoDouble(int[][] arr) {
        double[][] array = new double[arr.length][300];
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
        System.out.println(pairs);
        System.out.println(pairs2);
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



