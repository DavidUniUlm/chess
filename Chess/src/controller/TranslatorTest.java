package controller;

import model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TranslatorTest {

    @Test
    void getAlgebraicNotation() {
        assertEquals("f2", Translator.getAlgebraicNotation(6,5));
        assertEquals("f2", Translator.getAlgebraicNotation(new Point(6,5)));
        assertFalse("a1".equals(Translator.getAlgebraicNotation(0,0)));
        assertFalse("a1".equals(Translator.getAlgebraicNotation(new Point(0,0))));
    }

}