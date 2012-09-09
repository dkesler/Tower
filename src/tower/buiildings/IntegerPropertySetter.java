package tower.buiildings;

import org.apache.commons.lang3.StringUtils;

public abstract class IntegerPropertySetter<T> implements PropertySetter<T>{
    protected int getIntValue(String value) {
        if (!StringUtils.isNumeric(value)) {
            throw new RuntimeException("Exception occurred setting property.  Expected numeric value.  Was [" + value + "]");
        }

        return Integer.parseInt(value);
    }
}
