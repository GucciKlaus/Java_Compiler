package Interpreter.Types;

public final class DoubleValue implements Value{
    public final double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DoubleValue{" +
                "value=" + value +
                '}';
    }
}
