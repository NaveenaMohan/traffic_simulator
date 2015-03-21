package ui.fileops;

import managers.runit.IRUnitManager;
import ui.Coordinates;

import java.io.Serializable;
import java.util.*;

/**
 * Created by naveena on 15/03/15.
 */
public class UIDataStructures implements Serializable {

    private Set<IRUnitManager> singleLaneRUnits = new LinkedHashSet<IRUnitManager>();
    private Set<IRUnitManager> doubleLaneRUnits = new LinkedHashSet<IRUnitManager>();
    private List<Coordinates> vehicleFactoryCoordinates = new ArrayList<Coordinates>();
    private Map<String, Coordinates> trafficLightCoordinates = new HashMap<String, Coordinates>();
    private Map<String, Coordinates> zebraCrossingCoordinates = new HashMap<String, Coordinates>();
    private List<Coordinates> blockageCoordinates = new ArrayList<Coordinates>();
    private List<Coordinates> stopCoordinates = new ArrayList<Coordinates>();
    private Map<Coordinates, String> leftCoordinates = new HashMap<Coordinates, String>();
    private Map<Coordinates, String> straightCoordinates = new HashMap<Coordinates, String>();
    private Map<Coordinates, String> rightCoordinates = new HashMap<Coordinates, String>();
    private List<Coordinates> speed20Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed30Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed50Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed60Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed70Coordinates = new ArrayList<Coordinates>();
    private List<Coordinates> speed90Coordinates = new ArrayList<Coordinates>();
    private Map<Coordinates, String> welcomeCoordinates = new HashMap<Coordinates, String>();

    public Set<IRUnitManager> getSingleLaneRUnits() {
        return singleLaneRUnits;
    }

    public void setSingleLaneRUnits(Set<IRUnitManager> singleLaneRUnits) {
        this.singleLaneRUnits = singleLaneRUnits;
    }

    public Set<IRUnitManager> getDoubleLaneRUnits() {
        return doubleLaneRUnits;
    }

    public void setDoubleLaneRUnits(Set<IRUnitManager> doubleLaneRUnits) {
        this.doubleLaneRUnits = doubleLaneRUnits;
    }

    public List<Coordinates> getVehicleFactoryCoordinates() {
        return vehicleFactoryCoordinates;
    }

    public void setVehicleFactoryCoordinates(List<Coordinates> vehicleFactoryCoordinates) {
        this.vehicleFactoryCoordinates = vehicleFactoryCoordinates;
    }

    public Map<String, Coordinates> getTrafficLightCoordinates() {
        return trafficLightCoordinates;
    }

    public void setTrafficLightCoordinates(Map<String, Coordinates> trafficLightCoordinates) {
        this.trafficLightCoordinates = trafficLightCoordinates;
    }

    public Map<String, Coordinates> getZebraCrossingCoordinates() {
        return zebraCrossingCoordinates;
    }

    public void setZebraCrossingCoordinates(Map<String, Coordinates> zebraCrossingCoordinates) {
        this.zebraCrossingCoordinates = zebraCrossingCoordinates;
    }

    public List<Coordinates> getBlockageCoordinates() {
        return blockageCoordinates;
    }

    public void setBlockageCoordinates(List<Coordinates> blockageCoordinates) {
        this.blockageCoordinates = blockageCoordinates;
    }

    public List<Coordinates> getStopCoordinates() {
        return stopCoordinates;
    }

    public void setStopCoordinates(List<Coordinates> stopCoordinates) {
        this.stopCoordinates = stopCoordinates;
    }

    public Map<Coordinates, String> getLeftCoordinates() {
        return leftCoordinates;
    }

    public void setLeftCoordinates(Map<Coordinates, String> leftCoordinates) {
        this.leftCoordinates = leftCoordinates;
    }

    public Map<Coordinates, String> getStraightCoordinates() {
        return straightCoordinates;
    }

    public void setStraightCoordinates(Map<Coordinates, String> straightCoordinates) {
        this.straightCoordinates = straightCoordinates;
    }

    public Map<Coordinates, String> getRightCoordinates() {
        return rightCoordinates;
    }

    public void setRightCoordinates(Map<Coordinates, String> rightCoordinates) {
        this.rightCoordinates = rightCoordinates;
    }

    public List<Coordinates> getSpeed20Coordinates() {
        return speed20Coordinates;
    }

    public void setSpeed20Coordinates(List<Coordinates> speed20Coordinates) {
        this.speed20Coordinates = speed20Coordinates;
    }

    public List<Coordinates> getSpeed30Coordinates() {
        return speed30Coordinates;
    }

    public void setSpeed30Coordinates(List<Coordinates> speed30Coordinates) {
        this.speed30Coordinates = speed30Coordinates;
    }

    public List<Coordinates> getSpeed50Coordinates() {
        return speed50Coordinates;
    }

    public void setSpeed50Coordinates(List<Coordinates> speed50Coordinates) {
        this.speed50Coordinates = speed50Coordinates;
    }

    public List<Coordinates> getSpeed60Coordinates() {
        return speed60Coordinates;
    }

    public void setSpeed60Coordinates(List<Coordinates> speed60Coordinates) {
        this.speed60Coordinates = speed60Coordinates;
    }

    public List<Coordinates> getSpeed70Coordinates() {
        return speed70Coordinates;
    }

    public void setSpeed70Coordinates(List<Coordinates> speed70Coordinates) {
        this.speed70Coordinates = speed70Coordinates;
    }

    public List<Coordinates> getSpeed90Coordinates() {
        return speed90Coordinates;
    }

    public void setSpeed90Coordinates(List<Coordinates> speed90Coordinates) {
        this.speed90Coordinates = speed90Coordinates;
    }

    public Map<Coordinates, String> getWelcomeCoordinates() {
        return welcomeCoordinates;
    }

    public void setWelcomeCoordinates(Map<Coordinates, String> welcomeCoordinates) {
        this.welcomeCoordinates = welcomeCoordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UIDataStructures that = (UIDataStructures) o;

        if (blockageCoordinates != null ? !blockageCoordinates.equals(that.blockageCoordinates) : that.blockageCoordinates != null)
            return false;
        if (doubleLaneRUnits != null ? !doubleLaneRUnits.equals(that.doubleLaneRUnits) : that.doubleLaneRUnits != null)
            return false;
        if (leftCoordinates != null ? !leftCoordinates.equals(that.leftCoordinates) : that.leftCoordinates != null)
            return false;
        if (rightCoordinates != null ? !rightCoordinates.equals(that.rightCoordinates) : that.rightCoordinates != null)
            return false;
        if (singleLaneRUnits != null ? !singleLaneRUnits.equals(that.singleLaneRUnits) : that.singleLaneRUnits != null)
            return false;
        if (speed20Coordinates != null ? !speed20Coordinates.equals(that.speed20Coordinates) : that.speed20Coordinates != null)
            return false;
        if (speed30Coordinates != null ? !speed30Coordinates.equals(that.speed30Coordinates) : that.speed30Coordinates != null)
            return false;
        if (speed50Coordinates != null ? !speed50Coordinates.equals(that.speed50Coordinates) : that.speed50Coordinates != null)
            return false;
        if (speed60Coordinates != null ? !speed60Coordinates.equals(that.speed60Coordinates) : that.speed60Coordinates != null)
            return false;
        if (speed70Coordinates != null ? !speed70Coordinates.equals(that.speed70Coordinates) : that.speed70Coordinates != null)
            return false;
        if (speed90Coordinates != null ? !speed90Coordinates.equals(that.speed90Coordinates) : that.speed90Coordinates != null)
            return false;
        if (stopCoordinates != null ? !stopCoordinates.equals(that.stopCoordinates) : that.stopCoordinates != null)
            return false;
        if (straightCoordinates != null ? !straightCoordinates.equals(that.straightCoordinates) : that.straightCoordinates != null)
            return false;
        if (trafficLightCoordinates != null ? !trafficLightCoordinates.equals(that.trafficLightCoordinates) : that.trafficLightCoordinates != null)
            return false;
        if (vehicleFactoryCoordinates != null ? !vehicleFactoryCoordinates.equals(that.vehicleFactoryCoordinates) : that.vehicleFactoryCoordinates != null)
            return false;
        if (welcomeCoordinates != null ? !welcomeCoordinates.equals(that.welcomeCoordinates) : that.welcomeCoordinates != null)
            return false;
        if (zebraCrossingCoordinates != null ? !zebraCrossingCoordinates.equals(that.zebraCrossingCoordinates) : that.zebraCrossingCoordinates != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = singleLaneRUnits != null ? singleLaneRUnits.hashCode() : 0;
        result = 31 * result + (doubleLaneRUnits != null ? doubleLaneRUnits.hashCode() : 0);
        result = 31 * result + (vehicleFactoryCoordinates != null ? vehicleFactoryCoordinates.hashCode() : 0);
        result = 31 * result + (trafficLightCoordinates != null ? trafficLightCoordinates.hashCode() : 0);
        result = 31 * result + (zebraCrossingCoordinates != null ? zebraCrossingCoordinates.hashCode() : 0);
        result = 31 * result + (blockageCoordinates != null ? blockageCoordinates.hashCode() : 0);
        result = 31 * result + (stopCoordinates != null ? stopCoordinates.hashCode() : 0);
        result = 31 * result + (leftCoordinates != null ? leftCoordinates.hashCode() : 0);
        result = 31 * result + (straightCoordinates != null ? straightCoordinates.hashCode() : 0);
        result = 31 * result + (rightCoordinates != null ? rightCoordinates.hashCode() : 0);
        result = 31 * result + (speed20Coordinates != null ? speed20Coordinates.hashCode() : 0);
        result = 31 * result + (speed30Coordinates != null ? speed30Coordinates.hashCode() : 0);
        result = 31 * result + (speed50Coordinates != null ? speed50Coordinates.hashCode() : 0);
        result = 31 * result + (speed60Coordinates != null ? speed60Coordinates.hashCode() : 0);
        result = 31 * result + (speed70Coordinates != null ? speed70Coordinates.hashCode() : 0);
        result = 31 * result + (speed90Coordinates != null ? speed90Coordinates.hashCode() : 0);
        result = 31 * result + (welcomeCoordinates != null ? welcomeCoordinates.hashCode() : 0);
        return result;
    }

}
