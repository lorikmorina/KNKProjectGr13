package application.models.dto;

public class CreateScheduleDto {
    private String day;
    private String startTime;
    private String endTime;
    private int teacher;
    private int classroomNr;

    public CreateScheduleDto(String day, String startTime, String endTime, int teacher, int classroomNr) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacher = teacher;
        this.classroomNr = classroomNr;
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
}
