package weapons;

import Tree.BinaryTreeNode;
import effects.Effect;
import entities.Enemy;
import entities.Player;
import game.GameController;
import map.MineRoom;

import java.awt.*;
import java.util.List;

/**
 * OrbitingOrb: orbits around the owner (player). Damages enemies on contact.
 * Visualized as a rotating orb that persists while life > 0.
 */
public class OrbittingOrb extends Projectile implements Effect {
    public final Player ownerPlayer;
    private final float orbitRadius;
    private final float angularSpeed; // radians per second
    private float angle; // current angle
    private final float damageInterval; // seconds between damage ticks on contact
    private float tickTimer = 0f;

    public OrbittingOrb(Player owner, BinaryTreeNode<MineRoom> roomNode, float initialAngle, float orbitRadius, float angularSpeed, float lifeSeconds, int damage, GameController controller) {
        super(owner, roomNode, owner.x, owner.y, 0f, 0f, lifeSeconds, damage, controller);
        this.ownerPlayer = owner;
        this.angle = initialAngle;
        this.orbitRadius = orbitRadius;
        this.angularSpeed = angularSpeed;
        this.damageInterval = 0.12f;
    }

    @Override
    public void update(float dt, GameController ctx) {
        if (expired) return;
        angle += angularSpeed * dt;
        // update position relative to owner
        x = ownerPlayer.x + (float)Math.cos(angle) * orbitRadius;
        y = ownerPlayer.y + (float)Math.sin(angle) * orbitRadius;
        tickTimer -= dt;
        if (tickTimer <= 0f) {
            // check collision with enemies at this instant
            if (roomNode != null) {
                List<Enemy> enemies = controller.enemyManager.getEnemiesAt(roomNode);
                float r2 = 12f * 12f;
                for (Enemy e : enemies) {
                    if (!e.isAlive()) continue;
                    float dx = e.getX() - x;
                    float dy = e.getY() - y;
                    if (dx*dx + dy*dy <= r2) {
                        e.damage(damage, controller);
                        float dist = (float)Math.sqrt(dx*dx + dy*dy);
                        float nx = dist > 0.001f ? dx / dist : 0f;
                        float ny = dist > 0.001f ? dy / dist : -1f;
                        e.applyKnockback(nx * 30f, ny * 30f);
                        // small feedback
                    }
                }
            }
            tickTimer = damageInterval;
        }
        life -= dt;
        if (life <= 0f) expire();
    }

    @Override
    public void render(Graphics2D g) {
        int s = 10;
        g.setColor(new Color(180, 200, 255));
        g.fillOval((int)(x - s/2), (int)(y - s/2), s, s);
        g.setColor(new Color(160,180,255,120));
        g.drawOval((int)(x - s), (int)(y - s), s*2, s*2);
    }

    @Override public boolean isExpired() { return expired; }
}
