package services;

import enums.UserRole;
import java.sql.Date;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import model.user.User;
import storage.UserStorage;

public class RegisterNewPatient {
   private static final int MAX_ATTEMPTS = 3;
   private static final Scanner sc;

   static {
      sc = new Scanner(System.in);
   }

   public RegisterNewPatient() {
   }

   public void startRegistration() {
      System.out.println("Welcome to HMS New Patient Registration");
      System.out.println("Dear patient, please enter the following information for registration");
      String fullName = this.getInputWithPrompt("Please enter your full name:", (input) -> {
         return !input.trim().isEmpty();
      });
      if (fullName != null) {
         String dateOfBirth = this.getInputWithRetry("Date of Birth [YYYY-MM-DD]:", (input) -> {
            return input.matches("\\d{4}-\\d{2}-\\d{2}");
         }, "Invalid format. Please enter in [YYYY-MM-DD] format.");
         if (dateOfBirth != null) {
            String genderInput = this.getInputWithRetry("Gender [Male or Female]:", (input) -> {
               return input.equalsIgnoreCase("Male") || input.equalsIgnoreCase("Female");
            }, "Invalid gender. Please enter 'Male' or 'Female'.");
            if (genderInput != null) {
               String var10000 = genderInput.substring(0, 1).toUpperCase();
               String gender = var10000 + genderInput.substring(1).toLowerCase();
               String bloodType = this.getInputWithRetry("Blood Type [A+, A, B+, B, O+, O, AB+, AB, other or unknown (enter 00)]:", (input) -> {
                  return Pattern.compile("^(A\\+|A|B\\+|B|O\\+|O|AB\\+|AB|00|other)$", 2).matcher(input).matches();
               }, "Invalid blood type. Please enter one of the specified options.");
               if (bloodType != null) {
                  String email = this.getInputWithRetry("Please enter your email address [NA if not applicable]:", (input) -> {
                     return input.contains("@") || input.equalsIgnoreCase("NA");
                  }, "Invalid email format. Please enter a valid email or 'NA'.");
                  if (email != null) {
                     if (UserStorage.isDuplicateRecord(fullName, gender)) {
                        System.out.println("The user is already registered. Returning to the main menu...");
                     } else {
                        String hospitalId = UserStorage.generateHospitalId();
                        String defaultPassword = "password";
                        Date dob = java.sql.Date.valueOf(dateOfBirth);
                        User newUser = new User(hospitalId, fullName, defaultPassword, UserRole.Patient, gender);
                        UserStorage.saveUser(newUser);
                        System.out.println("Registration successful!");
                        System.out.println("Your Hospital ID: " + hospitalId);
                        System.out.println("Your default password is: " + defaultPassword);
                        System.out.println("Please change your password after logging in.");
                     }
                  }
               }
            }
         }
      }
   }

   private String getInputWithPrompt(String prompt, Predicate<String> validator) {
      System.out.println(prompt);

      for(int attempts = 0; attempts < 3; ++attempts) {
         String input = sc.nextLine().trim();
         if (validator.test(input)) {
            return input;
         }

         System.out.println("Invalid input. Please try again.");
      }

      System.out.println("Maximum attempts reached. Returning to the main menu...");
      return null;
   }

   private String getInputWithRetry(String prompt, Predicate<String> validator, String errorMessage) {
      for(int attempts = 0; attempts < 3; ++attempts) {
         System.out.println(prompt);
         String input = sc.nextLine().trim();
         if (validator.test(input)) {
            return input;
         }

         System.out.println(errorMessage);
      }

      System.out.println("Maximum attempts reached. Returning to the main menu...");
      return null;
   }
}
