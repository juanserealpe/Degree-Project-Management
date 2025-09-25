package Enums;

public enum EnumProgram {
    ING_SISTEMAS(0);

    private final int id;

    EnumProgram(int id) { this.id = id; }
    public int getId() { return id; }
    public static EnumProgram fromId(int id) {
        for (EnumProgram program : EnumProgram.values()) {
            if (program.getId() == id) {
                return program;
            }
        }
        throw new IllegalArgumentException("IdProgram inválido: " + id);
    }
}
