import javax.swing.*;
import java.util.Random;

public class Disc
{
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private ImageIcon img;

    public Disc(ImageIcon pImg)
    {
        x = 405 / 2;
        y = 720 / 2;
        Random random = new Random();
        int direction = random.nextInt(0, 2);
        if(direction == 0)
        {
            xSpeed = 3; // todo should change accordingly
        }
        else
        {
            xSpeed = -3; // todo should change accordingly
        }
        ySpeed = -7; // todo should change accordingly
        img = pImg;
    }



    // Getters
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getXSpeed()
    {
        return xSpeed;
    }

    public int getYSpeed()
    {
        return ySpeed;
    }

    public ImageIcon getImg()
    {
        return img;
    }

    public boolean isInRange()
    {
        boolean xRange = x < (405 - 40) && x > 0;
        boolean yRange = y < (720 - 60) && y > 0;

        return xRange && yRange;
    }


    // setters
    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setXSpeed(int xSpeed)
    {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed)
    {
        this.ySpeed = ySpeed;
    }

    public void setImg(ImageIcon img)
    {
        this.img = img;
    }

    public void incrementSpeed()
    {
        xSpeed += 1; // todo should change accordingly
        ySpeed += 1; // todo should change accordingly
    }
}
