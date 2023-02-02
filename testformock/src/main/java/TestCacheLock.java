package java;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TestCacheLock {


}

class UserDateLock implements Lock {
    private final static ConcurrentHashMap<String, Thread> lockPool = new ConcurrentHashMap<>();

    private final String lockKey;

    public UserDateLock(String userCode, String date) {
        this.lockKey = userCode + "_" + date;
    }

    @Override
    public boolean tryLock() {
        return lockPool.putIfAbsent(lockKey, Thread.currentThread()) == null;
    }

    @Override
    public void unlock() {
        lockPool.remove(lockKey, Thread.currentThread());
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lock() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    private static class LockEntry {

    }
}
