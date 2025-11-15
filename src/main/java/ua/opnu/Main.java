package ua.opnu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Predicate<Integer> primePredicate = Main::isPrime;
        int[] numbers = {2, 3, 4, 5, 15, 17, 19, 21};
        int[] primes = filterIntArray(numbers, primePredicate);
        System.out.println(Arrays.toString(primes));

        Student[] students = {
                new Student("Ivan", "Petrenko", "IP-01", new int[]{95, 82, 77, 100}),
                new Student("Olena", "Shevchenko", "IP-01", new int[]{55, 76, 88, 91}),
                new Student("Maksym", "Koval", "IP-02", new int[]{61, 63, 59, 68}),
                new Student("Iryna", "Bondarenko", "IP-02", new int[]{85, 90, 93, 88})
        };
        Predicate<Student> noDebts = s -> Arrays.stream(s.getMarks()).allMatch(mark -> mark >= 60);
        Student[] filteredStudents = filterStudents(students, noDebts);
        System.out.println(Arrays.toString(Arrays.stream(filteredStudents).map(Student::getFullName).toArray(String[]::new)));

        List<Student> excellentStudents = filterByTwoPredicates(students, noDebts, s -> Arrays.stream(s.getMarks()).average().orElse(0) >= 90);
        System.out.println(Arrays.toString(excellentStudents.stream().map(Student::getFullName).toArray(String[]::new)));

        Consumer<Student> printName = s -> System.out.println(s.getSurname() + " " + s.getName());
        forEach(students, printName);

        Predicate<Integer> moreThanTen = x -> x > 10;
        Consumer<Integer> printNumber = System.out::println;
        int[] mixedNumbers = {4, 10, 12, 15, 7, 30};
        applyIf(mixedNumbers, moreThanTen, printNumber);

        Function<Integer, Integer> powerOfTwo = n -> 1 << n;
        int[] inputs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] powers = processArray(inputs, powerOfTwo);
        System.out.println(Arrays.toString(powers));

        Function<Integer, String> numberToWord = n -> {
            String[] words = {"нуль", "один", "два", "три", "чотири", "п'ять", "шість", "сім", "вісім", "дев'ять"};
            return words[n];
        };
        String[] wordsResult = stringify(inputs, numberToWord);
        System.out.println(Arrays.toString(wordsResult));
    }

    static boolean isPrime(int value) {
        if (value < 2) {
            return false;
        }
        if (value == 2) {
            return true;
        }
        if (value % 2 == 0) {
            return false;
        }
        int limit = (int) Math.sqrt(value);
        for (int i = 3; i <= limit; i += 2) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }

    static int[] filterIntArray(int[] input, Predicate<Integer> predicate) {
        int[] temp = new int[input.length];
        int index = 0;
        for (int value : input) {
            if (predicate.test(value)) {
                temp[index++] = value;
            }
        }
        return Arrays.copyOf(temp, index);
    }

    static Student[] filterStudents(Student[] students, Predicate<Student> predicate) {
        Student[] temp = new Student[students.length];
        int index = 0;
        for (Student student : students) {
            if (predicate.test(student)) {
                temp[index++] = student;
            }
        }
        return Arrays.copyOf(temp, index);
    }

    static <T> List<T> filterByTwoPredicates(T[] input, Predicate<T> first, Predicate<T> second) {
        List<T> result = new ArrayList<>();
        for (T element : input) {
            if (first.test(element) && second.test(element)) {
                result.add(element);
            }
        }
        return result;
    }

    static <T> void forEach(T[] input, Consumer<T> consumer) {
        for (T element : input) {
            consumer.accept(element);
        }
    }

    static void applyIf(int[] input, Predicate<Integer> predicate, Consumer<Integer> consumer) {
        for (int value : input) {
            if (predicate.test(value)) {
                consumer.accept(value);
            }
        }
    }

    static int[] processArray(int[] input, Function<Integer, Integer> function) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = function.apply(input[i]);
        }
        return result;
    }

    static String[] stringify(int[] input, Function<Integer, String> function) {
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = function.apply(input[i]);
        }
        return result;
    }

    static class Student {
        private final String name;
        private final String surname;
        private final String group;
        private final int[] marks;

        Student(String name, String surname, String group, int[] marks) {
            this.name = name;
            this.surname = surname;
            this.group = group;
            this.marks = marks;
        }

        String getName() {
            return name;
        }

        String getSurname() {
            return surname;
        }

        String getGroup() {
            return group;
        }

        int[] getMarks() {
            return marks;
        }

        String getFullName() {
            return surname + " " + name;
        }
    }
}
