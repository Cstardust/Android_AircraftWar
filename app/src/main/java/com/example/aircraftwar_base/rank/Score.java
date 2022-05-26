package com.example.aircraftwar_base.rank;

public class Score implements Comparable<Score> {

    private int score;
    private String userName;
    private String date;

    public Score(int score, String userName, String date) {
        this.score = score;
        this.userName = userName;
        this.date = date;
    }

    @Override
    public int compareTo(Score score2) {
        if (getScore() > score2.getScore()) {
            return -1;
        } else if (getScore() < score2.getScore()) {
            return 1;
        } else {
            return 0;
        }

    }

    public String[] toStringArr(int i) {
        return new String[] {i + "", userName, score + "", date};
    }
    @Override
    public String toString() {
        return score + " " +  userName + " " + date;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getScore() {
        return score;
    }
    public String getDate() {
        return date;
    }


}
