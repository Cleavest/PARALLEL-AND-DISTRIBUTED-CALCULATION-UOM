package two;

import java.util.function.Consumer;

/**
 * @author Cleavest on 13/4/2024
 */
public class Reference {

    public static void calc(int p, int size, Consumer<Integer> consumer){
        for (int i = p*p; i <= size; i += p)
        {
            consumer.accept(i);
        }
    }
}
