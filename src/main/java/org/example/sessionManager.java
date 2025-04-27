package org.example;

//Uygulamayi suan kullanan kullanicinin username ve id sini tutan
// kullanici giris yapti mi çikti mi diye fonksiyonlari bulunan metodu
public class sessionManager {

    private static String currentUser;
    private static int currentUserId;

    public static void startSession(String username) {
        currentUser = username;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void endSession() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        sessionManager.currentUserId = currentUserId;
    }
}
