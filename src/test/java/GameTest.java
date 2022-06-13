import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    //Test a valid string input which represents full strike frames. It should give back 300 points as a return.
    @Test
    public void it_should_be_300() throws InvalidInputException {
        assertEquals(300, Game.CalculateScore("x x x x x x x x x x x x"));
    }

    //Test a valid string input which contains number, spare and strike. After 10 frames there were given 2 more throw.
    @Test
    public void it_should_be_176() throws InvalidInputException {
        assertEquals(176, Game.CalculateScore("53 72 x x 7/ 9/ 24 6/ x x x 5"));
    }

    //Test a valid string input which contains full missed frames which coded as a single '-' char.
    @Test
    public void it_should_be_0() throws InvalidInputException {
        assertEquals(0, Game.CalculateScore("- - - - - - - - - -"));
    }

    //Test a valid string input which contains mixed misses. Frames where are points and misses mixed and frames where the two throw was miss.
    @Test
    public void it_should_be_18() throws InvalidInputException {
        assertEquals(18, Game.CalculateScore("-3 3- x 1- - - - - - -"));
    }

    //Test a valid string input which contains a spare in the 10. frame.
    @Test
    public void it_should_be_163() throws InvalidInputException {
        assertEquals(163, Game.CalculateScore("53 72 x x 7/ 9/ 24 6/ x 2/ x"));
    }

    //Test a valid string input which not contains a spare or strike in the 10. frame.
    @Test
    public void it_should_be_153() throws InvalidInputException {
        assertEquals(153, Game.CalculateScore("53 72 x x 7/ 9/ 24 6/ x 25"));
    }

    //Test an invalid input which is null
    @Test
    public void it_should_throw_InvalidInputException_when_input_is_null() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Input is null");
        Game.CalculateScore(null);
    }

    //Test an invalid input where an empty string given
    @Test
    public void it_should_throw_InvalidInputException_when_input_is_an_empty_string() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Input is an empty String");
        Game.CalculateScore("");
    }

    //Test an invalid input which contains less than 10 valid frames
    @Test
    public void it_should_throw_InvalidInputException_when_input_too_short() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("There are less than 10 frames!");
        Game.CalculateScore("x x 56 32 3/ -");
    }

    //Test an invalid input which contains more than 12 valid frames
    @Test
    public void it_should_throw_InvalidInputException_when_input_too_long() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("There are more than 12 frames");
        Game.CalculateScore("53 72 x x 7/ 9/ 24 6/ x x x 5 41");
    }

    //Test an invalid input which contains an invalid char
    @Test
    public void it_should_throw_InvalidInputException_when_input_has_illegal_char() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("There is an illegal character in the input!");
        Game.CalculateScore("x x 56 g2 3/ - x x x x 12");
    }
    //Test an invalid input which contains an invalid number
    @Test
    public void it_should_throw_InvalidInputException_when_input_has_illegal_number() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("There is an illegal character in the input!");
        Game.CalculateScore("x x 56 02 3/ - x x x x 12");
    }

    //Test an invalid input which contains more than 2 throw in a frame
    @Test
    public void it_should_throw_InvalidInputException_when_more_than_two_throw_in_a_frame() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Invalid input format, more than 2 throw in a frame");
        Game.CalculateScore("5372_x x 7/ 9/ 24 6/ x x x x 12");
    }
    //Test an invalid input where one of its frame has more than 2 'x'
    @Test
    public void it_should_throw_InvalidInputException_when_more_than_two_strike_code_in_one_frame() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Invalid input format, one frame contains more than 1 x,-,/");
        Game.CalculateScore("53 x 7/ 9/ 24 6/ xx x x x 12");
    }
    //Test an invalid input where one of its frame has more than 2 '-'
    @Test
    public void it_should_throw_InvalidInputException_when_more_than_two_miss_code_in_one_frame() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Invalid input format, one frame contains more than 1 x,-,/");
        Game.CalculateScore("53 x 7/ 9/ 24 6/ -- x x x 12");
    }
    //Test an invalid input where one of its frame starting with a '/' char
    @Test
    public void it_should_throw_InvalidInputException_when_a_frame_starting_with_a_spare_code() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Invalid input format, a frame starting with a '/' char");
        Game.CalculateScore("13 x 7/ 9/ /4 6/ x x x x 12");
    }
    //Test an invalid input where one of its frame has more than 2 '/'
    @Test
    public void it_should_throw_InvalidInputException_when_more_than_two_spare_code_in_one_frame() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Invalid input format, one frame contains more than 1 x,-,/");
        Game.CalculateScore("53 x 7/ 9/ 24 6/ // x x x");
    }


    //Test an invalid input where the 11. bonus throw is not a strike and one more frame were given
    @Test
    public void it_should_throw_InvalidInputException_where_three_more_bonus_throw_were_given() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Input has 2 bonus frames contain more than 2 throw");
        Game.CalculateScore("53 72 x x 7/ 9/ 24 6/ x x 53 5");
    }

    //Test an invalid input where the 11. bonus throw is a strike and one more whole frame were given
    @Test(expected = InvalidInputException.class)
    public void it_should_throw_InvalidInputException_after_bonus_strike_and_whole_frame_follows() throws InvalidInputException {
        expectedEx.expect(InvalidInputException.class);
        expectedEx.expectMessage("Input has 2 bonus frames contain a strike and after 2 more throw");
        Game.CalculateScore("53 72 x x 7/ 9/ 24 6/ x x x 53");
    }

}
