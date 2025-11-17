package menu;

import Tree.BinaryTree;
import Tree.BinaryTreeNode;
import Tree.PreorderIterator;
import entities.Player;
import perks.*;
import weapons.*;

import java.util.*;
/**
 * menu.PerkPool implementado usando tus BinaryTree y BinaryTreeNode.
 * Internamente guardamos las definiciones en árboles porque pediste usar tu implementación.
 *
 * Registro:
 *  - registerWeapon(weapons.WeaponDef)
 *  - registerPassive(perks.PassiveDef)
 *
 * Selección:
 *  - pickOptions(int n, entities.Player player) -> lista de menu.Choice
 *
 * Nota: el constructor registra las armas y pasivas básicas. Si ya implementaste weapons.WeaponDef
 * y sus subclases, puedes comentar o complementar las líneas del constructor.
 */
public class PerkPool {
    // dos árboles: uno para armas, otro para pasivas
    private final BinaryTree<WeaponDef> weaponsTree = new BinaryTree<WeaponDef>();
    private final BinaryTree<PassiveDef> passivesTree = new BinaryTree<PassiveDef>();
    private final Random rnd = new Random();

    public PerkPool() {
        // registrar armas (si ya las implementaste, estas líneas duplicarán definiciones;
        // remueve las que no necesites registrar aquí)
        registerWeapon(new PickaxeWeapon());
        registerWeapon(new DynamiteWeapon());
        registerWeapon(new CoalOrbWeapon());
        registerWeapon(new LanternWeapon());

        // registrar pasivas (en inglés las clases, pero ids y nombres en español)
        registerPassive(new DwarvenEndurance());
        registerPassive(new MinerGreed());
        registerPassive(new ForgeTempering());
        registerPassive(new HearthRegeneration());
    }

    // Inserta arma en el árbol. Construimos una cadena derecha simple si el árbol está vacío o no.
    public void registerWeapon(WeaponDef w) {
        if (w == null) return;
        BinaryTreeNode<WeaponDef> node = new BinaryTreeNode<>(w);
        if (weaponsTree.getRoot() == null) {
            weaponsTree.setRoot(node);
        } else {
            // insertar como el siguiente right-most child's right (cadena simple)
            BinaryTreeNode<WeaponDef> cursor = (BinaryTreeNode<WeaponDef>) weaponsTree.getRoot();
            while (cursor.getRight() != null) cursor = cursor.getRight();
            cursor.setRight(node);
        }
    }

    public void registerPassive(PassiveDef p) {
        if (p == null) return;
        BinaryTreeNode<PassiveDef> node = new BinaryTreeNode<>(p);
        if (passivesTree.getRoot() == null) {
            passivesTree.setRoot(node);
        } else {
            BinaryTreeNode<PassiveDef> cursor = (BinaryTreeNode<PassiveDef>) passivesTree.getRoot();
            while (cursor.getRight() != null) cursor = cursor.getRight();
            cursor.setRight(node);
        }
    }

    // Obtiene lista de todas las weapons desde el árbol usando PreorderIterator
    private List<WeaponDef> allWeapons() {
        List<WeaponDef> out = new ArrayList<>();
        if (weaponsTree.getRoot() == null) return out;
        PreorderIterator<WeaponDef> it = weaponsTree.preOrderIterator();
        while (it.hasNext()) {
            WeaponDef w = it.nextNode().getInfo();
            if (w != null) out.add(w);
        }
        return out;
    }

    // Obtiene lista de todas las passives desde el árbol usando PreorderIterator
    private List<PassiveDef> allPassives() {
        List<PassiveDef> out = new ArrayList<>();
        if (passivesTree.getRoot() == null) return out;
        PreorderIterator<PassiveDef> it = passivesTree.preOrderIterator();
        while (it.hasNext()) {
            PassiveDef p = it.nextNode().getInfo();
            if (p != null) out.add(p);
        }
        return out;
    }

    public WeaponDef getWeapon(String id) {
        for (WeaponDef w : allWeapons()) if (w.id.equals(id)) return w;
        return null;
    }

    public PassiveDef getPassive(String id) {
        for (PassiveDef p : allPassives()) if (p.id.equals(id)) return p;
        return null;
    }

    /**
     * pickOptions: devuelve hasta n opciones mezclando armas y pasivas.
     * Intenta mezclar armas y pasivas; evita duplicados en la misma selección.
     */
    public List<Choice> pickOptions(int n, Player player) {
        List<Choice> out = new ArrayList<>();
        List<WeaponDef> wList = allWeapons();
        List<PassiveDef> pList = allPassives();

        if (wList.isEmpty() && pList.isEmpty()) return out;

        // pools locales para muestreo sin repetición
        List<WeaponDef> wPool = new ArrayList<>(wList);
        List<PassiveDef> pPool = new ArrayList<>(pList);

        // preferencia balanceada: intentar alternar; si uno vacía, tomar del otro
        while (out.size() < n && (!wPool.isEmpty() || !pPool.isEmpty())) {
            boolean pickWeapon = rnd.nextBoolean();
            if (pickWeapon && !wPool.isEmpty()) {
                WeaponDef chosen = wPool.remove(rnd.nextInt(wPool.size()));
                // añadir si no está ya en out
                if (out.stream().noneMatch(c -> c.id.equals(chosen.id))) {
                    out.add(new Choice(Choice.Kind.WEAPON, chosen.id, chosen.name, chosen.description));
                }
            } else if (!pPool.isEmpty()) {
                PassiveDef chosen = pPool.remove(rnd.nextInt(pPool.size()));
                if (out.stream().noneMatch(c -> c.id.equals(chosen.id))) {
                    out.add(new Choice(Choice.Kind.PASSIVE, chosen.id, chosen.name, chosen.description));
                }
            } else if (!wPool.isEmpty()) {
                WeaponDef chosen = wPool.remove(rnd.nextInt(wPool.size()));
                if (out.stream().noneMatch(c -> c.id.equals(chosen.id))) {
                    out.add(new Choice(Choice.Kind.WEAPON, chosen.id, chosen.name, chosen.description));
                }
            }
        }

        // Si no llegamos a n por falta de variedad, devolvemos lo que hay
        return out;
    }
}
