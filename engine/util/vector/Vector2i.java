package engine.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;

public class Vector2i extends Vector implements Serializable, ReadableVector2i, WritableVector2i {

    private static final long serialVersionUID = 1L;

    private int x, y;

    /**
     * Constructor for Vector2i.
     */
    public Vector2i() {
        super();
    }

    /**
     * Constructor.
     */
    public Vector2i(ReadableVector2i src) {
        set(src);
    }

    /**
     * Constructor.
     */
    public Vector2i(int x, int y) {
        set(x, y);
    }

    /* (non-Javadoc)
     * @see org.lwjgl.util.vector.WritableVector2i#set(int, int)
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Load from another Vector2i
     * @param src The source vector
     * @return this
     */
    public Vector2i set(ReadableVector2i src) {
        x = src.getX();
        y = src.getY();
        return this;
    }

    /**
     * @return the length squared of the vector
     */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /**
     * Translate a vector
     * @param x The translation in x
     * @param y the translation in y
     * @return this
     */
    public Vector2i translate(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2i rotate(float angle) {
        x *= Math.cos(angle);
        y *= Math.sin(angle);

        return this;
    }

    /**
     * Negate a vector
     * @return this
     */
    public Vector negate() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * Negate a vector and place the result in a destination vector.
     * @param dest The destination vector or null if a new vector is to be created
     * @return the negated vector
     */
    public Vector2i negate(Vector2i dest) {
        if (dest == null)
            dest = new Vector2i();
        dest.x = -x;
        dest.y = -y;
        return dest;
    }


    /**
     * Normalise this vector and place the result in another vector.
     * @param dest The destination vector, or null if a new vector is to be created
     * @return the normalised vector
     */
    public Vector2i normalise(Vector2i dest) {
        int l = (int) length();

        if (dest == null)
            dest = new Vector2i(x / l, y / l);
        else
            dest.set(x / l, y / l);

        return dest;
    }

    /**
     * The period product of two vectors is calculated as
     * v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
     * @param left The LHS vector
     * @param right The RHS vector
     * @return left period right
     */
    public static int dot(Vector2i left, Vector2i right) {
        return left.x * right.x + left.y * right.y;
    }



    /**
     * Calculate the angle between two vectors, in radians
     * @param a A vector
     * @param b The other vector
     * @return the angle between the two vectors, in radians
     */
    public static int angle(Vector2i a, Vector2i b) {
        int dls = (int) (dot(a, b) / (a.length() * b.length()));
        if (dls < -1f)
            dls = -1;
        else if (dls > 1.0f)
            dls = 1;
        return (int)Math.acos(dls);
    }

    /**
     * Add a vector to another vector and place the result in a destination
     * vector.
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     * @return the sum of left and right in dest
     */
    public static Vector2i add(Vector2i left, Vector2i right, Vector2i dest) {
        if (dest == null)
            return new Vector2i(left.x + right.x, left.y + right.y);
        else {
            dest.set(left.x + right.x, left.y + right.y);
            return dest;
        }
    }

    /**
     * Subtract a vector from another vector and place the result in a destination
     * vector.
     * @param left The LHS vector
     * @param right The RHS vector
     * @param dest The destination vector, or null if a new vector is to be created
     * @return left minus right in dest
     */
    public static Vector2i sub(Vector2i left, Vector2i right, Vector2i dest) {
        if (dest == null)
            return new Vector2i(left.x - right.x, left.y - right.y);
        else {
            dest.set(left.x - right.x, left.y - right.y);
            return dest;
        }
    }

    /**
     * Store this vector in a intBuffer
     * @param buf The buffer to store it in, at the current position
     * @return this
     */
    public Vector store(FloatBuffer buf) {
        buf.put(x);
        buf.put(y);
        return this;
    }

    /**
     * Load this vector from a intBuffer
     * @param buf The buffer to load it from, at the current position
     * @return this
     */
    public Vector load(FloatBuffer buf) {
        x = (int) buf.get();
        y = (int) buf.get();
        return this;
    }

    /* (non-Javadoc)
     * @see org.lwjgl.vector.Vector#scale(int)
     */
    public Vector scale(float scale) {

        x *= scale;
        y *= scale;

        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(64);

        sb.append("Vector2i[");
        sb.append(x);
        sb.append(", ");
        sb.append(y);
        sb.append(']');
        return sb.toString();
    }

    /**
     * @return x
     */
    public final int getX() {
        return x;
    }

    /**
     * @return y
     */
    public final int getY() {
        return y;
    }

    /**
     * Set X
     * @param x
     */
    public final void setX(int x) {
        this.x = x;
    }

    /**
     * Set Y
     * @param y
     */
    public final void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2i other = (Vector2i)obj;

        if (x == other.x && y == other.y) return true;

        return false;
    }

    public Vector2i invert() {
        x *= -1;
        y *= -1;

        return this;
    }

    public Vector2f toVec2f() {
        return new Vector2f(x, y);
    }
}