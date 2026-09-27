package bridge;

public final class Square extends Shape {
    private final double side;

    public Square(double x, double y, double side, Renderer renderer) {
        super(x, y, renderer);
        this.side = requirePositive(side, "Side");
    }

    @Override
    public void draw() {
        Renderer renderer = getRenderer();
        renderer.renderLine(x, y, x + side, y);
        renderer.renderLine(x + side, y, x + side, y + side);
        renderer.renderLine(x + side, y + side, x, y + side);
        renderer.renderLine(x, y + side, x, y);
    }
}
