package services;

import java.util.Scanner;
import java.util.function.Predicate;
import model.user.User;
import storage.UserStorage;

public class ResetPassword {
   private static final int MAX_ATTEMPTS = 3;
   private static final Scanner sc;

   static {
      sc = new Scanner(System.in);
   }

   public ResetPassword() {
   }

   public void startResetPassword() {
      System.out.println("Welcome to the HMS Password Reset Service");
      String hospitalId = this.getInputWithRetry("Please enter your Hospital ID:", (input) -> {
         return !input.trim().isEmpty();
      }, "Hospital ID cannot be empty. Please enter a valid Hospital ID.");
      if (hospitalId != null) {
         User user = UserStorage.fetchUserByHospitalId(hospitalId);
         if (user == null) {
            System.out.println("Hospital ID not found. Returning to the main menu...");
         } else {
            String fullName = this.getInputWithRetry("Please enter your full name for verification:", (input) -> {
               return input.equalsIgnoreCase(user.getName());
            }, "Invalid or mismatched full name. Please try again.");
            if (fullName != null) {
               System.out.println("Verification successful.");
               String newPassword = this.getNewPassword();
               if (newPassword != null) {
                  UserStorage.updateUserPassword(hospitalId, newPassword);
                  System.out.println("Your password has been reset successfully.");
                  System.out.println("You can now log in with your new password.");
               }
            }
         }
      }
   }

   private String getNewPassword() {
      while(true) {
         System.out.print("Enter your new password: ");
         String password = sc.nextLine().trim();
         if (!this.isPasswordComplex(password)) {
            System.out.println(this.getComplexityErrorMessage());
         } else {
            System.out.print("Confirm your new password: ");
            String confirmPassword = sc.nextLine().trim();
            if (password.equals(confirmPassword)) {
               return password;
            }

            System.out.println("Passwords do not match. Please try again.");
         }
      }
   }

   private boolean isPasswordComplex(String password) {
      if (password.length() >= 6 && password.length() <= 20) {
         boolean hasUpper = false;
         boolean hasLower = false;
         boolean hasDigit = false;
         char[] var8;
         int var7 = (var8 = password.toCharArray()).length;

         for(int var6 = 0; var6 < var7; ++var6) {
            char c = var8[var6];
            if (Character.isUpperCase(c)) {
               hasUpper = true;
            } else if (Character.isLowerCase(c)) {
               hasLower = true;
            } else if (Character.isDigit(c)) {
               hasDigit = true;
            }
         }

         if (hasUpper && hasLower && hasDigit) {
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private String getComplexityErrorMessage() {
      return "Password must be 6-20 characters and contain at least one digit, one lowercase letter, and one uppercase letter.";
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
