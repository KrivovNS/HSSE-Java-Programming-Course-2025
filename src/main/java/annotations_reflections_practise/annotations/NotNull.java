package annotations_reflections_practise.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

  String message() default "Поле не должно быть null";
}
