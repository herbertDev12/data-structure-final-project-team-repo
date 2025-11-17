package weapons;

import Tree.BinaryTreeNode;
import entities.Enemy;
import entities.Player;
import game.GameController;
import map.MineRoom;

import java.awt.*;
import java.util.List;

public abstract class Projectile {
    public float x, y;
    public float vx, vy;
    protected float life;      // remaining life in seconds
    protected boolean expired = false;
    public final BinaryTreeNode<MineRoom> roomNode; // room where this projectile exists
    public final GameController controller;
    public final Player owner;
    public final int damage;

    public Projectile(Player owner, BinaryTreeNode<MineRoom> roomNode, float x, float y, float vx, float vy, float lifeSeconds, int damage, GameController controller) {
        this.owner = owner;
        this.roomNode = roomNode;
        this.x = x; this.y = y;
        this.vx = vx; this.vy = vy;
        this.life = lifeSeconds;
        this.damage = damage;
        this.controller = controller;
    }

    public boolean isExpired() { return this.expired; }
    protected void expire() { expired = true; }

    /**
     * Update logic (dt seconds). Subclasses should handle collisions and call expire().
     */
    public void update(float dt, GameController ctx) {
        if (expired) return;
        life -= dt;
        if (life <= 0f) { expire(); return; }
        x += vx * dt;
        y += vy * dt;
    }

    /**
     * Render in room-local coordinates (controller.renderProjectiles translates to room origin).
     */
    public abstract void render(Graphics2D g);

    /**
     * Helper: damage enemies inside radius at (cx,cy).
     * Uses controller.enemyManager.getEnemiesAt(roomNode)
     */
    protected void damageEnemiesInRadius(float cx, float cy, float radius, int dmg) {
        if (roomNode == null) return;
        List<Enemy> enemies = controller.enemyManager.getEnemiesAt(roomNode);
        float r2 = radius * radius;
        for (Enemy e : enemies) {
            if (!e.isAlive()) continue;
            float dx = e.getX() - cx;
            float dy = e.getY() - cy;
            if (dx*dx + dy*dy <= r2) {
                // apply damage + small knockback away from explosion center
                e.damage(dmg, controller);
                float dist = (float)Math.sqrt(dx*dx + dy*dy);
                float nx = dist > 0.001f ? dx / dist : 0f;
                float ny = dist > 0.001f ? dy / dist : -1f;
                e.applyKnockback(nx * 80f, ny * 80f);
            }
        }
    }
}
