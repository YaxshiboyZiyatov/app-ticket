package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "artist_id"}))
public class EventArtist extends AbsEntity {

    @ManyToOne
    private Event event;

    @ManyToOne
    private Artist artist;
    @Column(name = "main_artist")
    private boolean mainArtist; //Bosh artistmi?

}
