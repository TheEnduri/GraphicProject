import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class GraphicAlgorithm {

    public static void main(String[] args){
        try {

            GraphicAlgorithm mountainRed = new GraphicAlgorithm("C:\\SCHOOL\\MAGISTERKA\\Semestr_I\\Grafika\\GraphicProject\\src\\mountain.jpg");
            mountainRed.changeColor(1);
            mountainRed.save("JPG","redMountain.jpg");
            GraphicAlgorithm mountainGreen = new GraphicAlgorithm("C:\\SCHOOL\\MAGISTERKA\\Semestr_I\\Grafika\\GraphicProject\\src\\mountain.jpg");
            mountainGreen.changeColor(2);
            mountainGreen.save("JPG","greenMountain.jpg");
            GraphicAlgorithm mountainBlue = new GraphicAlgorithm("C:\\SCHOOL\\MAGISTERKA\\Semestr_I\\Grafika\\GraphicProject\\src\\mountain.jpg");
            mountainBlue.changeColor(3);
            mountainBlue.save("JPG","blueMountain.jpg");

            GraphicAlgorithm mountain1 = new GraphicAlgorithm("C:\\SCHOOL\\MAGISTERKA\\Semestr_I\\Grafika\\GraphicProject\\src\\mountain.jpg");
            mountain1.greyScale();
            mountain1.save("JPG","grayScaleMountain.jpg");

            GraphicAlgorithm mountain2 = new GraphicAlgorithm("C:\\SCHOOL\\MAGISTERKA\\Semestr_I\\Grafika\\GraphicProject\\src\\mountain.jpg");
            mountain2.sepia();
            mountain2.save("JPG","sepiaMountain.jpg");

            GraphicAlgorithm mountain3 = new GraphicAlgorithm("C:\\SCHOOL\\MAGISTERKA\\Semestr_I\\Grafika\\GraphicProject\\src\\mountain.jpg");
            try{
                mountain3.brightness(30);
                mountain3.save("JPG","brightnessMountain.jpg");
            }
            catch(IllegalAccessException ae) {
                ae.printStackTrace();
            }

            GraphicAlgorithm mountain4 = new GraphicAlgorithm("C:\\SCHOOL\\MAGISTERKA\\Semestr_I\\Grafika\\GraphicProject\\src\\mountain.jpg");
            try{
                mountain4.exaggeration(10,10);
                mountain4.save("JPG","exaggerationMountain.jpg");
            }
            catch(IllegalArgumentException ae) {
                ae.printStackTrace();
            }

        }
        catch(IOException e) {
            e.printStackTrace();
        }


    }

    private BufferedImage im=null;
    private WritableRaster raster=null;
    private int width = 0;
    private int height = 0;
    private int pixels[] = new int[3];

    /**
     * Create new instance of class
     *
     * @param fileName file from disk which is base to run class
     * @throws IOException
     */

    public GraphicAlgorithm(String fileName) throws IOException {
        im=ImageIO.read(new File(fileName));
        raster = im.getRaster();
        width = raster.getWidth();
        height = raster.getHeight();
    }
    /**
     * Method to change colour from picture to one of selected
     *
     * @param color 1-red, 2-green, 3-blue
     */
    public void changeColor(int color){
        Random r = new Random();
        int ww[]=new int[3];

        if(color>3 || color<1)
            throw new IllegalArgumentException("Unsupported argument. Value must be <1:3>");
        for(int i=0;i<width;i++)
        {

            for(int j=0;j<height;j++)
            {

                raster.getPixel(i, j, pixels);

                ww[0] = r.nextInt((int)((color==1?1:0)*pixels[0])+1);
                ww[1] = r.nextInt((int)((color==2?1:0)*pixels[1])+1);
                ww[2] = r.nextInt((int)((color==3?1:0)*pixels[2])+1);

                raster.setPixel(i, j, ww);

            }

        }

    }
    /**
     * Method to change colour from picture to grey scale
     *
     */
    public void greyScale(){

        double ww[]=new double[3];
        for(int i=0;i<width;i++)
        {

            for(int j=0;j<height;j++)
            {

                raster.getPixel(i, j, pixels);

                ww[0] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];
                ww[1] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];
                ww[2] = 0.299*pixels[0]+0.587*pixels[1]+0.114*pixels[2];

                raster.setPixel(i, j, ww);

            }

        }


    }
    /**
     * Method to change colour from picture to sepia
     *
     */
    public void sepia(){

        double ww[]=new double[3];



        this.greyScale();
        for(int i=0;i<width;i++)
        {

            for(int j=0;j<height;j++)
            {

                raster.getPixel(i, j, pixels);

                ww[0] = (pixels[0] * 0.393 + pixels[1] * 0.769 + pixels[2] * 0.189 ) / 1.351;
                ww[1] = (pixels[0] * 0.349 + pixels[1] * 0.686 + pixels[2] * 0.186 ) / 1.203;
                ww[2] = (pixels[0] * 0.272 + pixels[1] * 0.534 + pixels[2] * 0.131 ) / 2.140;

                raster.setPixel(i, j, ww);

            }

        }


    }
    /**
     * Method to change image's brightness
     *
     * @param howMore How more you want to add brightness
     * @throws IllegalAccessException
     */
    public void brightness(int howMore) throws IllegalAccessException
    {
        if(howMore<0)
            throw new IllegalAccessException("Only positive values!");
        double hsv[];
        double ww[];
        for(int i=0;i<width;i++)
        {

            for(int j=0;j<height;j++)
            {

                raster.getPixel(i, j, pixels);
                hsv=rgb2hsv(pixels[0], pixels[1], pixels[2]);
                hsv[2]=hsv[2]+howMore>240?240:hsv[2]+howMore;

                ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                raster.setPixel(i, j, ww);

            }

        }

    }

    /**
     * Method to change image exaggeration
     *
     * @param h how much you want to add to H i HSV model
     * @param s how much you want to add to S i HSV model
     * @throws IllegalArgumentException
     */
    public void exaggeration(int h, int s) throws IllegalArgumentException
    {

        if(h<0 || s<0)
            throw new IllegalArgumentException("Only positive values!");
        if(h>360 || s>240)
            throw new IllegalArgumentException("H must be <0:360>, S<0:240>");
        double hsv[];
        double ww[];
        for(int i=0;i<width;i++)
        {

            for(int j=0;j<height;j++)
            {

                raster.getPixel(i, j, pixels);
                hsv=rgb2hsv(pixels[0], pixels[1], pixels[2]);
                hsv[0]=hsv[0]+h>360?360:hsv[0]+h;
                hsv[1]=hsv[1]+s>240?240:hsv[1]+s;

                ww=hsv2rgb(hsv[0], hsv[1], hsv[2]);
                raster.setPixel(i, j, ww);

            }

        }

    }
    /**
     * Method to save image to disc, if file exists - overwrite file.
     *
     * @param type type of saved file ex. PNG, JPG
     * @param fileName filename of file which will be saved
     * @throws IOException
     */
    public void save(String type, String fileName) throws IOException{

        ImageIO.write(im,type,new File(fileName));
    }
    /**
     * Method to calculate HSV to RGB
     *
     * @param hue H in HSV model
     * @param sat S in HSV model
     * @param val V in HSV model
     * @return            void
     */
    private double[] hsv2rgb(double hue, double sat, double val)
    {
        double red = 0, grn = 0, blu = 0;
        double i, f, p, q, t;
        double result[] = new double[3];

        if(val==0)
        {
            red = 0;
            grn = 0;
            blu = 0;
        } else
        {
            hue/=60;
            i = Math.floor(hue);
            f = hue-i;
            p = val*(1-sat);
            q = val*(1-(sat*f));
            t = val*(1-(sat*(1-f)));
            if (i==0) {red=val; grn=t; blu=p;}
            else if (i==1) {red=q; grn=val; blu=p;}
            else if (i==2) {red=p; grn=val; blu=t;}
            else if (i==3) {red=p; grn=q; blu=val;}
            else if (i==4) {red=t; grn=p; blu=val;}
            else if (i==5) {red=val; grn=p; blu=q;}
        }
        result[0] = red;
        result[1] = grn;
        result[2] = blu;
        return result;
    }
    /**
     * Method to calculate RGB to HSV
     *
     * @param red R in RGB model
     * @param green G in RGB model
     * @param blue B in RGB model
     * @return            void
     */
    private double[] rgb2hsv(double red, double grn, double blu)
    {
        double hue, sat, val;
        double x, f, i;
        double result[] = new double[3];

        x = Math.min(Math.min(red, grn), blu);
        val = Math.max(Math.max(red, grn), blu);
        if (x == val){
            hue = 0;
            sat = 0;
        }
        else
        {
            f = (red == x) ? grn-blu : ((grn == x) ? blu-red : red-grn);
            i = (red == x) ? 3 : ((grn == x) ? 5 : 1);
            hue = ((i-f/(val-x))*60)%360;
            sat = ((val-x)/val);
        }
        result[0] = hue;
        result[1] = sat;
        result[2] = val;
        return result;
    }
}