package io.github.simplex.serialization.object.content;

/**
 * An {@code ObjectHolder} is a class that stores a specific number of
 * objects that can be registered or unregistered to a registry at will.
 * All classes that implements {@code ObjectHolder} store data in some
 * form of a collection or map.
 */
public interface ObjectHolder {
    /**
     * Adds all objects from the collection to the registry.
     */
    void addAll();

    /**
     * Removes all objects from the registry <b>but not from the collection itself</b>.
     */
    void removeAll();
}
