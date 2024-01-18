package academy.mischok.persondatabase.command.method;

import java.lang.reflect.Method;

public record MethodDetails(Method method, boolean needsScanner) {

}