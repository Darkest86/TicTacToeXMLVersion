package com.company;

public class GameMap {
    int[][] Map = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int turnsLeft = 9;
    GameHistory gh = new GameHistory();

    public void setMark(int x) {
        this.Map[x / 3][x % 3] = -1 + 2 * (this.turnsLeft % 2);
        this.turnsLeft--;
    }

    public void out() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("| ");
                switch (this.Map[i][j]) {
                    case -1:
                        System.out.print("O ");
                        break;
                    case 1:
                        System.out.print("X ");
                        break;
                    case 0:
                        System.out.print("- ");
                        break;
                }
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public boolean checkNoWin(Player p1, Player p2, LogFile log)
    {
        int h1 = 0, h2 = 0, h3 = 0, v1 = 0, v2 = 0, v3 = 0, d1 = 0, d2 = 0;
        for (int i = 0; i < 3; i++) {
            h1 += this.Map[0][i];
            h2 += this.Map[1][i];
            h3 += this.Map[2][i];
            v1 += this.Map[i][0];
            v2 += this.Map[i][1];
            v3 += this.Map[i][2];
            d1 += this.Map[i][i];
            d2 += this.Map[2 - i][i];
        }
        if (h1 == 3 || h2 == 3 || h3 == 3 || v1 == 3 || v2 == 3 || v3 == 3 || d1 == 3 || d2 == 3) {
            gh.GameEndWin(p1, p2);
            log.endLog(p1);
            System.out.println(p1.getName() + " won!");
            return false;
        }
        if (h1 == -3 || h2 == -3 || h3 == -3 || v1 == -3 || v2 == -3 || v3 == -3 || d1 == -3 || d2 == -3) {
            gh.GameEndWin(p2, p1);
            log.endLog(p2);
            System.out.println(p2.getName() + " won!");
            return false;
        }
        return true;
    }

    public boolean notPossibleTurn(int x) {
        if (x < 9) {
            if (this.Map[x / 3][x % 3] == 0) {
                this.setMark(x);
                return false;
            } else
                System.out.println("Invalid input. Try again.");
        } else
            System.out.println("Invalid input. Try again.");
        return true;
    }
}
