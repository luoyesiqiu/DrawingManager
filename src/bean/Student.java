package bean;

public class Student {
	public Student(String studentNumber, String password, String studentName, boolean studentGender,
			String studentGrade, String studentMajor, String studentBirth, String studentPhoto) {
		this.studentNumber = studentNumber;
		this.password = password;
		this.studentName = studentName;
		this.studentGender = studentGender;
		this.studentGrade = studentGrade;
		this.studentMajor = studentMajor;
		this.studentBirth = studentBirth;
		this.studentPhoto = studentPhoto;
	}
	
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public boolean isStudentGender() {
		return studentGender;
	}
	public void setStudentGender(boolean studentGender) {
		this.studentGender = studentGender;
	}
	public String getStudentGrade() {
		return studentGrade;
	}
	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}
	public String getStudentMajor() {
		return studentMajor;
	}
	public void setStudentMajor(String studentMajor) {
		this.studentMajor = studentMajor;
	}
	public String getStudentBirth() {
		return studentBirth;
	}
	public void setStudentBirth(String studentBirth) {
		this.studentBirth = studentBirth;
	}
	public String getStudentPhoto() {
		return studentPhoto;
	}
	public void setStudentPhoto(String studentPhoto) {
		this.studentPhoto = studentPhoto;
	}

	@Override
	public String toString() {
		return "Student [studentNumber=" + studentNumber + ", password=" + password + ", studentName=" + studentName
				+ ", studentGender=" + studentGender + ", studentGrade=" + studentGrade + ", studentMajor="
				+ studentMajor + ", studentBirth=" + studentBirth + ", studentPhoto=" + studentPhoto + "]";
	}

	String studentNumber;
	String password;
	String studentName;boolean studentGender;
	String studentGrade;
	String studentMajor;
	String studentBirth;
	String studentPhoto;
}
