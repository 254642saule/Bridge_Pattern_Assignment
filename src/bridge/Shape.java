package bridge;

import java.util.Objects;

/** The abstraction owns geometry and delegates drawing to a Renderer. */
public abstract class Shape {
    protected final double x;
    protected final double y;
    private Renderer renderer;

    protected Shape(double x, double y, Renderer renderer) {
        this.x = requireFinite(x, "x");
        this.y = requireFinite(y, "y");
        setRenderer(renderer);
    }

    public final void setRenderer(Renderer renderer) {
        this.renderer = Objects.requireNonNull(renderer, "Renderer must not be null");
    }

    protected final Renderer getRenderer() {
        return renderer;
    }

    protected static double requirePositive(double value, String name) {
        requireFinite(value, name);
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }
        return value;
    }

    private static double requireFinite(double value, String name) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }

    public abstract void draw();
}
