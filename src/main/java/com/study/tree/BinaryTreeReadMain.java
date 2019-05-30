package com.study.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 前序递归遍历算法：访问根结点-->递归遍历根结点的左子树-->递归遍历根结点的右子树
 * 中序递归遍历算法：递归遍历根结点的左子树-->访问根结点-->递归遍历根结点的右子树
 * 后序递归遍历算法：递归遍历根结点的左子树-->递归遍历根结点的右子树-->访问根结点
 */
public class BinaryTreeReadMain {

    public static void main(String[] args) {
        BinaryTreeNode root = prepare();
        preOrderRead(root);
        System.out.println();
        preOrderRead1(root);
        System.out.println();
        inOrderRead(root);
        System.out.println();
        inOrderRead1(root);
        System.out.println();
        lastOrderRead(root);
        System.out.println();
        lastOrderRead1(root);
        System.out.println();

        depthFirst(root);
        System.out.println();
        breadthFirst(root);
        System.out.println();
    }

    /**
     * 广度优先
     */
    private static void breadthFirst(BinaryTreeNode node) {
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
            System.out.print(node.getValue() + "\t");
        }
    }

    /**
     * 深度优先（二叉树类似于前序遍历）
     */
    private static void depthFirst(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getValue() + "\t");
        depthFirst(node.getLeft());
        depthFirst(node.getRight());
    }

    private static void lastOrderRead1(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (true) {
            if (null != node) {
                stack.push(node);
                node = node.getLeft();
            } else {
                if (stack.isEmpty()) {
                    break;
                }
                if (null == stack.peek().getRight()) {
                    node = stack.pop();
                    System.out.print(node.getValue() + "\t");
                    while (node == stack.peek().getRight()) {
                        System.out.print(stack.peek().getValue() + "\t");
                        node = stack.pop();
                        if (stack.isEmpty()) {
                            break;
                        }
                    }
                }
                if (!stack.isEmpty()) {
                    node = stack.peek().getRight();
                } else {
                    node = null;
                }
            }
        }
    }

    /**
     * 后序遍历
     */
    private static void lastOrderRead(BinaryTreeNode node) {
        if (null != node) {
            lastOrderRead(node.getLeft());
            lastOrderRead(node.getRight());
            System.out.print(node.getValue() + " \t");
        }
    }

    private static void inOrderRead1(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (true) {
            while (null != node) {
                stack.push(node);
                node = node.getLeft();
            }
            if (stack.isEmpty()) {
                break;
            }
            node = stack.pop();
            System.out.print(node.getValue() + "\t");
            node = node.getRight();
        }
    }

    /**
     * 中序遍历
     */
    private static void inOrderRead(BinaryTreeNode node) {
        if (null != node) {
            inOrderRead(node.getLeft());
            System.out.print(node.getValue() + "\t");
            inOrderRead(node.getRight());
        }
    }

    private static void preOrderRead1(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (true) {
            while (null != node) {
                System.out.print(node.getValue() + "\t");
                stack.push(node);
                node = node.getLeft();
            }
            if (stack.isEmpty()) {
                break;
            }
            node = stack.pop();
            node = node.getRight();
        }

    }

    /**
     * 前序遍历
     */
    private static void preOrderRead(BinaryTreeNode node) {
        if (null != node) {
            System.out.print(node.getValue() + "\t");
            preOrderRead(node.getLeft());
            preOrderRead(node.getRight());
        }
    }

    public static BinaryTreeNode prepare() {
        BinaryTreeNode root = new BinaryTreeNode();
        root.setValue(0);

        BinaryTreeNode left = new BinaryTreeNode();
        left.setValue(1);
        root.setLeft(left);

        BinaryTreeNode right = new BinaryTreeNode();
        right.setValue(2);
        root.setRight(right);

        BinaryTreeNode leftLeft = new BinaryTreeNode();
        leftLeft.setValue(3);
        left.setLeft(leftLeft);
        BinaryTreeNode leftRight = new BinaryTreeNode();
        leftRight.setValue(4);
        left.setRight(leftRight);

        BinaryTreeNode rightLeft = new BinaryTreeNode();
        rightLeft.setValue(5);
        right.setLeft(rightLeft);
        BinaryTreeNode rightRight = new BinaryTreeNode();
        rightRight.setValue(6);
        right.setRight(rightRight);
        return root;
    }
}
