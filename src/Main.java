import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        int numberOfStudents = 0;
        boolean flag = true;

        do{
            System.out.print("Enter the number of students to input: ");
            int studentNumber = scanner.nextInt();

            if(studentNumber >= 11 & studentNumber <= 15){
                numberOfStudents = studentNumber;
                scanner.nextLine();  // Consume newline
                flag = true;
            }else{
                System.out.println("Number must be between 11 and 15");
                flag = false;
            }

        }while(!flag);


        for (int i = 0; i < numberOfStudents; i++) {
            while (true) {
                try {
                    System.out.print("Enter student type (good or Good/normal or Normal): ");
                    String studentType = scanner.nextLine();

                    if (!studentType.equalsIgnoreCase("good") && !studentType.equalsIgnoreCase("normal")) {
                        throw new InvalidStudentTypeException("Student type must be either 'good' or 'normal'.");
                    }

                    System.out.print("Enter full name: ");
                    String fullName = scanner.nextLine();

                    if (fullName.length() < 10 || fullName.length() > 50) {
                        throw new InvalidFullNameException("Full name must be between 10 and 50 characters.");
                    }

                    System.out.print("Enter grade level: ");
                    String gradeLevel = scanner.nextLine();

                    System.out.print("Enter university name: ");
                    String universityName = scanner.nextLine();

                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();

                    String phoneRegex = "^(090|098|091|031|035|038)\\d{7}$";
                    if (!Pattern.matches(phoneRegex, phoneNumber)) {
                        throw new InvalidPhoneNumberException("Phone number must be 10 digits long and start with one of the following prefixes: 090, 098, 091, 031, 035, or 038.");
                    }

                    System.out.print("Enter date of birth (dd/MM/yyyy): ");
                    String dobString = scanner.nextLine();
                    LocalDate dob;
                    try {
                        dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } catch (DateTimeParseException e) {
                        throw new InvalidDOBException("Date of birth must be in the format dd/MM/yyyy.");
                    }

                    System.out.print("Enter sex: ");
                    String sex = scanner.nextLine();

                    if (studentType.equalsIgnoreCase("good")) {
                        System.out.print("Enter GPA: ");
                        double gpa = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline

                        System.out.print("Enter best reward name: ");
                        String bestRewardName = scanner.nextLine();

                        students.add(new GoodStudent(fullName, gradeLevel, universityName, phoneNumber, dob, sex, gpa, bestRewardName));
                    } else if (studentType.equalsIgnoreCase("normal")) {
                        System.out.print("Enter English score: ");
                        String englishScore = scanner.nextLine();

                        System.out.print("Enter entry test score: ");
                        String entryTestScore = scanner.nextLine();

                        students.add(new NormalStudent(fullName, gradeLevel, universityName, phoneNumber, dob, sex, englishScore, entryTestScore));
                    }
                    break;  // Exit the loop if no exception
                } catch (InvalidStudentTypeException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidFullNameException | InvalidDOBException | InvalidPhoneNumberException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Input files have unknown errors !!!");
                }
            }
        }

        // Hiển thị thông tin sinh viên
        for (Student student : students) {
            student.ShowMyInfor();
            System.out.println();
        }

        // Sắp xếp sinh viên theo yêu cầu
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                int nameCompare = s2.fullName.compareTo(s1.fullName);
                if (nameCompare != 0) {
                    return nameCompare;
                }
                return s1.phoneNumber.compareTo(s2.phoneNumber);
            }
        });

        System.out.println("Sorted Students:");
        for (Student student : students) {
            student.ShowMyInfor();
            System.out.println();
        }

        // Tuyển chọn ứng viên trúng tuyển vào công ty
        System.out.print("Enter the number of students to recruit: ");
        int numberOfStudentsToRecruit = scanner.nextInt();

        List<Student> recruitedStudents = recruitStudents(students, numberOfStudentsToRecruit);

        System.out.println("Recruited Students:");
        for (Student student : recruitedStudents) {
            student.ShowMyInfor();
            System.out.println();
        }

        scanner.close();
    }

    public static List<Student> recruitStudents(List<Student> students, int numberOfStudentsToRecruit) {
        List<GoodStudent> goodStudents = new ArrayList<>();
        List<NormalStudent> normalStudents = new ArrayList<>();

        for (Student student : students) {
            if (student instanceof GoodStudent) {
                goodStudents.add((GoodStudent) student);
            } else if (student instanceof NormalStudent) {
                normalStudents.add((NormalStudent) student);
            }
        }

        // Sắp xếp sinh viên khá giỏi theo điểm GPA và tên
        Collections.sort(goodStudents, new Comparator<GoodStudent>() {
            @Override
            public int compare(GoodStudent s1, GoodStudent s2) {
                int gpaCompare = Double.compare(s2.gpa, s1.gpa);
                if (gpaCompare != 0) {
                    return gpaCompare;
                }
                return s1.fullName.compareTo(s2.fullName);
            }
        });

        // Sắp xếp sinh viên trung bình theo điểm thi đầu vào và điểm TOEIC
        Collections.sort(normalStudents, new Comparator<NormalStudent>() {
            @Override
            public int compare(NormalStudent s1, NormalStudent s2) {
                int entryTestCompare = s2.entryTestScore.compareTo(s1.entryTestScore);
                if (entryTestCompare != 0) {
                    return entryTestCompare;
                }
                int englishScoreCompare = s2.englishScore.compareTo(s1.englishScore);
                if (englishScoreCompare != 0) {
                    return englishScoreCompare;
                }
                return s1.fullName.compareTo(s2.fullName);
            }
        });

        List<Student> recruitedStudents = new ArrayList<>();
        int goodStudentCount = Math.min(goodStudents.size(), numberOfStudentsToRecruit);
        recruitedStudents.addAll(goodStudents.subList(0, goodStudentCount));

        int remainingSlots = numberOfStudentsToRecruit - goodStudentCount;
        if (remainingSlots > 0) {
            recruitedStudents.addAll(normalStudents.subList(0, Math.min(normalStudents.size(), remainingSlots)));
        }

        return recruitedStudents;
    }
}
