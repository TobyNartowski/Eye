package pl.tobynartowski.component;

@FunctionalInterface
public interface Mapper<T> {

    T map(float value);
}
