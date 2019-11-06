public class Point implements PointInterface {
    private float x,y,z;
    private MyArrayList<Edge> pointEdgeList = new MyArrayList<>();
    private MyArrayList<Triangle> pointTriangleList = new MyArrayList<>();

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float[] getXYZcoordinate() {
        return new float[]{x,y,z};
    }

    void addEdge(Edge edge) {
        pointEdgeList.add(edge);
    }

    void addTriangle(Triangle triangle) {
        pointTriangleList.add(triangle);
    }

    public String hashString() { // for hashing "x,y,z"
        return x +
                "," + y +
                "," + z;
    }

    @Override
    public String toString() {
        return x +
                "," + y +
                "," + z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Float.compare(point.x, x) != 0) return false;
        if (Float.compare(point.y, y) != 0) return false;
        return Float.compare(point.z, z) == 0;
    }
}
