package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Event extends AbsEntity {

    private String name;

    @OneToOne
    private Attachment banner;

    @OneToOne
    private Attachment schema;

    @ManyToOne
    private Place place;

    private String description;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EventSession> eventSession;

    public Event(String name, Attachment banner, Attachment schema, Place place, String description) {
        this.name = name;
        this.banner = banner;
        this.schema = schema;
        this.place = place;
        this.description = description;
    }
}
