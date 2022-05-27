package com.example.aircraftwar_base.rank;

public interface PlayerDao {
    public void addRecord(int score, String userName, String date);
    public String[][] dataToStrArr();
    public void readData();
    public void putData();
    public void deleteRecord(int[] index);
}
