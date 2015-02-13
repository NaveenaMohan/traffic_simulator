package managers.vehicle;

import managers.globalconfig.ClimaticCondition;
import managers.space.Space;

public interface IVehicleManager {

    void move(Space mySpace, Long time, ClimaticCondition climaticCondition);

    boolean isVisible(int minX, int maxX, int minY, int maxY);

    Vehicle getVehicle();

}
