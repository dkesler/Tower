package tower.entity.buiildings;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import tower.entity.EntityPrototypeLoader;
import tower.entity.items.ItemPrototype;
import tower.entity.items.ItemPrototypeBuilderFactory;
import tower.grid.GridCoord;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingFactory {
    Map<String, BuildingPrototype> prototypes;

    public BuildingFactory() {
        prototypes = new EntityPrototypeLoader<BuildingPrototype>(
                "/details/buildings",
                new BuildingPrototypeBuilderFactory()
        ).getPrototypes();
    }

    public Building createByName(String name, GridCoord gridCoord) {
        BuildingPrototype prototype = getPrototype(name);

        return new Building(
                prototype,
                gridCoord
        );
    }

    public BuildingPrototype getPrototype(String name) {
        if (!prototypes.containsKey(name)) {
            throw new RuntimeException("No building prototype for [" + name + "]");
        }

        return prototypes.get(name);
    }

    public Collection<String> getBuildingNames() {
        return prototypes.keySet();
    }
}