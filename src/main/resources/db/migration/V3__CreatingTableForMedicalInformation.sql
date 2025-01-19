CREATE TABLE med_info
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    discharge_summary VARCHAR(255),
    initial_diagnosis VARCHAR(255),
    CONSTRAINT pk_med_info PRIMARY KEY (id)
);

ALTER TABLE period
ADD med_info_id    BIGINT;

ALTER TABLE period
    ADD CONSTRAINT FK_PERIOD_ON_MED_INFO FOREIGN KEY (med_info_id) REFERENCES med_info (id);
