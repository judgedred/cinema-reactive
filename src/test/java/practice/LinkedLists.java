package practice;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedLists {

    //    2.1
    @Test
    void removeDups() {
        LinkedList<Integer> data = new LinkedList<>(Arrays.asList(5, 6, 7, 5, 2, 5, 7, 4));
        LinkedList<Integer> expected = new LinkedList<>(Arrays.asList(5, 6, 7, 2, 4));
        assertEquals(expected, removeDuplicates2(data));
        Node data2 = new Node(5, 0,
                new Node(6, 1,
                        new Node(7, 2,
                                new Node(5, 3,
                                        new Node(2, 4,
                                                new Node(5, 5,
                                                        new Node(7, 6,
                                                                new Node(4, 7, null))))))));
        Node expected2 = new Node(5, 0,
                new Node(6, 1,
                        new Node(7, 2,
                                new Node(2, 4,
                                        new Node(4, 7, null)))));
        assertEquals(expected2, removeDuplicates3(data2));
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

    private Node removeDuplicates3(Node data) {
        Node current = data;
        while (current.next != null) {
            Node current2 = current;
            while (current2.next != null) {
                if (current.value == current2.next.value) {
                    current2.next = current2.next.next;
                }
                current2 = current2.next;
            }
            current = current.next;
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
        assertEquals((Integer) 2, findElement2(data, 4));
        System.out.println("index= " + findRecursively2(data, 4));
        assertEquals((Integer) 2, findRecursively3(data, 4, new Index()));
        assertNull(findRecursively3(data, 9, new Index()));
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
                return find(data, index);
//        return findRecursively(data, index);
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

    private Integer findElement2(Node data, int numberToLast) {
        if (data == null) {
            return null;
        }
        Node current = data;
        Node current2 = data;
        int count = 0;
        while (current2.next != null && count < numberToLast) {
            current2 = current2.next;
            count++;
        }
        while (current2 != null) {
            current2 = current2.next;
            current = current.next;
        }
        return requireNonNull(current).value;
    }

//    requires additional O(n) space
    private Integer findRecursively(Node data, int index) {
        if (data == null) {
            return null;
        }
        if (data.index == index) {
            return data.value;
        }
        return findRecursively(data.next, index);
    }

    private Integer findRecursively2(Node data, int numberToLast) {
        if (data == null) {
            return 0;
        }
        Integer index = findRecursively2(data.next, numberToLast);
        index++;
        if (index == numberToLast) {
            System.out.println(data.value);
        }
        return index;
    }

    private Integer findRecursively3(Node data, int numberToLast, Index index) {
        if (data == null) {
            index.index = 0;
            return null;
        }
        Integer value = findRecursively3(data.next, numberToLast, index);
        index.index++;
        if (index.index == numberToLast) {
            return data.value;
        }
        return value;
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

//    won't work for the last node
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

//    2.4
    @Test
    void partition() {
        Node data = new Node(3, 0,
                new Node(5, 1,
                        new Node(8, 2,
                                new Node(5, 3,
                                        new Node(10, 4,
                                                new Node(2, 5,
                                                        new Node(1, 6, null)))))));
        Node data2 = new Node(3, 0,
                new Node(5, 1,
                        new Node(8, 2,
                                new Node(5, 3,
                                        new Node(10, 4,
                                                new Node(2, 5,
                                                        new Node(1, 6, null)))))));
        Node expected = new Node(3, 0,
                new Node(2, 1,
                        new Node(1, 2,
                                new Node(5, 3,
                                        new Node(8, 4,
                                                new Node(5, 5,
                                                        new Node(10, 6, null)))))));
        Node expected2 = new Node(1, 0,
                new Node(2, 1,
                        new Node(3, 2,
                                new Node(5, 3,
                                        new Node(8, 4,
                                                new Node(5, 5,
                                                        new Node(10, 6, null)))))));
        Node expected3 = new Node(1, 0,
                new Node(2, 1,
                        new Node(10, 2,
                                new Node(5, 3,
                                        new Node(8, 4,
                                                new Node(5, 5,
                                                        new Node(3, 6, null)))))));
        assertEquals(expected, doPartition(data, 5));
        assertEquals(expected2, doPartition2(data, 5));
        assertEquals(expected3, doPartition2(data2, 11));
    }

    private Node doPartition(Node data, int pivot) {
        if (data == null) {
            return null;
        }
        Node less = null;
        Node lessCurrent = null;
        Node more = null;
        Node moreCurrent = null;
        Node current = data;
        while (current != null) {
            if (current.value < pivot) {
                if (less == null) {
                    less = new Node(current.value, current.index, null);
                    lessCurrent = less;
                } else {
                    lessCurrent.next = new Node(current.value, current.index, null);
                    lessCurrent = lessCurrent.next;
                }
            } else {
                if (more == null) {
                    more = new Node(current.value, current.index, null);
                    moreCurrent = more;
                } else {
                    moreCurrent.next = new Node(current.value, current.index, null);
                    moreCurrent = moreCurrent.next;
                }
            }
            current = current.next;
        }
        if (less == null) {
            return reindex(more);
        }
        lessCurrent.next = more;
        return reindex(less);
    }

    private Node doPartition2(Node data, int pivot) {
        if (data == null) {
            return null;
        }
        Node head = data;
        Node tail = data;
        Node current = data.next;
        while (current != null) {
            Node next = current.next;
            if (current.value < pivot) {
                current.next = head;
                head = current;
            } else {
                tail.next = current;
                tail = current;
            }
            current = next;
        }
        tail.next = null;
        return reindex(head);
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
        if (data == null && data2 == null) {
            return null;
        }
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
        if (data == null && data2 == null) {
            return null;
        }
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
//        return sumReversedRecursively(preparedData, preparedData2, lastIndex, 0, null);
        return sumReversedRecursively2(preparedData, preparedData2, new Adding(), 0);
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

    private Node sumReversedRecursively2(Node data, Node data2, Adding adding, int index) {
        if (data == null || data2 == null) {
            return null;
        }
        Node result = sumReversedRecursively2(data.next, data2.next, adding, index + 1);
        int sum = data.value + data2.value + adding.value;
        int newValue = sum % 10;
        adding.value = sum / 10;
        Node newResult = new Node(newValue, index, result);
        if (index == 0) {
            if (adding.value > 0) {
                newResult = new Node(adding.value, -1, newResult);
                return reindex(newResult);
            }
        }
        return newResult;
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
        assertTrue(isPalindromeRecursively2(data, new NodeIterator(data)));
        assertFalse(isPalindromeRecursively2(data2, new NodeIterator(data2)));
        assertTrue(isPalindromeRecursively2(data3, new NodeIterator(data3)));
//        another solution is to create a new reversed linked list and then compare
//        another solution is to use a stack for the first half of the linked list
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

    private boolean isPalindromeRecursively2(Node current, NodeIterator headIterator) {
        if (current == null) {
            return true;
        }
        boolean result = isPalindromeRecursively2(current.next, headIterator);
        int headValue = headIterator.get().value;
        headIterator.next();
        return result && current.value == headValue;
    }

//    2.7
    @Test
    void intersection() {
        Node intersection = new Node(5, 1, new Node(1, 2, null));
        Node data = new Node(9, 0, intersection);
        Node data2 = new Node(1, 0,
                new Node(9, 1,
                        new Node(6, 2,
                                intersection)));
        Node data3 = new Node(9, 0,
                new Node(10, 1,
                        new Node(6 , 2, null)));
        Node data4 = new Node(1, 0,
                new Node(9, 1,
                        new Node(6, 2,
                                intersection)));
        assertEquals(intersection, getIntersection(data, data2));
        assertNull(getIntersection(data3, data4));
        assertEquals(intersection, getIntersection(intersection, data2));
        assertEquals(intersection, getIntersection(intersection, intersection));

        assertEquals(intersection, getIntersection2(data, data2));
        assertNull(getIntersection2(data3, data4));
        assertEquals(intersection, getIntersection2(intersection, data2));
        assertEquals(intersection, getIntersection2(intersection, intersection));

        assertEquals(intersection, getIntersection3(data, data2, -100, -100));
        assertNull(getIntersection3(data3, data4, -100, -100));
        assertEquals(intersection, getIntersection3(intersection, data2, -100, -100));
        assertEquals(intersection, getIntersection3(intersection, intersection, -100, -100));

        assertEquals(intersection, getIntersection4(data, data2));
        assertNull(getIntersection4(data3, data4));
        assertEquals(intersection, getIntersection4(intersection, data2));
        assertEquals(intersection, getIntersection4(intersection, intersection));

        assertEquals(intersection, getIntersectionRecursively(data, data2));
//        assertNull(getIntersection4(data3, data4));
//        assertEquals(intersection, getIntersection4(intersection, data2));
//        assertEquals(intersection, getIntersection4(intersection, intersection));
    }

    private Node getIntersection(Node data, Node data2) {
        Node current = data;
        while (current != null) {
            Node current2 = data2;
            while (current2 != null) {
                if (current == current2) {
                    return current;
                }
                current2 = current2.next;
            }
            current = current.next;
        }
        return null;
    }

    private Node getIntersection2(Node data, Node data2) {
        Map<Node, Node> map = new HashMap<>();
        Node current2 = data2;
        while (current2 != null) {
            map.put(current2, current2);
            current2 = current2.next;
        }
        Node current = data;
        while (current != null) {
            if (map.get(current) != null) {
                return map.get(current);
            }
            current = current.next;
        }
        return null;
    }

    private Node getIntersection3(Node data, Node data2, int index, int index2) {
        if (index == -1) {
            return data;
        }
        if (index2 == -1) {
            return data2;
        }
        Node current = data;
        Node current2 = data2;
        if (index == -100 && index2 == -100) {
            int lastIndex = 0;
            int lastIndex2 = 0;
            while (current.next != null || current2.next != null) {
                if (current.next != null) {
                    current = current.next;
                    lastIndex++;
                }
                if (current2.next != null) {
                    current2 = current2.next;
                    lastIndex2++;
                }
            }
            if (current != current2) {
                return null;
            }
            return getIntersection3(data, data2, lastIndex - 1, lastIndex2 - 1);
        } else {
            int currentIndex = 0;
            int currentIndex2 = 0;
            while (currentIndex != index || currentIndex2 != index2) {
                if (currentIndex != index) {
                    current = current.next;
                    currentIndex++;
                }
                if (currentIndex2 != index2) {
                    current2 = current2.next;
                    currentIndex2++;
                }
            }
            if (current != current2) {
                return current.next;
            }
            return getIntersection3(data, data2, currentIndex - 1, currentIndex2 - 1);
        }
    }

    private Node getIntersection4(Node data, Node data2) {
        if (data == null || data2 == null) {
            return null;
        }
        Node current = data;
        Node current2 = data2;
        int lastIndex = 0;
        int lastIndex2 = 0;
        while (current.next != null || current2.next != null) {
            if (current.next != null) {
                current = current.next;
                lastIndex++;
            }
            if (current2.next != null) {
                current2 = current2.next;
                lastIndex2++;
            }
        }
        if (current != current2) {
            return null;
        }
        current = data;
        current2 = data2;
        if (lastIndex != lastIndex2) {
            if (lastIndex > lastIndex2) {
                int startIndex = lastIndex - lastIndex2;
                for (int i = 0; i < startIndex; i++) {
                    current = current.next;
                }
            } else {
                int startIndex = lastIndex2 - lastIndex;
                for (int i = 0; i < startIndex; i++) {
                    current2 = current2.next;
                }
            }
        }
        while (current != current2) {
            current = current.next;
            current2 = current2.next;
        }
        return current;
    }

//    will work only for equal lists. so on of them should be prepared to start from tail.
    private Node getIntersectionRecursively(Node data, Node data2) {
        if (data == null && data2 == null) {
            return null;
        } else if (data == null || data2 == null) {
            throw new UnsupportedOperationException("Only equal lists are supported.");
        }
        Node node = getIntersectionRecursively(data.next, data2.next);
        if (data == data2) {
            return data;
        } else {
            return node;
        }
    }

//    2.8
    @Test
    void loopDetection() {
        Node circular = new Node(5, 5, new Node(1, 6, null));
        Node data = new Node(1, 0,
                new Node(9, 1,
                        new Node(6, 2,
                                new Node(3, 3,
                                        new Node(2, 4, circular)))));
//        assertNull(getLoopNode5(data));
        Node current = circular;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new Node(4, 7, circular);
//        assertEquals(circular, getLoopNode5(data));
        Node circular2 = new Node(4, 5, new Node(5, 6,
                new Node(6, 7,
                        new Node(7, 8,
                                new Node(8, 9,
                                        new Node(9, 10, null))))));
        Node data2 = new Node(1, 0,
                new Node(2, 1,
                        new Node(3, 2, circular2)));
        current = circular2;
        while (current.next != null) {
            current = current.next;
        }
        current.next = circular2;
        assertEquals(circular2, getLoopNode5(data2));
    }

    private Node getLoopNode(Node data) {
        Map<Node, Node> map = new HashMap<>();
        Node current = data;
        while (current.next != null) {
            Node node = map.put(current, current);
            if (node != null) {
                return node;
            }
            current = current.next;
        }
        return null;
    }

    private Node getLoopNode2(Node data) {
        Node current = data;
        while (current.next != null) {
            if (current.index == -1) {
                return current;
            }
            current.index = -1;
            current = current.next;
        }
        return null;
    }

    private Node getLoopNode3(Node data) {
        Node marker = new Node(-1, -1, null);
        Node current = data;
        while (current.next != null) {
            if (current.next == marker) {
                return current;
            }
            Node prev = current;
            current = current.next;
            prev.next = marker;
        }
        return null;
    }

    private Node getLoopNode4(Node data) {
        if (data == null || data.next == null) {
            return null;
        }
        Node current = data;
        Node current2 = data.next;
        while (current2 != null && current2.next != null) {
            boolean isLoop = current2 == current;   // they will collide anyway. hopping over is not possible.
            if (isLoop) {
                while (true) {
                    current = current.next;
                    current2 = current.next;
                    Node current3 = data;
                    while (current3.next != current2) {
                        current3 = current3.next;
                    }
                    if (current3 != current) {
                        return current2;
                    }
                }
            }
            current2 = current2.next;
            current2 = current2.next;
            current = current.next;
        }
        return null;
    }

    private Node getLoopNode5(Node data) {
        if (data == null || data.next == null) {
            return null;
        }
        Node current = data;
        Node current2 = data;
        while (current2 != null && current2.next != null) {
            current2 = current2.next;
            current2 = current2.next;
            current = current.next;
            boolean isLoop = current2 == current;
            if (isLoop) {
                current = data;
                while (current != current2) {   // they will meet at the start of the loop anyway
                    current = current.next;
                    current2 = current2.next;
                }
                return current;
            }
        }
        return null;
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

    private static class Index {
        int index;
    }

    private static class Adding {
        int value;
    }

    private static class NodeIterator {
        private Node pointer;

        private NodeIterator(Node node) {
            this.pointer = node;
        }

        private Node get() {
            return pointer;
        }

        private void next() {
            pointer = pointer.next;
        }
    }
}






