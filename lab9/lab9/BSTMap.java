package lab9;

import lab9tester.TestBSTMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) > 0){
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, this.root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            this.root = new Node(key, value);
            return this.root;
        } else if (p.key.compareTo(key) == 0) {
            p.value = value;
            return p;
        } else if (p.key.compareTo(key) > 0) {
            if (p.left != null) {
                return putHelper(key, value, p.left);
            } else {
                p.left = new Node(key, value);
                return p.left;
            }
        } else {
            if (p.right != null) {
                return putHelper(key, value, p.right);
            } else {
                p.right = new Node(key, value);
                return p.right;
            }
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        this.size += 1;
        putHelper(key, value, this.root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */

    private Set<K> keySetHelper(Node p) {
        if (p == null) {
            return null;
        }
        Set<K> keyBSTMap = new HashSet<>();
        if (p.left != null) {
            keyBSTMap.addAll(keySetHelper(p.left));
        }
        if(p.right != null) {
            keyBSTMap.addAll(keySetHelper(p.right));
        }
        keyBSTMap.add(p.key);
        return keyBSTMap;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keyBSTMap = keySetHelper(this.root);
        return keyBSTMap;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node preNode = null;
        Node curNode = this.root;
        int direction = 0;//relationship between pre and cur, -1 denote the left,1 denote the right
        while (curNode != null && key.compareTo(curNode.key) != 0) {
            preNode = curNode;
            if (key.compareTo(curNode.key) > 0) {
                curNode = curNode.right;
                direction = 1;
            } else {
                curNode = curNode.left;
                direction = -1;
            }
        }

        if (curNode == null) {
            return null;
        }
        if (curNode.left == null && curNode.right ==null) {
            if (preNode == null) {
                this.clear();
                return curNode.value;
            } else {
                if (direction < 0) {
                    preNode.left = null;
                } else {
                    preNode.right = null;
                }
            }
            this.size -= 1;
            return curNode.value;
        }

        if (curNode.left == null && curNode.right != null) {
            if (preNode == null) {
                this.root = curNode.right;
                this.size -= 1;
                return curNode.value;
            }
            if (direction < 0) {
                preNode.left = curNode.right;
            } else {
                preNode.right = curNode.right;
            }
            this.size -= 1;
            return curNode.value;
        }

        if (curNode.left != null && curNode.right == null) {
            if (preNode == null) {
                this.size -= 1;
                this.root = curNode.left;
                return curNode.value;
            }
            if (direction < 0) {
                preNode.left = curNode.left;
            } else {
                preNode.right = curNode.left;
            }
            this.size -= 1;
            return curNode.value;
        }

        Node minNode = removeMin(curNode.right);

        V returnValue = curNode.value;
        curNode.key = minNode.key;
        curNode.value = minNode.value;

        return returnValue;
    }

    private Node removeMin(Node p) {
        if (p.left == null) {
            remove(p.key);
            return p;
        } else {
            return removeMin(p.left);
        }
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (value == this.get(key)) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> a = new BSTMap<>();
        a.put("Zh", 0);
        a.put("an", 1);
        a.put("gY", 2);
        a.put("ua", 3);
        a.put("nC", 4);
        a.put("he", 5);
        a.put("n", 6);
        a.remove("sdfas");
        a.remove("Zh");
        a.remove("nC");
        StringBuilder b = new StringBuilder();
        for (String i : a) {
            b.append(i + " ");
        }
        String c = b.toString();
        System.out.println(c);
    }
}
