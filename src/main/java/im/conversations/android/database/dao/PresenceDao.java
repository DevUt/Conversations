package im.conversations.android.database.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import eu.siacs.conversations.xmpp.Jid;
import im.conversations.android.database.entity.PresenceEntity;
import im.conversations.android.database.model.Account;
import im.conversations.android.database.model.PresenceShow;
import im.conversations.android.database.model.PresenceType;
import java.util.Arrays;

@Dao
public abstract class PresenceDao {

    @Query("DELETE FROM presence WHERE accountId=:account")
    public abstract void deletePresences(long account);

    @Query("DELETE FROM presence WHERE accountId=:account AND address=:address")
    abstract void deletePresences(long account, Jid address);

    @Query(
            "DELETE FROM presence WHERE accountId=:account AND address=:address AND"
                    + " resource=:resource")
    abstract void deletePresence(long account, Jid address, String resource);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(PresenceEntity entity);

    public void set(
            @NonNull final Account account,
            @NonNull final Jid address,
            @Nullable final String resource,
            @Nullable final PresenceType type,
            @Nullable final PresenceShow show,
            @Nullable final String status) {
        if (resource == null
                && Arrays.asList(PresenceType.ERROR, PresenceType.UNAVAILABLE).contains(type)) {
            deletePresences(account.id, address);
        }
        if (type == PresenceType.UNAVAILABLE) {
            if (resource != null) {
                deletePresence(account.id, address, resource);
            }
            // unavailable presence only delete previous nothing left to do
            return;
        }
        final var entity = PresenceEntity.of(account.id, address, resource, type, show, status);
        insert(entity);
    }
}
