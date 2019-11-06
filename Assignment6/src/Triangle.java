public class Triangle implements TriangleInterface {
    private Point P1, P2, P3;
    private Edge e1, e2, e3;

    public Triangle(Point p1, Point p2, Point p3, Edge e1, Edge e2, Edge e3) {
        P1 = p1;
        P2 = p2;
        P3 = p3;
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public void setP1(Point p1) {
        P1 = p1;
    }

    public void setP2(Point p2) {
        P2 = p2;
    }

    public void setP3(Point p3) {
        P3 = p3;
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

    @Override
    public PointInterface[] triangle_coord() {
        return new PointInterface[]{P1,P2,P3};
    }
}
