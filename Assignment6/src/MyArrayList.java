import java.util.Arrays;

public class MyArrayList<T> {
    private Object[] myArrayList;
    private int lastIndex;

    public MyArrayList(int size) {
        this.myArrayList = new Object[size];
        this.lastIndex = -1;
    }

    MyArrayList() {
        this.myArrayList = new Object[100];
        this.lastIndex = -1;
    }

    public int size() {
        return lastIndex+1;
    }

    public void add(T element) {
        lastIndex+=1;
        if (lastIndex == myArrayList.length) {
            myArrayList = extendMyArrayList(myArrayList);
        }
        myArrayList[lastIndex] = element;
    }

    private Object[] extendMyArrayList(Object[] myArrayList) {
        Object[] newMyArrayList = new Object[myArrayList.length*2];
        int i = 0;
        for (Object element : myArrayList) {
            newMyArrayList[i] = element;
            i+=1;
        }
        return newMyArrayList;
    }

    public Object[] getMyArrayList() {
        return myArrayList;
    }

    @Override
    public String toString() {
        return "MyArrayList{" +
                "myArrayList=" + Arrays.toString(myArrayList) +
                '}';
    }

    public int getLastIndex() {
        return lastIndex;
    }
}
