package perks;

import entities.Player;


public class MinerGreed extends PassiveDef {
    public MinerGreed() {
        super("codicia_minera", "Codicia Minera", "+10% XP de cristales por pila");
    }
    @Override
    public void applyTo(Player player, int stacks) {
        player.crystalXpMultiplier *= Math.pow(1.10, stacks);
    }
}
