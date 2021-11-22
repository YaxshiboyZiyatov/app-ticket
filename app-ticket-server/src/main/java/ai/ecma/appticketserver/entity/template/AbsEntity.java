package ai.ecma.appticketserver.entity.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@MappedSuperclass
@Data
@EntityListeners(value = AuditingEntityListener.class)
public abstract class AbsEntity {
    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false )
    @JsonIgnore
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private Timestamp updatedAt;

    @CreatedBy
    @Column(updatable = false)
    @JsonIgnore
    private UUID createdById;

    @LastModifiedBy
    @JsonIgnore
    private UUID updatedById;

    @JsonIgnore
    private boolean deleted;
}
