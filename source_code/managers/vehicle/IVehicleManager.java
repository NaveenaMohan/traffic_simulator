package managers.vehicle;

import managers.globalconfig.ClimaticCondition;
import managers.space.ISpaceManager;
import managers.space.Space;

public interface IVehicleManager {

    void move(ISpaceManager spaceManager, Long time, ClimaticCondition climaticCondition);

    boolean isVisible(int minX, int maxX, int minY, int maxY);

    Vehicle getVehicle();

}
