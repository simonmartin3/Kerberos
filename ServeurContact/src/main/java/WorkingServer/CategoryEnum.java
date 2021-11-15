package WorkingServer;

public enum CategoryEnum {
    COMMERCIAL(1),
    RECHERCHE_AGRO(2),
    CONSEILLER_TECHNIQUE(3),
    CONSEILLER_HORECA(4);

    private final int value;

    CategoryEnum(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
}
