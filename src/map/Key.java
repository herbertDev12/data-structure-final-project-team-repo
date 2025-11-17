package map;
import effects.*;
import entities.*;
import perks.*;
import weapons.*;
import menu.*;
import game.*;
import java.util.*;
import map.*;
import utils.*;


public class Key {
    public float x;
    public float y;
    public String id;
    public boolean collected = false;

    public Key(float x, float y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
}
