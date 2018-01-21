CREATE TABLE researchers (
  id               INT                    NOT NULL AUTO_INCREMENT PRIMARY KEY,
  organisation     VARCHAR(255)           NOT NULL,
  organisation_uid VARCHAR(255)           NOT NULL,
  employee_type    VARCHAR(255)           NOT NULL DEFAULT 'NOT',
  is_authoritative TINYINT(1) DEFAULT '0' NOT NULL,
  name             VARCHAR(255),
  email            VARCHAR(255),
  created          TIMESTAMP              NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated          TIMESTAMP              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
  ENGINE = InnoDB;
ALTER TABLE researchers
  ADD INDEX researchers_organisation_index (organisation);
ALTER TABLE researchers
  ADD INDEX researchers_organisation_uid_index (organisation_uid);
ALTER TABLE researchers
  ADD INDEX researchers_organisation_email_index (email);

CREATE TABLE identities (
  id             INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  researcher_id  INT          NOT NULL,
  identity_value VARCHAR(255) NOT NULL,
  identity_type  VARCHAR(255) NOT NULL,
  CONSTRAINT identities_researcher_id FOREIGN KEY (researcher_id) REFERENCES researchers (id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB;
ALTER TABLE identities
  ADD INDEX identities_value_index (identity_value);
ALTER TABLE identities
  ADD INDEX identities_type_index (identity_type);


CREATE TABLE researcher_relations (
  id        INT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  parent_id INT       NOT NULL,
  child_id  INT       NOT NULL,
  weight    MEDIUMINT NOT NULL,
  CONSTRAINT researchers_parents_children_parent_id FOREIGN KEY (parent_id) REFERENCES researchers (id)
    ON DELETE CASCADE,
  CONSTRAINT researchers_parents_children_child_id FOREIGN KEY (child_id) REFERENCES researchers (id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB;

CREATE TABLE papers (
  id         INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title      VARCHAR(255) NOT NULL,
  publisher  VARCHAR(255),
  published  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  doi        VARCHAR(255),
  public_url VARCHAR(255)
)
  ENGINE = InnoDB;


CREATE TABLE authors (
  id            INT                    NOT NULL AUTO_INCREMENT PRIMARY KEY,
  researcher_id INT                    NOT NULL,
  paper_id      INT                    NOT NULL,
  co_author     TINYINT(1) DEFAULT '1' NOT NULL,
  created       TIMESTAMP              NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated       TIMESTAMP              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT authors_researcher_id FOREIGN KEY (researcher_id) REFERENCES researchers (id)
    ON DELETE CASCADE,
  CONSTRAINT authors_paper_id FOREIGN KEY (paper_id) REFERENCES papers (id)
    ON DELETE CASCADE

)
  ENGINE = InnoDB;
