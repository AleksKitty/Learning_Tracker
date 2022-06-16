package tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student {
    private final int id;
    private final String name;
    private final String surName;
    private final String email;

    private final ArrayList<Boolean> notifiers = new ArrayList<>(Arrays.asList(false, false, false, false));

    private ArrayList<Integer> points = new ArrayList<>(Arrays.asList(0, 0, 0, 0));

    public Student(int id, String name, String surName, String email) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.email = email;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public boolean getNotifiersByCourse(String course) {
        if ("java".equalsIgnoreCase(course)) {
            return notifiers.get(0);
        } else if ("dsa".equalsIgnoreCase(course)) {
            return notifiers.get(1);
        } else if ("databases".equalsIgnoreCase(course)) {
            return notifiers.get(2);
        } else if ("spring".equalsIgnoreCase(course)) {
            return notifiers.get(3);
        } else {
            return true;
        }
    }

    public void setNotifiersByCourse(String course) {
        if ("java".equalsIgnoreCase(course)) {
            notifiers.set(0, true);
        } else if ("dsa".equalsIgnoreCase(course)) {
            notifiers.set(1, true);
        } else if ("databases".equalsIgnoreCase(course)) {
            notifiers.set(2, true);
        } else if ("spring".equalsIgnoreCase(course)) {
            notifiers.set(3, true);
        }
    }

    public Integer getPointByCourse(String course) {
        if ("java".equalsIgnoreCase(course)) {
            return points.get(0);
        } else if ("dsa".equalsIgnoreCase(course)) {
            return points.get(1);
        } else if ("databases".equalsIgnoreCase(course)) {
            return points.get(2);
        } else if ("spring".equalsIgnoreCase(course)) {
            return points.get(3);
        } else {
            return 0;
        }
    }

    public void setPoints(List<Integer> points) {
        this.points = (ArrayList<Integer>) points;
    }
}
