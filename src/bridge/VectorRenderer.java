package bridge;

/** A console simulation of vector commands; coordinates keep their decimals. */
public final class VectorRenderer implements Renderer {
    @Override
    public void renderCircle(double x, double y, double radius) {
        System.out.println("Vector circle: center=(" + x + ", " + y
                + "), radius=" + radius);
    }

    @Override
    public void renderLine(double startX, double startY, double endX, double endY) {
        System.out.println("Vector line: from=(" + startX + ", " + startY
                + "), to=(" + endX + ", " + endY + ")");
    }
}
