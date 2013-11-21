package co.gurbuz.hazel.atomiclong;

import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;
import com.hazelcast.spi.AbstractDistributedObject;
import com.hazelcast.spi.NodeEngine;

/**
 * @ali 21/11/13
 */
public class AtomicLongProxy extends AbstractDistributedObject<AtomicLongService> implements IAtomicLong {

    final String name;
    final IMap<String, Long> backingMap;

    public AtomicLongProxy(String name, NodeEngine nodeEngine, AtomicLongService service, IMap<String, Long> backingMap) {
        super(nodeEngine, service);
        this.name = name;
        this.backingMap = backingMap;
    }

    public long addAndGet(long delta) {
        return (Long)backingMap.executeOnKey(name, new AtomicEntryProcessor(delta, false, false));
    }

    public boolean compareAndSet(long expect, long update) {
        return false;
    }

    public long decrementAndGet() {
        return addAndGet(-1);
    }

    public long get() {
        return backingMap.get(name);
    }

    public long getAndAdd(long delta) {
        return (Long)backingMap.executeOnKey(name, new AtomicEntryProcessor(delta, true, false));
    }

    public long getAndSet(long newValue) {
        return (Long)backingMap.executeOnKey(name, new AtomicEntryProcessor(newValue, true, false));
    }

    public long incrementAndGet() {
        return addAndGet(1);
    }

    public long getAndIncrement() {
        return getAndAdd(1);
    }

    public void set(long newValue) {
        getAndSet(newValue);
    }

    public String getName() {
        return name;
    }

    public String getServiceName() {
        return AtomicLongService.SERVICE_NAME;
    }
}
