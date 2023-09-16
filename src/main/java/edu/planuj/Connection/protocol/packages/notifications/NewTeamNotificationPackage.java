package edu.planuj.Connection.protocol.packages.notifications;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.clientConnection.NotificationObserverImplementation;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;

import java.util.logging.Logger;

public class NewTeamNotificationPackage implements ClientPackable, NotificationPackage {
    private TeamInfo teamInfo;

    public NewTeamNotificationPackage(TeamInfo teamInfo) {
        this.teamInfo = teamInfo;
    }

    @Override
    public void accept(ClientPackageVisitor v) {
        NotificationObserverImplementation.getInstance().notify(this);
    }

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(TeamInfo teamInfo) {
        this.teamInfo = teamInfo;
    }

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return null;
    }

    @Override
    public void accept(NotificationPackageVisitor v, ServerClient recipient) {
        try{
            v.handleTeamNotification(this, recipient);
        }catch (DatabaseException e){
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while marking as notified: ", e);
        }
    }
}
