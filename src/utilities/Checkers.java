package utilities;

public class Checkers {

    public boolean checkName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean checkNumber(String number) {
        return number == null || number.equals("") || number.matches("^[0-9]{8}$");
    }


}
