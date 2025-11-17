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


/**
 * Recompensa dejada por enemigos al morir.
 */
public class Crystal {
    public float x, y;
    public int value;
    public boolean collected = false;

    public Crystal(float x, float y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
