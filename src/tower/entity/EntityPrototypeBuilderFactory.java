package tower.entity;

public interface EntityPrototypeBuilderFactory<E extends EntityPrototypeBuilder> {
    E createEntityPrototypeBuilder();
}
