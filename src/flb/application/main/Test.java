package flb.application.main;

import java.util.*;

// An interface to be implemented by everyone interested in "Hello" events
interface HelloListener {
    void someoneSaidHello();
}

// Someone who says "Hello"
class Initiater {
    private List<HelloListener> listeners = new ArrayList<HelloListener>();

    public void addListener(HelloListener toAdd) {
        listeners.add(toAdd);
    }

    public void sayHello() {
        System.out.println("Hello!!");

        // Notify everybody that may be interested.
        for (HelloListener hl : listeners)
            hl.someoneSaidHello();
    }
}

// Someone interested in "Hello" events
class Responder implements HelloListener {
    @Override
    public void someoneSaidHello() {
        System.out.println("Hello there...");
    }
}

class Test {
    public static void main(String[] args) {
        Initiater initiater = new Initiater();
        Responder responder = new Responder();
        Responder responder2 = new Responder();

        initiater.addListener(responder);
        initiater.addListener(responder2);

        initiater.sayHello();  // Prints "Hello!!!" and "Hello there..."
    }
}
