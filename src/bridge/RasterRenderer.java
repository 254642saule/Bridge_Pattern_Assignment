package bridge;

/** A console simulation that maps geometry to a unit-sized pixel grid. */
public final class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(double x, double y, double radius) {
        long pixelRadius = Math.max(1L, Math.round(radius));
        System.out.println("Raster circle: centerPixel=(" + Math.round(x) + ", "
                + Math.round(y) + "), radiusPixels=" + pixelRadius);
    }

    @Override
    public void renderLine(double startX, double startY, double endX, double endY) {
        System.out.println("Raster line: fromPixel=(" + Math.round(startX) + ", "
                + Math.round(startY) + "), toPixel=(" + Math.round(endX) + ", "
                + Math.round(endY) + ")");
    }
}
