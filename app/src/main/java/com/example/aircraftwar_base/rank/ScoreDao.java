package com.example.aircraftwar_base.rank;

public interface ScoreDao {
    public void addRecord(int score, String userName, String date);
    public String[][] dataToStrArr();
    public void readData();
    public void putData();
    public void deleteRecord(int[] index);
}
