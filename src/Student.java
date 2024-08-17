import java.time.LocalDate;
import java.util.regex.Pattern;

abstract class Student {
    protected String fullName;
    protected LocalDate dob;
    protected String sex;
    protected String phoneNumber;
    protected String universityName;
    protected String gradeLevel;

    public Student(String fullName, String gradeLevel, String universityName, String phoneNumber, LocalDate dob, String sex) throws InvalidFullNameException, InvalidDOBException, InvalidPhoneNumberException {
        if (fullName.length() < 10 || fullName.length() > 50) {
            throw new InvalidFullNameException("Full name must be between 10 and 50 characters.");
        }

        String phoneRegex = "^(090|098|091|031|035|038)\\d{7}$";
        if (!Pattern.matches(phoneRegex, phoneNumber)) {
            throw new InvalidPhoneNumberException("Phone number must be 10 digits long and start with one of the following prefixes: 090, 098, 091, 031, 035, or 038.");
        }

        this.fullName = fullName;
        this.gradeLevel = gradeLevel;
        this.universityName = universityName;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.sex = sex;
    }

    public abstract void ShowMyInfor();
}
