package dao;

import java.util.Objects;

public class ScoreEntity {
    private final String login;
    private int win;
    private int loss;
    private double winRate;


    public ScoreEntity(String login, int win, int loss, double winRate) {
        if(login == null) {
            throw new NullPointerException("Нельзя задать нулевой логин");
        }
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

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreEntity that = (ScoreEntity) o;
        return login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}

