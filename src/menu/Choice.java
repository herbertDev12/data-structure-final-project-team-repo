package menu;

import java.io.Serializable;

public class Choice implements Serializable {
    public enum Kind { WEAPON, PASSIVE }
    public final Kind kind;
    public final String id;       // weaponId o passiveId
    public final String name;     // nombre para UI (en español)
    public final String description;

    public Choice(Kind kind, String id, String name, String description) {
        this.kind = kind;
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
