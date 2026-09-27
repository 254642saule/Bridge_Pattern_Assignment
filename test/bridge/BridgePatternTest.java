package bridge;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Dependency-free behavior checks; run through test.bat or test.sh. */
public final class BridgePatternTest {
    private static int checks;

    private BridgePatternTest() {
    }

    public static void main(String[] args) {
        testCircleDispatch();
        testSquareGeometry();
        testRuntimeSwitch();
        testNullRenderer();
        testInvalidGeometry();
        testVectorOutput();
        testRasterOutput();
        testIndependentExtension();
        testMainDemo();
        System.out.println("PASS: 9 test groups, " + checks + " checks.");
    }

    private static void testCircleDispatch() {
        RecordingRenderer renderer = new RecordingRenderer();
        new Circle(2, -1, 3, renderer).draw();
        check(renderer.circles.size() == 1, "Circle delegates exactly once");
        check(Arrays.equals(renderer.circles.get(0), new double[]{2, -1, 3}),
                "Circle forwards its geometry unchanged");
    }

    private static void testSquareGeometry() {
        RecordingRenderer renderer = new RecordingRenderer();
        new Square(1, 2, 3, renderer).draw();
        double[][] expected = {{1, 2, 4, 2}, {4, 2, 4, 5}, {4, 5, 1, 5}, {1, 5, 1, 2}};
        check(renderer.lines.size() == 4, "Square has four edges");
        for (int i = 0; i < expected.length; i++) {
            check(Arrays.equals(renderer.lines.get(i), expected[i]), "Square edge " + i);
        }
    }

    private static void testRuntimeSwitch() {
        RecordingRenderer first = new RecordingRenderer();
        RecordingRenderer second = new RecordingRenderer();
        Shape shape = new Circle(1, 2, 3, first);
        shape.draw();
        shape.setRenderer(second);
        shape.draw();
        check(first.circles.size() == 1, "Old renderer is not called after switch");
        check(second.circles.size() == 1, "New renderer receives the next draw call");
        check(Arrays.equals(first.circles.get(0), second.circles.get(0)),
                "Switching preserves the same shape geometry");
        shape.setRenderer(first);
        shape.draw();
        check(first.circles.size() == 2, "Switching back also works");
    }

    private static void testNullRenderer() {
        expectThrows(NullPointerException.class, () -> new Circle(0, 0, 1, null));
        RecordingRenderer renderer = new RecordingRenderer();
        Shape shape = new Square(0, 0, 1, renderer);
        expectThrows(NullPointerException.class, () -> shape.setRenderer(null));
        shape.draw();
        check(renderer.lines.size() == 4, "Rejected null leaves the old renderer usable");
    }

    private static void testInvalidGeometry() {
        Renderer renderer = new RecordingRenderer();
        for (double size : new double[]{0, -1, Double.NaN,
                Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            expectThrows(IllegalArgumentException.class, () -> new Circle(0, 0, size, renderer));
            expectThrows(IllegalArgumentException.class, () -> new Square(0, 0, size, renderer));
        }
        expectThrows(IllegalArgumentException.class,
                () -> new Circle(Double.NaN, 0, 1, renderer));
        expectThrows(IllegalArgumentException.class,
                () -> new Square(0, Double.POSITIVE_INFINITY, 1, renderer));
    }

    private static void testVectorOutput() {
        String output = capture(() -> {
            Renderer renderer = new VectorRenderer();
            new Circle(5.4, 3.2, 2.6, renderer).draw();
            new Square(1.2, 2.4, 3.5, renderer).draw();
        });
        check(output.contains("center=(5.4, 3.2), radius=2.6"), "Vector keeps decimals");
        check(output.contains("from=(1.2, 2.4), to=(4.7, 2.4)"), "Vector draws first edge");
        check(output.contains("from=(1.2, 5.9), to=(1.2, 2.4)"), "Vector closes square");
    }

    private static void testRasterOutput() {
        String output = capture(() -> {
            Renderer renderer = new RasterRenderer();
            new Circle(5.4, 3.2, 2.6, renderer).draw();
            new Circle(0, 0, 0.1, renderer).draw();
            new Square(1.2, 2.4, 3.5, renderer).draw();
        });
        check(output.contains("centerPixel=(5, 3), radiusPixels=3"), "Raster rounds geometry");
        check(output.contains("radiusPixels=1"), "Tiny circle uses at least one pixel radius");
        check(output.contains("fromPixel=(1, 2), toPixel=(5, 2)"), "Raster draws first edge");
        check(output.contains("fromPixel=(1, 6), toPixel=(1, 2)"), "Raster closes square");
    }

    private static void testIndependentExtension() {
        RecordingRenderer renderer = new RecordingRenderer();
        Shape triangle = new Triangle(renderer);
        triangle.draw();
        check(renderer.lines.size() == 3, "New shape reuses existing line operation");
        String output = capture(() -> {
            triangle.setRenderer(new VectorRenderer());
            triangle.draw();
        });
        check(output.lines().filter(line -> line.startsWith("Vector line:")).count() == 3,
                "New shape works with an unchanged existing renderer");
    }

    private static void testMainDemo() {
        String output = capture(() -> Main.main(new String[0]));
        check(output.contains("Vector circle:"), "Demo includes vector circle");
        check(output.contains("Raster circle:"), "Demo includes raster circle");
        check(output.lines().filter(line -> line.startsWith("Vector line:")).count() == 4,
                "Demo includes vector square");
        check(output.lines().filter(line -> line.startsWith("Raster line:")).count() == 4,
                "Demo includes raster square");
        check(output.indexOf("Vector circle:") < output.indexOf("Raster circle:"),
                "Circle renderer changes during the demo");
        check(output.indexOf("Vector line:") < output.indexOf("Raster line:"),
                "Square renderer changes during the demo");
    }

    private static String capture(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream temporary = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(temporary);
            action.run();
        } finally {
            System.setOut(original);
        }
        return bytes.toString(StandardCharsets.UTF_8);
    }

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void expectThrows(Class<? extends Throwable> type, Runnable action) {
        checks++;
        try {
            action.run();
        } catch (Throwable error) {
            if (type.isInstance(error)) {
                return;
            }
            throw new AssertionError("Expected " + type.getSimpleName(), error);
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }

    private static final class RecordingRenderer implements Renderer {
        private final List<double[]> circles = new ArrayList<>();
        private final List<double[]> lines = new ArrayList<>();

        @Override
        public void renderCircle(double x, double y, double radius) {
            circles.add(new double[]{x, y, radius});
        }

        @Override
        public void renderLine(double startX, double startY, double endX, double endY) {
            lines.add(new double[]{startX, startY, endX, endY});
        }
    }

    /** Test-only extension: production classes and Renderer stay unchanged. */
    private static final class Triangle extends Shape {
        private Triangle(Renderer renderer) {
            super(0, 0, renderer);
        }

        @Override
        public void draw() {
            getRenderer().renderLine(x, y, x + 2, y);
            getRenderer().renderLine(x + 2, y, x + 1, y + 2);
            getRenderer().renderLine(x + 1, y + 2, x, y);
        }
    }
}
