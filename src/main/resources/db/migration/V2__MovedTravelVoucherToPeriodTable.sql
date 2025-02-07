ALTER TABLE patient
    DROP COLUMN travel_voucher;

ALTER TABLE period
    ADD COLUMN travel_voucher SMALLINT NOT NULL;
