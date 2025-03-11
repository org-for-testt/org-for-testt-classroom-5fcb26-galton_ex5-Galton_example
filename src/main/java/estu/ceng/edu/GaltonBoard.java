package estu.ceng.edu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GaltonBoard {
    private volatile static GaltonBoard uniqueInstance;
    private int[] bins;

    private GaltonBoard(int numberOfBins) {
        this.bins = new int[numberOfBins];
        for(int i = 0; i<numberOfBins; i++)
            bins[i]=0;
    }

    public static GaltonBoard create(int numberOfBins){
        if(uniqueInstance == null){
            synchronized (GaltonBoard.class){
                if(uniqueInstance == null){
                    uniqueInstance = new GaltonBoard(numberOfBins);
                }
            }
        }
        return uniqueInstance;
    }

    public static GaltonBoard getInstance(){
        if(uniqueInstance == null){
            synchronized (GaltonBoard.class){
                if(uniqueInstance == null){
                    uniqueInstance = new GaltonBoard(10);
                }
            }
        }
        return uniqueInstance;
    }

    public int getBinLength(){
        return bins.length;
    }

    public  void updateBin(int index){
        this.bins[index] +=1;
    }

    public int getBinValue(int index) {
        return bins[index];
    }

}
