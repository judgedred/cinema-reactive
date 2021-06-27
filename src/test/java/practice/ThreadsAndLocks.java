package practice;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadsAndLocks {

//    15.3
    @Test
    public void diningPhilosophers() throws InterruptedException {
        Lock chopstick1 = new ReentrantLock();
        Lock chopstick2 = new ReentrantLock();
        Lock chopstick3 = new ReentrantLock();
        Lock chopstick4 = new ReentrantLock();
        Lock chopstick5 = new ReentrantLock();
        Lock chopstick6 = new ReentrantLock();
        Philosopher philosopher1 = new Philosopher(chopstick1, chopstick2);
        Philosopher philosopher2 = new Philosopher(chopstick3, chopstick1);
        Philosopher philosopher3 = new Philosopher(chopstick4, chopstick3);
        Philosopher philosopher4 = new Philosopher(chopstick5, chopstick4);
        Philosopher philosopher5 = new Philosopher(chopstick6, chopstick5);
        Philosopher philosopher6 = new Philosopher(chopstick2, chopstick6);
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        executorService.submit(philosopher6);
        executorService.submit(philosopher5);
        executorService.submit(philosopher4);
        executorService.submit(philosopher3);
        executorService.submit(philosopher2);
        executorService.submit(philosopher1);
        executorService.shutdown();
        boolean success = executorService.awaitTermination(60, TimeUnit.SECONDS);
        assertTrue(success);
    }

//    15.5
    @Test
    public void callInOrder() throws InterruptedException {
        OrderedCall orderedCall = new OrderedCall();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(orderedCall::first);
        executorService.submit(orderedCall::second);
        executorService.submit(orderedCall::third);
        executorService.submit(orderedCall::first);
        executorService.submit(orderedCall::second);
        executorService.submit(orderedCall::third);
        executorService.shutdown();
        boolean success = executorService.awaitTermination(10, TimeUnit.SECONDS);
        assertTrue(success);
    }

//    15.7
    @Test
    public void fizzBuzz() throws InterruptedException {
        FizzBuzzExecutor fizzBuzzExecutor = new FizzBuzzExecutor();
        fizzBuzzExecutor.execute(0, 12);
    }

    private static class Philosopher implements Runnable {

//        we can numerate chopsticks
//        each philosopher will take the smaller number first
//        this will conclude in the last philosopher taking (waiting) the right chopstick first that would break the cycling dependency
        private final Lock leftChopstick;
        private final Lock rightChopstick;

        private Philosopher(Lock leftChopstick, Lock rightChopstick) {
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
        }

//        this solution seems not to be working if all philosophers are fully synchronized
//        we could random the waiting time or the execution to desynchronize them
        @Override
        public void run() {
            try {
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " getting left chopstick...");
                if (leftChopstick.tryLock(16, TimeUnit.SECONDS)) {
                    System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " got left chopstick...");
                    System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " getting right chopstick...");
                    if (rightChopstick.tryLock(10, TimeUnit.SECONDS)) {
                        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " got right chopstick...");
                        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " eating...");
                        TimeUnit.SECONDS.sleep(7);
                    } else {
                        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " failed getting right chopstick...");
                        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " falling back left chopstick...");
                        leftChopstick.unlock();
                        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " fell back left chopstick...");
                        run();
                    }
                } else {
                    System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " failed getting left chopstick...");
                    run();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " releasing right chopstick...");
                rightChopstick.unlock();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " released right chopstick...");
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " releasing left chopstick...");
                leftChopstick.unlock();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " released left chopstick...");
            }
        }
    }

    //    15.4
    private static class DeadlockFree {

        private Lock lock = new ReentrantLock();
        private Lock internalLock = new ReentrantLock();
        private Set<Object> acquiredLocks = new HashSet<>();

        boolean acquireLock(Object caller) {
            if (caller == null) {
                return false;
            }
            try {
                internalLock.lock();
                if (!isInAcquiredLocks(caller)) {
                    acquiredLocks.add(caller);
                    lock.lock();
                    return true;
                } else {
                    return false;
                }
            } finally {
                internalLock.unlock();
            }
        }

        boolean releaseLock(Object caller) {
            if (caller == null) {
                return false;
            }
            try {
                internalLock.lock();
                if (!isInAcquiredLocks(caller)) {
                    return false;
                } else {
                    acquiredLocks.remove(caller);
                    lock.unlock();
                    return true;
                }
            } finally {
                internalLock.unlock();
            }
        }

        private boolean isInAcquiredLocks(Object caller) {
            for (Object acquiredLock : acquiredLocks) {
                if (acquiredLock == caller) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class OrderedCall {

        private Semaphore first = new Semaphore(1);
        private Semaphore second = new Semaphore(1);
        private Semaphore third = new Semaphore(1);

        public OrderedCall() {
            try {
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locking second...");
                second.acquire();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locked second.");
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locking third...");
                third.acquire();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locked third.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void first() {
            try {
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locking first...");
                first.acquire();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locked first.");
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " unlocking second...");
                second.release();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " unlocked second.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void second() {
            try {
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locking second...");
                second.acquire();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locked second.");
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " unlocking third...");
                third.release();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " unlocked third.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void third() {
            try {
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locking third...");
                third.acquire();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " locked third...");
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " unlocking first...");
                first.release();
                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread() + " unlocked first...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class FizzBuzzExecutor {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        void execute(int start, int end) throws InterruptedException {
            Thread fizzBuzz = new Thread(() -> checkFizzBuzz(start, end), "FizzBuzz");
            Thread fizz = new Thread(() -> checkFizz(start, end), "Fizz");
            Thread buzz = new Thread(() -> checkBuzz(start, end), "Buzz");
            Thread print = new Thread(() -> print(start, end), "Print");
            fizzBuzz.start();
            fizz.start();
            buzz.start();
            print.start();
            while (fizzBuzz.isAlive() || fizz.isAlive() || buzz.isAlive() || print.isAlive()) {
                TimeUnit.SECONDS.sleep(5);
            }
        }

        private void checkFizzBuzz(int start, int end) {
            IntStream.rangeClosed(start, end)
                    .filter(i -> i % 5 == 0 && i % 3 == 0)
                    .forEach(i -> System.out.println(Thread.currentThread()));
        }

        private void checkFizz(int start, int end) {
            IntStream.rangeClosed(start, end)
                    .filter(i -> i % 3 == 0 && i % 5 != 0)
                    .forEach(i -> System.out.println(Thread.currentThread()));
        }

        private void checkBuzz(int start, int end) {
            IntStream.rangeClosed(start, end)
                    .filter(i -> i % 5 == 0 && i % 3 != 0)
                    .forEach(i -> System.out.println(Thread.currentThread()));
        }

        private void print(int start, int end) {
            IntStream.rangeClosed(start, end)
                    .forEach(i -> System.out.println(Thread.currentThread() + " " + i));
        }
    }
}
