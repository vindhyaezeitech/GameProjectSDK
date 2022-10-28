package com.today.gamesdk.shabdamsdk;

import com.today.gamesdk.shabdamsdk.model.getwordresp.Datum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDataManager {
    private static GameDataManager gameDataManager = null;
    private List<Datum> dataList = new ArrayList<>();

    public static GameDataManager getInstance(){
        if(gameDataManager == null){
            gameDataManager = new GameDataManager();
        }
        return gameDataManager;
    }

    public void addData(List<Datum> data){
        if(dataList != null){
            dataList.clear();
        }
        dataList.addAll(data);
    }

    public List<Datum> getDataList() {
        return dataList;
    }

    public void removeData(){
        if(dataList != null && dataList.size()>0){
            dataList.remove(0);
        }
    }

    public void removeAll(){
        if(dataList != null && dataList.size()>0){
            dataList.clear();
        }
    }

    public void shuffleList(){
        if(dataList != null && dataList.size()>0){
            Collections.shuffle(dataList);
        }
    }



}
