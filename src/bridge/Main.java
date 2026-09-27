package bridge;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Renderer vector = new VectorRenderer();
        Renderer raster = new RasterRenderer();

        Shape circle = new Circle(5.4, 3.2, 2.6, vector);
        Shape square = new Square(1.2, 2.4, 3.5, vector);

        demonstrateSwitch("Circle", circle, raster);
        demonstrateSwitch("Square", square, raster);
    }

    private static void demonstrateSwitch(String name, Shape shape, Renderer replacement) {
        System.out.println("=== " + name + " ===");
        System.out.println("Before switch:");
        shape.draw();

        System.out.println("Switching renderer on the same " + name + " object...");
        shape.setRenderer(replacement);

        System.out.println("After switch:");
        shape.draw();
        System.out.println();
    }
}
