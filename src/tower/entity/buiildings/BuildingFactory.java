package tower.entity.buiildings;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import tower.grid.GridCoord;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingFactory {
    Map<String, BuildingPrototype> prototypes = new HashMap<>();

    public static final FilenameFilter DAT_FILE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".dat");
        }
    };

    public BuildingFactory() {
        File buildingFolder = FileUtils.toFile(ClassLoader.class.getResource("/details/buildings"));

        for(File buildingFile : buildingFolder.listFiles(DAT_FILE_FILTER)) {
            addPrototypeFromFile(buildingFile);
        }
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

    private void addPrototypeFromFile(File buildingFile) {
        List<String> lines = readFile(buildingFile);
        BuildingPrototypeBuilder buildingPrototypeBuilder = new BuildingPrototypeBuilder();
        processLines(lines, buildingPrototypeBuilder);
        validateAndAddPrototype(buildingFile.getName(), buildingPrototypeBuilder);
    }

    private void validateAndAddPrototype(String fileName, BuildingPrototypeBuilder buildingPrototypeBuilder) {
        if (!buildingPrototypeBuilder.isValid()) {
            throw new RuntimeException("Building [" + fileName + "] is incomplete");
        }

        prototypes.put(
                buildingPrototypeBuilder.getName(),
                buildingPrototypeBuilder.toBuilding()
        );
    }

    private void processLines(List<String> lines, BuildingPrototypeBuilder buildingPrototypeBuilder) {
        for (String line : lines) {
            validateLine(line);

            String[] splitLine = line.split("=");

            buildingPrototypeBuilder.addProperty(splitLine[0], splitLine[1]);
        }
    }

    private void validateLine(String line) {
        if (StringUtils.isNotBlank(line) && !line.contains("=")) {
            throw new RuntimeException("Non-blank line [" + line + "] did not contain an equals sign");
        }
    }

    private List<String> readFile(File buildingFile) {
        List<String> lines;
        try {
            lines = FileUtils.readLines(buildingFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not load building files", e);
        }
        return lines;
    }


}
