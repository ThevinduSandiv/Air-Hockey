import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Paddle extends KeyAdapter
{
    private int x;
    private final int y = 600;
    private final int speed = 5;
    private ImageIcon paddleImg;
    private JLabel label;
    private int direction; // 1 - right, -1 - left, 0 - still
    private boolean pressed;
    public Paddle(ImageIcon pImg, JLabel pLabel)
    {
        x = 405 / 2;
        paddleImg = pImg;
        label = pLabel;
        pressed = false;
        direction = 0;

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

    public int getSpeed()
    {
        return speed;
    }

    public ImageIcon getImg()
    {
        return paddleImg;
    }

    public int getDirection()
    {
        return direction;
    }

    public boolean isPressed()
    {
        return pressed;
    }

    // Setters
    public void setX(int x)
    {
        this.x = x;
    }

    public void moveRight()
    {
        if(x < (405 - 75))
        {
            x += speed;
            label.setBounds(x, y, 64, 64);
        }
    }

    public void moveLeft()
    {
        if(x > 0)
        {
            x -= speed;
            label.setBounds(x, y, 64, 64);
        }
    }





    // overriding methods
    @Override
    public void keyPressed(KeyEvent e)
    {
        super.keyPressed(e);
        pressed = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            direction = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            direction = -1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        super.keyReleased(e);
        pressed = false;
        direction = 0;
    }
}
