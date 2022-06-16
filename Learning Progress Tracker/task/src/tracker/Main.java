package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final HashMap<Integer, Student> students = new HashMap<>();
    private static final HashSet<String> emails = new HashSet<>();
    private static int idInit = 10000;

    private final HashMap<String, Integer> resultStudents = new HashMap<>();
    private final HashMap<String, Integer> resultActivity = new HashMap<>();
    private final HashMap<String, Float> resultSumPoints = new HashMap<>();

    private final HashMap<String, Integer> maxes = new HashMap<>();

    public static void main(String[] args) {
        new Main().mainStart();
    }

    public void mainStart() {
        resultStudents.put("Java", 0);
        resultStudents.put("DSA", 0);
        resultStudents.put("Databases", 0);
        resultStudents.put("Spring", 0);

        resultActivity.put("Java", 0);
        resultActivity.put("DSA", 0);
        resultActivity.put("Databases", 0);
        resultActivity.put("Spring", 0);

        resultSumPoints.put("Java", 0f);
        resultSumPoints.put("DSA", 0f);
        resultSumPoints.put("Databases", 0f);
        resultSumPoints.put("Spring", 0f);

        maxes.put("Java", 600);
        maxes.put("DSA", 400);
        maxes.put("Databases", 480);
        maxes.put("Spring", 550);

        System.out.println("Learning Progress Tracker");

        boolean end = false;
        while (!end) {
            end = checkInput(scanner.nextLine().strip());
        }

        System.out.println("Bye!");
    }

    private boolean checkInput(String input) {
        boolean end = false;
        if (input.isBlank()) {
            System.out.println("No input");
        } else if ("add students".equals(input)) {
            addStudents();
        } else if ("list".equals(input)) {
            printList();
        } else if ("add points".equals(input)) {
            addPoints();
        } else if ("find".equals(input)) {
            findById();
        } else if ("statistics".equals(input)) {
            showStatistics();
        } else if ("notify".equals(input)) {
            notifyStudents();
        } else if ("back".equals(input)) {
            System.out.println("Enter 'exit' to exit the program");
        } else if (!"exit".equals(input)) {
            System.out.println("Unknown command!");
        } else {
            end = true;
        }

        return end;
    }

    private static void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");

        int i = 0;
        String input;
        while (true) {
            input = scanner.nextLine().strip();

            if ("back".equals(input)) {
                System.out.println("Total " + i + " students have been added.");
                break;
            }

            String[] credentials = input.split(" ");

            if (credentials.length < 3) {
                System.out.println("Incorrect credentials.");
            } else if (checkCredentials(input)) {
                i++;
                System.out.println("Student has been added.");
                String surName = input.substring(input.indexOf(' ') + 1, input.lastIndexOf(' '));
                Student student = new Student(idInit, credentials[0], surName, credentials[credentials.length - 1]);
                students.put(idInit, student);
                emails.add(credentials[credentials.length - 1]);
                idInit++;
            }
        }
    }

    private static void printList() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students");
            for (Integer id : students.keySet()) {
                System.out.println(id);
            }
        }
    }

    private void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        String input;
        while (true) {
            input = scanner.nextLine().strip();

            if ("back".equals(input)) {
                break;
            }

            ArrayList<String> points = new ArrayList<>(Arrays.asList(input.split(" ")));
            String idString = points.get(0);

            boolean isCorrect = true;
            if (input.split(" ").length != 5) {
                isCorrect = false;
                System.out.println("Incorrect points format.");
            } else {
                for (int i = 1; i < points.size(); i++) {
                    if (!points.get(i).matches("\\d+")) {
                        isCorrect = false;
                        System.out.println("Incorrect points format.");
                        break;
                    }
                }
            }

            if (isCorrect && (!idString.matches("\\d+") || !students.containsKey(Integer.parseInt(idString)))) {
                isCorrect = false;
                System.out.println("No student is found for id=" + idString + ".");
            }

            if (isCorrect) {
                points.remove(0);
                ArrayList<Integer> pointsInt = (ArrayList<Integer>) points.stream().map(Integer::parseInt).collect(Collectors.toList());

                Student student = students.get(Integer.parseInt(idString));
                ArrayList<Integer> oldPoints = (ArrayList<Integer>) student.getPoints();

                // first time
                if (oldPoints.get(0) == 0 && oldPoints.get(1) == 0 && oldPoints.get(2) == 0 && oldPoints.get(3) == 0) {
                    plusOne(pointsInt, resultStudents);
                }

                // every time
                plusOne(pointsInt, resultActivity);
                plusPoints(pointsInt, resultSumPoints);

                for (int i = 0; i < points.size(); i++) {
                    oldPoints.set(i, oldPoints.get(i) + pointsInt.get(i));
                }
                student.setPoints(oldPoints);
                System.out.println("Points updated.");
            }
        }
    }

    private static void plusOne(ArrayList<Integer> points, HashMap<String, Integer> map) {
        if (points.get(0) != 0) {
            map.put("Java", map.get("Java") + 1);
        }
        if (points.get(1) != 0) {
            map.put("DSA", map.get("DSA") + 1);
        }
        if (points.get(2) != 0) {
            map.put("Databases", map.get("Databases") + 1);
        }
        if (points.get(3) != 0) {
            map.put("Spring", map.get("Spring") + 1);
        }
    }

    private static void plusPoints(ArrayList<Integer> points, HashMap<String, Float> map) {
        map.put("Java", map.get("Java") + points.get(0));

        map.put("DSA", map.get("DSA") + points.get(1));

        map.put("Databases", map.get("Databases") + points.get(2));

        map.put("Spring", map.get("Spring") + points.get(3));
    }

    private static void findById() {
        System.out.println("Enter an id or 'back' to return:");
        String input;
        while (true) {
            input = scanner.nextLine().strip();

            if ("back".equals(input)) {
                break;
            }

            if (!input.matches("\\d+") || !students.containsKey(Integer.parseInt(input))) {
                System.out.println("No student is found for id=" + input + ".");
            } else {

                Integer id = Integer.parseInt(input);
                List<Integer> points = students.get(id).getPoints();

                System.out.println(id + " points: Java=" + points.get(0) + "; DSA=" + points.get(1) +
                        "; Databases=" + points.get(2) + "; Spring=" + points.get(3));
            }
        }
    }

    private void showStatistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");

        if (students.isEmpty()) {
            System.out.println("Most popular: n/a");
            System.out.println("Least popular: n/a");
            System.out.println("Highest activity: n/a");
            System.out.println("Lowest activity: n/a");
            System.out.println("Easiest course: n/a");
            System.out.println("Hardest course: n/a");
        } else {
            LinkedHashMap<String, Integer> sortedResultStudents = resultStudents.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));

            LinkedHashMap<String, Integer> sortedResultActivity = resultActivity.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));

            resultSumPoints.replaceAll((k, v) -> resultSumPoints.get(k) / resultActivity.get(k));

            LinkedHashMap<String, Float> sortedAvgPoints = resultSumPoints.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));

            String[] keysStudents = sortedResultStudents.keySet().toArray(new String[0]);
            String[] keysActivity = sortedResultActivity.keySet().toArray(new String[0]);
            String[] keysAvgPoints = sortedAvgPoints.keySet().toArray(new String[0]);


            System.out.println("Most popular: " + printOutput(sortedResultStudents, keysStudents[0]));
            if ((int) sortedResultStudents.get(keysStudents[0]) != sortedResultStudents.get(keysStudents[3])) {
                System.out.println("Least popular: " + printOutput(sortedResultStudents, keysStudents[3]));
            } else {
                System.out.println("Least popular: n/a");
            }

            System.out.println("Highest activity: " + printOutput(sortedResultActivity, keysActivity[0]));
            if ((int) sortedResultActivity.get(keysActivity[0]) != sortedResultActivity.get(keysActivity[3])) {
                System.out.println("Lowest activity: " + printOutput(sortedResultActivity, keysActivity[3]));
            } else {
                System.out.println("Lowest activity: n/a");
            }

            System.out.println("Easiest course: " + printOutput(sortedAvgPoints, keysAvgPoints[3]));
            if ((float) sortedAvgPoints.get(keysAvgPoints[3]) != sortedAvgPoints.get(keysAvgPoints[0])) {
                System.out.println("Hardest course: " + printOutput(sortedAvgPoints, keysAvgPoints[0]));
            } else {
                System.out.println("Hardest course: n/a");
            }
        }


        String course;
        while (true) {

            course = scanner.nextLine().strip();

            if ("back".equals(course)) {
                break;
            }

            boolean isCourse = false;
            for (String key : maxes.keySet()) {
                if (key.equalsIgnoreCase(course)) {
                    System.out.println(key);
                    course = key;
                    isCourse = true;
                    break;
                }
            }

            if (!isCourse) {
                System.out.println("Unknown course.");
            } else {
                System.out.println("id    points    completed");

                HashMap<Integer, Integer> studentsByCourse = new HashMap<>();

                for (Map.Entry<Integer, Student> student : students.entrySet()) {
                    if (student.getValue().getPointByCourse(course) > 0) {
                        studentsByCourse.put(student.getKey(), student.getValue().getPointByCourse(course));
                    }
                }

                LinkedHashMap<Integer, Integer> sortedStudents = studentsByCourse.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));

                for (Map.Entry<Integer, Integer> student : sortedStudents.entrySet()) {
                    int id = student.getKey();
                    int points = sortedStudents.get(id);
                    BigDecimal bigDecimal = BigDecimal.valueOf((float) points / maxes.get(course) * 100);
                    bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);

                    System.out.println(id + " " + points + " ".repeat(Math.max(0, 10 - String.valueOf(points).length()))
                            + bigDecimal + "%");
                }
            }
        }
    }

    private void notifyStudents(){
        int i = 0;
        boolean onlyOnce;
        for (Map.Entry<Integer, Student> student : students.entrySet()) {
            Student studentValue = student.getValue();
            onlyOnce = true;

            for (Map.Entry<String, Integer> max : maxes.entrySet()) {
                String course = max.getKey();
                if ((int) studentValue.getPointByCourse(course) == maxes.get(course)
                        && !studentValue.getNotifiersByCourse(course)) {

                    System.out.println("To: " + studentValue.getEmail());
                    System.out.println("Re: Your Learning Progress");
                    System.out.println("Hello, " + studentValue.getName() + " " + studentValue.getSurName() + "! " +
                            "You have accomplished our " + course +" course!");

                    studentValue.setNotifiersByCourse(course);

                    if (onlyOnce) {
                        onlyOnce = false;
                        i++;
                    }
                }
            }
        }

        System.out.println("Total " + i + " students have been notified.");
    }

    private static boolean checkCredentials(String input) {
        String[] credentials = input.toLowerCase().split(" ");

        boolean isCorrect = true;
        boolean isMatches = true;
        for (int i = 0; i < credentials.length - 1; i++) {

            if (credentials[i].matches("^['-].*") || credentials[i].matches(".*['-]$")
                    || credentials[i].matches(".*(-'|'-|--|'').*")) {
                isMatches = false;
            }

            if (credentials[i].length() < 2 || !isMatches || !credentials[i].matches("[a-z'-]+")) {
                isCorrect = false;

                if (i == 0) {
                    System.out.println("Incorrect first name");
                } else {
                    System.out.println("Incorrect last name");
                }
                break;
            }
        }

        String email = credentials[credentials.length - 1];

        if (isCorrect && (!email.matches(".+@.+\\..+") || email.matches(".*@.*@.*"))) {
            isCorrect = false;
            System.out.println("Incorrect email");
        } else if (emails.contains(email)) {
            isCorrect = false;
            System.out.println("This email is already taken.");
        }

        return isCorrect;
    }

    private String printOutput(HashMap<String, ?> map, String keyEdge) {
        StringBuilder stringBuilder = new StringBuilder().append(keyEdge);

        for (Map.Entry<String, ?> student : map.entrySet()) {
            String key = student.getKey();

            if (map.get(key).equals(map.get(keyEdge)) && !key.equals(keyEdge)) {
                stringBuilder.append(" ").append(key);
            }
        }

        return stringBuilder.toString();
    }
}
