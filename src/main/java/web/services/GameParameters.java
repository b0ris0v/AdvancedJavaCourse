package web.services;

public class GameParameters {
    private Integer score;
    private Long startTime;

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(long startTime) {
        this.startTime = startTime;
    }

    public Integer getScore() {
        return score;
    }

    public Long getStartTime() {
        return startTime;
    }
}
