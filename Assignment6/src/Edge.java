public class Edge implements EdgeInterface {
    private Point P1, P2;
    public MyArrayList<Triangle> edgeTriangleList;

    Edge(Point p1, Point p2) {
        P1 = p1;
        P2 = p2;
        edgeTriangleList = new MyArrayList<>();
    }

    @Override
    public PointInterface[] edgeEndPoints() {
        return new PointInterface[]{P1,P2};
    }

    Point getP1() {
        return P1;
    }

    Point getP2() {
        return P2;
    }

    void addTriangle(Triangle triangle) {
        edgeTriangleList.add(triangle);
    }

    int triangleListSize() {
        return edgeTriangleList.size();
    }

    public String hashString() {
        if (P1.getX() < P2.getX()) {
            return P1.toString() + "," + P2.toString();
        }
        else if (P1.getX() > P2.getX()) {
            return P2.toString() + "," + P1.toString();
        }
        else { // P1.x == P2.x
            if (P1.getY() < P2.getY()) {
                return P1.toString() + "," + P2.toString();
            }
            else if (P1.getY() > P2.getY()) {
                return P2.toString() + "," + P1.toString();
            }
            else { // P1.y == P2.y
                if (P1.getZ() < P2.getZ()) {
                    return P1.toString() + "," + P2.toString();
                }
                else if (P1.getZ() > P2.getZ()) {
                    return P2.toString() + "," + P1.toString();
                }
                else { // P1 == P2
                    System.out.println("Both points of edge are same");
                    return null;
                }
            }
        }
    }

    @Override
    public String toString() {
        if (P1.getX() < P2.getX()) {
            return P1.toString() + "," + P2.toString();
        }
        else if (P1.getX() > P2.getX()) {
            return P2.toString() + "," + P1.toString();
        }
        else { // P1.x == P2.x
            if (P1.getY() < P2.getY()) {
                return P1.toString() + "," + P2.toString();
            }
            else if (P1.getY() > P2.getY()) {
                return P2.toString() + "," + P1.toString();
            }
            else { // P1.y == P2.y
                if (P1.getZ() < P2.getZ()) {
                    return P1.toString() + "," + P2.toString();
                }
                else if (P1.getZ() > P2.getZ()) {
                    return P2.toString() + "," + P1.toString();
                }
                else { // P1 == P2
                    System.out.println("Both points of edge are same");
                    return null;
                }
            }
        }
    }
}
