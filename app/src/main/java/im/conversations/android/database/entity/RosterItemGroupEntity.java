package im.conversations.android.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "roster_group",
        primaryKeys = {"rosterItemId", "groupId"},
        foreignKeys = {
            @ForeignKey(
                    entity = RosterItemEntity.class,
                    parentColumns = {"id"},
                    childColumns = {"rosterItemId"},
                    onDelete = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = GroupEntity.class,
                    parentColumns = {"id"},
                    childColumns = {"groupId"},
                    onDelete = ForeignKey.RESTRICT),
        },
        indices = {@Index(value = "groupId")})
public class RosterItemGroupEntity {

    @NonNull public Long rosterItemId;

    @NonNull public Long groupId;

    public static RosterItemGroupEntity of(long rosterItemId, final long groupId) {
        final var entity = new RosterItemGroupEntity();
        entity.rosterItemId = rosterItemId;
        entity.groupId = groupId;
        return entity;
    }
}