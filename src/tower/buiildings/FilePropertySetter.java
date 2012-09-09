package tower.buiildings;

import org.apache.commons.io.FileUtils;

import java.io.File;

public abstract class FilePropertySetter<T> implements PropertySetter<T> {
    protected File getFileValue(String value) {
        return FileUtils.toFile(ClassLoader.class.getResource(value));
    }
}
