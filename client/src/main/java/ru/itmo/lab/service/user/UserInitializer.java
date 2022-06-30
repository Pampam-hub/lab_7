package ru.itmo.lab.service.user;

import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.handlers.DragonValidator;

import java.util.*;

public class UserInitializer {
    private UserInitializer() {
    }

    public static boolean acceptAnswer(Scanner scanner) throws IllegalArgumentException {
        boolean answer = true;
        try {
            String stringAnswer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            DragonValidator<Boolean> dragonValidator =
                    new DragonValidator<>(DragonValidator.checkBoolean(stringAnswer));
            dragonValidator.validateNull(false);
            answer = dragonValidator.getValue();
        } catch (NoSuchElementException e) {
            OutputMessage.printErrorMessage("Invalid character entered");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            OutputMessage.printErrorMessage(e.getMessage());
        }
        return answer;
    }

    public static boolean askForRegistration(String question, Scanner scanner) {
        System.out.println(question);
        boolean isRunning = true;
        boolean answer = true;
        while (isRunning) {
            try {
                answer = acceptAnswer(scanner);
                isRunning = false;
            } catch (IllegalArgumentException e) {
                OutputMessage.printErrorMessage(e.getMessage());
            }
        }
        return answer;
    }

    public static List<String> getUser(Scanner scanner) {
        String login = null;
        boolean isRunning = true;
        while (isRunning) {
            try {
                login = inputLogin(scanner);
                isRunning = false;
            } catch (IllegalArgumentException e) {
                OutputMessage.printErrorMessage(e.getMessage());
            }
        }
        String password = null;
        isRunning = true;
        while (isRunning) {
            try {
                password = inputPassword(scanner);
                isRunning = false;
            } catch (IllegalArgumentException e) {
                OutputMessage.printErrorMessage(e.getMessage());
            }
        }

        List<String> user = new LinkedList<>();
        user.add(login);
        user.add(password);
        return user;
    }

    private static String inputLogin(Scanner scanner) throws IllegalArgumentException {
        String login = null;
        try {
            // ??????why??????
            System.out.println("Enter username, "
                    + "(minimum length is 3 characters)");
            login = scanner.nextLine().trim();

            DragonValidator<String> dragonValidator = new DragonValidator<>(login);
            dragonValidator.validateNull(false);
            dragonValidator.validatePredicate((arg) -> ((String)arg).length() >= 3,
                    "minimum length is 3 characters");
            login = dragonValidator.getValue();

        } catch (NoSuchElementException e) {
            OutputMessage.printErrorMessage("Invalid character entered");
            System.exit(0);
        }
        return login;
    }

    private static String inputPassword(Scanner scanner) throws IllegalArgumentException {
        String password = null;
        try {
            System.out.println("Enter password, (minimum length is 7 characters)");
            password = scanner.nextLine().trim();

            DragonValidator<String> dragonValidator = new DragonValidator<>(password);
            dragonValidator.validateNull(false);
            dragonValidator.validatePredicate((arg) -> ((String)arg).length() >= 7,
                    "minimum length is 7 characters");
            password = dragonValidator.getValue();

        } catch (NoSuchElementException e) {
            OutputMessage.printErrorMessage("Invalid character entered");
            System.exit(0);
        }
        return password;
    }
}
