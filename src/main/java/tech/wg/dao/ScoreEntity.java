package tech.wg.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ScoreEntity  {
    private final String login;
    private int win;
    private int loss;
    private double winRate;

    private long score;


    public ScoreEntity(String login, int win, int loss, double winRate, long score) {
        if(login == null) {
            throw new NullPointerException("Нельзя задать нулевой логин");
        }
        this.login = login;
        this.win = win;
        this.loss = loss;
        this.winRate = winRate;
        this.score = score;
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

