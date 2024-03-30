package bucketsort;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Data_Generetic {

    public static void main(String[] args) {
        Random rand = new Random();

        try (FileWriter writer = new FileWriter("data.txt", false)) {
            int randomNumber = rand.nextInt(51) + 50;
            for (int i = 0; i < randomNumber; i++) {
                int size = rand.nextInt(9900) + 100;
                int[] array = new int[size];

                for (int j = 0; j < size; j++) {
                    array[j] = rand.nextInt(999999999);
                }

                writer.write(size + "\n");
                for (int element : array) {
                    writer.write(element + " ");
                }
                writer.write("\n");
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}
