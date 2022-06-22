package pl.training.processor.adv.search.ports;

public record Repository(String name) {

    public boolean hasLength(int length) {
        return name.length() >= length;
    }

}
