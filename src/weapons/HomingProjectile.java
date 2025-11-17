package weapons;

import Tree.BinaryTreeNode;
import entities.Enemy;
import entities.Player;
import game.GameController;
import map.MineRoom;

import java.awt.*;
import java.util.List;

/**
 * Homing projectile: steers towards nearest enemy in room and damages on impact.
 * Simple steering: adjust velocity each frame towards target.
 */
public class HomingProjectile extends Projectile {
    private Enemy target;
    private final float speed;
    private final float turnSpeed; // radians per second approx as factor

    public HomingProjectile(Player owner, BinaryTreeNode<MineRoom> roomNode, float x, float y, float speed, float lifeSeconds, int damage, GameController controller) {
        super(owner, roomNode, x, y, 0f, 0f, lifeSeconds, damage, controller);
        this.speed = speed;
        this.turnSpeed = 8f; // steering factor
    }

    @Override
    public void update(float dt, GameController ctx) {
        if (expired) return;

        // find target if none or dead
        if (target == null || !target.isAlive()) {
            target = findNearestEnemy();
        }

        if (target != null) {
            float dx = target.getX() - x;
            float dy = target.getY() - y;
            float dist = (float)Math.sqrt(dx*dx + dy*dy);
            if (dist < 2f) {
                // impact
                target.damage(damage, ctx);
                // knockback small toward away vector
                float nx = dist > 0.001f ? dx / dist : 0f;
                float ny = dist > 0.001f ? dy / dist : -1f;
                target.applyKnockback(nx * 40f, ny * 40f);
                expire();
                return;
            }
            // desired velocity
            float dxn = dx / (dist + 1e-6f);
            float dyn = dy / (dist + 1e-6f);
            // simple lerp steering
            vx = vx + (dxn * speed - vx) * Math.min(1f, turnSpeed * dt);
            vy = vy + (dyn * speed - vy) * Math.min(1f, turnSpeed * dt);
            // normalize to speed
            float vmag = (float)Math.sqrt(vx*vx + vy*vy);
            if (vmag > 0.001f) {
                vx = vx / vmag * speed;
                vy = vy / vmag * speed;
            }
        } else {
            // no target: fly straight by current velocity; if zero, expire
            if (vx == 0f && vy == 0f) { expire(); return; }
        }

        super.update(dt, ctx);
    }

    @Override
    public void render(Graphics2D g) {
        int s = 8;
        g.setColor(new Color(255, 220, 80));
        g.fillOval((int)(x - s/2), (int)(y - s/2), s, s);
        // glow
        g.setColor(new Color(255, 200, 40, 100));
        g.fillOval((int)(x - s), (int)(y - s), s*2, s*2);
    }

    private Enemy findNearestEnemy() {
        if (roomNode == null) return null;
        List<Enemy> list = controller.enemyManager.getEnemiesAt(roomNode);
        Enemy best = null;
        float best2 = Float.MAX_VALUE;
        for (Enemy e : list) {
            if (!e.isAlive()) continue;
            float dx = e.getX() - x, dy = e.getY() - y;
            float d2 = dx*dx + dy*dy;
            if (d2 < best2) { best2 = d2; best = e; }
        }
        return best;
    }
}
