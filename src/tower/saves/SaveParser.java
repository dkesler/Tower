package tower.saves;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import org.apache.commons.io.FileUtils;
import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.entity.items.ItemFactory;
import tower.grid.GridCoord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SaveParser {
    private final String saveFileName;

    public SaveParser(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public Collection<Building> getBuildings() {

        JsonStreamParser parser = createParser();

        List<Building> buildings = new LinkedList<>();

        while (parser.hasNext()) {
            JsonObject buildingJson = getNextBuilding(parser);

            String buildingType = buildingJson.get("type").getAsString();
            JsonObject location = buildingJson.getAsJsonObject("location");
            Building building = new Building(
                    BuildingFactory.getPrototype(buildingType),
                    GridCoord.fromUnits(
                            location.get("x").getAsInt(),
                            location.get("y").getAsInt()
                    )
            );

            JsonArray items = buildingJson.getAsJsonArray("items");

            for (JsonElement itemElement : items) {
                JsonObject itemObject = itemElement.getAsJsonObject();
                for (int quantity = 0; quantity < itemObject.get("quantity").getAsInt(); ++quantity){
                    building.addItem(
                            ItemFactory.createByName(itemObject.get("type").getAsString())
                    );
                }

            }

            buildings.add(building);
        }

        return buildings;
    }

    private JsonObject getNextBuilding(JsonStreamParser parser) {
        JsonElement nextElement = parser.next();
        if (!nextElement.isJsonObject()) {
            throw new RuntimeException("Json element [" + nextElement.getAsString() + "] in save file is not a Json object");
        }
        return nextElement.getAsJsonObject();
    }

    private JsonStreamParser createParser() {
        try {
            File saveFile = FileUtils.toFile(ClassLoader.class.getResource(saveFileName));
            return new JsonStreamParser(new FileReader(saveFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find save file [" + saveFileName + "]", e);
        }
    }
}
