package fr.afpa.tools;

public class ContactChecker {

    public static boolean isNomPrenomValid(String nom){
        boolean res = false;
        if(nom.matches("^[A-Za-zÀ-ÿ\\-\\s]{2,30}$")) { // 2 to 30 characters, letters, hyphens, and spaces
            res = true;
        }
        return res;
    }

    public static boolean isPseudoValid(String pseudo) {
        boolean res = false;
        if (pseudo.matches("^[a-zA-Z0-9_-]{3,15}$")) { // 3 to 15 characters, alphanumeric, underscores, or hyphens
            res = true;
        }
        return res;
    }

    public static boolean isEmailValid(String email) {
        boolean res = false;
        if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            res = true;
        }
        return res;
    }

    public static boolean isAdresse(String adresse) {
        boolean res = false;
        if (adresse.matches("^[A-Za-zÀ-ÿ0-9 ,'-]{5,100}$")) {
            res = true;
        }
        return res;
    }

    public static boolean isNumero(String numero) {
        boolean res = false;
        if (numero.matches("^\\+?[0-9]{10,15}$")) {
            res = true;
        }
        return res;
    }

    public static boolean isDateValid(String date) {
        boolean res = false;
        if (date.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) { // Format: dd/mm/yyyy
            res = true;
        }
        return res;
    }
    public static boolean isGithubValid(String github) {
        boolean res = false;
        if (github.matches("^(https?://)?(www\\.)?github\\.com/[a-zA-Z0-9_-]+(/[a-zA-Z0-9_-]+)?$")) {
            res = true;
        }
        return res;
    }
      

}
