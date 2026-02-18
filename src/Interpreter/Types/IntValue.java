package Interpreter.Types;

public final class IntValue implements Value {
    public final int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntValue{" +
                "value=" + value +
                '}';
    }
}
