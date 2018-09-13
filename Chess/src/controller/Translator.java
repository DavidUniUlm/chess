package controller;

import model.Point;

/**
 * This class translates numeric coordinates into algebraic chess notation.
 * e.g. (7,0) -> a1
 */
public class Translator {

    public static String getAlgebraicNotationRow(int r) {
        return "" + (8 - r);
    }

    public static String getAlgebraicNotationColumn(int c) {
        return "" + (char) (97 + c);
    }

    public static String getAlgebraicNotationRow(Point point) {
        return getAlgebraicNotationRow((int) point.getX());
    }

    public static String getAlgebraicNotationColumn(Point point) {
        return getAlgebraicNotationColumn((int) point.getY());
    }

    public static String getAlgebraicNotation(int r, int c) {
        return getAlgebraicNotationColumn(c) + getAlgebraicNotationRow(r);
    }

    public static String getAlgebraicNotation(Point square) {
        return getAlgebraicNotation((int) square.getX(), (int) square.getY());
    }

}
