class NumberPrinter {
    private int current = 1;
    private final int MAX = 10;

    public synchronized void printOdd() {
        while (current <= MAX) {
            if (current % 2 == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(current);
            current++;
            notify();
        }
    }

    public synchronized void printEven() {
        while (current <= MAX) {
            if (current % 2 != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(current);
            current++;
            notify();
        }
    }
}

class ThreadA extends Thread {
    private final NumberPrinter printer;

    public ThreadA(NumberPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        printer.printOdd();
    }
}

class ThreadB extends Thread {
    private final NumberPrinter printer;

    public ThreadB(NumberPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        printer.printEven();
    }
}

public class Main {
    public static void main(String[] args) {
        NumberPrinter printer = new NumberPrinter();
        ThreadA threadA = new ThreadA(printer);
        ThreadB threadB = new ThreadB(printer);

        threadA.start();
        threadB.start();
    }
}
