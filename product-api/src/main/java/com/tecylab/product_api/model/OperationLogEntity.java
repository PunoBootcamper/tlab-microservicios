package com.tecylab.product_api.model;

import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "operation_logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OperationLogEntity {
    @Id
    private String id;

    private String operation;

    private String description;

    private LocalDateTime timestamp;
}
