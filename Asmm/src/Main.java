import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student {
    private String id;
    private String name;
    private double marks;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public String getRank() {
        if (marks > 10.0) {
            return "Invalid marks: must be 10 or less";
        }
        if (marks < 5.0) return "Fail";
        if (marks < 6.5) return "Medium";
        if (marks < 7.5) return "Good";
        if (marks < 9.0) return "Very Good";
        return "Excellent";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Rank: " + getRank();
    }
}

class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void addStudent() {
        System.out.println("Enter Student ID:");
        String id = scanner.nextLine();

        // Check if a student with the same ID already exists
        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.println("Error: A student with this ID already exists. Please try a different ID.");
                return;
            }
        }
        System.out.println("Enter Student Name:");
        String name = scanner.nextLine();
        double marks = 0;
        while (true) {
            try {
                System.out.println("Enter Student Marks (0-10):");
                marks = scanner.nextDouble();
                if (marks < 0 || marks > 10) {
                    System.out.println("Error: Marks must be between 0 and 10. Please re-enter.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number for marks.");
                scanner.next(); // Clear invalid input
            }
        }

        scanner.nextLine(); // consume newline
        students.add(new Student(id, name, marks));
        System.out.println("Student Added!");
    }


    public void editStudent() {
        System.out.println("Enter Student ID to Edit:");
        String id = scanner.nextLine();
        boolean found = false;
        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.println("Enter New Name:");
                student.setName(scanner.nextLine());
                double newMarks = 0;
                while (true) {
                    try {
                        System.out.println("Enter New Marks (0-10):");
                        newMarks = scanner.nextDouble();
                        if (newMarks < 0 || newMarks > 10) {
                            System.out.println("Error: Marks must be between 0 and 10. Please re-enter.");
                        } else {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Please enter a valid number for marks.");
                        scanner.next(); // Clear invalid input
                    }
                }
                student.setMarks(newMarks);
                scanner.nextLine();
                System.out.println("Student Updated!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student Not Found!");
        }
    }

    public void deleteStudent() {
        System.out.println("Enter Student ID to Delete:");
        String id = scanner.nextLine();
        if (students.removeIf(student -> student.getId().equals(id))) {
            System.out.println("Student Removed!");
        } else {
            System.out.println("Student Not Found!");
        }
    }

    public void searchStudent() {
        System.out.println("Enter Student ID to Search:");
        String id = scanner.nextLine();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.println(student);
                return;
            }
        }
        System.out.println("Student Not Found!");
    }

    public void sortStudents() {
        quickSort(students, 0, students.size() - 1);
        System.out.println("Students Sorted by Marks!");
        printAllStudents();
    }
    public void sortStudents1() {
        int n = students.size(); // Lấy kích thước của danh sách sinh viên
        //        // Vòng lặp để thực hiện nhiều lần sắp xếp
        for (int outer = 0; outer < n - 1; outer++) {
            // Biến swapped để kiểm tra có sự hoán đổi hay không
            boolean swapped = false;
            // Vòng lặp so sánh từng cặp sinh viên
            for (int inner = 0; inner < n - outer - 1; inner++) {
                // So sánh điểm của sinh viên hiện tại với sinh viên tiếp theo
                if (students.get(inner).getMarks() < students.get(inner + 1).getMarks()) {
                    // Hoán đổi vị trí nếu cần
                    Student temp = students.get(inner);
                    students.set(inner, students.get(inner + 1));
                    students.set(inner + 1, temp);
                    swapped = true; // Đặt swapped là true nếu có sự hoán đổi
                }
            }
            // Nếu không có sự hoán đổi nào, danh sách đã được sắp xếp
            if (!swapped) {
                break;
            }
        }
        System.out.println("Students Sorted by Marks using Bubble Sort!");
        printAllStudents(); // In danh sách sinh viên đã sắp xếp
    }

    private void quickSort(ArrayList<Student> arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    private int partition(ArrayList<Student> arr, int low, int high) {
        double pivot = arr.get(high).getMarks();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr.get(j).getMarks() > pivot) {
                i++;
                Student temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }
        Student temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);
        return i + 1;
    }


    public void printAllStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Sort Students");
            System.out.println("6. Print All Students");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = 0;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
                continue; // Prompt for choice again
            }

            switch (choice) {
                case 1:
                    manager.addStudent();
                    break;
                case 2:
                    manager.editStudent();
                    break;
                case 3:
                    manager.deleteStudent();
                    break;
                case 4:
                    manager.searchStudent();
                    break;
                case 5:
                    manager.sortStudents();
                    break;
                case 6:
                    manager.printAllStudents();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    scanner.close(); // Close the scanner to release resources
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }
}
