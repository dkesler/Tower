package tower.entity.items;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemFactory {
    Map<String, ItemPrototype> prototypes = new HashMap<>();

    public static final FilenameFilter DAT_FILE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".dat");
        }
    };

    public ItemFactory() {
        File buildingFolder = FileUtils.toFile(ClassLoader.class.getResource("/details/items"));

        for(File buildingFile : buildingFolder.listFiles(DAT_FILE_FILTER)) {
            addPrototypeFromFile(buildingFile);
        }
    }

    public Item createByName(String name) {
        ItemPrototype prototype = getPrototype(name);

        return new Item(
                prototype
        );
    }

    public ItemPrototype getPrototype(String name) {
        if (!prototypes.containsKey(name)) {
            throw new RuntimeException("No item prototype for [" + name + "]");
        }

        return prototypes.get(name);
    }

    public Collection<String> getItemNames() {
        return prototypes.keySet();
    }

    private void addPrototypeFromFile(File itemFile) {
        List<String> lines = readFile(itemFile);
        ItemPrototypeBuilder itemPrototypeBuilder = new ItemPrototypeBuilder();
        processLines(lines, itemPrototypeBuilder);
        validateAndAddPrototype(itemFile.getName(), itemPrototypeBuilder);
    }

    private void validateAndAddPrototype(String fileName, ItemPrototypeBuilder itemPrototypeBuilder) {
        if (!itemPrototypeBuilder.isValid()) {
            throw new RuntimeException("Item [" + fileName + "] is incomplete");
        }

        prototypes.put(
                itemPrototypeBuilder.getName(),
                itemPrototypeBuilder.toItem()
        );
    }

    private void processLines(List<String> lines, ItemPrototypeBuilder itemPrototypeBuilder) {
        for (String line : lines) {
            validateLine(line);

            String[] splitLine = line.split("=");

            itemPrototypeBuilder.addProperty(splitLine[0], splitLine[1]);
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
