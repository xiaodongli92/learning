package com.study.sort;

/**
 * 单向链表反转
 */
public class LinkedList {

    public static void main(String[] args) {
        Node head = new Node(0);
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        head.next = one;
        one.next = two;
        two.next = three;
        three.next = four;
        printLinked(head);
        printLinked(four);
        reverse(head);
        printLinked(head);
        printLinked(four);
    }

    private static void printLinked(Node node) {
        if (null != node) {
            System.out.print(node.value);
            printLinked(node.next);
        } else {
            System.out.println();
        }
    }

    private static void reverse(Node node) {
        if (null == node || node.next == null) {
            return;
        }
        reverse(node.next);
        node.next.next = node;
        node.next = null;
    }

    static class Node{

        int value;

        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
