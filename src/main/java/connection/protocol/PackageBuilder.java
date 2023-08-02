package connection.protocol;

import java.io.Serializable;

public interface PackageBuilder {

    void Reset();
    Package build();
}
