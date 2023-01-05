
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinarySearchTree <T extends Comparable<T>> implements Iterable<T> {

    private Node<T> root;
    private String name;

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static class Node<T> {

        private Node<T> left;
        private Node<T> right;

        private T element;

        public Node(T element){
            this.element = element;
            this.right = null;
            this.left = null;
        }

        public Node(T element, Node<T> left, Node<T> right){
            this.element = element;
            this.right = right;
            this.left = left;
        }

        public T getElement(){
            return this.element;
        }

        public Node<T> getLeft(){
            return this.left;
        }

        public Node<T> getRight(){
            return this.right;
        }


        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        public void setElement(T element) {
            this.element = element;
        }
    }

    public BinarySearchTree (Node<T> root, String name){
        this.root = root;
        this.name = name;
    }

    public BinarySearchTree (String name){
        this.name = name;
        this.root = null;
    }


    public void add(Node<T> node){
        if(node == null)
            return;

        if(this.root == null)
            this.root = node;

        addHelper(this.root, node);

    }

    private void addHelper(Node<T> traverse , Node<T> node){
        if(node.element.compareTo(traverse.element) > 0){
            if(traverse.right == null){
                traverse.right = node;
            } else {
                addHelper(traverse.right, node);
            }
        }

        else if(node.element.compareTo(traverse.element) < 0){
            if(traverse.left == null){
                traverse.left = node;
            } else {
                addHelper(traverse.left, node);
            }
        } else {
            return;
        }
    }

    public void addAll(List<T> list){
        for(T element : list)
            add(new Node<T>(element));
    }

    private void printInorder(Node<T> root){                     //CHANGE THIS METHOD BACK TO PRIVATE
        //System.out.println(this.root.element + " root");

        if(root.left != null)
            printInorder(root.left);

        System.out.print(root.element + " ");

        if(root.right != null)
            printInorder(root.right);
    }

    private ArrayList<Node<T>> inorder(Node<T> root, ArrayList<Node<T>> list){

        if(root.left != null)
            inorder(root.left, list);

        list.add(root);

        if(root.right != null)
            inorder(root.right, list);

        return list;
    }

    private String inorderString(Node<T> root, String concat){
        concat += root.element + "";

        if(root.left != null){
            //concat = concat + "L:";
            concat = inorderString(root.left, concat + " L:(");
            concat += ")";
        }

        if(root.right != null){
            //concat = concat + "R:";
            concat = inorderString(root.right, concat + " R:(");
            concat += ")";
        }

        return concat;
    }

    @Override
    public String toString(){
        ArrayList<Node<T>> list = inorder(this.root, new ArrayList<>());

        if(this.root == null)
            return "null BST";

        String toReturn = "[" + this.name + "] " + inorderString(this.root, "");

        return toReturn;

    }


    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            List<Node<T>> BST = inorder(root, new ArrayList<>());

            @Override
            public boolean hasNext() {
                return BST.size() != 0;
            }

            @Override
            public T next() {
                return BST.remove(0).element;
            }

            @Override
            public void remove(){
                BST.remove(0);
            }
        };

        return iterator;
    }

    private static class MergeThread<T extends Comparable<T>> implements Runnable{

        private BinarySearchTree<T> tree1;
        private BinarySearchTree<T> merged;

        public MergeThread(BinarySearchTree<T> t1, BinarySearchTree<T> mergeTree){
            this.tree1 = t1;
            this.merged = mergeTree;
        }



        @Override
        public void run() {

            for(T element : tree1){
                merged.add(new Node<>(element));
            }

        }
    }

    public static <T extends Comparable<T>> List<T> merge (BinarySearchTree<T> t1, BinarySearchTree<T> t2){
        BinarySearchTree<T> mergedTree = new BinarySearchTree<>("Merged Tree");

        Thread t1Thread = new Thread(new MergeThread<T>(t1,mergedTree));
        Thread t2Thread = new Thread(new MergeThread<T>(t2,mergedTree));

        t1Thread.start();
        t2Thread.start();

        while(true){
            if(!(t1Thread.isAlive() || t2Thread.isAlive())) {
                return BinarySearchTree.nodeToElementList(mergedTree.inorder(mergedTree.root, new ArrayList<>()));
            }
        }
    }

    private static <T> List<T> nodeToElementList(List<Node<T>> list){
        List<T> toReturn = new ArrayList<>();
        for(Node<T> node : list){
            toReturn.add(node.element);
        }
        return  toReturn;
    }

}
