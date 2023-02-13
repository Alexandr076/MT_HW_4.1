import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final int capacity = 100;
    private static final int stringLength = 100;
    private static final int iterationCount = 100;
    private static final String template = "abc";
    private static final String letter_A = "a";
    private static final String letter_B = "b";
    private static final String letter_C = "c";

    private static BlockingQueue<String> queue_A = new ArrayBlockingQueue(capacity);
    private static BlockingQueue<String> queue_B = new ArrayBlockingQueue(capacity);
    private static BlockingQueue<String> queue_C = new ArrayBlockingQueue(capacity);

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread generatorThread = new Thread(() -> {
            for (int i = 0; i <= iterationCount; i++) {
                String str = generateText(template, stringLength);
                try {
                    queue_A.put(str);
                    queue_B.put(str);
                    queue_C.put(str);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "generatorThread");
        generatorThread.start();

        new Thread(() -> {
            int length = 0;
            int maxSize = 0;
            for (int i = 0; i <= iterationCount; i++) {
                try {
                    length = queue_A.take().replace(letter_A, "").length();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                maxSize = stringLength - length > maxSize ? stringLength - length : maxSize;
                length = 0;
            }
            System.out.println("Max size of letter 'a' is: " + maxSize);
        }).start();

        new Thread(() -> {
            int length = 0;
            int maxSize = 0;
            for (int i = 0; i <= iterationCount; i++) {
                try {
                    length = queue_B.take().replace(letter_B, "").length();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                maxSize = stringLength - length > maxSize ? stringLength - length : maxSize;
                length = 0;
            }
            System.out.println("Max size of letter 'b' is: " + maxSize);
        }).start();

        new Thread(() -> {
            int length = 0;
            int maxSize = 0;
            for (int i = 0; i <= iterationCount; i++) {
                try {
                    length = queue_C.take().replace(letter_C, "").length();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                maxSize = stringLength - length > maxSize ? stringLength - length : maxSize;
                length = 0;
            }
            System.out.println("Max size of letter 'c' is: " + maxSize);
        }).start();
    }
}