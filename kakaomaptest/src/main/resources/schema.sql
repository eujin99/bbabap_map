DROP TABLE charge IF EXISTS;

CREATE TABLE charge (
                        CHARGE_ID VARCHAR(40),
                        CHARGE_NAME VARCHAR(40),
                        CHARGE_ADDR VARCHAR(100),
                        CHARGE_TYPE VARCHAR(40),
                        BUSI_NAME VARCHAR(40),
                        PRIMARY KEY(CHARGE_ID)
);