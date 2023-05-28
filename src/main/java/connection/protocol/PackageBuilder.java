package connection.protocol;

import java.io.Serializable;

public interface PackageBuilder {

    abstract void Reset();
    abstract AbstractPackage build();
}
