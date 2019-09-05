import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class InvalidValueException extends Exception {}

public class Assignment3 {
    public static MyHashTable_DH<KeyPair, Student> hashTable_dh;
    public static void main(String[] args) {
        int T = Integer.parseInt(args[0]);
        String type = args[1];
        String path = args[2];
        hashTable_dh = new MyHashTable_DH<>(T);

        BufferedReader reader1;
        try {
            reader1 = new BufferedReader(new FileReader(path));
            String line = reader1.readLine();

            while (line != null) {
                String[] words = line.split(" ");
                switch (words[0]) {
                    case "insert": {
                        try {
                            KeyPair k = new KeyPair(words[1],words[2]);
                            Student s = new Student(words[1], words[2], words[3], words[4], words[5]);
                            int n = hashTable_dh.insert(k,s);
                            if (n<0) {
                                throw new InvalidValueException();
                            } else {
                                System.out.println(n);
                            }
                        } catch (Exception e) {
                            System.out.println("E");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "update": {
                        try {
                            KeyPair k = new KeyPair(words[1],words[2]);
                            Student s = new Student(words[1], words[2], words[3], words[4], words[5]);
                            int n = hashTable_dh.update(k,s);
                            if (n<0) {
                                throw new InvalidValueException();
                            } else {
                                System.out.println(n);
                            }
                        } catch (Exception e) {
                             System.out.println("E");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "delete": {
                        try {
                            KeyPair k = new KeyPair(words[1], words[2]);
                            int n = hashTable_dh.delete(k);
                            if (n<0) {
                                throw new InvalidValueException();
                            } else {
                                System.out.println(n);
                            }
                        } catch (Exception e) {
                            System.out.println("E");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "address": {
                        try {
                            KeyPair k = new KeyPair(words[1], words[2]);
                            String addr = hashTable_dh.address(k);
                            System.out.println(addr);
                        } catch (Exception e) {
                            System.out.println("E");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "contains": {
                        try {
                            KeyPair k = new KeyPair(words[1], words[2]);
                            if (hashTable_dh.contains(k)) {
                                System.out.println("T");
                            }
                            else {
                                System.out.println("F");
                            }
                        } catch (Exception e) {
                            System.out.println("E");
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "get": {
                        try {
                            KeyPair k = new KeyPair(words[1], words[2]);
                            Student s = hashTable_dh.get(k);
                            System.out.println(s.fname()+" "+s.lname()+" "+s.hostel()+" "+s.department()+" "+s.cgpa());
                        } catch (Exception e) {
                            System.out.println("E");
                            e.printStackTrace();
                        }
                        break;
                    }
                    default: break;
                }
                // read next line
                line = reader1.readLine();
            }
            reader1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
