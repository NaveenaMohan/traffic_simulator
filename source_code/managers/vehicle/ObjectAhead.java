package managers.vehicle;

/**
 * Created by Fabians on 17/02/2015.
 */
public class ObjectAhead {
    //this class is used by the vehicle to mark and act upon an object up ahead. This could be another car or a stop sign
    private double distance;//distance from you in metres
    private double speed;//speed of the object
    private double currentTime;//the time passed to the object

    public ObjectAhead(double distance, double currentTime){
        this.distance=distance;
        this.currentTime=currentTime;
        speed=0;
    }

    public void recalculateSpeed(double mySpeed, double distance, double currentTime)
    {
        //checks object speed. It does that by comparing previous readings with these ones and calculating the difference

        //get distance change
        double distanceChange = Math.abs(this.distance - distance);

        //get time change
        double timeChange = Math.abs(this.currentTime - currentTime);

        //update currentTime
        this.currentTime=currentTime;

        if(timeChange!=0)//for div/0
        {
            //new speed is equal to the speed of the vehicle (mySpeed) plus the change in speed per second
            this.speed = mySpeed + distanceChange/timeChange;
        }

        System.out.println("recalcuated speed: " + speed + " distanceChange: " + distanceChange + " timeChange: " + timeChange);
    }

    public double getDistance() {
        return distance;
    }

    public double getSpeed() {
        return speed;
    }
}
