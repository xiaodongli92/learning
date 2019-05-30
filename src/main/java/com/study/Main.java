package com.study;

import com.study.tree.BinaryTreeNode;
import com.study.tree.BinaryTreeReadMain;

import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        BinaryTreeNode node = BinaryTreeReadMain.prepare();
        first(node);
        System.out.println();
        first1(node);
        System.out.println();
        System.out.println();

        mid(node);
        System.out.println();
        mid1(node);
        System.out.println();
        System.out.println();

        after(node);
        System.out.println();
        after1(node);
        System.out.println();
        System.out.println();
    }

    private static void after1(BinaryTreeNode node) {
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
                        node = stack.pop();
                        System.out.print(node.getValue() + "\t");
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

    private static void after(BinaryTreeNode node) {
        if (null == node) {
            return;
        }
        after(node.getLeft());
        after(node.getRight());
        System.out.print(node.getValue() + "\t");
    }

    private static void mid1(BinaryTreeNode node) {
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

    private static void mid(BinaryTreeNode node) {
        if (null == node) {
            return;
        }
        mid(node.getLeft());
        System.out.print(node.getValue() + "\t");
        mid(node.getRight());
    }

    private static void first1(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (true) {
            while (node != null) {
                stack.push(node);
                System.out.print(node.getValue() + "\t");
                node = node.getLeft();
            }
            if (stack.isEmpty()) {
                break;
            }
            node = stack.pop();
            node = node.getRight();
        }
    }

    private static void first(BinaryTreeNode node) {
        if (null == node) {
            return;
        }
        System.out.print(node.getValue() + "\t");
        first(node.getLeft());
        first(node.getRight());
    }

}
