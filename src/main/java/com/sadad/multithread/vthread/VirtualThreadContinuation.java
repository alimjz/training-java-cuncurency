package com.sadad.multithread.vthread;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

public class VirtualThreadContinuation {

    public static void main(String[] args) {
        ContinuationScope scope = new ContinuationScope("test");
        var continuation = new Continuation(scope,
                () -> {
                    System.out.println("Running ContinuationScope");
                    Continuation.yield(scope);
                    System.out.println("After yield.");
                });

        {
        }
        {
        }
        {
            System.out.println("Running The Main Thread!");
        }
        continuation.run();
        System.out.println("Back to the Main Thread!");
        continuation.run();
        System.out.println("Back to the Main Thread After yield.");
    }
}
