public class Triangle implements TriangleInterface {
    private Point p1, p2, p3;
    private Edge e1; // between p1 p2
    private Edge e2; // between p2 p3
    private Edge e3; // between p1 p3
    private int timeStamp;
    private boolean visited = false;

    public Triangle(Point p1, Point p2, Point p3, Edge e1, Edge e2, Edge e3, int timeStamp) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.timeStamp = timeStamp;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
    }

    public void setE1(Edge e1) {
        this.e1 = e1;
    }

    public void setE2(Edge e2) {
        this.e2 = e2;
    }

    public void setE3(Edge e3) {
        this.e3 = e3;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    public Edge getE1() {
        return e1;
    }

    public Edge getE2() {
        return e2;
    }

    public Edge getE3() {
        return e3;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    @Override
    public PointInterface[] triangle_coord() {
        return new PointInterface[]{p1,p2,p3};
    }

    public Point[] pointArray() {
        return new Point[]{p1,p2,p3};
    }

    Edge[] edgeArray() {
        return new Edge[]{e1,e2,e3};
    }

    Edge[] getPointAdjEdges(int i) {
        switch (i) {
            case 1: return new Edge[]{e1,e3};
            case 2: return new Edge[]{e1,e2};
            case 3: return new Edge[]{e2,e3};
            default: return null;
        }
    }

    @Override
    public String toString() {
        if (p1.compareTo(p2) < 0) {
            if (p2.compareTo(p3) < 0) {
                return p1 + "," + p2 + "," + p3;
            }
            else {
                if (p1.compareTo(p3) < 0) return p1 + "," + p3 + "," + p2;
                else return p3 + "," + p1 + "," + p2;
            }
        }
        else {
            if (p1.compareTo(p3) < 0) return p2 + "," + p1 + "," + p3;
            else {
                if (p2.compareTo(p3) < 0) return p2 + "," + p3 + "," + p1;
                else return p3 + "," + p2 + "," + p1;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triangle triangle = (Triangle) o;

        return timeStamp == triangle.timeStamp;
    }
}
