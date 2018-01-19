INSERT INTO `researchers` (`id`, `organisation`, `organisation_uid`, `employee_type`, `is_authorative`, `name`, `email`)
VALUES
  (1, 'example.org', 'john.doe', 'NOT', 0, 'John Doe', 'john.doe@example.org');
INSERT INTO `researchers` (`id`, `organisation`, `organisation_uid`, `employee_type`, `is_authorative`, `name`, `email`)
VALUES
  (2, 'example.org', 'mary.doe', 'CURRENT', 0, 'Mary Doe', 'mary.doe@example.org');
INSERT INTO `researchers` (`id`, `organisation`, `organisation_uid`, `employee_type`, `is_authorative`, `name`, `email`)
VALUES
  (3, 'example.org', 'steward.doe', 'FORMER', 0, 'Steward Doe', 'steward.doe@example.org');
INSERT INTO `identities` (`id`, `identity_value`, `identity_type`)
VALUES
  (1, 'https://orcid.org/0000-0002-3843-3472', 'ORCID');
INSERT INTO `identities` (`id`, `identity_value`, `identity_type`)
VALUES
  (2, 'https://orcid.org/0000-0002-3843-3473', 'ORCID');
INSERT INTO `identities` (`id`, `identity_value`, `identity_type`)
VALUES
  (3, '15737449500', 'SCOPUS');
INSERT INTO `researchers_identities` (`id`, `researcher_id`, `identity_id`)
VALUES
  (1, 1, 1);
INSERT INTO `researchers_identities` (`id`, `researcher_id`, `identity_id`)
VALUES
  (2, 2, 2);
INSERT INTO `researchers_identities` (`id`, `researcher_id`, `identity_id`)
VALUES
  (3, 2, 3);
INSERT INTO `researchers_parents_children` (`id`, `parent_id`, `child_id`)
VALUES
  (1, 1, 2);
INSERT INTO `researchers_parents_children` (`id`, `parent_id`, `child_id`)
VALUES
  (2, 3, 1);
INSERT INTO `researchers_parents_children` (`id`, `parent_id`, `child_id`)
VALUES
  (3, 2, 3);
INSERT INTO `papers` (`id`, `title`, `publisher`, `published`, `doi`, `public_url`)
VALUES
  (1, 'nice book', 'example', '2018-01-19 11:36:52', '10.1000/182', 'http://example.com/nice_book');
INSERT INTO `papers` (`id`, `title`, `publisher`, `published`, `doi`, `public_url`)
VALUES
  (2, 'boring book', 'example', '2018-01-19 11:36:52', '10.1000/183', 'http://example.com/boring_book');
INSERT INTO `authors` (`id`, `researcher_id`, `paper_id`, `co_author`)
VALUES
  (1, 1, 1, 0);
INSERT INTO `authors` (`id`, `researcher_id`, `paper_id`, `co_author`)
VALUES
  (2, 2, 2, 1);
INSERT INTO `authors` (`id`, `researcher_id`, `paper_id`, `co_author`)
VALUES
  (3, 3, 2, 0);
INSERT INTO `authors` (`id`, `researcher_id`, `paper_id`, `co_author`)
VALUES
  (4, 1, 2, 1);





