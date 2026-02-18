package Interpreter.Types;

public final class StringValue implements Value{
    public final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StringValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
