package connection.protocol;

import java.io.Serializable;

public interface PackageBuilder {

    abstract void Reset();
    abstract Package build();
}
