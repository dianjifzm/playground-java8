package org.projectbarbel.playground.revisitevolatileagain;

public class SafelyPublishedImmutableLazySingleton {
    private FinalWrapper wrapper;
    private final boolean someFlag = true;
    // other final fields

    public SafelyPublishedImmutableLazySingleton get() {
      FinalWrapper w = wrapper;
      if (w == null) { // check 1
        synchronized(this) {
          w = wrapper;
          if (w == null) { // check2
            w = new FinalWrapper(new SafelyPublishedImmutableLazySingleton());
            wrapper = w;
          }
        }
      }
      return w.instance;
    }

    public boolean isSomeFlag() {
        return someFlag;
    }

    private static class FinalWrapper {
        // Initialize the value into a final field - JLS 17.5
      public final SafelyPublishedImmutableLazySingleton instance;
      public FinalWrapper(SafelyPublishedImmutableLazySingleton instance) {
        this.instance = instance;
      }
    }
  }