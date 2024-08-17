import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class GoodStudent extends Student {
    double gpa;
    private String bestRewardName;

    public GoodStudent(String fullName, String gradeLevel, String universityName, String phoneNumber, LocalDate dob, String sex, double gpa, String bestRewardName) throws InvalidFullNameException, InvalidDOBException, InvalidPhoneNumberException {
        super(fullName, gradeLevel, universityName, phoneNumber, dob, sex);
        this.gpa = gpa;
        this.bestRewardName = bestRewardName;
    }

    @Override
    public void ShowMyInfor() {
        System.out.println("Full Name: " + fullName);
        System.out.println("Date of Birth: " + dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Sex: " + sex);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("University Name: " + universityName);
        System.out.println("Grade Level: " + gradeLevel);
        System.out.println("GPA: " + gpa);
        System.out.println("Best Reward Name: " + bestRewardName);
    }
}
