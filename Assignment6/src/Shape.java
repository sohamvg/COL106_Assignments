import java.util.Arrays;

public class Shape implements ShapeInterface
{
    private int hashTableSize = 15313;
    private int meshType = 1;
    private int boundaryEdges = 0;
    private int connectedComponents = 0;

//    TODO driver
    private MyHashTable<String, Point> pointHashTable = new MyHashTable<>(hashTableSize);
    private MyHashTable<String, Edge> edgeHashTable = new MyHashTable<>(hashTableSize);
    private MyHashTable<String, Triangle> triangleMyHashTable = new MyHashTable<>(hashTableSize);
    private MyArrayList<Edge> allEdgeList = new MyArrayList<>(hashTableSize);

    @Override
    public boolean ADD_TRIANGLE(float[] triangle_coord) {

        Point p1 = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        Point p2 = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        Point p3 = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);

        boolean foundP1 = false;
        boolean foundP2 = false;
        boolean foundP3 = false;

        Edge e1 = new Edge(p1,p2);
        Edge e2 = new Edge(p2,p3);
        Edge e3 = new Edge(p3,p1);

        boolean foundE1 = false;
        boolean foundE2 = false;
        boolean foundE3 = false;

        Triangle triangle = new Triangle(p1,p2,p3,e1,e2,e3);
        triangleMyHashTable.insert(triangle.toString(), triangle);

        // e1
        Edge oldE1 = edgeHashTable.get(e1.toString());
        if (oldE1 != null) {
            if (p1.equals(oldE1.getP1())) {
                p1 = oldE1.getP1();
                p2 = oldE1.getP2();
            }
            else {
                p1 = oldE1.getP2();
                p2 = oldE1.getP1();
            }

            e1 = oldE1;

            System.out.println("hell");
            foundP1 = true;
            foundP2 = true;
            foundE1 = true;
        }

        // e2
        Edge oldE2 = edgeHashTable.get(e2.toString());
        if (oldE2 != null) {
            System.out.println("tttt "+oldE2.getP1() + " " + oldE2.getP1().getPointTriangleList().toString());

            if (foundP2) {
                if (p3.equals(oldE2.getP1())) {
                    p3 = oldE2.getP1();
                } else {
                    p3 = oldE2.getP2();
                }
            } else {
                if (p2.equals(oldE2.getP1())) {
                    p2 = oldE2.getP1();
                    p3 = oldE2.getP2();
                }
                else {
                    p2 = oldE2.getP2();
                    p3 = oldE2.getP1();
                }
            }

            e2 = oldE2;

            System.out.println(p2 + "  "+ p2.getPointTriangleList().toString());
            System.out.println(oldE2.getP1() + " " + oldE2.getP1().getPointTriangleList().toString());
            foundP2 = true;
            foundP3 = true;
            foundE2 = true;

            System.out.println("hell 1   "+p2 + " found : "+ foundP2 + " " + p2.getPointTriangleList().size() + " "+p2.getPointEdgeList().size());
        }

        // e3
        Edge oldE3 = edgeHashTable.get(e3.toString());
        if (oldE3 != null) {
            if (foundP1) {
                if (!foundP3) {
                    if (p3.equals(oldE3.getP1())) {
                        p3 = oldE3.getP1();
                    } else {
                        p3 = oldE3.getP2();
                    }
                }
            } else {
                if (foundP3) {
                    if (p1.equals(oldE3.getP1())) {
                        p1 = oldE3.getP1();
                    } else p1 = oldE3.getP2();
                } else {
                    if (p1.equals(oldE3.getP1())) {
                        p1 = oldE3.getP1();
                        p3 = oldE3.getP2();
                    }
                    else {
                        p1 = oldE3.getP2();
                        p3 = oldE3.getP1();
                    }
                }
            }

            e3 = oldE3;

            foundP1 = true;
            foundP3 = true;
            foundE3 = true;
        }

        // if p1,p2,p3 are not found in edges
        if (!foundP1) {
            Point oldP1 = pointHashTable.get(p1.toString());
            if (oldP1 != null) {
                p1 = oldP1;
                foundP1 = true;
            }
        }
        if (!foundP2) {
            Point oldP2 = pointHashTable.get(p2.toString());
            if (oldP2 != null) {
                p2 = oldP2;
                foundP2 = true;
            }

        }
        if (!foundP3) {
            Point oldP3 = pointHashTable.get(p3.toString());
            if (oldP3 != null) {
                p3 = oldP3;
                foundP3 = true;
            }
        }

        e1.addTriangle(triangle);
        e2.addTriangle(triangle);
        e3.addTriangle(triangle);
        p1.addTriangle(triangle);
        p2.addTriangle(triangle);
        p3.addTriangle(triangle);

        if (!foundP1) {
            pointHashTable.insert(p1.toString(),p1);
        }
        if (!foundP2) {
            pointHashTable.insert(p2.toString(),p2);
        }
        if (!foundP3) {
            pointHashTable.insert(p3.toString(),p3);
        }

//        System.out.println("e1 "+e1 + " " + e1.triangleListSize());
//        System.out.println("e2 "+e2 + " " + e2.triangleListSize());
//        System.out.println("e3 "+e3 + " " + e3.triangleListSize());
//        System.out.println(boundaryEdges);

        if (meshType != 3) {
            if (boundaryEdges == 3 && foundE1 && foundE2 && foundE3) meshType = 1;
            else meshType = 2;
        }

        if (!foundE1 && !foundE2 && !foundE3) connectedComponents+=1;

        if (!foundE1) {
            edgeHashTable.insert(e1.toString(), e1);
            allEdgeList.add(e1);
            p1.addEdge(e1);
            p1.addNeighbourPoint(p2);
            p2.addEdge(e1);
            p2.addNeighbourPoint(p1);

            boundaryEdges+=1;
//            System.out.println("not found e1: " + e1 + " " + e1.triangleListSize());
        }
        else {
            if (e1.triangleListSize() == 2) boundaryEdges-=1;
            if (e1.triangleListSize() >= 3) meshType = 3;
//            System.out.println("found e1: " + e1 + " " + e1.triangleListSize());
        }

        if (!foundE2) {
            edgeHashTable.insert(e2.toString(), e2);
            allEdgeList.add(e2);
            p2.addEdge(e2);
            p2.addNeighbourPoint(p3);
            p3.addEdge(e2);
            p3.addNeighbourPoint(p2);

            boundaryEdges+=1;
//            System.out.println("not found e2: " + e2 + " points : " + p2 + " " + p3);
        }
        else {
            if (e2.triangleListSize() == 2) boundaryEdges-=1;
            if (e2.triangleListSize() >= 3) meshType = 3;
//            System.out.println("found e2: " + e2 + " " + e2.triangleListSize());
        }

        if (!foundE3) {
            edgeHashTable.insert(e3.toString(), e3);
            allEdgeList.add(e3);
            p1.addEdge(e3);
            p1.addNeighbourPoint(p3);
            p3.addEdge(e3);
            p3.addNeighbourPoint(p1);

            boundaryEdges+=1;
//            System.out.println("not found e3: " + e3 + " " + e3.triangleListSize());
        }
        else {
            if (e3.triangleListSize() == 2) boundaryEdges-=1;
            if (e3.triangleListSize() >= 3) meshType = 3;
//            System.out.println("found e3: " + e3 + " " + e3.triangleListSize());
        }

        triangle.setP1(p1);
        triangle.setP2(p2);
        triangle.setP3(p3);
        triangle.setE1(e1);
        triangle.setE2(e2);
        triangle.setE3(e3);

        Point check = pointHashTable.get(pointToString(new float[]{0,-1,0}));
        System.out.println("p222         " + check + "  " + check.getPointTriangleList().size());
        System.out.println("Points : "+ p1 + " " + p1.getPointTriangleList().size()+ "    " + p2 + " found : "+ foundP2 + " " + p2.getPointTriangleList().size() + " "+p2.getPointEdgeList().size()+ "    " + p3 + " " + p3.getPointTriangleList().size());

        // TODO check collinear
        return true;
    }

    @Override
    public int TYPE_MESH() {
        System.out.println("edges : " + edgeHashTable.inserted);
        System.out.println("points : " + pointHashTable.inserted);
        return meshType;
    }

    @Override
    public EdgeInterface[] BOUNDARY_EDGES() {
        MyArrayList<Edge> temp = new MyArrayList<>();

        for (int i = 0; i < allEdgeList.size(); i++) {
            Edge e = allEdgeList.get(i);
            if (e.triangleListSize() == 1) temp.add(e);
        }

        EdgeInterface[] boundaryEdges = new EdgeInterface[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            boundaryEdges[i] = temp.get(i);
        }

        if (boundaryEdges.length == 0) return null;
        return boundaryEdges;
    }

    @Override
    public int COUNT_CONNECTED_COMPONENTS() {
        return connectedComponents;
    }

    @Override
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float[] triangle_coord) {
        String hashKey = triangleToString(triangle_coord);
        Triangle t = triangleMyHashTable.get(hashKey);
        if (t == null) return null;
        else {
            TriangleInterface[] res = new TriangleInterface[t.getE1().triangleListSize()+t.getE2().triangleListSize()+t.getE3().triangleListSize()-3];
            int index = 0;

            for (int i = 0; i < t.getE1().triangleListSize(); i++) {
                Triangle ti = t.getE1().getEdgeTriangleList().get(i);
                if (!ti.equals(t)) {
                    res[index] = ti;
                    index+=1;

                }
                res[i] = t.getE1().getEdgeTriangleList().get(i);
            }

            for (int i = 0; i < t.getE2().triangleListSize(); i++) {
                Triangle ti = t.getE2().getEdgeTriangleList().get(i);
                if (!ti.equals(t)) {
                    res[index] = ti;
                    index+=1;
                }
            }
            for (int i = 0; i < t.getE3().triangleListSize(); i++) {
                Triangle ti = t.getE3().getEdgeTriangleList().get(i);
                if (!ti.equals(t)) {
                    res[index] = ti;
                    index+=1;
                }
            }
            // TODO sort

//            System.out.println(res.toString());
            return res;
        }
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        EdgeInterface[] res = new EdgeInterface[3];
        String hashKey = triangleToString(triangle_coord);

        Triangle t = triangleMyHashTable.get(hashKey);
        if (t == null) {
            return null;
        }
        else {
            res[0] = t.getE1();
            res[1] = t.getE2();
            res[2] = t.getE3();
        }
        return res;
    }

    @Override
    public PointInterface[] VERTEX_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        PointInterface[] res = new PointInterface[3];
        String hashKey = triangleToString(triangle_coord);

        Triangle t = triangleMyHashTable.get(hashKey);
        if (t == null) return null;
        else {
            res[0] = t.getP1();
            res[1] = t.getP2();
            res[2] = t.getP3();
        }
        return res;
    }

    @Override
    public TriangleInterface[] EXTENDED_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        String hashKey = triangleToString(triangle_coord);

        Triangle t = triangleMyHashTable.get(hashKey);
        if (t == null) return null;
        else {
            MyArrayList<Triangle> resList = new MyArrayList<>();

            Point[] tPoints = t.pointArray();
            for (int i = 0; i < 3; i++) {
                System.out.println("tPoint : " + tPoints[i]);
                MyArrayList<Triangle> pointMyArrayList = tPoints[i].getPointTriangleList();
                System.out.println("no of tr " + tPoints[i].getPointTriangleList().size());

                for (int j = 0; j < pointMyArrayList.size(); j++) {

                    Triangle tj = pointMyArrayList.get(j);
                    Point[] tjPoints = tj.pointArray();

                    for (int k = 0; k < 3; k++) {

                        if (tjPoints[k].equals(tPoints[i])) {

                            System.out.println(tj);
                            System.out.println(tjPoints[k]);
                            Edge[] ee = tj.getPointAdjEdges(k+1);
                            System.out.println(Arrays.toString(ee));
                            Edge[] tPointEdges = t.getPointAdjEdges(i+1);
                            System.out.println(Arrays.toString(tPointEdges));
                            if (!ee[0].equals(tPointEdges[0]) && !ee[0].equals(tPointEdges[1])  &&  !ee[1].equals(tPointEdges[0]) && !ee[1].equals(tPointEdges[1])) {
                                resList.add(tj);
                            }

                            break;
                        }
                    }
                }
            }

            Edge[] tEdges = t.edgeArray();
            for (Edge e : tEdges) {
                MyArrayList<Triangle> eTriangles = e.getEdgeTriangleList();
                for (int i = 0; i < eTriangles.size(); i++) {
                    Triangle ti = eTriangles.get(i);
                    if (!ti.equals(t)) {
                        resList.add(ti);
                    }
                }
            }

            if (resList.size() == 0) return null;

            TriangleInterface[] res = new TriangleInterface[resList.size()];
            for (int i = 0; i < resList.size(); i++) {
                res[i] = resList.get(i);
            }

            System.out.println("ress  "+ Arrays.toString(res));
            //TODO sort
            return res;
        }
    }

    @Override
    public TriangleInterface[] INCIDENT_TRIANGLES(float[] point_coordinates) {
        String hashKey = pointToString(point_coordinates);
        Point p = pointHashTable.get(hashKey);
        if (p == null) return null;
        else {
            TriangleInterface[] res = new TriangleInterface[p.getPointTriangleList().size()];
            for (int i = 0; i < p.getPointTriangleList().size(); i++) {
                res[i] = p.getPointTriangleList().get(i); // already sorted
            }
            return res;
        }
    }

    @Override
    public PointInterface[] NEIGHBORS_OF_POINT(float[] point_coordinates) {
        String hashKey = pointToString(point_coordinates);

        Point p = pointHashTable.get(hashKey);
        if (p == null) return null;
        else {
            PointInterface[] res = new PointInterface[p.getPointEdgeList().size()];

            for (int i = 0; i < p.getNeighbourPointList().size(); i++) {
                res[i] = p.getNeighbourPointList().get(i);
            }

//            System.out.println(Arrays.toString(res));
            return res;
        }
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        String hashKey = pointToString(point_coordinates);

        Point p = pointHashTable.get(hashKey);
        if (p == null) return null;
        else {
            EdgeInterface[] res = new EdgeInterface[p.getPointEdgeList().size()];
            for (int i = 0; i < p.getPointEdgeList().size(); i++) {
                res[i] = p.getPointEdgeList().get(i);
            }
            return res;
        }
    }

    @Override
    public TriangleInterface[] FACE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        // same as incident triangles
        return new TriangleInterface[0];
    }

    @Override
    public boolean IS_CONNECTED(float[] triangle_coord_1, float[] triangle_coord_2) {
        return false;
    }

    @Override
    public TriangleInterface[] TRIANGLE_NEIGHBOR_OF_EDGE(float[] edge_coordinates) {
        return new TriangleInterface[0];
    }

    @Override
    public int MAXIMUM_DIAMETER() {
        return 0;
    }

    @Override
    public PointInterface[] CENTROID() {
        return new PointInterface[0];
    }

    @Override
    public PointInterface CENTROID_OF_COMPONENT(float[] point_coordinates) {
        return null;
    }

    @Override
    public PointInterface[] CLOSEST_COMPONENTS() {
        return new PointInterface[0];
    }

    private String pointToString(float[] point_coordinates) {
        return point_coordinates[0] + "," + point_coordinates[1] + "," + point_coordinates[2];
    }

    private String edgeToString(float[] edge_coordinates) {
        Point p1 = new Point(edge_coordinates[0], edge_coordinates[1], edge_coordinates[2]);
        Point p2 = new Point(edge_coordinates[3], edge_coordinates[4], edge_coordinates[5]);
        Edge edge = new Edge(p1, p2);

        return edge.toString();
    }

    private String triangleToString(float[] triangle_coord) {
        Point p1 = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        Point p2 = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        Point p3 = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);

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
}

