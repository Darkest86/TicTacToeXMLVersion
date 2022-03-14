package com.company;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

public class TicTacToeGameXML {

    public static Scanner scan = new Scanner(System.in);

    public static void startGame() {
        Player player1 = new Player();
        Player player2 = new Player();
        GameMap Game = new GameMap();

        System.out.println("Enter player 1 name:");
        player1.setName(scan.nextLine());
        player1.setId(1);
        player1.setMark("X");
        System.out.println("Enter player 2 name:");
        player2.setName(scan.nextLine());
        player2.setId(2);
        player2.setMark("O");
        System.out.println("Game started");

        LogFile log = new LogFile(player1, player2);

        boolean gameContinue = true;

        while (Game.turnsLeft > 0 && gameContinue) {
            int pos;
            do {
                System.out.println("Enter number from 1 to 9 (left up corner is 1, right up is 3, right down is 9)");
                Game.out();
                pos = Integer.parseInt(scan.nextLine()) - 1;
            }
            while (Game.notPossibleTurn(pos));
            log.addStep(pos, 9 - Game.turnsLeft);
            gameContinue = Game.checkNoWin(player1, player2, log);
        }
        if (Game.turnsLeft == 0 && gameContinue) {
            log.endLog();
            Game.gh.GameDraw(player1, player2);
            System.out.println("Draw!");

        }
    }

    public static FilenameFilter xmlFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            if (name.lastIndexOf('.') > 0) {
                int lastIndex = name.lastIndexOf('.');
                String str = name.substring(lastIndex);
                if (str.equals(".xml")) {
                    return true;
                }
            }
            return false;
        }
    };

    public static String loadList(){
        String loadPath = System.getProperty("user.dir") + "\\Logs\\";
        File dir = new File(loadPath);
        File[] listFile = dir.listFiles(xmlFilter);
        if (listFile != null) {
            System.out.println("Select game by number:");
            int index = 1;
            for (File f : listFile) {
                System.out.print(index + ". ");
                System.out.println(f.getName());
                index++;
            }
            int i = 0;
            while (true) {
                i = Integer.parseInt(scan.nextLine());
                if (!(i > listFile.length || i <= 0))
                    break;
                else
                    System.out.println("Invalid input. Try again.");
            }
            return listFile[i - 1].toString();
        }
        return null;
    }

    public static String loadFirst() {
        String loadPath = System.getProperty("user.dir") + "\\Load\\";
        File dir = new File(loadPath);
        if (!dir.exists())
        {
            dir.mkdir();
            System.out.println("No games in directory");
            return null;
        }
        File[] fileList = dir.listFiles(xmlFilter);
        if (fileList != null)
            return fileList[0].toString();
        else
            System.out.println("No games in directory");
        return null;
    }

    public static void loadGame(){
        boolean notExit = true;
        String gamePath = null;
        while (true) {
            System.out.println("View list of saved games? (L)\nLoad first game from \"Load\" directory? (F)\nBack? (B)");
            String s = scan.nextLine();
            if (s.equals("L")) {
                gamePath = loadList();
                break;
            }
            if (s.equals("F")) {
                gamePath = loadFirst();
                break;
            }
            if (s.equals("B")) {
                notExit = false;
                break;
            }
            System.out.println("Invalid input. Try again.");
            System.out.println();
        }
        if (gamePath != null) {
            if (notExit) {
                SAXGameParser parser;
                try {
                    parser = new SAXGameParser(gamePath);
                } catch (ParserConfigurationException | SAXException | IOException pce) {
                    System.out.println(pce.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Start new game? (G)\nLoad game? (L)\nExit? (E)");
            String s = scan.nextLine();
            if (s.equals("G"))
                startGame();
            if (s.equals("L"))
                loadGame();
            if (s.equals("E"))
                break;
            if (!s.equals("G") && !s.equals("L"))
                System.out.println("Invalid input. Try again.");
            System.out.println();
        }
    }
}
