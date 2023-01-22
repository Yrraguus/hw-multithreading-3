// https://github.com/netology-code/jd-homeworks/blob/video/volatile/task1/README.md

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger threeCounter = new AtomicInteger(0);
    public static AtomicInteger fourCounter = new AtomicInteger(0);
    public static AtomicInteger fiveCounter = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (
                int i = 0;
                i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(
                () -> {
                    for (String string : texts) {
                        if (palindrome(string)) {
                            switch (string.length()) {
                                case 3:
                                    threeCounter.incrementAndGet();
                                case 4:
                                    fourCounter.incrementAndGet();
                                case 5:
                                    fiveCounter.incrementAndGet();
                            }
                        }
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (String string : texts) {
                        if (sameLetter(string)) {
                            switch (string.length()) {
                                case 3:
                                    threeCounter.incrementAndGet();
                                case 4:
                                    fourCounter.incrementAndGet();
                                case 5:
                                    fiveCounter.incrementAndGet();
                            }
                        }
                    }
                }
        );
        Thread thread3 = new Thread(
                () -> {
                    for (String string : texts) {
                        if (alphabetical(string)) {
                            switch (string.length()) {
                                case 3:
                                    threeCounter.incrementAndGet();
                                case 4:
                                    fourCounter.incrementAndGet();
                                case 5:
                                    fiveCounter.incrementAndGet();
                            }
                        }
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + threeCounter.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + fourCounter.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + fiveCounter.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean palindrome(String string1) {
        StringBuilder string2 = new StringBuilder();
        for (int i = string1.length() - 1; i >= 0; i--) {
            string2.append(string1.charAt(i));
        }
        return string1.equals(string2.toString());
    }

    public static boolean sameLetter(String string) {
        boolean sameLetter = true;
        char ch = string.charAt(0);
        for (int i = 1; i < string.length(); i++) {
            if (ch != string.charAt(i)) {
                sameLetter = false;
                break;
            }
        }
        return sameLetter;
    }

    public static boolean alphabetical(String string) {
        boolean alphabetical = true;
        for (int i = 1; i < string.length(); i++) {
            if (string.charAt(i) < string.charAt(i - 1)) {
                alphabetical = false;
                break;
            }
        }
        return alphabetical;
    }
}
