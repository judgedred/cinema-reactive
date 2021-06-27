package practice;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TreesAndGraphs {

//    4.1
    @Test
    public void routeBetweenNodes() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        node1.children = new Node[]{node2};
        node2.children = new Node[]{node3, node4, node5};
        node3.children = new Node[]{node7};
        node4.children = new Node[]{node3};
        node5.children = new Node[]{node6};
        node6.children = new Node[]{node7};
        Graph graph = new Graph(new Node[]{node1, node2, node3, node4, node5, node6, node7});
        assertTrue(routeFound(graph, 0, 6));
        assertTrue(routeFound(graph, 3, 6));
        assertFalse(routeFound(graph, 3, 5));
    }

    private boolean routeFound(Graph graph, int start, int end) {
        Node startNode = graph.nodes[start];
        Node endNode = graph.nodes[end];
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(startNode);
        Set<Node> searched = new HashSet<>();
        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            if (!searched.contains(node)) {
                if (node == endNode) {
                    return true;
                }
                if (node.children != null) {
                    nodes.addAll(Arrays.asList(node.children));
                }
                searched.add(node);
            }
        }
        return false;
    }

//    4.2
    @Test
    public void minimalTree() {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        String expected = "BinaryNode{value=4, left=BinaryNode{value=2, left=BinaryNode{value=1, left=null, right=null}, right=BinaryNode{value=3, left=null, right=null}}, right=BinaryNode{value=6, left=BinaryNode{value=5, left=null, right=null}, right=BinaryNode{value=7, left=null, right=BinaryNode{value=8, left=null, right=null}}}}";
        int[] data2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        String expected2 = "BinaryNode{value=6, left=BinaryNode{value=3, left=BinaryNode{value=1, left=null, right=BinaryNode{value=2, left=null, right=null}}, right=BinaryNode{value=4, left=null, right=BinaryNode{value=5, left=null, right=null}}}, right=BinaryNode{value=9, left=BinaryNode{value=7, left=null, right=BinaryNode{value=8, left=null, right=null}}, right=BinaryNode{value=11, left=BinaryNode{value=10, left=null, right=null}, right=BinaryNode{value=12, left=null, right=null}}}}";
        BinaryNode result = buildMinimalTree(data, 0, data.length - 1);
        BinaryNode result2 = buildMinimalTree(data2, 0, data2.length - 1);
        System.out.println(result);
        assertNotNull(result);
        assertEquals(expected, result.toString());
        assertNotNull(result2);
        assertEquals(expected2, result2.toString());
    }

//    unnecessary list creation
    private BinaryNode buildMinimalTree(int[] data) {
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(IntStream.of(data).boxed().collect(Collectors.toList()));
        BinaryNode root = null;
        while (!queue.isEmpty()) {
            List<Integer> list = queue.poll();
            int next = list.size() == 1 ? 0 : (list.size() - 1) / 2;
            int nextElement = list.get(next);
            if (root == null) {
                root = new BinaryNode(nextElement);
            } else {
                insertRecursive(root, nextElement);
            }
            List<Integer> left = new ArrayList<>();
            List<Integer> right = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (i < next) {
                    left.add(list.get(i));
                }
                if (i > next) {
                    right.add(list.get(i));
                }
            }
            if (!left.isEmpty()) {
                queue.add(left);
            }
            if (!right.isEmpty()) {
                queue.add(right);
            }
        }
        return root;
    }

    private void insertRecursive(BinaryNode node, int element) {
        if (element < node.value) {
            if (node.left != null) {
                insertRecursive(node.left, element);
            } else {
                node.left = new BinaryNode(element);
            }
        } else {
            if (node.right != null) {
                insertRecursive(node.right, element);
            } else {
                node.right = new BinaryNode(element);
            }
        }
    }

    private BinaryNode buildMinimalTree(int[] data, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        BinaryNode node = new BinaryNode(data[mid]);
        node.left = buildMinimalTree(data, start, mid - 1);
        node.right = buildMinimalTree(data, mid + 1, end);
        return node;
    }

//    4.3
    @Test
    public void listOfDepths() {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] data2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        BinaryNode tree = buildMinimalTree(data, 0, data.length - 1);
        BinaryNode tree2 = buildMinimalTree(data2, 0, data2.length - 1);
        List<LinkedList<BinaryNode>> result = new ArrayList<>();
        buildDepthRecursive(tree, 0, result);
        assertEquals(4, result.size());
        List<LinkedList<BinaryNode>> result2 = new ArrayList<>();
        buildDepthRecursive(tree2, 0, result2);
        assertEquals(4, result2.size());
        int[] result2values = {6, 3, 9, 1, 4, 7, 11, 2, 5, 8, 10, 12};
        int[] result2sizes = {1, 2, 4, 5};
        int valueIndex = 0;
        int sizeIndex = 0;
        for (LinkedList<BinaryNode> list : result2) {
            assertEquals(result2sizes[sizeIndex++], list.size());
            for (BinaryNode node : list) {
                assertEquals(result2values[valueIndex++], node.value);
            }
        }
    }

    private void buildDepthRecursive(BinaryNode node, int depth, List<LinkedList<BinaryNode>> depths) {
        if (node == null) {
            return;
        }
        LinkedList<BinaryNode> linkedList;
        if (depth < depths.size()) {
            linkedList = depths.get(depth);
        } else {
            linkedList = new LinkedList<>();
            depths.add(linkedList);
        }
        linkedList.add(node);
        buildDepthRecursive(node.left, depth + 1, depths);
        buildDepthRecursive(node.right, depth + 1, depths);
    }

//    4.4
    @Test
    public void checkBalanced() {
        BinaryNode node1 = new BinaryNode(4);
        BinaryNode node2 = new BinaryNode(2);
        BinaryNode node3 = new BinaryNode(1);
        BinaryNode node4 = new BinaryNode(-4);
        BinaryNode node5 = new BinaryNode(3);
        BinaryNode node6 = new BinaryNode(6);
        BinaryNode node7 = new BinaryNode(5);
        BinaryNode node8 = new BinaryNode(-1);
        BinaryNode node9 = new BinaryNode(-2);
        BinaryNode node10 = new BinaryNode(-3);
        BinaryNode node11 = new BinaryNode(10);
        BinaryNode node12 = new BinaryNode(7);
        BinaryNode node13 = new BinaryNode(8);
        BinaryNode node14 = new BinaryNode(9);
        BinaryNode node15 = new BinaryNode(-5);
        BinaryNode node16 = new BinaryNode(11);
        BinaryNode node17 = new BinaryNode(-6);
        BinaryNode node18 = new BinaryNode(-7);
        node1.left = node2;
        node1.right = node6;
        node2.left = node3;
        node2.right = node5;
        node3.left = node4;
        node3.right = node17;
        node4.left = node15;
        node5.right = node16;
        node6.left = node7;
        node6.right = node12;
        node7.left = node8;
        node7.right = node11;
        node8.left = node9;
        node12.right = node13;
        node12.left = node18;
        node13.right = node14;
        assertTrue(isBalanced(node1));
        assertTrue(isBalanced2(node1));
        node9.left = node10;
        assertFalse(isBalanced(node1));
        assertFalse(isBalanced2(node1));
    }

//    O(NlogN)
    private boolean isBalanced(BinaryNode tree) {
        if (tree == null) {
            return true;
        }
        int leftDepth = getMaxDepth(tree.left, 1);
        int rightDepth = getMaxDepth(tree.right, 1);
        return Math.abs(leftDepth - rightDepth) < 2
                && isBalanced(tree.left)
                && isBalanced(tree.right);
    }

    private int getMaxDepth(BinaryNode node, int depth) {
        if (node == null) {
            return depth - 1;
        }
        return Math.max(getMaxDepth(node.left, depth + 1), getMaxDepth(node.right, depth + 1));
    }

    private boolean isBalanced2(BinaryNode tree) {
        return getMaxDepth2(tree, 0).isBalanced;
    }

//    O(N)
//    break recursion if not balanced is detected
    private BalancedResult getMaxDepth2(BinaryNode node, int depth) {
        if (node == null) {
            return new BalancedResult(true, depth - 1);
        }
        BalancedResult leftResult = getMaxDepth2(node.left, depth + 1);
        if (!leftResult.isBalanced) {
            return leftResult;
        }
        BalancedResult rightResult = getMaxDepth2(node.right, depth + 1);
        if (!rightResult.isBalanced) {
            return rightResult;
        }
        boolean isBalanced = Math.abs(leftResult.maxDepth - rightResult.maxDepth) < 2;
        return new BalancedResult(isBalanced, Math.max(leftResult.maxDepth, rightResult.maxDepth));
    }

//    4.5
    @Test
    public void validateBST() {
        int[] data = {1, 2, 3, 4, 5, 6, 8, 9};
        BinaryNode tree = buildMinimalTree(data, 0, data.length - 1);
        assertNotNull(tree);
        assertTrue(isBST(tree));
        assertTrue(isBST2(tree));
        assertTrue(isBST3(tree, new PrevValue()));
        assertTrue(isBST4(tree, null, null));
        BinaryNode current = tree;
        while (requireNonNull(current).right != null) {
            current = current.right;
        }
        current.left = new BinaryNode(7);
        System.out.println(tree);
        assertFalse(isBST(tree));
        assertFalse(isBST2(tree));
        assertFalse(isBST3(tree, new PrevValue()));
        assertFalse(isBST4(tree, null, null));
    }

    private boolean isBST(BinaryNode tree) {
        return getMinMax(tree).isBST;
    }

//    O(N)
//    uses the property of BST that left subtree is less or equal and right subtree is more
//    moving from down to up
    private BstResult getMinMax(BinaryNode node) {
        if (node == null) {
            return new BstResult(true, null, null);
        }
        BstResult left = getMinMax(node.left);
        if (!left.isBST) {
            return left;
        }
        BstResult right = getMinMax(node.right);
        if (!right.isBST) {
            return right;
        }
        boolean isBST = (left.max == null || left.max <= node.value)        // duplicates are allowed on the left
                && (right.min == null || right.min > node.value);
        int subtreeMin = executeNullSafeFunc(left.min, right.min, Math::min, node.value);
        int subtreeMax = executeNullSafeFunc(left.max, right.max, Math::max, node.value);
        return new BstResult(isBST, Math.min(subtreeMin, node.value), Math.max(subtreeMax, node.value));
    }

    private int executeNullSafeFunc(Integer value, Integer value2, BiFunction<Integer, Integer, Integer> function,
            int defaultValue) {
        int result;
        if (value != null && value2 != null) {
            result = function.apply(value, value2);
        } else if (value == null && value2 == null) {
            result = defaultValue;
        } else if (value == null) {
            result = value2;
        } else {
            result = value;
        }
        return result;
    }

//    O(NlogN)
    private boolean isBST2(BinaryNode tree) {
        if (tree == null) {
            return true;
        }
        return subtreeIsLessOrEquals(tree.left, tree.value)
                && subtreeIsMore(tree.right, tree.value)
                && isBST2(tree.left)
                && isBST2(tree.right);
    }

//    if duplicates are allowed only at the left
    private boolean subtreeIsLessOrEquals(BinaryNode node, int value) {
        if (node == null) {
            return true;
        }
        return node.value <= value
                && subtreeIsLessOrEquals(node.left, value)
                && subtreeIsLessOrEquals(node.right, value);
    }

    private boolean subtreeIsMore(BinaryNode node, int value) {
        if (node == null) {
            return true;
        }
        return node.value > value
                && subtreeIsMore(node.left, value)
                && subtreeIsMore(node.right, value);
    }

//    O(N)
//    uses in order traversal BST property
//    won't work for duplicates
    private boolean isBST3(BinaryNode tree, PrevValue prevValue) {
        if (tree == null) {
            return true;
        }
        boolean left = isBST3(tree.left, prevValue);
        boolean current = prevValue.value == null || tree.value > prevValue.value;
        prevValue.value = tree.value;
        return left && current && isBST3(tree.right, prevValue);
    }

//    O(N)
//    moving from up to down
    private boolean isBST4(BinaryNode node, Integer min, Integer max) {
        if (node == null) {
            return true;
        }
        boolean isLessOrEqual = max == null || node.value <= max;
        boolean isMore = min == null || node.value > min;
        return isLessOrEqual
                && isMore
                && isBST4(node.left, min, node.value)
                && isBST4(node.right, node.value, max);
    }

//    4.6
    @Test
    public void successor() {
        BinaryNode node1 = new BinaryNode(20);
        BinaryNode node2 = new BinaryNode(8);
        BinaryNode node3 = new BinaryNode(4);
        BinaryNode node4 = new BinaryNode(12);
        BinaryNode node5 = new BinaryNode(10);
        BinaryNode node6 = new BinaryNode(14);
        BinaryNode node7 = new BinaryNode(22);
        BinaryNode node8 = new BinaryNode(14);
        node1.left = node2;
        node1.right = node7;
        node2.left = node3;
        node2.right = node4;
        node2.parent = node1;
        node3.parent = node2;
        node4.left = node5;
        node4.right = node6;
        node6.right = node8;
        node4.parent = node2;
        node5.parent = node4;
        node6.parent = node4;
        node7.parent = node1;
        node8.parent = node6;
        assertEquals(node5.value, nextSuccessor(node2).value);
        assertEquals(node4.value, nextSuccessor(node5).value);
        assertEquals(node6.value, nextSuccessor(node4).value);
        assertEquals(node1.value, nextSuccessor(node8).value);
        assertNull(nextSuccessor(node7));

    }

    private BinaryNode nextSuccessor(BinaryNode node) {
        BinaryNode next = traverseLeft(node.right);
        return next != null ? next : traverseParent(node);
    }

    private BinaryNode traverseLeft(BinaryNode node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node;
        }
        return traverseLeft(node.left);
    }

    private BinaryNode traverseParent(BinaryNode node) {
        if (node.parent == null) {
            return null;
        }
        BinaryNode parent = node.parent;
        return parent.left == node ? parent : traverseParent(node.parent);
    }

//    4.7
    @Test
    public void buildOrder() {
//        legend
//        Node nodeF = new Node(1);
//        Node nodeA = new Node(2);
//        Node nodeD = new Node(4);
//        Node nodeB = new Node(3);
//        Node nodeC = new Node(5);
//        Node nodeE = new Node(6);
//        Node nodeG = new Node(7);

        Graph graph2 = new Graph(null);
        graph2.addPair(2, 4);
        graph2.addPair(1, 3);
        graph2.addPair(3, 4);
        graph2.addPair(1, 2);
        graph2.addPair(4, 5);
        graph2.addSingle(6);
        graph2.build();
        List<Node> projects = buildProjects(graph2);
        assertEquals(6, projects.size());
        int[] expected = new int[]{1, 3, 2, 4, 5, 6};
        for (int i = 0; i < projects.size(); i++) {
            assertEquals(expected[i], projects.get(i).value);
        }

        Graph graph3 = new Graph(null);
        graph3.addPair(2, 4);
        graph3.addPair(1, 3);
        graph3.addPair(1, 2);
        graph3.addPair(4, 5);
        graph3.addPair(5, 3);
        graph3.addSingle(6);
        graph3.build();
        projects = buildProjects(graph3);
        assertEquals(6, projects.size());
        expected = new int[]{1, 2, 4, 5, 3, 6};
        for (int i = 0; i < projects.size(); i++) {
            assertEquals(expected[i], projects.get(i).value);
        }

        Graph graph4 = new Graph(null);
        graph4.addPair(1, 5);
        graph4.addPair(1, 3);
        graph4.addPair(1, 2);
        graph4.addPair(5, 2);
        graph4.addPair(3, 2);
        graph4.addPair(2, 6);
        graph4.addPair(3, 6);
        graph4.addPair(4, 7);
        graph4.build();
        projects = buildProjects(graph4);
        assertEquals(7, projects.size());
        expected = new int[]{1, 5, 3, 2, 6, 4, 7};
        for (int i = 0; i < projects.size(); i++) {
            assertEquals(expected[i], projects.get(i).value);
        }
    }

//    need to switch off toString
    @Test(expected = IllegalStateException.class)
    public void buildOrderCyclic() {
        Graph graph4 = new Graph(null);
        graph4.addPair(2, 4);
        graph4.addPair(1, 3);
        graph4.addPair(1, 2);
        graph4.addPair(4, 7);
        graph4.addPair(4, 5);
        graph4.addPair(5, 3);
        graph4.addPair(3, 4);
        graph4.addSingle(6);
        graph4.build();
    }

    private List<Node> buildProjects(Graph graph) {
        List<Node> projects = new ArrayList<>();
        for (int i = 0; i < graph.nodes.length; i++) {
            buildProjects(graph.nodes[i], projects);
        }
        return projects;
    }

    private void buildProjects(Node node, List<Node> builtProjects) {
        if (!builtProjects.contains(node)
                && (node.parents == null || builtProjects.containsAll(Arrays.asList(node.parents)))) {
            builtProjects.add(node);
        }
        if (node.children != null) {
            for (int i = 0; i < node.children.length; i++) {
                if (!builtProjects.contains(node.children[i])
                        && (node.children[i].parents == null
                        || builtProjects.containsAll(Arrays.asList(node.children[i].parents)))) {
                    builtProjects.add(node.children[i]);
                }
            }
            for (int i = 0; i < node.children.length; i++) {
                buildProjects(node.children[i], builtProjects);
            }
        }
    }

//    4.8
    @Test
    public void firstCommonAncestor() {
        BinaryNode node1 = new BinaryNode(8);
        BinaryNode node2 = new BinaryNode(20);
        BinaryNode node3 = new BinaryNode(12);
        BinaryNode node4 = new BinaryNode(4);
        BinaryNode node5 = new BinaryNode(14);
        BinaryNode node6 = new BinaryNode(10);
        BinaryNode node7 = new BinaryNode(22);
        node1.left = node2;
        node1.right = node7;
        node2.left = node3;
        node2.right = node4;
        node2.parent = node1;
        node3.parent = node2;
        node4.left = node5;
        node4.right = node6;
        node4.parent = node2;
        node5.parent = node4;
        node6.parent = node4;
        node7.parent = node1;

        assertEquals(node4.value, requireNonNull(getAncestor(node5, node6)).value);
        assertEquals(node2.value, requireNonNull(getAncestor(node3, node5)).value);
        assertEquals(node2.value, requireNonNull(getAncestor(node6, node4)).value);
        assertEquals(node1.value, requireNonNull(getAncestor(node5, node7)).value);
        assertEquals(node4.value, requireNonNull(getAncestor(node6, node6)).value);
        assertNull(getAncestor(node1, node7));

        assertEquals(node4.value, requireNonNull(getAncestor2(node1, node5, node6)).value);
        assertEquals(node2.value, requireNonNull(getAncestor2(node1, node3, node5)).value);
        assertEquals(node2.value, requireNonNull(getAncestor2(node1, node6, node4)).value);
        assertEquals(node1.value, requireNonNull(getAncestor2(node1, node5, node7)).value);
        assertEquals(node4.value, requireNonNull(getAncestor2(node1, node6, node6)).value);
        assertNull(getAncestor2(node1, node1, node7));

        assertEquals(node4.value, requireNonNull(getAncestor3(node5, node6)).value);
        assertEquals(node2.value, requireNonNull(getAncestor3(node3, node5)).value);
        assertEquals(node2.value, requireNonNull(getAncestor3(node6, node4)).value);
        assertEquals(node1.value, requireNonNull(getAncestor3(node5, node7)).value);
        assertEquals(node4.value, requireNonNull(getAncestor3(node6, node6)).value);
        assertNull(getAncestor3(node1, node7));

        assertEquals(node4.value, requireNonNull(getAncestor4(node1, node5, node6)).value);
        assertEquals(node2.value, requireNonNull(getAncestor4(node1, node3, node5)).value);
        assertEquals(node2.value, requireNonNull(getAncestor4(node1, node6, node4)).value);
        assertEquals(node1.value, requireNonNull(getAncestor4(node1, node5, node7)).value);
        assertEquals(node4.value, requireNonNull(getAncestor4(node1, node6, node6)).value);
        assertNull(getAncestor4(node1, node1, node7));
    }

//    seems to be O(d*d)
    private BinaryNode getAncestor(BinaryNode node, BinaryNode node2) {
        BinaryNode current = node.parent;
        while (current != null) {
            if (isAncestor(current, node2)) {
                return current;
            }
            current = current.parent;
        }
        return null;
    }

    private boolean isAncestor(BinaryNode parent, BinaryNode node) {
        if (node.parent == null) {
            return false;
        }
        return parent == node.parent || isAncestor(parent, node.parent);
    }

//    O(n)
    private BinaryNode getAncestor2(BinaryNode tree, BinaryNode node, BinaryNode node2) {
        if (!isPresent(tree, node) || !isPresent(tree, node2)) {
            return null;
        }
        return getAncestor2Recursive(tree, node, node2, null);
    }

    private BinaryNode getAncestor2Recursive(BinaryNode tree, BinaryNode node, BinaryNode node2, BinaryNode parent) {
        if (tree == node || tree == node2) {
            return parent;
        }
        boolean node1Left = isPresent(tree.left, node);
        boolean node2Left = isPresent(tree.left, node2);
        if (node1Left != node2Left) {
            return tree;
        }
        return node1Left
                ? getAncestor2Recursive(tree.left, node, node2, tree)
                : getAncestor2Recursive(tree.right, node, node2, tree);
    }

    private boolean isPresent(BinaryNode tree, BinaryNode node) {
        if (tree == null) {
            return false;
        }
        return tree == node || isPresent(tree.left, node) || isPresent(tree.right, node);
    }

//    O(d)
    private BinaryNode getAncestor3(BinaryNode node, BinaryNode node2) {
        if (node == null || node2 == null) {
            return null;
        }
        int firstDepth = countDepth(node);
        int secondDepth = countDepth(node2);
        BinaryNode current = node;
        BinaryNode current2 = node2;
        if (firstDepth > secondDepth) {
            current = moveUp(node, firstDepth - secondDepth);
        }
        if (secondDepth > firstDepth) {
            current2 = moveUp(node2, secondDepth - firstDepth);
        }
        while (current.parent != current2.parent) {
            current = current.parent;
            current2 = current2.parent;
            if (current == null || current2 == null) {
                return null;
            }
        }
        return current.parent;
    }

    private int countDepth(BinaryNode node) {
        int counter = 0;
        BinaryNode current = node;
        while (current.parent != null) {
            current = current.parent;
            counter++;
        }
        return counter;
    }

    private BinaryNode moveUp(BinaryNode node, int depths) {
        BinaryNode current = node;
        int counter = depths;
        while (counter != 0) {
            current = current.parent;
            counter--;
        }
        return current;
    }

    private BinaryNode getAncestor4(BinaryNode tree, BinaryNode node, BinaryNode node2) {
        return getAncestor4Recursive(tree, node, node2).ancestor;
    }

//    getAncestor2 optimised
    private AncestorSearchResult getAncestor4Recursive(BinaryNode tree, BinaryNode node, BinaryNode node2) {
        if (tree == null) {
            return new AncestorSearchResult(false, false, null);
        }
        AncestorSearchResult leftResult = getAncestor4Recursive(tree.left, node, node2);
        if (leftResult.ancestor != null) {
            return leftResult;
        }
        AncestorSearchResult rightResult = getAncestor4Recursive(tree.right, node, node2);
        if (rightResult.ancestor != null) {
            return rightResult;
        }
        boolean nodeIsFound = leftResult.nodeIsFound || rightResult.nodeIsFound;
        boolean node2IsFound = leftResult.node2IsFound || rightResult.node2IsFound;
        AncestorSearchResult result = new AncestorSearchResult(
                nodeIsFound,
                node2IsFound,
                nodeIsFound && node2IsFound ? tree : null);
        if (tree == node || tree == node2) {
            return new AncestorSearchResult(
                    tree == node || result.nodeIsFound,
                    tree == node2 || result.node2IsFound,
                    null);
        }
        return result;
    }

//    4.9
    @Test
    public void bstSequences() {
        int[] data = {1, 2, 3, 4, 5, 6, 8, 9};
        BinaryNode tree = buildMinimalTree(data, 0, data.length - 1);
        assertNotNull(tree);
        List<List<Integer>> sequences = getSequences(tree);
        for (List<Integer> sequence : sequences) {
            System.out.println(sequence);
        }
    }

    private List<List<Integer>> getSequences(BinaryNode node) {
        if (node == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> sequencesLeft = getSequences(node.left);
        List<List<Integer>> sequencesRight = getSequences(node.right);
        List<List<Integer>> subtreeSequences = new ArrayList<>();
        if (sequencesLeft.isEmpty() && sequencesRight.isEmpty()) {
            subtreeSequences.add(Collections.singletonList(node.value));
            return subtreeSequences;
        }
//        need more complex weaving algorithm. this produces very few sequences.
        for (List<Integer> sequenceLeft : sequencesLeft) {
            if (!sequencesRight.isEmpty()) {
                for (List<Integer> sequenceRight : sequencesRight) {
                    List<Integer> subtreeSequence = new ArrayList<>();
                    subtreeSequence.add(node.value);
                    subtreeSequence.addAll(sequenceLeft);
                    subtreeSequence.addAll(sequenceRight);
                    subtreeSequences.add(subtreeSequence);
                }
            } else {
                List<Integer> subtreeSequence = new ArrayList<>();
                subtreeSequence.add(node.value);
                subtreeSequence.addAll(sequenceLeft);
                subtreeSequences.add(subtreeSequence);
            }
        }
        for (List<Integer> sequenceRight : sequencesRight) {
            if (!sequencesLeft.isEmpty()) {
                for (List<Integer> sequenceLeft : sequencesLeft) {
                    List<Integer> subtreeSequence = new ArrayList<>();
                    subtreeSequence.add(node.value);
                    subtreeSequence.addAll(sequenceRight);
                    subtreeSequence.addAll(sequenceLeft);
                    subtreeSequences.add(subtreeSequence);
                }
            } else {
                List<Integer> subtreeSequence = new ArrayList<>();
                subtreeSequence.add(node.value);
                subtreeSequence.addAll(sequenceRight);
                subtreeSequences.add(subtreeSequence);
            }
        }
        return subtreeSequences;
    }

//    4.10
    @Test
    public void checkSubtree() {
        BinaryNode node1 = new BinaryNode(8);
        BinaryNode node2 = new BinaryNode(20);
        BinaryNode node3 = new BinaryNode(12);
        BinaryNode node4 = new BinaryNode(4);
        BinaryNode node5 = new BinaryNode(14);
        BinaryNode node6 = new BinaryNode(10);
        BinaryNode node7 = new BinaryNode(22);
        BinaryNode node8 = new BinaryNode(15);
        node2.left = node1;
        node2.right = node7;
        node1.left = node4;
        node1.right = node3;
        node3.left = node6;
        node3.right = node5;
        node5.right = node8;

        BinaryNode subtree = new BinaryNode(12);
        BinaryNode snode1 = new BinaryNode(10);
        BinaryNode snode2 = new BinaryNode(14);
        BinaryNode snode3 = new BinaryNode(15);
        subtree.left = snode1;
        subtree.right = snode2;
        snode2.right = snode3;

        BinaryNode subtree2 = new BinaryNode(14);
        BinaryNode s2node1 = new BinaryNode(12);
        BinaryNode s2node2 = new BinaryNode(10);
        BinaryNode s2node3 = new BinaryNode(15);
        subtree2.left = s2node1;
        subtree2.right = s2node3;
        s2node1.left = s2node2;

        BinaryNode tree2 = new BinaryNode(12);
        BinaryNode tnode1 = new BinaryNode(12);
        BinaryNode tnode2 = new BinaryNode(12);
        BinaryNode tnode3 = new BinaryNode(12);
        BinaryNode tnode4 = new BinaryNode(13);
        BinaryNode tnode5 = new BinaryNode(12);
        BinaryNode tnode6 = new BinaryNode(12);
        tree2.left = tnode1;
        tree2.right = tnode2;
        tnode2.right = tnode4;
        tnode4.right = tnode5;
        tnode5.right = tnode6;
        BinaryNode subtree3 = new BinaryNode(12);
        subtree3.left = new BinaryNode(12);
        BinaryNode subtree4 = new BinaryNode(12);
        subtree4.right = new BinaryNode(12);

        assertTrue(isSubtree(node2.left, subtree) || isSubtree(node2.right, subtree));
        assertFalse(isSubtree(node2.left, subtree2) || isSubtree(node2.right, subtree2));
        assertFalse(isSubtree(tree2.left, subtree3) || isSubtree(tree2.right, subtree3));
        assertTrue(isSubtree(tree2.left, subtree4) || isSubtree(tree2.right, subtree4));
    }

    private boolean isSubtree(BinaryNode tree, BinaryNode subtree) {
        if (tree == null) {
            return false;
        }
        if (tree.value == subtree.value && isSubtreeFound(tree, subtree)) {
            return true;
        }
        return isSubtree(tree.left, subtree) || isSubtree(tree.right, subtree);
    }

    private boolean isSubtreeFound(BinaryNode tree, BinaryNode subtree) {
        if (subtree == null) {
            return true;
        }
        if (tree == null) {
            return false;
        }
        return tree.value == subtree.value
                && isSubtreeFound(tree.left, subtree.left)
                && isSubtreeFound(tree.right, subtree.right);
    }

//    4.11
    @Test
    public void randomNode() {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.insert(12);
        binaryTree.insert(14);
        binaryTree.insert(10);
        binaryTree.insert(15);
        System.out.println(binaryTree.binaryNode);
        assertEquals(15, binaryTree.find(15).value);
        binaryTree.delete(14);
        System.out.println(binaryTree.binaryNode);
        assertNull(binaryTree.find(14));
        binaryTree.insert(14);
        binaryTree.delete(12);
        System.out.println(binaryTree.binaryNode);
        assertNull(binaryTree.find(12));
        System.out.println("randoms:");
        System.out.println(binaryTree.getRandomNode());
        System.out.println(binaryTree.getRandomNode());
        System.out.println(binaryTree.getRandomNode());
        System.out.println(binaryTree.getRandomNode());
        System.out.println(binaryTree.getRandomNode());
    }

//    4.12
    @Test
    public void pathsWithSum() {
        BinaryNode node1 = new BinaryNode(8);
        BinaryNode node2 = new BinaryNode(10);
        BinaryNode node3 = new BinaryNode(12);
        BinaryNode node4 = new BinaryNode(12);
        BinaryNode node5 = new BinaryNode(6);
        BinaryNode node6 = new BinaryNode(2);
        BinaryNode node7 = new BinaryNode(3);
        BinaryNode node8 = new BinaryNode(11);
        BinaryNode node9 = new BinaryNode(5);
        BinaryNode node10 = new BinaryNode(4);
        BinaryNode node11 = new BinaryNode(1);
        BinaryNode node12 = new BinaryNode(20);
        BinaryNode node13 = new BinaryNode(18);
        BinaryNode node14 = new BinaryNode(38);
        BinaryNode node15 = new BinaryNode(-25);
        BinaryNode node16 = new BinaryNode(25);
        node1.left = node2;
        node1.right = node12;
        node2.left = node3;
        node2.right = node4;
        node4.left = node5;
        node4.right = node8;
        node5.left = node6;
        node5.right = node7;
        node8.left = node9;
        node8.right = node10;
        node10.left = node11;
        node12.left = node13;
        node12.right = node14;
        node14.left = node15;
        node15.left = node16;
        assertEquals(6, countPaths(node1, 38));
        assertEquals(6, countPaths2(node1, 38).paths);
    }

    private int countPaths(BinaryNode tree, int sum) {
        if (tree == null) {
            return 0;
        }
        return countSubPaths(tree, sum, 0)
                + countPaths(tree.left, sum)
                + countPaths(tree.right, sum);
    }

//    O(N*logN) in a balanced tree
//    (ON*2) in a straight line tree
    private int countSubPaths(BinaryNode tree, int sum, int currentSum) {
        if (tree == null) {
            return 0;
        }
        currentSum += tree.value;
        if (currentSum == sum) {
            return 1 + countSubPaths(tree.left, sum, currentSum) + countSubPaths(tree.right, sum, currentSum);
        }
        if (currentSum > sum) {
            return 0;
        }
        return countSubPaths(tree.left, sum, currentSum) + countSubPaths(tree.right, sum, currentSum);
    }

    private PathResult countPaths2(BinaryNode tree, int sum) {
        if (tree == null) {
            return new PathResult(Collections.emptyList(), 0);
        }
        PathResult leftResult = countPaths2(tree.left, sum);
        PathResult rightResult = countPaths2(tree.right, sum);
        List<Integer> pathSums = new ArrayList<>(leftResult.sums.size() + rightResult.sums.size() + 1);
        AtomicInteger paths = new AtomicInteger();
        Stream.concat(leftResult.sums.stream(), rightResult.sums.stream())
                .forEach(pathSum -> {
                    pathSum += tree.value;
                    if (pathSum == sum) {
                        paths.getAndIncrement();
                    }
                    pathSums.add(pathSum);
                });
        pathSums.add(tree.value);
        if (tree.value == sum) {
            paths.getAndIncrement();
        }
        return new PathResult(pathSums, paths.get() + leftResult.paths + rightResult.paths);
    }

    private static class Graph {
        Node[] nodes;
        Map<Integer, Node> allNodes = new HashMap<>();

        Graph(Node[] nodes) {
            this.nodes = nodes;
        }

        void addPair(int parent, int child) {
            Node parentNode = allNodes.getOrDefault(parent, new Node(parent));
            Node childNode = allNodes.getOrDefault(child, new Node(child));
            parentNode.children = addToArray(parentNode.children, childNode);
            childNode.parents = addToArray(childNode.parents, parentNode);
            allNodes.putIfAbsent(parentNode.value, parentNode);
            allNodes.putIfAbsent(childNode.value, childNode);
        }

        void addSingle(int parent) {
            allNodes.put(parent, new Node(parent));
        }

        void build() {
            allNodes.values().forEach(this::validate);
            for (Node node : allNodes.values()) {
                if (node.parents == null) {
                    nodes = addToArray(nodes, node);
                }
            }
        }

        private void validate(Node current) {
            if (current.status == Node.Status.VISITING) {
                throw new IllegalStateException("Cycling graph not supported. current: " + current);
            }
            current.status = Node.Status.VISITING;
            if (current.children != null) {
                for (Node child : current.children) {
                    validate(child);
                }
            }
            current.status = Node.Status.VISITED;
        }

        private Node[] addToArray(Node[] array, Node childNode) {
            if (array == null) {
                return new Node[]{childNode};
            } else {
                Node[] children = new Node[array.length + 1];
                System.arraycopy(array, 0, children, 0, array.length);
                children[children.length - 1] = childNode;
                return children;
            }
        }
    }

    private static class Node {
        int value;
        Node[] children;
        Node[] parents;
        Status status = Status.NEW;

        Node(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            Node node = (Node) o;

            return value == node.value;
        }

        @Override
        public int hashCode() {
            return value;
        }

        /*@Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "{", "}")
                    .add("value=" + value)
                    .add("children=" + Arrays.toString(children))
                    .toString();
        }*/

        private enum Status {
            NEW, VISITING, VISITED;
        }
    }

    private static class BinaryNode {
        int value;
        BinaryNode left;
        BinaryNode right;
        BinaryNode parent;
        int size = 1;

        public BinaryNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", BinaryNode.class.getSimpleName() + "{", "}").add("value=" + value)
                    .add("left=" + left)
                    .add("right=" + right)
                    .toString();
        }
    }

    private static class BinaryTree {
        private BinaryNode binaryNode;
        private int size = 0;

        BinaryNode insert(int value) {
            BinaryNode node;
            if (binaryNode == null) {
                binaryNode = new BinaryNode(value);
                node = binaryNode;
            } else {
                node = insert(binaryNode, value);
            }
            size++;
            return node;
        }

        BinaryNode find(int value) {
            return find(binaryNode, value);
        }

        void delete(int value) {
            BinaryNode node = find(binaryNode, value);
            if (node == null) {
                return;
            }
            BinaryNode parent = node.parent;
            if (parent != null) {
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                binaryNode = null;
            }
            reinsert(parent, node.left);
            reinsert(parent, node.right);
            size--;
        }

        BinaryNode getRandomNode() {
            int index = new Random().nextInt(size);
//            BinaryNode node = findByIndex(binaryNode, index, new Counter());
            BinaryNode node = findByCounter(binaryNode, index);
            if (node == null) {
                throw new IllegalStateException("Not found by index " + index);
            }
            return node;
        }

        private BinaryNode insert(BinaryNode tree, int value) {
            if (value <= tree.value) {
                if (tree.left != null) {
                    return insert(tree.left, value);
                }
                BinaryNode node = new BinaryNode(value);
                tree.left = node;
                tree.size++;
                node.parent = tree;
                return node;
            } else {
                if (tree.right != null) {
                    return insert(tree.right, value);
                }
                BinaryNode node = new BinaryNode(value);
                tree.right = node;
                tree.size++;
                node.parent = tree;
                return node;
            }
        }

        private BinaryNode find(BinaryNode tree, int value) {
            if (tree == null) {
                return null;
            }
            if (tree.value == value) {
                return tree;
            }
            if (value < tree.value) {
                return find(tree.left, value);
            } else {
                return find(tree.right, value);
            }
        }

//        O(N)
        private BinaryNode findByIndex(BinaryNode tree, int index, Counter counter) {
            if (tree == null) {
                return null;
            }
            if (index == counter.value) {
                return tree;
            }
            counter.value++;
            BinaryNode left = findByIndex(tree.left, index, counter);
            return left != null ? left : findByIndex(tree.right, index, counter);
        }

//        O(logN)
//        counter starts from 0
        private BinaryNode findByCounter(BinaryNode tree, int counter) {
            int leftSize = tree.left != null ? tree.left.size : 0;
            if (counter < leftSize) {
                return findByCounter(requireNonNull(tree.left), counter);
            }
            if (counter == leftSize) {
                return tree;
            }
            int newCounter = counter - leftSize - 1;
            return findByCounter(tree.right, newCounter);
        }

        private void reinsert(BinaryNode subtree, BinaryNode node) {
            if (node == null) {
                return;
            }
            if (subtree == null) {
                if (binaryNode == null) {
                    binaryNode = new BinaryNode(node.value);
                } else {
                    subtree = binaryNode;
                    insert(subtree, node.value);
                }
            } else {
                insert(subtree, node.value);
            }
            reinsert(subtree, node.left);
            reinsert(subtree, node.right);
        }
    }

    private static class BalancedResult {
        boolean isBalanced;
        int maxDepth;

        public BalancedResult(boolean isBalanced, int maxDepth) {
            this.isBalanced = isBalanced;
            this.maxDepth = maxDepth;
        }
    }

    private static class BstResult {
        boolean isBST;
        Integer min;
        Integer max;

        BstResult(boolean isBST, Integer min, Integer max) {
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    }

    private static class PrevValue {
        Integer value;
    }

    private static class Counter {
        int value;
    }

    private static class PathResult {
        List<Integer> sums;
        int paths;

        PathResult(List<Integer> sums, int paths) {
            this.sums = sums;
            this.paths = paths;
        }
    }

    private static class AncestorSearchResult {
        boolean nodeIsFound;
        boolean node2IsFound;
        BinaryNode ancestor;

        AncestorSearchResult(boolean nodeIsFound, boolean node2IsFound, BinaryNode ancestor) {
            this.nodeIsFound = nodeIsFound;
            this.node2IsFound = node2IsFound;
            this.ancestor = ancestor;
        }
    }
}
