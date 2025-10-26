package annotations_reflections_practise.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

  long min() default Long.MIN_VALUE;

  long max() default Long.MAX_VALUE;

  String message() default "Число должно быть в диапазоне от {min} до {max}";
}
