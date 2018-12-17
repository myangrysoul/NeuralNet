import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) {
        NeuralNet net;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("Net.ser"));
            net = (NeuralNet) is.readObject();
        } catch (Exception e1) {
            net = new NeuralNet();
            net.study();
            try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Net.ser"));
                os.writeObject(net);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        net.initInputLayer("A");
        HashMap<Character, String> map = net.check(net.inputLayer);
        for (Entry<Character, String> characterStringEntry : map.entrySet()) {
            String s = characterStringEntry.getKey() + " : " + characterStringEntry.getValue() + '%';
            System.out.println(s);
        }
    }
}

