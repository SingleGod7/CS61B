public class A {
    public static void main(String[] args){
        LinkedListDeque<Integer> student = new  LinkedListDeque<>();
        student.removeFirst();
        student.removeFirst();
        System.out.print(student.size());
        System.out.println(student.getRecursive(3));
    }
}
