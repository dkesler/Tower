package tower.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import tower.entity.buiildings.BuildingPrototypeBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    private JsonStreamParser createParser(File file) {
        try {
            return new JsonStreamParser(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find file [" + file.getName() + "]", e);
        }
    }

    private void addPrototypeFromFile(File entityFile) {
        JsonStreamParser parser = createParser(entityFile);

        if (!parser.hasNext()) {
            throw new RuntimeException("Tried to load entity file [" + entityFile.getName() + "] with no contents");
        }

        EntityPrototypeBuilder<E> prototypeBuilder = entityPrototypeBuilderFactory.createEntityPrototypeBuilder();
        JsonElement next = parser.next();
        processEntityJson(next.getAsJsonObject(), prototypeBuilder);
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

    private void processEntityJson(JsonObject entity, EntityPrototypeBuilder<E> entityPrototypeBuilder) {
        for (Map.Entry<String, JsonElement> entry : entity.entrySet()) {
            entityPrototypeBuilder.addProperty(entry.getKey(), entry.getValue());
        }
    }
}
