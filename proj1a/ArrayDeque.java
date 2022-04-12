public class ArrayDeque<T> {
    private int arraySize;
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] deque;

    public ArrayDeque() {
        this.arraySize = 8;
        this.deque = (T[]) new Object[this.arraySize];
        this.size = 0;
        this.nextFirst = this.arraySize / 2;
        this.nextLast = this.nextFirst + 1;
    }

    private int leftPosition(int x) {
        x -= 1;
        if (x < 0) {
            x += this.arraySize;
        }
        return x;
    }

    private int rightPosition(int x) {
        x += 1;
        if (x >= this.arraySize) {
            x -= this.arraySize;
        }
        return x;
    }

    private void resize(int newArraySize) {
        int oldArraySize = this.arraySize;

        T[] newArray = (T[]) new Object[newArraySize];

        int start = newArraySize / 4;
        int offset = rightPosition(this.nextFirst);
        for(int i = 0;i < this.size; i++) {
            newArray[start + i] = this.deque[(i + offset) % oldArraySize];
        }

        this.nextFirst = start - 1;
        this.nextLast = start + this.size;
        this.deque = newArray;
        this.arraySize = newArraySize;
    }

    public void addFirst(T item) {
        deque[this.nextFirst] = item;
        this.nextFirst = leftPosition(this.nextFirst);
        if(this.nextFirst == this.nextLast) {
            resize(this.arraySize * 2);
        }
        this.size += 1;
    }

    public void addLast(T item) {
        deque[this.nextLast] = item;
        this.nextLast = rightPosition(this.nextLast);
        if(this.nextFirst == this.nextLast) {
            resize(this.arraySize * 2);
        }
        this.size += 1;
    }

    public boolean isEmpty() {
        return rightPosition(this.nextFirst) == this.nextLast;

    }

    public int size() {
        return this.size;
    }

    public void  printDeque() {
        int start = rightPosition(this.nextFirst);
        for(int i = 0; i < this.size; i++) {
            System.out.print(this.deque[(i + start) % this.arraySize]);
            System.out.print(" ");
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }
        this.nextFirst = rightPosition(this.nextFirst);
        this.size -= 1;
        T valueRemoved = this.deque[this.nextFirst];
        if(this.size * 4 <= this.arraySize && this.arraySize > 8) {
            resize(this.arraySize / 2);
        }
        return valueRemoved;
    }

    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        this.nextLast = leftPosition(this.nextLast);
        this.size -= 1;
        T valueRemoved = this.deque[this.nextLast];
        if(this.size() * 4 <= this.arraySize && this.arraySize > 8) {
            resize(this.arraySize / 2);
        }
        return valueRemoved;
    }

    public T get(int index) {
        return this.deque[(rightPosition(this.nextFirst) + index) % this.arraySize];
    }

    }
