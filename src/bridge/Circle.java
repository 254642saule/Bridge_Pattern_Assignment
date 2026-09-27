package bridge;

public final class Circle extends Shape {
    private final double radius;

    public Circle(double x, double y, double radius, Renderer renderer) {
        super(x, y, renderer);
        this.radius = requirePositive(radius, "Radius");
    }

    @Override
    public void draw() {
        getRenderer().renderCircle(x, y, radius);
    }
}
