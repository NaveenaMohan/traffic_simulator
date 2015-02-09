package managers.vehicle;

import managers.space.Space;

public interface IVehicleManager {

    void move(Space mySpace);

    boolean isVisible(int minX, int maxX, int minY, int maxY);

}
