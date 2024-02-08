import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Score score = new Score();

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

            // Score texts
            JLabel highScore = new JLabel("HighScore: " + score.getHighScore());
            highScore.setBounds(0,0, 200, 30);
            highScore.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            gamePane.add(highScore, JLayeredPane.PALETTE_LAYER);

            JLabel playerScore = new JLabel("Score: " + score.getScore());
            playerScore.setBounds(280,0, 200, 30);
            playerScore.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            gamePane.add(playerScore, JLayeredPane.PALETTE_LAYER);

            // Restart Btn
            JButton restartBtn = new JButton("🔄");
            restartBtn.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
            restartBtn.setBounds(405/2 - 50/2, 720/2 - 50/2, 50,50);
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


            // Glow
            inputStream = Main.class.getClassLoader().getResourceAsStream("glow.png");
            ImageIcon glowIcon = new ImageIcon(inputStream.readAllBytes());
            JLabel glowLabel = new JLabel(glowIcon);
            glowLabel.setVisible(false);
            gamePane.add(glowLabel, JLayeredPane.POPUP_LAYER);

            // Glow Timer
            Timer glowTimer = new Timer(200, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    glowLabel.setVisible(false);
                }
            });
            glowTimer.setRepeats(false);


            // Disc
            inputStream = Main.class.getClassLoader().getResourceAsStream("disc.png");
            ImageIcon discIcon = new ImageIcon(inputStream.readAllBytes());
                // setting the icon of the game
                mainFrame.setIconImage(discIcon.getImage());
            Disc disc = new Disc(discIcon);
            JLabel discLabel = new JLabel(disc.getImg());
            discLabel.setBounds(disc.getX(), disc.getY(), 32, 32);
            gamePane.add(discLabel, JLayeredPane.PALETTE_LAYER);

            Random random = new Random();

            restartBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    highScore.setText("HighScore: " + score.getHighScore());
                    int direction = random.nextInt(0, 2);
                    if(direction == 0)
                    {
                        disc.setXSpeed(3);
                    }
                    else
                    {
                        disc.setXSpeed(-3);
                    }
                    disc.setYSpeed(-7);
                    disc.setX(405/2 - 32/2);
                    disc.setY(720/2 - 50/2);

                    score.reset();
                    restartBtn.setVisible(false);
                    restartBtn.setEnabled(false);
                }
            });

            // Use a Timer to update the disc's position at regular intervals
            Timer timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(disc.isInRange())
                    {
                        if((disc.getY() + 10 > paddle.getY() && disc.getY() < paddle.getY() + 20) && (disc.getX() + 32 > paddle.getX() && disc.getX() < paddle.getX() + 64))
                        {
                            disc.setYSpeed(disc.getYSpeed() * -1);
                            int speedDirection = random.nextInt(-1, 2);
                            disc.setXSpeed(disc.getXSpeed() + (speedDirection * (paddle.getSpeed() / 3)));
                        }
                    }
                    else
                    {
                        if(collisionHandler(disc, paddle, glowLabel, glowTimer, score))
                        {
                            if(score.getScore() > score.getHighScore())
                            {
                                score.setHighScore(score.getScore());
                            }
                            restartBtn.setEnabled(true);
                            restartBtn.setVisible(true);
                        }
                    }
                    disc.setX(disc.getX() + disc.getXSpeed());
                    disc.setY(disc.getY() + disc.getYSpeed());

                    playerScore.setText("Score: " + score.getScore());

                    discLabel.setBounds(disc.getX(), disc.getY(), 32, 32);
                    gamePane.repaint();
                }
            });
            timer.start();

            Timer paddleTimer = new Timer(1, new ActionListener()
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


        mainFrame.addWindowListener(new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                // Do Nothing
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                score.writeHighScore();
            }

            @Override
            public void windowClosed(WindowEvent e)
            {
                // Do Nothing
            }

            @Override
            public void windowIconified(WindowEvent e)
            {
                // Do Nothing
            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {
                // Do Nothing
            }

            @Override
            public void windowActivated(WindowEvent e)
            {
                // Do Nothing
            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {
                // Do Nothing
            }
        });

        mainFrame.add(gamePane);
        mainFrame.setVisible(true);
    }

    public static boolean collisionHandler(Disc disc, Paddle paddle, JLabel glow, Timer glowTimer, Score score)
    {
        boolean lost = false;
        if(disc.getX() < 0)
        {
            disc.setXSpeed(disc.getXSpeed() * -1);
            glow.setBounds(disc.getX() - 12, disc.getY(), 32, 32);
            glow.setVisible(true);
            glowTimer.restart();
            score.incrementScore();
        }
        if(disc.getX() > (405 - 40))
        {
            disc.setXSpeed(disc.getXSpeed() * -1);
            glow.setBounds(disc.getX() + 7, disc.getY(), 32, 32);
            glow.setVisible(true);
            glowTimer.restart();
            score.incrementScore();
        }

        if(disc.getY() < 0)
        {
            disc.setYSpeed(disc.getYSpeed() * -1);
            glow.setBounds(disc.getX(), disc.getY() - 10, 32, 32);
            glow.setVisible(true);
            glowTimer.restart();
            score.incrementScore();
        }
        if(disc.getY() > (720 - 30))
        {
            lost = true;
            disc.setXSpeed(0);
            disc.setYSpeed(0);

        }

        return lost;
    }

}