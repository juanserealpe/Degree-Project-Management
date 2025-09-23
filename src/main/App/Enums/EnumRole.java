package Enums;

public enum EnumRole {
    UNDERGRADUATE_STUDENT(1),
    DIRECTOR(2),
    COORDINATOR(3),
    JURY(4);

    private final int id;

    EnumRole(int id) { this.id = id; }
    public int getId() { return id; }
}
