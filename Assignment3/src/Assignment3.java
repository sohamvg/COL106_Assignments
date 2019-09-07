import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class InvalidValueException extends Exception {}

public class Assignment3 {
    public static void main(String[] args) {
        int T = Integer.parseInt(args[0]);
        String type = args[1];
        String path = args[2];
        if (type.equals("DH")) {
            MyHashTable_DH<KeyPair<String, String>, Student> hashTable_dh = new MyHashTable_DH<>(T);

            BufferedReader reader1;
            try {
                reader1 = new BufferedReader(new FileReader(path));
                String line = reader1.readLine();

                while (line != null) {
                    String[] words = line.split(" ");
                    switch (words[0]) {
                        case "insert": {
                            try {
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
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
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
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
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
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
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
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
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
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
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
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
        else if (type.equals("SCBST")) {
            MyHashTable_BST<KeyPair<String, String>, Student> hashTable_bst = new MyHashTable_BST<>(T);

            BufferedReader reader1;
            try {
                reader1 = new BufferedReader(new FileReader(path));
                String line = reader1.readLine();

                while (line != null) {
                    String[] words = line.split(" ");
                    switch (words[0]) {
                        case "insert": {
                            try {
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
                                Student s = new Student(words[1], words[2], words[3], words[4], words[5]);
                                int n = hashTable_bst.insert(k, s);
                                System.out.println(n);
                            } catch (Exception e) {
                                System.out.println("E");
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "update": {
                            try {
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
                                Student s = new Student(words[1], words[2], words[3], words[4], words[5]);
                                int n = hashTable_bst.update(k, s);
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
                        case "contains": {
                            try {
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
                                if (hashTable_bst.contains(k)) {
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
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
                                Student s = hashTable_bst.get(k);
                                System.out.println(s.fname()+" "+s.lname()+" "+s.hostel()+" "+s.department()+" "+s.cgpa());
                            } catch (Exception e) {
                                System.out.println("E");
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "address": {
                            try {
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
                                String addr = hashTable_bst.address(k);
                                System.out.println(addr);
                            } catch (Exception e) {
                                System.out.println("E");
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "delete": {
                            try {
                                KeyPair<String, String> k = new KeyPair<>(words[1], words[2]);
                                int n = hashTable_bst.delete(k);
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
}
