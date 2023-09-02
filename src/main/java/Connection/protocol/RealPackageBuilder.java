package Connection.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * kind of a template to how to implement MessageBuilder
 */
public class RealPackageBuilder implements PackageBuilder {
    private List<String> information;

    public RealPackageBuilder() {
        this.information = new ArrayList<>();
    }

    public RealPackageBuilder addInformation(String info) {
        information.add(info);
        return this;
    }
    @Override
    public void Reset() {

    }

    @Override
    public Packable build() {
        return null;
    }
}
