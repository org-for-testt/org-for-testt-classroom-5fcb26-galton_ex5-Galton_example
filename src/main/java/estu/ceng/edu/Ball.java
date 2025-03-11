package estu.ceng.edu;

import java.util.Random;

public class Ball extends Thread {
    @Override
    public void run() {
        int binL = GaltonBoard.getInstance().getBinLength();
        int start = binL -1;

        for(int j=0;j<binL-1;j++){
            int r = new Random().nextInt(2);
            if(r==0) start -= 1;
        }
        GaltonBoard.getInstance().updateBin(start);
    }
}
