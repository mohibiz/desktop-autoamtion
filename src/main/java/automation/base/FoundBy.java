package automation.base;

public enum FoundBy {
    ID("id"),
    LINK_TEXT("link text"),
    PARTIAL_LINK_TEXT("partial link text"),
    TAG_NAME("tag name"),
    NAME("name"),
    CLASS_NAME("class name"),
    CSS_SELECTOR("css selector"),
    XPATH("xpath");

    private final String fbStrings;

    private FoundBy(String foundBy) {
        this.fbStrings = foundBy;
    }

    public static FoundBy fromString(String foundBy) {
        FoundBy[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            FoundBy b = var1[var3];
            if (b.fbStrings.equalsIgnoreCase(foundBy)) {
                return b;
            }
        }

        return null;
    }
}

