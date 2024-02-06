import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        // initialize JFrame
        JFrame mainFrame = new JFrame("Air Hockey");
        mainFrame.setBounds(0, 0, 405, 720);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        // Game pane
        JLayeredPane gamePane = new JLayeredPane();
        gamePane.setFocusable(true);

        try
        {
            // Background
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("background.jpg");
            ImageIcon backgroundIcon = new ImageIcon(inputStream.readAllBytes());
            JLabel backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setBounds(0, 0, 405, 720);
            gamePane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

            // Restart Btn
            JButton restartBtn = new JButton("🔄");
            restartBtn.setBounds(405/2, 720/2, 50,50);
            restartBtn.setEnabled(false);
            restartBtn.setVisible(false);
            restartBtn.setFocusPainted(false);
            gamePane.add(restartBtn, JLayeredPane.POPUP_LAYER);

            // Paddle
            inputStream = Main.class.getClassLoader().getResourceAsStream("bar.png");
            ImageIcon paddleIcon = new ImageIcon(inputStream.readAllBytes());
            JLabel paddleLabel = new JLabel(paddleIcon);
            Paddle paddle = new Paddle(paddleIcon, paddleLabel);
            gamePane.addKeyListener(paddle);
            paddleLabel.setBounds(paddle.getX(), paddle.getY(), 64, 64);
            //paddleLabel.setBounds(0, 0, 200, 200);
            gamePane.add(paddleLabel, JLayeredPane.PALETTE_LAYER);

            // Disc
            inputStream = Main.class.getClassLoader().getResourceAsStream("disc.png");
            ImageIcon discIcon = new ImageIcon(inputStream.readAllBytes());
                // setting the icon of the game
                mainFrame.setIconImage(discIcon.getImage());
            Disc disc = new Disc(discIcon);
            JLabel discLabel = new JLabel(disc.getImg());
            discLabel.setBounds(disc.getX(), disc.getY(), 32, 32);
            gamePane.add(discLabel, JLayeredPane.PALETTE_LAYER);


            restartBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Random random = new Random();
                    int direction = random.nextInt(0, 2);
                    if(direction == 0)
                    {
                        disc.setXSpeed(5);
                    }
                    else
                    {
                        disc.setXSpeed(-5);
                    }
                    disc.setXSpeed(5);
                    disc.setYSpeed(-10);
                    disc.setX(405/2);
                    disc.setY(720/2);
                    restartBtn.setVisible(false);
                    restartBtn.setEnabled(false);
                }
            });

            // Use a Timer to update the disc's position at regular intervals
            Timer timer = new Timer(20, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(disc.isInRange())
                    {
                        if((disc.getY() + 10 > paddle.getY() && disc.getY() < paddle.getY() + 20) && (disc.getX() + 32 > paddle.getX() && disc.getX() < paddle.getX() + 64))
                        {
                            disc.setYSpeed(disc.getYSpeed() * -1);
                            disc.setXSpeed(disc.getXSpeed() + paddle.getSpeed() / 2);
                        }
                    }
                    else
                    {
                        if(collisionHandler(disc, paddle))
                        {
                            restartBtn.setEnabled(true);
                            restartBtn.setVisible(true);
                        }
                    }
                    disc.setX(disc.getX() + disc.getXSpeed());
                    disc.setY(disc.getY() + disc.getYSpeed());

                    discLabel.setBounds(disc.getX(), disc.getY(), 32, 32);
                    gamePane.repaint();
                }
            });
            timer.start();

            Timer paddleTimer = new Timer(20, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(paddle.isPressed())
                    {
                        if(paddle.getDirection() == 1)
                        {
                            paddle.moveRight();
                        }
                        else if(paddle.getDirection() == -1)
                        {
                            paddle.moveLeft();
                        }
                    }
                }
            });
            paddleTimer.start();
        }
        catch(Exception exception)
        {
            System.out.println(exception);
        }


        mainFrame.add(gamePane);
        mainFrame.setVisible(true);
    }

    public static boolean collisionHandler(Disc disc, Paddle paddle)
    {
        boolean lost = false;
        if(disc.getX() < 0)
        {
            disc.setXSpeed(disc.getXSpeed() * -1);
        }
        if(disc.getX() > (405 - 40))
        {
            disc.setXSpeed(disc.getXSpeed() * -1);
        }

        if(disc.getY() < 0)
        {
            disc.setYSpeed(disc.getYSpeed() * -1);
        }
        if(disc.getY() > (720 - 30))
        {
            lost = true;
        }

        return lost;
    }

}