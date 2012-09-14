package tower.entity;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import tower.entity.buiildings.BuildingPrototypeBuilder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityPrototypeLoader<E> {
    final private Map<String, E> prototypes = new HashMap<>();
    final private EntityPrototypeBuilderFactory<EntityPrototypeBuilder<E>> entityPrototypeBuilderFactory;


    public static final FilenameFilter DAT_FILE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".dat");
        }
    };

    public EntityPrototypeLoader(
            final String entityDirectory,
            final EntityPrototypeBuilderFactory<EntityPrototypeBuilder<E>> entityPrototypeBuilderFactory
    ) {
        this.entityPrototypeBuilderFactory = entityPrototypeBuilderFactory;

        File entityFolder = FileUtils.toFile(ClassLoader.class.getResource(entityDirectory));

        for(File entityFile : entityFolder.listFiles(DAT_FILE_FILTER)) {
            addPrototypeFromFile(entityFile);
        }
    }

    public Map<String, E> getPrototypes() {
        return prototypes;
    }

    private void addPrototypeFromFile(File entityFile) {
        List<String> lines = readFile(entityFile);
        EntityPrototypeBuilder<E> prototypeBuilder = entityPrototypeBuilderFactory.createEntityPrototypeBuilder();
        processLines(lines, prototypeBuilder);
        validateAndAddPrototype(entityFile.getName(), prototypeBuilder);
    }

    private void validateAndAddPrototype(String fileName, EntityPrototypeBuilder<E> prototypeBuilder) {
        if (!prototypeBuilder.isValid()) {
            throw new RuntimeException("Building [" + fileName + "] is incomplete");
        }

        prototypes.put(
                prototypeBuilder.getName(),
                prototypeBuilder.toEntityPrototype()
        );
    }

    private void processLines(List<String> lines, EntityPrototypeBuilder<E> entityPrototypeBuilder) {
        for (String line : lines) {
            validateLine(line);

            String[] splitLine = line.split("=");

            entityPrototypeBuilder.addProperty(splitLine[0], splitLine[1]);
        }
    }

    private void validateLine(String line) {
        if (StringUtils.isNotBlank(line) && !line.contains("=")) {
            throw new RuntimeException("Non-blank line [" + line + "] did not contain an equals sign");
        }
    }

    private List<String> readFile(File entityFile) {
        List<String> lines;
        try {
            lines = FileUtils.readLines(entityFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not load entity files", e);
        }
        return lines;
    }


}
