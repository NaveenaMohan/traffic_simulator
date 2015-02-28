package managers.globalconfig;

/**
 * Created by Fabians on 13/02/2015.
 */
public class TickTime {
    private int ratio;
    private Long currentTick;

    public void incrementTick(){
        currentTick+=1;
    }
    public TickTime(int ratio) {
        this.ratio = ratio;
        currentTick=0L;
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
