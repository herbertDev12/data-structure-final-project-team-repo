package weapons;

import effects.SlashEffect;
import entities.Enemy;
import entities.Player;
import game.GameController;

import java.util.List;

public class PickaxeWeapon extends WeaponDef {
    public PickaxeWeapon() {
        super("pico", "Pico", "Ataque cuerpo a cuerpo frontal del enano.", 0.16f);
    }

    @Override
    public void fire(Player player, int level, List<Enemy> enemies, GameController controller) {
        float dirX = player.facingX;
        float dirY = player.facingY;

        // safety fallback si por alguna razón facing estuviera a cero
        float len = (float) Math.sqrt(dirX * dirX + dirY * dirY);
        if (len < 1e-4f) {
            // fallback a velocidad residual o arriba
            dirX = player.vx;
            dirY = player.vy;
            len = (float) Math.sqrt(dirX * dirX + dirY * dirY);
        }
        if (len < 1e-4f) {
            dirX = 0f;
            dirY = -1f;
            len = 1f;
        }
        dirX /= len;
        dirY /= len;

        float range = 48f + (level - 1) * 6f;
        float angle = (float) Math.toRadians(70);
        int dmg = Math.round((8 + (level - 1) * 3) * player.damageMultiplier);

        SlashEffect slash = new SlashEffect(player, controller.nodoActual, player.x, player.y, dirX, dirY, range, angle, dmg, 0.12f, controller);
        controller.spawnEffect(slash);
    }
}