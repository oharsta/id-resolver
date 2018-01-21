INSERT INTO `researchers` (`id`, `organisation`, `organisation_uid`, `employee_type`, `is_authoritative`, `name`, `email`)
VALUES
  (1, 'example.org', 'john.doe', 'NOT', 0, 'John Doe', 'john.doe@example.org');
INSERT INTO `researchers` (`id`, `organisation`, `organisation_uid`, `employee_type`, `is_authoritative`, `name`, `email`)
VALUES
  (2, 'example.org', 'mary.doe', 'CURRENT', 1, 'Mary Doe', 'mary.doe@example.org');
INSERT INTO `researchers` (`id`, `organisation`, `organisation_uid`, `employee_type`, `is_authoritative`, `name`, `email`)
VALUES
  (3, 'example.org', 'steward.doe', 'FORMER', 1, 'Steward Doe', 'steward.doe@example.org');
INSERT INTO `identities` (`id`, `identity_value`, `identity_type`, `researcher_id`)
VALUES
  (1, 'https://orcid.org/0000-0002-3843-3472', 'ORCID', 1);
INSERT INTO `identities` (`id`, `identity_value`, `identity_type`, `researcher_id`)
VALUES
  (2, 'https://orcid.org/0000-0002-3843-3473', 'ORCID', 2);
INSERT INTO `identities` (`id`, `identity_value`, `identity_type`, `researcher_id`)
VALUES
  (3, '15737449500', 'SCOPUS', 2);
INSERT INTO `researcher_relations` (`id`, `parent_id`, `child_id`, `weight`)
VALUES
  (1, 1, 2, 10);
INSERT INTO `researcher_relations` (`id`, `parent_id`, `child_id`, `weight`)
VALUES
  (2, 3, 1, 50);
INSERT INTO `researcher_relations` (`id`, `parent_id`, `child_id`, `weight`)
VALUES
  (3, 1, 3, 100);
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





