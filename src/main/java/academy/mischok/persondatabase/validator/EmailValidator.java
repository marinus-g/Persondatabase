package academy.mischok.persondatabase.validator;

public class EmailValidator implements StringValidator {
    @Override
    public boolean isValidString(String string) {
        return string.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z.-]+\\.[a-zA-Z]{2,6}$");
    }
}