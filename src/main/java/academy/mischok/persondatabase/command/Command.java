package academy.mischok.persondatabase.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    String name();

    String description();

    String[] aliases() default {};

    String usage() default "";
}