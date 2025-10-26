package annotations_reflections_practise.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

  String message() default "Строка должна быть валидным email";
}
