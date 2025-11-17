package Tree;

import java.util.List;

public abstract class Tree<E> {
    protected TreeNode<E> root;

    public Tree() {
        this.root = null;
    }

    public Tree(TreeNode<E> root) {
        this.root = root;
    }

    public TreeNode<E> getRoot() {
        return this.root;
    }

    public void setRoot(TreeNode<E> root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public abstract int totalNodes();

    public abstract List<TreeNode<E>> getLeaves();

    public abstract int nodeLevel(TreeNode<E> var1);

    public abstract int treeLevel();

    public abstract int treeHeight();

    public abstract boolean nodeIsLeaf(TreeNode<E> var1);

    public abstract int nodeDegree(TreeNode<E> var1);

    public abstract E deleteNode(BinaryTreeNode<E> var1);
}
