package collections_practise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentUtils {

  public static List<Student> findStudentsByGradeRange(Map<Integer, Student> map,
      double minGrade, double maxGrade) {
    List<Student> result = new ArrayList<>();
    for (Student student : map.values()) {
      if (student.getGrade() >= minGrade && student.getGrade() <= maxGrade) {
        result.add(student);
      }
    }
    return result;
  }

  public static List<Student> getTopNStudents(TreeMap<Integer, Student> treeMap, int n) {
    List<Student> result = new ArrayList<>();
    int count = 0;

    for (Student student : treeMap.values()) {
      if (count >= n) {
        break;
      }
      result.add(student);
      count++;
    }
    return result;
  }
}
