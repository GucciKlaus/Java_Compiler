package Interpreter.Types;

public final class BoolValue implements Value{
    public final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BoolValue{" +
                "value=" + value +
                '}';
    }
}
