package com.example.aircraftwar_base.rank;

import android.util.Log;

import com.example.aircraftwar_base.activity.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlayerDaoIm implements PlayerDao {

    private File file = null;
    private List<Player> playerList = new LinkedList<>();
    public static final SimpleDateFormat sml = new SimpleDateFormat("MM-dd kk:mm");


    public static final String EASY = "easy.txt";
    public static final String COMMON = "common.txt";
    public static final String HARD = "hard.txt";

    public PlayerDaoIm(String fileName) {

        //获取路径(app数据会随app的删除而删除)
        file = new File(MainActivity.fileDir, fileName);
        Log.d("local store", file.getPath());
        if (!file.exists()) {
            Log.d("create file", "try ....");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("create file", "error");
            }
            Log.d("create file", "done");
        } else {
            Log.d("local store", "file exit!");
        }

    }

    @Override
    public void deleteRecord(int[] index) {
        playerList.clear();
        readData();
        Collections.sort(playerList);
        List<Player> tmp = new LinkedList<>();
        for (int i : index) {
            tmp.add(playerList.get(i));
            System.out.println("删除第" + (i+1) + "条记录！");
        }
        for (Player s : tmp) {
            playerList.remove(s);
        }
        putData();
        playerList.clear();
    }


    @Override
    public void addRecord(int score, String userName, String date) {
        playerList.clear();
        Player playerOb = new Player(score, userName, date);
        playerList.add(playerOb);
        readData();
        putData();
        playerList.clear();
    }

    @Override
    public String[][] dataToStrArr() {
        playerList.clear();
        readData();
        Collections.sort(playerList);
        String[][] ss = new String[playerList.size()][];
        for (int i = 0; i < playerList.size(); ++i) {
            ss[i] = playerList.get(i).toStringArr(i+1);
        }
        playerList.clear();
        return ss;
    }
    @Override
    public void readData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                String[] ss = s.split(" ");
                playerList.add(new Player(Integer.parseInt(ss[0]), ss[1], ss[2]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void putData() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(Player s : playerList) {
                bw.write(s.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}