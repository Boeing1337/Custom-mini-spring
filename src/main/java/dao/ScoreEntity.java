package dao;

public class ScoreEntity {
    private final String login;
    private final int win;

    private final int loss;

    private final double winRate;


    public ScoreEntity(String login, int win, int loss, double winRate) {
        this.login = login;
        this.win = win;
        this.loss = loss;
        this.winRate = winRate;
    }

    public String getLogin() {
        return login;
    }

    public int getWin() {
        return win;
    }

    public int getLoss() {return loss;}

    public double getWinRate() {return winRate;}
}

