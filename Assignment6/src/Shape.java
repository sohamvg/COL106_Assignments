public class Shape implements ShapeInterface
{
    private int hashTableSize = 15313;
    private int meshType = 1;
    private int boundaryEdges = 0;
    private int connectedComponents = 0;

//    TODO driver
    private MyHashTable<String, Point> pointHashTable = new MyHashTable<>(hashTableSize);
    private MyHashTable<String, Edge> edgeHashTable = new MyHashTable<>(hashTableSize);

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

        // e1
        Object[] e1List = edgeHashTable.getHashList(e1.toString());
        if (e1List != null) {
            for (Object obj : e1List) {
                Edge e = (Edge) obj;
                if (e1.toString().equals(e.toString())) {
                    p1 = e.getP1();
                    p2 = e.getP2();

                    e1 = e;

                    foundP1 = true;
                    foundP2 = true;
                    foundE1 = true;
                    break;
                }
            }
        }

        // e2
        Object[] e2List = edgeHashTable.getHashList(e2.toString());
        if (e2List != null) {
            for (Object obj : e2List) {
                Edge e = (Edge) obj;
                if (e2.toString().equals(e.toString())) {
                    if (foundP2) {
                        if (p3.equals(e.getP1())) {
                            p3 = e.getP1();
                        } else {
                            p3 = e.getP2();
                        }
                    } else { // p2 is already found
                        p2 = e.getP1();
                        p3 = e.getP2();
                    }

                    e2 = e;

                    foundP2 = true;
                    foundP3 = true;
                    foundE2 = true;
                    break;
                }
            }
        }

        // e3
        Object[] e3List = edgeHashTable.getHashList(e3.toString());
        if (e3List != null) {
            for (Object obj : e3List) {
                Edge e = (Edge) obj;
                if (e3.toString().equals(e.toString())) {
                    if (foundP1) {
                        if (!foundP3) {
                            if (p3.equals(e.getP1())) {
                                p3 = e.getP1();
                            } else {
                                p3 = e.getP2();
                            }
                        }
                    } else {
                        if (foundP3) {
                            if (p1.equals(e.getP1())) {
                                p3 = e.getP1();
                            } else p3 = e.getP2();
                        } else {
                            p1 = e.getP1();
                            p3 = e.getP2();
                        }
                    }

                    e3 = e;

                    foundP1 = true;
                    foundP3 = true;
                    foundE3 = true;
                    break;
                }
            }
        }


        e1.addTriangle(triangle);
        e2.addTriangle(triangle);
        e3.addTriangle(triangle);
        p1.addTriangle(triangle);
        p2.addTriangle(triangle);
        p3.addTriangle(triangle);

        // if p1,p2,p3 are not found in edges
        if (!foundP1) {
            Object[] p1List = pointHashTable.getHashList(p1.toString());
            if (p1List != null) {
                for (Object obj : p1List) {
                    Point p = (Point) obj;
                    if (p1.equals(p)) {
                        p1 = p;
                        foundP1 = true;
                        break;
                    }
                }
            }
        }
        if (!foundP2) {
            Object[] p2List = pointHashTable.getHashList(p2.toString());
            if (p2List != null) {
                for (Object obj : p2List) {
                    Point p = (Point) obj;
                    if (p2.equals(p)) {
                        p2 = p;
                        foundP2 = true;
                        break;
                    }
                }
            }
        }
        if (!foundP3) {
            Object[] p3List = pointHashTable.getHashList(p3.toString());
            if (p3List != null) {
                for (Object obj : p3List) {
                    Point p = (Point) obj;
                    if (p3.equals(p)) {
                        p3 = p;
                        foundP3 = true;
                        break;
                    }
                }
            }
        }


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

        if (!foundE1) {
            edgeHashTable.insert(e1.toString(), e1);
            p1.addEdge(e1);
            p2.addEdge(e1);

            boundaryEdges+=1;
        }
        else {
            if (e1.triangleListSize() == 2) boundaryEdges-=1;
            if (e1.triangleListSize() >= 3) meshType = 3;
//            System.out.println("found e1: " + e1 + " " + e1.triangleListSize());
        }

        if (!foundE2) {
            edgeHashTable.insert(e2.toString(), e2);
            p2.addEdge(e2);
            p3.addEdge(e2);

            boundaryEdges+=1;
        }
        else {
            if (e2.triangleListSize() == 2) boundaryEdges-=1;
            if (e2.triangleListSize() >= 3) meshType = 3;
//            System.out.println("found e2: " + e2 + " " + e2.triangleListSize());
        }

        if (!foundE3) {
            edgeHashTable.insert(e3.toString(), e3);
            p1.addEdge(e3);
            p3.addEdge(e3);

            boundaryEdges+=1;
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
        return new EdgeInterface[0];
    }

    @Override
    public int COUNT_CONNECTED_COMPONENTS() {
        return 0;
    }

    @Override
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float[] triangle_coord) {
        return new TriangleInterface[0];
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        return new EdgeInterface[0];
    }

    @Override
    public PointInterface[] VERTEX_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        return new PointInterface[0];
    }

    @Override
    public TriangleInterface[] EXTENDED_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        return new TriangleInterface[0];
    }

    @Override
    public TriangleInterface[] INCIDENT_TRIANGLES(float[] point_coordinates) {
        return new TriangleInterface[0];
    }

    @Override
    public PointInterface[] NEIGHBORS_OF_POINT(float[] point_coordinates) {
        return new PointInterface[0];
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        return new EdgeInterface[0];
    }

    @Override
    public TriangleInterface[] FACE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
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
}

