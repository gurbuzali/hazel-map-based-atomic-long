package co.gurbuz.hazel.atomiclong;

import com.hazelcast.map.AbstractEntryProcessor;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Map;

/**
 * @ali 21/11/13
 */
public class AtomicEntryProcessor extends AbstractEntryProcessor<String, Long> implements DataSerializable {

    long value;

    boolean getOldValue;

    boolean set;

    public AtomicEntryProcessor() {
    }

    public AtomicEntryProcessor(long value, boolean getOldValue, boolean set) {
        this.value = value;
        this.getOldValue = getOldValue;
    }

    public Object process(Map.Entry<String, Long> entry) {
        Long oldValue = entry.getValue();
        if (oldValue == null) {
            oldValue = 0L;
        }
        final long newValue;
        if (set) {
            newValue = value;
        } else {
            newValue = oldValue + value;
        }
        entry.setValue(newValue);
        if (getOldValue) {
            return oldValue;
        }
        return newValue;
    }

    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(value);
        out.writeBoolean(getOldValue);
        out.writeBoolean(set);
    }

    public void readData(ObjectDataInput in) throws IOException {
        value = in.readLong();
        getOldValue = in.readBoolean();
        set = in.readBoolean();
    }
}
