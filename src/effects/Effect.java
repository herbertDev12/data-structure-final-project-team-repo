package effects;

import game.GameController;

import java.awt.*;


/**
 * effects.Effect: interfaz minimal para efectos visuales/temporales que viven en una sala.
 * - update(dt, controller) se llama cada frame (dt en segundos)
 * - render(g) dibuja el efecto en coordenadas de sala (game.GameController ya traslada)
 * - isExpired() indica cuándo debe eliminarse
 */
public interface Effect {
    void update(float dt, GameController controller);
    void render(Graphics2D g);
    boolean isExpired();
}
