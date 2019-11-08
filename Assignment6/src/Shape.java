import java.util.Arrays;

public class Shape implements ShapeInterface
{
    private int hashTableSize = 15313;
    private int meshType = 1;
    private int boundaryEdges = 0;
    private int time = 0;

//    TODO driver
    private MyHashTable<String, Point> pointHashTable = new MyHashTable<>(hashTableSize);
    private MyHashTable<String, Edge> edgeHashTable = new MyHashTable<>(hashTableSize);
    private MyHashTable<String, Triangle> triangleMyHashTable = new MyHashTable<>(hashTableSize);
    private MyArrayList<Edge> allEdgeList = new MyArrayList<>();
    private MyArrayList<Triangle> allTriangleList = new MyArrayList<>();
    private MyArrayList<Triangle> visitedTriangles;

    @Override
    public boolean ADD_TRIANGLE(float[] triangle_coord) {

        float z31 = triangle_coord[8] - triangle_coord[2];
        float y31 = triangle_coord[7] - triangle_coord[1];
        float x31 = triangle_coord[6] - triangle_coord[0];
        float z21 = triangle_coord[5] - triangle_coord[2];
        float y21 = triangle_coord[4] - triangle_coord[1];
        float x21 = triangle_coord[3] - triangle_coord[0];

        if((z31*y21 == z21*y31) && (x31*y21 == y31*x21) && (z31*x21 == x31*z21)) {
            return false;
        }
        else {

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

                foundP1 = true;
                foundP2 = true;
                foundE1 = true;
            }

            // e2
            Edge oldE2 = edgeHashTable.get(e2.toString());
            if (oldE2 != null) {
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

                foundP2 = true;
                foundP3 = true;
                foundE2 = true;
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

            Triangle triangle = new Triangle(p1,p2,p3,e1,e2,e3,time);
            time+=1;
            allTriangleList.add(triangle);
            triangleMyHashTable.insert(triangle.toString(), triangle);
            e1.addTriangle(triangle);
            e2.addTriangle(triangle);
            e3.addTriangle(triangle);
            p1.addTriangle(triangle);
            p2.addTriangle(triangle);
            p3.addTriangle(triangle);
            e1.setPoints(p1,p2);
            e2.setPoints(p2,p3);
            e3.setPoints(p1,p3);

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
            return true;
        }
    }

    @Override
    public int TYPE_MESH() {
//        System.out.println("edges : " + edgeHashTable.inserted);
//        System.out.println("points : " + pointHashTable.inserted);
        return meshType;
    }

    @Override
    public EdgeInterface[] BOUNDARY_EDGES() {
        if (boundaryEdges == 0) return null;

        EdgeInterface[] boundaryEdgeArray = new EdgeInterface[boundaryEdges];
        int bi = 0;
        for (int i = 0; i < allEdgeList.size(); i++) {
            Edge e = allEdgeList.get(i);
            if (e.triangleListSize() == 1) {
                boundaryEdgeArray[bi] = e;
                bi+=1;
            }
        }

        mergeSort(boundaryEdgeArray,0,boundaryEdges-1);
        //TODO sort
        return boundaryEdgeArray;
    }

    private float edgeSquaredLength(EdgeInterface edge) {
        float x1_x2 = edge.edgeEndPoints()[0].getX() - edge.edgeEndPoints()[1].getX();
        float y1_y2 = edge.edgeEndPoints()[0].getY() - edge.edgeEndPoints()[1].getY();
        float z1_z2 = edge.edgeEndPoints()[0].getZ() - edge.edgeEndPoints()[1].getZ();

        return (x1_x2*x1_x2) + (y1_y2*y1_y2) + (z1_z2*z1_z2);
    }

    private void merge(EdgeInterface[] a, int l, int r) {
        int m = (l+r)/2;
        int n1 = m - l + 1;
        int n2 = r - m;
        EdgeInterface[] L= new EdgeInterface[n1];
        EdgeInterface[] R = new EdgeInterface[n2];
        System.arraycopy(a, l, L, 0, n1);

        for (int j=0; j<n2; ++j) {
            R[j] = a[m+1+j];
        }
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (edgeSquaredLength(L[i]) <= edgeSquaredLength(R[j])) {
                a[k] = L[i];
                i++;
            }
            else {
                a[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            a[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            a[k] = R[j];
            j++;
            k++;
        }
    }

    private void mergeSort(EdgeInterface[] a, int l, int r) {
        if (l < r) {
            int m = (l+r)/2;
            mergeSort(a, l, m);
            mergeSort(a, m+1, r);
            merge(a, l, r);
        }
    }

    @Override
    public int COUNT_CONNECTED_COMPONENTS() {
        int count = 0;

        for (int i = 0; i < allTriangleList.size(); i++) {
            Triangle t = allTriangleList.get(i);

            if (!t.isVisited()) {
                dfs(t);
                count+=1;
            }
        }

        // un-mark visited triangles
        for (int i = 0; i < allTriangleList.size(); i++) {
            allTriangleList.get(i).setVisited(false);
        }

        return count;
    }

    private void dfs(Triangle t) {
        t.setVisited(true);

        Edge[] edges = t.edgeArray();

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < edges[j].triangleListSize(); i++) {
                Triangle ti = edges[j].getEdgeTriangleList().get(i);
                if (!ti.isVisited()) {
                    dfs(ti);
                }
            }
        }
    }

    @Override
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float[] triangle_coord) {
        String hashKey = triangleToString(triangle_coord);
        Triangle t = triangleMyHashTable.get(hashKey);
        if (t == null) return null;
        else {
            int size = t.getE1().triangleListSize()+t.getE2().triangleListSize()+t.getE3().triangleListSize()-3;

            TriangleInterface[] res = new TriangleInterface[size];
//            int index = 0;
//
//            for (int i = 0; i < t.getE1().triangleListSize(); i++) {
//                Triangle ti = t.getE1().getEdgeTriangleList().get(i);
//                if (!ti.toString().equals(t.toString())) {
//                    res[index] = ti;
//                    index+=1;
//                }
//                res[i] = t.getE1().getEdgeTriangleList().get(i);
//            }
//
//            for (int i = 0; i < t.getE2().triangleListSize(); i++) {
//                Triangle ti = t.getE2().getEdgeTriangleList().get(i);
//                if (!ti.toString().equals(t.toString())) {
//                    res[index] = ti;
//                    index+=1;
//                }
//            }
//            for (int i = 0; i < t.getE3().triangleListSize(); i++) {
//                Triangle ti = t.getE3().getEdgeTriangleList().get(i);
//                if (!ti.toString().equals(t.toString())) {
//                    res[index] = ti;
//                    index+=1;
//                }
//            }

            int i = 0; int i1 = 0; int i2 = 0; int i3 = 0;

            while (i < size) {

                if (i1 >= t.getE1().triangleListSize()) { // i1 maxed
                    if (i2 >= t.getE2().triangleListSize()) { // i2 maxed
                        // i3
                        Triangle t3 = t.getE3().getEdgeTriangleList().get(i3);
                        if (!t3.equals(t)) {
                            res[i] = t3;
                            i++;
                        }
                        i3++;
                    }
                    else {
                        if (i3 >= t.getE3().triangleListSize()) { // i3 maxed
                            // i2
                            Triangle t2 = t.getE2().getEdgeTriangleList().get(i2);
                            if (!t2.equals(t)) {
                                res[i] = t2;
                                i++;
                            }
                            i2++;
                        }
                        else {
                            // i2 i3
                            Triangle t2 = t.getE2().getEdgeTriangleList().get(i2);
                            Triangle t3 = t.getE3().getEdgeTriangleList().get(i3);

                            if (t2.getTimeStamp() < t3.getTimeStamp()) {
                                if (!t2.equals(t)) {
                                    res[i] = t2;
                                    i++;
                                }
                                i2++;
                            }
                            else if (t3.getTimeStamp() < t2.getTimeStamp()) {
                                if (!t3.equals(t)) {
                                    res[i] = t3;
                                    i++;
                                }
                                i3++;
                            }
                            else {
                                i2++;
                                i3++;
                            }
                        }
                    }
                }
                else {
                    if (i2 >= t.getE2().triangleListSize()) { // i2 maxed
                        if (i3 >= t.getE3().triangleListSize()) { // i3 maxed
                            // i1
                            Triangle t1 = t.getE1().getEdgeTriangleList().get(i1);
                            if (!t1.equals(t)) {
                                res[i] = t1;
                                i++;
                            }
                            i1++;
                        }
                        else {
                            // i1 i3
                            Triangle t1 = t.getE1().getEdgeTriangleList().get(i1);
                            Triangle t3 = t.getE3().getEdgeTriangleList().get(i3);

                            if (t1.getTimeStamp() < t3.getTimeStamp()) {
                                if (!t1.equals(t)) {
                                    res[i] = t1;
                                    i++;
                                }
                                i1++;
                            }
                            else if (t3.getTimeStamp() < t1.getTimeStamp()) {
                                if (!t3.equals(t)) {
                                    res[i] = t3;
                                    i++;
                                }
                                i3++;
                            }
                            else {
                                i1++;
                                i3++;
                            }
                        }
                    }
                    else {
                        if (i3 >= t.getE3().triangleListSize()) { // i3 maxed
                            // i1 i2
                            Triangle t1 = t.getE1().getEdgeTriangleList().get(i1);
                            Triangle t2 = t.getE2().getEdgeTriangleList().get(i2);

                            if (t1.getTimeStamp() < t2.getTimeStamp()) {
                                if (!t1.equals(t)) {
                                    res[i] = t1;
                                    i++;
                                }
                                i1++;
                            }
                            else if (t2.getTimeStamp() < t1.getTimeStamp()) {
                                if (!t2.equals(t)) {
                                    res[i] = t2;
                                    i++;
                                }
                                i2++;
                            }
                            else {
                                i1++;
                                i2++;
                            }
                        }
                        else {
                            // i1 i2 i3
                            Triangle t1 = t.getE1().getEdgeTriangleList().get(i1);
                            Triangle t2 = t.getE2().getEdgeTriangleList().get(i2);
                            Triangle t3 = t.getE3().getEdgeTriangleList().get(i3);

                            if (t1.getTimeStamp() <= t2.getTimeStamp()) {
                                if (t2.getTimeStamp() <= t3.getTimeStamp()) {
                                    if (!t1.equals(t)) {
                                        res[i] = t1;
                                        i++;
                                    }
                                    i1++;
                                }
                                else {
                                    if (t3.getTimeStamp() <= t1.getTimeStamp()) {
                                        if (!t3.equals(t)) {
                                            res[i] = t3;
                                            i++;
                                        }
                                        i3++;
                                    }
                                    else {
                                        if (!t1.equals(t)) {
                                            res[i] = t1;
                                            i++;
                                        }
                                        i1++;
                                    }
                                }
                            }
                            else {
                                if (t1.getTimeStamp() <= t3.getTimeStamp()) {
                                    if (!t2.equals(t)) {
                                        res[i] = t2;
                                        i++;
                                    }
                                    i2++;
                                }
                                else {
                                    if (t3.getTimeStamp() <= t2.getTimeStamp()) {
                                        if (!t3.equals(t)) {
                                            res[i] = t3;
                                            i++;
                                        }
                                        i3++;
                                    }
                                    else {
                                        if (!t2.equals(t)) {
                                            res[i] = t2;
                                            i++;
                                        }
                                        i2++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

//            System.out.println(Arrays.toString(res));
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
                MyArrayList<Triangle> pointMyArrayList = tPoints[i].getPointTriangleList();

                for (int j = 0; j < pointMyArrayList.size(); j++) {

                    Triangle tj = pointMyArrayList.get(j);
                    Point[] tjPoints = tj.pointArray();

                    for (int k = 0; k < 3; k++) {

                        if (tjPoints[k].equals(tPoints[i])) {

                            Edge[] ee = tj.getPointAdjEdges(k+1);
                            Edge[] tPointEdges = t.getPointAdjEdges(i+1);
                            if (!ee[0].toString().equals(tPointEdges[0].toString()) && !ee[0].toString().equals(tPointEdges[1].toString())  &&  !ee[1].toString().equals(tPointEdges[0].toString()) && !ee[1].toString().equals(tPointEdges[1].toString())) {
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
                    if (!ti.toString().equals(t.toString())) {
                        resList.add(ti);
                    }
                }
            }

            if (resList.size() == 0) return null;

            TriangleInterface[] res = new TriangleInterface[resList.size()];
            for (int i = 0; i < resList.size(); i++) {
                res[i] = resList.get(i);
            }

//            System.out.println("res:  "+ Arrays.toString(res));
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
//            System.out.println(Arrays.toString(res));
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
        String hashKey = pointToString(point_coordinates);
        Point p = pointHashTable.get(hashKey);
        if (p == null) return null;
        else {
            TriangleInterface[] res = new TriangleInterface[p.getPointTriangleList().size()];
            for (int i = 0; i < p.getPointTriangleList().size(); i++) {
                res[i] = p.getPointTriangleList().get(i); // already sorted
            }
//            System.out.println(Arrays.toString(res));
            return res;
        }
    }

    private boolean foundConnection = false;

    @Override
    public boolean IS_CONNECTED(float[] triangle_coord_1, float[] triangle_coord_2) {
        foundConnection = false;

        String hashKey = triangleToString(triangle_coord_1);
        Triangle t = triangleMyHashTable.get(hashKey);

        visitedTriangles = new MyArrayList<>();
        dfs1(t, triangleToString(triangle_coord_2));

        // un-mark visited triangles
        for (int i = 0; i < visitedTriangles.size(); i++) {
            visitedTriangles.get(i).setVisited(false);
        }

        return foundConnection;
    }

    private void dfs1(Triangle t, String triangleToString) {
       t.setVisited(true);
       visitedTriangles.add(t);

        Edge[] edges = t.edgeArray();

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < edges[j].triangleListSize(); i++) {
                Triangle ti = edges[j].getEdgeTriangleList().get(i);
                if (!ti.isVisited()) {
                    if (ti.toString().equals(triangleToString)) {
                        foundConnection = true;
                        return;
                    }
                    dfs1(ti, triangleToString);
                }
            }
        }
    }

    @Override
    public TriangleInterface[] TRIANGLE_NEIGHBOR_OF_EDGE(float[] edge_coordinates) {
        String hashKey = edgeToString(edge_coordinates);
        Edge e = edgeHashTable.get(hashKey);
        if (e == null) return null;
        else {
            TriangleInterface[] res = new TriangleInterface[e.getEdgeTriangleList().size()];
            for (int i = 0; i < e.getEdgeTriangleList().size(); i++) {
                res[i] = e.getEdgeTriangleList().get(i); // already sorted
            }
            return res;
        }
    }

    @Override
    public int MAXIMUM_DIAMETER() {
        return 0;
    }

    @Override
    public PointInterface[] CENTROID() {
        MyArrayList<Point> temp = new MyArrayList<>();

        for (int i = 0; i < allTriangleList.size(); i++) {
            Triangle t = allTriangleList.get(i);

            if (!t.isVisited()) {
                sum = new float[]{0,0,0};
                totalComponentPoints = 0;
                dfs2(t);
                Point p = new Point(sum[0]/totalComponentPoints,sum[1]/totalComponentPoints,sum[2]/totalComponentPoints);
                temp.add(p);
            }
        }

        // un-mark visited triangles
        for (int i = 0; i < allTriangleList.size(); i++) {
            Triangle t = allTriangleList.get(i);
            t.setVisited(false);
            t.getP1().setVisited(false);
            t.getP2().setVisited(false);
            t.getP3().setVisited(false);
        }

        PointInterface[] res = new PointInterface[temp.size()];

        for (int i = 0; i < temp.size(); i++) {
            res[i] = temp.get(i);
        }

//        System.out.println(Arrays.toString(res));
        // TODO sort
        return res;
    }

    @Override
    public PointInterface CENTROID_OF_COMPONENT(float[] point_coordinates) {
        String hashKey = pointToString(point_coordinates);
        Point p = pointHashTable.get(hashKey);
        if (p == null) return null;
        else {
            visitedTriangles = new MyArrayList<>();
            sum = new float[]{0,0,0};
            totalComponentPoints = 0;
            dfs2(p.getPointTriangleList().get(0));

            // un-mark visited triangles and points
            for (int i = 0; i < visitedTriangles.size(); i++) {
                Triangle t = visitedTriangles.get(i);
                t.setVisited(false);
                t.getP1().setVisited(false);
                t.getP2().setVisited(false);
                t.getP3().setVisited(false);
            }

            System.out.println(sum[0]/totalComponentPoints + " " + sum[1]/totalComponentPoints + " " + sum[2]/totalComponentPoints);
            return new Point(sum[0]/totalComponentPoints,sum[1]/totalComponentPoints,sum[2]/totalComponentPoints);
        }
    }

    private float[] sum;
    private int totalComponentPoints;

    private void dfs2(Triangle t) {
        t.setVisited(true);
        visitedTriangles.add(t);

        Point[] points = t.pointArray();

        for (int i = 0; i < 3; i++) {
            if (!points[i].isVisited()) {
                points[i].setVisited(true);
                totalComponentPoints+=1;

                sum[0] = sum[0] + points[i].getX();
                sum[1] = sum[1] + points[i].getY();
                sum[2] = sum[2] + points[i].getZ();
            }
        }

        Edge[] edges = t.edgeArray();

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < edges[j].triangleListSize(); i++) {
                Triangle ti = edges[j].getEdgeTriangleList().get(i);
                if (!ti.isVisited()) {
                    dfs2(ti);
                }
            }
        }
    }

    @Override
    public PointInterface[] CLOSEST_COMPONENTS() {
        MyArrayList<MyArrayList<Point>> componentPoints = new MyArrayList<>();

        for (int i = 0; i < allTriangleList.size(); i++) {
            Triangle t = allTriangleList.get(i);

            if (!t.isVisited()) {
                dfs(t);
            }
        }

        // un-mark visited triangles
        for (int i = 0; i < allTriangleList.size(); i++) {
            allTriangleList.get(i).setVisited(false);
        }

        return new PointInterface[0];
    }

    ///////////////////////////////////////////////////////////////////

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

