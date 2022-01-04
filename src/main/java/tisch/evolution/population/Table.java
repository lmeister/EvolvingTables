package tisch.evolution.population;

import java.util.List;
import java.util.Objects;

/**
 * Simple object representing a table.
 */
public class Table {
    private int leg1;
    private int leg2;
    private int leg3;
    private int leg4;

    /**
     * Standard constructor for table object.
     * @param leg1 leg length for leg 1
     * @param leg2 leg length for leg 2
     * @param leg3 leg length for leg 3
     * @param leg4 leg length for leg 4
     */
    public Table(int leg1, int leg2, int leg3, int leg4) {
        this.leg1 = leg1;
        this.leg2 = leg2;
        this.leg3 = leg3;
        this.leg4 = leg4;
    }

    /**
     * Copy constructor
     * @param table The table that is copied
     */
    public Table(Table table) {
        this.leg1 = table.getLeg1();
        this.leg2 = table.getLeg2();
        this.leg3 = table.getLeg3();
        this.leg4 = table.getLeg4();
    }

    public int getLeg1() {
        return leg1;
    }

    public void setLeg1(int length) {
        this.leg1 = Math.max(length, 1);
    }

    public int getLeg2() {
        return leg2;
    }

    public void setLeg2(int length) {
        this.leg2 = Math.max(length, 1);
    }

    public int getLeg3() {
        return leg3;
    }

    public void setLeg3(int length) {
        this.leg3 = Math.max(length, 1);
    }

    public int getLeg4() {
        return leg4;
    }

    public void setLeg4(int length) {
        this.leg4 = Math.max(length, 1);
    }

    public List<Integer> getLegList() {
        return List.of(this.leg1, this.leg2, this.leg3, this.leg4);
    }

    // TODO Remove single leg getters/setters or dont expose to public

    /**
     * Get length of specified leg
     * @param number Number of leg
     * @return Length of given leg
     */
    public int getLegFromNumber(int number) {
        switch (number) {
            case 1:
                return this.getLeg1();

            case 2:
                return this.getLeg2();

            case 3:
                return this.getLeg3();

            case 4:
                return this.getLeg4();

            default:
                return -1;
        }
    }

    /**
     * Set length of specified leg. Will use 1 if length < 1
     * @param number Number of leg
     * @param length Target length
     */
    public void setLegFromNumber(int number, int length) {
        length = Math.max(length, 1);
        switch (number) {
            case 1:
                this.leg1 = length;
                break;

            case 2:
                this.leg2 = length;
                break;

            case 3:
                this.leg3 = length;
                break;

            case 4:
                this.leg4 = length;
                break;

            default:
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return leg1 == table.leg1 && leg2 == table.leg2 && leg3 == table.leg3 && leg4 == table.leg4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leg1, leg2, leg3, leg4);
    }

    @Override
    public String toString() {
        return "Table{" +
                "leg1=" + leg1 +
                ", leg2=" + leg2 +
                ", leg3=" + leg3 +
                ", leg4=" + leg4 +
                '}';
    }
}
