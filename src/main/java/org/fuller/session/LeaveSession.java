package org.fuller.session;

import lombok.Data;
import org.fuller.entity.Role;
import org.fuller.entity.Student;
import org.fuller.entity.Teacher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class LeaveSession {
    public static int USER_TYPE_TEACHER = 1;
    public static int USER_TYPE_STUDENT = 2;

    public static final int DATA_AREA_PERSON = 1;
    public static final int DATA_AREA_GRADE = 2;
    public static final int DATA_AREA_COLLEGE = 3;
    public static final int DATA_AREA_SYSTEM = 4;

    private Set<String> permissions = new HashSet<>();
    private int userType;
    private Student student;
    private Teacher teacher;
    private List<Role> roles = new ArrayList<>();

    private String manageGrades;
    private int collegeId;
    private boolean isHeadTeacher;
    private boolean isFdy;
    private boolean isLander;
    private int dataArea = DATA_AREA_PERSON;

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}