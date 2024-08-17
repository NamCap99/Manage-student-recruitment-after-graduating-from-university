import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class NormalStudent extends Student {
    String englishScore;
    String entryTestScore;

    public NormalStudent(String fullName, String gradeLevel, String universityName, String phoneNumber, LocalDate dob, String sex, String englishScore, String entryTestScore) throws InvalidFullNameException, InvalidDOBException, InvalidPhoneNumberException {
        super(fullName, gradeLevel, universityName, phoneNumber, dob, sex);
        this.englishScore = englishScore;
        this.entryTestScore = entryTestScore;
    }

    @Override
    public void ShowMyInfor() {
        System.out.println("Full Name: " + fullName);
        System.out.println("Date of Birth: " + dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("Sex: " + sex);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("University Name: " + universityName);
        System.out.println("Grade Level: " + gradeLevel);
        System.out.println("English Score: " + englishScore);
        System.out.println("Entry Test Score: " + entryTestScore);
    }
}
