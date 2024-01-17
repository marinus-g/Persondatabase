package academy.mischok.persondatabase.validator;

public class NameValidator implements StringValidator{

    @Override
    public boolean isValidString(String string) {
        return string.matches("^([A-Z])([a-z]{1,99})$");
    }
}