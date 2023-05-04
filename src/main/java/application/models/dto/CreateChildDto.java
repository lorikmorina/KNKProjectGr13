package application.models.dto;

public class CreateChildDto {
    private int child_id;
    private String childsName;
    private int parent_id;
    private int child_age;
    private String teacher;
    private int classNr;
    private String contactInfo;
    private String medicalInfo;

    public CreateChildDto(int child_id, String childsName, int parent_id, int child_age, String teacher, int classNr, String contactInfo, String medicalInfo) {
        this.child_id = child_id;
        this.childsName = childsName;
        this.parent_id = parent_id;
        this.child_age = child_age;
        this.teacher = teacher;
        this.classNr = classNr;
        this.contactInfo = contactInfo;
        this.medicalInfo = medicalInfo;
    }

    public int getChild_id() {
        return child_id;
    }

    public String getChildsName() {
        return childsName;
    }

    public int getParent_id() {
        return parent_id;
    }

    public int getChild_age() {
        return child_age;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getClassNr() {
        return classNr;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getMedicalInfo() {
        return medicalInfo;
    }

}
