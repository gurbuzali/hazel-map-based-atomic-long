package co.gurbuz.hazel.atomiclong;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.IMap;
import com.hazelcast.spi.NodeEngine;
import com.hazelcast.spi.RemoteService;

/**
 * @ali 21/11/13
 */
public class AtomicLongService implements RemoteService {

    public static final String SERVICE_NAME = "grbz:atomicLongService";

    public static final String BACKING_MAP_PREFIX = "grbz:atomicLongBackingMap:";

    private final NodeEngine nodeEngine;

    public AtomicLongService(NodeEngine nodeEngine) {
        this.nodeEngine = nodeEngine;
    }

    public DistributedObject createDistributedObject(String objectName) {
        final IMap<String, Long> map = nodeEngine.getHazelcastInstance().getMap(BACKING_MAP_PREFIX + objectName);
        return new AtomicLongProxy(objectName, nodeEngine, this, map);
    }

    public void destroyDistributedObject(String objectName) {
        //TODO
    }

}
