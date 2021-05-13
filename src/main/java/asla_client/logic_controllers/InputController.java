package asla_client.logic_controllers;

import java.text.MessageFormat;
import java.util.regex.Pattern;

/**
 * @author Sebastian Norén <s.norén@gmail.com>
 */
public class InputController {

    public boolean passwordMatch(String password, String matchingPassword) {
        if (password.isEmpty() || matchingPassword.isEmpty()) {
            return false;
        }
        return password.equals(matchingPassword);
    }

    public boolean validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (email.isEmpty()) {
            return false;
        }
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public boolean validAccountName(String accountName, String specialCharactersAllowed) {
        String accountRegex = MessageFormat.format("^[a-zA-Z0-9{0}-]*", specialCharactersAllowed);
        if (accountName.isEmpty()) {
            return false;
        }
        Pattern pat = Pattern.compile(accountRegex);
        return pat.matcher(accountName).matches();
    }

    public String trimInput(String inputToTrim) {
        return inputToTrim.trim();
    }

    public boolean validPassword(String password, int passLenght, boolean includeNumb, boolean includeUpperCase, boolean includeSpecChar) {
        if (passLenght <= 0) {
            passLenght = 0;
        }
        if (password.isEmpty() || password.length() < passLenght) {
            return false;
        }
        if (includeUpperCase) {
            if (!password.matches(".*[A-Z].*")) {
                return false;
            }
        }
        if (includeNumb) {
            if (!password.matches(".*[0-9].*")) {
                return false;
            }
        }
        if (includeSpecChar) {
            if (!password.matches(".*[!@#$%^&*_].*")) {
                return false;
            }
        }
        return true;
    }

    public boolean CheckInputsIsValid(boolean...inputs) {
        for (boolean checks: inputs) {
            if (!checks){
                return false;
            }
        }
        return true;
    }

    public boolean CheckInputsIsValid(String...inputs) {
        for (String checks: inputs) {
            if (checks.isEmpty()){
                return false;
            }
        }
        return true;
    }

}
