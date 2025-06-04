package com.ducthong.TopCV.extract_data.entity;

import com.ducthong.TopCV.domain.entity.Auditable;
import com.ducthong.TopCV.extract_data.dto.AwardAuditDTO;
import com.ducthong.TopCV.utility.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@SqlResultSetMappings(
        @SqlResultSetMapping(
                name = "AwardAuditMapping",
                classes = @ConstructorResult(
                        targetClass = AwardAuditDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = String.class),
                                @ColumnResult(name = "rev", type = Integer.class),
                                @ColumnResult(name = "revtype", type = Integer.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "time", type = String.class),
                                @ColumnResult(name = "revtstmp", type = Long.class),
                        }
                )
        )
)
@Entity
@Table(name = "awards")
@Audited
@AuditTable(value = "award_audit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award extends Auditable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Audited
    private String time;
    @Audited
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @JoinColumn(name = "cv_infor_id")
    private CvInfor cvInfor;

    public static boolean checkAllNull(Award award) {
        return StringUtils.isNullOrEmpty(award.getName()) && StringUtils.isNullOrEmpty(award.getTime());
    }
}
