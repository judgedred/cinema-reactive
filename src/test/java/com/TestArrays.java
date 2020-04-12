package com;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class TestArrays {

    @Test
    void name() {
        ArrayList<String> listA = new ArrayList<>();
        listA.add("a0");
        listA.add("a1");
        listA.add("a2");
        listA.add("a3");
        listA.add("a4");
        listA.add("a5");
        ArrayList<String> listB = new ArrayList<>();
        listB.add("b0");
        listB.add("b1");
        listB.add("b2");
        listB.add("b3");
        listB.add("b4");
        listB.add("b5");
        String nextA = listA.get(1);
        String currentA = "";
        int i = 1;
        for (String b : listB) {
            currentA = listA.get(i);
            if (i % 2 == 0) {
                if (listA.size() <= i + 1) {
                    listA.add(nextA);
                } else {
                    listA.set(i, nextA);
                }
                nextA = currentA;
            } else {
                if (listA.size() <= i + 1) {
                    listA.add(b);
                } else {
                    listA.set(i, b);
                }
            }
            i++;
        }
        System.out.println(listA);
    }

    @Test
    public void strings() {
        String s = "asvkugfiugsalddlasguifgukvsa";
        Character[] characters = s.chars()
                .mapToObj(c -> (char)c)
                .toArray(Character[]::new);
        ArrayList<Character> letters = new ArrayList<>(new HashSet<>(Arrays.asList(characters)));
        int maxString = 0;
        ArrayList<Character> result = new ArrayList<>();
        for(int i = 0; i < letters.size() - 1; i++) {
            Character first = letters.get(i);
            for (int j = 1; j < letters.size(); j++) {
                Character second = letters.get(j);
                ArrayList<Character> newString = new ArrayList<>();
                for (int k = 0, l = 0; k < characters.length; k++) {
                    if (characters[k].equals(first) || characters[k].equals(second)) {
                        if (l != 0 && newString.get(l - 1).equals(characters[k])) {
                            newString = new ArrayList<>();
                            break;
                        }
                        newString.add(characters[k]);
                        l++;
                    }
                }
                if (maxString < newString.size()) {
                    maxString = newString.size();
                }
            }
        }
     }

    @Test
    public void nodeSum() {
        Assert.assertThat(sum(buildNodeTree()), CoreMatchers.is(45));
    }

    private int sum(Node node) {
        int sum = 0;
        for (Node child : node.children) {
            sum += sum(child);
        }
        return node.value + sum;
    }

    private Node buildNodeTree() {
        return new Node(1, Arrays.asList(
                        new Node(2, Arrays.asList(
                                new Node(3, Collections.emptyList()),
                                new Node(4, Collections.emptyList()))),
                        new Node(5, Arrays.asList(
                                new Node(6, Collections.emptyList()),
                                new Node(7, Collections.emptyList()))),
                        new Node(8, Arrays.asList(
                                new Node(9, Collections.emptyList())))));
    }

    private static class Node {
        private Integer value;
        private List<Node> children;
        private Node(Integer value, List<Node> children) {
            this.children = children;
            this.value = value;
        }
    }
}
