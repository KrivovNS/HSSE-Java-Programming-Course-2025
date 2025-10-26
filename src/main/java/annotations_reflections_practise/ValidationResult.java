package annotations_reflections_practise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {
  private boolean isValid;
  private List<String> errors;

  public ValidationResult() {
    isValid = true;
    errors = new ArrayList<>();
  }

  public boolean isValid() {
    return isValid;
  }

  public void addError(String error) {
    errors.add(error);
    isValid = false;
  }

  public ArrayList<String> getErrors() {
    return new ArrayList<>(errors);
  }

  @Override
  public String toString() {
    if (isValid) {
      return "ValidationResult{isValid=true}";
    } else {
      return "ValidationResult{isValid=false; " + errors + "}";
    }
  }
}