package pl.tobynartowski.mapper;

public class MappableFieldNotFound extends IllegalStateException {

    public MappableFieldNotFound(String fieldName) {
        super(String.format("Field %s cannot be found in any mappable class", fieldName));
    }
}
