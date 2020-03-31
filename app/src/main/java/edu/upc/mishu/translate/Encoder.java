package edu.upc.mishu.translate;

public class Encoder implements Transformable {
    @Override
    public String encode(String string) {
        return string;
    }

    @Override
    public String decode(String string) {
        return string;
    }
}
