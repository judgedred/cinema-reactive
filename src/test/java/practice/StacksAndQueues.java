package practice;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StacksAndQueues {

//    3.2
    @Test
    public void min() {
        Stack stack = new Stack();
        stack.push(2);
        stack.push(3);
        stack.push(3);
        stack.push(2);
        stack.push(1);
        assertEquals(1, stack.min());
        stack.pop();
        assertEquals(2, stack.min());
        stack.pop();
        assertEquals(2, stack.min());
    }

//    3.3
    @Test
    public void stackOfPlates() {
        StackSet stackSet = new StackSet();
        stackSet.push(1);
        stackSet.push(2);
        stackSet.push(3);
        stackSet.push(4);
        assertEquals(3, stackSet.popAt(0));
        assertEquals(0, stackSet.currentStack);
        assertEquals(4, stackSet.pop());
        assertEquals(2, stackSet.pop());
        assertEquals(1, stackSet.pop());
    }

//    3.4
    @Test
    public void queueViaStack() {
        Queue queue = new Queue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.remove());
        assertEquals(2, queue.peek());
        assertEquals(2, queue.remove());
        queue.add(4);
        assertEquals(3, queue.peek());
        assertEquals(3, queue.remove());
        assertEquals(4, queue.remove());
        assertTrue(queue.isEmpty());
    }

//    3.5
    @Test
    public void sortStack() {
        Stack stack = new Stack();
        stack.push(2);
        stack.push(1);
        stack.push(7);
        stack.push(4);
        stack.push(9);
        stack.push(3);
        sort(stack);
        assertEquals(1, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(4, stack.pop());
        assertEquals(7, stack.pop());
        assertEquals(9, stack.pop());
    }

//    3.6
    @Test
    public void animalShelter() {
        AnimalShelter animalShelter = new AnimalShelter();
        animalShelter.enqueue(new Cat());
        animalShelter.enqueue(new Dog());
        animalShelter.enqueue(new Cat());
        animalShelter.enqueue(new Dog());
        animalShelter.enqueue(new Cat());
        assertTrue(animalShelter.dequeue() instanceof Cat);
        assertTrue(animalShelter.dequeueDog() instanceof Dog);
        assertTrue(animalShelter.dequeue() instanceof Cat);
        assertTrue(animalShelter.dequeueCat() instanceof Cat);
        assertTrue(animalShelter.dequeue() instanceof Dog);
        assertNull(animalShelter.dequeue());
    }

    private void sort(Stack stack) {
        if (stack.isEmpty()) {
            return;
        }
        Stack temp = new Stack();
        int size = getSize(stack);
        for (int index = 0; index < size; index++) {
            int max = stack.pop();
            for (int i = 1; i < size - index; i++) {
                int element = stack.pop();
                if (element > max) {
                    temp.push(max);
                    max = element;
                } else {
                    temp.push(element);
                }
            }
            stack.push(max);
            while (!temp.isEmpty()) {
                stack.push(temp.pop());
            }
        }
    }

    private int getSize(Stack stack) {
        Stack temp = new Stack();
        int counter = 0;
        while (!stack.isEmpty()) {
            temp.push(stack.pop());
            counter++;
        }
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
        return counter;
    }

    private void sort2(Stack stack) {
        Stack sorted = new Stack();
        while (!stack.isEmpty()) {
            int temp = stack.pop();
            while (!sorted.isEmpty() && sorted.peek() > temp) {
                stack.push(sorted.pop());
            }
            sorted.push(temp);
        }
        while (!sorted.isEmpty()) {
            stack.push(sorted.pop());
        }
    }

    //    to implement 3 stacks in 1 the pointers should move with 3 element step
    private static class Stack {

        private static final int DEFAULT_SIZE = 24;
        private Node[] array;
        private int current;
        private Integer min;

        public Stack() {
            array = new Node[DEFAULT_SIZE];
            current = -1;
        }

        void push(int element) {
            if (current == array.length - 1) {
                array = Arrays.copyOf(array, array.length + DEFAULT_SIZE);
            }
            min = min == null ? element : Math.min(element, min);
            array[++current] = new Node(element, min);
        }

        int peek() {
            if (current == -1) {
                throw new NoSuchElementException();
            }
            return array[current].value;
        }

        int pop() {
            if (current == -1) {
                throw new NoSuchElementException();
            }
            Node element = array[current];
            current--;
            if (min == element.value) {
                min = current != -1 ? array[current].min : null;
            }
            return element.value;
        }

        boolean isEmpty() {
            return current == -1;
        }

        int min() {
            if (min == null) {
                throw new NoSuchElementException();
            }
            return min;
        }
    }

    private static class Node {
        int value;
        int min;    // for optimization another stack with mins can be used.

        Node(int value, int min) {
            this.value = value;
            this.min = min;
        }
    }

    private static class StackSet {

        private static final int DEFAULT_SIZE = 3;
        Stack[] stacks;
        int currentStack;

        StackSet() {
            stacks = new Stack[DEFAULT_SIZE];
            stacks[0] = new Stack();
            currentStack = 0;
        }

        void push(int element) {
            Stack stack = stacks[currentStack];
            if (stack.current == DEFAULT_SIZE - 1) {
                if (currentStack == stacks.length - 1) {
                    stacks = Arrays.copyOf(stacks, stacks.length + DEFAULT_SIZE);
                }
                stacks[++currentStack] = new Stack();
                stack = stacks[currentStack];
            }
            stack.push(element);
        }

        int pop() {
            if (currentStack == -1) {
                throw new NoSuchElementException();
            }
            Stack stack = stacks[currentStack];
            if (stack.current == -1) {
                currentStack--;
                if (currentStack == -1) {
                    throw new NoSuchElementException();
                }
                stack = stacks[currentStack];
            }
            return stack.pop();
        }

        int popAt(int stackIndex) {
            if (currentStack < stackIndex) {
                throw new NoSuchElementException();
            }
            Stack stack = stacks[stackIndex];
            if (stack.current == -1) {
                throw new NoSuchElementException();
            }
            int element = stack.pop();
            if (stackIndex != currentStack) {
                rebalance(stackIndex);
            }
            return element;
        }

        void rebalance(int index) {
            int lastStack = currentStack;
            currentStack = index;
            Stack[] notBalanced = new Stack[lastStack - index];
            System.arraycopy(this.stacks, index + 1, notBalanced, 0, notBalanced.length);
            for (Stack stack : notBalanced) {
                for (int j = 0; j <= stack.current; j++) {
                    push(stack.array[j].value);
                }
            }
        }
    }

    private static class Queue {

        Stack in = new Stack();
        Stack out = new Stack();

        void add(int element) {
            in.push(element);
        }

        int remove() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) {
                    out.push(in.pop());
                }
            }
            return out.pop();
        }

        int peek() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) {
                    out.push(in.pop());
                }
            }
            return out.peek();
        }

        boolean isEmpty() {
            return in.isEmpty() && out.isEmpty();
        }
    }

    private static class AnimalShelter {

        private LinkedList<Animal> all = new LinkedList<>();
        private LinkedList<Animal> cats = new LinkedList<>();
        private LinkedList<Animal> dogs = new LinkedList<>();

        void enqueue(Animal animal) {
            all.add(animal);
            if (animal instanceof Cat) {
                cats.add(animal);
            }
            if (animal instanceof Dog) {
                dogs.add(animal);
            }
        }

        Animal dequeue() {
            Animal nextCat = cats.peekLast();
            Animal nextDog = dogs.peekLast();
            Animal animal = all.pollLast();
            while (animal != nextCat && animal != nextDog && animal != null) {
                animal = all.pollLast();
            }
            if (animal == nextCat) {
                return cats.pollLast();
            }
            if (animal == nextDog) {
                return dogs.pollLast();
            }
            throw new IllegalStateException();
        }

        Animal dequeueCat() {
            return cats.pollLast();
        }

        Animal dequeueDog() {
            return dogs.pollLast();
        }
    }

    private interface Animal {}     // adding order to an animal is the best solution
    private static class Cat implements Animal {}
    private static class Dog implements Animal {}
}
