package pl.training.processor.adv.examples;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class References {

    public static void main(String[] args) throws InterruptedException {
        // Strong reference
        String text = "Training";

        // Soft reference
        /*var softReference = new SoftReference<>(text);
        text = null;
        System.out.println(softReference.get());
        */

        // Weak reference
        /*var weakReference = new WeakReference<>(text);
        text = null;
        System.gc();
        Thread.sleep(5_000);

        System.out.println(weakReference.get());
        */

        var weakHashMap = new WeakHashMap<>();
        var key = "key";
        weakHashMap.put(key, "Text");
        System.out.println(weakHashMap.get(key));
        key = null;
        System.out.println(weakHashMap.get(key));
    }

}
