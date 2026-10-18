package com.tecylab.client_api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "operation_logs")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OperationLogEntity {
    @Id
    private String id;

    private String operation;

    private String description;

    private LocalDateTime timestamp;
}
