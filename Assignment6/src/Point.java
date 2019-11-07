public class Point implements PointInterface, Comparable<Point> {
    private float x,y,z;
    private MyArrayList<Edge> pointEdgeList = new MyArrayList<>();
    private MyArrayList<Triangle> pointTriangleList = new MyArrayList<>();
    private MyArrayList<Point> neighbourPointList = new MyArrayList<>();

    Point(float x, float y, float z) {
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

    MyArrayList<Edge> getPointEdgeList() {
        return pointEdgeList;
    }

    MyArrayList<Triangle> getPointTriangleList() {
        return pointTriangleList;
    }

    MyArrayList<Point> getNeighbourPointList() {
        return neighbourPointList;
    }

    void addEdge(Edge edge) {
        pointEdgeList.add(edge);
    }

    void addTriangle(Triangle triangle) {
        pointTriangleList.add(triangle);
    }

    void addNeighbourPoint(Point point) {
        neighbourPointList.add(point);
    }

    @Override
    public String toString() { // for hashing "x,y,z"
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

    @Override
    public int compareTo(Point point) {
        if (x < point.x) return -1;
        else if (x > point.x) return 1;
        else {
            if (y < point.y) return -1;
            else if (y > point.y) return 1;
            else {
                return Float.compare(z, point.z);
            }
        }
    }
}