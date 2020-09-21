package io.github.simplex.serialization.object;

public enum Entries {
    COMPOSTABLE(Compostable.class),
    FLAMMABLE(Flammable.class),
    FUEL(Fuel.class),
    PAVEABLE(Paveable.class);

    private final Class<?> clazz;

    Entries(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }
}
