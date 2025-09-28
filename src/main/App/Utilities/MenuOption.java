package Utilities;

import Enums.EnumMenuOption;

public class MenuOption {
    private final EnumMenuOption id;
    private final String descripcion;

    public MenuOption(EnumMenuOption id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public EnumMenuOption getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion; // útil si usas directamente en botones
    }
}
