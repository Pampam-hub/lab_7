package ru.itmo.lab.service.handlers;


import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class DragonValidator<T> {
    private String string;
    private T value;

    public DragonValidator(String string, T value) {
        this.string = string;
    }

    public DragonValidator(T value) {
        this.value = value;
    }

    public DragonValidator(Scanner scanner) {
        try {
            string = scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("\nInvalid character entered");
            System.exit(0);
        }
    }

    public static void validateNumberOfArgs(String[] args, int numberOfArgs) {
        if (args.length != numberOfArgs) {
            throw new IllegalArgumentException("\nWrong number of arguments, " +
                    "this command expects " + numberOfArgs + " arguments");
        }
    }

    public static void validateId(String id) {
        // либу надо подключить
        /*
        if(!NumberUtils.isParasable(id)) {
            throw new IllegalArgumentException("id must be an integer");
        }
        Integer.parseInt(id);
         */
    }

    public static boolean checkBoolean(String answer) {
        if ("true".equals(answer) || "yes".equals(answer)) {
            return true;
        } else if ("false".equals(answer) || "no".equals(answer)) {
            return false;
        } else {
            throw new IllegalArgumentException("Value must be yes or no");
        }
    }

    public void validateNull(boolean nullable) {
        if ("".equals(string) ) {
            if (nullable)
                value = null;
            else
                throw new IllegalArgumentException("\nValue can't be null");
        }
    }

    public void validateValueNull(boolean nullable, String name) {
        if (value == null) {
            if (!nullable)
                throw new IllegalArgumentException(name + " can't be null");
        }
    }

    public void validateFunction(Function<String, T> function, String description) {
        if (!"".equals(string) ) {
            try {
                value = function.apply(string);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("\n" + description);
            }
        }
    }

    public void validatePredicate(Predicate<Object> predicate, String error) {
        if (!"".equals(string)) {
            if (!predicate.test(value)) {
                throw new IllegalArgumentException(error);
            }
        }
    }

    public T getValue() {
        if (value == null && !"".equals(string)) {
            value = (T) string;
        }
        return value;
    }
}