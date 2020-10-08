import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void getExcludeAsString(){
        Category category = new Category("Name1", 100, true);

        String actualResult = category.getExcludeAsString();

        assertEquals("TRUE", actualResult);

        category = new Category("Name1", 100, false);

        actualResult = category.getExcludeAsString();

        assertEquals("FALSE", actualResult);

    }

    @Test
    void getDefaultGoalAsString() {
        Category category = new Category("Name1", 100, true);

        String actualResult = category.getDefaultGoalAsString();

        assertEquals("100.00", actualResult);
    }
    @Test
    void getNaNGoalAsString() {
        Category category = new Category ("Name1", Float.NaN, true);

        String actualResult = category.getDefaultGoalAsString();

        assertEquals("NULL", actualResult);
    }
}
