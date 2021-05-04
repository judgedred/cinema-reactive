package practice;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrackingCodingInterviewLinkedLists {

    //    2.1
    @Test
    void removeDups() {
        LinkedList<Integer> data = new LinkedList<>(Arrays.asList(5, 6, 7, 5, 2, 5, 7, 4));
        LinkedList<Integer> expected = new LinkedList<>(Arrays.asList(5, 6, 7, 2, 4));
        assertEquals(expected, removeDuplicates2(data));
    }

    private LinkedList<Integer> removeDuplicates(LinkedList<Integer> data) {
        Map<Integer, Integer> map = new HashMap<>();
        Iterator<Integer> iterator = data.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            Integer value = map.put(integer, integer);
            if (value != null) {
                iterator.remove();
            }
        }
        return data;
    }

    //    if sorting not allowed
    //    if list iterator not allowed then count indexes manually
    private LinkedList<Integer> removeDuplicates2(LinkedList<Integer> data) {
        ListIterator<Integer> iterator = data.listIterator(data.size() - 1);
        while (iterator.hasPrevious()) {
            int index = iterator.previousIndex();
            Integer current = iterator.previous();
            ListIterator<Integer> listIterator = data.listIterator();
            while (listIterator.hasNext() && listIterator.nextIndex() < index) {
                Integer integer = listIterator.next();
                if (integer.equals(current)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return data;
    }

    //    2.2
    @Test
    void kthToLast() {
        Node data = new Node(5, 0,
                new Node(6, 1,
                        new Node(7, 2,
                                new Node(5, 3,
                                        new Node(2, 4,
                                                new Node(5, 5,
                                                        new Node(7, 6,
                                                                new Node(4, 7, null))))))));
        assertEquals((Integer) 2, findElement(data, 4));
    }

    private Integer findElement(Node data, int numberToLast) {
        if (data == null) {
            return null;
        }
        int size = 1;
        Node current = data;
        while (current.next != null) {
            current = current.next;
            size++;
        }
        int index = size - numberToLast;
        //        return find(data, index);
        return findRecursively(data, index);
    }

    private Integer find(Node data, int index) {
        Node current = data;
        do {
            if (current.index == index) {
                return current.value;
            }
            current = current.next;
        } while (current != null);
        return null;
    }

    private Integer findRecursively(Node data, int index) {
        if (data == null) {
            return null;
        }
        if (data.index == index) {
            return data.value;
        }
        return findRecursively(data.next, index);
    }

    //    2.3
    @Test
    void deleteMiddleNode() {
        Node middle = new Node(5, 5, new Node(7, 6, new Node(4, 7, null)));
        Node data = new Node(5, 0,
                new Node(6, 1,
                        new Node(7, 2,
                                new Node(5, 3,
                                        new Node(2, 4, middle)))));
        System.out.println(data);
        delete(middle);
        System.out.println(data);
    }

    private void delete(Node node) {
        if (node == null || node.next == null) {
            return;
        }
        Node next = node.next;
        node.value = next.value;
        node.next = next.next;
        Node current = node;
        while (current.next != null) {
            current.next.index = current.index + 1;
            current = current.next;
        }
    }

    //    2.5
    @Test
    void sumLists() {
        Node data = new Node(7, 0,
                new Node(1, 1,
                        new Node(6, 2, null)));
        Node data2 = new Node(5, 0,
                new Node(9, 1,
                        new Node(2, 2, null)));
        Node result = new Node(2, 0,
                new Node(1, 1,
                        new Node(9, 2, null)));
        Node data3 = new Node(1, 0,
                new Node(5, 1,
                        new Node(9, 2, null)));
        Node data4 = new Node(2, 0,
                new Node(3, 1,
                        new Node(6, 2,
                                new Node(9, 3,
                                        new Node(1, 4, null)))));
        Node result2 = new Node(3, 0,
                new Node(8, 1,
                        new Node(5, 2,
                                new Node(0, 3,
                                        new Node(2, 4, null)))));
        Node data5 = new Node(9, 0,
                new Node(7, 1,
                        new Node(8, 2, null)));
        Node data6 = new Node(6, 0,
                new Node(8, 1,
                        new Node(5, 2, null)));
        Node result3 = new Node(5, 0,
                new Node(6, 1,
                        new Node(4, 2,
                                new Node(1, 3, null))));
        assertEquals(result, sum(data, data2));
        assertEquals(result2, sum(data4, data3));
        assertEquals(result3, sum(data5, data6));
        assertEquals(result2, sumRecursively(data4, data3, 0, 0));
        assertEquals(result3, sumRecursively(data5, data6, 0, 0));
    }

    private Node sum(Node data, Node data2) {
        int adding = 0;
        Node current = data;
        Node current2 = data2;
        Node result = new Node(0, 0, null);
        Node currentResult = result;
        while (current != null || current2 != null) {
            if (current == null) {
                current = new Node(0, current2.index, null);
            }
            if (current2 == null) {
                current2 = new Node(0, current.index, null);
            }
            int sum = current.value + current2.value + adding;
            adding = sum / 10;
            currentResult.value = sum % 10;
            currentResult.index = current.index;
            if (current.next != null || current2.next != null) {
                currentResult.next = new Node(0, 0, null);
            }
            if (current.next == null && current2.next == null) {
                if (adding > 0) {
                    currentResult.next = new Node(adding, current.index + 1, null);
                }
            }
            currentResult = currentResult.next;
            current = current.next;
            current2 = current2.next;
        }
        return result;
    }

    private Node sumRecursively(Node data, Node data2, int adding, int index) {
        if (data == null && data2 == null) {
            if (adding > 0) {
                return new Node(adding, index, null);
            }
            return null;
        }
        if (data == null) {
            int sum = data2.value + adding;
            return new Node(sum % 10, index, sumRecursively(null, data2.next, sum / 10, data2.index + 1));
        }
        if (data2 == null) {
            int sum = data.value + adding;
            return new Node(sum % 10, index, sumRecursively(data.next, null, sum / 10, data.index + 1));
        }
        int sum = data.value + data2.value + adding;
        return new Node(sum % 10, index, sumRecursively(data.next, data2.next, sum / 10, data.index + 1));
    }

    @Test
    void sumListsReversed() {
        Node data = new Node(6, 0,
                new Node(1, 1,
                        new Node(7, 2, null)));
        Node data2 = new Node(2, 0,
                new Node(9, 1,
                        new Node(5, 2, null)));
        Node result = new Node(9, 0,
                new Node(1, 1,
                        new Node(2, 2, null)));
        Node data3 = new Node(9, 0,
                new Node(5, 1,
                        new Node(1, 2, null)));
        Node data4 = new Node(1, 0,
                new Node(9, 1,
                        new Node(6, 2,
                                new Node(3, 3,
                                        new Node(2, 4, null)))));
        Node result2 = new Node(2, 0,
                new Node(0, 1,
                        new Node(5, 2,
                                new Node(8, 3,
                                        new Node(3, 4, null)))));
        Node data5 = new Node(8, 0,
                new Node(7, 1,
                        new Node(9, 2, null)));
        Node data6 = new Node(5, 0,
                new Node(8, 1,
                        new Node(6, 2, null)));
        Node result3 = new Node(1, 0,
                new Node(4, 1,
                        new Node(6, 2,
                                new Node(5, 3, null))));
        assertEquals(result, sumReversed(data, data2));
        assertEquals(result2, sumReversed(data3, data4));
        assertEquals(result3, sumReversed(data5, data6));
        assertEquals(result, sumReversed2(data, data2));
        assertEquals(result2, sumReversed2(data3, data4));
        assertEquals(result3, sumReversed2(data5, data6));
    }

    private Node sumReversed(Node data, Node data2) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        Node current = data;
        Node current2 = data2;
        while (current != null || current2 != null) {
            if (current != null) {
                stringBuilder.append(current.value);
                current = current.next;
            }
            if (current2 != null) {
                stringBuilder2.append(current2.value);
                current2 = current2.next;
            }
        }
        int value = Integer.parseInt(stringBuilder.toString());
        int value2 = Integer.parseInt(stringBuilder2.toString());
        int sum = value + value2;
        char[] chars = ((Integer) sum).toString().toCharArray();
        Node result = new Node(0, 0, null);
        Node currentResult = result;
        for (int i = 0; i < chars.length; i++) {
            currentResult.value = Integer.parseInt(String.valueOf(chars[i]));
            currentResult.index = i;
            if (i + 1 < chars.length) {
                currentResult.next = new Node(0, 0,  null);
            }
            currentResult = currentResult.next;
        }
        return result;
    }

    private Node sumReversed2(Node data, Node data2) {
        Node preparedData = data;
        Node preparedData2 = data2;
        Node current = data;
        Node current2 = data2;
        while (current.next != null || current2.next != null) {
            if (current.next != null) {
                current = current.next;
            }
            if (current2.next != null) {
                current2 = current2.next;
            }
        }
        int lastIndex = Math.max(current.index, current2.index);
        int minIndex = Math.min(current.index, current2.index);
        int diff = (lastIndex + 1) - (minIndex + 1);
        if (diff != 0) {
            Node node = new Node(0, 0, null);
            Node current3 = node;
            for (int i = 1; i < diff; i++) {
                current3.next = new Node(0, i, null);
                current3 = current3.next;
            }
            if (current.index < lastIndex) {
                current3.next = preparedData;
                preparedData = node;
                preparedData = reindex(preparedData);
            }
            if (current2.index < lastIndex) {
                current3.next = preparedData2;
                preparedData2 = node;
                preparedData2 = reindex(preparedData2);
            }
        }
        return sumReversedRecursively(preparedData, preparedData2, lastIndex, 0, null);
    }

    private Node sumReversedRecursively(Node data, Node data2, int lastIndex, int adding, Node result) {
        if (lastIndex == -1) {
            if (adding > 0) {
                Node node = new Node(adding, lastIndex, result);
                return reindex(node);
            }
            return result;
        }
        Node current = data;
        Node current2 = data2;
        while (current.index != lastIndex && current2.index != lastIndex) {
            current = current.next;
            current2 = current2.next;
        }
        int sum = current.value + current2.value + adding;
        int newValue = sum % 10;
        int newAdding = sum / 10;
        Node newResult = new Node(newValue, lastIndex, result);
        return sumReversedRecursively(data, data2, lastIndex - 1, newAdding, newResult);
    }

    private Node reindex(Node node) {
        Node result = new Node(node.value, 0, null);
        Node currentResult = result;
        Node current = node;
        for (int i = 1; current.next != null; i++) {
            current = current.next;
            Node copy = new Node(current.value, i, null);
            currentResult.next = copy;
            currentResult = copy;
        }
        return result;
    }

    //    2.6
    @Test
    void palindromeLinkedList() {
        Node data = new Node(1, 0,
                new Node(9, 1,
                        new Node(6, 2,
                                new Node(9, 3,
                                        new Node(1, 4, null)))));
        Node data2 = new Node(1, 0,
                new Node(9, 1,
                        new Node(9, 2,
                                new Node(6, 3,
                                        new Node(6, 4,
                                                new Node(9, 5,
                                                        new Node(1, 6, null)))))));
        Node data3 = new Node(1, 0,
                new Node(9, 1,
                        new Node(9, 2,
                                new Node(6, 3,
                                        new Node(6, 4,
                                                new Node(9, 5,
                                                        new Node(9, 6,
                                                                new Node(1, 7, null))))))));
        assertTrue(isPalindrome(data));
        assertFalse(isPalindrome(data2));
        assertTrue(isPalindrome(data3));
        assertTrue(isPalindromeRecursively(data, -1));
        assertFalse(isPalindromeRecursively(data2, -1));
        assertTrue(isPalindromeRecursively(data3, -1));
    }

    //    if use LinkedList then we can use 2 iterators and compare till they meet
    private boolean isPalindrome(Node data) {
        Node current = data;
        Node last = data;
        while (current != null) {
            last = current;
            current = current.next;
        }
        if (data.value != last.value) {
            return false;
        }
        current = data.next;
        if (current != null) {
            while (current.index < (last.index + 1) / 2) {
                int index = last.index - current.index;
                if (current.value != getNode(current, index).value) {
                    return false;
                }
                current = current.next;
            }
        }
        return true;
    }

    //    can be optimised using hash map or array list
    private Node getNode(Node node, int index) {
        Node current = node;
        while (current != null) {
            if (current.index == index) {
                return current;
            }
            current = current.next;
        }
        throw new IndexOutOfBoundsException();
    }

    private boolean isPalindromeRecursively(Node node, int lastIndex) {
        Node current = node;
        if (lastIndex == -1) {
            while (current.next != null) {
                current = current.next;
            }
        } else if (node.index >= lastIndex) {
            return true;
        } else {
            while (current.index < lastIndex) {
                current = current.next;
            }
        }
        return node.value == current.value && isPalindromeRecursively(node.next, current.index - 1);
    }

    private static class Node {
        int value;
        int index;
        Node next;

        Node(int value, int index, Node next) {
            this.value = value;
            this.index = index;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            Node node = (Node) o;

            if (value != node.value) { return false; }
            if (index != node.index) { return false; }
            return next != null ? next.equals(node.next) : node.next == null;
        }

        @Override
        public int hashCode() {
            int result = value;
            result = 31 * result + index;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            StringJoiner stringJoiner = new StringJoiner("; ", Node.class.getSimpleName() + "[", "]");
            StringJoiner add = stringJoiner.add("value=" + value + ",index=" + index);
            Node current = this;
            while (current.next != null) {
                current = current.next;
                add = stringJoiner.add("value=" + current.value + ",index=" + current.index);
            }
            return add.toString();
        }
    }
}
