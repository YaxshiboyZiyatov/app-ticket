package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AttachmentContent extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    private byte[] bytes;

}
