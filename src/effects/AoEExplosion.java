package effects;

import Tree.BinaryTreeNode;
import entities.Player;
import game.GameController;
import map.MineRoom;
import weapons.Projectile;

import java.awt.*;


/**
 * effects.AoEExplosion: instant explosion effect that damages immediately then renders an expanding circle.
 * lifeSeconds controls total visual length.
 */
public class AoEExplosion extends Projectile implements Effect {
    private final float maxRadius;
    private float age = 0f;

    public AoEExplosion(Player owner, BinaryTreeNode<MineRoom> roomNode, float x, float y, float maxRadius, int damage, float lifeSeconds, GameController controller) {
        super(owner, roomNode, x, y, 0f, 0f, lifeSeconds, damage, controller);
        this.maxRadius = maxRadius;
        // damage immediately once upon creation
        damageEnemiesInRadius(x, y, maxRadius * 0.6f, damage); // immediate inner burst
    }

    @Override
    public void update(float dt, GameController ctx) {
        age += dt;
        if (age >= life || expired) { expire(); return; }
        // optionally, during early frames apply additional damage to simulate lingering AoE
        float progress = age / life;
        float currentRadius = maxRadius * progress;
        // small damage tick early (could be omitted)
        if (age < 0.1f) {
            damageEnemiesInRadius(x, y, currentRadius, damage/2);
        }
    }

    @Override
    public void render(Graphics2D g) {
        float progress = Math.min(1f, age / life);
        float r = maxRadius * (0.3f + 0.7f * progress);
        int ir = Math.max(2, (int)r);
        Color col = new Color(255, 140, 40, (int)(180 * (1f - progress)));
        g.setColor(col);
        g.fillOval((int)(x - r), (int)(y - r), ir*2, ir*2);
        // add bright center
        g.setColor(new Color(255, 200, 120, (int)(220 * (1f - progress))));
        g.fillOval((int)(x - r*0.4f), (int)(y - r*0.4f), Math.max(4, (int)(r*0.8f)), Math.max(4, (int)(r*0.8f)));
    }

    // effects.Effect interface
    @Override public boolean isExpired() { return expired; }
}
