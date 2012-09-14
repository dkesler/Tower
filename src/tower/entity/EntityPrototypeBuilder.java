package tower.entity;

public interface EntityPrototypeBuilder<E> {
    void addProperty(String property, String value);
    boolean isValid();
    String getName();
    E toEntityPrototype();
}
