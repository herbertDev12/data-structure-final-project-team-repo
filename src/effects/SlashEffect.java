package effects;

import Tree.BinaryTreeNode;
import entities.Enemy;
import entities.Player;
import game.GameController;
import map.MineRoom;
import weapons.Projectile;

import java.awt.*;
import java.util.List;


public class SlashEffect extends Projectile implements Effect {
    private final float originX, originY;
    private final float dirX, dirY; // normalized direction of slash
    private final float range;
    private final float angle; // in radians, total sweep
    private final float lifeTotal;
    private float age = 0f;
    private boolean damaged = false;

    public SlashEffect(Player owner, BinaryTreeNode<MineRoom> roomNode, float originX, float originY, float dirX, float dirY, float range, float angle, int damage, float lifeSeconds, GameController controller) {
        super(owner, roomNode, originX, originY, 0f, 0f, lifeSeconds, damage, controller);
        this.originX = originX; this.originY = originY;
        float len = (float)Math.sqrt(dirX*dirX + dirY*dirY);
        if (len < 1e-6f) { this.dirX = 1f; this.dirY = 0f; } else { this.dirX = dirX/len; this.dirY = dirY/len; }
        this.range = range;
        this.angle = angle;
        this.lifeTotal = lifeSeconds;
    }

    @Override
    public void update(float dt, GameController ctx) {
        age += dt;
        if (!damaged) {
            applySlashDamage();
            damaged = true;
        }
        if (age >= lifeTotal)
            expire();
    }

    private void applySlashDamage() {
        if (roomNode == null)
            return;
        List<Enemy> enemies = controller.enemyManager.getEnemiesAt(roomNode);
        float coneCos = (float)Math.cos(angle * 0.5f);
        for (Enemy e : enemies) {
            if (!e.isAlive())
                continue;
            float dx = e.getX() - originX;
            float dy = e.getY() - originY;
            float d2 = dx*dx + dy*dy;
            if (d2 > range*range)
                continue;
            float d = (float)Math.sqrt(d2);
            float nx = dx / (d + 1e-6f);
            float ny = dy / (d + 1e-6f);
            float dot = nx * dirX + ny * dirY;
            if (dot >= coneCos) {
                e.damage(damage, controller);
                e.applyKnockback(nx * 60f, ny * 60f);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        float progress = Math.min(1f, age / lifeTotal);
        float alpha = 1f - progress;
        int r = (int)range;
        // draw translucent wedge: approximate by an arc
        g.setColor(new Color(220, 220, 80, (int)(180 * alpha)));
        // compute angle and center for arc rendering using Graphics2D.fillArc needs int bounding box
        // we'll draw a simple filled oval overlay at origin with rotation hint (approximation)
        g.fillOval((int)(originX - r), (int)(originY - r), r*2, r*2);
        // optional directional marker
        g.setColor(new Color(255,255,200, (int)(200*alpha)));
        int lx = (int)(originX + dirX * (r*0.6f));
        int ly = (int)(originY + dirY * (r*0.6f));
        g.fillOval(lx-6, ly-6, 12, 12);
    }

    @Override
    public boolean isExpired() {
        return expired;
    }
}
