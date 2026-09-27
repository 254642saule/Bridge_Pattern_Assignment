package bridge;

/** Low-level drawing operations shared by all rendering implementations. */
public interface Renderer {
    void renderCircle(double x, double y, double radius);

    void renderLine(double startX, double startY, double endX, double endY);
}
