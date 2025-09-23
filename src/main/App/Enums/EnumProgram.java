package Enums;

public enum EnumProgram {
    ING_SISTEMAS(1);

    private final int id;

    EnumProgram(int id) { this.id = id; }
    public int getId() { return id; }
}
