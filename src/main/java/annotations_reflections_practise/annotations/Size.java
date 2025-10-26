package annotations_reflections_practise.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

  int min() default 0;

  int max() default 1_000_000_000;

  String message() default "Строка должна быть в пределах длины от {min} до {max}";
}
