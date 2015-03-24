package managers.globalconfig;

import java.io.Serializable;

/**
 * Created by Fabians on 13/02/2015.
 */
public class TickTime implements Serializable {
    private int ratio;
    private long currentTick;
    private double currentSecond;

    public TickTime(int ratio) {
        this.ratio = ratio;
        currentTick = 0L;
        currentSecond = 0;
    }

    public void incrementTick() {
        currentTick += 1;
        currentSecond += (double) 1 / ratio;
    }

    public double getCurrentSecond() {
        return currentSecond;
    }

    public Long getCurrentTick() {
        return currentTick;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
