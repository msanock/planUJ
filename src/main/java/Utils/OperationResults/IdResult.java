package Utils.OperationResults;

import Connection.protocol.Packable;
import Connection.protocol.RealPackageBuilder;
import Connection.protocol.packages.ResponsePackage;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.util.UUID;

public class IdResult extends OperationResult {
    private final int id;

    public IdResult(int id) {
        this.id = id;
    }

    @Override
    public Packable toResponsePackage(UUID uuid) {
        ResponsePackage.Builder builder = new ResponsePackage.Builder();
        builder.addData(ResponsePackage.Dictionary.ID, id);
        builder.setUuid(uuid);
        builder.setSuccess(true);
        return builder.build();
    }

    public int getId() {
        return id;
    }
}
