package entities;

import Tree.BinaryTreeNode;
import map.MineRoom;


public class BossEnemy extends Enemy {
    private final float radiusVisual;
    private final float roarCooldown = 3.0f;
    private float roarTimer = 0f;

    public BossEnemy(BinaryTreeNode<MineRoom> node, float startX, float startY, int baseHp, float baseSpeed, int level) {
        // llamamos al constructor de entities.Enemy para inicializar lo básico
        super(node, startX, startY, baseHp, baseSpeed, level);
        // sobrescribir/ajustar valores para jefe
        // aumentar vida fuertemente, daño de contacto y resistencia a knockback
        this.hp = Math.max(this.hp, baseHp + (level - 1) * 40 + 800); // vida base grande
        this.knockbackResistance = Math.min(0.98f, 0.6f + 0.05f * level); // muy resistente
        // sobreeescribir contactDamage (campo heredado puede ser final en tu código; si es final,
        // cambia la definición de entities.Enemy para permitir set o usa otro campo)
        // Aquí asumimos que contactDamage es no-final y accesible:
        try {
            java.lang.reflect.Field f = Enemy.class.getDeclaredField("contactDamage");
            f.setAccessible(true);
            f.setInt(this, Math.max(20, this.contactDamage + level * 8));
        } catch (Exception ex) {
            // Si no se puede usar reflection, asegúrate de permitir setear contactDamage desde entities.Enemy.
        }
        // tamaño visual mayor (usado por el render)
        this.radiusVisual = 28f + level * 6f;
    }

    @Override
    public void update(float dt, Player player, MineRoom room) {
        if (!isAlive()) {
            super.update(dt, player, room);
            return;
        }

        // comportamiento sencillo del jefe:
        // - se mueve más lento pero con ráfagas: persigue al jugador con pausas para "rugir"
        roarTimer -= dt;
        float pursuitSpeed = Math.max(12f, this.speed * 0.6f); // más lento en persecución
        if (roarTimer <= 0f) {
            // ráfaga de empuje: aumenta velocidad momentáneamente y reinicia roar
            float burst = 1.8f + (this.level * 0.05f);
            // vector hacia jugador
            float dx = player.x - this.getX();
            float dy = player.y - this.getY();
            float d2 = dx*dx + dy*dy;
            if (d2 > 0.0001f) {
                float d = (float)Math.sqrt(d2);
                float nx = dx / d;
                float ny = dy / d;
                // dar un pequeño impulso hacia el jugador
                this.applyKnockback(nx * burst * 120f * (1f - this.knockbackResistance),
                        ny * burst * 120f * (1f - this.knockbackResistance));
            }
            roarTimer = roarCooldown;
        }

        // movimiento base (como entities.Enemy) mezclado con su vx/vy
        // replicamos lógica de entities.Enemy.update pero con speed reducido
        if (room == null) return;
        float dx = player.x - getX();
        float dy = player.y - getY();
        float dist2 = dx*dx + dy*dy;
        if (dist2 > 0.0001f) {
            float dist = (float)Math.sqrt(dist2);
            float nx = dx / dist;
            float ny = dy / dist;
            // mover hacia el jugador usando pursuitSpeed y sumando velocidades físicas
            // uso directo de translate para asegurar consistencia con colisiones posteriores
            translate((nx * pursuitSpeed + vx) * dt, (ny * pursuitSpeed + vy) * dt);
        } else {
            translate(vx * dt, vy * dt);
        }

        // damping de velocidad física (fricción)
        vx *= Math.max(0f, 1f - 4f * dt);
        vy *= Math.max(0f, 1f - 4f * dt);

        clampIfNearRoom(room);
    }

    // getter visual (si el render lo usa)
    public float getRadiusVisual() { return radiusVisual; }
}
