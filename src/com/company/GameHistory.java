package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameHistory {
    File f;

    public GameHistory()
    {
        f = new File("GameHistory.txt");
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (
                    IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public void GameEndWin(Player p1, Player p2) {
        try {
            FileWriter wr = new FileWriter(f, true);
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            wr.write(formatForDateNow.format(new Date()) + " " + p1.getName() + " won " + p2.getName() + '\n');
            wr.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void GameDraw(Player p1, Player p2)
    {
        try
        {
            FileWriter wr = new FileWriter(f, true);
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            wr.write(formatForDateNow.format(new Date()) + " Draw between " + p1.getName() + " and " + p2.getName() + '\n');
            wr.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
