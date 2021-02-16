import java.util.*;

public class Main {
    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter: ");
        String sentence = in .nextLine();

        int[] array = new int[256]; //an array to store all the frequencies

        for (int i = 0; i < sentence.length(); i++) { //go through the sentence
            array[(int) sentence.charAt(i)]++; //increment the appropriate frequencies
        }

        PriorityQueue < Tree > PQ = new PriorityQueue < Tree > (); //make a priority queue to hold the forest of trees    

        for (int i = 0; i < array.length; i++) { //go through frequency array
            if (array[i] > 0) { //print out non-zero frequencies - cast to a char

                //FILL THIS IN:
                //MAKE THE FOREST OF TREES AND ADD THEM TO THE PQ

                //create a new Tree 
                Tree t = new Tree();
                //set the cumulative frequency of that Tree
                t.frequency = array[i];
                //insert the letter as the root node
                Node n = new Node();
                t.root = n;
                n.letter = (char)i;
                n.smallestLetter =  (char)i;
                //add the Tree into the PQ
                PQ.add(t);
            }
        }

        while (PQ.size() > 1) { //while there are two or more Trees left in the forest

        //FILL THIS IN:
        //IMPLEMENT THE HUFFMAN ALGORITHM

        //create two temp trees to store the polled trees from PriorityQueue
        Tree temp = PQ.poll();
        Tree temp1 = PQ.poll();
        //create tree that will re-enter the queue
        Tree combo = new Tree();
        combo.frequency = temp.frequency+temp1.frequency;
        //when you're making the new combined tree, don't forget to create a new root node (or else you'll get a null pointer exception)
        Node c = new Node();
        combo.root = c;
        //make sure to set the smallestLetter of the new combined tree to what it should be
        if (temp.root.smallestLetter < temp1.root.smallestLetter){
          combo.root.smallestLetter = temp.root.smallestLetter;
        }
        else{
          combo.root.smallestLetter = temp1.root.smallestLetter;
        }
        //the first tree to come out of PQ goes on the left, the second on the right
        combo.root.leftChild = temp.root;
        combo.root.rightChild = temp1.root;   
        //add combo tree back into PriorityQueue
        PQ.add(combo);
        }

        //now there's only one tree left - get its codes
        Tree HuffmanTree = PQ.poll(); 

        //FILL THIS IN:
        //get all the codes for the letters and print them out
        for (int i = 0; i<sentence.length();i++){
        //call the getCode() method on the HuffmanTree Tree object for each letter in the sentence
          System.out.print(HuffmanTree.getCode(sentence.charAt(i)));
        }
    }
}

class Node {

    public char letter = '@'; //stores letter
    public char smallestLetter = '@';  //a nice idea is to track the smallest letter in the tree in a special variable like this
				
    public Node leftChild; // this node's left child
    public Node rightChild; // this node's right child


} // end class Node


class Tree implements Comparable < Tree > {
    public Node root; // first node of tree
    public int frequency = 0;

    public Tree() // constructor
    {
        root = null;
    } // no nodes in tree yet

    //the PriorityQueue needs to be able to somehow rank the objects in it
    //thus, the objects in the PriorityQueue must implement an interface called Comparable
    //the interface requires you to write a compareTo() method so here it is:

    public int compareTo(Tree object) {
        if (frequency - object.frequency > 0) { //compare the cumulative frequencies of the tree
            return 1;
        } else if (frequency - object.frequency < 0) {
            return -1; //return 1 or -1 depending on whether these frequencies are bigger or smaller
        } else {
            // Sort based on letters
            char a = this.root.smallestLetter;
            char b = object.root.smallestLetter;

            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
            return 0;
        }
    }

    String path = "error"; //this variable will track the path to the letter we're looking for 

    public String getCode(char letter) { //we want the code for this letter

        return this._getCode(letter, this.root, ""); //return the path that results
    }

    private String _getCode(char letter, Node current, String path) {
        if (current == null) {
            return null;
        }
        if (current.letter == letter) {
            return path;
        }

        String leftPath = this._getCode(letter, current.leftChild, path + "0");
        if (leftPath != null) {
            return leftPath;
        }

        String rightPath = this._getCode(letter, current.rightChild, path + "1");
        return rightPath;
    }

} // end class Tree