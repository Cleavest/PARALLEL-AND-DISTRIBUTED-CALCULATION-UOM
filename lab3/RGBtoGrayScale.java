import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class RGBtoGrayScale {
    public static void main(String args[]) {

        String fileNameR = null;
        String fileNameW = null;

        //Input and Output files using command line arguments
        if (args.length != 2) {
            System.out.println("Usage: java RGBtoGrayScale <file to read > <file to write>");
            System.exit(1);
        }
        fileNameR = args[0];
        fileNameW = args[1];

        //The same without command line arguments
        // fileNameR = "original.jpg";
        // fileNameW = "new.jpg";

        //Reading Input file to an image
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileNameR));
        } catch (IOException e) {}

        int numThreads = Runtime.getRuntime().availableProcessors();
        //Start timing
        long start = System.currentTimeMillis();

        int block = img.getHeight() / numThreads;
        int from = 0;
        int to = 0;

        RGBtoGrayGroupThread[] threads = new RGBtoGrayGroupThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            from = i * block;
            to = i * block + block;
            if (i == (numThreads - 1)) to = img.getHeight();
            threads[i] = new RGBtoGrayGroupThread(img, from,to);
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        //Stop timing
        long elapsedTimeMillis = System.currentTimeMillis()-start;

        //Saving the modified image to Output file
        try {
            File file = new File(fileNameW);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {}

        System.out.println("Done...");
        System.out.println("time in ms = "+ elapsedTimeMillis);
    }
}

class RGBtoGrayGroupThread extends Thread{

    private static final double redCoefficient = 0.299;
    private static final double greenCoefficient = 0.587;
    private static final double blueCoefficient = 0.114;

    private BufferedImage img;
    private final int myfrom;
    private final int myto;

    public RGBtoGrayGroupThread(BufferedImage img, int myfrom, int myto) {
        this.img = img;
        this.myfrom = myfrom;
        this.myto = myto;
    }

    public void run() {

        for (int y = myfrom; y < myto; y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x,y);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values, 8 bits per r,g,b
                //Calculating GrayScale
                int red = (int) (color.getRed() * redCoefficient);
                int green = (int) (color.getGreen() * greenCoefficient);
                int blue = (int) (color.getBlue() * blueCoefficient);
                //Creating new Color object
                color = new Color(red+green+blue,
                        red+green+blue,
                        red+green+blue);
                //Setting new Color object to the image
                img.setRGB(x, y, color.getRGB());
            }
        }
    }
}