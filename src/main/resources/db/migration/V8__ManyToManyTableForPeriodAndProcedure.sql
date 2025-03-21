CREATE TABLE period_procedure
(
    period_id    BIGINT NOT NULL,
    procedure_id BIGINT NOT NULL
);

ALTER TABLE period_procedure
    ADD CONSTRAINT fk_perpro_on_patient_stay_period FOREIGN KEY (period_id) REFERENCES period (id);

ALTER TABLE period_procedure
    ADD CONSTRAINT fk_perpro_on_procedure FOREIGN KEY (procedure_id) REFERENCES procedure (id);