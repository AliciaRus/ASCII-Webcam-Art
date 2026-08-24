import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    static String characters = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

    public static void main(String[] args) throws IOException {
        //Webcam setup
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setFPSLimit(1.0);

        //Display webcam
        JFrame frame = new JFrame();
        frame.add(webcamPanel);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        webcam.open();

        //Capture initial frame
        String imgFileName = "firstCapture.jpg";
        File imgFile = new File(imgFileName);
        ImageIO.write(webcam.getImage(),"JPG", imgFile);
        BufferedImage img = null;

        String resultFileName = "result.txt";

        try {
            img = ImageIO.read(imgFile);
        }
        catch (IOException e) {
            System.out.println("Failed to read file");
            return;
        }

        //Scaling the image down
        double scaleFactor = 0.2;
        int imgWidth = (int) (img.getWidth() * scaleFactor);
        int imgHeight = (int) (img.getHeight() * scaleFactor);

        char[][] imgArr = new char[imgWidth][imgHeight];

        System.out.println("Successfully loaded image!");
        System.out.println("Image size: " + imgWidth + " x " + imgHeight + "\n");

        while (true) {
            //Wait between ASCII frames
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            BufferedImage imgFrame = webcam.getImage();
            if (imgFrame != null) {
                fillCharArray(imgArr, imgFrame, scaleFactor);
                printImageToFile(imgArr, resultFileName);
            }

            ImageIO.write(imgFrame, "JPG", imgFile);

            try {
                img = ImageIO.read(imgFile);
            } catch (IOException e) {
                System.out.println("Failed to read file");
                continue;
            }

            fillCharArray(imgArr, img, scaleFactor);
            printImageToFile(imgArr, resultFileName);
        }

    }

    public static void fillCharArray(char[][] arr, BufferedImage img, double scaleFactor) {
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[x].length; y++) {
                int origX = (int) (x / scaleFactor);
                int origY = (int) (y / scaleFactor);

                int RGBint = img.getRGB(origX, origY);
                Color color = new Color(RGBint);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int brightness = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                brightness *= (characters.length()-1) / 255.0;
                arr[x][y] = characters.charAt(brightness);
            }
        }
    }
    public static void printImageToConsole(char[][] arr) {
        for (int y = 0; y < arr[0].length; y++) {
            for (int x = 0; x < arr.length; x++) {
                System.out.print(arr[x][y]);
            }
            System.out.println();
        }
    }

    public static void printImageToFile(char[][] arr, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int y = 0; y < arr[0].length; y++) {
                for (int x = 0; x < arr.length; x++) {
                    writer.print(arr[x][y]);
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be created");
        }
    }

}
