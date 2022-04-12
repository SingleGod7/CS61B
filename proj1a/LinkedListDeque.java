public class LinkedListDeque<T> {
    private class LinkedNode {
        private T item;
        private LinkedNode next;
        private LinkedNode prev;

        public LinkedNode(T value, LinkedNode prev, LinkedNode next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private LinkedNode firstSentinel;
    private LinkedNode lastSentinel;
    private int size;

    /* initialize the empty linked list */
    public LinkedListDeque() {
        this.size = 0;
        this.firstSentinel = new LinkedNode(null, null, null);
        this.lastSentinel = new LinkedNode(null, this.firstSentinel, null);
        this.firstSentinel.next = this.lastSentinel;
    }

    /* add an item at the first of the Linkedlist */
    public void addFirst(T item) {
        this.size += 1;
        this.firstSentinel.next = new LinkedNode(item, this.firstSentinel, this.firstSentinel.next);
        this.firstSentinel.next.next.prev = this.firstSentinel.next;
    }
    public void addLast(T item) {
        this.size += 1;
        this.lastSentinel.prev = new LinkedNode(item, this.lastSentinel.prev, this.lastSentinel);
        this.lastSentinel.prev.prev.next = this.lastSentinel.prev;
    }

    public boolean isEmpty() {
        return this.firstSentinel.next == this.lastSentinel;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        LinkedNode x = this.firstSentinel;
        if (x.next != this.lastSentinel) {
            x = x.next;
            System.out.print(x.item);
            System.out.print(" ");
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        } else {
            this.size -= 1;
            LinkedNode removedNode = this.firstSentinel.next;
            this.firstSentinel.next = this.firstSentinel.next.next;
            this.firstSentinel.next.prev = this.firstSentinel;
            return removedNode.item;
        }
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        } else {
            this.size -= 1;
            LinkedNode removedNode = this.lastSentinel.prev;
            this.lastSentinel.prev = this.lastSentinel.prev.prev;
            this.lastSentinel.prev.next = this.lastSentinel;
            return removedNode.item;
        }
    }

    public T get(int index) {
        LinkedNode x = this.firstSentinel;
        while (index >= 0) {
            if (x.next == this.lastSentinel) {
                return null;
            }
            x = x.next;
            index -= 1;
        }
        return x.item;
    }

    private T getRecursive(int index, LinkedNode x) {
        if (index == 0) {
            return x.item;
        } else if (x.next == this.lastSentinel) {
            return null;
        } else {
            return getRecursive(index - 1, x.next);
        }
    }

    public T getRecursive(int index) {
        if (isEmpty()) {
            return null;
        }
        LinkedNode x = this.firstSentinel.next;
        return getRecursive(index, x);
    }
}
