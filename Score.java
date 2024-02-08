import java.io.*;
import java.util.Random;

public class Score
{
    private int score;
    private int highScore;

    public Score()
    {
        score = 0;
        highScore = readHighScore();
    }

    // Getters
    public int getScore()
    {
        return score;
    }

    public int getHighScore()
    {
        return highScore;
    }

    // Setters
    public void incrementScore()
    {
        score++;
    }

    public void setHighScore(int highScore)
    {
        this.highScore = highScore;
    }

    public void reset()
    {
        score = 0;
    }


    // ReadHighScore
    private int readHighScore()
    {
        String line;
        try
        {
            FileInputStream fileStrm = new FileInputStream("high_score.txt");
            InputStreamReader isr = new InputStreamReader(fileStrm);
            BufferedReader bfr = new BufferedReader(isr);

            line = decrypt(bfr.readLine());
        }
        catch(Exception e)
        {
            line = "0";
        }

        return Integer.parseInt(line);
    }

    public void writeHighScore()
    {
        try
        {
            FileWriter fw = new FileWriter("high_score.txt");
            fw.write(encrypt(highScore));
            fw.close();
        }
        catch(IOException ignore) {}

    }

    private String decrypt(String line)
    {
        String scoreStr = "";
        int diffInt = (int)line.charAt(line.length() - 1) - 97;

        for(int letter = 0; letter < line.length() - 1; letter++)
        {
            scoreStr += (int)line.charAt(letter) - diffInt - 97;
        }
        return scoreStr;
    }

    private String encrypt(int finalScore)
    {
        String returnLine = "";
        String scoreStr = String.valueOf(finalScore);

        Random random = new Random();
        int randomInt = random.nextInt(1,  10);

        for(int i = 0; i < scoreStr.length(); i++)
        {
            returnLine += (char) (Integer.parseInt(String.valueOf(scoreStr.charAt(i))) + randomInt + 97);
        }
        returnLine += (char) (randomInt + 97);

        return returnLine;

    }
}
