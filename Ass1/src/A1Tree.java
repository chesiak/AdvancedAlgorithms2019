//package assignment1AADS.assignment1;
//Aleksandra Chesiak
//Patryk Staniszewski

public interface A1Tree {
    public void insert(Integer value);
    public Integer mostSimilarValue(Integer value);
    public void printByLevels();
}

class Node {
    Integer value;
    int depth;
    Node left = null;
    Node right = null;

    public Node () {}

    public Node (Integer value) {
        this.value = value;
    }

    public Node (Integer value, int depth) {
        this.value = value;
        this.depth = depth;
    }
}

class MyIntegerBST implements A1Tree {
    Node root;
    Integer minValue;
    Integer maxValue;

    public MyIntegerBST () {
        root = null;
        minValue = 0;
        maxValue = 0;
    }

    public void insert (Integer value) {
        if (root == null) {
            minValue = value;
            maxValue = value;
        }
        else {
            if (value < minValue) {
                minValue = value;
            }
            if (value > maxValue) {
                maxValue = value;
            }
        }

        root = insert (value, root, 0);
    }

    private Node insert (Integer x, Node t, int depth) {
        if (t == null) {
            return new Node (x, depth);
        }

        if (x.compareTo(t.value) < 0) {         // x < t.value
            t.left = insert(x, t.left, depth + 1);
        }

        else if (x.compareTo(t.value) > 0) {
            t.right = insert(x, t.right, depth + 1);
        }

        return t;
    }

    private int absoluteValue (int value) {
        if (value < 0) {
            return (0 - value);
        }
        else {
            return value;
        }
    }

    public Integer mostSimilarValue (Integer x) {

        if (root == null) {
            return -1;
        }

        if (x <= minValue) {
            return minValue;
        }

        if (x >= maxValue) {
            return maxValue;
        }

        Node temp = root;
        Node result = root;
        int diff = absoluteValue(x - temp.value);
        int workingDiff;

        while (temp != null) {
            if (x.compareTo(temp.value) < 0) {
                temp = temp.left;
            }

            else if (x.compareTo(temp.value) > 0) {
                temp = temp.right;
            }

            else {
                return temp.value;
            }

            if (temp != null) {
                workingDiff = absoluteValue(x - temp.value);

                if (workingDiff < diff) {
                    diff = workingDiff;
                    result = temp;
                }
            }
        }

        return result.value;
    }

    public void printByLevels () {
        Node temp = root;
        Queue myQueue = new Queue();

        if (temp == null) return;

        myQueue.add(temp);
        int depth = -1;

        while (!myQueue.isEmpty()) {
            temp = myQueue.remove();

            if (temp.left != null) {
                myQueue.add(temp.left);
            }

            if (temp.right != null) {
                myQueue.add(temp.right);
            }

            if (temp.depth != depth) {
                depth++;
                if(depth != 0) {
                    System.out.print("\n");
                }
                System.out.print("Depth " + depth + ": ");
            }
            System.out.print(temp.value + " ");
        }
    }
}


class Link {
    Node node;
    Link next = null;

    public Link (Node x) {
        this.node = x;
    }
}

class Queue {
    Link head;
    Link last;

    public Queue () {
        head = null;
        last = null;
    }

    public boolean isEmpty () {
        return head == null;
    }

    public Node remove () {
        if (!isEmpty()) {
            Link temp = head;
            head = head.next;
            return temp.node;
        }
        else {
            return null;
        }
    }

    public void add (Node x) {
        if (isEmpty()) {
            head = new Link (x);
            last = head;
        }
        else {
            Link temp = new Link (x);
            last.next = temp;
            last = temp;
        }
    }
}