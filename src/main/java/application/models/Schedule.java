package application.models;

public class Schedule {
    private int id;
    private String day;
    private String startTime;
    private String endTime;
    private int teacher;
    private int classroomNr;


    public Schedule(int id, String day, String startTime, String endTime, int teacher, int classroomNr) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacher = teacher;
        this.classroomNr = classroomNr;
    }

//    public Schedule(int id, String fullName, String email, String personalNr) {
//        this.id = id;
//        this.fullName = fullName;
//        this.email = email;
//        this.personalNr = personalNr;
//    }


    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getTeacher() {
        return teacher;
    }

    public int getClassroomNr() {
        return classroomNr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTeacher(int teacher) {
        this.teacher = teacher;
    }

    public void setClassroomNr(int classroomNr) {
        this.classroomNr = classroomNr;
    }
}
