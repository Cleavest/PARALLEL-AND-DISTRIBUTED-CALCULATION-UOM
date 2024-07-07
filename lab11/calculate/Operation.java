/**
 * @author Cleavest on 12/6/2024
 */
public enum Operation {
    ADDITION() {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    SUBTRACTION() {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    MULTIPLICATION() {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVISION() {
        public double apply(double x, double y) {
            if (y == 0) {
                throw new ArithmeticException("Cannot divide by zero");
            }
            return x / y;
        }
    };

    public abstract double apply(double x, double y);
}
